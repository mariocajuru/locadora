package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.PermissoesLocacao;
import br.com.locadora.modelo.SituacaoLocacao;

public interface PermissoesLocacaoDAO {
	public void salvar(PermissoesLocacao permissoesLocacao);

	public void atualizar(PermissoesLocacao permissoesLocacao);

	public void excluir(PermissoesLocacao permissoesLocacao);

	public PermissoesLocacao carregar(Integer perLocId);

	public PermissoesLocacao buscarPorPermissoesLocacao(String permissoesLocacao);

	public List<PermissoesLocacao> listar();
	
	public boolean dependecias(PermissoesLocacao permissoesLocacao);
	
	public List<PermissoesLocacao> carregarPorSituacaoLocacao(SituacaoLocacao situacaoLocacao);
}
