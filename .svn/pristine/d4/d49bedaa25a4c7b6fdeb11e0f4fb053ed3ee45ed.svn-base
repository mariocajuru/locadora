package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.InformacaoAdicionalDAO;
import br.com.locadora.modelo.InformacaoAdicional;
import br.com.locadora.modelo.Locacao;
import br.com.locadora.util.DAOFactory;

public class InformacaoAdicionalRN {
	private InformacaoAdicionalDAO informacaoAdicionalDAO;
	public InformacaoAdicionalRN() {
		this.informacaoAdicionalDAO = DAOFactory.criarInformacaoAdicionalDAO();
	}
	public InformacaoAdicional carregar(Integer filId) {
		return this.informacaoAdicionalDAO.carregar(filId);

	}

	public InformacaoAdicional buscarPorInformacaoAdicional(String informacaoAdicional) {
		return this.informacaoAdicionalDAO.buscarPorInformacaoAdicional(informacaoAdicional);
	}

	public void salvar(InformacaoAdicional informacaoAdicional) {
		Integer codigo = informacaoAdicional.getInfAdiId();
		if (codigo == null || codigo == 0) {
			this.informacaoAdicionalDAO.salvar(informacaoAdicional);
		} else {
			this.informacaoAdicionalDAO.atualizar(informacaoAdicional);
		}

	}

	public void excluir(InformacaoAdicional informacaoAdicional) {
		this.informacaoAdicionalDAO.excluir(informacaoAdicional);
	}

	public List<InformacaoAdicional> listar() {
		return this.informacaoAdicionalDAO.listar();
	}
	
	public List<InformacaoAdicional> carregarPorLocacao(Locacao locacao) {
		return this.informacaoAdicionalDAO.carregarPorLocacao(locacao);
	}
	
}
