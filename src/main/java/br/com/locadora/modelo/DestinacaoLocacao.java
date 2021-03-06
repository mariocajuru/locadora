package br.com.locadora.modelo;

// Generated 18/06/2014 15:07:04 by Hibernate Tools 4.0.0

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
 * DestinacaoLocacao generated by hbm2java
 */
@Audited
@Entity
@Table(name = "DESTINACAO_LOCACAO", catalog = "renovarsistemas")
@AuditTable(value="DESTINACAO_LOCACAO", schema="log", catalog="renovarsistemas")
public class DestinacaoLocacao implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 499688934405040842L;
	private int desLocId;
	private String desLocNome;
	private String desLocSeguro;
	private Set<Locacao> locacaos = new HashSet<Locacao>(0);

	public DestinacaoLocacao() {
	}

	public DestinacaoLocacao(int desLocId) {
		this.desLocId = desLocId;
	}

	public DestinacaoLocacao(int desLocId, String desLocNome,String desLocSeguro,
			Set<Locacao> locacaos) {
		this.desLocId = desLocId;
		this.desLocNome = desLocNome;
		this.desLocSeguro=desLocSeguro;
		this.locacaos = locacaos;
	}

	@Id
	@GeneratedValue
	@Column(name = "DES_LOC_ID", unique = true, nullable = false)
	public int getDesLocId() {
		return this.desLocId;
	}

	public void setDesLocId(int desLocId) {
		this.desLocId = desLocId;
	}

	@Column(name = "DES_LOC_NOME", length = 70)
	public String getDesLocNome() {
		return this.desLocNome;
	}

	public void setDesLocNome(String desLocNome) {
		this.desLocNome = desLocNome;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "destinacaoLocacao")
	@NotAudited
	public Set<Locacao> getLocacaos() {
		return this.locacaos;
	}

	public void setLocacaos(Set<Locacao> locacaos) {
		this.locacaos = locacaos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Column(name = "DES_LOC_SEGURO", length = 60)
	public String getDesLocSeguro() {
		return desLocSeguro;
	}

	public void setDesLocSeguro(String desLocSeguro) {
		this.desLocSeguro = desLocSeguro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + desLocId;
		result = prime * result
				+ ((desLocNome == null) ? 0 : desLocNome.hashCode());
		result = prime * result
				+ ((locacaos == null) ? 0 : locacaos.hashCode());
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
		DestinacaoLocacao other = (DestinacaoLocacao) obj;
		if (desLocId != other.desLocId)
			return false;
		if (desLocNome == null) {
			if (other.desLocNome != null)
				return false;
		} else if (!desLocNome.equals(other.desLocNome))
			return false;
		if (locacaos == null) {
			if (other.locacaos != null)
				return false;
		} else if (!locacaos.equals(other.locacaos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DestinacaoLocacao [desLocId=" + desLocId + "]";
	}

}
