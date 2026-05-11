package com.app.trans.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

import com.app.trans.dtos.LicentaDTO;
import com.app.trans.models.enums.TipLicenta;

@Entity
@Getter
@Setter
@ToString(exclude = { "masina" })
@EqualsAndHashCode
@Table(name = "licenta")
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

	public Licenta(long id, Masina masina, TipLicenta tip, String serie, LocalDate data_inceput, LocalDate data_final,
			Float pret) {
		super();
		this.id = id;
		this.masina = masina;
		this.tip = tip;
		this.serie = serie;
		this.data_inceput = data_inceput;
		this.data_final = data_final;
		this.pret = pret;
	}

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
