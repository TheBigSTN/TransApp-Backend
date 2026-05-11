package com.app.trans.models;

import com.app.trans.dtos.PendingAccountDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PendingAccount {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private Role role;
    private String activationCode;

    private Instant expiresAt;

    public PendingAccountDTO toDTO() {
        return PendingAccountDTO.builder()
                .id(this.id)
                .activationCode(this.activationCode)
                .expiresAt(this.expiresAt)
                .role(this.role.toString())
                .build();
    }
}
