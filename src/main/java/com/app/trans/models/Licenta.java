package com.app.trans.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import com.app.trans.models.enums.TipLicenta;

@Entity
@Data
@Table(name = "licenta")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Licenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	@Column(name = "id_licenta")
	private long id;

	@ManyToOne
	@ToString.Exclude()
	@JoinColumn(name = "id_masina")
	private Masina masina;

	@Column(name = "tip")
	@Enumerated(EnumType.STRING)
	private TipLicenta tip;

	@Column(name = "serie")
	private String serie;

	@Column(name = "data_inceput")
	private LocalDate data_inceput;

	@Column(name = "data_final")
	private LocalDate data_final;

	@Column(name = "pret")
	private Float pret;

	@ManyToOne
	@ToString.Exclude()
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
}
