package br.com.locadora.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.CroppedImage;

import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.ChaveNoQuadro;
import br.com.locadora.modelo.Cidade;
import br.com.locadora.modelo.Email;
import br.com.locadora.modelo.EmprestimoChave;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Filial;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.QuadroDeChaves;
import br.com.locadora.modelo.Ramoatuacao;
import br.com.locadora.modelo.Situacaoimovel;
import br.com.locadora.modelo.Telefone;
import br.com.locadora.rn.BairroRN;
import br.com.locadora.rn.ChaveNoQuadroRN;
import br.com.locadora.rn.CidadeRN;
import br.com.locadora.rn.EmailRN;
import br.com.locadora.rn.EmprestimoChaveRN;
import br.com.locadora.rn.EnderecoRN;
import br.com.locadora.rn.FilialRN;
import br.com.locadora.rn.FuncionarioRN;
import br.com.locadora.rn.ImovelRN;
import br.com.locadora.rn.PesquisaRN;
import br.com.locadora.rn.PessoaRN;
import br.com.locadora.rn.QuadroDeChavesRN;
import br.com.locadora.rn.RamoatuacaoRN;
import br.com.locadora.rn.TelefoneRN;
import br.com.locadora.util.UtilException;
import br.com.locadora.web.util.CepWebService;
import br.com.locadora.web.util.ContextoUtil;
import br.com.locadora.web.util.CpfValidator;

