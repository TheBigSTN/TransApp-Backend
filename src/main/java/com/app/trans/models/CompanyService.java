package com.app.trans.models;

import com.app.trans.dtos.CompanyServiceDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyService {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonBackReference
    private Company company;

    @Column(nullable = false)
    private String title;

    @Column(name = "display_order")
    private Integer order;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    public CompanyServiceDTO toDTO() {
        return CompanyServiceDTO.builder()
                .title(this.title)
                .order(this.order)
                .build();
    }
}