package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.FiadorDAO;
import br.com.locadora.modelo.Fiador;
import br.com.locadora.modelo.Locacao;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.util.DAOFactory;

public class FiadorRN {
	private FiadorDAO fiadorDAO;

	public FiadorRN() {
		this.fiadorDAO = DAOFactory.criarFiadorDAO();
	}

	public Fiador carregar(Integer filId) {
		return this.fiadorDAO.carregar(filId);

	}

	public Fiador buscarPorFiador(String fiador) {
		return this.fiadorDAO.buscarPorFiador(fiador);
	}

	public void salvar(Fiador fiador) {
		Integer codigo = fiador.getFiaId();
		if (codigo == null || codigo == 0) {
			this.fiadorDAO.salvar(fiador);
		} else {
			this.fiadorDAO.atualizar(fiador);
		}

	}

	public void excluir(Fiador fiador) {
		this.fiadorDAO.excluir(fiador);
	}

	public List<Fiador> listar() {
		return this.fiadorDAO.listar();
	}
	public List<Fiador> carregarFiadoresPorImovel(Locacao locacao){
		return this.fiadorDAO.carregarFiadoresPorImovel(locacao);
	}
	
	public Fiador carregarPorPessoa(Pessoa pessoa){
		return this.fiadorDAO.carregarPorPessoa(pessoa);
	}
}
