package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.SituacaoImovelDAO;
import br.com.locadora.modelo.Situacaoimovel;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class SituacaoImovelRN {
	private SituacaoImovelDAO situacaoImovelDAO;

	public SituacaoImovelRN() {
		this.situacaoImovelDAO= DAOFactory.criarSituacaoImovelDAO();
	}
	
	public Situacaoimovel carregar(Integer sitId) {
		return this.situacaoImovelDAO.carregar(sitId);

	}

	public Situacaoimovel buscarPorSede(String situacaoimovel) {
		return this.situacaoImovelDAO.buscarPorSituacao(situacaoimovel);
	}

	public void salvar(Situacaoimovel situacaoimovel) {
		Integer codigo = situacaoimovel.getSitId();
		if (codigo == null || codigo == 0) {
			this.situacaoImovelDAO.salvar(situacaoimovel);
		} else {
			this.situacaoImovelDAO.atualizar(situacaoimovel);
		}

	}

	public boolean excluir(Situacaoimovel situacaoimovel) {		
		if(this.situacaoImovelDAO.dependecias(situacaoimovel)){
			this.situacaoImovelDAO.excluir(situacaoimovel);
			return true;
		}else{
			try {
				throw new UtilException("Erro ao excluir. Essa situação tem ligações com outros elementos e não pode ser deletado.");
			} catch (UtilException e) {				
				return false;
			}
		}
	}

	public List<Situacaoimovel> listar() {
		return this.situacaoImovelDAO.listar();
	}
}
