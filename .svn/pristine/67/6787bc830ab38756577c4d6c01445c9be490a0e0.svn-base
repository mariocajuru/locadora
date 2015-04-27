package br.com.locadora.modelo.log;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
@Entity
@RevisionEntity(Gravar.class)
@Table(name="AUDITORIA", schema="log", catalog="renovarsistemas")
public class Auditoria extends DefaultRevisionEntity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7197655600139767254L;

	/**
	 * 
	 */
	
	private String loguin;
	
	private String nome;
	/*@Column(length=900)
	private String nivelAcesso;*/
	private String cpf;
	private String ip_maquina;
	/*private String pagina;*/
	private String nome_maquina;
	
	private Integer ip_funcionario;
	
	public String getLoguin() {
		return loguin;
	}
	public void setLoguin(String loguin) {
		this.loguin = loguin;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getIp_maquina() {
		return ip_maquina;
	}
	public void setIp_maquina(String ip_maquina) {
		this.ip_maquina = ip_maquina;
	}
		
	
	public Integer getIp_funcionario() {
		return ip_funcionario;
	}
	public void setIp_funcionario(Integer ip_funcionario) {
		this.ip_funcionario = ip_funcionario;
	}
	
	public String getNome_maquina() {
		return nome_maquina;
	}
	public void setNome_maquina(String nome_maquina) {
		this.nome_maquina = nome_maquina;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result
				+ ((ip_funcionario == null) ? 0 : ip_funcionario.hashCode());
		result = prime * result
				+ ((ip_maquina == null) ? 0 : ip_maquina.hashCode());
		result = prime * result + ((loguin == null) ? 0 : loguin.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((nome_maquina == null) ? 0 : nome_maquina.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Auditoria other = (Auditoria) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (ip_funcionario == null) {
			if (other.ip_funcionario != null)
				return false;
		} else if (!ip_funcionario.equals(other.ip_funcionario))
			return false;
		if (ip_maquina == null) {
			if (other.ip_maquina != null)
				return false;
		} else if (!ip_maquina.equals(other.ip_maquina))
			return false;
		if (loguin == null) {
			if (other.loguin != null)
				return false;
		} else if (!loguin.equals(other.loguin))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (nome_maquina == null) {
			if (other.nome_maquina != null)
				return false;
		} else if (!nome_maquina.equals(other.nome_maquina))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Auditoria [getId()=" + getId() + "]";
	}
	
}
