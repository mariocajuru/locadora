package br.com.locadora.web.util.converter;



import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

import br.com.locadora.modelo.Bairro;
import br.com.locadora.rn.BairroRN;


@FacesConverter(value="converteBairro")
public class ConverteBairro implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				BairroRN categoriaRN = new BairroRN();
				return categoriaRN.carregar(codigo);
			} catch (Exception e) {
				throw new ConverterException("Não foi possível encontrar a categoria de código " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Bairro categoria = (Bairro) value;
			return String.valueOf(categoria.getBaiId());
		}
		return "";
	}
}
