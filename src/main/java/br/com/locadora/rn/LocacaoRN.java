package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.LocacaoDAO;
import br.com.locadora.modelo.Fiador;
import br.com.locadora.modelo.Locacao;
import br.com.locadora.util.DAOFactory;

public class LocacaoRN {
	private LocacaoDAO locacaoDAO;

	public LocacaoRN() {
		this.locacaoDAO = DAOFactory.criarLocacaoDAO();
	}

	public Locacao carregar(Integer locId) {
		return this.locacaoDAO.carregar(locId);

	}

	public Locacao buscarPorLocacao(String locacao) {
		return this.locacaoDAO.buscarPorLocacao(locacao);
	}

	public void salvar(Locacao locacao) {
		Integer codigo = locacao.getLocId();
		if (codigo == null || codigo == 0) {
			this.locacaoDAO.salvar(locacao);
		} else {
			this.locacaoDAO.atualizar(locacao);
		}

	}

	public void excluir(Locacao locacao) {
		this.locacaoDAO.excluir(locacao);
	}

	public List<Locacao> listar() {
		return this.locacaoDAO.listar();
	}
	public List<Fiador> carregarFiadores(Locacao locacao) {
		return this.locacaoDAO.carregarFiadores(locacao);
	}
	
	/*public List<SituacaoLocacao> carregarSituacaoLocacao(Locacao locacao) {
		return this.locacaoDAO.carregarSituacaoLocacao(locacao);
	}
	
	public List<PermissoesLocacao> carregarPermissoesLocacao(Locacao locacao) {
		return this.locacaoDAO.carregarPermissoesLocacao(locacao);
	}*/
	
	public List<Locacao> contratosVencimentroMes(Integer mes) {
		return this.locacaoDAO.contratosVencimentroMes(mes);
	}
}
