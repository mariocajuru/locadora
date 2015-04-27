package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.LancamentoContasAPagarDAO;
import br.com.locadora.modelo.LancamentoContasAPagar;
import br.com.locadora.util.DAOFactory;

public class LancamentoContasAPagarRN {
	private LancamentoContasAPagarDAO lancamentoContasAPagarDAO;
	public LancamentoContasAPagarRN() {
		this.lancamentoContasAPagarDAO = DAOFactory.criarLancamentoContasAPagarDAO();
	}
	public LancamentoContasAPagar carregar(Integer lanConPagId) {
		return this.lancamentoContasAPagarDAO.carregar(lanConPagId);

	}

	public void salvar(LancamentoContasAPagar lancamentoContasAPagar) {
		Integer codigo = lancamentoContasAPagar.getLanConPagId();
		if (codigo == null || codigo == 0) {
			this.lancamentoContasAPagarDAO.salvar(lancamentoContasAPagar);
		} else {
			this.lancamentoContasAPagarDAO.atualizar(lancamentoContasAPagar);
		}

	}

	public void excluir(LancamentoContasAPagar lancamentoContasAPagar) {
		this.lancamentoContasAPagarDAO.excluir(lancamentoContasAPagar);
	}

	public List<LancamentoContasAPagar> listar() {
		return this.lancamentoContasAPagarDAO.listar();
	}

	public LancamentoContasAPagar buscarLancamentoContasAPagar(String lancamentoContasAPagar){
		return this.lancamentoContasAPagarDAO.buscarLancamentoContasAPagar(lancamentoContasAPagar);
	}
}
