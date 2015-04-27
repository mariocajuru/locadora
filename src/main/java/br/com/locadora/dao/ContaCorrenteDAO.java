package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.ContaCorrente;

public interface ContaCorrenteDAO {
	public void salvar(ContaCorrente contaCorrente);

	public void atualizar(ContaCorrente contaCorrente);

	public void excluir(ContaCorrente contaCorrente);

	public ContaCorrente carregar(Integer conCorId);

	public ContaCorrente buscarContaCorrente(String contaCorrente);

	public List<ContaCorrente> listar();
}
