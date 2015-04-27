package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.IndicesReajustes;
import br.com.locadora.modelo.ValorIndiceReajuste;
import br.com.locadora.rn.IndicesReajustesRN;
import br.com.locadora.rn.ValorIndiceReajusteRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "indicesReajustesBean")
@ViewScoped
public class IndicesReajustesBean implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = -8263755930023582030L;
	
	@Getter @Setter private IndicesReajustes indicesReajustes=new IndicesReajustes();
	@Getter @Setter private ValorIndiceReajuste valorIndiceReajuste=new ValorIndiceReajuste();
	@Getter @Setter private List<IndicesReajustes> listaIndicesReajustes;
	@Getter @Setter private List<ValorIndiceReajuste> listaValorIndiceReajuste;
	@Getter @Setter private String tipoReajuste=new String("");// tipo do reajuste 1 indice ou 2 para valor
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public IndicesReajustesBean() {
		carregarListas();
		carregar();
	}
	public void carregar(){
		int indicesReajustesID = this.contextoBean.getParametro("id", -1);
		this.indicesReajustes=new IndicesReajustes();
		this.valorIndiceReajuste=new ValorIndiceReajuste();
		this.tipoReajuste=new String();
		if (indicesReajustesID > 0) {
			IndicesReajustes  indReaCarregado=new IndicesReajustesRN().carregar(indicesReajustesID);			
			if (indReaCarregado != null){
				this.indicesReajustes = indReaCarregado;
				this.listaValorIndiceReajuste=new ValorIndiceReajusteRN().listarPorIndicesReajuste(this.indicesReajustes);
				this.alteracao=true;
			}
		}
	}
	public void carregarListas(){
		this.listaIndicesReajustes=new  IndicesReajustesRN().listar();
		this.listaValorIndiceReajuste=new ArrayList<ValorIndiceReajuste>();
	}
	
public void salvar() {
		
	IndicesReajustesRN indicesReajustesRN=new IndicesReajustesRN();
		IndicesReajustes indRea=indicesReajustesRN.buscarPorIndicesReajustes(this.indicesReajustes.getIndReaNome());
		if(indRea!=null){
			if(alteracao){
				indicesReajustesRN.salvar(indRea);	
				carregar();
				carregarListas();
				this.contextoBean.redirecionarParaPagina("admin/locacao/indices-reajuste/consulta.jsf");
				return;
			}
			this.contextoBean.mostrarAviso("Esse Indice reajuste já existe");
			return;
		}
		indicesReajustesRN.salvar(this.indicesReajustes);	
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/locacao/indices-reajuste/consulta.jsf");
	}

	public void excluir() {
		if (this.indicesReajustes.getIndReaId() <= 0) {
			this.contextoBean.mostrarAviso("Esse Indice ainda não foi salvo!");
			return;
		}
		boolean resposta=new IndicesReajustesRN().excluir(this.indicesReajustes);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Indices de reajustes de locação não excluído, esse indice está ligado a uma locação.");
			return;
		}
	}
	
	public void adicionarReajuste(){
		
		ValorIndiceReajusteRN valorIndiceReajusteRN=new ValorIndiceReajusteRN();
		
		if(this.tipoReajuste==null || this.tipoReajuste==""){
			this.contextoBean.mostrarErro("Selecione o tipo de reajuste! Indice ou valor");
			return;
		}
		
		if(this.tipoReajuste.equals("1")){
			this.valorIndiceReajuste.setValIndReaIndice(true);
			this.valorIndiceReajuste.setValIndReaMoeda(false);
			this.valorIndiceReajuste.setIndicesReajustes(this.indicesReajustes);
			valorIndiceReajusteRN.salvar(this.valorIndiceReajuste);
			this.valorIndiceReajuste=new ValorIndiceReajuste();
			this.tipoReajuste=new String();
			this.listaValorIndiceReajuste= valorIndiceReajusteRN.listarPorIndicesReajuste(this.indicesReajustes);
			return;
		}
		if(this.tipoReajuste.equals("2")){
			this.valorIndiceReajuste.setValIndReaMoeda(true);
			this.valorIndiceReajuste.setValIndReaIndice(false);
			this.valorIndiceReajuste.setIndicesReajustes(this.indicesReajustes);
			valorIndiceReajusteRN.salvar(this.valorIndiceReajuste);
			this.valorIndiceReajuste=new ValorIndiceReajuste();
			this.tipoReajuste=new String();
			this.listaValorIndiceReajuste= valorIndiceReajusteRN.listarPorIndicesReajuste(this.indicesReajustes);
			return;
		}
	}
	
	public void excluirValorIndiceReajuste(){
		ValorIndiceReajusteRN valorIndiceReajusteRN=new ValorIndiceReajusteRN();
		valorIndiceReajusteRN.excluir(this.valorIndiceReajuste);
		this.contextoBean.mostrarAviso("Excluído com sucesso");
		
		this.valorIndiceReajuste=new ValorIndiceReajuste();
		this.listaValorIndiceReajuste= valorIndiceReajusteRN.listarPorIndicesReajuste(this.indicesReajustes);		
	}
}
