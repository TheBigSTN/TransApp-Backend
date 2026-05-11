package com.app.trans.models;

import com.app.trans.dtos.CompanyInfoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "company_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfo {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false, unique = true)
    @JsonBackReference
    private Company company;

    @Column(nullable = false)
    private Integer foundedYear;

    @Column(nullable = false)
    private String employees;

    @Column(nullable = false)
    private String headquarters;

    @Column(columnDefinition = "TEXT")
    private String ourMission;

    @Column(columnDefinition = "TEXT")
    private String ourVision;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant updatedAt;

    public CompanyInfoDTO toDTO() {
        return CompanyInfoDTO.builder()
                .name(this.company.getName())
                .foundedYear(this.foundedYear)
                .employees(this.employees)
                .headquarters(this.headquarters)
                .mission(this.ourMission)
                .vision(this.ourVision)
                .build();
    }
}