package br.com.locadora.modelo;

// Generated 18/12/2014 16:03:12 by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ImovelProcurado generated by hbm2java
 */
@Entity
@Table(name = "IMOVEL_PROCURADO", catalog = "renovarsistemas")
public class ImovelProcurado implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8350298917349836386L;
	private int imoProId;
	private Pessoa pessoa;
	private String imoProSql;
	private Boolean imoProEncontrado;

	public ImovelProcurado() {
	}

	public ImovelProcurado(int imoProId, Pessoa pessoa) {
		this.imoProId = imoProId;
		this.pessoa = pessoa;
	}

	public ImovelProcurado(int imoProId, Pessoa pessoa, String imoProSql,
			Boolean imoProEncontrado) {
		this.imoProId = imoProId;
		this.pessoa = pessoa;
		this.imoProSql = imoProSql;
		this.imoProEncontrado = imoProEncontrado;
	}

	@Id
	@GeneratedValue
	@Column(name = "IMO_PRO_ID", unique = true, nullable = false)
	public int getImoProId() {
		return this.imoProId;
	}

	public void setImoProId(int imoProId) {
		this.imoProId = imoProId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PES_ID", nullable = false)
	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Column(name = "IMO_PRO_SQL", length = 1000)
	public String getImoProSql() {
		return this.imoProSql;
	}

	public void setImoProSql(String imoProSql) {
		this.imoProSql = imoProSql;
	}

	@Column(name = "IMO_PRO_ENCONTRADO")
	public Boolean getImoProEncontrado() {
		return this.imoProEncontrado;
	}

	public void setImoProEncontrado(Boolean imoProEncontrado) {
		this.imoProEncontrado = imoProEncontrado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((imoProEncontrado == null) ? 0 : imoProEncontrado.hashCode());
		result = prime * result + imoProId;
		result = prime * result
				+ ((imoProSql == null) ? 0 : imoProSql.hashCode());
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
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
		ImovelProcurado other = (ImovelProcurado) obj;
		if (imoProEncontrado == null) {
			if (other.imoProEncontrado != null)
				return false;
		} else if (!imoProEncontrado.equals(other.imoProEncontrado))
			return false;
		if (imoProId != other.imoProId)
			return false;
		if (imoProSql == null) {
			if (other.imoProSql != null)
				return false;
		} else if (!imoProSql.equals(other.imoProSql))
			return false;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImovelProcurado [imoProId=" + imoProId + "]";
	}

}
