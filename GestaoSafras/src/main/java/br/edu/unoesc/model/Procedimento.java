package br.edu.unoesc.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.unoesc.conversor.Arredondador;

@Entity 
@Table(name = "Procedimento")
public class Procedimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	private String descricao;
	private String tipo;
	private Double quantidade;
	private Double valorGasto;
	private LocalDate data;
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name = "safra_id")
	private Safra safra;

	public String getValorGastoFormatado() {
		return new Arredondador().arredondar(this.valorGasto);
	}
	
	public Procedimento() {
		
	}

	public Procedimento(Long codigo, String descricao, String tipo, Double quantidade, Double valorGasto,
			LocalDate data, String observacao, Safra safra) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.tipo = tipo;
		this.quantidade = quantidade;
		this.valorGasto = valorGasto;
		this.data = data;
		this.observacao = observacao;
		this.safra = safra;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValorGasto() {
		return valorGasto;
	}

	public void setValorGasto(Double valorGasto) {
		this.valorGasto = valorGasto;
	}

	public LocalDate getData() {
		return data;
	}
	
	public String dataFormatada() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = this.data.format(formatter);
        return dataFormatada;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Safra getSafra() {
		return safra;
	}

	public void setSafra(Safra safra) {
		this.safra = safra;
	}

	@Override
	public String toString() {
		return descricao + " " + data;
	}

	
}
