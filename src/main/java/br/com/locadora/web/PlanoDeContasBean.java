package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.PlanoDeContas;
import br.com.locadora.rn.PlanoDeContasRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "planoDeContasBean")
@ViewScoped
public class PlanoDeContasBean implements Serializable{

	/**
	 * 
	 */
	@Getter	private static final long serialVersionUID = -1701648974234746513L;
	@Getter @Setter private PlanoDeContas planoDeContas=new PlanoDeContas();
	@Getter @Setter private List<PlanoDeContas> listaPlanoDeContas;
	@Getter @Setter private String tipoPlano=new String("");// tipo do plano 1 Debito ou 2 para Credito
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();


	public PlanoDeContasBean() {
		carregarListas();
		carregar();
	}

	public void carregar(){
		int planoDeContasID = this.contextoBean.getParametro("id", -1);
		this.planoDeContas=new PlanoDeContas();
		this.tipoPlano=new String();
		if (planoDeContasID > 0) {
			PlanoDeContas plaDeContas=new PlanoDeContasRN().carregar(planoDeContasID);			
			if (plaDeContas != null){
				this.planoDeContas = plaDeContas;
				this.alteracao=true;
				if(this.planoDeContas.getPlaConCredito()){
					this.tipoPlano="2";
				}else{
					this.tipoPlano="1";
				}
			}
		}
	}
	public void carregarListas(){
		this.listaPlanoDeContas=new PlanoDeContasRN().listar();
	}

	public void salvar() {
		if(this.tipoPlano==null || this.tipoPlano==""){
			this.contextoBean.mostrarErro("Selecione o tipo de plano de contas, débito ou crédito");
			return;
		}
		if(this.tipoPlano.equals("1")){
			this.planoDeContas.setPlaConDebito(true);
			this.planoDeContas.setPlaConCredito(false);
		}
		if(this.tipoPlano.equals("2")){
			this.planoDeContas.setPlaConDebito(false);
			this.planoDeContas.setPlaConCredito(true);
		}

		PlanoDeContasRN planoDeContasRN=new PlanoDeContasRN();
		if(alteracao){
			planoDeContasRN.salvar(this.planoDeContas);	
			carregar();
			carregarListas();
			this.contextoBean.redirecionarParaPagina("admin/locacao/contas-locacao/plano/consulta.jsf");
			return;
		}
		planoDeContasRN.salvar(this.planoDeContas);	
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/locacao/contas-locacao/plano/consulta.jsf");
	}

	public void excluir() {
		if (this.planoDeContas.getPlaConId() <= 0) {
			this.contextoBean.mostrarAviso("Essa plano de contas ainda não foi salvo!");
			return;
		}
		boolean resposta=new PlanoDeContasRN().excluir(this.planoDeContas);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
			return;}else{
				this.contextoBean.mostrarErro("Plano de contas não excluído, esse conta está ligado a uma locação.");
				return;
			}
	}
}
