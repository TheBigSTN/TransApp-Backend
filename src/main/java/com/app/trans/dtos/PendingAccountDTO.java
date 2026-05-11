package com.app.trans.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingAccountDTO {
    private UUID id;
    private String activationCode;
    private Instant expiresAt;
    private String role;
}
