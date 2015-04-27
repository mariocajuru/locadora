package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.locadora.modelo.DestinacaoLocacao;
import br.com.locadora.rn.DestinacaoLocacaoRN;
import br.com.locadora.web.util.ContextoUtil;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "destinacaoLocacaoBean")
@ViewScoped
public class DestinacaoLocacaoBean implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = 4487433469648691090L;
	@Getter @Setter private DestinacaoLocacao destinacaoLocacao=new DestinacaoLocacao();
	@Getter @Setter private List<DestinacaoLocacao> listaDestinacaoLocacao;
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public DestinacaoLocacaoBean() {
		carregarListas();
		carregar();
	}
	public void carregar(){
		int destinacaoLocacaoID = this.contextoBean.getParametro("id", -1);
		this.destinacaoLocacao=new DestinacaoLocacao();
		if (destinacaoLocacaoID > 0) {
			DestinacaoLocacao destLocCarregado=new DestinacaoLocacaoRN().carregar(destinacaoLocacaoID);			
			if (destLocCarregado != null){
				this.destinacaoLocacao = destLocCarregado;
				this.alteracao=true;
			}
		}
	}
	public void carregarListas(){
		this.listaDestinacaoLocacao=new DestinacaoLocacaoRN().listar();
	}
	
public void salvar() {
		
		DestinacaoLocacaoRN destinacaoLocacaoRN=new DestinacaoLocacaoRN();
	/*	DestinacaoLocacao des=destinacaoLocacaoRN.buscarPorDestinacaoLocacao(this.destinacaoLocacao.getDesLocNome());
		if(des!=null){
			if(alteracao){
				destinacaoLocacaoRN.salvar(des);	
				carregar();
				carregarListas();
				super.redirecionarParaPagina("admin/locacao/destinacao-locacao/consulta.jsf");
				return;
			}
			super.mostrarAviso("Essa destinação de locação já existe");
			return;
		}*/
		destinacaoLocacaoRN.salvar(this.destinacaoLocacao);	
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/locacao/destinacao-locacao/consulta.jsf");
	}

	public void excluir() {
		if (this.destinacaoLocacao.getDesLocId() <= 0) {
			this.contextoBean.mostrarAviso("Esse registro ainda não foi salvo!");
			return;
		}
		boolean resposta=new DestinacaoLocacaoRN().excluir(this.destinacaoLocacao);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Destinação locação não excluído, essa destinação está ligado a um registro.");
			return;
		}
	}
}
