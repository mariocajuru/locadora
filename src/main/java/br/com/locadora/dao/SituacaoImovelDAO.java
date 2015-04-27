package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Situacaoimovel;

public interface SituacaoImovelDAO {
	public void salvar(Situacaoimovel situacaoImovel);

	public void atualizar(Situacaoimovel situacaoImovel);

	public void excluir(Situacaoimovel situacaoImovel);

	public Situacaoimovel carregar(Integer sitId);

	public Situacaoimovel buscarPorSituacao(String situacaoImovel);

	public List<Situacaoimovel> listar();
	
	public boolean dependecias(Situacaoimovel situacaoimovel);
}
