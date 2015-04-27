package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.PermissoesLocacao;
import br.com.locadora.modelo.SituacaoLocacao;
import br.com.locadora.modelo.SituacaoLocacaoPermissoes;
import br.com.locadora.modelo.SituacaoLocacaoPermissoesId;
import br.com.locadora.modelo.Situacaoimovel;
import br.com.locadora.rn.SituacaoLocacaoPermissoesRN;
import br.com.locadora.rn.SituacaoLocacaoRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "situacaoLocacaoBean")
@ViewScoped
public class SituacaoLocacaoBean implements Serializable{

	/**
	 * 
	 */
	@Getter	private static final long serialVersionUID = 7578069242957458858L;
	@Getter @Setter private SituacaoLocacao situacaoLocacao=new SituacaoLocacao();
	@Getter @Setter private Situacaoimovel situacaoimovel=new Situacaoimovel();
	@Getter @Setter private SituacaoLocacaoPermissoes situacaoLocacaoPermissoes=new SituacaoLocacaoPermissoes();
	@Getter @Setter private SituacaoLocacaoPermissoesId situacaoLocacaoPermissoesId=new SituacaoLocacaoPermissoesId();
	@Getter @Setter private List<SituacaoLocacao> listaSituacaoLocacoes;
	@Getter @Setter private List<PermissoesLocacao> listaPermissoesLocacaos;
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public SituacaoLocacaoBean() {
		carregarListas();
		carregar();
	}
	public void carregar(){
		int situacaoLocacaoID = this.contextoBean.getParametro("id", -1);
		this.situacaoLocacao=new SituacaoLocacao();
		this.situacaoimovel=new Situacaoimovel();
		this.situacaoLocacaoPermissoes=new SituacaoLocacaoPermissoes();
		this.situacaoLocacaoPermissoesId=new SituacaoLocacaoPermissoesId();
		if (situacaoLocacaoID > 0) {
			SituacaoLocacao sitLocCarregado=new SituacaoLocacaoRN().carregar(situacaoLocacaoID);			
			if (sitLocCarregado != null){
				this.situacaoLocacao = sitLocCarregado;
				this.situacaoimovel.setSitId(this.situacaoLocacao.getSituacaoimovel().getSitId());
				this.listaPermissoesLocacaos=new SituacaoLocacaoPermissoesRN().listarPermissoesLocacaoPorSituacao(this.situacaoLocacao);
				this.alteracao=true;
			}
		}
	}
	public void carregarListas(){
		this.listaSituacaoLocacoes=new SituacaoLocacaoRN().listar();
		this.listaPermissoesLocacaos=new ArrayList<PermissoesLocacao>();
	}
	
public void salvar() {
		
	SituacaoLocacaoRN situacaoLocacaoRN=new SituacaoLocacaoRN();
	SituacaoLocacaoPermissoesRN locacaoPermissoesRN=new SituacaoLocacaoPermissoesRN();
	
		SituacaoLocacao sit=situacaoLocacaoRN.buscarPorSituacaoLocacao(this.situacaoLocacao.getSitLocNome());
		if(sit!=null){
			if(alteracao){				
				List<PermissoesLocacao> listaPermissoesTeste=new SituacaoLocacaoPermissoesRN().listarPermissoesLocacaoPorSituacao(sit);
				List<SituacaoLocacaoPermissoes> listaSituacaoLocacaoPermissoes=locacaoPermissoesRN.listar();
				for(PermissoesLocacao perTemp: listaPermissoesTeste){
					for(SituacaoLocacaoPermissoes situacaoLocacaoPermissoes: listaSituacaoLocacaoPermissoes){
						if((situacaoLocacaoPermissoes.getId().getPerLocId()==perTemp.getPerLocId()) && (situacaoLocacaoPermissoes.getId().getSitLocId()==sit.getSitLocId())){
							locacaoPermissoesRN.excluir(situacaoLocacaoPermissoes);
						}
					}
				}
				
				sit.setSituacaoimovel(this.situacaoimovel);
				situacaoLocacaoRN.salvar(sit);
				for(PermissoesLocacao p: this.listaPermissoesLocacaos){
					this.situacaoLocacaoPermissoesId.setPerLocId(p.getPerLocId());
					this.situacaoLocacaoPermissoesId.setSitLocId(sit.getSitLocId());
					this.situacaoLocacaoPermissoes.setId(this.situacaoLocacaoPermissoesId);
					this.situacaoLocacaoPermissoes.setPermissoesLocacao(p);
					this.situacaoLocacaoPermissoes.setSituacaoLocacao(sit);
					locacaoPermissoesRN.salvar(this.situacaoLocacaoPermissoes);
					this.situacaoLocacaoPermissoes=new SituacaoLocacaoPermissoes();
					this.situacaoLocacaoPermissoesId=new SituacaoLocacaoPermissoesId();
				}
				carregar();
				carregarListas();
				this.contextoBean.redirecionarParaPagina("admin/locacao/situacao-locacao/consulta.jsf");
				return;
			}
			this.contextoBean.mostrarAviso("Essa situação de locação já existe");
			return;
		}		
			
		this.situacaoLocacao.setSituacaoimovel(this.situacaoimovel);
		
		situacaoLocacaoRN.salvar(this.situacaoLocacao);
		
		for(PermissoesLocacao p: this.listaPermissoesLocacaos){
			this.situacaoLocacaoPermissoesId.setPerLocId(p.getPerLocId());
			this.situacaoLocacaoPermissoesId.setSitLocId(this.situacaoLocacao.getSitLocId());
			this.situacaoLocacaoPermissoes.setId(this.situacaoLocacaoPermissoesId);
			this.situacaoLocacaoPermissoes.setPermissoesLocacao(p);
			this.situacaoLocacaoPermissoes.setSituacaoLocacao(this.situacaoLocacao);
			locacaoPermissoesRN.salvar(this.situacaoLocacaoPermissoes);
			this.situacaoLocacaoPermissoes=new SituacaoLocacaoPermissoes();
			this.situacaoLocacaoPermissoesId=new SituacaoLocacaoPermissoesId();
		}
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/locacao/situacao-locacao/consulta.jsf");
	}

