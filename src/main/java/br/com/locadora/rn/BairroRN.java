package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.BairroDAO;
import br.com.locadora.modelo.Bairro;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class BairroRN {
	private BairroDAO bairroDAO;

	public BairroRN() {
		this.bairroDAO = DAOFactory.criarBairroDAO();
	}

	public Bairro carregar(Integer baiId) {
		return this.bairroDAO.carregar(baiId);

	}

	public Bairro buscarPorBairro(String bairro) {
		return this.bairroDAO.buscarPorBairro(bairro);
	}

	public void salvar(Bairro bairro) {
		Integer codigo = bairro.getBaiId();
		if (codigo == null || codigo == 0) {
			this.bairroDAO.salvar(bairro);
		} else {
			this.bairroDAO.atualizar(bairro);
		}

	}

	public boolean excluir(Bairro bairro) {
		if(this.bairroDAO.dependecias(bairro)){
		this.bairroDAO.excluir(bairro);
		return true;
	}else{
		try {
			throw new UtilException("Erro ao excluir. Esse bairro tem liga��es com outros endere�os e n�o pode ser deletado.");
		} catch (UtilException e) {
			/*FacesContext facesContext = FacesContext.getCurrentInstance();  
			facesContext.addMessage("ERRO",   
			        new FacesMessage(FacesMessage.SEVERITY_ERROR,   
			                               "Bairro n�o excluido",   
			                               "Bairro n�o excluido"));*/
			return false;
		}
	}
	}

	public List<Bairro> listar() {
		return this.bairroDAO.listar();
	}
}
