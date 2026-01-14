package com.example.LMS.domain.model;

import com.example.LMS.domain.Enum.Action;
import com.example.LMS.domain.Enum.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="history")
public class History extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name="action")
    private Action action;

    @Column(name="description")
    private String description;
}
