package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.PermissoesLocacao;
import br.com.locadora.modelo.SituacaoLocacao;

public interface SituacaoLocacaoDAO {
	public void salvar(SituacaoLocacao situacaoLocacao);

	public void atualizar(SituacaoLocacao situacaoLocacao);

	public void excluir(SituacaoLocacao situacaoLocacao);

	public SituacaoLocacao carregar(Integer sitLocId);

	public SituacaoLocacao buscarPorSituacaoLocacao(String situacaoLocacao);

	public List<SituacaoLocacao> listar();
	
	public boolean dependecias(SituacaoLocacao situacaoLocacao);
	
	public List<SituacaoLocacao> carregarSituacoesLocacoesPorPermissoesLocacoes(PermissoesLocacao permissoesLocacao);
}
