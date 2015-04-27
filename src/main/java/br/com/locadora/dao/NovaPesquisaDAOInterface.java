package br.com.locadora.dao;

import java.util.List;

import org.primefaces.json.JSONException;

import br.com.locadora.modelo.Imovel;
import br.com.renovarsistemas.francisco.controller.pesquisa.PesquisaException;


public interface NovaPesquisaDAOInterface {
	
	public List<Imovel> pesquisar(String jsonArray) throws JSONException, PesquisaException;
	
}