package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.DestinacaoLocacao;

public interface DestinacaoLocacaoDAO {
	public void salvar(DestinacaoLocacao destinacaoLocacao);

	public void atualizar(DestinacaoLocacao destinacaoLocacao);

	public void excluir(DestinacaoLocacao destinacaoLocacao);

	public DestinacaoLocacao carregar(Integer desLocId);

	public DestinacaoLocacao buscarPorDestinacaoLocacao(String destinacaoLocacao);

	public List<DestinacaoLocacao> listar();
	
	public boolean dependecias(DestinacaoLocacao destinacaoLocacao);
}
