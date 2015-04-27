package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.ProprietariosLocacaoDAO;
import br.com.locadora.modelo.ProprietariosLocacao;
import br.com.locadora.util.DAOFactory;

public class ProprietariosLocacaoRN {
	private ProprietariosLocacaoDAO proprietariosLocacaoDAO;

	public ProprietariosLocacaoRN() {
		this.proprietariosLocacaoDAO = DAOFactory.criarProprietariosLocacaoDAO();
	}
	
	public ProprietariosLocacao carregar(Integer proLocId) {
		return this.proprietariosLocacaoDAO.carregar(proLocId);

	}

	public void salvar(ProprietariosLocacao proprietariosLocacao) {
		Integer codigo = proprietariosLocacao.getProLocId();
		if (codigo == null || codigo == 0) {
			this.proprietariosLocacaoDAO.salvar(proprietariosLocacao);
		} else {
			this.proprietariosLocacaoDAO.atualizar(proprietariosLocacao);
		}

	}

	public void excluir(ProprietariosLocacao proprietariosLocacao) {
		this.proprietariosLocacaoDAO.excluir(proprietariosLocacao);
	}

	public List<ProprietariosLocacao> listar() {
		return this.proprietariosLocacaoDAO.listar();
	}
}