	public void excluir() {
		SituacaoLocacaoRN situacaoLocacaoRN=new SituacaoLocacaoRN();
		
		if(!situacaoLocacaoRN.dependecias(this.situacaoLocacao)){
			this.contextoBean.mostrarErro("Situação locação não excluído, essa situação está ligado a uma locação.");
			return;
		}
		List<PermissoesLocacao> listaPermissoesTeste=new SituacaoLocacaoPermissoesRN().listarPermissoesLocacaoPorSituacao(this.situacaoLocacao);
		SituacaoLocacaoPermissoesRN locacaoPermissoesRN=new SituacaoLocacaoPermissoesRN();
		
		List<SituacaoLocacaoPermissoes> listaSituacaoLocacaoPermissoes=locacaoPermissoesRN.listar();
		for(PermissoesLocacao perTemp: listaPermissoesTeste){
			for(SituacaoLocacaoPermissoes situacaoLocacaoPermissoes: listaSituacaoLocacaoPermissoes){
				if((situacaoLocacaoPermissoes.getId().getPerLocId()==perTemp.getPerLocId()) && (situacaoLocacaoPermissoes.getId().getSitLocId()==this.situacaoLocacao.getSitLocId())){
					locacaoPermissoesRN.excluir(situacaoLocacaoPermissoes);
				}
			}
		}	

			if (this.situacaoLocacao.getSitLocId() <= 0) {
				this.contextoBean.mostrarAviso("Essa situação ainda não foi salvo!");
				return;
			}
		boolean resposta=situacaoLocacaoRN.excluir(this.situacaoLocacao);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Situação locação não excluído, essa situação está ligado a uma locação.");
			return;
		}
	}
	
	public String carregarPermissoes(Integer id){
		SituacaoLocacao sit=new SituacaoLocacao();
		sit.setSitLocId(id);
		List<PermissoesLocacao> lista=new SituacaoLocacaoPermissoesRN().listarPermissoesLocacaoPorSituacao(sit);
		String nome=new String();
		for(PermissoesLocacao p: lista){
			nome+=p.getPerLocNome()+" : ";
		}
		return nome;
	}
}
