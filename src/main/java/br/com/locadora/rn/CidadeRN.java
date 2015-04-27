package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.CidadeDAO;
import br.com.locadora.modelo.Cidade;
import br.com.locadora.util.DAOFactory;

public class CidadeRN {
	private CidadeDAO cidadeDAO;

	public CidadeRN() {
		this.cidadeDAO = DAOFactory.criarCidadeDAO();
	}

	public Cidade carregar(Integer codigo) {
		return this.cidadeDAO.carregar(codigo);

	}

	public Cidade buscarPorCidade(String cidade) {
		return this.cidadeDAO.buscarPorCidade(cidade);
	}

	public void salvar(Cidade cidade) {
		Integer codigo = cidade.getCidId();
		if (codigo == null || codigo == 0) {
			this.cidadeDAO.salvar(cidade);
		} else {
			this.cidadeDAO.atualizar(cidade);
		}

	}

	public void excluir(Cidade cidade) {
		this.cidadeDAO.excluir(cidade);
	}

	public List<Cidade> listar() {
		return this.cidadeDAO.listar();
	}
}
