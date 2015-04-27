package br.com.locadora.modelo;

// Generated 11/04/2014 14:12:50 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * Telefone generated by hbm2java
 */
@Audited
@Entity
@Table(name = "TELEFONE", catalog = "renovarsistemas")
@AuditTable(value="TELEFONE", schema="log", catalog="renovarsistemas")
public class Telefone implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1113858767150959275L;
	private int telId;
	private Pessoa pessoa;
	private Filial filial;
	private String telNumero;
	private String telTipo;
	private Character telSms;

	public Telefone() {
	}

	public Telefone(int telId, Pessoa pessoa, String telNumero) {
		this.telId = telId;
		this.pessoa = pessoa;
		this.telNumero = telNumero;
	}

	public Telefone(int telId, Pessoa pessoa, Filial filial, String telNumero,
			String telTipo, Character telSms) {
		this.telId = telId;
		this.pessoa = pessoa;
		this.filial = filial;
		this.telNumero = telNumero;
		this.telTipo = telTipo;
		this.telSms = telSms;
	}

	@Id
	@GeneratedValue
	@Column(name = "TEL_ID", unique = true, nullable = false)
	public int getTelId() {
		return this.telId;
	}

	public void setTelId(int telId) {
		this.telId = telId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PES_ID", nullable = false)
	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIL_ID")
	public Filial getFilial() {
		return this.filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	@Column(name = "TEL_NUMERO", nullable = false, length = 16)
	public String getTelNumero() {
		return this.telNumero;
	}

	public void setTelNumero(String telNumero) {
		this.telNumero = telNumero;
	}

	@Column(name = "TEL_TIPO", length = 20)
	public String getTelTipo() {
		return this.telTipo;
	}

	public void setTelTipo(String telTipo) {
		this.telTipo = telTipo;
	}

	@Column(name = "TEL_SMS", length = 1)
	public Character getTelSms() {
		return this.telSms;
	}

	public void setTelSms(Character telSms) {
		this.telSms = telSms;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filial == null) ? 0 : filial.hashCode());
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
		result = prime * result + telId;
		result = prime * result
				+ ((telNumero == null) ? 0 : telNumero.hashCode());
		result = prime * result + ((telSms == null) ? 0 : telSms.hashCode());
		result = prime * result + ((telTipo == null) ? 0 : telTipo.hashCode());
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
		Telefone other = (Telefone) obj;
		if (filial == null) {
			if (other.filial != null)
				return false;
		} else if (!filial.equals(other.filial))
			return false;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		if (telId != other.telId)
			return false;
		if (telNumero == null) {
			if (other.telNumero != null)
				return false;
		} else if (!telNumero.equals(other.telNumero))
			return false;
		if (telSms == null) {
			if (other.telSms != null)
				return false;
		} else if (!telSms.equals(other.telSms))
			return false;
		if (telTipo == null) {
			if (other.telTipo != null)
				return false;
		} else if (!telTipo.equals(other.telTipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Telefone [telId=" + telId + "]";
	}

}