package com.app.trans.models;

import com.app.trans.models.enums.TipAlimentare;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@ToString(exclude = { "masina" })
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "alimentare")
public class Alimentare {

    @Id
    @Column(name = "id_alimentare")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_masina")
    private Masina masina;

    @Column(name = "data_alimentare")
    private LocalDate data_alimentare;

    @Column(name = "numar_litri")
    private Float litri;

    @Column(name = "pret_unitar")
    private Float pret_unitar;

    @Column(name = "tip")
    @Enumerated(EnumType.STRING)
    private TipAlimentare tip;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
