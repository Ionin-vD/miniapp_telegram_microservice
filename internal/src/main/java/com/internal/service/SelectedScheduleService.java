package com.internal.service;

import java.util.List;
import java.util.Optional;

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

    public Optional<SelectedSchedule> findByScheduleId(Long id) {
        return selectedScheduleRepository.findBySchedule_Id(id);
    }
}
