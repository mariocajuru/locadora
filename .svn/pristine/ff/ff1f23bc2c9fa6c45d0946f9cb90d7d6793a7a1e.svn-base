package br.com.locadora.web.util.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.locadora.modelo.SituacaoLocacao;

@FacesConverter(value="situacaoLocacaoConverter")
public class ConverteSituacaoLocacao implements Converter{


    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        return ((SituacaoLocacao) object).getSitLocId()+"";
    }

    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        List<SituacaoLocacao> languages = (List<SituacaoLocacao>) context.getApplication().evaluateExpressionGet(context, "#{situacaoLocacaoBean.listaSituacaoLocacoes}", List.class);

        for (SituacaoLocacao language : languages) {
            if (language.getSitLocId()+""==submittedValue) {
                return language;
            }
        }

        return null;
    }

}
