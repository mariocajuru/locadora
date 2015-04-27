package br.com.locadora.modelo;

// Generated 17/07/2014 09:18:53 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * Banco generated by hbm2java
 */
@Audited
@Entity
@Table(name = "BANCO", catalog = "renovarsistemas")
@AuditTable(value="BANCO", schema="log", catalog="renovarsistemas")
public class Banco implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4767857461145845006L;
	private int banId;
	private String banNome;
	private Set<DadosBancariosProprietario> dadosBancariosProprietarios = new HashSet<DadosBancariosProprietario>(
			0);

	public Banco() {
	}

	public Banco(int banId) {
		this.banId = banId;
	}

	public Banco(int banId, String banNome,
			Set<DadosBancariosProprietario> dadosBancariosProprietarios) {
		this.banId = banId;
		this.banNome = banNome;
		this.dadosBancariosProprietarios = dadosBancariosProprietarios;
	}

	@Id
	@GeneratedValue
	@Column(name = "BAN_ID", unique = true, nullable = false)
	public int getBanId() {
		return this.banId;
	}

	public void setBanId(int banId) {
		this.banId = banId;
	}

	@Column(name = "BAN_NOME", length = 50)
	public String getBanNome() {
		return this.banNome;
	}

	public void setBanNome(String banNome) {
		this.banNome = banNome;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "banco")
	@NotAudited
	public Set<DadosBancariosProprietario> getDadosBancariosProprietarios() {
		return this.dadosBancariosProprietarios;
	}

	public void setDadosBancariosProprietarios(
			Set<DadosBancariosProprietario> dadosBancariosProprietarios) {
		this.dadosBancariosProprietarios = dadosBancariosProprietarios;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + banId;
		result = prime * result + ((banNome == null) ? 0 : banNome.hashCode());
		result = prime
				* result
				+ ((dadosBancariosProprietarios == null) ? 0
						: dadosBancariosProprietarios.hashCode());
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
		Banco other = (Banco) obj;
		if (banId != other.banId)
			return false;
		if (banNome == null) {
			if (other.banNome != null)
				return false;
		} else if (!banNome.equals(other.banNome))
			return false;
		if (dadosBancariosProprietarios == null) {
			if (other.dadosBancariosProprietarios != null)
				return false;
		} else if (!dadosBancariosProprietarios
				.equals(other.dadosBancariosProprietarios))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Banco [banId=" + banId + "]";
	}

}