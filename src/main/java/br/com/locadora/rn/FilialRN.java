package br.com.locadora.rn;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.locadora.dao.FilialDAO;
import br.com.locadora.modelo.Filial;
import br.com.locadora.modelo.QuadroDeChaves;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class FilialRN {
	private FilialDAO filialDAO;

	public FilialRN() {
		this.filialDAO = DAOFactory.criarSedeDAO();
	}

	public Filial carregar(Integer sedId) {
		return this.filialDAO.carregar(sedId);

	}

	public Filial buscarPorSede(String sede) {
		return this.filialDAO.buscarPorSede(sede);
	}

	public void salvar(Filial sede) {
		Integer codigo = sede.getFilId();
		if (codigo == null || codigo == 0) {
			this.filialDAO.salvar(sede);
		} else {
			this.filialDAO.atualizar(sede);
		}

	}

	public boolean excluir(Filial sede) {
		if(this.filialDAO.dependecias(sede)){
			QuadroDeChavesRN quadroDeChavesRN= new QuadroDeChavesRN();
			QuadroDeChaves quadroDeChaves=quadroDeChavesRN.buscarPorFilial(sede.getFilId());
			if(quadroDeChaves!=null){
				quadroDeChavesRN.excluir(quadroDeChaves);
			}
			this.filialDAO.excluir(sede);
		return true;
	}else{
		try {
			throw new UtilException("Erro ao excluir. Essa filial tem ligações com outros registros e não pode ser deletado.");
		} catch (UtilException e) {
			FacesContext facesContext = FacesContext.getCurrentInstance();  
			facesContext.addMessage("ERRO",   
					new FacesMessage(FacesMessage.SEVERITY_ERROR,   
							"Filial não excluido",   
							"Filial não excluido"));
			return false;
		}
	}
	}

	public List<Filial> listar() {
		return this.filialDAO.listar();
	}
}
