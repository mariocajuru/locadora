package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.FaixaImpostoDeRenda;
import br.com.locadora.modelo.ImpostoDeRenda;
import br.com.locadora.rn.FaixaImpostoDeRendaRN;
import br.com.locadora.rn.ImpostoDeRendaRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "impostoDeRendaBean")
@ViewScoped
public class ImpostoDeRendaBean implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = -3415450329585392239L;
	@Getter @Setter private ImpostoDeRenda impostoDeRenda=new ImpostoDeRenda();
	@Getter @Setter private FaixaImpostoDeRenda faixaImpostoDeRenda=new FaixaImpostoDeRenda();
	@Getter @Setter private List<ImpostoDeRenda> listaImpostoDeRenda=new ArrayList<ImpostoDeRenda>();
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public ImpostoDeRendaBean() {
		carregarListas();
		carregar();
	}
	
	public void carregar(){
		int impostoDeRendaID = this.contextoBean.getParametro("id", -1);
		this.impostoDeRenda=new ImpostoDeRenda();
		this.faixaImpostoDeRenda=new FaixaImpostoDeRenda();
		if (impostoDeRendaID > 0) {
			ImpostoDeRenda impostoDeRenda=new ImpostoDeRendaRN().carregar(impostoDeRendaID);			
			if (impostoDeRenda != null){
				this.impostoDeRenda = impostoDeRenda;				
				this.alteracao=true;
			}
		}
	}
	public void carregarListas(){
		this.listaImpostoDeRenda=new  ImpostoDeRendaRN().listar();
	}
	
public void salvar() {
	
	if(this.impostoDeRenda.getImpRenAno() < 2014){
		this.contextoBean.mostrarErro("O ano tem que ser cima de 2014");
		return;
	}
		
	ImpostoDeRendaRN impostoDeRendaRN=new ImpostoDeRendaRN();
		ImpostoDeRenda impDeRenda=impostoDeRendaRN.buscarPorImpostoDeRenda(this.impostoDeRenda.getImpRenMes(),this.impostoDeRenda.getImpRenAno());
		if(impDeRenda!=null){
			if(alteracao){
				impostoDeRendaRN.salvar(impDeRenda);	
				carregar();
				carregarListas();
				this.contextoBean.redirecionarParaPagina("admin/locacao/imposto-de-renda-locacao/consulta.jsf");
				return;
			}
			this.contextoBean.mostrarAviso("Esse mês e ano de imposto de renda já existe");
			return;
		}
		impostoDeRendaRN.salvar(this.impostoDeRenda);	
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/locacao/imposto-de-renda-locacao/consulta.jsf");
	}

	public void excluir() {
		if (this.impostoDeRenda.getImpRenId() <= 0) {
			this.contextoBean.mostrarAviso("Esse Indice ainda não foi salvo!");
			return;
		}
		boolean resposta=new ImpostoDeRendaRN().excluir(this.impostoDeRenda);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Imposto de renda não excluído, esse indice está ligado a valores cadastrados, não pode ser excluidos.");
			return;
		}
	}
	
	public void adicionarReajuste(){
		if((this.faixaImpostoDeRenda.getFaiImpRenAliquota()==null)||(this.faixaImpostoDeRenda.getFaiImpRenFaixa()=="")||(this.faixaImpostoDeRenda.getFaiImpRenLimiteInferior()==null)||(this.faixaImpostoDeRenda.getFaiImpRenLimiteSuperior()==null)){
			this.contextoBean.mostrarErro("Preencha todos os campos em valores");
			return;
		}
		FaixaImpostoDeRendaRN faixaImpostoDeRendaRN=new FaixaImpostoDeRendaRN();
		this.faixaImpostoDeRenda.setImpostoDeRenda(this.impostoDeRenda);
		faixaImpostoDeRendaRN.salvar(this.faixaImpostoDeRenda);
		this.faixaImpostoDeRenda=new FaixaImpostoDeRenda();
		this.impostoDeRenda=new ImpostoDeRendaRN().carregar(this.impostoDeRenda.getImpRenId());
	}
}
