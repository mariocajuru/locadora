package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.ContasAPagarDAO;
import br.com.locadora.modelo.ContasAPagar;
import br.com.locadora.util.DAOFactory;

public class ContasAPagarRN {
	private ContasAPagarDAO contasAPagarDAO;
	public ContasAPagarRN() {
		this.contasAPagarDAO = DAOFactory.criarContasAPagarDAO();
	}
	public ContasAPagar carregar(Integer conPagId) {
		return this.contasAPagarDAO.carregar(conPagId);

	}

	public void salvar(ContasAPagar contasAPagar) {
		Integer codigo = contasAPagar.getConPagId();
		if (codigo == null || codigo == 0) {
			this.contasAPagarDAO.salvar(contasAPagar);
		} else {
			this.contasAPagarDAO.atualizar(contasAPagar);
		}

	}

	public void excluir(ContasAPagar contaCorrente) {
		this.contasAPagarDAO.excluir(contaCorrente);
	}

	public List<ContasAPagar> listar() {
		return this.contasAPagarDAO.listar();
	}
}
