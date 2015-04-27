package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.TemPertoDAO;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.TemPerto;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class TemPertoRN {
	private TemPertoDAO temPertoDAO;

	public TemPertoRN() {
		this.temPertoDAO = DAOFactory.criarTemPertoDAO();
	}

	public TemPerto carregar(Integer temPerId) {
		return this.temPertoDAO.carregar(temPerId);

	}

	public TemPerto buscarPorProposta(String temPerto) {
		return this.temPertoDAO.buscarPorTemPerto(temPerto);
	}

	public void salvar(TemPerto temPerto) {
		Integer codigo = temPerto.getTemPerId();
		if (codigo == null || codigo == 0) {
			this.temPertoDAO.salvar(temPerto);
		} else {
			this.temPertoDAO.atualizar(temPerto);
		}

	}

	public boolean excluir(TemPerto temPerto) {
		if(this.temPertoDAO.dependecias(temPerto)){
			this.temPertoDAO.excluir(temPerto);
			return true;
		}else{
			try {
				throw new UtilException("Erro ao excluir. Essa item tem ligações com outros elementos e não pode ser deletado.");
			} catch (UtilException e) {				
				return false;
			}
		}
	}

	public List<TemPerto> listar() {
		return this.temPertoDAO.listar();
	}
	public List<TemPerto> carregarListaPorImovel(Imovel imovel){
		return this.temPertoDAO.carregarListaPorImovel(imovel);
	}
}
