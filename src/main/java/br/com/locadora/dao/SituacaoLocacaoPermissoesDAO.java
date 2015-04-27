package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.PermissoesLocacao;
import br.com.locadora.modelo.SituacaoLocacao;
import br.com.locadora.modelo.SituacaoLocacaoPermissoes;

public interface SituacaoLocacaoPermissoesDAO {
	public void salvar(SituacaoLocacaoPermissoes situacaoLocacaoPermissoes);

	public void atualizar(SituacaoLocacaoPermissoes situacaoLocacaoPermissoes);

	public void excluir(SituacaoLocacaoPermissoes situacaoLocacaoPermissoes);
	
	public void excluirTudo();

	public SituacaoLocacaoPermissoes carregar(Integer codigo);

	public List<SituacaoLocacaoPermissoes> listar();
	
	public List<PermissoesLocacao> listarPermissoesLocacaoPorSituacao(SituacaoLocacao situacaoLocacao);
	
	
}
