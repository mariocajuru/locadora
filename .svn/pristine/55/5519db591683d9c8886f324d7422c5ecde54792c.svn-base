package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Email;
import br.com.locadora.modelo.Filial;
import br.com.locadora.modelo.Pessoa;

public interface EmailDAO {
	public void salvar(Email email);

	public void atualizar(Email email);

	public void excluir(Email email);

	public Email carregar(Integer emaId);

	public Email buscarPorEmail(String email);

	public List<Email> listar();
	
	public List<Email> carregarPessoa(Pessoa pessoa);
	
	public List<Email> carregarFilial(Filial filial);
}
