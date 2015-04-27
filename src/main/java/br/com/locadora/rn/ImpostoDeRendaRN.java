package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.ImpostoDeRendaDAO;
import br.com.locadora.modelo.ImpostoDeRenda;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class ImpostoDeRendaRN {
	private ImpostoDeRendaDAO impostoDeRendaDAO;
	public ImpostoDeRendaRN() {
		this.impostoDeRendaDAO = DAOFactory.criarImpostoDeRendaDAO();
	}
	public ImpostoDeRenda carregar(Integer faiImpId) {
		return this.impostoDeRendaDAO.carregar(faiImpId);

	}

	public ImpostoDeRenda buscarPorImpostoDeRenda(Integer mes, Integer ano) {
		return this.impostoDeRendaDAO.buscarPorImpostoDeRenda(mes,ano);
	}

	public void salvar(ImpostoDeRenda impostoDeRenda) {
		Integer codigo = impostoDeRenda.getImpRenId();
		if (codigo == null || codigo == 0) {
			this.impostoDeRendaDAO.salvar(impostoDeRenda);
		} else {
			this.impostoDeRendaDAO.atualizar(impostoDeRenda);
		}

	}

	public boolean excluir(ImpostoDeRenda impostoDeRenda) {
		if(this.impostoDeRendaDAO.dependecias(impostoDeRenda)){
		this.impostoDeRendaDAO.excluir(impostoDeRenda);
		return true;
	}else{
		try {
			throw new UtilException("Erro ao excluir. Esse Imposto de Renda tem ligações com faixa de imposto de renda e não pode ser deletado.");
		} catch (UtilException e) {
				return false;
		}
	}
	}

	public List<ImpostoDeRenda> listar() {
		return this.impostoDeRendaDAO.listar();
	}
}
