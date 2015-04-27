package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.FuncionarioDAO;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.util.DAOFactory;

public class FuncionarioRN {
	private FuncionarioDAO funcionarioDAO;

	public FuncionarioRN() {
		this.funcionarioDAO = DAOFactory.criarFuncionarioDAO();
	}

	public Funcionario carregar(Integer funId) {
		return this.funcionarioDAO.carregar(funId);

	}

	public Funcionario buscarPorLogin(String login) {
		return this.funcionarioDAO.buscarPorLogin(login);
	}

	public void salvar(Funcionario funcionario) {
		Integer codigo = funcionario.getPesId();
		if (codigo == null || codigo == 0) {
			this.funcionarioDAO.salvar(funcionario);
		} else {
			this.funcionarioDAO.atualizar(funcionario);
		}

	}

	public void excluir(Funcionario funcionario) {
		this.funcionarioDAO.excluir(funcionario);
	}

	public List<Funcionario> listar() {
		return this.funcionarioDAO.listar();
	}
	
	public boolean loguinIgualEntreFuncionarios(Funcionario funcionario) {
		return this.funcionarioDAO.loguinIgualEntreFuncionarios(funcionario);
	}
	
	public boolean pessoaCadastradaComoFuncionario(Pessoa pessoa){
		return this.funcionarioDAO.pessoaCadastradaComoFuncionario(pessoa);
	}
}
