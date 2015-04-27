package br.com.locadora.web;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.CentroDeCusto;
import br.com.locadora.modelo.ContasFinanceiro;
import br.com.locadora.modelo.PlanoDeContas;
import br.com.locadora.modelo.GrupoDeContas;
import br.com.locadora.rn.CentroDeCustoRN;
import br.com.locadora.rn.ContasFinanceiroRN;
import br.com.locadora.rn.GrupoDeContasRN;
import br.com.locadora.rn.PlanoDeContasRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "grupoDeContasBean")
@ViewScoped
public class GrupoDeContasBean implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = 7720686513783591774L;
	@Getter @Setter private GrupoDeContas grupoDeContas=new GrupoDeContas();
	@Getter @Setter private CentroDeCusto centroDeCusto=new CentroDeCusto();
	@Getter @Setter private PlanoDeContas planoDeContasCreditoValor=new PlanoDeContas();
	@Getter @Setter private PlanoDeContas planoDeContasDebitoValor=new PlanoDeContas();
	@Getter @Setter private PlanoDeContas planoDeContasCreditoMulta=new PlanoDeContas();
	@Getter @Setter private PlanoDeContas planoDeContasDebitoMulta=new PlanoDeContas();
	@Getter @Setter private PlanoDeContas planoDeContasCreditoJuros=new PlanoDeContas();
	@Getter @Setter private PlanoDeContas planoDeContasDebitoJuros=new PlanoDeContas();
	@Getter @Setter private ContasFinanceiro contasFinanceiroCredito=new ContasFinanceiro();
	@Getter @Setter private ContasFinanceiro contasFinanceiroDebito=new ContasFinanceiro();
	
	@Getter @Setter private List<GrupoDeContas> listaGrupoDeContas;
	@Getter @Setter private List<PlanoDeContas> listaPlanoDeContasCreditoValor;
	@Getter @Setter private List<PlanoDeContas> listaPlanoDeContasDebitoValor;
	
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public GrupoDeContasBean() {
		carregarListas();
		carregar();
	}

	public void carregar(){
		int grupoDeContasID = this.contextoBean.getParametro("id", -1);
		this.grupoDeContas=new GrupoDeContas();
		this.centroDeCusto=new CentroDeCusto();
		if (grupoDeContasID > 0) {
			GrupoDeContas grupoDeContasCarregado=new GrupoDeContasRN().carregar(grupoDeContasID);			
			if (grupoDeContasCarregado != null){
				this.grupoDeContas = grupoDeContasCarregado;
				this.centroDeCusto=new CentroDeCustoRN().carregar(this.grupoDeContas.getCentroDeCusto().getCenCusId());
				
				PlanoDeContasRN planoDeContasRN=new PlanoDeContasRN();
				
				// está sendo colocado somente o id no objeto , porque quando irei altera-lo não é retornado com o id novo e sim com o id antigo deste obejto que está sendo add.
				this.planoDeContasCreditoValor=new PlanoDeContas();
				this.planoDeContasCreditoValor.setPlaConId((planoDeContasRN.carregarPlanoDeContasCreditoValor(this.grupoDeContas)).getPlaConId());
				
				this.planoDeContasDebitoValor=new PlanoDeContas();
				this.planoDeContasDebitoValor.setPlaConId((planoDeContasRN.carregarPlanoDeContasDebitoValor(this.grupoDeContas)).getPlaConId());
				
				this.planoDeContasCreditoJuros=new PlanoDeContas();
				this.planoDeContasCreditoJuros.setPlaConId((planoDeContasRN.carregarPlanoDeContasCreditoJuros(this.grupoDeContas)).getPlaConId());
				
				this.planoDeContasDebitoJuros=new PlanoDeContas();
				this.planoDeContasDebitoJuros.setPlaConId((planoDeContasRN.carregarPlanoDeContasDebitoJuros(this.grupoDeContas)).getPlaConId());
				
				this.planoDeContasDebitoMulta=new PlanoDeContas();
				this.planoDeContasDebitoMulta.setPlaConId((planoDeContasRN.carregarPlanoDeContasDebitoMulta(this.grupoDeContas)).getPlaConId());
				
				//planoDeContasRN.carregarPlanoDeContasCreditoMulta(this.grupoDeContas);
				this.planoDeContasCreditoMulta=new PlanoDeContas();
				this.planoDeContasCreditoMulta.setPlaConId((planoDeContasRN.carregarPlanoDeContasCreditoMulta(this.grupoDeContas)).getPlaConId());
				
				ContasFinanceiroRN contasFinanceiroRN=new ContasFinanceiroRN();
				this.contasFinanceiroCredito=contasFinanceiroRN.carregarContasFinanceiroCredito(this.grupoDeContas);
				this.contasFinanceiroDebito=contasFinanceiroRN.carregarContasFinanceiroDebito(this.grupoDeContas);
				this.alteracao=true;
			}
		}
	}
	public void carregarListas(){
		this.listaGrupoDeContas=new GrupoDeContasRN().listar();
		PlanoDeContasRN planoDeContasRN=new PlanoDeContasRN();
		this.listaPlanoDeContasCreditoValor=planoDeContasRN.listarPorCredito();
		this.listaPlanoDeContasDebitoValor=planoDeContasRN.listarPorDebito();
	}

	public void salvar() {
		if(this.centroDeCusto.getCenCusId()==0){
			this.contextoBean.mostrarErro("Selecione um centro de custo");
			return;
		}

		this.grupoDeContas.setCentroDeCusto(this.centroDeCusto);

		GrupoDeContasRN grupoDeContasRN=new GrupoDeContasRN();
		ContasFinanceiroRN contasFinanceiroRN=new ContasFinanceiroRN();
		
		if(alteracao){
			//Deletando todas a dependencias(List) do grupoDeContas
			this.grupoDeContas.setGrupoContasJuros(new HashSet<PlanoDeContas>());
			this.grupoDeContas.setGrupoContasMultas(new HashSet<PlanoDeContas>());
			this.grupoDeContas.setGrupoContasValorOriginal(new HashSet<PlanoDeContas>());

			this.grupoDeContas.setContasFinanceirosCredito(new HashSet<ContasFinanceiro>());
			this.grupoDeContas.setContasFinanceirosDebito(new HashSet<ContasFinanceiro>());
			
			grupoDeContasRN.salvar(this.grupoDeContas);	
			
			//deletando contas financeiro do grupo de contas antigos
			ContasFinanceiro financeiroCredito=new ContasFinanceiro();
			financeiroCredito=contasFinanceiroRN.carregarContasFinanceiroCredito(this.grupoDeContas);			
			ContasFinanceiro financeiroDebito=new ContasFinanceiro();
			financeiroDebito=contasFinanceiroRN.carregarContasFinanceiroDebito(this.grupoDeContas);
			
			financeiroCredito.setGrupoFinanceiroCredito(new HashSet<GrupoDeContas>());
			financeiroDebito.setGrupoFinanceiroDebito(new HashSet<GrupoDeContas>());
			
			contasFinanceiroRN.salvar(financeiroCredito);
			contasFinanceiroRN.salvar(financeiroDebito);
			
			Set<PlanoDeContas> planoDeContaJuros=new HashSet<PlanoDeContas>();
			Set<PlanoDeContas> planoDeContaMulta=new HashSet<PlanoDeContas>();
			Set<PlanoDeContas> planoDeContaValor=new HashSet<PlanoDeContas>();
			
			PlanoDeContas creditoJuros=new PlanoDeContas();
			creditoJuros.setPlaConId(this.planoDeContasCreditoJuros.getPlaConId());
			planoDeContaJuros.add(creditoJuros);
			
			PlanoDeContas debitoJuros=new PlanoDeContas();
			debitoJuros.setPlaConId(this.planoDeContasDebitoJuros.getPlaConId());
			planoDeContaJuros.add(debitoJuros);
			
			this.grupoDeContas.setGrupoContasJuros(planoDeContaJuros);
			
			PlanoDeContas creditoMulta=new PlanoDeContas();
			creditoMulta.setPlaConId(this.planoDeContasCreditoMulta.getPlaConId());
			planoDeContaMulta.add(creditoMulta);
			
			PlanoDeContas debitoMulta=new PlanoDeContas();
			debitoMulta.setPlaConId(this.planoDeContasDebitoMulta.getPlaConId());
			planoDeContaMulta.add(debitoMulta);
			
			this.grupoDeContas.setGrupoContasMultas(planoDeContaMulta);
			
			PlanoDeContas creditoValor=new PlanoDeContas();
			creditoValor.setPlaConId(this.planoDeContasCreditoValor.getPlaConId());
			planoDeContaValor.add(creditoValor);
			
			PlanoDeContas debitoValor=new PlanoDeContas();
			debitoValor.setPlaConId(this.planoDeContasDebitoValor.getPlaConId());
			planoDeContaValor.add(debitoValor);
			
			this.grupoDeContas.setGrupoContasValorOriginal(planoDeContaValor);
			
			Set<ContasFinanceiro> contasFinanceiroC=new HashSet<ContasFinanceiro>();
			Set<ContasFinanceiro> contasFinanceiroD=new HashSet<ContasFinanceiro>();
			
			ContasFinanceiro finCredito=new ContasFinanceiro();
			finCredito.setConFinId(this.contasFinanceiroCredito.getConFinId());
			ContasFinanceiro finDebito=new ContasFinanceiro();
			finDebito.setConFinId(this.contasFinanceiroDebito.getConFinId());
			
			contasFinanceiroC.add(finCredito);
			contasFinanceiroD.add(finDebito);
			
			this.grupoDeContas.setContasFinanceirosCredito(contasFinanceiroC);
			this.grupoDeContas.setContasFinanceirosDebito(contasFinanceiroD);
			
			//carregando ContasFinanceiros, o incremento da tabela composta grupoFinanceiroCredito e grupoFinanceiroDebito está do lado ContasFinanceiros
			this.contasFinanceiroCredito=contasFinanceiroRN.carregar(this.contasFinanceiroCredito.getConFinId());
			this.contasFinanceiroDebito=contasFinanceiroRN.carregar(this.contasFinanceiroDebito.getConFinId());
			
		
			//para poder add na Set<>, obrigatoriamente somente um objeto com o ID é aceitavel
			
			GrupoDeContas contas=new GrupoDeContas();
			contas.setGruConId(this.grupoDeContas.getGruConId());
			Set<GrupoDeContas> grupoDeContasLista=new HashSet<GrupoDeContas>();			
			grupoDeContasLista.add(contas);
			
			grupoDeContasRN.salvar(this.grupoDeContas);	
			
			//salvar contasFinanceiro
			this.contasFinanceiroCredito.setGrupoFinanceiroCredito(grupoDeContasLista);
			this.contasFinanceiroDebito.setGrupoFinanceiroDebito(grupoDeContasLista);
			contasFinanceiroRN.salvar(this.contasFinanceiroCredito);
			contasFinanceiroRN.salvar(this.contasFinanceiroDebito);
			
			carregar();
			carregarListas();
			this.contextoBean.redirecionarParaPagina("admin/locacao/contas-locacao/grupo/consulta.jsf");
			return;
		}
		
		Set<PlanoDeContas> planoDeContaJuros=new HashSet<PlanoDeContas>();
		Set<PlanoDeContas> planoDeContaMulta=new HashSet<PlanoDeContas>();
		Set<PlanoDeContas> planoDeContaValor=new HashSet<PlanoDeContas>();
		
		planoDeContaJuros.add(this.planoDeContasCreditoJuros);
		planoDeContaJuros.add(this.planoDeContasDebitoJuros);
		this.grupoDeContas.setGrupoContasJuros(planoDeContaJuros);
		
		planoDeContaMulta.add(this.planoDeContasCreditoMulta);
		planoDeContaMulta.add(this.planoDeContasDebitoMulta);
		this.grupoDeContas.setGrupoContasMultas(planoDeContaMulta);
		
		planoDeContaValor.add(this.planoDeContasCreditoValor);
		planoDeContaValor.add(this.planoDeContasDebitoValor);
		this.grupoDeContas.setGrupoContasValorOriginal(planoDeContaValor);
		
		Set<ContasFinanceiro> contasFinanceiroC=new HashSet<ContasFinanceiro>();
		Set<ContasFinanceiro> contasFinanceiroD=new HashSet<ContasFinanceiro>();
		
		ContasFinanceiro conFinCredito =new ContasFinanceiro();
		ContasFinanceiro conFinDebito=new ContasFinanceiro();
		conFinCredito.setConFinId(this.contasFinanceiroCredito.getConFinId());
		conFinDebito.setConFinId(this.contasFinanceiroDebito.getConFinId());
		
		contasFinanceiroC.add(conFinCredito);
		contasFinanceiroD.add(conFinDebito);
		
		this.grupoDeContas.setContasFinanceirosCredito(contasFinanceiroC);
		this.grupoDeContas.setContasFinanceirosDebito(contasFinanceiroD);
		
		//carregando ContasFinanceiros, o incremento da tabela composta grupoFinanceiroCredito e grupoFinanceiroDebito está do lado ContasFinanceiros
		this.contasFinanceiroCredito=contasFinanceiroRN.carregar(this.contasFinanceiroCredito.getConFinId());
		this.contasFinanceiroDebito=contasFinanceiroRN.carregar(this.contasFinanceiroDebito.getConFinId());
		
		Set<GrupoDeContas> grupoDeContasLista=new HashSet<GrupoDeContas>();
		
		grupoDeContasLista.add(this.grupoDeContas);
		
		grupoDeContasRN.salvar(this.grupoDeContas);	
		
		//salvar contasFinanceiro
		this.contasFinanceiroCredito.setGrupoFinanceiroCredito(grupoDeContasLista);
		this.contasFinanceiroDebito.setGrupoFinanceiroDebito(grupoDeContasLista);
		contasFinanceiroRN.salvar(this.contasFinanceiroCredito);
		contasFinanceiroRN.salvar(this.contasFinanceiroDebito);
		
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/locacao/contas-locacao/grupo/consulta.jsf");
	}

	public void excluir() {
		if (this.grupoDeContas.getGruConId() <= 0) {
			this.contextoBean.mostrarAviso("Essa grupo de contas ainda não foi salvo!");
			return;
		}
		boolean resposta=new GrupoDeContasRN().excluir(this.grupoDeContas);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
			return;}else{
				this.contextoBean.mostrarErro("Grupo de contas não excluído, esse conta está ligado a uma locação.");
				return;
			}
	}
}
