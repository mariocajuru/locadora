package br.com.locadora.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.locadora.modelo.Cidade;
import br.com.locadora.rn.CidadeRN;

@FacesConverter(value="converteCidade" )
public class ConverteCidade implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				CidadeRN cidadeRN = new CidadeRN();
				return cidadeRN.carregar(codigo);
			} catch (Exception e) {
				throw new ConverterException("Não foi possível encontrar a categoria de código " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Cidade categoria = (Cidade) value;
			return String.valueOf(categoria.getCidId());
		}
		return "";
	}
}

