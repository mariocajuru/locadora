package br.com.locadora.rn;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.locadora.dao.BancoDAO;
import br.com.locadora.modelo.Banco;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class BancoRN {
	private BancoDAO bancoDAO;

	public BancoRN() {
		this.bancoDAO = DAOFactory.criarBancoDAO();
	}
	public Banco carregar(Integer banId) {
		return this.bancoDAO.carregar(banId);

	}

	public Banco buscarPorBanco(String bancoNome) {
		return this.bancoDAO.buscarPorBanco(bancoNome);
	}

	public void salvar(Banco banco) {
		Integer codigo = banco.getBanId();
		if (codigo == null || codigo == 0) {
			this.bancoDAO.salvar(banco);
		} else {
			this.bancoDAO.atualizar(banco);
		}

	}

	public boolean excluir(Banco banco) {
		if(this.bancoDAO.dependecias(banco)){
			this.bancoDAO.excluir(banco);
			return true;
		}else{
			try {
				throw new UtilException("Erro ao excluir. Esse Banco tem ligações com outros endereços e não pode ser deletado.");
			} catch (UtilException e) {
				FacesContext facesContext = FacesContext.getCurrentInstance();  
				facesContext.addMessage("ERRO",   
						new FacesMessage(FacesMessage.SEVERITY_ERROR,   
								"Banco não excluido",   
								"Banco não excluido"));
				return false;
			}
		}
	}

	public List<Banco> listar() {
		return this.bancoDAO.listar();
	}
}
