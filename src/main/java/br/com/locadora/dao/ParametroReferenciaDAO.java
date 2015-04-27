package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.ParametroReferencia;

public interface ParametroReferenciaDAO {
	public void salvar(ParametroReferencia parametroReferencia);

	public void atualizar(ParametroReferencia parametroReferencia);

	public void excluir(ParametroReferencia parametroReferencia);

	public ParametroReferencia carregar(Integer parametroReferenciaId);

	public ParametroReferencia buscarPorParametroReferencia(String parametroReferencia);

	public List<ParametroReferencia> listar();
	
	public boolean dependecias(ParametroReferencia parametroReferencia);
}
