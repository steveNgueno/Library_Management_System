package com.example.LMS.service;

import com.example.LMS.domain.Enum.Action;
import com.example.LMS.domain.Enum.Status;
import com.example.LMS.domain.model.History;

import java.util.List;

public interface HistoryService {

    History create(Status status, Action action, String description);
    List<History> get();
    List<History> getByAction(Action action);
}
