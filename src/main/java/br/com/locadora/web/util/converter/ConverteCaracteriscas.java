package br.com.locadora.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.locadora.modelo.Caracteristicas;
import br.com.locadora.rn.CaracteriscasRN;

@FacesConverter(value="converteCaracteriscas")
public class ConverteCaracteriscas implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			System.out.println("Codigo do converteDetalhes: "+Integer.parseInt(value));
			try {
				CaracteriscasRN  caracteriscasRN = new CaracteriscasRN();
				return caracteriscasRN.carregar(Integer.parseInt(value));
			} catch (Exception e) {
				throw new ConverterException("Não foi possóvel encontrar o detalhe de código " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Caracteristicas caracteriscas  = (Caracteristicas) value;
			return String.valueOf(caracteriscas.getCarId());
		}
		return "";
	}
}


