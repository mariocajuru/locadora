package br.com.locadora.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.locadora.modelo.Situacaoimovel;
import br.com.locadora.rn.SituacaoImovelRN;

@FacesConverter(value = "converteSituacao")
public class ConverteSituacao implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().length() == 0)
			return null;
		
		Integer cod = Integer.valueOf(value);

		Situacaoimovel situacao = null;
		
		try {			
			situacao = new SituacaoImovelRN().carregar(cod);
		} catch (Exception e) {
			throw new ConverterException(e.getMessage());
		}
		
		return situacao;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Situacaoimovel situacaoImovel = (Situacaoimovel) value;
		
		String retorno = String.valueOf(situacaoImovel.getSitId());
		
		return retorno == null ? "" : retorno;
	}
}
