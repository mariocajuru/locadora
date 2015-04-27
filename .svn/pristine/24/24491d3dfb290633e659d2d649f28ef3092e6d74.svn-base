package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.DadosBancariosProprietarioDAO;
import br.com.locadora.modelo.DadosBancariosProprietario;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.util.DAOFactory;

public class DadosBancariosProprietarioRN {
	private DadosBancariosProprietarioDAO bancariosProprietarioDAO;
	public DadosBancariosProprietarioRN() {
		this.bancariosProprietarioDAO=DAOFactory.criarDadosBancariosProprietarioDAO();
	}
	public DadosBancariosProprietario carregar(Integer dadBanProId) {
		return this.bancariosProprietarioDAO.carregar(dadBanProId);

	}

	public DadosBancariosProprietario buscarPorEmail(String dadosBancariosProprietario) {
		return this.bancariosProprietarioDAO.buscarPorDadosBancariosProprietario(dadosBancariosProprietario);
	}

	public void salvar(DadosBancariosProprietario dadosBancariosProprietario) {
		Integer codigo = dadosBancariosProprietario.getDadBanProId();
		if (codigo == null || codigo == 0) {
			this.bancariosProprietarioDAO.salvar(dadosBancariosProprietario);
		} else {
			this.bancariosProprietarioDAO.atualizar(dadosBancariosProprietario);
		}

	}

	public void excluir(DadosBancariosProprietario dadosBancariosProprietario) {
		this.bancariosProprietarioDAO.excluir(dadosBancariosProprietario);
	}

	public List<DadosBancariosProprietario> listar() {
		return this.bancariosProprietarioDAO.listar();
	}
	
	public DadosBancariosProprietario buscarPorPessoa(Pessoa pessoa) {
		return this.bancariosProprietarioDAO.buscarPorPessoa(pessoa);
	}

}
