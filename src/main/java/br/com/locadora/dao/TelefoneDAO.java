package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Telefone;;

public interface TelefoneDAO {
	public void salvar(Telefone telefone);

	public void atualizar(Telefone telefone);

	public void excluir(Telefone telefone);

	public Telefone carregar(Integer telId);

	public Telefone buscarPorTelefone(String telefone);

	public List<Telefone> listar();
	
	public List<Telefone> carregarPessoa(Pessoa pessoa);
}
