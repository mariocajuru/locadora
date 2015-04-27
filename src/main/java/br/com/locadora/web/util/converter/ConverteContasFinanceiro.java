package br.com.locadora.web.util.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.locadora.modelo.ContasFinanceiro;
@FacesConverter(value="converteContasFinanceiro")
public class ConverteContasFinanceiro implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().length() == 0)
			return null;
		
		try {			
		    for (UIComponent child : component.getChildren()) {  
	            if (child instanceof UISelectItems) {  
	                @SuppressWarnings("unchecked")
					List<ContasFinanceiro> items = (List<ContasFinanceiro>) ((UISelectItems) child).getValue();
	                int id=Integer.parseInt(value);
	                for(ContasFinanceiro permissoesLocacao: items){
	                	if(permissoesLocacao.getConFinId()==id){
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
			ContasFinanceiro contasFinanceiro = (ContasFinanceiro) value;
			return String.valueOf(contasFinanceiro.getConFinId());
		}
		return "";
	}
}
