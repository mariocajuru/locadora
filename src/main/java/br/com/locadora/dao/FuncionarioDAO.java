package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.Pessoa;

public interface FuncionarioDAO {
	public void salvar(Funcionario funcionario);

	public void atualizar(Funcionario funcionario);

	public void excluir(Funcionario funcionario);

	public Funcionario carregar(Integer funId);

	public Funcionario buscarPorLogin(String login);

	public List<Funcionario> listar();
	
	public boolean loguinIgualEntreFuncionarios(Funcionario funcionario);
	
	public boolean pessoaCadastradaComoFuncionario(Pessoa pessoa);
}
