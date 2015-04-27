package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.EmailDAO;
import br.com.locadora.modelo.Email;
import br.com.locadora.modelo.Filial;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.util.DAOFactory;

public class EmailRN {

	private EmailDAO emailDAO;

	public EmailRN() {
		this.emailDAO = DAOFactory.criarEmailDAO();
	}

	public Email carregar(Integer emaId) {
		return this.emailDAO.carregar(emaId);

	}

	public Email buscarPorEmail(String email) {
		return this.emailDAO.buscarPorEmail(email);
	}

	public void salvar(Email email) {
		Integer codigo = email.getEmaId();
		if (codigo == null || codigo == 0) {
			this.emailDAO.salvar(email);
		} else {
			this.emailDAO.atualizar(email);
		}

	}

	public void excluir(Email email) {
		this.emailDAO.excluir(email);
	}

	public List<Email> listar() {
		return this.emailDAO.listar();
	}
	
	public List<Email> carregarPessoa(Pessoa pessoa){		
		return this.emailDAO.carregarPessoa(pessoa);
	}
	public List<Email> carregarFilial(Filial filial){
		return this.emailDAO.carregarFilial(filial);
	}
}