package br.com.locadora.dao;



import java.util.List;

import br.com.locadora.modelo.Cidade;

public interface CidadeDAO {
	public void salvar(Cidade cidade);

	public void atualizar(Cidade cidade);

	public void excluir(Cidade cidade);

	public Cidade carregar(Integer codigo);

	public Cidade buscarPorCidade(String cidade);

	public List<Cidade> listar();
}
