package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.ProprietarioDAO;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Proprietario;
import br.com.locadora.util.DAOFactory;

public class ProprietarioRN {
	private ProprietarioDAO proprietarioDAO;

	public ProprietarioRN() {
		this.proprietarioDAO = DAOFactory.criarProprietarioDAO();
	}

	public Proprietario carregar(Integer proId) {
		return this.proprietarioDAO.carregar(proId);

	}

	public Proprietario buscarPorPessoa(String proprietario) {
		return this.proprietarioDAO.buscarPorProprietario(proprietario);
	}

	public void salvar(Proprietario proprietario, boolean alterar) {
	//	if(!alterar){*/
		// mesmo sendo uma auteração de cadastro o metodo salvar em ProprietarioDAOHibernate tem o metodo saveOrUpdate que verifica automaticamente quando é para salvar ou para atualizar.
		this.proprietarioDAO.salvar(proprietario);
		//}
		/*this.proprietarioDAO.excluir(proprietario);
		this.proprietarioDAO.salvar(proprietario);*/
	}

	public void excluir(Proprietario proprietario) {
		this.proprietarioDAO.excluir(proprietario);
	}

	public List<Proprietario> listar() {
		return this.proprietarioDAO.listar();
	}
	
	public List<Proprietario> carregarProprietarios(Imovel imovel){
		return this.proprietarioDAO.carregarProprietarios(imovel);
	}
	
	public void excluirProprietariosDeImovel(Imovel imovel) {
		List<Proprietario> lista = this.proprietarioDAO.carregarProprietarios(imovel);
		
		for (Proprietario p : lista) {
			this.proprietarioDAO.excluir(p);
		}
	}
	
	public Proprietario carregarProprietarioPorImovelPessoa(Pessoa pessoa, Imovel imovel){
		return this.proprietarioDAO.carregarProprietarioPorImovelPessoa(pessoa, imovel);
	}
	
}
