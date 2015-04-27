package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.HistoricoPessoaDAO;
import br.com.locadora.modelo.HistoricoPessoa;
import br.com.locadora.util.DAOFactory;

public class HistoricoPessoaRN {
	private HistoricoPessoaDAO historicoPessoaDAO;

	public HistoricoPessoaRN() {
		this.historicoPessoaDAO = DAOFactory.criarHistoricoPessoaDAO();
	}

	public HistoricoPessoa carregar(Integer hisId) {
		return this.historicoPessoaDAO.carregar(hisId);

	}

	public HistoricoPessoa buscarPorHistoricoPessoa(String historicoPessoa) {
		return this.historicoPessoaDAO.buscarPorHistoricoPessoa(historicoPessoa);
	}

	public void salvar(HistoricoPessoa historicoPessoa) {
		Integer codigo = historicoPessoa.getHisId();
		if (codigo == null || codigo == 0) {
			this.historicoPessoaDAO.salvar(historicoPessoa);
		} else {
			this.historicoPessoaDAO.atualizar(historicoPessoa);
		}

	}

	public void excluir(HistoricoPessoa historicoPessoa) {
		this.historicoPessoaDAO.excluir(historicoPessoa);
	}

	public List<HistoricoPessoa> listar() {
		return this.historicoPessoaDAO.listar();
	}
}
