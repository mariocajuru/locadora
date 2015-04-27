package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.DescontoLocacao;

public interface DescontoLocacaoDAO {
	public void salvar(DescontoLocacao descontoLocacao);

	public void atualizar(DescontoLocacao descontoLocacao);

	public void excluir(DescontoLocacao descontoLocacao);

	public DescontoLocacao carregar(Integer descLocId);

	public DescontoLocacao buscarPorDescontoLocacao(Double descontoLocacao);

	public List<DescontoLocacao> listar();
}
