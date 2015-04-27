package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.ContasFinanceiroDAO;
import br.com.locadora.modelo.ContasFinanceiro;
import br.com.locadora.modelo.GrupoDeContas;
import br.com.locadora.util.DAOFactory;

public class ContasFinanceiroRN {

	private ContasFinanceiroDAO contasFinanceiroDAO;
	public ContasFinanceiroRN() {
		this.contasFinanceiroDAO = DAOFactory.criarContasFinanceiroDAO();
	}
	public ContasFinanceiro carregar(Integer conFinId) {
		return this.contasFinanceiroDAO.carregar(conFinId);

	}

	public ContasFinanceiro buscarContasFinanceiro(String centroDeCusto) {
		return this.contasFinanceiroDAO.buscarContasFinanceiro(centroDeCusto);
	}

	public void salvar(ContasFinanceiro centroDeCusto) {
		Integer codigo = centroDeCusto.getConFinId();
		if (codigo == null || codigo == 0) {
			this.contasFinanceiroDAO.salvar(centroDeCusto);
		} else {
			this.contasFinanceiroDAO.atualizar(centroDeCusto);
		}

	}

	public boolean excluir(ContasFinanceiro centroDeCusto) {
	//	if(this.contasDebitoDAO.dependecias(contasDebito)){
			this.contasFinanceiroDAO.excluir(centroDeCusto);
			return true;
	/*	}else{
			try {
				throw new UtilException("Erro ao excluir. Esse ContasDebito tem ligações com outros locação e não pode ser deletado.");
			} catch (UtilException e) {
				return false;
			}
		}*/
	}

	public List<ContasFinanceiro> listar() {
		return this.contasFinanceiroDAO.listar();
	}

	public ContasFinanceiro carregarContasFinanceiroCredito(GrupoDeContas grupoDeContas){
		return this.contasFinanceiroDAO.carregarContasFinanceiroCredito(grupoDeContas);
	}

	public ContasFinanceiro carregarContasFinanceiroDebito(GrupoDeContas grupoDeContas){
		return this.contasFinanceiroDAO.carregarContasFinanceiroDebito(grupoDeContas);
	}

}
