package com.example.LMS.repository;

import com.example.LMS.domain.Enum.Action;
import com.example.LMS.domain.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByAction(Action action);

    boolean existsByAction(Action action);
}
