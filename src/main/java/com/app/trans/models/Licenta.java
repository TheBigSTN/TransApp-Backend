package com.app.trans.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import com.app.trans.dtos.LicentaDTO;
import com.app.trans.models.enums.TipLicenta;

@Entity
@Getter
@Setter
@ToString(exclude = { "masina" })
@EqualsAndHashCode
@Table(name = "licenta")
@AllArgsConstructor
@NoArgsConstructor
public class Licenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	@Column(name = "id_licenta")
	private long id;

	@ManyToOne
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

	public Licenta(LicentaDTO licentaDTO) {
		this.id = licentaDTO.getId();
		this.masina.setId(licentaDTO.getMasinaId());
		this.tip = licentaDTO.getTip();
		this.serie = licentaDTO.getSerie();
		this.data_inceput = licentaDTO.getData_inceput();
		this.data_final = licentaDTO.getData_final();
		this.pret = licentaDTO.getPret();
	}

}
