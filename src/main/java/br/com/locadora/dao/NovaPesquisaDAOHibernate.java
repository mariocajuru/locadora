package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.ImovelProcurado;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.rn.ImovelProcuradoRN;
import br.com.renovarsistemas.francisco.controller.pesquisa.PesquisaException;
import br.com.renovarsistemas.francisco.util.NumerosHelper;

public class NovaPesquisaDAOHibernate implements NovaPesquisaDAOInterface {

	private Session       session;
	private StringBuilder sql;
	private Query         query;

	public void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Imovel> pesquisar(String jsonString) throws JSONException, PesquisaException {
		if (jsonString == null)
			throw new PesquisaException("Parâmetros incorretos.");
		
		this.sql = new StringBuilder();
		
		this.sql.append("SELECT distinct imo");
		this.sql.append(" FROM Imovel as imo");
		this.sql.append(" LEFT JOIN imo.endereco as endereco");
		this.sql.append(" LEFT JOIN imo.endereco.cidade as cidade");
		this.sql.append(" LEFT JOIN imo.imovelCaracteristicases as car");
		this.sql.append(" LEFT JOIN imo.tipoimovel as tipo");
		this.sql.append(" LEFT JOIN car.id as car1");

		this.sql.append(" WHERE");
		
		JSONObject json = new JSONObject(jsonString);
		
		criarFiltroPorCidade(
			json.getJSONArray("cidades") 
		);
		
		criarFiltroPorTipoImovel(
				json.getJSONArray("tipo_imoveis") 
			);
		
		criarFiltroPorCaracteristica(
			json.getJSONArray("caracteristicas") 
		);
		
		criarFitroPorObjetivo(
			json.getJSONArray("objetivos")
		);
		
		criarFiltroPorValor(
			json.getJSONArray("valores")	
		);
		
		this.query = session.createQuery(this.sql.toString());
				
		List<Imovel> listaImoveis=this.query.list();
		
		if(listaImoveis.size()==0){
			ImovelProcurado imovelProcurado=new ImovelProcurado();
			Pessoa pessoa=new Pessoa();
			pessoa.setPesId(2);
			imovelProcurado.setPessoa(pessoa);
			imovelProcurado.setImoProSql(this.sql.toString());
			imovelProcurado.setImoProEncontrado(false);
			new ImovelProcuradoRN().salvar(imovelProcurado);
		}
		
		return listaImoveis;
	}
	
	/**
	 * -------------------------------------------------------------------------------------------
	 * Filtro por Cidade
	 * -------------------------------------------------------------------------------------------
	 * 
	 * SQL com 1 cidade:  (cidade.cidId = 1)
	 * SQL com 2 cidades: (cidade.cidId = 1 OR cidade.cidId = 2)
	 * 
	 * @param arrayCriterios array com todas cidades recebidas na pesquisa
	 * @throws JSONException
	 */
	private void criarFiltroPorCidade(JSONArray arrayCriterios) throws JSONException {
		if (arrayCriterios.length() == 0)
			return;
		
		this.sql.append(" (");
		
		for (int i = 0; i < arrayCriterios.length(); i++) {
			JSONObject cidadeJson = arrayCriterios.getJSONObject(i);
			
			if (i > 0)
				this.sql.append(" OR");
			
			this.sql.append(" cidade.cidId = " + cidadeJson.getInt("id"));
		}

		this.sql.append(" )");
	}
	
