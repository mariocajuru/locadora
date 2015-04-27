package br.com.locadora.dao;

import java.util.Date;
import java.util.List;

import br.com.locadora.modelo.IndicesReajustes;
import br.com.locadora.modelo.ValorIndiceReajuste;

public interface ValorIndiceReajusteDAO {
	public void salvar(ValorIndiceReajuste valorIndiceReajuste);

	public void atualizar(ValorIndiceReajuste valorIndiceReajuste);

	public void excluir(ValorIndiceReajuste valorIndiceReajuste);

	public ValorIndiceReajuste carregar(Integer valIndReaId);

	public ValorIndiceReajuste buscarPorValorIndiceReajuste(String valor);

	public List<ValorIndiceReajuste> listar();
	
	public List<ValorIndiceReajuste> listarPorIndicesReajuste(IndicesReajustes indicesReajustes);
	
	public ValorIndiceReajuste buscarValorIndicePorData(IndicesReajustes indicesReajustes, Date data);
	
}
