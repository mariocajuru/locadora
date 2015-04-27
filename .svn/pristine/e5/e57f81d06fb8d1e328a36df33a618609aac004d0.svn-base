package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Fiador;
import br.com.locadora.modelo.Locacao;

public interface LocacaoDAO {
	public void salvar(Locacao locacao);

	public void atualizar(Locacao locacao);

	public void excluir(Locacao locacao);

	public Locacao carregar(Integer locId);

	public Locacao buscarPorLocacao(String locacao);

	public List<Locacao> listar();
	
	public List<Fiador> carregarFiadores(Locacao locacao);
	
	public List<Locacao> contratosVencimentroMes(Integer mes);
}
