package br.com.renovarsistemas.francisco.util;

import javax.faces.context.FacesContext;



public class ParametrosHelper {
	
	public String getParametroPost(String parametro, String valorPadrao) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String passedParameter = (String) facesContext.getExternalContext().getRequestParameterMap().get("parameter1");
		
		return passedParameter != null ? passedParameter : valorPadrao;
	}
	
}
