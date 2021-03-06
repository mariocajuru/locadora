package br.com.locadora.modelo;

// Generated 30/04/2014 13:20:46 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PessoaFiadorId generated by hbm2java
 */
@Embeddable
public class PessoaFiadorId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9208028838151102767L;
	private Integer fiaId;
	private Integer pesId;

	public PessoaFiadorId() {
	}

	public PessoaFiadorId(Integer fiaId, Integer pesId) {
		this.fiaId = fiaId;
		this.pesId = pesId;
	}

	@Column(name = "FIA_ID")
	public Integer getFiaId() {
		return this.fiaId;
	}

	public void setFiaId(Integer fiaId) {
		this.fiaId = fiaId;
	}

	@Column(name = "PES_ID")
	public Integer getPesId() {
		return this.pesId;
	}

	public void setPesId(Integer pesId) {
		this.pesId = pesId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PessoaFiadorId))
			return false;
		PessoaFiadorId castOther = (PessoaFiadorId) other;

		return ((this.getFiaId() == castOther.getFiaId()) || (this.getFiaId() != null
				&& castOther.getFiaId() != null && this.getFiaId().equals(
				castOther.getFiaId())))
				&& ((this.getPesId() == castOther.getPesId()) || (this
						.getPesId() != null && castOther.getPesId() != null && this
						.getPesId().equals(castOther.getPesId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFiaId() == null ? 0 : this.getFiaId().hashCode());
		result = 37 * result
				+ (getPesId() == null ? 0 : this.getPesId().hashCode());
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "PessoaFiadorId [fiaId=" + fiaId + ", pesId=" + pesId + "]";
	}

}
