package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.ReferenciapessoalDAO;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Referenciapessoal;
import br.com.locadora.util.DAOFactory;

public class ReferenciapessoalRN {
private ReferenciapessoalDAO referenciapessoalDAO;
	public ReferenciapessoalRN() {
		this.referenciapessoalDAO=DAOFactory.criarReferenciapessoalDAO();
	}
	public Referenciapessoal carregar(Integer baiId) {
		return this.referenciapessoalDAO.carregar(baiId);

	}

	public Referenciapessoal buscarPorReferencia(String referenciapessoal) {
		return this.referenciapessoalDAO.buscarPorReferencia(referenciapessoal);
	}

	public void salvar(Referenciapessoal referenciapessoal) {
		Integer codigo = referenciapessoal.getRefId();
		if (codigo == null || codigo == 0) {
			this.referenciapessoalDAO.salvar(referenciapessoal);
		} else {
			this.referenciapessoalDAO.atualizar(referenciapessoal);
		}

	}

	public void excluir(Referenciapessoal referenciapessoal) {
		this.referenciapessoalDAO.excluir(referenciapessoal);
	}

	public List<Referenciapessoal> listar() {
		return this.referenciapessoalDAO.listar();
	}
	
public List<Referenciapessoal> carregarPessoa(Pessoa pessoa){
		
		return this.referenciapessoalDAO.carregarPessoa(pessoa);
	}
}
