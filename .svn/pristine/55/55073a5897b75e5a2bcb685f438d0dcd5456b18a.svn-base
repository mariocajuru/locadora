package br.com.renovarsistemas.francisco.controller.pesquisa;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import br.com.locadora.modelo.Tipoimovel;
import br.com.locadora.rn.TipoImovelRN;
import lombok.Getter;

@ManagedBean(name = "JSONTipoImovel")
@ViewScoped
public class TipoImovelJSON implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = -4254034634248974075L;

	public void rendenizarJson() throws IOException, JSONException {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    externalContext.setResponseContentType("application/json");
	    externalContext.setResponseCharacterEncoding("UTF-8");
	    externalContext.getResponseOutputWriter().write(carregarLista());
	    facesContext.responseComplete();
	}
	
	private String carregarLista() throws JSONException {
		List<Tipoimovel> lista = new TipoImovelRN().listar();
		
		JSONArray jsonArray = new JSONArray();
		
		for (Tipoimovel item : lista) {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("id", item.getTipId());
			jsonObject.put("value", item.getTipNome());
			
			jsonArray.put(jsonObject);
		}
		
		return jsonArray.toString();
	}
}
