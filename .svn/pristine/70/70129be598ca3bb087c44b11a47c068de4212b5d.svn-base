package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.DestinacaoLocacaoDAO;
import br.com.locadora.modelo.DestinacaoLocacao;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class DestinacaoLocacaoRN {
	private DestinacaoLocacaoDAO destinacaoLocacaoDAO;
	public DestinacaoLocacaoRN() {
		this.destinacaoLocacaoDAO = DAOFactory.criarDestinacaoLocacaoDAO();
	}
	public DestinacaoLocacao carregar(Integer desLocId) {
		return this.destinacaoLocacaoDAO.carregar(desLocId);

	}

	public DestinacaoLocacao buscarPorDestinacaoLocacao(String destinacaoLocacao) {
		return this.destinacaoLocacaoDAO.buscarPorDestinacaoLocacao(destinacaoLocacao);
	}

	public void salvar(DestinacaoLocacao destinacaoLocacao) {
		Integer codigo = destinacaoLocacao.getDesLocId();
		if (codigo == null || codigo == 0) {
			this.destinacaoLocacaoDAO.salvar(destinacaoLocacao);
		} else {
			this.destinacaoLocacaoDAO.atualizar(destinacaoLocacao);
		}

	}

	public boolean excluir(DestinacaoLocacao destinacaoLocacao) {
		if(this.destinacaoLocacaoDAO.dependecias(destinacaoLocacao)){
		this.destinacaoLocacaoDAO.excluir(destinacaoLocacao);
		return true;
	}else{
		try {
			throw new UtilException("Erro ao excluir. Esse Destinacao Locacao tem ligações com outros endereços e não pode ser deletado.");
		} catch (UtilException e) {
				return false;
		}
	}
	}

	public List<DestinacaoLocacao> listar() {
		return this.destinacaoLocacaoDAO.listar();
	}
}
