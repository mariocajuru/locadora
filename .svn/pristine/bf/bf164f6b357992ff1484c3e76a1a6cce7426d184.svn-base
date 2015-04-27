package br.com.locadora.web.util.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.locadora.modelo.Tipoimovel;
import br.com.locadora.rn.TipoImovelRN;

@FacesConverter(value="converteTipoImovel" )
public class ConverteTipoimovel implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (value != null && value.trim().length() > 0) {
			try {
				System.out.println("Codigo do converteTipoImovel: "+Integer.parseInt(value));
				TipoImovelRN tipoRN = new TipoImovelRN();
				Tipoimovel tipoImovel= tipoRN.carregar(Integer.parseInt(value));//pode ocorrer um bug no Hibernate. #Triste
				return tipoImovel;
			} catch (Exception e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possivel encontrar o código", ""+value+"." + e.getMessage()));
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Tipoimovel categoria = (Tipoimovel) value;
			return String.valueOf(categoria.getTipId());
		}
		return "";
	}
}

