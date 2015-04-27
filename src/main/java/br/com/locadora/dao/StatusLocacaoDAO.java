package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.StatusLocacao;

public interface StatusLocacaoDAO {
	public void salvar(StatusLocacao statusLocacao);

	public void atualizar(StatusLocacao statusLocacao);

	public void excluir(StatusLocacao statusLocacao);

	public StatusLocacao carregar(Integer staLoccId);

	public StatusLocacao buscarPorStatusLocacao(String statusLocacao);

	public List<StatusLocacao> listar();
	
	public boolean dependecias(StatusLocacao statusLocacao);
}
