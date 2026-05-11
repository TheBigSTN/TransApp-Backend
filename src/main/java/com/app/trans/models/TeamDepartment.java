package com.app.trans.models;

import com.app.trans.dtos.TeamDepartmentDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "team_departments")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDepartment {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonBackReference
    private Company company;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String count;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "display_order")
    private Integer order;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    public TeamDepartmentDTO toDTO() {
        return TeamDepartmentDTO.builder()
                .count(this.count)
                .description(this.description)
                .role(this.role)
                .build();
    }
}