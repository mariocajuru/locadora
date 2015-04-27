package br.com.locadora.web.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.locadora.web.ContextoBean;

public class ContextoUtil {
	public static ContextoBean getContextoBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		HttpSession session = (HttpSession) external.getSession(true);

		ContextoBean contextoBean = (ContextoBean) session.getAttribute("contextoBean");
		
		return contextoBean;
	}
	
	public static GenericBean getGenericBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		HttpSession session = (HttpSession) external.getSession(true);

		GenericBean genericBean = (GenericBean) session.getAttribute("genericBean");
		
		return genericBean;
	}
}


