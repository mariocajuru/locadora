package br.com.locadora.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;

import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Banco;
import br.com.locadora.modelo.DadosBancariosProprietario;
import br.com.locadora.modelo.Fiador;
import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Cidade;
import br.com.locadora.modelo.Email;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Ramoatuacao;
import br.com.locadora.modelo.Referenciapessoal;
import br.com.locadora.modelo.Telefone;
import br.com.locadora.rn.BairroRN;
import br.com.locadora.rn.BancoRN;
import br.com.locadora.rn.CidadeRN;
import br.com.locadora.rn.DadosBancariosProprietarioRN;
import br.com.locadora.rn.EmailRN;
import br.com.locadora.rn.EnderecoRN;
import br.com.locadora.rn.FiadorRN;
import br.com.locadora.rn.ImovelRN;
import br.com.locadora.rn.PesquisaRN;
import br.com.locadora.rn.PessoaRN;
import br.com.locadora.rn.RamoatuacaoRN;
import br.com.locadora.rn.ReferenciapessoalRN;
import br.com.locadora.rn.TelefoneRN;
import br.com.locadora.util.UtilException;
import br.com.locadora.web.util.ContextoUtil;
import br.com.locadora.web.util.CpfValidator;
import br.com.locadora.web.util.GenericBean;

@SuppressWarnings("unused")
@ManagedBean(name = "pessoaBean")
@ViewScoped
public class PessoaBean implements Serializable {

	@Getter private static final long serialVersionUID = 1979296976487702495L;
	
	@Getter @Setter private Referenciapessoal       referencia       = new Referenciapessoal();
	@Getter @Setter private Bairro                  bairro           = new Bairro();
	@Getter @Setter private Cidade                  cidade           = new Cidade();
	@Getter @Setter private Bairro                  bairroCorrespondencia           = new Bairro();
	@Getter @Setter private Cidade                  cidadeCorrespondencia           = new Cidade();
	@Getter @Setter private Telefone                telefone         = new Telefone();
	@Getter @Setter private Email                   email            = new Email();
	@Getter @Setter private Endereco                endereco         = new Endereco();
	@Getter @Setter private Endereco                enderecoCorrespondencia         = new Endereco();
	@Getter @Setter private Pessoa                  pessoa           = new Pessoa();
	@Getter @Setter private Ramoatuacao             ramo             = new Ramoatuacao();
	@Getter @Setter private Pessoa                  pessoaTemporaria   = new Pessoa();
	@Getter @Setter private DadosBancariosProprietario dadosBancariosProprietario=new DadosBancariosProprietario();
	@Getter @Setter private String                  localPagamentoProprietario              = new String();
	@Getter @Setter private Fiador					fiador			 = new Fiador();
	@Getter @Setter private String                  cpf              = null;
	@Getter @Setter private List<Pessoa>            listaPessoas     = null;
	@Getter @Setter private List<Telefone>          listaTel         = new ArrayList<Telefone>();
	@Getter @Setter private List<Email>             listaEmail       = new ArrayList<Email>();
	@Getter @Setter private List<Referenciapessoal> listaReferencias = new ArrayList<Referenciapessoal>();
	@Getter @Setter private List<Banco> 			listaBancos		 = null;
	@Getter @Setter private List<Imovel>            listaImoveisProprietario = null;
	@Getter @Setter private String                  destinoSalvar    = "";
	@Getter @Setter private boolean                 alteracao        = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public PessoaBean() {
		this.dadosBancariosProprietario=new DadosBancariosProprietario();
		this.localPagamentoProprietario=new String();
		this.fiador			= new Fiador();
		this.pessoa         = new Pessoa();
		this.referencia     = new Referenciapessoal();
		this.bairro         = new Bairro();
		this.bairroCorrespondencia=new Bairro();
		this.cidade         = new Cidade();
		this.cidadeCorrespondencia=new Cidade();
		this.email          = new Email();
		this.ramo           = new Ramoatuacao();
		this.telefone       = new Telefone();
		this.endereco       = new Endereco();
		this.enderecoCorrespondencia=new Endereco();
		this.pessoaTemporaria = new Pessoa();
		this.pessoa.setEndereco(new Endereco());
		this.pessoa.getEndereco().setBairro(new Bairro());
		this.cpf            = "";
		
		carregarListas();
		carregar();
		
		/*pessoa.setPesTipo('F');
		
		if (super.getParametro("tipo", "F").charAt(0) == 'J')
			pessoa.setPesTipo('J');
		else
			pessoa.setPesTipo('F');*/
	}
	
