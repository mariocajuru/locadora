package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.FaixaImpostoDeRendaDAO;
import br.com.locadora.modelo.FaixaImpostoDeRenda;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class FaixaImpostoDeRendaRN {
	private FaixaImpostoDeRendaDAO faixaImpostoDeRendaDAO;
	public FaixaImpostoDeRendaRN() {
		this.faixaImpostoDeRendaDAO = DAOFactory.criarFaixaImpostoDeRendaDAO();
	}
	public FaixaImpostoDeRenda carregar(Integer faiImpId) {
		return this.faixaImpostoDeRendaDAO.carregar(faiImpId);

	}

	public FaixaImpostoDeRenda buscarPorDestinacaoLocacao(String faixaImpostoDeRenda) {
		return this.faixaImpostoDeRendaDAO.buscarPorFaixaImpostoDeRenda(faixaImpostoDeRenda);
	}

	public void salvar(FaixaImpostoDeRenda faixaImpostoDeRenda) {
		Integer codigo = faixaImpostoDeRenda.getFaiImpRenId();
		if (codigo == null || codigo == 0) {
			this.faixaImpostoDeRendaDAO.salvar(faixaImpostoDeRenda);
		} else {
			this.faixaImpostoDeRendaDAO.atualizar(faixaImpostoDeRenda);
		}

	}

	public boolean excluir(FaixaImpostoDeRenda faixaImpostoDeRenda) {
		if(this.faixaImpostoDeRendaDAO.dependecias(faixaImpostoDeRenda)){
		this.faixaImpostoDeRendaDAO.excluir(faixaImpostoDeRenda);
		return true;
	}else{
		try {
			throw new UtilException("Erro ao excluir. Esse Faixa de Imposto de Renda tem ligações com imposto de renda e não pode ser deletado.");
		} catch (UtilException e) {
				return false;
		}
	}
	}

	public List<FaixaImpostoDeRenda> listar() {
		return this.faixaImpostoDeRendaDAO.listar();
	}
}
