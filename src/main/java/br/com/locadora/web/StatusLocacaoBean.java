package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.StatusLocacao;
import br.com.locadora.rn.StatusLocacaoRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "statusLocacaoBean")
@ViewScoped
public class StatusLocacaoBean implements Serializable{
	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = -7130668564342179274L;
	/**
	 * 
	 */
	@Getter @Setter private StatusLocacao statusLocacao=new StatusLocacao();
	@Getter @Setter private List<StatusLocacao> listaStatusLocacao;
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public StatusLocacaoBean() {
		carregarListas();
		carregar();
	}
	public void carregar(){
		int statusLocacaoID = this.contextoBean.getParametro("id", -1);
		this.statusLocacao=new StatusLocacao();
		if (statusLocacaoID > 0) {
			StatusLocacao staLocCarregado=new StatusLocacaoRN().carregar(statusLocacaoID);			
			if (staLocCarregado != null){
				this.statusLocacao = staLocCarregado;
				this.alteracao=true;
			}
		}
	}
	public void carregarListas(){
		this.listaStatusLocacao=new StatusLocacaoRN().listar();
	}
	
public void salvar() {
		
		StatusLocacaoRN statusLocacaoRN=new StatusLocacaoRN();
		StatusLocacao staLoc=statusLocacaoRN.buscarPorDestinacaoLocacao(this.statusLocacao.getStaLocNome());
		if(staLoc!=null){
			if(alteracao){
				statusLocacaoRN.salvar(staLoc);	
				carregar();
				carregarListas();
				this.contextoBean.redirecionarParaPagina("admin/locacao/status-locacao/consulta.jsf");
				return;
			}
			this.contextoBean.mostrarAviso("Esse Status de locação já existe");
			return;
		}
		statusLocacaoRN.salvar(this.statusLocacao);	
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/locacao/status-locacao/consulta.jsf");
	}

	public void excluir() {
		if (this.statusLocacao.getStaLocId() <= 0) {
			this.contextoBean.mostrarAviso("Esse status ainda não foi salvo!");
			return;
		}
		boolean resposta=new StatusLocacaoRN().excluir(this.statusLocacao);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Status de locação não excluído, esse status está ligado a uma locação.");
			return;
		}
	}
}