	private void criarFiltroPorTipoImovel(JSONArray arrayCriterios) throws JSONException {
		if (arrayCriterios.length() == 0)
			return;
		
		if (this.sql.toString().endsWith("WHERE") == false)
			this.sql.append(" AND");
		
		this.sql.append(" (");
		
		for (int i = 0; i < arrayCriterios.length(); i++) {
			JSONObject tipoJson = arrayCriterios.getJSONObject(i);
			
			if (i > 0)
				this.sql.append(" OR");
			
			this.sql.append(" tipo.tipId = " + tipoJson.getInt("id"));
		}

		this.sql.append(" )");
	}

	
	/**
	 * -------------------------------------------------------------------------------------------
	 * Filtro por Caracteristicas
	 * -------------------------------------------------------------------------------------------
	 * 
	 * SQL com 1 cidade:  ( (car1.carId = 1 AND car.imoCarQtd = 1) )
	 * SQL com 2 cidades: ( (car1.carId = 1 AND car.imoCarQtd = 1) OR (car1.carId = 2 AND car.imoCarQtd = 1) )
	 * 
	 * @param arrayCriterios array com todas cidades recebidas na pesquisa
	 * @throws JSONException
	 */
	private void criarFiltroPorCaracteristica(JSONArray arrayCriterios) throws JSONException {	
		if (arrayCriterios.length() == 0)
			return;
		
		if (this.sql.toString().endsWith("WHERE") == false)
			this.sql.append(" AND");

		this.sql.append(" (");

		for (int i = 0; i < arrayCriterios.length(); i++) {
			JSONObject caracteristicaJson = arrayCriterios.getJSONObject(i);

			if (i > 0)
				this.sql.append(" OR");
			
			int id = caracteristicaJson.getInt("id");
			
			
			/** Quanto a quantidade conter '1-2' usar o between */ 
			if (caracteristicaJson.getString("texto").contains("-")) {
				int quantInicial = NumerosHelper.apenasNumeros(caracteristicaJson.getString("texto").split("-")[0]);
				int quantFinal   = NumerosHelper.apenasNumeros(caracteristicaJson.getString("texto").split("-")[1]);
				
				this.sql.append(" (car1.carId = " + id + " AND car.imoCarQtd BETWEEN " + quantInicial + " AND " + quantFinal + ")");
			} 

			/** Comparação normal */ 
			else {
				int quant = NumerosHelper.apenasNumeros(caracteristicaJson.getString("texto"));
				
				this.sql.append(" (car1.carId = " + id + " AND car.imoCarQtd = " + quant + ")");
			}
		}
				
		this.sql.append(" )");
	}
	
	
	/**
	 * -------------------------------------------------------------------------------------------
	 * Filtro por Objetivo (LOCAÇÂO OU VENDA)
	 * -------------------------------------------------------------------------------------------
	 * 
	 * @param jsonArray
	 * @throws JSONException
	 */
	private void criarFitroPorObjetivo(JSONArray arrayCriterios) throws JSONException, PesquisaException {
		if (arrayCriterios.length() == 0)
			return;
		
		if (this.sql.toString().endsWith("WHERE") == false)
			this.sql.append(" AND");

		this.sql.append(" (");

		for (int i = 0; i < arrayCriterios.length(); i++) {
			JSONObject objetivoJson = arrayCriterios.getJSONObject(i);

			if (i > 0)
				this.sql.append(" OR");
			
			String tipo = objetivoJson.getString("value");
			
			if (tipo.equalsIgnoreCase("COMPRAR"))
				this.sql.append(" imo.imoVenda = true");
			else if (tipo.equalsIgnoreCase("ALUGAR"))
				this.sql.append(" imo.imoLocacao = true");
			else
				throw new PesquisaException("Parâmetros de Venda/Aluguel inválidos.");
		}
		
		this.sql.append(" )");
	}
	
	
	/**
	 * -------------------------------------------------------------------------------------------
	 * Filtro por Valor
	 * -------------------------------------------------------------------------------------------
	 * 
	 * @param jsonArray
	 * @throws JSONException
	 */
	private void criarFiltroPorValor(JSONArray arrayCriterios) throws JSONException, PesquisaException {
		if (arrayCriterios.length() == 0)
			return;
		
		if (this.sql.toString().endsWith("WHERE") == false)
			this.sql.append(" AND");

		this.sql.append(" (");

		for (int i = 0; i < arrayCriterios.length(); i++) {
			JSONObject valorJson = arrayCriterios.getJSONObject(i);

			if (i > 0)
				this.sql.append(" OR");

			double valor = NumerosHelper.apenasNumerosDouble(valorJson.getString("texto"));
			
			if (valorJson.getString("texto").contains("VALOR-ALUGUEL")) 
				this.sql.append(" imo.imoValorAluguel = " + valor);
			else
				throw new PesquisaException("Parâmetros de valores inválidos.");
		}
		
		this.sql.append(" )");
	}

	


}