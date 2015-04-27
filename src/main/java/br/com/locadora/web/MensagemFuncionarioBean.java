package br.com.locadora.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.MensagemFuncionario;
import br.com.locadora.rn.FuncionarioRN;
import br.com.locadora.rn.MensagemFuncionarioRN;
import br.com.locadora.web.util.ContextoUtil;
import br.com.locadora.web.util.GenericBean;

@ManagedBean(name = "mensagemFuncionarioBean")
@ViewScoped
public class MensagemFuncionarioBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3548478851776096063L;
	@Getter @Setter private MensagemFuncionario mensagem=new MensagemFuncionario();
	@Setter	private List<MensagemFuncionario> listaMensagens;
	@Getter @Setter	private List<MensagemFuncionario> listaMensagensCompleta;
	@Getter @Setter	private Funcionario funcionario = new Funcionario();
	@Getter @Setter	private List<Funcionario> listaFuncionarios;
	
	@Getter @Setter private GenericBean genericBean=ContextoUtil.getGenericBean();
	
	public MensagemFuncionarioBean() {
		carregarLista();
		carregar();
	}
	
	private void carregar() {
		this.funcionario=new Funcionario();
		this.mensagem=new MensagemFuncionario();
	}
	
	private void carregarLista(){
		this.listaMensagensCompleta=new MensagemFuncionarioRN().listar();
		this.listaFuncionarios=new FuncionarioRN().listar();
	}	
	
	@SuppressWarnings("static-access")
	public void salvar(){
		FuncionarioRN funcionarioRN=new FuncionarioRN();
		this.funcionario=funcionarioRN.carregar(this.funcionario.getPesId());
		this.mensagem.setFuncionario(this.funcionario);
		this.mensagem.setMenFunDataCriacao(new Date());
		this.mensagem.setMenFunRemetente(this.genericBean.getFuncionarioLogado().getPessoa().getPesNome());
		this.mensagem.setMenFunVisualizada(false);
		new MensagemFuncionarioRN().salvar(this.mensagem);
		carregarLista();
		carregar();
		this.genericBean.redirecionarParaPagina("restrito/pessoa/consultaMensagens.jsf");
	}
	
	@SuppressWarnings("static-access")
	public List<MensagemFuncionario> getListaMensagens() {
		MensagemFuncionarioRN mensagemFuncionarioRN = new MensagemFuncionarioRN();
		if((this.listaMensagens==null)||(this.listaMensagens.size()==0)){
			if((this.genericBean.getFuncionarioLogado()!=null)||(this.genericBean.getFuncionarioLogado().getPesId()>0)){
				if(this.genericBean.getFuncionarioLogado().getPesId()==0){
					FacesContext context = FacesContext.getCurrentInstance();
					ExternalContext external = context.getExternalContext();
					String login = external.getRemoteUser();
					Funcionario fun=new Funcionario();
					if (login != null) {
						FuncionarioRN funcionarioRN = new FuncionarioRN();
						fun = funcionarioRN.buscarPorLogin(login);
						this.listaMensagens=mensagemFuncionarioRN.buscarPorFuncionarioVisualizada(fun);
					}	
				}else{
					this.listaMensagens=mensagemFuncionarioRN.buscarPorFuncionarioVisualizada(this.genericBean.getFuncionarioLogado());
				}
			}else{
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext external = context.getExternalContext();
				String login = external.getRemoteUser();
				Funcionario fun=new Funcionario();
				if (login != null) {
					FuncionarioRN funcionarioRN = new FuncionarioRN();
					fun = funcionarioRN.buscarPorLogin(login);
					this.listaMensagens=mensagemFuncionarioRN.buscarPorFuncionarioVisualizada(fun);
				}
			}
		}
		return listaMensagens;
	}
	
	public void mensagemLida(){
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String idObjeto = params.get("paramId");
		MensagemFuncionarioRN mensagemFuncionarioRN = new MensagemFuncionarioRN();
		this.mensagem=mensagemFuncionarioRN.carregar(Integer.valueOf(idObjeto));
		this.mensagem.setMenFunDataLeitura(new Date());
		this.mensagem.setMenFunVisualizada(true);
		mensagemFuncionarioRN.salvar(this.mensagem);
		//super.redirecionarParaPagina("restrito/principal.jsf");
		this.listaMensagens=null;
	}

}
