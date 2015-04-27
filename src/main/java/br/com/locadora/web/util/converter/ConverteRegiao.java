package br.com.locadora.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.locadora.modelo.Regiao;
import br.com.locadora.rn.RegiaoRN;

@FacesConverter(value="converteRegiao" )
public class ConverteRegiao implements Converter{
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				RegiaoRN regiaoRN = new RegiaoRN();
				return regiaoRN.carregar(codigo);
			} catch (Exception e) {
				throw new ConverterException("N�o foi poss�vel encontrar o detalhe de c�digo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Regiao detalhe = (Regiao) value;
			return String.valueOf(detalhe.getRegId());
		}
		return "";
	}
}


