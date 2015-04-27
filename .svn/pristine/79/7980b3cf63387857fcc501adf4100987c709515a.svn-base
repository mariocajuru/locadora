package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.CentroDeCustoDAO;
import br.com.locadora.modelo.CentroDeCusto;
import br.com.locadora.modelo.GrupoDeContas;
import br.com.locadora.util.DAOFactory;

public class CentroDeCustoRN {

	private CentroDeCustoDAO centroDeCustoDAO;
	public CentroDeCustoRN() {
		this.centroDeCustoDAO = DAOFactory.criarCentroDeCustoDAO();
	}
	public CentroDeCusto carregar(Integer cenCusId) {
		return this.centroDeCustoDAO.carregar(cenCusId);

	}

	public CentroDeCusto buscarPorCentroDeCusto(String centroDeCusto) {
		return this.centroDeCustoDAO.buscarPorCentroDeCusto(centroDeCusto);
	}

	public void salvar(CentroDeCusto centroDeCusto) {
		Integer codigo = centroDeCusto.getCenCusId();
		if (codigo == null || codigo == 0) {
			this.centroDeCustoDAO.salvar(centroDeCusto);
		} else {
			this.centroDeCustoDAO.atualizar(centroDeCusto);
		}

	}

	public boolean excluir(CentroDeCusto centroDeCusto) {
		//if(this.contasCreditoDAO.dependecias(contasCredito)){
			this.centroDeCustoDAO.excluir(centroDeCusto);
			return true;
			/*	}else{
			try {
				throw new UtilException("Erro ao excluir. Esse ContasCredito tem ligações com outros locação e não pode ser deletado.");
			} catch (UtilException e) {
				return false;
			}
		}*/
	}

	public List<CentroDeCusto> listar() {
		return this.centroDeCustoDAO.listar();
	}

	public CentroDeCusto carregarPorGrupoDeContas(GrupoDeContas grupoDeContas){
		return this.centroDeCustoDAO.carregarPorGrupoDeContas(grupoDeContas);
	}
}
