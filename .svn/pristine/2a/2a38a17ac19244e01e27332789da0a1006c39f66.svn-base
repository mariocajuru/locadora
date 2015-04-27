package br.com.renovarsistemas.francisco.controller.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;

import org.primefaces.json.JSONException;

import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.rn.NovaPesquisaRN;
import br.com.renovarsistemas.francisco.util.ParametrosHelper;

@ManagedBean(name = "ResultadoPesquisa")
@ViewScoped
@SuppressWarnings("unused")
public class ResultadoPesquisaBean implements Serializable {
	
	private static final long serialVersionUID = 1797363445245177312L;
	
	@Getter private List<Imovel> resultados;
	@Getter private String       erro;
	
	@PostConstruct
	public void init() {
		try {
			NovaPesquisaRN   pesquisaRN = new NovaPesquisaRN();
			ParametrosHelper parametros = new ParametrosHelper();
			
			String json = parametros.getParametroPost("parameter1", null);
			
			this.resultados = pesquisaRN.pesquisar(json);			
		} catch (JSONException | PesquisaException e) {
			this.resultados = new ArrayList<Imovel>();
			this.erro = e.getMessage();
		}
	}
	
}