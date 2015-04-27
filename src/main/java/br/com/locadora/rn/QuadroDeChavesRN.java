package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.QuadroDeChavesDAO;
import br.com.locadora.modelo.QuadroDeChaves;
import br.com.locadora.util.DAOFactory;

public class QuadroDeChavesRN {
	private QuadroDeChavesDAO quadroDeChavesDAO;

	public QuadroDeChavesRN() {
		this.quadroDeChavesDAO = DAOFactory.criarQuadroDeChavesDAO();
	}

	public QuadroDeChaves carregar(Integer chaveId) {
		return this.quadroDeChavesDAO.carregar(chaveId);

	}

	public void salvar(QuadroDeChaves chave) {
		Integer codigo = chave.getQuaId();
		if (codigo == null || codigo == 0) {
			this.quadroDeChavesDAO.salvar(chave);
		} else {
			this.quadroDeChavesDAO.atualizar(chave);
		}

	}

	public void excluir(QuadroDeChaves chave) {
		this.quadroDeChavesDAO.excluir(chave);
	}

	public List<QuadroDeChaves> listar() {
		return this.quadroDeChavesDAO.listar();
	}

	public QuadroDeChaves buscarPorQuadroDeChaves(String quaChaves) {
		return this.quadroDeChavesDAO.buscarPorQuadroDeChaves(quaChaves);
	}
	
	public QuadroDeChaves buscarPorFilial(Integer idFilial) {
		return this.quadroDeChavesDAO.buscarPorFilial(idFilial);
	
	}
}
