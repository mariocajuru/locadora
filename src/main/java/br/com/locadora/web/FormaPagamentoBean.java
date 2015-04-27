package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.FormaPagamento;
import br.com.locadora.rn.FormaPagamentoRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "formaPagamentoBean")
@ViewScoped
public class FormaPagamentoBean implements Serializable{
	/**
	 * 
	 */
	@Getter	private static final long serialVersionUID = 8005420757736919788L;
	@Getter @Setter private FormaPagamento formaPagamento=new FormaPagamento();
	@Getter @Setter private List<FormaPagamento> listaFormaPagamentos;
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public FormaPagamentoBean() {
		carregar();
		carregarLista();
	}

	public void carregar(){
		int formaPagamentoID = this.contextoBean.getParametro("id", -1);
		this.formaPagamento=new FormaPagamento();
		if (formaPagamentoID > 0) {
			FormaPagamento formaPagamentoCarregado=new FormaPagamentoRN().carregar(formaPagamentoID);			
			if (formaPagamentoCarregado != null){
			this.formaPagamento = formaPagamentoCarregado;
			this.alteracao=true;
			}
		}
	}
	public void carregarLista(){
		this.listaFormaPagamentos= new FormaPagamentoRN().listar();
	}
	public void salvar() {
		
		FormaPagamentoRN formaPagamentoRN=new FormaPagamentoRN();
		if(alteracao){
			formaPagamentoRN.salvar(this.formaPagamento);	
			carregar();
			carregarLista();
			this.contextoBean.redirecionarParaPagina("admin/imovel-proprietario/consulta.jsf");
			return;
		}
		FormaPagamento formaPagamento=formaPagamentoRN.buscarFormaPagamento(this.formaPagamento.getForPagNome());
		if(formaPagamento!=null){			
			this.contextoBean.mostrarAviso("Esta forma de pagamento já existe");
			return;
		}
		formaPagamentoRN.salvar(this.formaPagamento);	
		carregar();
		carregarLista();
		this.contextoBean.redirecionarParaPagina("admin/imovel-proprietario/consulta.jsf");
	}

	public void excluir() {
		if (this.formaPagamento.getForPagId() <= 0) {
			this.contextoBean.mostrarAviso("Essa forma de pagamento ainda não foi salvo!");
			return;
		}
		
		boolean resposta=new FormaPagamentoRN().excluir(this.formaPagamento);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Forma de pagamento não excluída, esse forma de pagamento está ligada a um registro.");
			return;
		}
	}
}
