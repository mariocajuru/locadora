package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.CentroDeCusto;
import br.com.locadora.modelo.GrupoDeContas;

public interface CentroDeCustoDAO {
	public void salvar(CentroDeCusto centroDeCusto);

	public void atualizar(CentroDeCusto centroDeCusto);

	public void excluir(CentroDeCusto centroDeCusto);

	public CentroDeCusto carregar(Integer cenCusId);

	public CentroDeCusto buscarPorCentroDeCusto(String centroDeCusto);

	public List<CentroDeCusto> listar();
	
	public boolean dependecias(CentroDeCusto centroDeCusto);
	
	public CentroDeCusto carregarPorGrupoDeContas(GrupoDeContas grupoDeContas);
}
