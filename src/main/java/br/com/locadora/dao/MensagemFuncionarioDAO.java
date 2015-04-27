package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.MensagemFuncionario;

public interface MensagemFuncionarioDAO {
	public void salvar(MensagemFuncionario mensagemFuncionario);

	public void atualizar(MensagemFuncionario mensagemFuncionario);

	public void excluir(MensagemFuncionario mensagemFuncionario);

	public MensagemFuncionario carregar(Integer menFunId);

	public List<MensagemFuncionario> buscarPorFuncionarioVisualizada(Funcionario funcionario);

	public List<MensagemFuncionario> listar();
}
