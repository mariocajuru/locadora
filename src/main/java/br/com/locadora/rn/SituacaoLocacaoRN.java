package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.SituacaoLocacaoDAO;
import br.com.locadora.modelo.PermissoesLocacao;
import br.com.locadora.modelo.SituacaoLocacao;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class SituacaoLocacaoRN {

	private SituacaoLocacaoDAO situacaoLocacaoDAO;
	public SituacaoLocacaoRN() {
		this.situacaoLocacaoDAO = DAOFactory.criarSituacaoLocacaoDAO();
	}
	public SituacaoLocacao carregar(Integer desLocId) {
		return this.situacaoLocacaoDAO.carregar(desLocId);

	}

	public SituacaoLocacao buscarPorSituacaoLocacao(String situacaoLocacao) {
		return this.situacaoLocacaoDAO.buscarPorSituacaoLocacao(situacaoLocacao);
	}

	public void salvar(SituacaoLocacao situacaoLocacao) {
		Integer codigo = situacaoLocacao.getSitLocId();
		if (codigo == null || codigo == 0) {
			this.situacaoLocacaoDAO.salvar(situacaoLocacao);
		} else {
			this.situacaoLocacaoDAO.atualizar(situacaoLocacao);
		}

	}

	public boolean excluir(SituacaoLocacao situacaoLocacao) {
		if(this.situacaoLocacaoDAO.dependecias(situacaoLocacao)){
		this.situacaoLocacaoDAO.excluir(situacaoLocacao);
		return true;
	}else{
		try {
			throw new UtilException("Erro ao excluir. Esse Situação Locacao tem ligações com outros locações e não pode ser deletado.");
		} catch (UtilException e) {
				return false;
		}
	}
	}

	public List<SituacaoLocacao> listar() {
		return this.situacaoLocacaoDAO.listar();
	}
	public boolean dependecias(SituacaoLocacao situacaoLocacao) {
		return this.situacaoLocacaoDAO.dependecias(situacaoLocacao);
	}
	
	public List<SituacaoLocacao> carregarSituacoesLocacoesPorPermissoesLocacoes(PermissoesLocacao permissoesLocacao){
		return this.situacaoLocacaoDAO.carregarSituacoesLocacoesPorPermissoesLocacoes(permissoesLocacao);
	}
}
