package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Tipoimovel;
import br.com.locadora.rn.TipoImovelRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "tipoImovelBean")
@ViewScoped
public class TipoImovelBean implements Serializable {

	@Getter private static final long serialVersionUID = 8307527555933024151L;
	
	@Getter @Setter private Tipoimovel       tipoImovel;
	@Getter @Setter private List<Tipoimovel> listaTipoImoveis;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public TipoImovelBean() {
		tipoImovel = new Tipoimovel();
		
		carregarListas();
		
		carregar();
	}
	
	public void carregarListas() {
		this.listaTipoImoveis = new TipoImovelRN().listar();
	}
	
	public void carregar() {
		int tipoID = this.contextoBean.getParametro("id", -1);
		
		if (tipoID > 0) {
			
			Tipoimovel tipoCarregado = new TipoImovelRN().carregar(tipoID);
			
			if (tipoCarregado == null)
				tipoCarregado = new Tipoimovel();
			
			tipoImovel = tipoCarregado;
		}
	}

	public void salvar() {
		new TipoImovelRN().salvar(tipoImovel);	
		this.contextoBean.redirecionarParaPagina("admin/imovel-tipo/consulta.jsf");
	}

	public void excluir() {
		if (tipoImovel.getTipId() <= 0) {
			this.contextoBean.mostrarAviso("Esse registro ainda não foi salvo!");
			return;
		}
		
		boolean resposta=new TipoImovelRN().excluir(tipoImovel);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Tipo de imóvel não excluído, esse tipo está ligada a um registro.");
			return;
		}
	}

}