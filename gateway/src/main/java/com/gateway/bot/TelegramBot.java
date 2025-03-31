package com.gateway.bot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.gateway.config.BotConfig;
import com.gateway.dto.ChatIdRequest;
import com.gateway.dto.CreateUserRequest;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final Map<Long, String> userState = new HashMap<>();
    private final int MAX_FIO_LENGTH = 50;
    private final int MIN_FIO_LENGTH = 5;
    @Autowired
    private WebClient webClient;

    @SuppressWarnings("deprecation")
    @Autowired
    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    public String sendRequestFindUserByChatId(long chatId) {
        ChatIdRequest request = new ChatIdRequest();
        request.setChat_id(chatId);

        try {
            String response = webClient.post()
                    .uri("/api/mini_app/find_user_by_chat_id")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return response;
        } catch (WebClientResponseException e) {
            System.err.println("Ошибка:" + e.getMessage());
        }
        return null;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if ("/start".equals(messageText)) {
                int code = checkStart(chatId);

                String replyMessage = switch (code) {
                    case 200 -> "Добро пожаловать! Введите ваше ФИО:";
                    case 201 -> "С возвращением! Откройте WebApp.";
                    default -> "Произошла ошибка. Попробуйте позже.";
                };

                sendMessage(chatId, replyMessage);

                if (code == 200) {
                    userState.put(chatId, "awaiting_fio");
                }
            } else {
                if ("awaiting_fio".equals(userState.get(chatId))) {
                    String fio = messageText;

                    if (fio.length() > MAX_FIO_LENGTH) {
                        sendMessage(chatId, "ФИО слишком длинное.");
                        return;
                    }
                    if (fio.length() < MIN_FIO_LENGTH) {
                        sendMessage(chatId, "ФИО слишком короткое.");
                        return;
                    }
                    if (!fio.matches("^[а-яА-ЯёЁ]+(?:\\s[а-яА-ЯёЁ]+){1,2}$")) {
                        sendMessage(chatId, "Неверный формат ФИО.");
                        return;
                    }

                    registerUserBot(chatId, fio);
                    sendMessage(chatId, "Спасибо! Ожидайте подтверждение от администратора.");
                    userState.remove(chatId);
                } else {
                    sendMessage(chatId, "Я пока не понимаю это сообщение. Напиши /start.");
                }
            }
        }
    }

    private void registerUserBot(long chatId, String fio) {
        CreateUserRequest request = new CreateUserRequest();
        request.setChat_id(chatId);
        request.setFio(fio);
        request.setAuth(true);

        try {
            webClient.post()
                    .uri("/api/mini_app/create_user")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Ошибка:" + e.getMessage());
        }
    }

    private int checkStart(long chatId) {
        // Реализация проверки: 200 — новый, 201 — уже есть
        String res = sendRequestFindUserByChatId(chatId);
        if (res != null) {
            return 201;
        }
        return 200;
    }

    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
        }
    }
}