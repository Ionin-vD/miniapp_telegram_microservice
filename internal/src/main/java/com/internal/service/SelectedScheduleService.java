package com.internal.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internal.model.SelectedSchedule;
import com.internal.repository.SelectedScheduleRepository;

@Service
public class SelectedScheduleService {
    @Autowired
    private SelectedScheduleRepository selectedScheduleRepository;

    public List<SelectedSchedule> findAll() {
        return selectedScheduleRepository.findAll();
    }

    public SelectedSchedule save(SelectedSchedule request) {
        return selectedScheduleRepository.save(request);
    }

    public List<SelectedSchedule> findByScheduleId(Long id) {
        return selectedScheduleRepository.findBySchedule_Id(id);
    }

    public void deleteById(Long scheduleId) {
        selectedScheduleRepository.deleteById(scheduleId);
    }

    public List<SelectedSchedule> findAllByUserId(Long userId) {
        return selectedScheduleRepository.findAllByUserId(userId);
    }

    public List<SelectedSchedule> findByScheduleIdIn(List<Long> scheduleIds) {
        return selectedScheduleRepository.findByScheduleIdIn(scheduleIds);
    }
}
