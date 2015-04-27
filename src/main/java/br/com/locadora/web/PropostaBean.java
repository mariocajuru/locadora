package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Email;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Cidade;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Proposta;
import br.com.locadora.modelo.Telefone;
import br.com.locadora.rn.BairroRN;
import br.com.locadora.rn.CidadeRN;
import br.com.locadora.rn.EmailRN;
import br.com.locadora.rn.EnderecoRN;
import br.com.locadora.rn.FuncionarioRN;
import br.com.locadora.rn.ImovelRN;
import br.com.locadora.rn.PesquisaRN;
import br.com.locadora.rn.PessoaRN;
import br.com.locadora.rn.PropostaRN;
import br.com.locadora.rn.TelefoneRN;
import br.com.locadora.util.UtilException;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "propostaBean")
@ViewScoped
public class PropostaBean implements Serializable {

	/**
	 * 
	 */
	@Getter	private static final long serialVersionUID = 1453043537670948613L;
	@Getter @Setter private Proposta proposta=new Proposta();
	@Getter @Setter private Pessoa pessoa=new Pessoa();
	@Getter @Setter private Endereco endereco=new Endereco();
	@Getter @Setter private Bairro bairro=new Bairro();
	@Getter @Setter private Cidade cidade=new Cidade();
	@Getter @Setter private Telefone telefone=new Telefone();
	@Getter @Setter private Email email=new Email();
	@Getter @Setter private Imovel imovel=new Imovel();
	@Getter @Setter private List<Proposta> listaPropostas;
	@Getter @Setter private List<Telefone> listaTel;
	@Getter @Setter private List<Email> listaEmail;
	@Getter @Setter private List<Imovel> listaImoveis;
	@Getter @Setter private List<Pessoa> listaPessoas;
	@Getter @Setter private String cpf = null;
	
	@Getter @Setter private Boolean pesquiarPessoa = true;
	/** Campos Escondidos */
	
