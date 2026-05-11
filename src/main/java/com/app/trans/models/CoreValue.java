package com.app.trans.models;

import com.app.trans.dtos.CoreValueDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "core_values")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreValue {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonBackReference
    private Company company;

    @Column(nullable = false)
    private String icon; // e.g., "Target", "Zap", "Shield", "TrendingUp"

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "display_order")
    private Integer order;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    public CoreValueDTO toDTO() {
        return CoreValueDTO.builder()
                .icon(this.icon)
                .title(this.title)
                .description(this.description)
                .build();
    }
}