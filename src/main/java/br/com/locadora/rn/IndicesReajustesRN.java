package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.IndicesReajustesDAO;
import br.com.locadora.modelo.IndicesReajustes;
import br.com.locadora.modelo.ValorIndiceReajuste;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class IndicesReajustesRN {

	private IndicesReajustesDAO indicesReajustesDAO;
	public IndicesReajustesRN() {
		this.indicesReajustesDAO = DAOFactory.criarIndicesReajustesDAO();
	}
	public IndicesReajustes carregar(Integer indReacId) {
		return this.indicesReajustesDAO.carregar(indReacId);

	}

	public IndicesReajustes buscarPorIndicesReajustes(String indicesReajustes) {
		return this.indicesReajustesDAO.buscarPorIndicesReajustes(indicesReajustes);
	}

	public void salvar(IndicesReajustes indicesReajustes) {
		Integer codigo = indicesReajustes.getIndReaId();
		if (codigo == null || codigo == 0) {
			this.indicesReajustesDAO.salvar(indicesReajustes);
		} else {
			this.indicesReajustesDAO.atualizar(indicesReajustes);
		}

	}

	public boolean excluir(IndicesReajustes indicesReajustes) {
		if(this.indicesReajustesDAO.dependecias(indicesReajustes)){
			ValorIndiceReajusteRN valorIndiceReajusteRN=new ValorIndiceReajusteRN();
			List<ValorIndiceReajuste> listaVal=valorIndiceReajusteRN.listarPorIndicesReajuste(indicesReajustes);
			for(ValorIndiceReajuste val: listaVal){
				valorIndiceReajusteRN.excluir(val);
			}
			
		this.indicesReajustesDAO.excluir(indicesReajustes);
		return true;
	}else{
		try {
			throw new UtilException("Erro ao excluir. Esse IndicesReajustes tem ligações com outros locação e não pode ser deletado.");
		} catch (UtilException e) {
				return false;
		}
	}
	}

	public List<IndicesReajustes> listar() {
		return this.indicesReajustesDAO.listar();
	}

}
