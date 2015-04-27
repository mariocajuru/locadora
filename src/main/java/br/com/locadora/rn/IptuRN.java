package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.IptuDAO;
import br.com.locadora.modelo.Iptu;
import br.com.locadora.modelo.Locacao;
import br.com.locadora.util.DAOFactory;

public class IptuRN {

	private IptuDAO iptuDAO;
	public IptuRN() {
		this.iptuDAO = DAOFactory.criarIptuDAO();
	}
	public Iptu carregar(Integer iptId) {
		return this.iptuDAO.carregar(iptId);

	}

	public Iptu buscarPorDestinacaoLocacao(Integer iptuAno) {
		return this.iptuDAO.buscarPorAnoIptu(iptuAno);
	}

	public void salvar(Iptu iptu) {
		Integer codigo = iptu.getIptId();
		if (codigo == null || codigo == 0) {
			this.iptuDAO.salvar(iptu);
		} else {
			this.iptuDAO.atualizar(iptu);
		}

	}

	public void excluir(Iptu iptu) {
		this.iptuDAO.excluir(iptu);
	}

	public List<Iptu> listar() {
		return this.iptuDAO.listar();
	}
	
	public List<Iptu> carregarPorLocacao(Locacao locacao){
		return this.iptuDAO.carregarPorLocacao(locacao);
	}
}
