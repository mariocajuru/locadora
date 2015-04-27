package br.com.locadora.rn;

import java.util.Date;
import java.util.List;

import br.com.locadora.dao.ValorIndiceReajusteDAO;
import br.com.locadora.modelo.IndicesReajustes;
import br.com.locadora.modelo.ValorIndiceReajuste;
import br.com.locadora.util.DAOFactory;

public class ValorIndiceReajusteRN {
	private ValorIndiceReajusteDAO valorIndiceReajusteDAO;
	public ValorIndiceReajusteRN() {
		this.valorIndiceReajusteDAO = DAOFactory.criarValorIndiceReajusteDAO();
	}
	public ValorIndiceReajuste carregar(Integer valIndReaId) {
		return this.valorIndiceReajusteDAO.carregar(valIndReaId);

	}

	public ValorIndiceReajuste buscarPorValorIndiceReajuste(String valor) {
		return this.valorIndiceReajusteDAO.buscarPorValorIndiceReajuste(valor);
	}

	public void salvar(ValorIndiceReajuste valorIndiceReajuste) {
		Integer codigo = valorIndiceReajuste.getValIndReaId();
		if (codigo == null || codigo == 0) {
			this.valorIndiceReajusteDAO.salvar(valorIndiceReajuste);
		} else {
			this.valorIndiceReajusteDAO.atualizar(valorIndiceReajuste);
		}

	}

	public void excluir(ValorIndiceReajuste valorIndiceReajuste) {
		this.valorIndiceReajusteDAO.excluir(valorIndiceReajuste);
		
	}

	public List<ValorIndiceReajuste> listar() {
		return this.valorIndiceReajusteDAO.listar();
	}
	
	public List<ValorIndiceReajuste> listarPorIndicesReajuste(
			IndicesReajustes indicesReajustes) {
		return this.valorIndiceReajusteDAO.listarPorIndicesReajuste(indicesReajustes);
	}
	
	public ValorIndiceReajuste buscarValorIndicePorData(IndicesReajustes indicesReajustes, Date data){
		return this.valorIndiceReajusteDAO.buscarValorIndicePorData(indicesReajustes, data);
	}
}
