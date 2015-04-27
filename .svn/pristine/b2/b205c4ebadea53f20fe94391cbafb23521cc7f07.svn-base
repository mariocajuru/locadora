package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.SeguroIncendioImovelResidencial;
import br.com.locadora.rn.SeguroIncendioImovelResidencialRN;
import br.com.locadora.web.util.ContextoUtil;
@ManagedBean(name = "seguroIncendioImovelResidencialBean")
@ViewScoped
public class SeguroIncendioImovelResidencialBean implements Serializable {

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = -6492853883954769208L;
	@Getter @Setter	private SeguroIncendioImovelResidencial seguroIncendioImovelResidencial=new SeguroIncendioImovelResidencial();
	@Getter @Setter	private List<SeguroIncendioImovelResidencial> listaSeguroIncendioImovelResidencials=null;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public SeguroIncendioImovelResidencialBean() {
		carregarListas();
		carregar();
	}
	public void carregarListas() {
		this.listaSeguroIncendioImovelResidencials = new SeguroIncendioImovelResidencialRN().listar();
	}

	public void carregar() {
		int seguroIncendioImovelResidencialID = this.contextoBean.getParametro("id", -1);

		if (seguroIncendioImovelResidencialID > 0) {

			SeguroIncendioImovelResidencial seguroIncendioImovelResidencialCarregado = new SeguroIncendioImovelResidencialRN().carregar(seguroIncendioImovelResidencialID);

			if (seguroIncendioImovelResidencialCarregado == null)
				seguroIncendioImovelResidencialCarregado = new SeguroIncendioImovelResidencial();

			this.seguroIncendioImovelResidencial = seguroIncendioImovelResidencialCarregado;
		}
	}

	public void salvar() {

		SeguroIncendioImovelResidencialRN seguroIncendioImovelResidencialRN=new SeguroIncendioImovelResidencialRN();
/*		SeguroIncendioImovelResidencial seg=seguroIncendioImovelResidencialRN.buscarPorSeguroImovelResidencial(String.valueOf(this.seguroIncendioImovelResidencial.getSegIncImoResValorVenal()));
		if(seg!=null){
			super.mostrarAviso("Esse valor venal já existe");
			return;
		}*/
		seguroIncendioImovelResidencialRN.salvar(this.seguroIncendioImovelResidencial);	
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/locacao/seguro-incendio/residencial/consulta.jsf");
	}

	public void excluir() {
		if (this.seguroIncendioImovelResidencial.getSegIncImoResId() <= 0) {
			this.contextoBean.mostrarAviso("Esse seguro de incendio de imóvel residencial ainda não foi salvo!");
			return;
		}

		new SeguroIncendioImovelResidencialRN().excluir(this.seguroIncendioImovelResidencial);
		this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;
	}

}
