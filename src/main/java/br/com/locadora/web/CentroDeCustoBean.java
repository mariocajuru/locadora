package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.CentroDeCusto;
import br.com.locadora.rn.CentroDeCustoRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "centroDeCustoBean")
@ViewScoped
public class CentroDeCustoBean implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = 3817624179364785542L;
	@Getter @Setter private CentroDeCusto centroDeCusto=new CentroDeCusto();
	@Getter @Setter private List<CentroDeCusto> listaCentroDeCusto;
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public CentroDeCustoBean() {
		carregarListas();
		carregar();
	}
	
	public void carregar(){
		int centroDeCustoID = this.contextoBean.getParametro("id", -1);
		this.centroDeCusto=new CentroDeCusto();
		if (centroDeCustoID > 0) {
			CentroDeCusto centroCustCarregado=new CentroDeCustoRN().carregar(centroDeCustoID);			
			if (centroCustCarregado != null){
				this.centroDeCusto = centroCustCarregado;
				this.alteracao=true;
			}
		}
	}
	public void carregarListas(){
		this.listaCentroDeCusto=new CentroDeCustoRN().listar();
	}
	
public void salvar() {
		
	CentroDeCustoRN centroDeCustoRN=new CentroDeCustoRN();
		CentroDeCusto centDeCus=centroDeCustoRN.buscarPorCentroDeCusto(this.centroDeCusto.getCenCusNome());
		if(centDeCus!=null){
			if(alteracao){
				centroDeCustoRN.salvar(centDeCus);	
				carregar();
				carregarListas();
				this.contextoBean.redirecionarParaPagina("admin/locacao/contas-locacao/custo/consulta.jsf");
				return;
			}
			this.contextoBean.mostrarAviso("Centro de Custo já existe");
			return;
		}
		centroDeCustoRN.salvar(this.centroDeCusto);	
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/locacao/contas-locacao/custo/consulta.jsf");
	}

	public void excluir() {
		if (this.centroDeCusto.getCenCusId() <= 0) {
			this.contextoBean.mostrarAviso("Centro de Custo ainda não foi salvo!");
			return;
		}
		boolean resposta=new CentroDeCustoRN().excluir(this.centroDeCusto);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Centro de Custo não excluído, esse custo está ligado a uma locação.");
			return;
		}
	}
}
