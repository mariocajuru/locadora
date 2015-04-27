package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.SeguroFianca;

public interface SeguroFiancaDAO {
	public void salvar(SeguroFianca seguroFianca);

	public void atualizar(SeguroFianca seguroFianca);

	public void excluir(SeguroFianca seguroFianca);

	public SeguroFianca carregar(Integer segFiaId);

	public SeguroFianca buscarPorSeguroFianca(String seguroFianca);

	public List<SeguroFianca> listar();
	
	public boolean dependecias(SeguroFianca seguroFianca);
}