	public void carregarListas() {
		this.listaPessoas = new PessoaRN().listar();
		this.listaBancos= new BancoRN().listar();
	}

	public void carregar() {
		try {

			int pessoaID = this.contextoBean.getParametro("id", -1);

			if (pessoaID <= 0) {
				alteracao = false;
			} else {
				alteracao = true;

				Pessoa imovelCarregado = new PessoaRN().carregar(pessoaID);

				this.pessoa = imovelCarregado;

				if (this.pessoa == null) {
					this.pessoa = new Pessoa();
					this.alteracao = false;
				} else {
					if(!this.pessoa.getPesPreCadastro()){
						this.endereco         = pessoa.getEndereco();
						this.bairro           = pessoa.getEndereco().getBairro();
						this.cidade           = pessoa.getEndereco().getCidade();

					}else{
						this.bairro=new Bairro();
						this.cidade=new Cidade();
						this.endereco=new Endereco();
					}
					this.ramo             = pessoa.getRamoatuacao();
					this.cpf			  =this.pessoa.getPesCpfCnpj();
					this.listaTel         = new TelefoneRN().carregarPessoa(pessoa);
					this.listaEmail       = new EmailRN().carregarPessoa(pessoa);
					this.listaReferencias = new ReferenciapessoalRN().carregarPessoa(pessoa);
					
					// se a pessoa é um proprietario irá carregar a lista de imoveis pertecentes a ele
					if((this.pessoa.getPesProprietario()!=null)&&(this.pessoa.getPesProprietario())){
					this.listaImoveisProprietario=new ImovelRN().listarImoveisPorPropritario(this.pessoa);
					}
					if((this.pessoa.getPesFiador()!=null) &&(this.pessoa.getPesFiador())){
						this.fiador 		  = new FiadorRN().carregarPorPessoa(this.pessoa);
						if(this.fiador==null){
							this.fiador=new Fiador();
						}
					}

					if((this.pessoa.getPesProprietario()!=null)&&(this.pessoa.getPesProprietario())){
						this.dadosBancariosProprietario=this.pessoa.getDadosBancariosProprietario();
						if(this.dadosBancariosProprietario!=null){
							if(this.dadosBancariosProprietario.getDadBanProDepositoBancario()){
								this.localPagamentoProprietario="2";
							}else{
								this.localPagamentoProprietario="1";
							}
						}else{
							this.dadosBancariosProprietario=new DadosBancariosProprietario();
							this.localPagamentoProprietario=new String();
						}
					}
					if(!this.pessoa.getPesPreCadastro()){
						if((this.pessoa.getPesEnderecoCorrespondencia()!=null)&&(this.pessoa.getPesEnderecoCorrespondencia())){
							this.enderecoCorrespondencia=new EnderecoRN().carregar(this.pessoa.getPesIdEnderecoCorrespondencia());
							if(this.enderecoCorrespondencia!=null){
								this.cidadeCorrespondencia=new CidadeRN().carregar(this.enderecoCorrespondencia.getCidade().getCidId());
								this.bairroCorrespondencia=new BairroRN().carregar(this.enderecoCorrespondencia.getBairro().getBaiId());
							}else {
								this.enderecoCorrespondencia=new Endereco();
								this.cidadeCorrespondencia=new Cidade();
								this.bairroCorrespondencia=new Bairro();
							}
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			alteracao = false;
		}
	}
	
	public void gravar() {
		/** Valida CPF/CNPJ */
		if(pessoa.getPesCpfCnpj() != null){
			if(pessoa.getPesCpfCnpj() != ""){
				if (pessoa.getPesTipo() == 'J') {
					if (CpfValidator.validarCNPJ(pessoa.getPesCpfCnpj()) == false) {
						this.contextoBean.mostrarAviso("CNPJ inválido!");
						return; 
					}

				} else {

					if (CpfValidator.validarCPF(pessoa.getPesCpfCnpj()) == false) {
						this.contextoBean.mostrarAviso("CPF inválido!");
						return; 
					}

				}
			}
		}
		if(pessoa.getPesCpfCnpj() ==""){
			this.contextoBean.mostrarAviso("CPF em branco!");
			return;
		}
		
		//Verificar se o locatario for do tipo F2 F3 ou J2 J3 tem e-mail cadastrado
		if(this.pessoa.getPesInquilino()){
			if(this.pessoa.getPesTipoLocatario().equals("F2")||this.pessoa.getPesTipoLocatario().equals("F3")){
				if(this.listaEmail.size()==0){
					this.contextoBean.mostrarErro("Para o  tipo locatário F2 e F3 é obrigatório ter e-mail(s) cadastrado(s)");
					return;
				}
			}
			if(this.pessoa.getPesTipoLocatario().equals("J2")||this.pessoa.getPesTipoLocatario().equals("J3")){
				if(this.listaEmail.size()==0){
					this.contextoBean.mostrarErro("Para o  tipo locatário J2 e J3 é obrigatório ter e-mail(s) cadastrado(s)");
					return;
				}
			}
		}
				
		/** Grava Cidade */
		CidadeRN cidadaRN = new CidadeRN();
		
		if (cidadaRN.buscarPorCidade(cidade.getCidNome()) == null){
			cidadaRN.salvar(this.cidade);
		} else {
			this.cidade = cidadaRN.buscarPorCidade(cidade.getCidNome());
		}
		
		/** Grava Bairro */
		BairroRN bairroRN = new BairroRN();
		
		if (bairroRN.buscarPorBairro(bairro.getBaiNome()) == null) {
			bairroRN.salvar(this.bairro); 
		} else {
			this.bairro = bairroRN.buscarPorBairro(this.bairro.getBaiNome());
		}
		
		//verificar o endereço para correspondencia
		if(this.pessoa.getPesEnderecoCorrespondencia()){
			if((this.enderecoCorrespondencia.getEndNumero()==null) 
					||(this.enderecoCorrespondencia.getEndNumero()== 0)
					||(this.enderecoCorrespondencia.getEndNome()== null)
					||(this.enderecoCorrespondencia.getEndNome()== "")
					||(this.enderecoCorrespondencia.getEndCep()== null)
					||(this.enderecoCorrespondencia.getEndCep()== "")
					||(this.cidadeCorrespondencia.getCidNome()== null)
					||(this.cidadeCorrespondencia.getCidNome()== "")
					||(this.cidadeCorrespondencia.getCidUf()== null)
					||(this.cidadeCorrespondencia.getCidUf()== "")
					||(this.bairroCorrespondencia.getBaiNome()== null)
					||(this.bairroCorrespondencia.getBaiNome()== "")
					){
				this.contextoBean.mostrarErro("Cadastro do endereço para correspondência tem que ser completo");
				return;
			}
			
			if (cidadaRN.buscarPorCidade(this.cidadeCorrespondencia.getCidNome()) == null){
				cidadaRN.salvar(this.cidadeCorrespondencia);
			} else {
				this.cidadeCorrespondencia = cidadaRN.buscarPorCidade(cidadeCorrespondencia.getCidNome());
			}
			
			if (bairroRN.buscarPorBairro(this.bairroCorrespondencia.getBaiNome()) == null) {
				bairroRN.salvar(this.bairroCorrespondencia); 
			} else {
				this.bairroCorrespondencia = bairroRN.buscarPorBairro(this.bairroCorrespondencia.getBaiNome());
			}
		}
		
		/** Endereço */
		String	cep = endereco.getEndCep();
		cep = cep.replaceAll("[.-]", "");
		this.endereco.setEndCep(cep);

		this.endereco.setCidade(cidade);
		this.endereco.setBairro(bairro);

		this.pessoa.setEndereco(endereco);

		endereco.setPessoa(pessoa);

		EnderecoRN endercoRN = new EnderecoRN();
		endercoRN.salvar(this.endereco);
		
		/** Endereço Correspondencia*/
		if(this.pessoa.getPesEnderecoCorrespondencia()){
		String	cepCorrespondencia = this.enderecoCorrespondencia.getEndCep();
		cepCorrespondencia = cepCorrespondencia.replaceAll("[.-]", "");
		this.enderecoCorrespondencia.setEndCep(cepCorrespondencia);
		this.enderecoCorrespondencia.setCidade(this.cidadeCorrespondencia);
		this.enderecoCorrespondencia.setBairro(this.bairroCorrespondencia);
		endercoRN.salvar(this.enderecoCorrespondencia);
		this.pessoa.setPesIdEnderecoCorrespondencia(this.enderecoCorrespondencia.getEndId());
		}
		
		/** Ramo de atividade */
		if (pessoa.getPesTipo() == 'J') {
			this.pessoa.setRamoatuacao(this.ramo);
		}
		
		/** Nacionalidade */
		this.pessoa.setPesNacionalidade("Brasileira");
		
		//dados bancarios do proprietario
		if(this.pessoa.getPesProprietario()){
			this.dadosBancariosProprietario.setDadBanProEmCarteria(false);
			this.dadosBancariosProprietario.setDadBanProDepositoBancario(false);
			if((this.localPagamentoProprietario!=null)&&(this.localPagamentoProprietario.equals("1"))){
				this.dadosBancariosProprietario.setDadBanProEmCarteria(true);
				this.dadosBancariosProprietario.setDadBanProDepositoBancario(false);
			}
			if((this.localPagamentoProprietario!=null)&&(this.localPagamentoProprietario.equals("2"))){
				this.dadosBancariosProprietario.setDadBanProEmCarteria(false);
				this.dadosBancariosProprietario.setDadBanProDepositoBancario(true);
			}
			if(this.dadosBancariosProprietario.getBanco()!=null){
				if(this.dadosBancariosProprietario.getDadBanProContaCorrente()==null){
					this.dadosBancariosProprietario.setDadBanProContaCorrente(true);
					this.dadosBancariosProprietario.setDadBanProPoupanca(false);
				}else if(this.dadosBancariosProprietario.getDadBanProContaCorrente()){
					this.dadosBancariosProprietario.setDadBanProContaCorrente(true);
					this.dadosBancariosProprietario.setDadBanProPoupanca(false);
				}else{
					this.dadosBancariosProprietario.setDadBanProContaCorrente(false);
					this.dadosBancariosProprietario.setDadBanProPoupanca(true);
				}
				this.pessoa.setDadosBancariosProprietario(this.dadosBancariosProprietario);
				new DadosBancariosProprietarioRN().salvar(this.dadosBancariosProprietario);
			}
		}

		/*if (pessoa.getRamoatuacao() != null)
			this.pessoa.setRamoatuacao(ramo);*/
		
		/** Funcionario que está cadastrando a pessoa */
		pessoa.setFuncionario(this.contextoBean.getFuncionarioLogado());
		
		/**  */
		PessoaRN pessoaRN = new PessoaRN();
		String im = pessoa.getPesIm();
		
		if (im != null && !im.equalsIgnoreCase("")) {
			im = im.replaceAll("[./]", "");
			pessoa.setPesIm(im);
		}
		
		im = pessoa.getPesRgIe();
		
		if (im.equalsIgnoreCase("")) {
			im = im.replaceAll("[.-]", "");
			pessoa.setPesRgIe(im);
		}
		
		/** Tipos de pessoa */
		if (pessoa.getPesFiador() == null)
			pessoa.setPesFiador(false);
		
		if (pessoa.getPesFuncionario() == null)
			pessoa.setPesFuncionario(false);
		
		if (pessoa.getPesInquilino() == null)
			pessoa.setPesInquilino(false);
		
		if (pessoa.getPesProprietario() == null)
			pessoa.setPesProprietario(false);
		
		/** Pré-cadastro */
		this.pessoa.setPesPreCadastro(false);
		
		/** Grava pessoa */
		try {
			pessoaRN.salvar(this.pessoa);
		} catch (UtilException e) {
			this.contextoBean.mostrarAviso(e.getMessage());
			return;
		}
		
		/** Email's */
		EmailRN emailRN = new EmailRN();

		for (Email e : listaEmail) {
			e.setPessoa(pessoa);
			emailRN.salvar(e);
		}
		
		/** Telefones */
		TelefoneRN telefoneRN = new TelefoneRN();
		
		for (Telefone f : listaTel) {
			String telefone = f.getTelNumero();
			
			telefone = telefone.replaceAll("[.-]", "");
			telefone = telefone.replaceAll("[)]", "");
			telefone = telefone.replaceAll("[(]", "");
			
			f.setTelNumero(telefone);
			f.setPessoa(pessoa);
			
			telefoneRN.salvar(f);
		}
		
		/** Referências */
		ReferenciapessoalRN refereciaRN = new ReferenciapessoalRN();
		
		for (Referenciapessoal r: listaReferencias){
			r.setPessoa(pessoa);
			refereciaRN.salvar(r);
		}
		
		/** Fiador */
		if(this.pessoa.getPesFiador()==true){
			this.fiador.setPessoa(this.pessoa);
			new FiadorRN().salvar(this.fiador);
		}
		
		/** Deletando ENDEREÇO PARA CORRESPONDÊNCIA, quando for selecionado para não ter endereço correspondencia*/
		if(!this.pessoa.getPesEnderecoCorrespondencia()){
			if(this.pessoa.getPesIdEnderecoCorrespondencia()!=null){
				Endereco end=endercoRN.carregar(this.pessoa.getPesIdEnderecoCorrespondencia());
				if(end!=null){
					endercoRN.excluir(end);
				}
			}
		}
		
		/** Redireciona página */
		this.contextoBean.redirecionarParaPagina("restrito/imovel/cadastroFinalizado.jsf");
	}

	public void excluir() throws IOException {
		PessoaRN pessoaRN = new PessoaRN();

		pessoaRN.excluir(this.pessoa);

		this.listaPessoas = null;
	}

	/**
	 * Inverte o tipo da pessoa (Física/Juridica)
	 */
	public void alterarTipoPessoa() {
		if(pessoa.getPesTipo() ==null){
			pessoa.setPesTipo('F');
			return;
		}
		/*if (pessoa.getPesTipo() == 'J')
			pessoa.setPesTipo('F');
		else
			pessoa.setPesTipo('J');*/
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
		
		this.pessoaTemporaria = new PessoaRN().carregar(telefoneTeste.getPessoa().getPesId());
		if(this.pessoaTemporaria!=null){
		RequestContext.getCurrentInstance().execute("PF('confirmacao').show()");
		}
	}

	public void removerEmail(Email e) {
		if(listaEmail == null)
			return;
		
		listaEmail.remove(e);
		//pessoa.getEmails().remove(e);
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
		this.pessoaTemporaria = new PessoaRN().carregar(emailTeste.getPessoa().getPesId());
		
		if(this.pessoaTemporaria!=null){
			RequestContext.getCurrentInstance().execute("PF('confirmacao').show()");
			}
	}

	public void removerTel(Telefone t){
		if(listaTel != null){
			this.listaTel.remove(t);
			this.telefone = new Telefone();
		}
	}

	public void removerReferencia(Referenciapessoal e) {
		if(listaReferencias != null){
			this.listaReferencias.remove(e);
			pessoa.getReferenciapessoals().remove(e);
			this.referencia = new Referenciapessoal();
		}
	}
	
	public void addReferencia() {
		if(!this.referencia.getRefNome().equalsIgnoreCase("")){
			/*if(this.referencia.getRefTipo()==null || this.referencia.getRefTipo().equals("")){
				super.mostrarErro("Campo Tipo  da referencia obrigatorio");
				return;
			}
			if(this.referencia.getRefContato()==null || this.referencia.getRefContato().equals("")){
				super.mostrarErro("Campo contato obrigatorio");
				return;
			}
			if(this.referencia.getRefTelefone1()==null || this.referencia.getRefTelefone1().equals("")){
				super.mostrarErro("Campo Telefone 1, obrigatorio");
				return;
			}
			if(this.referencia.getRefEmail1()==null || this.referencia.getRefEmail1().equals("")){
				super.mostrarErro("Campo Email 1, obrigatorio");
				return;
			}*/
			referencia.setPessoa(pessoa);
			this.listaReferencias.add(referencia);
			this.referencia=new Referenciapessoal();
		}
	}

		//TODO: codigo do mario , validado de cpf e cnpj
	public void confirmaCPF(String CPF) {
		String CPFcomCaracteres=CPF;

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
				boolean encontrou=new PesquisaRN().existeCPF(CPF);
				boolean encontrou2=new PesquisaRN().existeCPF(CPFcomCaracteres);
				if(encontrou||encontrou2){
					PessoaRN pessoaRN=new PessoaRN();
					this.pessoa.setPesCpfCnpj(CPF);
					this.pessoaTemporaria=pessoaRN.carregarPessoaPorCPF_CNPJ(this.pessoa);
					//se não for encontrada uma pessoa com o cpf sem carecteres, é buscado cadastrada com caracteres
					if(this.pessoaTemporaria==null){
						this.pessoaTemporaria=pessoaRN.carregarPessoaPorCPF_CNPJ(CPFcomCaracteres);
					}
					if(this.pessoaTemporaria!=null){
						RequestContext.getCurrentInstance().execute("PF('confirmacao').show()");
						this.cpf=null;
						this.pessoa.setPesCpfCnpj(this.cpf);
						}
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
			this.pessoa.setPesCpfCnpj(this.cpf);
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



	public static boolean isCnpjValido(String cnpj) {
		if (!cnpj.substring(0, 1).equals("")) {
			try {
				cnpj = cnpj.replace('.', ' ');// onde h� ponto coloca espa�o
				cnpj = cnpj.replace('/', ' ');// onde h� barra coloca espa�o
				cnpj = cnpj.replace('-', ' ');// onde h� tra�o coloca espa�o
				cnpj = cnpj.replaceAll(" ", "");// retira espa�o
				int soma = 0, dig;
				String cnpj_calc = cnpj.substring(0, 12);

				if (cnpj.length() != 14) {
					return false;
				}
				char[] chr_cnpj = cnpj.toCharArray();
				/* Primeira parte */
				for (int i = 0; i < 4; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
						soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
					}
				}
				dig = 11 - (soma % 11);
				cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer
						.toString(dig);
				/* Segunda parte */
				soma = 0;
				for (int i = 0; i < 5; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
						soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
					}
				}
				dig = 11 - (soma % 11);
				cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer
						.toString(dig);
				return cnpj.equals(cnpj_calc);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public void confirmaCNPJ(String CNPJ) {
		CNPJ = CNPJ.replaceAll("[.-]", "");
		CNPJ = CNPJ.replaceAll("[/]", "");
		if(CNPJ==""){
			this.cpf=new String();
			return;
		}
		// System.out.println("CNPJ 0 Convertido: "+CNPJ);
		if (isCnpjValido(CNPJ) == true) {
			if (CNPJ != null && !CNPJ.equals("") && CNPJ.length() == 14) {
				/*
				 * CNPJ = CNPJ.substring(0, 3) + "." + CNPJ.substring(3, 6) +
				 * "." + CNPJ.substring(6, 9) + "-" + CNPJ.substring(9, 11);
				 */
				// System.out.println("CNPJ Convertido: "+CNPJ);
				this.pessoa.setPesCpfCnpj(CNPJ);
				PesquisaRN pesquisaRN=new PesquisaRN();
				if(pesquisaRN.existeCPF(CNPJ)==true){
					PessoaRN pessoaRN=new PessoaRN();
					this.pessoaTemporaria=pessoaRN.carregarPessoaPorCPF_CNPJ(this.pessoa);
					if(this.pessoaTemporaria!=null){
						RequestContext.getCurrentInstance().execute("PF('confirmacao').show()");
					}
				}
				// System.out.println("PesCpfCnpj: "+pessoa.getPesCpfCnpj());
			}

		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Campos invalidos", "CNPJ invalido !");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
			// System.out.println("CPF invalido !"+CPF);
		}

	}
	
}