package br.com.locadora.rn;

import java.util.List;

import org.primefaces.json.JSONException;

import br.com.locadora.dao.NovaPesquisaDAOInterface;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.util.DAOFactory;
import br.com.renovarsistemas.francisco.controller.pesquisa.PesquisaException;

public class NovaPesquisaRN {
	
	private NovaPesquisaDAOInterface pesquisaDAO;

	public NovaPesquisaRN() {
		this.pesquisaDAO = DAOFactory.criarNovaPesquisaDAO();
	}

	public List<Imovel> pesquisar(String jsonString) throws JSONException, PesquisaException {
		return this.pesquisaDAO.pesquisar(jsonString);
	}
	
}
