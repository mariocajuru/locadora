package br.com.locadora.web.util.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.locadora.modelo.PlanoDeContas;
@FacesConverter(value="convertePlanoDeContas")
public class ConvertePlanoDeContas implements Converter{
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().length() == 0)
			return null;
		
		try {			
		    for (UIComponent child : component.getChildren()) {  
	            if (child instanceof UISelectItems) {  
	                @SuppressWarnings("unchecked")
					List<PlanoDeContas> items = (List<PlanoDeContas>) ((UISelectItems) child).getValue();
	                int id=Integer.parseInt(value);
	                for(PlanoDeContas permissoesLocacao: items){
	                	if(permissoesLocacao.getPlaConId()==id){
	                		return permissoesLocacao;
	                	}
	                }
	            }  
	        }  
	        return null;  
			
		} catch (Exception e) {
			throw new ConverterException(e.getMessage());
		}
		
	//	return contasFinanceiro;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if(value.equals("")){
				return "";
			}
			PlanoDeContas categoria = (PlanoDeContas) value;
			Integer id=categoria.getPlaConId();
			String idString=String.valueOf(id);
			return idString;
		}
		return "";
	}
}
