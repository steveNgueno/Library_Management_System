package com.example.LMS.service.impl;

import com.example.LMS.domain.Enum.Action;
import com.example.LMS.domain.Enum.Status;
import com.example.LMS.domain.model.History;
import com.example.LMS.repository.HistoryRepository;
import com.example.LMS.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository repo;

    @Override
    public History create(Status status, Action action, String description){

        History history = History.builder()
                .status(status)
                .action(action)
                .description(description)
                .build();
        return repo.save(history);
    }

    @Override
    public List<History> get() {
        return repo.findAll();
    }

    @Override
    public List<History> getByAction(Action action) {

        if(!repo.existsByAction(action)) throw new IllegalArgumentException("no history found with : "+action.toString());
        return repo.findByAction(action);
    }
}
