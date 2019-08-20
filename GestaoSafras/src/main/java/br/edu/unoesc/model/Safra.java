package br.edu.unoesc.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
@Table(name = "Safra")
public class Safra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "tipoSafra_codigo")
	private TipoSafra tipo = new TipoSafra();
	
	private LocalDate data;
	private Double tamanho;
	private String tipoSemente;
	private String observacao;
	private boolean emAtividade;
	
	@OneToMany(mappedBy = "safra",targetEntity = Procedimento.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Set<Procedimento> procedimentos = new HashSet<Procedimento>();
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public void adicionaProcedimento(Procedimento procedimento) {
		this.procedimentos.add(procedimento);
	}

	public Safra(Long codigo, String descricao, TipoSafra tipo, Double tamanho, String tipoSemente, String observacao,
			boolean emAtividade, Set<Procedimento> procedimentos) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.tipo = tipo;
		this.tamanho = tamanho;
		this.tipoSemente = tipoSemente;
		this.observacao = observacao;
		this.emAtividade = emAtividade;
		this.procedimentos = procedimentos;
	}

	public Safra() {
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoSafra getTipo() {
		return tipo;
	}

	public void setTipo(TipoSafra tipo) {
		this.tipo = tipo;
	}

	public Double getTamanho() {
		return tamanho;
	}

	public void setTamanho(Double tamanho) {
		this.tamanho = tamanho;
	}

	public String getTipoSemente() {
		return tipoSemente;
	}

	public void setTipoSemente(String tipoSemente) {
		this.tipoSemente = tipoSemente;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public boolean isEmAtividade() {
		return emAtividade;
	}

	public void setEmAtividade(boolean emAtividade) {
		this.emAtividade = emAtividade;
	}

	public Set<Procedimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(Set<Procedimento> procedimentos) {
		this.procedimentos = procedimentos;
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
	
	@Override
	public String toString() {
		return this.tipo.getNome()+ " - " + this.descricao;
	}

}
