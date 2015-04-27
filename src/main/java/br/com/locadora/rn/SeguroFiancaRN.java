package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.SeguroFiancaDAO;
import br.com.locadora.modelo.SeguroFianca;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class SeguroFiancaRN {

	private SeguroFiancaDAO seguroFiancaDAO;
	public SeguroFiancaRN() {
		this.seguroFiancaDAO = DAOFactory.criarSeguroFiancaDAO();
	}
	public SeguroFianca carregar(Integer staLoccId) {
		return this.seguroFiancaDAO.carregar(staLoccId);

	}

	public SeguroFianca buscarPorSeguroFianca(String seguroFianca) {
		return this.seguroFiancaDAO.buscarPorSeguroFianca(seguroFianca);
	}

	public void salvar(SeguroFianca statusLocacao) {
		Integer codigo = statusLocacao.getSegFiaId();
		if (codigo == null || codigo == 0) {
			this.seguroFiancaDAO.salvar(statusLocacao);
		} else {
			this.seguroFiancaDAO.atualizar(statusLocacao);
		}

	}

	public boolean excluir(SeguroFianca statusLocacao) {
		if(this.seguroFiancaDAO.dependecias(statusLocacao)){
		this.seguroFiancaDAO.excluir(statusLocacao);
		return true;
	}else{
		try {
			throw new UtilException("Erro ao excluir. Esse Seguro Fiança tem ligações com uma locação e não pode ser deletado.");
		} catch (UtilException e) {
				return false;
		}
	}
	}

	public List<SeguroFianca> listar() {
		return this.seguroFiancaDAO.listar();
	}

}
