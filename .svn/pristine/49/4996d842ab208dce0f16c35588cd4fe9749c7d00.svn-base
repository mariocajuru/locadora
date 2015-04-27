package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.SeguroIncendioImovelComercial;
import br.com.locadora.rn.SeguroIncendioImovelComercialRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "seguroIncendioImovelComercialBean")
@ViewScoped
public class SeguroIncendioImovelComercialBean implements Serializable {

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = 1803797051608541328L;
	@Getter @Setter	private SeguroIncendioImovelComercial seguroIncendioImovelComercial=new SeguroIncendioImovelComercial();
	@Getter @Setter	private List<SeguroIncendioImovelComercial> listaSeguroIncendioImovelComercials=null;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public SeguroIncendioImovelComercialBean() {
		carregarListas();
		carregar();
	}

	public void carregarListas() {
		this.listaSeguroIncendioImovelComercials = new SeguroIncendioImovelComercialRN().listar();
	}

	public void carregar() {
		int seguroIncendioImovelComercialID = this.contextoBean.getParametro("id", -1);

		if (seguroIncendioImovelComercialID > 0) {

			SeguroIncendioImovelComercial seguroIncendioImovelComercialCarregado = new SeguroIncendioImovelComercialRN().carregar(seguroIncendioImovelComercialID);

			if (seguroIncendioImovelComercialCarregado == null)
				seguroIncendioImovelComercialCarregado = new SeguroIncendioImovelComercial();

			this.seguroIncendioImovelComercial = seguroIncendioImovelComercialCarregado;
		}
	}

	public void salvar() {

		SeguroIncendioImovelComercialRN seguroIncendioImovelComercialRN=new SeguroIncendioImovelComercialRN();
		/*SeguroIncendioImovelComercial seg=seguroIncendioImovelComercialRN.buscarPorSeguroImovelComercial(String.valueOf(this.seguroIncendioImovelComercial.getSegIncImoComValorVenal()));
		if(seg!=null){
			super.mostrarAviso("Esse valor venal já existe");
			return;
		}*/
		seguroIncendioImovelComercialRN.salvar(this.seguroIncendioImovelComercial);	
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/locacao/seguro-incendio/comercial/consulta.jsf");
	}

	public void excluir() {
		if (this.seguroIncendioImovelComercial.getSegIncImoComId() <= 0) {
			this.contextoBean.mostrarAviso("Esse seguro de incendio de imóvel comercial ainda não foi salvo!");
			return;
		}

		new SeguroIncendioImovelComercialRN().excluir(this.seguroIncendioImovelComercial);
		this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;
	}

}

