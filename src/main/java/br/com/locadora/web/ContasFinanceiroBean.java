package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.ContasFinanceiro;
import br.com.locadora.rn.ContasFinanceiroRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "contasFinanceiroBean")
@ViewScoped
public class ContasFinanceiroBean implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = 7257741626346737725L;
	@Getter @Setter private ContasFinanceiro contasFinanceiro=new ContasFinanceiro();
	@Getter @Setter private List<ContasFinanceiro> listaContasFinanceiros;
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public ContasFinanceiroBean() {
		carregarListas();
		carregar();
	}
	
	public void carregar(){
		int contasFinanceiroID = this.contextoBean.getParametro("id", -1);
		this.contasFinanceiro=new ContasFinanceiro();
		if (contasFinanceiroID > 0) {
			ContasFinanceiro contasFinanceiroCarregado=new ContasFinanceiroRN().carregar(contasFinanceiroID);			
			if (contasFinanceiroCarregado != null){
				this.contasFinanceiro = contasFinanceiroCarregado;
				this.alteracao=true;
			}
		}
	}
	public void carregarListas(){
		this.listaContasFinanceiros=new ContasFinanceiroRN().listar();
	}
	
public void salvar() {
		
	ContasFinanceiroRN contasCreditoRN=new ContasFinanceiroRN();
		ContasFinanceiro conFin=contasCreditoRN.buscarContasFinanceiro(this.contasFinanceiro.getConFinNome());
		if(conFin!=null){
			if(alteracao){
				contasCreditoRN.salvar(conFin);	
				carregar();
				carregarListas();
				this.contextoBean.redirecionarParaPagina("admin/locacao/contas-locacao/financeiro/consulta.jsf");
				return;
			}
			this.contextoBean.mostrarAviso("Esse Conta de financeira já existe");
			return;
		}
		contasCreditoRN.salvar(this.contasFinanceiro);	
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/locacao/contas-locacao/financeiro/consulta.jsf");
	}

	public void excluir() {
		if (this.contasFinanceiro.getConFinId() <= 0) {
			this.contextoBean.mostrarAviso("Essa conta financeiro ainda não foi salvo!");
			return;
		}
		boolean resposta=new ContasFinanceiroRN().excluir(this.contasFinanceiro);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Conta financeiro não excluído, esse conta está ligado a uma locação.");
			return;
		}
	}
}
