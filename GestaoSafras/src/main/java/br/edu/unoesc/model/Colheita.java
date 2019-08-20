package br.edu.unoesc.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.unoesc.conversor.Arredondador;

@Entity 
@Table(name = "Colheita")
public class Colheita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(name = "safra_id")
	private Safra safra;
	
	private LocalDate dataColheita;
	private Double qtdColhida;
	private Double valorVenda;
	
	private Double gastos() {
		Double gastos = 0.0;
		Set<Procedimento> procedimentos = this.safra.getProcedimentos(); 
		for (Procedimento procedimento : procedimentos) {
			gastos += procedimento.getValorGasto();
		}
		return gastos;
	}
	
	public Long duracaoDias() {
		return ChronoUnit.DAYS.between(this.safra.getData(), this.dataColheita);
	}
	
	public String lucros() {
		return new Arredondador().arredondar(((this.valorVenda * this.qtdColhida) - gastos()));
	}
	
	public String getGastos() {
		return new Arredondador().arredondar(gastos());
	}
	
	public String valorTotalRecebido() {
		return new Arredondador().arredondar((this.qtdColhida * this.valorVenda));
	}
	
	public String sacasHectare() {
		return new Arredondador().arredondar(this.qtdColhida / this.safra.getTamanho());
	}
	
	public String dataFormatada() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = this.dataColheita.format(formatter);
        return dataFormatada;
	}
	
	public Colheita() {
	
	}

	public Colheita(Long codigo, Safra safra, LocalDate dataColheita, Double qtdColhida, Double valorVenda) {
		super();
		this.codigo = codigo;
		this.safra = safra;
		this.dataColheita = dataColheita;
		this.qtdColhida = qtdColhida;
		this.valorVenda = valorVenda;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Safra getSafra() {
		return safra;
	}

	public void setSafra(Safra safra) {
		this.safra = safra;
	}

	public LocalDate getDataColheita() {
		return dataColheita;
	}
	
	public void setDataColheita(LocalDate dataColheita) {
		this.dataColheita = dataColheita;
	}

	public Double getQtdColhida() {
		return qtdColhida;
	}

	public void setQtdColhida(Double qtdColhida) {
		this.qtdColhida = qtdColhida;
	}

	public Double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(Double valorVenda) {
		this.valorVenda = valorVenda;
	}
	
	public String tipo() {
		return this.safra.getTipo() + " - "+ this.safra.getDescricao();
	}

	@Override
	public String toString() {
		return this.dataFormatada() + " - "+ this.safra.getTipo().getNome();
	}
	
}
