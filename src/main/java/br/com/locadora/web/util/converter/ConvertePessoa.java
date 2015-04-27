package br.com.locadora.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.rn.PessoaRN;

@FacesConverter(value="convertePessoa" )
public class ConvertePessoa implements Converter{
	private boolean primera=false;
	private Integer cod=0;
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			PessoaRN pessoaRN = new PessoaRN();
			Pessoa pessoa=new Pessoa();
			if(cod==codigo){
				return pessoa;
			}
			cod=codigo;
			try {
				
				pessoa=pessoaRN.carregar(codigo);
				return pessoa;
			} catch (Exception e) {
				throw new ConverterException("Não foi possóvel encontrar a categoria de código, problema no converterPessoa " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Pessoa pessoa = (Pessoa) value;
			return String.valueOf(pessoa.getPesId());
		}
		return "";
	}

	public boolean isPrimera() {
		return primera;
	}

	public void setPrimera(boolean primera) {
		this.primera = primera;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}
	
}
