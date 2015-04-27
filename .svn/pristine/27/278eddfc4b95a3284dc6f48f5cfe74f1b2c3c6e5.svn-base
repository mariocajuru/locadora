package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Pessoa;

public interface PessoaDAO {
	public void salvar(Pessoa pessoa);

	public void atualizar(Pessoa pessoa);

	public void excluir(Pessoa pessoa);

	public Pessoa carregar(Integer pesId);

	public Pessoa buscarPorPessoa(String pessoa);

	public List<Pessoa> listar();
	
	public List<Pessoa> listaProprietarios();
	
	public List<Pessoa> carregarListaPessoasNaoPreCadastro();
	
	public List<Pessoa> carregarListaPessoasPreCadastro();
	
	public Pessoa carregarPessoaPorCPF_CNPJ(Pessoa pessoa);
	
	public Pessoa carregarPessoaPorCPF_CNPJ_Com_Caracteres(String cpf_cnpf);
}
