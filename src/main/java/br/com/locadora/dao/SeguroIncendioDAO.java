package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.SeguroIncendio;

public interface SeguroIncendioDAO {
	public void salvar(SeguroIncendio seguroIncendio);

	public void atualizar(SeguroIncendio seguroIncendio);

	public void excluir(SeguroIncendio seguroIncendio);

	public SeguroIncendio carregar(Integer segIncId);

	public SeguroIncendio buscarPorSeguroIncendio(String seguroIncendio);

	public List<SeguroIncendio> listar();
	
	public boolean dependecias(SeguroIncendio seguroIncendio);
}
