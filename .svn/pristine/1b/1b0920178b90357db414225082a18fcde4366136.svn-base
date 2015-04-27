package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.ContaCorrenteDAO;
import br.com.locadora.modelo.ContaCorrente;
import br.com.locadora.util.DAOFactory;

public class ContaCorrenteRN {
	private ContaCorrenteDAO contaCorrenteDAO;
	public ContaCorrenteRN() {
		this.contaCorrenteDAO = DAOFactory.criarContaCorrenteDAO();
	}
	public ContaCorrente carregar(Integer conCorId) {
		return this.contaCorrenteDAO.carregar(conCorId);

	}

	public ContaCorrente buscarContaCorrente(String contaCorrente) {
		return this.contaCorrenteDAO.buscarContaCorrente(contaCorrente);
	}

	public void salvar(ContaCorrente contaCorrente) {
		Integer codigo = contaCorrente.getConCorId();
		if (codigo == null || codigo == 0) {
			this.contaCorrenteDAO.salvar(contaCorrente);
		} else {
			this.contaCorrenteDAO.atualizar(contaCorrente);
		}

	}

	public void excluir(ContaCorrente contaCorrente) {
		this.contaCorrenteDAO.excluir(contaCorrente);
	}

	public List<ContaCorrente> listar() {
		return this.contaCorrenteDAO.listar();
	}
}
