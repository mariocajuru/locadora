package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.PessoaFiador;

public interface PessoaFiadorDAO {
	public void salvar(PessoaFiador pessoaFiador);

	public void atualizar(PessoaFiador pessoaFiador);

	public void excluir(PessoaFiador pessoaFiador);

	public PessoaFiador carregar(Integer pesFiaId);

	public PessoaFiador buscarPorPessoaFiador(String pessoaFiador);

	public List<PessoaFiador> listar();
}
