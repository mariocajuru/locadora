package br.com.locadora.rn;

import java.util.List;


import br.com.locadora.dao.TelefoneDAO;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Telefone;
import br.com.locadora.util.DAOFactory;

public class TelefoneRN {
	private TelefoneDAO telefoneDAO;

	public TelefoneRN() {
		this.telefoneDAO = DAOFactory.criarTelefoneDAO();
	}

	public Telefone carregar(Integer telId) {
		return this.telefoneDAO.carregar(telId);

	}

	public Telefone buscarPorTelefone(String telefone) {
		return this.telefoneDAO.buscarPorTelefone(telefone);
	}

	public void salvar(Telefone telefone) {
		Integer codigo = telefone.getTelId();
//		TelefoneRN telefoneRN=new TelefoneRN();
//			Telefone telefone2=telefoneRN.buscarPorTelefone(telefone.getTelNumero());
//		if(telefone2!=null){
//			FacesContext
//			.getCurrentInstance()
//			.addMessage(
//					null,
//					new FacesMessage(
//							FacesMessage.SEVERITY_ERROR,
//							"Cadastro não efetuado - Telefone já existente !",
//							" Esse telefone já foi cadastrado em nosso banco de dados !"));
//			new UtilException("Cadastro não efetuado - Telefone já existente");
//			return;
//		}
		if (codigo == null || codigo == 0) {
			this.telefoneDAO.salvar(telefone);
		} else {
			this.telefoneDAO.atualizar(telefone);
		}

	}

	public void excluir(Telefone telefone) {
		this.telefoneDAO.excluir(telefone);
	}

	public List<Telefone> listar() {
		return this.telefoneDAO.listar();
	}
	
public List<Telefone> carregarPessoa(Pessoa pessoa){
		
		return this.telefoneDAO.carregarPessoa(pessoa);
	}
}