@ManagedBean(name = "emprestimoChaveBean")
@ViewScoped
public class EmprestimoChaveBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8720538999681044701L;

	@Getter @Setter 	private Filial filial = new Filial();
	@Getter @Setter		private List<Imovel> listaImovel = new ArrayList<Imovel>();
	@Getter @Setter		private List<Imovel> listaImovelEscolhido = new ArrayList<Imovel>();
	@Setter		private List<Filial> listaFiliaisTemChave;
	@Getter @Setter		private Pessoa pessoa = new Pessoa();
	@Getter @Setter		private Bairro bairro = new Bairro();
	@Getter @Setter		private Cidade cidade = new Cidade();
	@Getter @Setter		private Endereco endereco = new Endereco();
	@Getter @Setter		private String cpf = null;
	@Getter @Setter		private Ramoatuacao ramo = new Ramoatuacao();
	@Getter @Setter		private Telefone telefone = new Telefone();
	@Getter @Setter		private Email email = new Email();
	@Getter @Setter		private Imovel imovel=new Imovel();
	@Getter @Setter		private Imovel imovelDevolvido=new Imovel();
	@Getter @Setter		private List<Telefone> listaTel = new ArrayList<Telefone>();
	@Getter @Setter		private List<Email> listaEmail = new ArrayList<Email>();
	@Getter @Setter		private EmprestimoChave emprestimoChave = new EmprestimoChave();
	@Getter @Setter		private Pessoa pessoaDevendoChave=new Pessoa();
	@Getter @Setter		private List<Imovel> listaImoveisDevolucaoChave;
	@Getter @Setter private String obsParecer=new String();
	@Getter @Setter private boolean menssagemSalvar=false;
	@Getter @Setter private boolean pessoaCPFcarregada=false;
	@Getter @Setter private boolean alocarImovel=false;
	@Getter @Setter private boolean faltaInformacaoCliente=false;

	//@Getter @Setter		private String obsEmprestimo=new String();
	@Getter @Setter private List<EmprestimoChave> listaEmprestimoOrdenadoPorPessoa;
	private List<EmprestimoChave> listaEmprestimos;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	//Variaveis Locais
	@SuppressWarnings("unused")
	private boolean alteracao = false;

	@Getter @Setter		private CroppedImage imagemRecortada;
	@Getter @Setter		private String foto;
	@Getter @Setter		private String fotoRecortada;
	@Getter @Setter		private String arquivoFoto;
	@Getter @Setter		private String arquivoFotoRecortada;
	@Getter @Setter		private boolean exibeImagemCapturada;
	@Getter @Setter		private ServletContext servletContext;
	
	//variaveis para geração do relatorio PDF do emprestimo de chave
	@Getter @Setter private Integer codigo_pessoa=0;
	@Getter @Setter private Integer codigo_emprestimo=0;
	@Getter @Setter private Integer codigo_funcionario=0;

	public EmprestimoChaveBean() {
		exibeImagemCapturada = false;
		servletContext = (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();
		carregarEmprestimo();
		carregarListas();
	}
	public void carregarEmprestimo(){
		
		this.pessoa=new Pessoa();
		this.endereco=new Endereco();
		
		int emprestimoID= this.contextoBean.getParametro("imovel", -1); 
		this.imovel= new ImovelRN().carregar(contextoBean.getParametro("imovel", -1));
		int idPessoa=(contextoBean.getParametro("pesId", -1));
		this.telefone.setTelNumero(contextoBean.getParametro("tel", ""));
		this.telefone.setTelTipo(contextoBean.getParametro("tipoTel",""));
		this.pessoa.setPesNome(contextoBean.getParametro("nome",""));
		
		this.telefone.setTelSms('S');
		//Caso não receba o tipo do celular, irá receber o tipo celular como default
		if(this.telefone.getTelTipo()==""){
		this.telefone.setTelTipo("CELULAR");
		}
		this.listaTel.add(this.telefone);
		
		if(idPessoa>0){
			this.pessoa=new PessoaRN().carregar(idPessoa);
			this.endereco=new EnderecoRN().carregar(this.pessoa.getEndereco().getEndId());
			this.listaEmail= new EmailRN().carregarPessoa(this.pessoa);
			this.listaTel=new TelefoneRN().carregarPessoa(this.pessoa);
			this.bairro=new BairroRN().carregar(this.endereco.getBairro().getBaiId());
			this.cidade=new CidadeRN().carregar(this.endereco.getCidade().getCidId());
			this.email = new Email();
			this.ramo = this.pessoa.getRamoatuacao();
			this.telefone = new Telefone();
			this.emprestimoChave = new EmprestimoChave();
			this.filial=new Filial();
			this.cpf=this.pessoa.getPesCpfCnpj();
			Calendar data = Calendar.getInstance();  
			data.set(Calendar.HOUR_OF_DAY,17);   
			data.set(Calendar.MINUTE,30);
			this.emprestimoChave.setEmpDataLimiteDevolucao(data.getTime());
			if(this.cpf!=null||this.cpf!=""){
				this.pessoaCPFcarregada=true;
			}
			//this.fotoRecortada=this.pessoa.getPesFoto(); Atenção: a foto já existe porem carrega-la tem que refazer a o componete de imagem recortada. O problema está no carminho da imagem...
			return;
		}
		
				
		this.telefone=new Telefone();
		
		if (emprestimoID <= 0) {
			alteracao = false;
		} else {
			alteracao = true;

			this.imovel= new ImovelRN().carregar(emprestimoID);
		}
		this.bairro = new Bairro();
		this.cidade = new Cidade();
		this.email = new Email();
		this.ramo = new Ramoatuacao();
		this.telefone = new Telefone();
		this.emprestimoChave = new EmprestimoChave();
		this.filial=new Filial();
		this.cpf=null;
		
		//this.pessoa=new Pessoa();
		this.endereco=new Endereco();
		
		//determinando a data da devolução da chave

		Calendar data = Calendar.getInstance();  
		data.set(Calendar.HOUR_OF_DAY,17);   
		data.set(Calendar.MINUTE,30);
		this.emprestimoChave.setEmpDataLimiteDevolucao(data.getTime());
		
	}
	public void carregarListas(){

	//	this.listaEmail = new ArrayList<Email>();
	//	this.listaTel = new ArrayList<Telefone>();
		this.listaEmprestimos=null;
		this.listaImovel=new ArrayList<Imovel>();
		this.listaImovelEscolhido=new ArrayList<Imovel>();
		this.listaFiliaisTemChave=null;
		this.listaEmprestimoOrdenadoPorPessoa=new EmprestimoChaveRN().listaEmprestimoOrdenadaPorPessoa();
	}
	private String getNumeroRandomico() {
		int i = (int) (Math.random() * 10000);
		return String.valueOf(i);
	}

	private void criaArquivo(String arquivo, byte[] dados) {
		FileImageOutputStream imageOutput;
		try {
			System.out.println("Carminho: "+arquivo);
			imageOutput = new FileImageOutputStream(new File(arquivo));
			imageOutput.write(dados, 0, dados.length);
			imageOutput.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(EmprestimoChaveBean.class.getName()).log(
					Level.SEVERE, null, ex);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Caminho não encontrado!", "Erro"));
		} catch (IOException ex) {
			Logger.getLogger(EmprestimoChaveBean.class.getName()).log(
					Level.SEVERE, null, ex);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro ao criar arquivo!", "Erro"));
		}
	}

	public void recortar() {
		verificaExistenciaArquivo(arquivoFotoRecortada);
		fotoRecortada = this.pessoa.getPesNome() + "_" + getNumeroRandomico()
				+ ".png";
		this.pessoa.setPesFoto("fotosPessoas/"+this.pessoa.getPesId()+"/" + fotoRecortada);
		this.arquivoFotoRecortada = servletContext.getRealPath(File.separator
				+ "resources" + File.separator + "fotosPessoas"
				+ File.separator + this.pessoa.getPesId()+ File.separator+fotoRecortada);
		
		File diretorio = new File(servletContext.getRealPath(File.separator
				+ "resources" + File.separator + "fotosPessoas"
				+ File.separator + this.pessoa.getPesId()+ File.separator));
		diretorio.mkdirs();
		
		criaArquivo(arquivoFotoRecortada, imagemRecortada.getBytes());
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Foto RECORTADA com sucesso!", "Informação"));
		System.out.println(arquivoFotoRecortada);		
		
	}

	public void oncapture(CaptureEvent captureEvent) {
		verificaExistenciaArquivo(arquivoFoto);
		foto = "foto" + getNumeroRandomico() + ".png";
		arquivoFoto = servletContext.getRealPath(File.separator + "resources"
				+ File.separator + "fotosPessoas" + File.separator
				+ "temporarias" + File.separator + foto);
		criaArquivo(arquivoFoto, captureEvent.getData());
		exibeImagemCapturada = true;
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Foto CAPTURADA com sucesso!", "Informação"));
	}

	private void verificaExistenciaArquivo(String arquivo) {
		if (arquivo != null) {
			File file = new File(arquivo);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void escolhaFilial() {
		this.listaImovelEscolhido = new ArrayList<Imovel>();
		this.listaImovel = new ArrayList<Imovel>();
		QuadroDeChavesRN quadroDeChavesRN = new QuadroDeChavesRN();
		QuadroDeChaves quadroDeChaves = quadroDeChavesRN
				.buscarPorFilial(this.filial.getFilId());
		ChaveNoQuadroRN chaveNoQuadroRN = new ChaveNoQuadroRN();
		List<ChaveNoQuadro> listaChaveNoQuadros = chaveNoQuadroRN
				.carregarChaveNoQuadroPorFilial(quadroDeChaves.getQuaId());
		for (ChaveNoQuadro c : listaChaveNoQuadros) {
			if((c.getEmprestimoChave()==null)){
				this.listaImovel.add(c.getImovel());}
			// this.listaImovel.add(new
			// ImovelRN().carregar(c.getChave().getImovel().getImoId()));
		}

	}

	public void carregarImoveisEscolhidos() {
		List<Imovel> lista = this.listaImovelEscolhido;
		this.listaImovelEscolhido = new ArrayList<Imovel>();
		ImovelRN imovelRN = new ImovelRN();
		for (Imovel i : lista) {
			this.listaImovelEscolhido.add(imovelRN.carregar(i.getImoId()));
		}
	}



	public String carregarTipoImovel(int imo) {
		return new ImovelRN().carregar(imo).getTipoimovel().getTipNome();
	}

	public String carregarBairroImovel(int imo) {
		return new ImovelRN().carregar(imo).getEndereco().getBairro()
				.getBaiNome();
	}

	public void confirmaCPF(String CPF) {
		String CPFcomCaracteres=CPF;

		if (isCPF(CPF) == true) {
			CPF = CPF.replaceAll("[.-]", "");
			if (CPF != null && !CPF.equals("") && CPF.length() == 11) {				
				///verifica se o cpf já existe no banco
				boolean encontrou=new PesquisaRN().existeCPF(CPF);
				boolean encontrou2=new PesquisaRN().existeCPF(CPFcomCaracteres);
				if(encontrou||encontrou2){
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
				//this.cpf=null;
				this.pessoa.setPesCpfCnpj(this.cpf);
			}

		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Campos invalidos", "CPF invalido !");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
			this.cpf=null;
			this.pessoa.setPesCpfCnpj(this.cpf);
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
				cnpj = cnpj.replace('.', ' ');// onde hï¿½ ponto coloca espaï¿½o
				cnpj = cnpj.replace('/', ' ');// onde hï¿½ barra coloca espaï¿½o
				cnpj = cnpj.replace('-', ' ');// onde hï¿½ traï¿½o coloca espaï¿½o
				cnpj = cnpj.replaceAll(" ", "");// retira espaï¿½o
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
		// System.out.println("CNPJ 0 Convertido: "+CNPJ);
		if (isCnpjValido(CNPJ) == true) {
			if (CNPJ != null && !CNPJ.equals("") && CNPJ.length() == 14) {
				/*
				 * CNPJ = CNPJ.substring(0, 3) + "." + CNPJ.substring(3, 6) +
				 * "." + CNPJ.substring(6, 9) + "-" + CNPJ.substring(9, 11);
				 */
				// System.out.println("CNPJ Convertido: "+CNPJ);
				this.pessoa.setPesCpfCnpj(CNPJ);
				// System.out.println("PesCpfCnpj: "+pessoa.getPesCpfCnpj());
			}

		} else {
			this.cpf=new String();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Campos invalidos", "CNPJ invalido !");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
			// System.out.println("CPF invalido !"+CPF);
		}

	}

	public void addTel() {
		String tel = telefone.getTelNumero();
		tel = tel.replaceAll("[.-]", "");
		tel = tel.replaceAll("[)]", "");
		tel = tel.replaceAll("[(]", "");
		TelefoneRN telefoneRN=new TelefoneRN();
		Telefone telefoneTeste=telefoneRN.buscarPorTelefone(tel);
		if(telefoneTeste==null){
			this.listaTel.add(telefone);
			this.telefone = new Telefone();
			return;}
		Pessoa pes=new PessoaRN().carregar(telefoneTeste.getPessoa().getPesId());
		FacesContext
		.getCurrentInstance()
		.addMessage(
				null,
				new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"Cadastro não efetuado - Telefone já existente !",
						"Telefone pertencente ao Sr. "+pes.getPesNome()+" Codigo: "+pes.getPesId()));
	}

	public void removerEmail(Email e) {
		if (listaEmail != null) {
			this.listaEmail.remove(e);
			//pessoa.getEmails().remove(e);
			this.email = new Email();
		}
	}

	public void addEmail() {
		// System.out.println("Email: "+email.getEmaEndereco());
		EmailRN emailRN=new EmailRN();
		Email emailTeste=emailRN.buscarPorEmail(email.getEmaEndereco());
		if(emailTeste==null){
			this.listaEmail.add(email);
			this.email = new Email();
			return;}
		Pessoa pes=new PessoaRN().carregar(emailTeste.getPessoa().getPesId());
		FacesContext
		.getCurrentInstance()
		.addMessage(
				null,
				new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"Cadastro não efetuado - Email já existente !",
						"Email pertencente ao Sr. "+pes.getPesNome()+" Codigo: "+pes.getPesId()));
	}

	public void removerTel(Telefone t) {
		 if(this.listaTel!=null){
		this.listaTel.remove(t);
		this.telefone = new Telefone();
		 }
	}

	// }
	public void encontraCEP() {
		// System.out.println("CEP: "+endereco.getEndCep());
		CepWebService cepWebService = new CepWebService(endereco.getEndCep());

		if ((cepWebService.getResultado() == 1)) {

			this.endereco.setEndNome(null);
			this.bairro = new Bairro();
			endereco.setEndNome(cepWebService.getTipoLogradouro() + " "
					+ cepWebService.getLogradouro());
			cidade.setCidUf(cepWebService.getEstado());
			cidade.setCidNome(cepWebService.getCidade());
			bairro.setBaiNome(cepWebService.getBairro());
			endereco.setEndNumero(null);
			// System.out.println("OK "+cidade.getCidNome()+bairro.getBaiNome());
		} else if ((cepWebService.getResultado() == 2)) {
			this.endereco.setEndNome(null);
			this.bairro = new Bairro();
			cidade.setCidUf(cepWebService.getEstado());
			cidade.setCidNome(cepWebService.getCidade());
			bairro.setBaiNome(cepWebService.getBairro());
			endereco.setEndNumero(null);
		} else {
			endereco.setEndNumero(null);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Servidor não estão respondendo",
							"Cep: não encontrado"));
		}
	}

	public String eventoProsseguir(FlowEvent event) {
		return event.getNewStep();
	}

	public void salvar() throws IOException {
		this.pessoa.setPesTipo('F');
		/** Valida CPF/CNPJ */
		if(pessoa.getPesCpfCnpj() == null){
			contextoBean.mostrarAviso("CPF não preenchido!");
			return;
		}
		if(pessoa.getPesCpfCnpj() == ""){
			contextoBean.mostrarAviso("CPF não preenchido!");
			return;
		}
		if(pessoa.getPesCpfCnpj() != null){
			if(pessoa.getPesCpfCnpj() != ""){
				if (pessoa.getPesTipo() == 'J') {
					if (CpfValidator.validarCNPJ(pessoa.getPesCpfCnpj()) == true) {
						contextoBean.mostrarAviso("CNPJ inválido!");
						return; 
					}

				} else {
					
					//esse if é porque está com problemas para confirma o cpf de uma pessoa que está carregada na pagina
					if(!this.pessoaCPFcarregada){
						//Mudei a resposta para != false, porque estava retornando false na validação CPF.
						if (CpfValidator.validarCPF(pessoa.getPesCpfCnpj()) != true) {
							contextoBean.mostrarAviso("CPF inválido!");
							return; 
						}
					}

				}
			}
		}
		if(this.listaEmail.size()==0){
			if(this.menssagemSalvar==false){
				contextoBean.mostrarAviso("Tem certeza em salvar emprestimo de chave sem e-mail cadastros para o Sr. "+this.pessoa.getPesNome()+"? Caso sim, click em cadastrar emprestimo novamente");
			this.menssagemSalvar=true;
			return;}
		}
		CidadeRN cidadaRN = new CidadeRN();
		if (cidadaRN.buscarPorCidade(cidade.getCidNome()) == null) {
			cidadaRN.salvar(this.cidade);
		} else {
			this.cidade = cidadaRN.buscarPorCidade(cidade.getCidNome());
		}

		BairroRN bairroRN = new BairroRN();
		if (bairroRN.buscarPorBairro(bairro.getBaiNome()) == null) {
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							"Não há região cadastrada para esse bairro !",
							" Entre em contato com o administrador do sistema para cadastrar a região desse bairro! Atenção: reinicie a pagina"));
			this.bairro = new Bairro();
			this.cidade = new Cidade();
			this.email = new Email();
			this.pessoa = new Pessoa();
			this.ramo = new Ramoatuacao();
			this.telefone = new Telefone();
			this.endereco = new Endereco();
			this.emprestimoChave = new EmprestimoChave();
			this.listaEmail = null;
			this.listaTel = null;
			this.listaEmprestimos=null;

			return;
			// bairroRN.salvar(this.bairro);
		} else {
			this.bairro = bairroRN.buscarPorBairro(this.bairro.getBaiNome());
		}
		if(this.pessoa.getPesId()<1){
			if((this.pessoa.getPesCpfCnpj()!="")||(this.pessoa.getPesCpfCnpj()!=null)){
				if(new PesquisaRN().existeCPF(this.pessoa.getPesCpfCnpj())){
					FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"CPF já cadastrado em nosso banco de dados !",
									"  "));
					return;
				}
			}
		}
		if((this.pessoa.getPesFoto()==null)||(this.pessoa.getPesFoto().equalsIgnoreCase(""))){
			contextoBean.mostrarErro("Foto obrigatório");
		return;
		}

		//deletando a foto temporaria
		File destino = new File(this.arquivoFoto);   
		try {  
			destino.delete();  
		} catch (Exception e) {  
			throw new RuntimeException("Erro ao deletar imagem", e);  
		}

		String cep = endereco.getEndCep();
		cep = cep.replaceAll("[.-]", "");
		this.endereco.setEndCep(cep);

		this.endereco.setCidade(cidade);
		this.endereco.setBairro(bairro);

		endereco.setPessoa(pessoa);

		this.pessoa.setEndereco(endereco);
		if(this.pessoa.getPesId()<1){ // se a pessoa não existe, é adicionado um ramo de atuação para ele
		Ramoatuacao ramo=new RamoatuacaoRN().buscarPorRamoatuacao("OUTROS");
		this.pessoa.setRamoatuacao(ramo);
		}
		EnderecoRN endercoRN = new EnderecoRN();
		if(this.pessoa.getPesId()>0){
			this.endereco.setEndId(this.pessoa.getEndereco().getEndId());
		}
		endercoRN.salvar(this.endereco);

		
		pessoa.setFuncionario(contextoBean.getFuncionarioLogado());
		if(this.pessoa.getPesId()<=1){
		this.pessoa.setPesPreCadastro(true);}

		PessoaRN pessoaRN = new PessoaRN();
		String im = pessoa.getPesIm();
		if (im == "") {
			im = im.replaceAll("[./]", "");
			pessoa.setPesIm(im);
		}
		im = pessoa.getPesRgIe();
		if (im == "") {
			im = im.replaceAll("[.-]", "");
			pessoa.setPesRgIe(im);
		}
		if(this.pessoa.getPesId()<1){
		this.pessoa.setPesInquilino(false);
		this.pessoa.setPesProprietario(false);
		this.pessoa.setPesFuncionario(false);
		this.pessoa.setPesFiador(false);
		this.pessoa.setPesPreCadastro(true);
		}
		try {
			pessoaRN.salvar(this.pessoa);
		} catch (UtilException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return ;
		}

		EmailRN emailRN = new EmailRN();
		TelefoneRN telefoneRN = new TelefoneRN();
		for (Telefone f : listaTel) {
			String tel = f.getTelNumero();
			tel = tel.replaceAll("[.-]", "");
			tel = tel.replaceAll("[)]", "");
			tel = tel.replaceAll("[(]", "");
			f.setTelNumero(tel);
			f.setPessoa(pessoa);
			telefoneRN.salvar(f);
		}

		for (Email e : listaEmail) {
			e.setPessoa(pessoa);
			emailRN.salvar(e);
		}
	/*	ChaveNoQuadro chaveNoQuadro = new ChaveNoQuadro();
		ChaveNoQuadroRN chaveNoQuadroRN = new ChaveNoQuadroRN();
		QuadroDeChaves quadroDeChaves = new QuadroDeChavesRN()
		.buscarPorFilial(this.filial.getFilId());

		EmprestimoChaveRN emprestimoChaveRN = new EmprestimoChaveRN();
		for (Imovel imo : this.listaImovelEscolhido) {
			this.emprestimoChave.setEmpDevolucao(false);
			emprestimoChave.setFuncionario(super.getFuncionario());
			this.emprestimoChave.setEmpDataEmprestimo(new Date());
			this.emprestimoChave.setEmpObservacaoParecer(this.obsEmprestimo);
			this.emprestimoChave.setEmpDevolucao(false);
			this.emprestimoChave.setPessoa(pessoa);
			this.emprestimoChave.setEmpFuncionario(false);
			this.emprestimoChave.setImovel(imo);
			emprestimoChaveRN.salvar(this.emprestimoChave);

			chaveNoQuadro = chaveNoQuadroRN.buscarChaveNoQuadro(
					quadroDeChaves.getQuaId(), imo.getImoId());
			chaveNoQuadro.setEmprestimoChave(this.emprestimoChave);
			chaveNoQuadroRN.salvar(chaveNoQuadro);
		}*/
		EmprestimoChaveRN emprestimoChaveRN = new EmprestimoChaveRN();
		ChaveNoQuadro chaveNoQuadro = new ChaveNoQuadro();
		ChaveNoQuadroRN chaveNoQuadroRN = new ChaveNoQuadroRN();
		QuadroDeChaves quadroDeChaves = new QuadroDeChavesRN()
		.buscarPorFilial(this.contextoBean.getFuncionarioLogado().getFilial().getFilId());

		this.emprestimoChave.setEmpDevolucao(false);
		this.emprestimoChave.setFuncionario(this.contextoBean.getFuncionarioLogado());
		this.emprestimoChave.setEmpDataEmprestimo(new Date());
		this.emprestimoChave.setEmpDevolucao(false);
		this.emprestimoChave.setPessoa(this.pessoa);
		this.emprestimoChave.setEmpFuncionario(false);
		this.emprestimoChave.setImovel(this.imovel);
		emprestimoChaveRN.salvar(this.emprestimoChave);

		chaveNoQuadro = chaveNoQuadroRN.buscarChaveNoQuadro(
				quadroDeChaves.getQuaId(), this.imovel.getImoId());
		chaveNoQuadro.setEmprestimoChave(this.emprestimoChave);
		chaveNoQuadroRN.salvar(chaveNoQuadro);

		/*FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Salvo com sucesso"));*/
		
		// novo contrato será gerado com relatorio e não com o documento word
		//gerarContrato();
		
		//atribuindo os codigos para geração do relatorio
		this.codigo_emprestimo=this.emprestimoChave.getEmpId();
		this.codigo_funcionario=this.emprestimoChave.getFuncionario().getPesId();
		this.codigo_pessoa=this.emprestimoChave.getPessoa().getPesId();
		
		carregar();
		carregarListas();
		
		//gerando relatorio
		try {
			gerarRelatorio();
		} catch (UtilException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	//	super.redirecionarParaPagina("restrito/consulta/pesquisaimovel.jsf");
	}

	public void refresh() {  
		FacesContext context = FacesContext.getCurrentInstance();  
		Application application = context.getApplication();  
		ViewHandler viewHandler = application.getViewHandler();  
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());  
		context.setViewRoot(viewRoot);  
		context.renderResponse();  
	}

	public List<EmprestimoChave> getListaEmprestimos() {
		if (this.listaEmprestimos == null) {
			EmprestimoChaveRN emprestimoChaveRN = new EmprestimoChaveRN();
			this.listaEmprestimos = emprestimoChaveRN.listar();
		}
		return listaEmprestimos;
	}

	public void setListaEmprestimos(List<EmprestimoChave> listaEmprestimos) {
		this.listaEmprestimos = listaEmprestimos;
	}

	public String buscarChave(EmprestimoChave emprestChave) {
		ChaveNoQuadroRN chaveNoQuadroRN = new ChaveNoQuadroRN();
		ChaveNoQuadro chaveNoQuadro = chaveNoQuadroRN
				.buscarChaveAtravesDoEmprestimo(emprestChave);
		if(chaveNoQuadro==null){
			return "Chave já devolvida ao quadro";
		}
		return "Codigo da chave: " + " posição no quadro";
	}

	public void carregar() {
		this.pessoa = new PessoaRN().carregar(this.emprestimoChave.getPessoa()
				.getPesId());
		this.endereco = new EnderecoRN().carregar(this.pessoa.getEndereco()
				.getEndId());
	}

	public void devolver() {
		if (this.emprestimoChave.getEmpDevolucao() == false) {
			this.emprestimoChave.setEmpDevolucao(true);
			ChaveNoQuadro chaveNoQuadro = new ChaveNoQuadroRN()
			.buscarChaveAtravesDoEmprestimo(emprestimoChave);
			chaveNoQuadro.setEmprestimoChave(null);
			new EmprestimoChaveRN().salvar(this.emprestimoChave);
			new ChaveNoQuadroRN().salvar(chaveNoQuadro);

			/// na devoluÃ§Ã£o, olha a listagem , porque se tiver mais de 1, tem que dar baixa .
			/// tem que ter mais analise nessa parte.

			this.listaEmprestimos = null;
			this.emprestimoChave = new EmprestimoChave();
		}
	}

	public String buscarPessoa(EmprestimoChave emp){
		Pessoa pessoa=new PessoaRN().carregar(emp.getPessoa().getPesId());
		if(pessoa==null){
			return "Não há pessoa";
		}
		return ""+pessoa.getPesNome();
	}


	public List<Pessoa> pesquisarNome(String query){
		//	this.pessoa.setPesNome(query);
		PessoaRN pessoaRN=new PessoaRN();
		List<Pessoa> pes=new ArrayList<Pessoa>();
		for(Pessoa p: pessoaRN.listar()){

			//passando a string para minuscula e verificando se existe nome inicial com a variavel query (* seleciona todas as string)
			if((p.getPesNome().toLowerCase().startsWith(query.toLowerCase()))||(query.equals("*"))){
				/*	if(p.getPesFoto().equals(null)){
					p.setPesFoto("upload/nao-encontrada.jpg");
				}*/
				pes.add(p);
			}
			
			for(int i = 0; i < pes.size(); i++) {  
				for(int j = 1; j < pes.size(); j++) {  
					if(pes.get(i).getPesId()==pes.get(j).getPesId()){  
						pes.remove(j);  
					}  
				}  
			}  
		}
		return pes;
	}

	public List<Pessoa> pesquisarNomeDevendoChave(String query){
		//	this.pessoa.setPesNome(query);
		EmprestimoChaveRN emprestimoChaveRN=new EmprestimoChaveRN();
		List<Pessoa> pes=new ArrayList<Pessoa>();
		for(EmprestimoChave p: emprestimoChaveRN.listar()){

			//passando a string para minuscula e verificando se existe nome inicial com a variavel query (* seleciona todas as string)
			if((p.getPessoa().getPesNome().toLowerCase().startsWith(query.toLowerCase()))||(query.equals("*"))){
				if(p.getEmpDevolucao()==false){
					pes.add(p.getPessoa());}
			}
		}
		/*for(int i = 0; i < pes.size(); i++) {  
			for(int j = 1; j < pes.size(); j++) {  
				if(pes.get(i).getPesNome()==pes.get(j).getPesNome()){  
					pes.remove(j);  
				}  
			}  
		} */
		// Em ordem crescente 
        Collections.sort (pes, new Comparator<Pessoa>() {  
            public int compare(Pessoa o1, Pessoa o2) {  
                Pessoa p1 = (Pessoa) o1;  
                Pessoa p2 = (Pessoa) o2;  
                return p1.getPesId() < p2.getPesId() ? -1 : (p1.getPesId() > p2.getPesId() ? +1 : 0);  
            }  
        });  
		return pes;
	}

	public void handleSelect(SelectEvent event) {  
		this.pessoaDevendoChave=new Pessoa();
		this.pessoaDevendoChave=((Pessoa) event.getObject());
		
	//	this.pessoaDevendoChave=new PessoaRN().carregar(this.pessoaDevendoChave.getPesId());

		this.listaImoveisDevolucaoChave=new ArrayList<Imovel>();

		EmprestimoChaveRN emprestimoChaveRN=new EmprestimoChaveRN();
		List<EmprestimoChave> listaEmprestimosPorPessoa=emprestimoChaveRN.carregarEmprestimoPorPessoa(this.pessoaDevendoChave);
		ImovelRN imovelRN=new ImovelRN();
		List<ChaveNoQuadro> listaChavenoQuadro=new ChaveNoQuadroRN().listar();
		for(EmprestimoChave emp: listaEmprestimosPorPessoa){
			for(ChaveNoQuadro c:listaChavenoQuadro){
				if(c.getEmprestimoChave()!=null){
					if(c.getEmprestimoChave().getEmpId()==emp.getEmpId()){
						this.listaImoveisDevolucaoChave.add(imovelRN.carregar(emp.getImovel().getImoId()));
					}
				}
			}
		}

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pessoa selecionada:" +  this.pessoaDevendoChave.getPesNome(), null);  	          
		FacesContext.getCurrentInstance().addMessage(null, message);  
	}


	public void gravarDevolucao(){
		if(this.imovelDevolvido==null){
			this.contextoBean.mostrarErro("Selecione o imóvel para devolução");
			return;
		}
		//for(Imovel imo:this.listaImoveisDevolucaoChave){
			EmprestimoChaveRN emprestimoChaveRN=new EmprestimoChaveRN();
			List<EmprestimoChave> listaEmprestimosPorPessoa=emprestimoChaveRN.carregarEmprestimoPorPessoa(this.pessoaDevendoChave);
			for(EmprestimoChave emp:listaEmprestimosPorPessoa){
				ChaveNoQuadroRN chaveNoQuadroRN=new ChaveNoQuadroRN();
				ChaveNoQuadro  chaveNoQuadro=chaveNoQuadroRN.buscarChaveAtravesDoEmprestimo(emp);
				if(chaveNoQuadro!=null){
				if(this.imovelDevolvido.getImoId()==chaveNoQuadro.getImovel().getImoId()){
					chaveNoQuadro.setEmprestimoChave(null);
					chaveNoQuadroRN.salvar(chaveNoQuadro);
					emp.setEmpObservacaoParecer(this.obsParecer);
					emp.setEmpDataDevolucao(new Date());
					emp.setEmpDevolucao(true);
					emp.setEmpEnteresseImovel(this.alocarImovel);
					new EmprestimoChaveRN().salvar(emp);
				}
			//	}
			}
		}
			if(this.alocarImovel){
				Situacaoimovel situacaoimovel=new Situacaoimovel();
				//situação 2, porque foi determinado que a situação do imovel 2 será 'NEGOCIANDO'
				situacaoimovel.setSitId(2);
				this.imovelDevolvido.setSituacaoimovel(situacaoimovel);
				new ImovelRN().salvar(this.imovelDevolvido);
			}

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Salvo com sucesso"));
		this.listaImoveisDevolucaoChave=null;
		this.obsParecer=new String();
		this.imovelDevolvido=new Imovel();
		this.listaEmprestimos=null;
		this.pessoaDevendoChave=new Pessoa();
		this.alocarImovel=false;
		new EmprestimoChaveBean();
		
		
	}
	public List<Filial> getListaFiliaisTemChave() {
		if(this.listaFiliaisTemChave==null){
			this.listaFiliaisTemChave=new ArrayList<Filial>();

			QuadroDeChavesRN quadroDeChavesRN=new QuadroDeChavesRN();			
			ChaveNoQuadroRN chaveNoQuadroRN=new ChaveNoQuadroRN();

			for(Filial f: new FilialRN().listar()){
				QuadroDeChaves quadroDeChaves= quadroDeChavesRN.buscarPorFilial(f.getFilId());
				ChaveNoQuadro chaveNoQuadro= chaveNoQuadroRN.buscarChaveNoQuadro(quadroDeChaves.getQuaId(), this.imovel.getImoId());
				if(chaveNoQuadro!=null){
					if(chaveNoQuadro.getEmprestimoChave()==null){
						this.listaFiliaisTemChave.add(f);
					}
				}
			}
		}
		return listaFiliaisTemChave;
	}

	public void salvarEmprestimoEntreFiliais() throws IOException{
		if(this.filial.getFilId()<1){
			this.contextoBean.mostrarErro("Selecione uma filial para o emprestimo entre as filiais");
			return;
		}
		if(this.listaEmail.size()==0){
			if(this.menssagemSalvar==false){
				this.contextoBean.mostrarAviso("Tem certeza em salvar emprestimo de chave sem e-mail cadastros para o Sr. "+this.pessoa.getPesNome()+"? Caso sim, click em cadastrar emprestimo novamente");
				this.menssagemSalvar=true;
				return;}
		}
		CidadeRN cidadaRN = new CidadeRN();
		if (cidadaRN.buscarPorCidade(cidade.getCidNome()) == null) {
			cidadaRN.salvar(this.cidade);
		} else {
			this.cidade = cidadaRN.buscarPorCidade(cidade.getCidNome());
		}

		BairroRN bairroRN = new BairroRN();
		if (bairroRN.buscarPorBairro(bairro.getBaiNome()) == null) {
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							"Não há região cadastrada para esse bairro !",
							" Entre em contato com o administrador do sistema para cadastrar a região desse bairro! Atenção: reinicie a pagina"));
			this.bairro = new Bairro();
			this.cidade = new Cidade();
			this.email = new Email();
			this.pessoa = new Pessoa();
			this.ramo = new Ramoatuacao();
			this.telefone = new Telefone();
			this.endereco = new Endereco();
			this.emprestimoChave = new EmprestimoChave();
			this.listaEmail = null;
			this.listaTel = null;
			this.listaEmprestimos=null;

			return;
		} else {
			this.bairro = bairroRN.buscarPorBairro(this.bairro.getBaiNome());
		}
		if(this.pessoa.getPesId()<1){
			if((this.pessoa.getPesCpfCnpj()!="")||(this.pessoa.getPesCpfCnpj()!=null)){
				if(new PesquisaRN().existeCPF(this.pessoa.getPesCpfCnpj())){
					FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"CPF já cadastrado em nosso banco de dados !",
									"  "));
					return;
				}
			}
		}
		if((this.pessoa.getPesFoto()==null)||(this.pessoa.getPesFoto().equalsIgnoreCase(""))){
			this.contextoBean.mostrarErro("Foto obrigatório");
			return;
			}
		String cep = endereco.getEndCep();
		cep = cep.replaceAll("[.-]", "");
		this.endereco.setEndCep(cep);

		this.endereco.setCidade(cidade);
		this.endereco.setBairro(bairro);

		endereco.setPessoa(pessoa);

		this.pessoa.setEndereco(endereco);
		if(this.pessoa.getPesId()<1){ // se a pessoa não existe, é adicionado um ramo de atuação para ele
		Ramoatuacao ramo=new RamoatuacaoRN().buscarPorRamoatuacao("OUTROS");
		this.pessoa.setRamoatuacao(ramo);
		}

		EnderecoRN endercoRN = new EnderecoRN();
		if(this.pessoa.getPesId()>0){
			this.endereco.setEndId(this.pessoa.getEndereco().getEndId());
		}
		endercoRN.salvar(this.endereco);

		if(this.pessoa.getPesId()<=1){
		pessoa.setFuncionario(this.contextoBean.getFuncionarioLogado());
		this.pessoa.setPesPreCadastro(true);
		}

		PessoaRN pessoaRN = new PessoaRN();
		String im = pessoa.getPesIm();
		if (im == "") {
			im = im.replaceAll("[./]", "");
			pessoa.setPesIm(im);
		}
		im = pessoa.getPesRgIe();
		if (im == "") {
			im = im.replaceAll("[.-]", "");
			pessoa.setPesRgIe(im);
		}
		if(this.pessoa.getPesId()<1){
		this.pessoa.setPesInquilino(false);
		this.pessoa.setPesProprietario(false);
		this.pessoa.setPesFuncionario(false);
		this.pessoa.setPesFiador(false);
		this.pessoa.setPesPreCadastro(true);
		}
		try {
			pessoaRN.salvar(this.pessoa);
		} catch (UtilException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return ;
		}

		EmailRN emailRN = new EmailRN();
		TelefoneRN telefoneRN = new TelefoneRN();
		for (Telefone f : listaTel) {
			String tel = f.getTelNumero();
			tel = tel.replaceAll("[.-]", "");
			tel = tel.replaceAll("[)]", "");
			tel = tel.replaceAll("[(]", "");
			f.setTelNumero(tel);
			f.setPessoa(pessoa);
			telefoneRN.salvar(f);
		}

		for (Email e : listaEmail) {
			e.setPessoa(pessoa);
			emailRN.salvar(e);
		}
		
		EmprestimoChaveRN emprestimoChaveRN = new EmprestimoChaveRN();
		ChaveNoQuadro chaveNoQuadro = new ChaveNoQuadro();
		ChaveNoQuadroRN chaveNoQuadroRN = new ChaveNoQuadroRN();
		QuadroDeChaves quadroDeChaves = new QuadroDeChavesRN()
		.buscarPorFilial(this.filial.getFilId());// filial detento-la da chave

		this.emprestimoChave.setEmpDevolucao(false);
		this.emprestimoChave.setFuncionario(this.contextoBean.getFuncionarioLogado());
		this.emprestimoChave.setEmpDataEmprestimo(new Date());
		this.emprestimoChave.setEmpDevolucao(false);
		this.emprestimoChave.setPessoa(this.pessoa);
		this.emprestimoChave.setEmpFuncionario(false);
		this.emprestimoChave.setImovel(this.imovel);
		this.emprestimoChave.setEmpEntreFilial(true);//emprestimo entre filiais
		this.emprestimoChave.setEmpEntreFilialId(this.filial.getFilId());// filial detento-la da chave
		emprestimoChaveRN.salvar(this.emprestimoChave);

		chaveNoQuadro = chaveNoQuadroRN.buscarChaveNoQuadro(
				quadroDeChaves.getQuaId(), this.imovel.getImoId());
		chaveNoQuadro.setEmprestimoChave(this.emprestimoChave);
		chaveNoQuadroRN.salvar(chaveNoQuadro);

		/*FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Salvo com sucesso"));*/

		// novo contrato será gerado com relatorio e não com o documento word
		//gerarContrato();

		//atribuindo os codigos para geração do relatorio
		this.codigo_emprestimo=this.emprestimoChave.getEmpId();
		this.codigo_funcionario=this.emprestimoChave.getFuncionario().getPesId();
		this.codigo_pessoa=this.emprestimoChave.getPessoa().getPesId();
		carregar();
		carregarListas();
		//gerando relatorio
				try {
					gerarRelatorio();
				} catch (UtilException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		//super.redirecionarParaPagina("restrito/consulta/pesquisaimovel.jsf");

	}

	//Função para saber em qual é a filial pertecente a chave
	public String qualFilialPertence(int i){
		String nomeFilial=new String();
		
		EmprestimoChaveRN emprestimoChaveRN=new EmprestimoChaveRN();
		List<EmprestimoChave> listaEmprestimosPorPessoa=emprestimoChaveRN.carregarEmprestimoPorPessoa(this.pessoaDevendoChave);
		List<ChaveNoQuadro> listaChavenoQuadro=new ChaveNoQuadroRN().listar();
		for(EmprestimoChave emp: listaEmprestimosPorPessoa){
			for(ChaveNoQuadro c:listaChavenoQuadro){
				if(c.getEmprestimoChave()!=null){
					if(c.getEmprestimoChave().getEmpId()==emp.getEmpId()){
						if(c.getImovel().getImoId()==i){
							nomeFilial=c.getQuadroDeChaves().getFilial().getFilNome();
						}
					}
				}
			}
		}

		return nomeFilial;
	}
	
	public synchronized void baixarArquivo(String path, String nomeArquivo) throws IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		System.out.println("Caminho do Arquivo: "+path);
		
        File file = new File(path);
  
        HttpServletResponse response = (HttpServletResponse) context.getResponse();  
        response.setHeader("Content-Disposition", "attachment;filename=\""+nomeArquivo+".doc\"");
        response.setContentLength((int) file.length());
        response.setContentType("application/pdf");
        
        FileInputStream in = new FileInputStream(file);  
        OutputStream out = response.getOutputStream();  
  
	    byte[] buf = new byte[(int)file.length()];  
	    int count;  

		while ((count = in.read(buf)) >= 0)
			out.write(buf, 0, count);
	    
	    in.close();  
	    out.flush();  
	    out.close();
        
        FacesContext.getCurrentInstance().responseComplete();
	}
	
	public void gerarContrato()throws IOException{
		
		String nomeArquivoGerado =" "+this.emprestimoChave.getEmpId() + " - emprestimo-chave.doc";
		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId()+ File.separator+ "emprestimoChaves");
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		
	
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"emprestimo-chave.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			 Funcionario funcinario=new FuncionarioRN().carregar(this.contextoBean.getFuncionarioLogado().getPesId());
			    range.replaceText("#NOMEATENDENTE", funcinario.getPessoa().getPesNome());
			    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss"); 
			    range.replaceText("#DATEEMP",df.format(this.emprestimoChave.getEmpDataEmprestimo()));
			    range.replaceText("#DATEDEV", df.format(this.emprestimoChave.getEmpDataLimiteDevolucao()));
			
			    SimpleDateFormat format2= new SimpleDateFormat("dd/MM/yyyy"); //você pode usar outras máscaras 
		        range.replaceText("#Dataatual",format2.format(new Date()));	
		        range.replaceText("#NOME",this.emprestimoChave.getPessoa().getPesNome());
		        range.replaceText("#CPF",this.emprestimoChave.getPessoa().getPesCpfCnpj()+" RG:"+this.emprestimoChave.getPessoa().getPesRgIe());
		        Pessoa pessoa=new PessoaRN().carregar(this.emprestimoChave.getPessoa().getPesId());
		        Endereco endereco=new EnderecoRN().carregar(pessoa.getEndereco().getEndId());
		        range.replaceText("#END", endereco.getEndNome()+"-"+endereco.getEndNumero()+"- CEP: "+endereco.getEndCep()+"- Bairro: "+endereco.getBairro().getBaiNome()+"- Cidade: "+endereco.getCidade().getCidNome());
				
		        range.replaceText("#Dataatual",this.emprestimoChave.getPessoa().getPesNome());
		        range.replaceText("#Dataatual",this.emprestimoChave.getPessoa().getPesNome());
		        range.replaceText("#Dataatual",this.emprestimoChave.getPessoa().getPesNome());
		        
		        String fixo=new String();
		        String celular=new String();
		        List<Telefone> listaTel=new TelefoneRN().carregarPessoa(this.emprestimoChave.getPessoa());
		        for(Telefone tel: listaTel){
		        	if(tel.getTelTipo().equals("CELULAR")){
		        		celular+=tel.getTelNumero()+". ";
		        	}
		        	if(tel.getTelTipo().equals("RESIDENCIAL")){
		        		fixo+=tel.getTelNumero()+". ";
		        	}
		        }
		        
		        range.replaceText("#FIXO",fixo);
		        range.replaceText("#CELULAR",celular);
		        
		        List<EmprestimoChave> listaEmprestimo=new EmprestimoChaveRN().carregarEmprestimoPorPessoa(this.emprestimoChave.getPessoa());
		        List<Imovel> listaImoveis=new ArrayList<Imovel>();
		        ImovelRN imovelRN=new ImovelRN();

		        for(EmprestimoChave emp: listaEmprestimo){
		        	if(emp.getEmpDevolucao()!=null && emp.getEmpDevolucao()==false){
		        		listaImoveis.add(imovelRN.carregar(emp.getImovel().getImoId()));
		        	}
		        }
		        // essa variavel pode ser pesquisado por filial e imovel.
		        ChaveNoQuadro chaveNoQuadro= new ChaveNoQuadroRN().buscarChaveAtravesDoEmprestimo(this.emprestimoChave);
			      
		        for (int i = 0; i < listaImoveis.size(); ) {
		        	range.replaceText("#imo", listaImoveis.get(i).getImoId()+"");
		        	Endereco endereco2=new EnderecoRN().carregar(listaImoveis.get(i).getEndereco().getEndId());
		        	range.replaceText("#IMEN", endereco2.getEndNome()+"-"+endereco2.getEndNumero()+"- CEP: "+endereco2.getEndCep()+"- Bairro: "+endereco2.getBairro().getBaiNome()+"- Cidade: "+endereco2.getCidade().getCidNome());
		        	range.replaceText("#COPIA0"," Cópias: "+ chaveNoQuadro.getChaQuaQtdChave());
		        	range.replaceText("#CONDOMINIO0"," R$: "+ listaImoveis.get(i).getImoValorCondominio());
		        	break;
		        }
		        for (int i = 1; i < listaImoveis.size();) {
		        	range.replaceText("#2imo", listaImoveis.get(i).getImoId()+"");
		        	Endereco endereco3=new EnderecoRN().carregar(listaImoveis.get(i).getEndereco().getEndId());
		        	range.replaceText("#2IMEN", endereco3.getEndNome()+"-"+endereco3.getEndNumero()+"- CEP: "+endereco3.getEndCep()+"- Bairro: "+endereco3.getBairro().getBaiNome()+"- Cidade: "+endereco3.getCidade().getCidNome());
		        	range.replaceText("#COPIA1"," Cópias: "+ chaveNoQuadro.getChaQuaQtdChave());
		        	range.replaceText("#CONDOMINIO1"," R$: "+ listaImoveis.get(i).getImoValorCondominio());
		        	break;
		        }
		        for (int i = 2; i < listaImoveis.size(); ) {
		        	range.replaceText("#3imo", listaImoveis.get(i).getImoId()+"");
		        	Endereco endereco4=new EnderecoRN().carregar(listaImoveis.get(i).getEndereco().getEndId());
		        	range.replaceText("#3IMEN", endereco4.getEndNome()+"-"+endereco4.getEndNumero()+"- CEP: "+endereco4.getEndCep()+"- Bairro: "+endereco4.getBairro().getBaiNome()+"- Cidade: "+endereco4.getCidade().getCidNome());
		        	range.replaceText("#COPIA2"," Cópias: "+ chaveNoQuadro.getChaQuaQtdChave());
		        	range.replaceText("#CONDOMINIO2"," R$: "+ listaImoveis.get(i).getImoValorCondominio());
		        	break;
		        }
		        for (int i = 3; i < listaImoveis.size();) {
		        	range.replaceText("#4imo", listaImoveis.get(i).getImoId()+"");
		        	Endereco endereco5=new EnderecoRN().carregar(listaImoveis.get(i).getEndereco().getEndId());
		        	range.replaceText("#4IMEN", endereco5.getEndNome()+"-"+endereco5.getEndNumero()+"- CEP: "+endereco5.getEndCep()+"- Bairro: "+endereco5.getBairro().getBaiNome()+"- Cidade: "+endereco5.getCidade().getCidNome());
		        	range.replaceText("#COPIA3"," Cópias: "+ chaveNoQuadro.getChaQuaQtdChave());
		        	range.replaceText("#CONDOMINIO3"," R$: "+ listaImoveis.get(i).getImoValorCondominio());
		        	break;
		        }
		        for (int i = 4; i < listaImoveis.size(); ) {
		        	range.replaceText("#5imo", listaImoveis.get(i).getImoId()+"");
		        	Endereco endereco6=new EnderecoRN().carregar(listaImoveis.get(i).getEndereco().getEndId());
		        	range.replaceText("#5IMEN", endereco6.getEndNome()+"-"+endereco6.getEndNumero()+"- CEP: "+endereco6.getEndCep()+"- Bairro: "+endereco6.getBairro().getBaiNome()+"- Cidade: "+endereco6.getCidade().getCidNome());
		        	range.replaceText("#COPIA4"," Cópias: "+ chaveNoQuadro.getChaQuaQtdChave());
		        	range.replaceText("#CONDOMINIO4"," R$: "+ listaImoveis.get(i).getImoValorCondominio());
		        	break;
		        }
		        for (int i = 5; i < listaImoveis.size();) {
		        	range.replaceText("#6imo", listaImoveis.get(i).getImoId()+"");
		        	Endereco endereco7=new EnderecoRN().carregar(listaImoveis.get(i).getEndereco().getEndId());
		        	range.replaceText("#6IMEN", endereco7.getEndNome()+"-"+endereco7.getEndNumero()+"- CEP: "+endereco7.getEndCep()+"- Bairro: "+endereco7.getBairro().getBaiNome()+"- Cidade: "+endereco7.getCidade().getCidNome());
		        	range.replaceText("#COPIA5"," Cópias: "+ chaveNoQuadro.getChaQuaQtdChave());
		        	range.replaceText("#CONDOMINIO5"," R$: "+ listaImoveis.get(i).getImoValorCondominio());
		        	break;
		        }

		        
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Emprestimo de Chave");
	        
	        //super.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	public String buscarTelefonePorPessoa(int id){		
		Pessoa pessoa =new PessoaRN().carregar(id);
		List<Telefone> tels=new TelefoneRN().carregarPessoa(pessoa);
		String tel=new String();
		for(Telefone t: tels){
			tel+=t.getTelNumero()+"-";
		}
		return tel;
	}
	
	private Connection getConexao() throws UtilException {
		java.sql.Connection conexao = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env/");
			javax.sql.DataSource ds = (javax.sql.DataSource) envContext.lookup("jdbc/LocadoraDB");
			conexao = (java.sql.Connection) ds.getConnection();
		} catch (NamingException e) {
			throw new UtilException("Não foi possivel encontrar o nome da conexão do banco.", e);
		} catch (SQLException e) {
			throw new UtilException("Ocorreu um erro de SQL.", e);
		}
		return conexao;
	}
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void gerarRelatorio() throws UtilException{
		this.contextoBean.commit();
		FacesContext context = FacesContext.getCurrentInstance();
		Connection conexao = this.getConexao();
		String nomeRelatorioSaida = "Emprestimo de Chave";
		String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"relatorios");
		String caminhoArquivoJasper = caminhoRelatorio + File.separator + "emprestimo_chave.jasper";
		System.out.println(caminhoArquivoJasper);
		String caminhoArquivoRelatorio = null;
		
		HashMap parametrosRelatorio = new HashMap();
		parametrosRelatorio.put("codigo_pessoa", this.codigo_pessoa);
		parametrosRelatorio.put("codigo_funcionario", this.codigo_funcionario);
		parametrosRelatorio.put("codigo_emprestimo", this.codigo_emprestimo);
		
		try {
			JasperReport relatorioJasper = (JasperReport) JRLoader.loadObject(caminhoArquivoJasper);
			JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, conexao);
			JRExporter tipoArquivoExportado= new JRPdfExporter();
			caminhoArquivoRelatorio = caminhoRelatorio + File.separator + nomeRelatorioSaida + ".pdf" ;
			File arquivoGerado =new java.io.File(caminhoArquivoRelatorio);
			tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
			tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
			tipoArquivoExportado.exportReport();
			download(((HttpServletResponse)(context.getExternalContext().getResponse())),JasperExportManager.exportReportToPdf(impressoraJasper));
			arquivoGerado.deleteOnExit();
			
		} catch (JRException e) {
			
		}
	}
	public synchronized void download(HttpServletResponse response,byte[] pdfAsBytes) {  
		try {

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=Emprestimo de Chave.pdf");

			OutputStream output = response.getOutputStream();
			output.write(pdfAsBytes);
			response.flushBuffer();

			FacesContext.getCurrentInstance().responseComplete();

		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }

	public void verificarPendencias(){
		if(this.imovelDevolvido==null){
			this.contextoBean.mostrarErro("Selecione o imovel");
			return;
		}
		if(this.alocarImovel){
			if(this.pessoaDevendoChave.getPesPreCadastro()!=null && this.pessoaDevendoChave.getPesPreCadastro()==true){
				this.faltaInformacaoCliente=true;
					RequestContext.getCurrentInstance().execute("PF('completarCadastro').show()");
				return;
			}
			this.faltaInformacaoCliente=false;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void gerarPreContrato() throws UtilException{
		FacesContext context = FacesContext.getCurrentInstance();
		Connection conexao = this.getConexao();
		String nomeRelatorioSaida = "Emprestimo de Chave";
		String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"relatorios");
		String caminhoArquivoJasper = caminhoRelatorio + File.separator + "inicio_de_contrato.jasper";
		System.out.println(caminhoArquivoJasper);
		String caminhoArquivoRelatorio = null;
		
		HashMap parametrosRelatorio = new HashMap();
		parametrosRelatorio.put("atendente", "atendente");
		parametrosRelatorio.put("locador", "locador");
		parametrosRelatorio.put("locatario", "locatario");
		
		try {
			JasperReport relatorioJasper = (JasperReport) JRLoader.loadObject(caminhoArquivoJasper);
			JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, conexao);
			JRExporter tipoArquivoExportado= new JRPdfExporter();
			caminhoArquivoRelatorio = caminhoRelatorio + File.separator + nomeRelatorioSaida + ".pdf" ;
			File arquivoGerado =new java.io.File(caminhoArquivoRelatorio);
			tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
			tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
			tipoArquivoExportado.exportReport();
			download(((HttpServletResponse)(context.getExternalContext().getResponse())),JasperExportManager.exportReportToPdf(impressoraJasper));
			arquivoGerado.deleteOnExit();
			
		} catch (JRException e) {
			
		}
	}
}
