package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.TipoImovelDAO;
import br.com.locadora.modelo.Tipoimovel;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class TipoImovelRN {
private TipoImovelDAO tipoImovelDAO;
	public TipoImovelRN() {
		this.tipoImovelDAO=DAOFactory.criarTipoImovelDAO();
	}

	public Tipoimovel carregar(Integer tipId) {
		return this.tipoImovelDAO.carregar(tipId);

	}

	public Tipoimovel buscarPorTelefone(String tipo) {
		return this.tipoImovelDAO.buscarPorTipoImovel(tipo);
	}

	public void salvar(Tipoimovel tipo) {
		Integer codigo = tipo.getTipId();
		if (codigo == null || codigo == 0) {
			this.tipoImovelDAO.salvar(tipo);
		} else {
			this.tipoImovelDAO.atualizar(tipo);
		}

	}

	public boolean excluir(Tipoimovel tipo) {
		if(this.tipoImovelDAO.dependecias(tipo)){
			this.tipoImovelDAO.excluir(tipo);
			return true;
		}else{
			try {
				throw new UtilException("Erro ao excluir. Esse tipo tem ligações com outros elementos e não pode ser deletado.");
			} catch (UtilException e) {				
				return false;
			}
		}
	}

	public List<Tipoimovel> listar() {
		return this.tipoImovelDAO.listar();
	}
}
