package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Fiador;
import br.com.locadora.modelo.Locacao;
import br.com.locadora.modelo.Pessoa;

public interface FiadorDAO {
	public void salvar(Fiador fiador);

	public void atualizar(Fiador fiador);

	public void excluir(Fiador fiador);

	public Fiador carregar(Integer filId);

	public Fiador buscarPorFiador(String fiador);

	public List<Fiador> listar();
	
	public List<Fiador> carregarFiadoresPorImovel(Locacao locacao);
	
	public Fiador carregarPorPessoa(Pessoa pessoa);
}
