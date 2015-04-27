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

import br.com.locadora.modelo.Caracteristicas;
import br.com.locadora.rn.CaracteriscasRN;

@ManagedBean(name = "JSONCaracteristicas")
@ViewScoped
public class CaracteristicasJSON implements Serializable{
	
	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = -8489722558999043689L;

	public void rendenizarJson() throws IOException, JSONException {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    externalContext.setResponseContentType("application/json");
	    externalContext.setResponseCharacterEncoding("UTF-8");
	    externalContext.getResponseOutputWriter().write(carregarLista());
	    facesContext.responseComplete();
	}
	
	private String carregarLista() throws JSONException {
		List<Caracteristicas> lista = new CaracteriscasRN().listar();
		
		JSONArray jsonArray = new JSONArray();
		
		for (Caracteristicas item : lista) {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("id", item.getCarId());
			jsonObject.put("value", item.getCarNome());
			
			jsonArray.put(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
}