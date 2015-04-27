package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Parametro;

public interface ParametroDAO {
	public void salvar(Parametro parametro);

	public void atualizar(Parametro parametro);

	public void excluir(Parametro parametro);

	public Parametro carregar(Integer parametroId);

	public Parametro buscarPorParametro(String parametro);

	public List<Parametro> listar();
	
	public boolean dependecias(Parametro parametro);
}
