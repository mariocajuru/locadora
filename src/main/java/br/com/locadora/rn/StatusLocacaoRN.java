package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.StatusLocacaoDAO;
import br.com.locadora.modelo.StatusLocacao;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class StatusLocacaoRN {

	private StatusLocacaoDAO statusLocacaoDAO;
	public StatusLocacaoRN() {
		this.statusLocacaoDAO = DAOFactory.criarStatusLocacaoDAO();
	}
	public StatusLocacao carregar(Integer staLoccId) {
		return this.statusLocacaoDAO.carregar(staLoccId);

	}

	public StatusLocacao buscarPorDestinacaoLocacao(String statusLocacao) {
		return this.statusLocacaoDAO.buscarPorStatusLocacao(statusLocacao);
	}

	public void salvar(StatusLocacao statusLocacao) {
		Integer codigo = statusLocacao.getStaLocId();
		if (codigo == null || codigo == 0) {
			this.statusLocacaoDAO.salvar(statusLocacao);
		} else {
			this.statusLocacaoDAO.atualizar(statusLocacao);
		}

	}

	public boolean excluir(StatusLocacao statusLocacao) {
		if(this.statusLocacaoDAO.dependecias(statusLocacao)){
		this.statusLocacaoDAO.excluir(statusLocacao);
		return true;
	}else{
		try {
			throw new UtilException("Erro ao excluir. Esse Status Locacao tem ligações com outros locação e não pode ser deletado.");
		} catch (UtilException e) {
				return false;
		}
	}
	}

	public List<StatusLocacao> listar() {
		return this.statusLocacaoDAO.listar();
	}

}
