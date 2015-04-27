package br.com.locadora.modelo;

// Generated 10/07/2014 16:00:50 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * ContasFinanceiro generated by hbm2java
 */
@Audited
@Entity
@Table(name = "CONTAS_FINANCEIRO", catalog = "renovarsistemas")
@AuditTable(value="CONTAS_FINANCEIRO", schema="log", catalog="renovarsistemas")
public class ContasFinanceiro implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3679420520182024764L;
	private int conFinId;
	private String conFinNome;
	private Set<GrupoDeContas> grupoFinanceiroDebito= new HashSet<GrupoDeContas>(0);
	private Set<GrupoDeContas> grupoFinanceiroCredito= new HashSet<GrupoDeContas>(0);

	public ContasFinanceiro() {
	}

	public ContasFinanceiro(int conFinId) {
		this.conFinId = conFinId;
	}

	public ContasFinanceiro(int conFinId, String conFinNome,
			Set<GrupoDeContas> grupoFinanceiroDebito,
			Set<GrupoDeContas> grupoFinanceiroCredito) {
		this.conFinId = conFinId;
		this.conFinNome = conFinNome;
		this.grupoFinanceiroDebito= grupoFinanceiroDebito;
		this.grupoFinanceiroCredito= grupoFinanceiroCredito;
	}

	@Id
	@GeneratedValue
	@Column(name = "CON_FIN_ID", unique = true, nullable = false)
	public int getConFinId() {
		return this.conFinId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setConFinId(int conFinId) {
		this.conFinId = conFinId;
	}

	@Column(name = "CON_FIN_NOME", length = 60)
	public String getConFinNome() {
		return this.conFinNome;
	}

	public void setConFinNome(String conFinNome) {
		this.conFinNome = conFinNome;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "GRUPO_FINANCEIRO_DEBITO", catalog = "renovarsistemas", joinColumns = { @JoinColumn(name = "CON_FIN_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "GRU_CON_ID", nullable = false, updatable = false) })
	public Set<GrupoDeContas> getGrupoFinanceiroDebito() {
		return this.grupoFinanceiroDebito;
	}

	public void setGrupoFinanceiroDebito(Set<GrupoDeContas> grupoFinanceiroDebito) {
		this.grupoFinanceiroDebito= grupoFinanceiroDebito;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "GRUPO_FINANCEIRO_CREDITO", catalog = "renovarsistemas", joinColumns = { @JoinColumn(name = "CON_FIN_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "GRU_CON_ID", nullable = false, updatable = false) })
	public Set<GrupoDeContas> getGrupoFinanceiroCredito() {
		return this.grupoFinanceiroCredito;
	}

	@Override
	public String toString() {
		return "ContasFinanceiro [conFinId=" + conFinId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + conFinId;
		result = prime * result
				+ ((conFinNome == null) ? 0 : conFinNome.hashCode());
		result = prime
				* result
				+ ((grupoFinanceiroCredito == null) ? 0
						: grupoFinanceiroCredito.hashCode());
		result = prime
				* result
				+ ((grupoFinanceiroDebito == null) ? 0 : grupoFinanceiroDebito
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContasFinanceiro other = (ContasFinanceiro) obj;
		if (conFinId != other.conFinId)
			return false;
		if (conFinNome == null) {
			if (other.conFinNome != null)
				return false;
		} else if (!conFinNome.equals(other.conFinNome))
			return false;
		if (grupoFinanceiroCredito == null) {
			if (other.grupoFinanceiroCredito != null)
				return false;
		} else if (!grupoFinanceiroCredito.equals(other.grupoFinanceiroCredito))
			return false;
		if (grupoFinanceiroDebito == null) {
			if (other.grupoFinanceiroDebito != null)
				return false;
		} else if (!grupoFinanceiroDebito.equals(other.grupoFinanceiroDebito))
			return false;
		return true;
	}

	public void setGrupoFinanceiroCredito(Set<GrupoDeContas> grupoFinanceiroCredito) {
		this.grupoFinanceiroCredito= grupoFinanceiroCredito;
	}

}