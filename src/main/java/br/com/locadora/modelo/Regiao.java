package br.com.locadora.modelo;

// Generated 11/04/2014 14:12:50 by Hibernate Tools 4.0.0

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
 * Regiao generated by hbm2java
 */
@Entity
@Audited
@Table(name = "REGIAO", catalog = "renovarsistemas")
@AuditTable(value="REGIAO", schema="log", catalog="renovarsistemas")
public class Regiao implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -671433494038046374L;
	private int regId;
	private String regNome;
	private Set<Bairro> bairros = new HashSet<Bairro>(0);
	private String regCorMapa;
	private Set<RegiaoCoordenada> regiaoCoordenadas = new HashSet<RegiaoCoordenada>(
			0);
	public Regiao() {
	}

	public Regiao(int regId) {
		this.regId = regId;
	}

	public Regiao(int regId, String regNome, Set<Bairro> bairros,
			Set<RegiaoCoordenada> regiaoCoordenadas,String regCorMapa) {
		this.regId = regId;
		this.regNome = regNome;
		this.bairros = bairros;
		this.regCorMapa = regCorMapa;
		this.regiaoCoordenadas=regiaoCoordenadas;
	}

	@Id
	@GeneratedValue
	@Column(name = "REG_ID", unique = true, nullable = false)
	public int getRegId() {
		return this.regId;
	}

	public void setRegId(int regId) {
		this.regId = regId;
	}

	@Column(name = "REG_NOME",unique=true, length = 40)
	public String getRegNome() {
		return this.regNome;
	}

	public void setRegNome(String regNome) {
		this.regNome = regNome;
	}
	@Column(name = "REG_COR_MAPA", length = 20)
	public String getRegCorMapa() {
		return regCorMapa;
	}

	public void setRegCorMapa(String regCorMapa) {
		this.regCorMapa = regCorMapa;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "regiao")
	public Set<RegiaoCoordenada> getRegiaoCoordenadas() {
		return this.regiaoCoordenadas;
	}

	public void setRegiaoCoordenadas(Set<RegiaoCoordenada> regiaoCoordenadas) {
		this.regiaoCoordenadas = regiaoCoordenadas;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "regiao")
	@NotAudited
	public Set<Bairro> getBairros() {
		return this.bairros;
	}

	public void setBairros(Set<Bairro> bairros) {
		this.bairros = bairros;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairros == null) ? 0 : bairros.hashCode());
		result = prime * result
				+ ((regCorMapa == null) ? 0 : regCorMapa.hashCode());
		result = prime * result + regId;
		result = prime * result + ((regNome == null) ? 0 : regNome.hashCode());
		result = prime
				* result
				+ ((regiaoCoordenadas == null) ? 0 : regiaoCoordenadas
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
		Regiao other = (Regiao) obj;
		if (bairros == null) {
			if (other.bairros != null)
				return false;
		} else if (!bairros.equals(other.bairros))
			return false;
		if (regCorMapa == null) {
			if (other.regCorMapa != null)
				return false;
		} else if (!regCorMapa.equals(other.regCorMapa))
			return false;
		if (regId != other.regId)
			return false;
		if (regNome == null) {
			if (other.regNome != null)
				return false;
		} else if (!regNome.equals(other.regNome))
			return false;
		if (regiaoCoordenadas == null) {
			if (other.regiaoCoordenadas != null)
				return false;
		} else if (!regiaoCoordenadas.equals(other.regiaoCoordenadas))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Regiao [regId=" + regId + "]";
	}

}