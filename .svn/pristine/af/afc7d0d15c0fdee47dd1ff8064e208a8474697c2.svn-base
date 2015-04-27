package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.PropostaDAO;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Proposta;
import br.com.locadora.util.DAOFactory;

public class PropostaRN {
	private PropostaDAO propostaDAO;

	public PropostaRN() {
		this.propostaDAO = DAOFactory.criarPropostaDAO();
	}

	public Proposta carregar(Integer proId) {
		return this.propostaDAO.carregar(proId);

	}

	public Proposta buscarPorProposta(String proposta) {
		return this.propostaDAO.buscarPorProposta(proposta);
	}

	public void salvar(Proposta proposta) {
		Integer codigo = proposta.getProId();
		if (codigo == null || codigo == 0) {
			this.propostaDAO.salvar(proposta);
		} else {
			this.propostaDAO.atualizar(proposta);
		}

	}

	public void excluir(Proposta proposta) {
		this.propostaDAO.excluir(proposta);
	}

	public List<Proposta> listar() {
		return this.propostaDAO.listar();
	}
	
	public List<Proposta> carregarPorImovel(Imovel imovel) {
		return this.propostaDAO.carregarPorImovel(imovel);
	}
}
