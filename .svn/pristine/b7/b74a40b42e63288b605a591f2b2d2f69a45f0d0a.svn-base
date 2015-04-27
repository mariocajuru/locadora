package br.com.locadora.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.locadora.modelo.Filial;
import br.com.locadora.rn.FilialRN;

@FacesConverter(value="converteFilial" )
public class ConverteFilial implements Converter{
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				FilialRN categoriaRN = new FilialRN();
				return categoriaRN.carregar(codigo);
			} catch (Exception e) {
				throw new ConverterException("N�o foi poss�vel encontrar a categoria de c�digo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Filial categoria = (Filial) value;
			return String.valueOf(categoria.getFilId());
		}
		return "";
	}
}
