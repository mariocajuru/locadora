package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.PermissoesLocacaoDAO;
import br.com.locadora.modelo.PermissoesLocacao;
import br.com.locadora.modelo.SituacaoLocacao;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class PermissoesLocacaoRN {

	private PermissoesLocacaoDAO permissoesLocacaoDAO;
	public PermissoesLocacaoRN() {
		this.permissoesLocacaoDAO = DAOFactory.criarPermissoesLocacaoDAO();
	}
	public PermissoesLocacao carregar(Integer perLocId) {
		return this.permissoesLocacaoDAO.carregar(perLocId);

	}

	public PermissoesLocacao buscarPorPermissoesLocacao(String permissoesLocacao) {
		return this.permissoesLocacaoDAO.buscarPorPermissoesLocacao(permissoesLocacao);
	}

	public void salvar(PermissoesLocacao permissoesLocacao) {
		Integer codigo = permissoesLocacao.getPerLocId();
		if (codigo == null || codigo == 0) {
			this.permissoesLocacaoDAO.salvar(permissoesLocacao);
		} else {
			this.permissoesLocacaoDAO.atualizar(permissoesLocacao);
		}

	}

	public boolean excluir(PermissoesLocacao permissoesLocacao) {
		if(this.permissoesLocacaoDAO.dependecias(permissoesLocacao)){
		this.permissoesLocacaoDAO.excluir(permissoesLocacao);
		return true;
	}else{
		try {
			throw new UtilException("Erro ao excluir. Essa Permissões Locacao tem ligações com outros locações e não pode ser deletado.");
		} catch (UtilException e) {
				return false;
		}
	}
	}

	public List<PermissoesLocacao> listar() {
		return this.permissoesLocacaoDAO.listar();
	}
	public List<PermissoesLocacao> carregarPorSituacaoLocacao(SituacaoLocacao situacaoLocacao){
		return this.permissoesLocacaoDAO.carregarPorSituacaoLocacao(situacaoLocacao);
	}

}
