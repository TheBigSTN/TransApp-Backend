package com.app.trans.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "expired_tokens")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ExpiredTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "id")
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "iat")
    private long iat; // The issued at timestamp for the token

    // Constructor for blacklisted token
    public ExpiredTokens(String email, long iat) {
        this.email = email;
        this.iat = iat;
    }
}