package br.com.renovarsistemas.francisco.controller.pesquisa;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import lombok.Getter;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import br.com.locadora.modelo.Cidade;
import br.com.locadora.rn.CidadeRN;

@ManagedBean(name = "JSONCidades")
@ViewScoped
public class CidadesJSON implements Serializable{
	
	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = -5024544154161904583L;

	public void rendenizarJson() throws IOException, JSONException {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    externalContext.setResponseContentType("application/json");
	    externalContext.setResponseCharacterEncoding("UTF-8");
	    externalContext.getResponseOutputWriter().write(carregarLista());
	    facesContext.responseComplete();
	}
	
	private String carregarLista() throws JSONException {
		List<Cidade> lista = new CidadeRN().listar();
		
		JSONArray jsonArray = new JSONArray();
		
		for (Cidade item : lista) {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("id", item.getCidId());
			jsonObject.put("value", item.getCidNome());
			
			jsonArray.put(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
}