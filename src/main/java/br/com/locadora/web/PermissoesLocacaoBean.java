package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.PermissoesLocacao;
import br.com.locadora.rn.PermissoesLocacaoRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "permissoesLocacaoBean")
@ViewScoped
public class PermissoesLocacaoBean implements Serializable{

	/**
	 * 
	 */
	@Getter	private static final long serialVersionUID = 1348958540108069184L;
	@Getter @Setter private PermissoesLocacao permissoesLocacao=new PermissoesLocacao();
	@Getter @Setter private List<PermissoesLocacao> listaPermissoesLocacoes;
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public PermissoesLocacaoBean() {
		carregarListas();
		carregar();
	}
	public void carregar(){
		int permissoesLocacaoID = this.contextoBean.getParametro("id", -1);
		this.permissoesLocacao=new PermissoesLocacao();
		if (permissoesLocacaoID > 0) {
			PermissoesLocacao perLocCarregado=new PermissoesLocacaoRN().carregar(permissoesLocacaoID);			
			if (perLocCarregado != null){
				this.permissoesLocacao = perLocCarregado;
				this.alteracao=true;
			}
		}
	}
	public void carregarListas(){
		this.listaPermissoesLocacoes=new PermissoesLocacaoRN().listar();
	}
	
public void salvar() {
		
	PermissoesLocacaoRN permissoesLocacaoRN=new PermissoesLocacaoRN();
		PermissoesLocacao per=permissoesLocacaoRN.buscarPorPermissoesLocacao(this.permissoesLocacao.getPerLocNome());
		if(per!=null){
			if(alteracao){
				permissoesLocacaoRN.salvar(per);	
				carregar();
				carregarListas();
				this.contextoBean.redirecionarParaPagina("admin/locacao/permissoes-locacao/consulta.jsf");
				return;
			}
			this.contextoBean.mostrarAviso("Essa permiss�o de loca��o j� existe");
			return;
		}
		permissoesLocacaoRN.salvar(this.permissoesLocacao);	
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/locacao/permissoes-locacao/consulta.jsf");
	}

	public void excluir() {
		if (this.permissoesLocacao.getPerLocId() <= 0) {
			this.contextoBean.mostrarAviso("Essa permiss�o ainda n�o foi salvo!");
			return;
		}
		boolean resposta=new PermissoesLocacaoRN().excluir(this.permissoesLocacao);
		if(resposta){
			this.contextoBean.mostrarAviso("Exclu�do com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Permiss�o loca��o n�o exclu�do, essa permiss�o est� ligado a uma locac�o.");
			return;
		}
	}
}
