package br.com.locadora.modelo;

// Generated 27/05/2014 14:17:03 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * ChaveNoQuadro generated by hbm2java
 */
@Entity
@Audited
@Table(name = "CHAVE_NO_QUADRO", catalog = "renovarsistemas")
@AuditTable(value="CHAVE_NO_QUADRO", schema="log", catalog="renovarsistemas")
public class ChaveNoQuadro implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1077541596418329458L;
	private ChaveNoQuadroId id;
	private Imovel imovel;
	private QuadroDeChaves quadroDeChaves;
	private EmprestimoChave emprestimoChave;
	private Integer posCha;
	private Date chaQuaDataEntrada;
	private Integer chaQuaQtdChave;
	private Integer chaQuaPosicao;

	public ChaveNoQuadro() {
	}

	public ChaveNoQuadro(ChaveNoQuadroId id, Imovel imovel,
			QuadroDeChaves quadroDeChaves) {
		this.id = id;
		this.imovel = imovel;
		this.quadroDeChaves = quadroDeChaves;
	}

	public ChaveNoQuadro(ChaveNoQuadroId id, Imovel imovel,
			QuadroDeChaves quadroDeChaves, EmprestimoChave emprestimoChave,
			Integer posCha, Date chaQuaDataEntrada, Integer chaQuaQtdChave,
			Integer chaQuaPosicao) {
		this.id = id;
		this.imovel = imovel;
		this.quadroDeChaves = quadroDeChaves;
		this.emprestimoChave = emprestimoChave;
		this.posCha = posCha;
		this.chaQuaDataEntrada = chaQuaDataEntrada;
		this.chaQuaQtdChave = chaQuaQtdChave;
		this.chaQuaPosicao = chaQuaPosicao;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "imoId", column = @Column(name = "IMO_ID", nullable = false)),
			@AttributeOverride(name = "quaId", column = @Column(name = "QUA_ID", nullable = false)) })
	public ChaveNoQuadroId getId() {
		return this.id;
	}

	public void setId(ChaveNoQuadroId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IMO_ID", nullable = false, insertable = false, updatable = false)
	public Imovel getImovel() {
		return this.imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUA_ID", nullable = false, insertable = false, updatable = false)
	public QuadroDeChaves getQuadroDeChaves() {
		return this.quadroDeChaves;
	}

	public void setQuadroDeChaves(QuadroDeChaves quadroDeChaves) {
		this.quadroDeChaves = quadroDeChaves;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMP_ID")
	public EmprestimoChave getEmprestimoChave() {
		return this.emprestimoChave;
	}

	public void setEmprestimoChave(EmprestimoChave emprestimoChave) {
		this.emprestimoChave = emprestimoChave;
	}

	@Column(name = "POS_CHA")
	public Integer getPosCha() {
		return this.posCha;
	}

	public void setPosCha(Integer posCha) {
		this.posCha = posCha;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHA_QUA_DATA_ENTRADA", length = 23)
	public Date getChaQuaDataEntrada() {
		return this.chaQuaDataEntrada;
	}

	public void setChaQuaDataEntrada(Date chaQuaDataEntrada) {
		this.chaQuaDataEntrada = chaQuaDataEntrada;
	}

	@Column(name = "CHA_QUA_QTD_CHAVE")
	public Integer getChaQuaQtdChave() {
		return this.chaQuaQtdChave;
	}

	public void setChaQuaQtdChave(Integer chaQuaQtdChave) {
		this.chaQuaQtdChave = chaQuaQtdChave;
	}

	@Column(name = "CHA_QUA_POSICAO")
	public Integer getChaQuaPosicao() {
		return this.chaQuaPosicao;
	}

	public void setChaQuaPosicao(Integer chaQuaPosicao) {
		this.chaQuaPosicao = chaQuaPosicao;
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
				+ ((chaQuaDataEntrada == null) ? 0 : chaQuaDataEntrada
						.hashCode());
		result = prime * result
				+ ((chaQuaPosicao == null) ? 0 : chaQuaPosicao.hashCode());
		result = prime * result
				+ ((chaQuaQtdChave == null) ? 0 : chaQuaQtdChave.hashCode());
		result = prime * result
				+ ((emprestimoChave == null) ? 0 : emprestimoChave.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imovel == null) ? 0 : imovel.hashCode());
		result = prime * result + ((posCha == null) ? 0 : posCha.hashCode());
		result = prime * result
				+ ((quadroDeChaves == null) ? 0 : quadroDeChaves.hashCode());
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
		ChaveNoQuadro other = (ChaveNoQuadro) obj;
		if (chaQuaDataEntrada == null) {
			if (other.chaQuaDataEntrada != null)
				return false;
		} else if (!chaQuaDataEntrada.equals(other.chaQuaDataEntrada))
			return false;
		if (chaQuaPosicao == null) {
			if (other.chaQuaPosicao != null)
				return false;
		} else if (!chaQuaPosicao.equals(other.chaQuaPosicao))
			return false;
		if (chaQuaQtdChave == null) {
			if (other.chaQuaQtdChave != null)
				return false;
		} else if (!chaQuaQtdChave.equals(other.chaQuaQtdChave))
			return false;
		if (emprestimoChave == null) {
			if (other.emprestimoChave != null)
				return false;
		} else if (!emprestimoChave.equals(other.emprestimoChave))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imovel == null) {
			if (other.imovel != null)
				return false;
		} else if (!imovel.equals(other.imovel))
			return false;
		if (posCha == null) {
			if (other.posCha != null)
				return false;
		} else if (!posCha.equals(other.posCha))
			return false;
		if (quadroDeChaves == null) {
			if (other.quadroDeChaves != null)
				return false;
		} else if (!quadroDeChaves.equals(other.quadroDeChaves))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChaveNoQuadro [id=" + id + "]";
	}

}
