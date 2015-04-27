package br.com.locadora.web.util.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.locadora.modelo.PermissoesLocacao;

@FacesConverter(value="convertePermissoesLocacao" )
public class ConvertePermissoesLocacao implements Converter{

   /* private int counter; */ 
  
    @SuppressWarnings("unchecked")
	@Override  
    public Object getAsObject(FacesContext context, UIComponent component,  
            String value) {  
        for (UIComponent child : component.getChildren()) {  
            if (child instanceof UISelectItems) {  
                List<PermissoesLocacao> items = (List<PermissoesLocacao>) ((UISelectItems) child).getValue();
                int id=Integer.parseInt(value);
                for(PermissoesLocacao permissoesLocacao: items){
                	if(permissoesLocacao.getPerLocId()==id){
                		return permissoesLocacao;
                	}
                }
            }  
        }  
        return null;  
    }  
  
    @Override  
    public String getAsString(FacesContext context, UIComponent component,  
            Object value) {  
    	if (value != null) {
    		PermissoesLocacao pessoa = (PermissoesLocacao) value;
			return String.valueOf(pessoa.getPerLocId());
		}
		return "";
	/*	return Integer.toString(++counter);  */
    }  
	
}
