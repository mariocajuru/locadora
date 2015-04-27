package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.DescontoLocacaoDAO;
import br.com.locadora.modelo.DescontoLocacao;
import br.com.locadora.util.DAOFactory;

public class DescontoLocacaoRN {
	private DescontoLocacaoDAO descontoLocacaoDAO;
	public DescontoLocacaoRN() {
		this.descontoLocacaoDAO=DAOFactory.criarDescontoLocacaoDAO();
	}
	public DescontoLocacao carregar(Integer descLocId) {
		return this.descontoLocacaoDAO.carregar(descLocId);

	}

	public DescontoLocacao buscarPorFotoimovel(Double descontoLocacao) {
		return this.descontoLocacaoDAO.buscarPorDescontoLocacao(descontoLocacao);
	}

	public void salvar(DescontoLocacao descontoLocacao) {
		Integer codigo = descontoLocacao.getDescLocId();
		if (codigo == null || codigo == 0) {
			this.descontoLocacaoDAO.salvar(descontoLocacao);
		} else {
			this.descontoLocacaoDAO.atualizar(descontoLocacao);
		}

	}

	public void excluir(DescontoLocacao descontoLocacao) {
		this.descontoLocacaoDAO.excluir(descontoLocacao);
	}

	public List<DescontoLocacao> listar() {
		return this.descontoLocacaoDAO.listar();
	}
}
