package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.util.DigestUtils;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Email;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Filial;
import br.com.locadora.rn.EmailRN;
import br.com.locadora.rn.FuncionarioRN;
import br.com.locadora.rn.PessoaRN;
import br.com.locadora.util.RNException;
import br.com.locadora.util.UtilException;
import br.com.locadora.web.util.ContextoUtil;
import br.com.locadora.web.util.GmailBean;

@ManagedBean(name = "funcionarioBean")
@ViewScoped
public class FuncionarioBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6227448696955415802L;
	@Getter @Setter	private Funcionario funcionario = new Funcionario();
	@Getter @Setter	private Pessoa pessoa = new Pessoa();
	@Getter @Setter	private Filial sede = new Filial();
	private List<Funcionario> listaFuncionarios;
	@Setter	private List<Funcionario> listaCaptadores;
	@Getter @Setter	private String confirmaSenha;
	@Getter @Setter	private String senha;
	@Getter @Setter	private String login;
	
	@Getter @Setter	private ArvoreTemp arvore=new ArvoreTemp();
	@Getter @Setter	private TreeNode[] arvoreSelecionada;
	
	@Getter @Setter boolean alteracao=false;
	
	private List<Pessoa> listaPessoasFuncionario;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public FuncionarioBean() {
		carregarLista();
		carregar();
	}
	
	@PostConstruct
	public void TestandoNumeroDaSessao(){
		System.out.println("Numero da Sessao do FuncionarioBean "+hashCode());
	}

	private void carregar() {
		this.funcionario=new Funcionario();
		
		int funcionarioID = this.contextoBean.getParametro("id", -1);

		if (funcionarioID <= 0) {
			setAlteracao(false);
		} else {
			setAlteracao(true);
			Funcionario funcionarioCarregado = new FuncionarioRN().carregar(funcionarioID);
			if(funcionarioCarregado!=null){
				this.funcionario=funcionarioCarregado;
				this.pessoa=this.funcionario.getPessoa();
				this.login=this.funcionario.getFunLoguin();
				this.sede=this.funcionario.getFilial();
				this.senha=new String();
				this.confirmaSenha=new String();
				funcionarioEscolhido();
			}
		}

		
	}



	private void carregarLista() {
	}



	public void incluirPessoa(Pessoa p) {
		this.pessoa = p;
		System.out.println("Nome : " + pessoa.getPesNome());
	}

	public String salvar(TreeNode[] nodes) throws RNException{
		if((this.pessoa==null)||(this.pessoa.getPesId()<1)){
			this.senha=new String();
			this.confirmaSenha=new String();
			this.contextoBean.mostrarErro("Selecione o funcionário ");
			return null;
		}
		
		if(this.sede.getFilId()==0){
			this.contextoBean.mostrarErro("Selecione a filial ");
			return null;
		}

		if (!this.senha.equals(this.confirmaSenha)) {
			this.senha=new String();
			this.confirmaSenha=new String();
			this.contextoBean.mostrarErro("A senha não foi confirmada corretamente");
			return null;
		}
		
		if(this.funcionario.getFunCargo()==null || this.funcionario.getFunCargo()==""){
			this.contextoBean.mostrarErro("Campo Cargo obrigatorio");
			return null;
		}

		FuncionarioRN funcionarioRN = new FuncionarioRN();

		Funcionario f=funcionarioRN.buscarPorLogin(login);
		if(f!=null){
			if(this.alteracao){
				if(f.getPesId()!=this.funcionario.getPesId()){
					this.contextoBean.mostrarErro("Esse login já existe no sistema. Pertence ao Sr."+f.getPessoa().getPesNome());
					return null;
				}else{
					//eliminado objeto da session
					this.contextoBean.evict(f);
				}
			}else{
				this.contextoBean.mostrarErro("Esse login já existe no sistema. Pertence ao Sr."+f.getPessoa().getPesNome());
				return null;
			}
		}
		
		if(nodes!=null)
			salvarPermissoes(nodes);
		
		this.funcionario.setFunLoguin(login);
		if(!this.senha.equals("")){
			String senhaCripto = DigestUtils.md5DigestAsHex(senha.getBytes());
			this.funcionario.setFunSenha(senhaCripto);
		}
		this.funcionario.setFilial(sede);
		
		if(!alteracao){
			PessoaRN pessoaRN=new PessoaRN();

			this.funcionario.setFunAtivo(true);
			this.funcionario.setPessoa(pessoa);
			pessoa=pessoaRN.carregar(funcionario.getPessoa().getPesId());
			pessoa.setFuncionario(funcionario);
			this.pessoa.setFuncionario(funcionario);
			try{
				pessoaRN.salvar(pessoa);
			} catch (UtilException e) {
				FacesContext context2 = FacesContext.getCurrentInstance();
				context2.addMessage(null, new FacesMessage(e.getMessage()));
				return null;
			}
			this.funcionario.getPermissao().add("ROLE_BASICO");
		}

		funcionarioRN.salvar(this.funcionario);

		GmailBean mail=new GmailBean();
		mail.setAssunto("Cadastro Francisco Imóveis");
		mail.setDe("mariocajuru@gmail.com");
		EmailRN l=new EmailRN();
		List<Email> listaEmail=l.carregarPessoa(pessoa);

		for(Email e: listaEmail){
			mail.setPara(e.getEmaEndereco());
			mail.setMensagem("Olá caro "+pessoa.getPesNome()+" sua senha é: "+this.senha+
					", login: "+funcionario.getFunLoguin());
			mail.enviarEmail();
		}
		
		this.contextoBean.redirecionarParaPagina("admin/filial/funcionario/consulta.jsf");
		return null;
		/*super.mostrarAviso("Salvo com sucesso");
		this.funcionario = new Funcionario();
		this.pessoa = new Pessoa();
		this.sede = new Filial();
		this.listaFuncionarios = null;
		this.listaCaptadores=null;
		this.listaPessoasFuncionario=null;
		senha=null;
		login=null;
		return null;*/

	}

	public void ativar() {

		if (this.funcionario.getFunAtivo() == true) {
			this.funcionario.setFunAtivo(false);
		} else {
			this.funcionario.setFunAtivo(true);
		}

		new FuncionarioRN().salvar(this.funcionario);
		
		this.contextoBean.redirecionarParaPagina("admin/filial/funcionario/consulta.jsf");
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Funcionario> getListaCaptadores() {
		if (this.listaCaptadores == null) {
			this.listaCaptadores=new ArrayList<Funcionario>();
			List<Funcionario> lista=new ArrayList<Funcionario>();
			FuncionarioRN funcionarioRN = new FuncionarioRN();
			lista = funcionarioRN.listar();
			for(Funcionario f: lista){
				if(f.getPermissao().contains("ROLE_CAPTADOR_INCLUIR")
						||f.getPermissao().contains("ROLE_CAPTADOR_CONSULTAR")
						||f.getPermissao().contains("ROLE_CAPTADOR_ALTERAR")
						||f.getPermissao().contains("ROLE_CAPTADOR_EXCLUIR")){
					this.listaCaptadores.add(f);
				}
			}
		}
		return this.listaCaptadores;
	}

	public List<Funcionario> getListaFuncionarios() {
		if (listaFuncionarios == null) {
			this.listaFuncionarios=new ArrayList<Funcionario>();
			FuncionarioRN funcionarioRN = new FuncionarioRN();			
			this.listaFuncionarios = funcionarioRN.listar();
		}
		return this.listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public void teste(){
		System.out.println("Id da pessoa "+pessoa.getPesId());
	}



	public List<Pessoa> getListaPessoasFuncionario() {
		if (this.listaPessoasFuncionario == null) {
			FuncionarioRN funcionarioRN=new FuncionarioRN();
			PessoaRN pessoaRN = new PessoaRN();
			List<Pessoa> pes= pessoaRN.listar();
			this.listaPessoasFuncionario=new ArrayList<Pessoa>();
			for(Pessoa p: pes){			
				if(p.getPesFuncionario()!=null){
					if(p.getPesFuncionario().equals(true)&&(!funcionarioRN.pessoaCadastradaComoFuncionario(p))){					
						this.listaPessoasFuncionario.add(p);
					}
				}
			}
		}
		return listaPessoasFuncionario;
	}

	public void setListaPessoasFuncionario(List<Pessoa> listaPessoasFuncionario) {
		this.listaPessoasFuncionario = listaPessoasFuncionario;
	}
	public void verificarFuncionarioLogado(){
		//verifica se o funcionario estÃ¡ logado
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		if(contextoBean == null){
			this.contextoBean.mostrarAviso("Usuario não logado, "+
					" Faça o loguin");
		}
	}

	public void editarProposta(RowEditEvent event) {
		this.funcionario=((Funcionario) event.getObject());

		FuncionarioRN funcionarioRN = new FuncionarioRN();
		/*this.funcionario=funcionarioRN.carregar(this.funcionario.getFunId());
		if(funcionarioRN.loguinIgualEntreFuncionarios(this.funcionario)){
			super.mostrarErro("Esse login já existe no sistema");
			return ;
		}*/
		if((this.confirmaSenha!=null)&&(!this.confirmaSenha.equals(""))){
			String senhaCripto = DigestUtils.md5DigestAsHex(this.confirmaSenha.getBytes());
			this.funcionario.setFunSenha(senhaCripto);
		}else{
			this.contextoBean.mostrarErro("Senha obrigatoria");
			return;
		}
		funcionarioRN.salvar(this.funcionario);
		this.contextoBean.mostrarAviso("Editado ok");
		this.confirmaSenha=new String();
	}
	
	public void cancelarEditacaoProposta(RowEditEvent event) {
		this.contextoBean.mostrarAviso("Cancelado ok");
	}
	
	public class ArvoreTemp implements Serializable{
		private static final long serialVersionUID = -1674089452923629671L;
		@Setter private TreeNode arvore;
		//@Getter @Setter Set<String> permissoes=new HashSet<String>();
		public TreeNode getArvore() {
			if(this.arvore==null){
				this.arvore=new DefaultTreeNode("root",null);
				TreeNode admi=new DefaultTreeNode("Documents","ADMINISTRAÇÃO",this.arvore);

				TreeNode bairro=new DefaultTreeNode("Pictures","BAIRRO",admi);
				/*TreeNode alterar=new DefaultTreeNode("alterar","ALTERAR",bairro);
			TreeNode excluir=new DefaultTreeNode("excluir","EXCLUIR",bairro);
			TreeNode incluir=new DefaultTreeNode("incluir","INCLUIR",bairro);
			TreeNode consultar=new DefaultTreeNode("consultar","CONSULTAR",bairro);*/
				newArray(bairro);

				TreeNode banco=new DefaultTreeNode("Pictures","BANCO",admi);
				newArray(banco);
				TreeNode caracteristicasImoveis=new DefaultTreeNode("Pictures","CARACTERISTICAS IMOVEL",admi);
				newArray(caracteristicasImoveis);
				TreeNode filiais=new DefaultTreeNode("Pictures","FILIAIS",admi);
				newArray(filiais);
				TreeNode funcionarios=new DefaultTreeNode("Pictures","FUNCIONARIOS",admi);
				newArray(funcionarios);
				TreeNode quadroChaves=new DefaultTreeNode("Pictures","QUADRO CHAVES",admi);
				newArray(quadroChaves);
				TreeNode regioes=new DefaultTreeNode("Pictures","REGIAO",admi);
				newArray(regioes);	
				TreeNode situacaoImovel=new DefaultTreeNode("Pictures","SITUACAO IMOVEL",admi);
				newArray(situacaoImovel);				
				TreeNode tipoImovel=new DefaultTreeNode("Pictures","TIPO IMOVEL",admi);
				newArray(tipoImovel);				
				TreeNode formaPagamentoProprietario=new DefaultTreeNode("Pictures","FORMA PAGAMENTO PROPRIETARIO",admi);
				newArray(formaPagamentoProprietario);				
				TreeNode pertoImovel=new DefaultTreeNode("Pictures","PERTO IMOVEL",admi);
				newArray(pertoImovel);				
				TreeNode ramoAtividade=new DefaultTreeNode("Pictures","RAMO ATIVIDADE",admi);
				newArray(ramoAtividade);				
				TreeNode detinacaoLocacao=new DefaultTreeNode("Pictures","DESTINACAO LOCACAO",admi);
				newArray(detinacaoLocacao);				
				TreeNode statusLocacao=new DefaultTreeNode("Pictures","STATUS LOCACAO",admi);
				newArray(statusLocacao);				
				TreeNode permissaoLocacao=new DefaultTreeNode("Pictures","PERMISSAO LOCACAO",admi);
				newArray(permissaoLocacao);
				TreeNode situacaoLocacao=new DefaultTreeNode("Pictures","SITUACAO LOCACAO",admi);
				newArray(situacaoLocacao);					
				TreeNode indiceReajusteLocacao=new DefaultTreeNode("Pictures","INDICE REAJUSTRE LOCACAO",admi);
				newArray(indiceReajusteLocacao);				
				TreeNode impostoRenda=new DefaultTreeNode("Pictures","IMPOSTO RENDA",admi);
				newArray(impostoRenda);	
				TreeNode planoContas=new DefaultTreeNode("Pictures","PLANO CONTAS",admi);
				newArray(planoContas);				
				TreeNode contasFinanceiro=new DefaultTreeNode("Pictures","CONTAS FINANCEIRO",admi);
				newArray(contasFinanceiro);				
				TreeNode centroCusto=new DefaultTreeNode("Pictures","CENTRO CUSTO",admi);
				newArray(centroCusto);				
				TreeNode grupoContas=new DefaultTreeNode("Pictures","GRUPO CONTAS",admi);
				newArray(grupoContas);				
				TreeNode parametros=new DefaultTreeNode("Pictures","PARAMETROS",admi);
				newArray(parametros);				
				TreeNode seguroIncendioResidencial=new DefaultTreeNode("Pictures","SEGURO INCENDIO RESIDENCIAL",admi);
				newArray(seguroIncendioResidencial);				
				TreeNode seguroIncendioComercial=new DefaultTreeNode("Pictures","SEGURO INCENDIO COMERCIAL",admi);
				newArray(seguroIncendioComercial);

				TreeNode financeiro=new DefaultTreeNode("Documents","FINANCEIRO",this.arvore);
				TreeNode contaCorrente=new DefaultTreeNode("Pictures","CONTA CORRENTE",financeiro);
				newArray(contaCorrente);
				TreeNode contaPagar=new DefaultTreeNode("Pictures","CONTA PAGAR",financeiro);
				newArray(contaPagar);
				TreeNode gerar=new DefaultTreeNode("Pictures","GERAR",financeiro);
				newArray(gerar);
				TreeNode lancarDesconto=new DefaultTreeNode("Pictures","LANCAR DESCONTO",financeiro);
				newArray(lancarDesconto);
				TreeNode reajuste=new DefaultTreeNode("Pictures","REAJUSTE",financeiro);
				newArray(reajuste);
				
				TreeNode cadastro=new DefaultTreeNode("Documents","CADASTRO",this.arvore);
				TreeNode devolucaoChave=new DefaultTreeNode("Pictures","DEVOLUCAO CHAVE",cadastro);
				newArray(devolucaoChave);
				TreeNode imovel=new DefaultTreeNode("Pictures","IMOVEL",cadastro);
				newArray(imovel);
				TreeNode pessoa=new DefaultTreeNode("Pictures","PESSOA",cadastro);
				newArray(pessoa);
				TreeNode vistoria=new DefaultTreeNode("Pictures","VISTORIA",cadastro);
				newArray(vistoria);
				
				TreeNode locacao=new DefaultTreeNode("Documents","LOCACAO",this.arvore);
				TreeNode contrato=new DefaultTreeNode("Pictures","CONTRATO",locacao);
				newArray(contrato);
				
				TreeNode captador=new DefaultTreeNode("Captador","CAPTADOR",this.arvore);
				newArray(captador);
			}

			return arvore;
		}		
		
		public void newArray(TreeNode object){
			TreeNode array[] = new TreeNode[4];
			array[0]=new DefaultTreeNode("excluir","EXCLUIR",object);
			array[1]=new DefaultTreeNode("alterar","ALTERAR",object);
			array[2]=new DefaultTreeNode("incluir","INCLUIR",object);
			array[3]=new DefaultTreeNode("consultar","CONSULTAR",object);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((arvore == null) ? 0 : arvore.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ArvoreTemp other = (ArvoreTemp) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (arvore == null) {
				if (other.arvore != null)
					return false;
			} else if (!arvore.equals(other.arvore))
				return false;
			return true;
		}

		private FuncionarioBean getOuterType() {
			return FuncionarioBean.this;
		}

		@Override
		public String toString() {
			return "ArvoreTemp [arvore=" + arvore + ", getArvore()="
					+ getArvore() + ", hashCode()=" + hashCode()
					+ ", getOuterType()=" + getOuterType() + ", getClass()="
					+ getClass() + ", toString()=" + super.toString() + "]";
		}

		
	}
	
	public void funcionarioEscolhido(){
		this.arvore=new ArvoreTemp();
		FuncionarioRN funcionarioRN=new FuncionarioRN();
		this.funcionario=funcionarioRN.carregar(this.funcionario.getPesId());
		Set<String> permissoes= this.funcionario.getPermissao();		
		List<TreeNode> nodes = this.arvore.getArvore().getChildren();

		for(String permissao: permissoes){
			
			String pai=permissao;
			String tipo=permissao;
			
			pai=tipo=pai.replace("ROLE_", "");
			pai=pai.replace("_EXCLUIR", "");
			pai=pai.replace("_ALTERAR", "");
			pai=pai.replace("_INCLUIR", "");
			pai=pai.replace("_CONSULTAR", "");
			pai=pai.replace("_", " ");
			
			tipo=tipo.replace("_", " ");
			tipo=tipo.replace("ADMIN", "");
			tipo=tipo.replace("CARACTERISTICAS IMOVEL", "");
			tipo=tipo.replace("BAIRRO", "");
			tipo=tipo.replace("BANCO", "");
			tipo=tipo.replace("REGIAO", "");
			tipo=tipo.replace("QUADRO CHAVES", "");
			tipo=tipo.replace("FUNCIONARIOS", "");
			tipo=tipo.replace("ATENDENTE", "");
			tipo=tipo.replace("OCUPACAO", "");			
			tipo=tipo.replace("CONTA CORRENTE", "");
			tipo=tipo.replace("CONTA PAGAR", "");
			tipo=tipo.replace("GERAR", "");
			tipo=tipo.replace("LANCAR DESCONTO", "");
			tipo=tipo.replace("REAJUSTE", "");
			tipo=tipo.replace("CADASTRO", "");
			tipo=tipo.replace("DEVOLUCAO CHAVE", "");
			tipo=tipo.replace("PESSOA", "");
			tipo=tipo.replace("VISTORIA", "");
			tipo=tipo.replace("LOCACAO", "");
			tipo=tipo.replace("CONTRATO", "");
			tipo=tipo.replace("FILIAIS", "");
			tipo=tipo.replace("FINANCEIRO", "");
			tipo=tipo.replace("SITUACAO IMOVEL","");
			tipo=tipo.replace("TIPO IMOVEL", "");
			tipo=tipo.replace("FORMA PAGAMENTO PROPRIETARIO", "");
			tipo=tipo.replace("PERTO IMOVEL", "");
			tipo=tipo.replace("RAMO ATIVIDADE", "");
			tipo=tipo.replace("DESTINACAO ", "");
			tipo=tipo.replace("STATUS ", "");
			tipo=tipo.replace("PERMISSAO ", "");
			tipo=tipo.replace("SITUACAO ", "");
			tipo=tipo.replace("INDICE REAJUSTRE ", "");
			tipo=tipo.replace("IMPOSTO RENDA", "");
			tipo=tipo.replace("PLANO CONTAS", "");
			tipo=tipo.replace("CONTAS ", "");
			tipo=tipo.replace("CENTRO CUSTO", "");
			tipo=tipo.replace("GRUPO ", "");
			tipo=tipo.replace("PARAMETROS", "");
			tipo=tipo.replace("SEGURO INCENDIO RESIDENCIAL", "");
			tipo=tipo.replace("SEGURO INCENDIO COMERCIAL", "");
			tipo=tipo.replace("CAPTADOR", "");

			tipo=tipo.replace("IMOVEL", "");
			tipo=tipo.replace(" ", "");
			
			//System.out.println("Classe: "+pai+" - Tipo: "+tipo);			
			
			for(int i=0; i<nodes.size();i++){	
				List<TreeNode> filho=nodes.get(i).getChildren();
				if(nodes.get(i).getData().toString().equalsIgnoreCase(pai)){
					for(TreeNode f: filho){
						if(f.getData().toString().equalsIgnoreCase(tipo))
							f.setSelected(true);
					}
				}
				for(TreeNode f: filho){
					List<TreeNode> neto=f.getChildren();
					if(f.getData().toString().equalsIgnoreCase(pai)){
						//f.setSelected(true);
						for(TreeNode n: neto){
							n.setParent(f);
							if(n.getData().toString().equalsIgnoreCase(tipo)){	
								n.setSelected(true);
							}
						}
					}
				}
			}
		}
		
	}
	
	public void salvarPermissoes(TreeNode[] nodes) {
		
	/*	FuncionarioRN funcionarioRN=new FuncionarioRN();
		this.funcionario=funcionarioRN.carregar(this.funcionario.getFunId());*/		
		//limpando as permissoes
		this.funcionario.setPermissao(new HashSet<String>());
		
		if(nodes != null && nodes.length > 0) {
			
			StringBuilder builder = new StringBuilder();
			for(TreeNode node : nodes) {
				List<TreeNode> list=node.getChildren();
				if(list.size()>0){
				}else if(list.size()==0){
					builder.append(node.getParent().toString()+"_"+node.getData().toString());
	                builder.append(" <br/>");
					String permissao=new String();
					permissao=node.getParent().toString()+"_"+node.getData().toString();
					permissao=permissao.replace(" ", "_");
					permissao=permissao.replace("<br/>", "");
					permissao=normalizar(permissao);
					permissao=permissao.toUpperCase();
					permissao="ROLE_"+permissao;
					this.funcionario.getPermissao().add(permissao);
				}   
			}
			
			/*FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Permissão adicionada ", builder.toString());
			FacesContext.getCurrentInstance().addMessage(null, message);*/
		}
		
		this.funcionario.getPermissao().add("ROLE_BASICO");
		/*funcionarioRN.salvar(this.funcionario);
		
		this.arvore=new ArvoreTemp();
		this.funcionario=new Funcionario();*/
	}
	public String normalizar(String str) {  
        
        /** Troca os caracteres acentuados por não acentuados **/  
        String[][] caracteresAcento = {  
                {"Á", "A"}, {"á", "a"},  
                {"É", "E"}, {"é", "e"},  
                {"Í", "I"}, {"í", "i"},  
                {"Ó", "O"}, {"ó", "o"},  
                {"Ú", "U"}, {"ú", "u"},  
                {"À", "A"}, {"à", "a"},  
                {"È", "E"}, {"è", "e"},  
                {"Ì", "I"}, {"ì", "i"},  
                {"Ò", "O"}, {"ò", "o"},  
                {"Ù", "U"}, {"ù", "u"},  
                {"Â", "A"}, {"â", "a"},  
                {"Ê", "E"}, {"ê", "e"},  
                {"Î", "I"}, {"î", "i"},  
                {"Ô", "O"}, {"ô", "o"},  
                {"Û", "U"}, {"û", "u"},  
                {"Ä", "A"}, {"ä", "a"},  
                {"Ë", "E"}, {"ë", "e"},  
                {"Ï", "I"}, {"ï", "i"},  
                {"Ö", "O"}, {"ö", "o"},  
                {"Ü", "U"}, {"ü", "u"},  
                {"Ã", "A"}, {"ã", "a"},   
                {"Õ", "O"}, {"õ", "o"},  
                {"Ç", "C"}, {"ç", "c"},  
        };  
          
        for (int i = 0; i < caracteresAcento.length; i++) {  
            str = str.replaceAll(caracteresAcento[i][0], caracteresAcento[i][1]);  
        }  
          
        /** Troca os caracteres especiais da string por "" **/  
        String[] caracteresEspeciais = {"\\.", ",", "-", ":", "\\(", "\\)", "ª", "\\|", "\\\\", "°"};  
          
        for (int i = 0; i < caracteresEspeciais.length; i++) {  
            str = str.replaceAll(caracteresEspeciais[i], "");  
        }  
  
        /** Troca os espaços no início por "" **/  
        str = str.replaceAll("^\\s+", "");  
        /** Troca os espaços no início por "" **/  
        str = str.replaceAll("\\s+$", "");  
        /** Troca os espaços duplicados, tabulações e etc por  " " **/  
        str = str.replaceAll("\\s+", " ");  
          
        return str;  
    }  
}
