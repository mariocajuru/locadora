package br.com.locadora.modelo;

// Generated 16/12/2014 10:31:31 by Hibernate Tools 4.3.1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * MensagemFuncionario generated by hbm2java
 */
@Audited
@Entity
@Table(name = "MENSAGEM_FUNCIONARIO", catalog = "renovarsistemas")
@AuditTable(value="MENSAGEM_FUNCIONARIO", schema="log", catalog="renovarsistemas")
@NamedQuery(name="consultaMensagemFuncionario", query="SELECT m FROM MensagemFuncionario m where m.funcionario.pesId = :codFun and menFunVisualizada = :visualizada")
public class MensagemFuncionario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6947074757782644596L;
	private int menFunId;
	private Funcionario funcionario;
	private String menFunMensagem;
	private Date menFunDataCriacao;
	private Date menFunDataLeitura;
	private String menFunRemetente;
	private Boolean menFunVisualizada;

	public MensagemFuncionario() {
	}

	public MensagemFuncionario(int menFunId, Funcionario funcionario) {
		this.menFunId = menFunId;
		this.funcionario = funcionario;
	}

	public MensagemFuncionario(int menFunId, Funcionario funcionario,
			String menFunMensagem, Date menFunDataCriacao,
			Date menFunDataLeitura, String menFunRemetente, Boolean menFunVisualizada) {
		this.menFunId = menFunId;
		this.funcionario = funcionario;
		this.menFunMensagem = menFunMensagem;
		this.menFunDataCriacao = menFunDataCriacao;
		this.menFunDataLeitura = menFunDataLeitura;
		this.menFunRemetente = menFunRemetente;
		this.menFunVisualizada=menFunVisualizada;
	}

	@Id
	@GeneratedValue
	@Column(name = "MEN_FUN_ID", unique = true, nullable = false)
	public int getMenFunId() {
		return this.menFunId;
	}

	public void setMenFunId(int menFunId) {
		this.menFunId = menFunId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PES_ID", nullable = false)
	public Funcionario getFuncionario() {
		return this.funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Column(name = "MEN_FUN_MENSAGEM", length = 151)
	public String getMenFunMensagem() {
		return this.menFunMensagem;
	}

	public void setMenFunMensagem(String menFunMensagem) {
		this.menFunMensagem = menFunMensagem;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MEN_FUN_DATA_CRIACAO", length = 23)
	public Date getMenFunDataCriacao() {
		return this.menFunDataCriacao;
	}

	public void setMenFunDataCriacao(Date menFunDataCriacao) {
		this.menFunDataCriacao = menFunDataCriacao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MEN_FUN_DATA_LEITURA", length = 23)
	public Date getMenFunDataLeitura() {
		return this.menFunDataLeitura;
	}

	public void setMenFunDataLeitura(Date menFunDataLeitura) {
		this.menFunDataLeitura = menFunDataLeitura;
	}

	@Column(name = "MEN_FUN_REMETENTE", length = 90)
	public String getMenFunRemetente() {
		return this.menFunRemetente;
	}

	public void setMenFunRemetente(String menFunRemetente) {
		this.menFunRemetente = menFunRemetente;
	}
	
	
	@Column(name = "MEN_FUN_VISUALIZADA")
	public Boolean getMenFunVisualizada() {
		return menFunVisualizada;
	}

	public void setMenFunVisualizada(Boolean menFunVisualizada) {
		this.menFunVisualizada = menFunVisualizada;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime
				* result
				+ ((menFunDataCriacao == null) ? 0 : menFunDataCriacao
						.hashCode());
		result = prime
				* result
				+ ((menFunDataLeitura == null) ? 0 : menFunDataLeitura
						.hashCode());
		result = prime * result + menFunId;
		result = prime * result
				+ ((menFunMensagem == null) ? 0 : menFunMensagem.hashCode());
		result = prime * result
				+ ((menFunRemetente == null) ? 0 : menFunRemetente.hashCode());
		result = prime
				* result
				+ ((menFunVisualizada == null) ? 0 : menFunVisualizada
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
		MensagemFuncionario other = (MensagemFuncionario) obj;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (menFunDataCriacao == null) {
			if (other.menFunDataCriacao != null)
				return false;
		} else if (!menFunDataCriacao.equals(other.menFunDataCriacao))
			return false;
		if (menFunDataLeitura == null) {
			if (other.menFunDataLeitura != null)
				return false;
		} else if (!menFunDataLeitura.equals(other.menFunDataLeitura))
			return false;
		if (menFunId != other.menFunId)
			return false;
		if (menFunMensagem == null) {
			if (other.menFunMensagem != null)
				return false;
		} else if (!menFunMensagem.equals(other.menFunMensagem))
			return false;
		if (menFunRemetente == null) {
			if (other.menFunRemetente != null)
				return false;
		} else if (!menFunRemetente.equals(other.menFunRemetente))
			return false;
		if (menFunVisualizada == null) {
			if (other.menFunVisualizada != null)
				return false;
		} else if (!menFunVisualizada.equals(other.menFunVisualizada))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MensagemFuncionario [menFunId=" + menFunId + "]";
	}

}