	@Getter @Setter private String                   propostasJson    = "";
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public PropostaBean() {
		this.imovel=new Imovel();
		int imovelID = this.contextoBean.getParametro("id", -1);
		if(imovelID>0){
			this.imovel= new ImovelRN().carregar(imovelID);
			if(this.imovel!=null){
				carregar();
				carregarListas();
			}			
		}else{
			this.contextoBean.redirecionarParaPagina("restrito/imovel/consulta.jsf");			
		}
	}

	
	public void carregarListas() {
		this.listaPropostas=new PropostaRN().carregarPorImovel(this.imovel);
		this.listaEmail=new ArrayList<Email>();
		this.listaTel=new ArrayList<Telefone>();
		this.listaImoveis=new ImovelRN().listar();
		this.listaPessoas=new PessoaRN().listar();
	}

	
	public void carregar() {
		this.pesquiarPessoa=true;
		this.proposta=new Proposta();
		this.pessoa=new Pessoa();
		this.telefone=new Telefone();
		this.email=new Email();
		this.endereco=new Endereco();
		this.bairro=new Bairro();
		this.cidade=new Cidade();
	}

	
	public void gravar() {
		if(this.pessoa.getPesId()<1){
		this.endereco.setBairro(new BairroRN().buscarPorBairro(this.bairro.getBaiNome()));
		this.endereco.setCidade(new CidadeRN().buscarPorCidade( this.cidade.getCidNome()));
		
		EnderecoRN enderecoRN=new EnderecoRN();
		enderecoRN.salvar(this.endereco);
		
		this.pessoa.setEndereco(this.endereco);
		PessoaRN pessoaRN=new PessoaRN();
		try {
			pessoaRN.salvar(this.pessoa);
		} catch (UtilException e) {
			e.printStackTrace();
		}
		}
		this.proposta.setPessoa(this.pessoa);
		this.proposta.setImovel(this.imovel);
		this.proposta.setProDataProposta(new Date());
		this.proposta.setProIdAtendente(this.contextoBean.getFuncionarioLogado().getPesId());
		this.proposta.setFuncionario(this.contextoBean.getFuncionarioLogado());
		
		PropostaRN propostaRN=new PropostaRN();
		propostaRN.salvar(this.proposta);
		
		carregar();
		carregarListas();
		this.contextoBean.mostrarAviso("Salvo");

	}
	public void addTel(){
		String tel = telefone.getTelNumero();
		tel = tel.replaceAll("[.-]", "");
		tel = tel.replaceAll("[)]", "");
		tel = tel.replaceAll("[(]", "");
		TelefoneRN telefoneRN=new TelefoneRN();
		Telefone telefoneTeste=telefoneRN.buscarPorTelefone(tel);

		if(telefoneTeste == null){
			this.listaTel.add(telefone);
			this.telefone = new Telefone();
			return;
		}
		PessoaRN pessoaRN=new PessoaRN();
		Pessoa pes = pessoaRN.carregar(telefoneTeste.getPessoa().getPesId());
		FacesContext
		.getCurrentInstance()
		.addMessage(
				null,
				new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"Cadastro não efetuado - Telefone já existente !",
						"Telefone pertencente ao Sr. "+pes.getPesNome()+" ID: "+pes.getPesId()));
		this.pesquiarPessoa=false;
		this.telefone=new Telefone();
		/*this.pessoa=pes;
		this.endereco=new EnderecoRN().carregar(this.pessoa.getEndereco().getEndId());
		this.bairro=this.endereco.getBairro();
		this.cidade=this.endereco.getCidade();
		this.listaEmail=new EmailRN().carregarPessoa(this.pessoa);
		this.listaTel=new TelefoneRN().carregarPessoa(this.pessoa);*/
	}

	public void removerEmail(Email e) {
		if(listaEmail == null)
			return;

		listaEmail.remove(e);
		pessoa.getEmails().remove(e);
		email = new Email();
	}

	public void addEmail(){
		EmailRN emailRN = new EmailRN();
		Email emailTeste = emailRN.buscarPorEmail(email.getEmaEndereco());

		if(emailTeste == null){
			this.listaEmail.add(email);
			this.email = new Email();
			return;
		}
		Pessoa pes = new PessoaRN().carregar(emailTeste.getPessoa().getPesId());

		FacesContext
		.getCurrentInstance()
		.addMessage(
				null,
				new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"Cadastro não efetuado - Email já existente!",
						"Email pertencente ao Sr. " + pes.getPesNome() + " ID: " + pes.getPesId()));
		this.pesquiarPessoa=false;
		this.email=new Email();
	}

	public void removerTel(Telefone t){
		if(listaTel != null){
			this.listaTel.remove(t);
			pessoa.getTelefones().remove(t);
			this.telefone = new Telefone();
		}
	}

	public void confirmaCPF(String CPF) {

		if (isCPF(CPF) == true) {
			CPF = CPF.replaceAll("[.-]", "");
			// System.out.println("CPF 0 Convertido: "+CPF);
			if (CPF != null && !CPF.equals("") && CPF.length() == 11) {
				/*
				 * CPF = CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
				 * CPF.substring(6, 9) + "-" + CPF.substring(9, 11);
				 */
				// System.out.println("CPF Convertido: "+CPF);

				///verifica se o cpf já existe no banco
				PesquisaRN pesquisaRN=new PesquisaRN();
				if(pesquisaRN.existeCPF(CPF)==true){
					FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"CPF já existente !",
									" Esse CPF já foi cadastrado em nosso banco de dados !"));
					return;
				}
				this.pessoa.setPesCpfCnpj(CPF);
				// System.out.println("PesCpfCnpj: "+pessoa.getPesCpfCnpj());
			}

		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Campos invalidos", "CPF invalido !");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
			this.cpf=null;
			// System.out.println("CPF invalido !"+CPF);
		}

	}

	public boolean isCPF(String CPF) {
		CPF = CPF.replaceAll("[.-]", "");

		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111")
				|| CPF.equals("22222222222") || CPF.equals("33333333333")
				|| CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777")
				|| CPF.equals("88888888888") || CPF.equals("99999999999")
				|| (CPF.length() != 11))
			return (false);
		char dig10, dig11;
		int sm, i, r, num, peso;
		// "try" - protege o codigo para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48);
			// converte no respectivo caractere numerico
			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);
			// Verifica se os digitos calculados conferem com os digitos
			// informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}
	
	public void editarProposta(RowEditEvent event) {
		Proposta prop=((Proposta) event.getObject());
		new PropostaRN().salvar(prop);
		this.contextoBean.mostrarAviso("Editado ok");
	}
	public void cancelarEditacaoProposta(RowEditEvent event) {
		this.contextoBean.mostrarAviso("Cancelado ok");
	}
	
	public String nomeFuncinario(int i){
		Funcionario fun=new FuncionarioRN().carregar(i);
		Pessoa pessoa =new PessoaRN().carregar(fun.getPessoa().getPesId());
		return pessoa.getPesNome();
	}
	
	public void procurar(){
		if(this.pessoa.getPesId()<=0){
			this.pessoa=new Pessoa();
			this.contextoBean.mostrarErro("Esse Id não pertence a nenhuma pessoa. Procure novamente");
			return;
		}
		this.pessoa=new PessoaRN().carregar(this.pessoa.getPesId());
		if(this.pessoa==null){
			this.pessoa=new Pessoa();
			this.contextoBean.mostrarErro("Esse Id não pertence a nenhuma pessoa. Procure novamente");
			return;
		}
		if(this.pessoa.getEndereco()!=null){
		this.endereco=new EnderecoRN().carregar(this.pessoa.getEndereco().getEndId());
		this.bairro=this.endereco.getBairro();
		this.cidade=this.endereco.getCidade();
		}
		if(this.pessoa.getEndereco()==null){
			this.endereco=new Endereco();
			this.bairro=new Bairro();
			this.cidade=new Cidade();
		}
		this.listaEmail=new EmailRN().carregarPessoa(this.pessoa);
		this.listaTel=new TelefoneRN().carregarPessoa(this.pessoa);
	}
	
	public void limpar(){
		carregar();
		carregarListas();
	}
	
	public void atualizarListaPessoas(){
		this.listaPessoas=new PessoaRN().listar();
	}
}
