package com.example.LMS.controller;

import com.example.LMS.domain.Enum.Action;
import com.example.LMS.domain.model.History;
import com.example.LMS.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/get/all")
    public ResponseEntity<List<History>> get(){
        return ResponseEntity.ok(historyService.get());
    }

    @GetMapping("/get/history")
    public ResponseEntity<List<History>> getByAction(@RequestParam Action action){
        return ResponseEntity.ok(historyService.getByAction(action));
    }
}
