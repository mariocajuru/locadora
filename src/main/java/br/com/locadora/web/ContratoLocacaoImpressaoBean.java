package br.com.locadora.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Email;
import br.com.locadora.modelo.Fiador;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Locacao;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Proprietario;
import br.com.locadora.modelo.Telefone;
import br.com.locadora.rn.EmailRN;
import br.com.locadora.rn.FiadorRN;
import br.com.locadora.rn.FuncionarioRN;
import br.com.locadora.rn.ImovelRN;
import br.com.locadora.rn.LocacaoRN;
import br.com.locadora.rn.PessoaRN;
import br.com.locadora.rn.ProprietarioRN;
import br.com.locadora.rn.TelefoneRN;
import br.com.locadora.web.util.ContextoUtil;

//@ManagedProperty("#{country}")

@ManagedBean(name = "contratoLocacaoImpressaoBean")
@ViewScoped
public class ContratoLocacaoImpressaoBean implements Serializable {
	
	@Getter private static final long serialVersionUID = 2446358028631742810L;
	
	@Getter @Setter private Locacao            locacao            = new Locacao();
	@Getter @Setter private Imovel             imovel             = new Imovel();
	@Getter @Setter private Pessoa             inquilino          = new Pessoa();
	@Getter @Setter private List<Fiador>       listaFiador        = new ArrayList<Fiador>();
	@Getter @Setter private List<Proprietario> listaProprietarios = new ArrayList<Proprietario>();

	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public ContratoLocacaoImpressaoBean() {
		carregar();
	}

	public void carregar() {
		try {			
			int locacaoID = this.contextoBean.getParametro("id", -1);
						
			if (locacaoID <= 0) {
				this.contextoBean.redirecionarParaPaginaExterna("http://www.google.com");
				
			} else {
				Locacao locacaoCarregado = new LocacaoRN().carregar(locacaoID);

				this.locacao = locacaoCarregado;
				
				if (this.locacao == null) {
					this.contextoBean.redirecionarParaPaginaExterna("http://www.google.com");
					return;
				}
			}
		} catch (NumberFormatException e) {
			this.contextoBean.redirecionarParaPaginaExterna("http://www.google.com");
		}
	}

	public synchronized void baixarArquivo(String path, String nomeArquivo) throws IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
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
	
	/** Informações proprietários - Relatorio ok */
	public void gerarContrato_InformacoesProprietario() throws IOException {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        
		
		String nomeArquivoGerado =" "+locacao.getLocId() + " - Informacao-ao-proprietario.doc";
		
		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;

		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"Informacoes-ao-proprietairo.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
		    List<Proprietario> listaProprietario=new ProprietarioRN().carregarProprietarios(this.imovel);
	        String nomes=new String();
	        for(Proprietario p: listaProprietario){
	        	if(p.getProAtivo()){
	        	nomes+=p.getPessoa().getPesNome()+" . ";
	        	}
	        }
	       
	        range.replaceText("#NOMELOCATARIO", inquilino.getPesNome());
	        range.replaceText("#NOMELOCADOR", nomes);
	        range.replaceText("#INICIOCONTRATO", format.format(locacao.getLocDataInicialContrato()));
	        range.replaceText("#MESESCONTRATO", locacao.getLocMesesDeContrato() + "");
	        range.replaceText("#VALORALUGUEL", "R$ " + imovel.getImoValorAluguel());
	        range.replaceText("#DATAPAGAMENTOLOCATARIO", locacao.getLocDiaPagamentoLocatario()+"");
	        range.replaceText("#DATAPAGAMENTOLOCADOR", locacao.getLocDiaPagamentoLocador()+"");
	        if(imovel.getImoTaxa()!=null){
	        	range.replaceText("#TAXADEADMINISTRACAO", imovel.getImoTaxa().toString());}
	        if(locacao.getLocMesesDeContrato()!=null){
	        	 range.replaceText("#PERIODOCADASTRO", locacao.getLocMesesDeContrato()+"");
	        }
	        range.replaceText("#VALORPRIMEIROALUGUEL","R$ "+ imovel.getImoValorAluguel().toString());
	        if(locacao.getLocDiaPagamentoLocatario()!=null){
	        	  range.replaceText("#INFORMOUPROPRIETARIO", format.format(locacao.getLocDiaPagamentoLocatario()));
	        }
	        
	        Funcionario funcinario=new FuncionarioRN().carregar(this.contextoBean.getFuncionarioLogado().getPesId());
	        range.replaceText("#NOMEFUNCIONARIO", funcinario.getPessoa().getPesNome());
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Informações ao Proprietário");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	/** Informações proprietários- Relatorio Ok */
	public void gerarContrato_ContratoParticularDeLocacao() throws IOException{
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        List<Fiador> listaFiadores=new FiadorRN().carregarFiadoresPorImovel(this.locacao);
       
		String nomeArquivoGerado =" "+  locacao.getLocId() + " - contrato-particular-de-locacao-de-imovel.doc";
		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
	 
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"contrato-particular-de-locacao-de-imovel.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
	        range.replaceText("#NOMELOCATARIO", inquilino.getPesNome());
	        range.replaceText("#NACIONALIDADELOCADOR", inquilino.getPesNacionalidade());
	        range.replaceText("#CPFCNPJLOCADOR", inquilino.getPesCpfCnpj());
	        range.replaceText("#PROFISSAOLOCADOR", inquilino.getPesProfissao());
	        range.replaceText("#ESTADOCLOCADOR", inquilino.getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        
	        List<Proprietario> listaProprietario=new ProprietarioRN().carregarProprietarios(this.imovel);
	        String nomes=new String();
	        String cpf=new String();
	        String estadoCivil=new String();
	        for(Proprietario p: listaProprietario){
	        	if(p.getProAtivo()){
	        	nomes+=p.getPessoa().getPesNome()+" . ";
	        	cpf+=p.getPessoa().getPesCpfCnpj()+" ";
	        	estadoCivil+=p.getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO"+"";
	        	estadoCivil+=" ";}
	        }
	        range.replaceText("#NOMELOCADOR", nomes);
	        range.replaceText("#NACIONALIDADELOCATARIO", listaProprietarios.get(0).getPessoa().getPesNacionalidade());
	        range.replaceText("#PROFISSAOLOCATARIO", listaProprietarios.get(0).getPessoa().getPesProfissao());
	        range.replaceText("#ESTADOCLOCATARIO", estadoCivil);
	        range.replaceText("#CPFCNPJLOCATARIO", cpf);
	        range.replaceText("#NOMELOCADOR", listaProprietarios.get(0).getPessoa().getPesNome());

	        range.replaceText("#ENDIMOVEL", imovel.getEndereco().getEndNome());
	        range.replaceText("#BAIRROIMOVEL", imovel.getEndereco().getBairro().getBaiNome());
	        range.replaceText("#CEPIMOVEL", imovel.getEndereco().getEndCep());
	        
	        range.replaceText("#INICIO", locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
	        range.replaceText("#MESESCONTRATO", locacao.getLocMesesDeContrato() + "");
	        range.replaceText("#VALORALUGUEL", "R$ " + imovel.getImoValorAluguel());
	        if(locacao.getLocDiaPagamentoLocatario()!=null){
	        range.replaceText("#DATAPAGAMENTOLOCATARIO", locacao.getLocDiaPagamentoLocatario()+"");
	        range.replaceText("#DATAPAGAMENTOLOCADOR", locacao.getLocDiaPagamentoLocador()+"");
	        }
	        if(locacao.getLocDataTerminioContrato()!=null){
	        	 range.replaceText("#DIAVENCIMENTO", locacao.getLocDataTerminioContrato().toString().replace(" 00:00:00.0", ""));
	        }
	       
	        for (int i = 0; i < listaFiadores.size(); ) {
	        	range.replaceText("#FIADOR1", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#ESTADOCFIADOR1", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#NACIONFIADOR1", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#PROFFIADOR1", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#CPFCNPJFIADOR1", listaFiadores.get(i).getPessoa().getPesCpfCnpj());
	        	range.replaceText("#ENDFIADOR1", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+" nº: "
	        			+listaFiadores.get(i).getPessoa().getEndereco().getEndNumero());
	        	break;
	        }
	        for (int i = 1; i < listaFiadores.size(); ) {
	        	range.replaceText("#FIADOR2", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#ESTADOCFIADOR2", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#NACIONFIADOR2", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#PROFFIADOR2", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#CPFCNPJFIADOR2", listaFiadores.get(i).getPessoa().getPesCpfCnpj());
	        	range.replaceText("#ENDFIADOR2", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+" nº: "
	        			+listaFiadores.get(i).getPessoa().getEndereco().getEndNumero());
	        	break;
	        }
	        for (int i = 2; i < listaFiadores.size(); ) {
	        	range.replaceText("#FIADOR3", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#ESTADOCFIADOR3", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#NACIONFIADOR3", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#PROFFIADOR3", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#CPFCNPJFIADOR3", listaFiadores.get(i).getPessoa().getPesCpfCnpj());
	        	range.replaceText("#ENDFIADOR3", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+" nº: "
	        			+listaFiadores.get(i).getPessoa().getEndereco().getEndNumero());
	        	break;
	        }
	        
	        
	        range.replaceText("#NOMEFUNCIONARIO", "Administrador");
	        range.replaceText("#PRAZOLOCACAO", this.locacao.getLocMesesDeContrato()+"");
	        range.replaceText("#TERMINO", this.locacao.getLocDataTerminioContrato().toString().replace(" 00:00:00.0", ""));
	       // range.replaceText("#VAGAGARAGEM",);
	      //  range.replaceText("#DIAVENCIMENTO",);
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Contrato Locacao");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	/** Informações INCIO DE CONTRATO- Relatorio Ok */
	public void gerarContrato_Inicio_locacao() {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
		this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
		String nomeArquivoGerado =" "+ locacao.getLocId() + " - Inicio-locacao.doc";

		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
		nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;	   
     
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"Inicio-locacao.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
	        range.replaceText("#DATAINCIOCONTRATO", this.locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
	        List<Proprietario> listaProprietario=new ProprietarioRN().carregarProprietarios(this.imovel);
	        String nomes=new String();
	        for(Proprietario p: listaProprietario){
	        	if(p.getProAtivo()){
	        	nomes+=p.getPessoa().getPesNome()+" ";}
	        }
	        range.replaceText("#PROPRIETARIO", nomes);
//	        range.replaceText("#FUN", super.getFuncionarioLogado().toString());
//	        range.replaceText("#CAPTADOR", this.imovel.getImoIdCaptador().toString());
	        
	        range.replaceText("#INQUILINO", this.inquilino.getPesNome());
	        range.replaceText("#IMOVELEXCLUSIVO", this.imovel.getImoExclusivo()? "SIM":"NÂO");
	        range.replaceText("#PROFISSAOLOCATARIO", listaProprietarios.get(0).getPessoa().getPesProfissao());
	      /*  range.replaceText("#CPFCNPJLOCATARIO", listaProprietarios.get(0).getPessoa().getPesCpfCnpj());
	        range.replaceText("#NOMELOCADOR", listaProprietarios.get(0).getPessoa().getPesNome());

	        range.replaceText("#ENDIMOVEL", imovel.getEndereco().getEndNome());
	        range.replaceText("#BAIRROIMOVEL", imovel.getEndereco().getBairro().getBaiNome());
	        range.replaceText("#CEPIMOVEL", imovel.getEndereco().getEndCep());
	        
	        range.replaceText("#INICIO", locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
	        range.replaceText("#MESESCONTRATO", locacao.getLocMesesDeContrato() + "");
	        range.replaceText("#VALORALUGUEL", "R$ " + imovel.getImoValorAluguel());
	        range.replaceText("#DATAPAGAMENTOLOCATARIO", locacao.getLocDataPagamentoLocatario().toString().replace(" 00:00:00.0", ""));
	        range.replaceText("#DATAPAGAMENTOLOCADOR", locacao.getLocDataPagamentoLocador().toString().replace(" 00:00:00.0", ""));
	        
	        range.replaceText("#NOMEFUNCIONARIO", "Administrador");*/
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"?");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	/** Informações INCIO DE CONTRATO- Relatorio Ok */
	public void gerarData_Inicio_Locacao() {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        
		String nomeArquivoGerado =  locacao.getLocId() + " - Data-inicio-locacao.doc";
		
		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;

		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"Data-inicio-locacao.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
	        range.replaceText("#DATA", this.locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
	        List<Proprietario> listaProprietario=new ProprietarioRN().carregarProprietarios(this.imovel);
	        String nomes=new String();
	        for(Proprietario p: listaProprietario){
	        	if(p.getProAtivo()){
	        	nomes+=p.getPessoa().getPesNome()+". ";}
	        }
	        range.replaceText("#PROPRIETARIO", nomes);
//	        range.replaceText("#FUN", super.getFuncionarioLogado().toString());
//	        range.replaceText("#CAPTADOR", this.imovel.getImoIdCaptador().toString());
	        
	        range.replaceText("#INQUILINO", this.inquilino.getPesNome());
	        range.replaceText("#IMOVELEXCLUSIVO", this.imovel.getImoExclusivo()? "SIM":"NÂO");
	        range.replaceText("#PROFISSAOLOCATARIO", listaProprietarios.get(0).getPessoa().getPesProfissao());
	      /*  range.replaceText("#CPFCNPJLOCATARIO", listaProprietarios.get(0).getPessoa().getPesCpfCnpj());
	        range.replaceText("#NOMELOCADOR", listaProprietarios.get(0).getPessoa().getPesNome());

	        range.replaceText("#ENDIMOVEL", imovel.getEndereco().getEndNome());
	        range.replaceText("#BAIRROIMOVEL", imovel.getEndereco().getBairro().getBaiNome());
	        range.replaceText("#CEPIMOVEL", imovel.getEndereco().getEndCep());
	        
	        range.replaceText("#INICIO", locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
	        range.replaceText("#MESESCONTRATO", locacao.getLocMesesDeContrato() + "");
	        range.replaceText("#VALORALUGUEL", "R$ " + imovel.getImoValorAluguel());
	        range.replaceText("#DATAPAGAMENTOLOCATARIO", locacao.getLocDataPagamentoLocatario().toString().replace(" 00:00:00.0", ""));
	        range.replaceText("#DATAPAGAMENTOLOCADOR", locacao.getLocDataPagamentoLocador().toString().replace(" 00:00:00.0", ""));
	        
	        range.replaceText("#NOMEFUNCIONARIO", "Administrador");*/
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"?");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	/** Informações contrato-particular- Relatorio Ok */
	public void gerarContrato_particular() {
		
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
      
		String nomeArquivoGerado =" "+ locacao.getLocId() + " - contrato-particular.doc";
		
		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		  
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"contrato-particular.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			
	        range.replaceText("#VALOR", this.imovel.getImoValorAluguel().toString());
	        List<Proprietario> listaProprietario=new ProprietarioRN().carregarProprietarios(this.imovel);
	        String nomes=new String();
	        String profissao=new String();
	        String cpf=new String();
	        String estadoCivil=new String();
	        String nacionalidade=new String();
	        for(Proprietario p: listaProprietario){
	        	if(p.getProAtivo()){
	        	nomes+=p.getPessoa().getPesNome()+". ";
	        	profissao+=p.getPessoa().getPesProfissao()+". ";
	        	cpf+=p.getPessoa().getPesCpfCnpj()+". ";
	        	nacionalidade+=p.getPessoa().getPesNacionalidade()+". ";
	        	estadoCivil+=p.getPessoa().getPesEstadoCivil()=='S'? "Solteiro":"Casado";
	        	estadoCivil+=" ";}
	        }
	        range.replaceText("#NOMCE_LOCATARIO", nomes);
	        range.replaceText("#NLOCADOR", nacionalidade);
	        range.replaceText("#CPLOCADOR", cpf);
	        range.replaceText("#OCADOR", profissao);
	        range.replaceText("#ESTCIVILCADOR", estadoCivil);
	        
	        range.replaceText("#NOME_LOCADOR", this.inquilino.getPesNome());
	        range.replaceText("#EOCATORIO", this.inquilino.getPesEstadoCivil()=='S'? "Solteiro":"Casado");
	        range.replaceText("#PLOCATARIO", this.inquilino.getPesProfissao());
	        range.replaceText("#NACIOLOCATARIO", this.inquilino.getPesNacionalidade().toString());
	        range.replaceText("#NOMELOCADOR", listaProprietarios.get(0).getPessoa().getPesNome());

	        range.replaceText("#END", imovel.getEndereco().getEndNome()+"-"+imovel.getEndereco().getEndNumero()+"- CEP: "+imovel.getEndereco().getEndCep());
	        range.replaceText("#DIA",locacao.getLocDiaPagamentoLocador()+"");
	         range.replaceText("#CEP", imovel.getEndereco().getEndCep());
	        
	        range.replaceText("#INCIO", locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
	        range.replaceText("#TERMINIO", locacao.getLocDataTerminioContrato().toString().replace(" 00:00:00.0", ""));
	        range.replaceText("#BAIRRO", imovel.getEndereco().getBairro().getBaiNome());
	        range.replaceText("#ESTATORIO", this.inquilino.getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");	
	        range.replaceText("#CATARIO", this.inquilino.getPesProfissao());
	        
	        range.replaceText("#GNPJLOCATARIO", this.inquilino.getPesCpfCnpj());
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Contrato Particular");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarRecibo_seguro() {
		
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
       
		String nomeArquivoGerado = " "+locacao.getLocId() + " - recibo-seguro.doc";

		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"recibo-seguro.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			   
	        String nomes=new String();
	        String cpfs=new String();
	        for(Proprietario p: listaProprietarios){
	        	if(p.getProAtivo()){
	        	nomes+=p.getPessoa().getPesNome()+". ";
	        	cpfs+=p.getPessoa().getPesCpfCnpj()+". ";}
	        }
			
	        range.replaceText("#NOME_LOCATARIO", nomes);
	        range.replaceText("#CPF_CNPJ_LOCATARIO", cpfs);
	        range.replaceText("#NOME_LOCADOR", this.inquilino.getPesNome());
	        range.replaceText("#CPF_CNPJ_LOCADOR", this.inquilino.getPesCpfCnpj());
	        
	        range.replaceText("#CPFCNPJLOCATARIO", listaProprietarios.get(0).getPessoa().getPesCpfCnpj());
	        range.replaceText("#NOMELOCADOR", listaProprietarios.get(0).getPessoa().getPesNome());

	        range.replaceText("#ENDIMOVEL", imovel.getEndereco().getEndNome());
	        range.replaceText("#BAIRROIMOVEL", imovel.getEndereco().getBairro().getBaiNome());
	        range.replaceText("#CEPIMOVEL", imovel.getEndereco().getEndCep());
	        
	        range.replaceText("#END", this.imovel.getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero());
	        range.replaceText("#COD", this.imovel.getImoId()+"");
	        range.replaceText("#CEP", this.imovel.getEndereco().getEndCep());
	        range.replaceText("#BAIRRO", this.imovel.getEndereco().getBairro().getBaiNome());
	        range.replaceText("#TIPO_IMOVEL", this.imovel.getTipoimovel().getTipNome());
	        SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#DATAATUAL",format2.format(new Date()));	        
	        range.replaceText("#VLR_ALUG", this.imovel.getImoValorAluguel().toString());
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"?");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_TermoDeComprissoParaAtualizacaoDeRegistro() {
		
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        List<Fiador> listaFiadores=new FiadorRN().carregarFiadoresPorImovel(this.locacao);
        
		String nomeArquivoGerado =" "+locacao.getLocId() + " - TERMO-DE-COMPROMISSO-PARA-ATUALIZACAO-DE-REGISTRO.doc";

		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"TERMO-DE-COMPROMISSO-PARA-ATUALIZACAO-DE-REGISTRO.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			   
			range.replaceText("#NOME_LOC", this.inquilino.getPesNome());
			range.replaceText("#1CPF_CNPJ_LOC", this.inquilino.getPesCpfCnpj());
	        
	        for (int i = 0; i < listaFiadores.size(); ) {
	        	range.replaceText("#F1IADOR_1", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#2CPF_CNPJ_FIA1", listaFiadores.get(i).getPessoa().getPesCpfCnpj());
	        	break;
	        }
	        for (int i = 1; i < listaFiadores.size(); ) {
	        	range.replaceText("#F2IADOR_2", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#3CPF_CNPJ_FIA2", listaFiadores.get(i).getPessoa().getPesCpfCnpj());
	        	break;
	        }
	        for (int i = 2; i < listaFiadores.size(); ) {
	        	range.replaceText("#F3IADOR_3", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#4CPF_CNPJ_FIA3", listaFiadores.get(i).getPessoa().getPesCpfCnpj());
	        	break;
	        }
			
	        
	        range.replaceText("#END", this.imovel.getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero()+"-"+ this.imovel.getEndereco().getEndCep());
	        range.replaceText("#BAIRRO", this.imovel.getEndereco().getBairro().getBaiNome());
	        SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#dataatual",format2.format(new Date()));	        
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Atualização de Registro");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_ParticularDeLocacaoDeImoveis2() {

		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
      //  List<Fiador> listaFiadores=new FiadorRN().carregarFiadoresPorImovel(this.locacao);
        
		String nomeArquivoGerado =" "+locacao.getLocId() + " - CONTRATO-PARTICULAR-DE-LOCACAO-DE-IMOVEIS-2.doc";

		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"CONTRATO-PARTICULAR-DE-LOCACAO-DE-IMOVEIS-2.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}

			range.replaceText("#NOME_LOCADOR", this.inquilino.getPesNome());
			range.replaceText("#ESTCIVIL_LOCADOR", this.inquilino.getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");			
			range.replaceText("#PROFL_OCADOR", this.inquilino.getPesProfissao());
			range.replaceText("#CPF_CNPJ_LOCADOR", this.inquilino.getPesCpfCnpj());
			range.replaceText("#NACIONALOCADOR", this.inquilino.getPesNacionalidade());
			range.replaceText("#PRAZO", this.locacao.getLocMesesDeContrato()+"");
			
			String nomes=new String();
			String cpfs=new String();
			String estadoCivil=new String();
			String profissoes=new String();
			for(Proprietario p: listaProprietarios){
				if(p.getProAtivo()){
		        	nomes+=p.getPessoa().getPesNome()+". ";
		        	cpfs+=p.getPessoa().getPesCpfCnpj()+". ";
		        	estadoCivil+=(p.getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO")+". ";}
		        	profissoes+=p.getPessoa().getPesProfissao()+". ";
		        }
			
			range.replaceText("#NOMCE_LOCATARIO", nomes);
			range.replaceText("#NACIOLOCATARIO", this.inquilino.getPesNacionalidade());
			range.replaceText("#ESTCIVIL_LOCATORIO", estadoCivil);
			range.replaceText("#PROF_LOCATARIO", profissoes);
			range.replaceText("#CPF_CNPJ_LOCATARIO", cpfs);
			
			range.replaceText("#BAIRRO", this.imovel.getEndereco().getBairro().getBaiNome());
			range.replaceText("#CEP", this.imovel.getEndereco().getEndCep());
			range.replaceText("#ENDIMO", this.imovel.getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero());
			SimpleDateFormat formate= new SimpleDateFormat("dd/MM/yyyy");
			range.replaceText("#INICIO", formate.format(this.locacao.getLocDataInicialContrato()));
			range.replaceText("#VALOR", this.imovel.getImoValorAluguel().toString());
			range.replaceText("#DIA", this.locacao.getLocDiaPagamentoLocador()+"");
			range.replaceText("#IMO_RAT_BOOLEAN", "");
			if(this.locacao.getLocDataTerminioContrato()!=null){
				range.replaceText("#TERMINO", ""+formate.format(this.locacao.getLocDataTerminioContrato()));
			}
			SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#dataatual",format2.format(new Date()));	        
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Particular de Locacao de Imoveis 2");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_ParticularDeLocacaoDeImoveis3() {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
      //  List<Fiador> listaFiadores=new FiadorRN().carregarFiadoresPorImovel(this.locacao);
       
		String nomeArquivoGerado =" "+locacao.getLocId() + " - CONTRATO-PARTICULAR-DE-LOCACAO-DE-IMOVEIS-3.doc";
		
		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		 
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"CONTRATO-PARTICULAR-DE-LOCACAO-DE-IMOVEIS-3.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			   
			range.replaceText("#NOME_LOCADOR", this.inquilino.getPesNome());
			range.replaceText("#ESTCIVIL_LOCADOR", this.inquilino.getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");			
			range.replaceText("#PROFL_OCADOR", this.inquilino.getPesProfissao());
			range.replaceText("#CPF_CNPJ_LOCADOR", this.inquilino.getPesCpfCnpj());
			range.replaceText("#NACIONALOCADOR", this.inquilino.getPesNacionalidade());
			range.replaceText("#PRAZO", this.locacao.getLocMesesDeContrato()+"");
			range.replaceText("#TERMINO", this.locacao.getLocDataTerminioContrato().toString().replace(" 00:00:00.0", ""));
			
			String nomes=new String();
			String cpfs=new String();
			String estadoCivil=new String();
			String profissoes=new String();
			for(Proprietario p: listaProprietarios){
				if(p.getProAtivo()){
		        	nomes+=p.getPessoa().getPesNome()+". ";
		        	cpfs+=p.getPessoa().getPesCpfCnpj()+". ";
		        	estadoCivil+=(p.getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO")+". ";
		        	profissoes+=p.getPessoa().getPesProfissao()+". ";}
		        }
			
			range.replaceText("#NOMCE_LOCATARIO", nomes);
			range.replaceText("#NACIOLOCATARIO", this.inquilino.getPesNacionalidade());
			range.replaceText("#ESTCIVIL_LOCATORIO", estadoCivil);
			range.replaceText("#PROF_LOCATARIO", profissoes);
			range.replaceText("#CPF_CNPJ_LOCATARIO", cpfs);
			
			range.replaceText("#BAIRRO", this.imovel.getEndereco().getBairro().getBaiNome());
			range.replaceText("#CEP", this.imovel.getEndereco().getEndCep());
			range.replaceText("#INICIO", this.locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
			range.replaceText("#VALOR", this.imovel.getImoValorAluguel().toString());
			range.replaceText("#DIA", this.locacao.getLocDiaPagamentoLocador()+"");
			range.replaceText("#IMO_RAT_BOOLEAN", "");
			SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#dataatual",format2.format(new Date()));	        
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Particular de Locacao de Imoveis 3");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_ParticularDeLocacaoDeImoveis4() {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        List<Fiador> listaFiadores=new FiadorRN().carregarFiadoresPorImovel(this.locacao);
       
		String nomeArquivoGerado =" "+locacao.getLocId() + " - CONTRATO-PARTICULAR-DE-LOCACAO-DE-IMOVEIS-4.doc";
		
		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		 
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"CONTRATO-PARTICULAR-DE-LOCACAO-DE-IMOVEIS-4.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			   
			range.replaceText("#NOME_LOCADOR", this.inquilino.getPesNome());
			range.replaceText("#ESTCIVIL_LOCADOR", this.inquilino.getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");			
			range.replaceText("#PROFL_OCADOR", this.inquilino.getPesProfissao());
			range.replaceText("#CPF_CNPJ_LOCADOR", this.inquilino.getPesCpfCnpj());
			range.replaceText("#NACIONALOCADOR", this.inquilino.getPesNacionalidade());
			range.replaceText("#PRAZO", this.locacao.getLocMesesDeContrato()+"");
			range.replaceText("#TERMINO", this.locacao.getLocDataTerminioContrato().toString().replace(" 00:00:00.0", ""));
			
			String nomes=new String();
			String cpfs=new String();
			String estadoCivil=new String();
			String profissoes=new String();
			for(Proprietario p: listaProprietarios){
				if(p.getProAtivo()){
		        	nomes+=p.getPessoa().getPesNome()+". ";
		        	cpfs+=p.getPessoa().getPesCpfCnpj()+". ";
		        	estadoCivil+=(p.getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO")+". ";
		        	profissoes+=p.getPessoa().getPesProfissao()+". ";}
		        }
			
			range.replaceText("#NOMCE_LOCATARIO", nomes);
			range.replaceText("#NACIOLOCATARIO", this.inquilino.getPesNacionalidade());
			range.replaceText("#ESTCIVIL_LOCATORIO", estadoCivil);
			range.replaceText("#PROF_LOCATARIO", profissoes);
			range.replaceText("#CPF_CNPJ_LOCATARIO", cpfs);
			
			range.replaceText("#BAIRRO", this.imovel.getEndereco().getBairro().getBaiNome());
			range.replaceText("#CEP", this.imovel.getEndereco().getEndCep());
			range.replaceText("#INICIO", this.locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
			range.replaceText("#VALOR", this.imovel.getImoValorAluguel().toString());
			range.replaceText("#DIA", this.locacao.getLocDiaPagamentoLocador()+"");
			range.replaceText("#IMO_RAT_BOOLEAN", "");
			SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#dataatual",format2.format(new Date()));	
	        
	        for (int i = 0; i < listaFiadores.size(); ) {
	        	range.replaceText("#NOMEFIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#CPF_CNPJ", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	        	
	        	range.replaceText("#ESTCIVILFIADOR", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#NACIONALIDADE", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#PROF", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#END", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+listaFiadores.get(i).getPessoa().getEndereco().getEndNumero()+"- CEP: "+listaFiadores.get(i).getPessoa().getEndereco().getEndCep());
	        	break;
	        }
	        for (int i = 1; i < listaFiadores.size(); ) {
	        	range.replaceText("#NOME_FIADOR_2", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#FIADOR_CPF_CNPJ_2", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	        	
	        	range.replaceText("#EST_CIVILFIADOR_2", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#NAC_FIADOR_2", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#FIADORPROF_2", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#FIADOREND_2", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+listaFiadores.get(i).getPessoa().getEndereco().getEndNumero()+"- CEP: "+listaFiadores.get(i).getPessoa().getEndereco().getEndCep());
	        	break;
	        }
	        for (int i = 2; i < listaFiadores.size(); ) {
	        	range.replaceText("#NO_FIADOR_3", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#_CPF_CNPJ_3", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	        	
	        	range.replaceText("#ES_CIVILFIADOR_3", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#N_FIADOR_3", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#P_FIADRO_3", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#ENDFIA_3", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+listaFiadores.get(i).getPessoa().getEndereco().getEndNumero()+"- CEP: "+listaFiadores.get(i).getPessoa().getEndereco().getEndCep());
	        	break;
	        }
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Particular de Locacao de Imoveis 4");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_ParticularDeLocacaoDeImoveisReajuste() {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        List<Fiador> listaFiadores=new FiadorRN().carregarFiadoresPorImovel(this.locacao);
      
		String nomeArquivoGerado =" "+locacao.getLocId() + " - CONTRATO-PARTICULAR-DE-LOCACAO-DE-IMOVEIS-REAJUSTE.doc";

		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"CONTRATO-PARTICULAR-DE-LOCACAO-DE-IMOVEIS-REAJUSTE.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			   
			range.replaceText("#NOME_LOCADOR", this.inquilino.getPesNome());
			range.replaceText("#ESTCIVIL_LOCADOR", this.inquilino.getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");			
			range.replaceText("#PROFL_OCADOR", this.inquilino.getPesProfissao());
			range.replaceText("#CPF_CNPJ_LOCADOR", this.inquilino.getPesCpfCnpj());
			range.replaceText("#NACIONALOCADOR", this.inquilino.getPesNacionalidade());
			range.replaceText("#PRAZO", this.locacao.getLocMesesDeContrato()+"");
			range.replaceText("#TERMINO", this.locacao.getLocDataTerminioContrato().toString().replace(" 00:00:00.0", ""));
			
			
			String nomes=new String();
			String cpfs=new String();
			String estadoCivil=new String();
			String profissoes=new String();
			for(Proprietario p: listaProprietarios){
				if(p.getProAtivo()){
		        	nomes+=p.getPessoa().getPesNome()+". ";
		        	cpfs+=p.getPessoa().getPesCpfCnpj()+". ";
		        	estadoCivil+=(p.getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO")+". ";
		        	profissoes+=p.getPessoa().getPesProfissao()+". ";}
		        }
			
			range.replaceText("#NOMCE_LOCATARIO", nomes);
			range.replaceText("#NACIOLOCATARIO", this.inquilino.getPesNacionalidade());
			range.replaceText("#ESTCIVIL_LOCATORIO", estadoCivil);
			range.replaceText("#PROF_LOCATARIO", profissoes);
			range.replaceText("#CPF_CNPJ_LOCATARIO", cpfs);
			
			range.replaceText("#BAIRRO", this.imovel.getEndereco().getBairro().getBaiNome());
			range.replaceText("#CEP", this.imovel.getEndereco().getEndCep());
			range.replaceText("#INICIO", this.locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
			range.replaceText("#VALOR", this.imovel.getImoValorAluguel().toString());
			range.replaceText("#DIA", this.locacao.getLocDiaPagamentoLocador()+"");
			range.replaceText("#IMO_RAT_BOOLEAN", "");
			SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#dataatual",format2.format(new Date()));	
	        
	        for (int i = 0; i < listaFiadores.size(); ) {
	        	range.replaceText("#NOMEFIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#CPF_CNPJ", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	        	
	        	range.replaceText("#ESTCIVILFIADOR", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#NACIONALIDADE", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#PROF", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#END", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+listaFiadores.get(i).getPessoa().getEndereco().getEndNumero()+"- CEP: "+listaFiadores.get(i).getPessoa().getEndereco().getEndCep());
	        	break;
	        }
	        for (int i = 1; i < listaFiadores.size(); ) {
	        	range.replaceText("#NOME_FIADOR_2", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#FIADOR_CPF_CNPJ_2", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	        	
	        	range.replaceText("#EST_CIVILFIADOR_2", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#NAC_FIADOR_2", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#FIADORPROF_2", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#FIADOREND_2", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+listaFiadores.get(i).getPessoa().getEndereco().getEndNumero()+"- CEP: "+listaFiadores.get(i).getPessoa().getEndereco().getEndCep());
	        	break;
	        }
	        for (int i = 2; i < listaFiadores.size(); ) {
	        	range.replaceText("#NO_FIADOR_3", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#_CPF_CNPJ_3", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	        	
	        	range.replaceText("#ES_CIVILFIADOR_3", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#N_FIADOR_3", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#P_FIADRO_3", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#ENDERECOFIADOR3", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+listaFiadores.get(i).getPessoa().getEndereco().getEndNumero()+"- CEP: "+listaFiadores.get(i).getPessoa().getEndereco().getEndCep());
	        	break;
	        }
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Particular de Locacao de Imoveis Reajuste");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_ParticularDeLocacaoDeImoveisSemReajuste() {
		
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        List<Fiador> listaFiadores=new FiadorRN().carregarFiadoresPorImovel(this.locacao);
        
		String nomeArquivoGerado =" "+locacao.getLocId() + " - CONTRATO-PARTICULAR-DE-LOCACAO-DE-IMOVEIS-SEM-REAJUSTE.doc";
		
		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"CONTRATO-PARTICULAR-DE-LOCACAO-DE-IMOVEIS-SEM-REAJUSTE.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			   
			range.replaceText("#NOME_LOCADOR", this.inquilino.getPesNome());
			range.replaceText("#ESTCIVIL_LOCADOR", this.inquilino.getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");			
			range.replaceText("#PROFL_OCADOR", this.inquilino.getPesProfissao());
			range.replaceText("#CPF_CNPJ_LOCADOR", this.inquilino.getPesCpfCnpj());
			range.replaceText("#NACIONALOCADOR", this.inquilino.getPesNacionalidade());
			range.replaceText("#PRAZO", this.locacao.getLocMesesDeContrato()+"");
			range.replaceText("#TERMINO", this.locacao.getLocDataTerminioContrato().toString().replace(" 00:00:00.0", ""));
		
			
			String nomes=new String();
			String cpfs=new String();
			String estadoCivil=new String();
			String profissoes=new String();
			for(Proprietario p: listaProprietarios){
				if(p.getProAtivo()){
		        	nomes+=p.getPessoa().getPesNome()+". ";
		        	cpfs+=p.getPessoa().getPesCpfCnpj()+". ";
		        	estadoCivil+=(p.getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO")+". ";
		        	profissoes+=p.getPessoa().getPesProfissao()+". ";}
		        }
			
			range.replaceText("#NOMCE_LOCATARIO", nomes);
			range.replaceText("#NACIOLOCATARIO", this.inquilino.getPesNacionalidade());
			range.replaceText("#ESTCIVIL_LOCATORIO", estadoCivil);
			range.replaceText("#PROF_LOCATARIO", profissoes);
			range.replaceText("#CPF_CNPJ_LOCATARIO", cpfs);
			
			range.replaceText("#BAIRRO", this.imovel.getEndereco().getBairro().getBaiNome());
			range.replaceText("#CEP", this.imovel.getEndereco().getEndCep());
			range.replaceText("#INICIO", this.locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
			range.replaceText("#VALOR", this.imovel.getImoValorAluguel().toString());
			range.replaceText("#DIA", this.locacao.getLocDiaPagamentoLocador()+"");
			range.replaceText("#IMO_RAT_BOOLEAN", "");
			SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#dataatual",format2.format(new Date()));	
	        
	        for (int i = 0; i < listaFiadores.size(); ) {
	        	range.replaceText("#NOMEFIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#CPF_CNPJ", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	        	
	        	range.replaceText("#EST_CIVILFIADOR_2", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#NACIONALIDADE", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#PROF", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#END", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+listaFiadores.get(i).getPessoa().getEndereco().getEndNumero()+"- CEP: "+listaFiadores.get(i).getPessoa().getEndereco().getEndCep());
	        	break;
	        }
	        for (int i = 1; i < listaFiadores.size(); ) {
	        	range.replaceText("#NOME_FIADOR_2", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#FIADOR_CPF_CNPJ_2", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	        	
	        	range.replaceText("#EST_CIVILFIADOR_2", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#NAC_FIADOR_2", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#FIADORPROF_2", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#FIADOREND_2", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+listaFiadores.get(i).getPessoa().getEndereco().getEndNumero()+"- CEP: "+listaFiadores.get(i).getPessoa().getEndereco().getEndCep());
	        	break;
	        }
	        for (int i = 2; i < listaFiadores.size(); ) {
	        	range.replaceText("#NO_FIADOR_3", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#_CPF_CNPJ_3", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	        	
	        	range.replaceText("#ES_CIVILFIADOR_3", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#N_FIADOR_3", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#P_FIADRO_3", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#LOGRADOROFIADOR3", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+listaFiadores.get(i).getPessoa().getEndereco().getEndNumero()+"- CEP: "+listaFiadores.get(i).getPessoa().getEndereco().getEndCep());
	        	break;
	        }
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Particular de Locacao de Imoveis Sem Reajuste");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_DadosDigitarAssinatura() {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        List<Fiador> listaFiadores=new FiadorRN().carregarFiadoresPorImovel(this.locacao);
        
		String nomeArquivoGerado =  locacao.getLocId() + " - DADOS-DIGITAR-ASSINATURA.doc";

		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"DADOS-DIGITAR-ASSINATURA.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			   
			range.replaceText("#LOCATARIO", this.inquilino.getPesNome());	
			range.replaceText("#CPF_CNPJ", this.inquilino.getPesCpfCnpj());
			
			String nomes=new String();
			for(Proprietario p: listaProprietarios){
				if(p.getProAtivo()){
		        	nomes+=p.getPessoa().getPesNome()+". ";	}	        	
		        }
			
			range.replaceText("#LOCADORES", nomes);
			range.replaceText("#ENDIMOVEL", this.imovel.getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero()+"- CEP: "+this.imovel.getEndereco().getEndCep()+"- Bairro: "+this.imovel.getEndereco().getBairro().getBaiNome()+"- Cidade: "+this.imovel.getEndereco().getCidade().getCidNome());
        	
			
			SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#dataatual",format2.format(new Date()));	
	        
	        for (int i = 0; i < listaFiadores.size(); ) {
	        	range.replaceText("#FIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#CPF", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	
	        	break;
	        }
	        for (int i = 1; i < listaFiadores.size();) {
	        	range.replaceText("#2FIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#2CPF", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	
	        	break;
	        }
	        for (int i = 2; i < listaFiadores.size(); ) {
	        	range.replaceText("#3FIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#3CPF", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	
	        	break;
	        }
	        for (int i = 3; i < listaFiadores.size(); ) {
	        	range.replaceText("#4FIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#4CPF", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	
	        	break;
	        }
	        for (int i = 4; i < listaFiadores.size(); ) {
	        	range.replaceText("#5FIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#5CPF", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	
	        	break;
	        }
	        for (int i = 5; i < listaFiadores.size(); ) {
	        	range.replaceText("#6FIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#6CPF", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	
	        	break;
	        }
	        for (int i = 6; i < listaFiadores.size(); ) {
	        	range.replaceText("#7FIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#7CPF", listaFiadores.get(i).getPessoa().getPesCpfCnpj());	
	        	break;
	        }
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Assinatura");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_FichaFinanceiraDoImovel() {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        
		String nomeArquivoGerado =  locacao.getLocId() + " - FICHA-FINANCEIRA–IMOVEL-RESIDENCIAL.doc";

		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"FICHA-FINANCEIRA–IMOVEL-RESIDENCIAL.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			   
			range.replaceText("#DIALOCATARIO", this.locacao.getLocDiaPagamentoLocatario()+"");
			range.replaceText("#1DIALOCADOR", this.locacao.getLocDiaPagamentoLocatario()+"");
			if(this.imovel.getImoTaxa()!=null){
			range.replaceText("#TAXA_PCT", this.imovel.getImoTaxa().toString());}
			range.replaceText("#VALOR_ALUGUEL", this.imovel.getImoValorAluguel().toString());
			
			List<Telefone> listaTel=new TelefoneRN().carregarPessoa(this.inquilino);
			String tels=new String();
			for(Telefone t: listaTel){
				tels+=t.getTelNumero()+". ";
			}
			range.replaceText("#TEL_LOCATARIO", tels);
			range.replaceText("#NOME_LOCATARIO", this.inquilino.getPesNome());
			 Funcionario funcinario=new FuncionarioRN().carregar(this.contextoBean.getFuncionarioLogado().getPesId());
		    range.replaceText("#ATENDENTE", funcinario.getPessoa().getPesNome());
		    range.replaceText("#FILIAL", funcinario.getFilial().getFilNome());
			range.replaceText("#END", this.imovel.getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero()+"- CEP: "+this.imovel.getEndereco().getEndCep()+"- Bairro: "+this.imovel.getEndereco().getBairro().getBaiNome()+"- Cidade: "+this.imovel.getEndereco().getCidade().getCidNome());
				
			String nomes=new String();
			String tels2=new String();
			String mails=new String();
			for(Proprietario p: listaProprietarios){
				if(p.getProAtivo()){
					nomes+=p.getPessoa().getPesNome()+". ";
					List<Email> listaMail=new EmailRN().carregarPessoa(p.getPessoa());
					mails+="(";
					for(Email e: listaMail){
						mails+=e.getEmaEndereco()+"-";
					}
					mails+=")";

					List<Telefone> listaTel2=new TelefoneRN().carregarPessoa(p.getPessoa());
					tels2+="(";
					for(Telefone t: listaTel2){
						tels2+=t.getTelNumero()+"-";
					}
					tels2+=")";
				}
			}
			
			range.replaceText("#NCADADOR", nomes);
			range.replaceText("#EMAIL", mails);
			range.replaceText("#TELOCADOR", tels2);
			SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#DATA",format2.format(new Date()));	
			
	        range.replaceText("#GARANTIA", imovel.getImoGarantia());
	      /*  range.replaceText("#GARANTIA", locacao.getLo);
	        range.replaceText("#GARANTIA", imovel.getImoGarantia());
	        range.replaceText("#GARANTIA", imovel.getImoGarantia());*/
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Ficha Financeira do Imóvel");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	

	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_InformacoesSobreALocacao() {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        
		String nomeArquivoGerado =" "+locacao.getLocId() + " - imprimir-inform-inquilino.doc";
		
		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"imprimir-inform-inquilino.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			   
			range.replaceText("#CEMIG", this.imovel.getImoIdluz() != "" ? "SIM":"NÂO");
			range.replaceText("#DIA_MES_ANO", this.locacao.getLocDiaPagamentoLocatario()+"");
			range.replaceText("#ALUGUEL", this.imovel.getImoValorAluguel().toString());
			range.replaceText("#DIA", this.locacao.getLocDiaPagamentoLocatario()+"");
			
			Funcionario funcinario=new FuncionarioRN().carregar(this.contextoBean.getFuncionarioLogado().getPesId());
		    range.replaceText("#ATENDENTE", funcinario.getPessoa().getPesNome());
			SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#DATA",format2.format(new Date()));	
			
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Informações Sobre a Locacao");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_ProtocoloDeEntregaDeContratoDeLocacao() {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        
		String nomeArquivoGerado =" "+locacao.getLocId() + " - protocolo-de-entrega.doc";

		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"protocolo-de-entrega.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			   
			range.replaceText("#END", this.imovel.getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero()+"- CEP: "+this.imovel.getEndereco().getEndCep()+"- Bairro: "+this.imovel.getEndereco().getBairro().getBaiNome()+"- Cidade: "+this.imovel.getEndereco().getCidade().getCidNome());
			
			range.replaceText("#NOME", this.inquilino.getPesNome());
			range.replaceText("#CPF_CNPJ", this.inquilino.getPesCpfCnpj());
			
			String nomes=new String();
			String cpfs=new String();
			for(Proprietario p: listaProprietarios){
				if(p.getProAtivo()){
					nomes+=p.getPessoa().getPesNome()+". ";
					cpfs+=p.getPessoa().getPesCpfCnpj()+". ";
				}
			}
			
			range.replaceText("#LOCADOR", nomes);
			range.replaceText("#LOCATARIO_CPF", cpfs);
			SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#DATA",format2.format(new Date()));	
			
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Protocolo de Entrega de Contrato de Locacao");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_ReciboSeguro() {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        
		String nomeArquivoGerado =" "+locacao.getLocId() + " - recibo-seguro.doc";

		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
        
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"recibo-seguro.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			   
			range.replaceText("#LOCATARIO", this.inquilino.getPesNome());
			range.replaceText("#CPFLOCATARIO", this.inquilino.getPesCpfCnpj());
			
			String nomes=new String();			
			String cpfs=new String();
			for(Proprietario p: listaProprietarios){
				if(p.getProAtivo()){
					nomes+=p.getPessoa().getPesNome()+". ";
					cpfs+=p.getPessoa().getPesCpfCnpj()+". ";
				}
			}
			
			range.replaceText("#LOCADOR", nomes);
			range.replaceText("#CPFLOCADOR", cpfs);			
			range.replaceText("#END", this.imovel.getEndereco().getEndNome()+"- nº "+this.imovel.getEndereco().getEndNumero());
			range.replaceText("#BAIRRO", this.imovel.getEndereco().getBairro().getBaiNome());
			range.replaceText("#CEP", this.imovel.getEndereco().getEndCep());
			range.replaceText("#COD", this.imovel.getImoId()+"");			
			range.replaceText("#ATIVIDADE", this.inquilino.getPesProfissao());
			range.replaceText("#TIPO_IMOVEL", this.imovel.getTipoimovel().getTipNome());			
			range.replaceText("#VLR_ALUG", this.imovel.getImoValorAluguel().toString());
			
			Funcionario funcinario=new FuncionarioRN().carregar(this.contextoBean.getFuncionarioLogado().getPesId());
		    range.replaceText("#ATENDENTE", funcinario.getPessoa().getPesNome());
			SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#DATA",format2.format(new Date()));	
			
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Recibo Seguro");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_ContratoLocacaoSolidario() {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        
		String nomeArquivoGerado =" "+locacao.getLocId() + " - cont_loc_solidario.doc";

		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"cont_loc_solidario.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			
			String nomes=new String();
			String cpfs=new String();
			String estadoCivil=new String();
			String profissoes=new String();
			String nacionalidade=new String();
			for(Proprietario p: listaProprietarios){
				if(p.getProAtivo()){
					nomes+=p.getPessoa().getPesNome()+". ";
					cpfs+=p.getPessoa().getPesCpfCnpj()+". ";
					estadoCivil+=(p.getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO")+". ";
					profissoes+=p.getPessoa().getPesProfissao()+". ";
					nacionalidade+=p.getPessoa().getPesNacionalidade()+". ";
				}
			}
			
			
			range.replaceText("#PRAZO", locacao.getLocMesesDeContrato()+"");
			range.replaceText("#NOMELOCADOR", nomes);
			range.replaceText("#ESTCIVILLOCADOR", estadoCivil);
			range.replaceText("#PROFLOCADOR", profissoes);
			range.replaceText("#CPFCNPJLOCADOR", cpfs);
			range.replaceText("#ACIONALOCADOR", nacionalidade);
			
			range.replaceText("#DATETERMINO", locacao.getLocDataTerminioContrato().toString().replace(" 00:00:00.0", ""));
			   
			range.replaceText("#NOMELOCATARIO", this.inquilino.getPesNome());
			range.replaceText("#CPFLOC", this.inquilino.getPesCpfCnpj());
			
			range.replaceText("#NACIOLOCATARIOSOLID", this.inquilino.getPesNacionalidade());
			range.replaceText("#ESTCIVILLOCATORIO", (this.inquilino.getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO"));
			range.replaceText("#PROFLOCATARIO", this.inquilino.getPesProfissao());
			range.replaceText("#CPFLOC", this.inquilino.getPesCpfCnpj());
			
	        range.replaceText("#END", this.imovel.getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero());
	        range.replaceText("#CEP", this.imovel.getEndereco().getEndCep());
	        range.replaceText("#BAIRRO",this.imovel.getEndereco().getBairro().getBaiNome());
	        
	        range.replaceText("#DATEINICIO", this.locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
	        //range.replaceText("#DATETERMINO", this.locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
	        range.replaceText("#VALOR", this.imovel.getImoValorAluguel().toString());
	        range.replaceText("#DIA", this.locacao.getLocDiaPagamentoLocatario()+"");
			SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#dataatual",format2.format(new Date()));	
	       
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Contrato Locacao Solidario");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
	
	/** Informações contrato-particular - Relatorio ok*/
	public void gerarContrato_ContratoPadrao() {
		this.inquilino          = new PessoaRN().carregar(this.locacao.getPessoa().getPesId());
		this.imovel             = new ImovelRN().carregar(this.locacao.getImovel().getImoId());
        this.listaProprietarios = new ProprietarioRN().carregarProprietarios(imovel);
        List<Fiador> listaFiadores=new FiadorRN().carregarFiadoresPorImovel(this.locacao);
        
		String nomeArquivoGerado =" "+locacao.getLocId() + " - contrato_padrao.doc";

		ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "contratos"
				+File.separator + "gerados"+ File.separator + this.imovel.getTipoimovel().getTipNome()
				+ File.separator +imovel.getImoId());
		File diretorio = new File(caminho);
		diretorio.mkdirs();
		SimpleDateFormat format= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
        nomeArquivoGerado=format.format(new Date())+"-"+nomeArquivoGerado;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String caminhoRelatorio = context.getExternalContext().getRealPath("resources"+File.separator+"contratos"+File.separator+"contrato_padrao.doc");
			
			HWPFDocument document = new HWPFDocument(new FileInputStream(new File(caminhoRelatorio)));
			
			Range range = document.getRange(); 
			
			if(this.imovel.getImoValorAluguel()==null){
				this.imovel.setImoValorAluguel(new BigDecimal(0));
			}
			
			String nomes=new String();
			String cpfs=new String();
			String estadoCivil=new String();
			String profissoes=new String();
			String nacionalidade=new String();
			for(Proprietario p: listaProprietarios){
				if(p.getProAtivo()){
					nomes+=p.getPessoa().getPesNome()+". ";
					cpfs+=p.getPessoa().getPesCpfCnpj()+". ";
					estadoCivil+=(p.getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO")+". ";
					profissoes+=p.getPessoa().getPesProfissao()+". ";
					nacionalidade+=p.getPessoa().getPesNacionalidade()+". ";
				}
			}
			
			range.replaceText("#NOMELOCADOR", nomes);
			range.replaceText("#ESTCIVILLOCADOR", estadoCivil);
			range.replaceText("#PROFLOCADOR", profissoes);
			range.replaceText("#CPFCNPJLOCADOR", cpfs);
			range.replaceText("#ACIONALOCADOR", nacionalidade);
			
			range.replaceText("#NDD",imovel.getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero()+"- CEP: "+this.imovel.getEndereco().getEndCep());
			range.replaceText("#DATETERMINIO", locacao.getLocDataTerminioContrato().toString().replace(" 00:00:00.0", ""));
			range.replaceText("#PRAZO", locacao.getLocMesesDeContrato()+"");
			   
			range.replaceText("#NOMELOCATARIO", this.inquilino.getPesNome());
			range.replaceText("#CPFLOC", this.inquilino.getPesCpfCnpj());
			
			range.replaceText("#NACIOLOCATARIOSOLID", this.inquilino.getPesNacionalidade());
			range.replaceText("#ESTCIVILLOCATORIO", (this.inquilino.getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO"));
			range.replaceText("#PROFLOCATARIO", this.inquilino.getPesProfissao());
			range.replaceText("#CPFLOC", this.inquilino.getPesCpfCnpj());
			
	        range.replaceText("#END", this.imovel.getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero());
	        range.replaceText("#CEP", this.imovel.getEndereco().getEndCep());
	        range.replaceText("#BAIRRO",this.imovel.getEndereco().getBairro().getBaiNome());
	        
	        range.replaceText("#DATEINICIO", this.locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
	        //range.replaceText("#DATETERMINO", this.locacao.getLocDataInicialContrato().toString().replace(" 00:00:00.0", ""));
	        range.replaceText("#VALOR", this.imovel.getImoValorAluguel().toString());
	        range.replaceText("#DIA", this.locacao.getLocDiaPagamentoLocatario()+"");
			SimpleDateFormat format2= new SimpleDateFormat("d 'de' MMMM 'de' yyyy"); //você pode usar outras máscaras 
	        range.replaceText("#dataatual",format2.format(new Date()));	
	        
	        for (int i = 0; i < listaFiadores.size(); ) {
	        	range.replaceText("#FIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#ESTCIVILFIADOR", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#NACIONFIADOR", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#PROIADOR", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#CPF", listaFiadores.get(i).getPessoa().getPesCpfCnpj());
	        	 range.replaceText("#ENR", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero()+"- CEP: "+this.imovel.getEndereco().getEndCep());
	 	        
	        	break;
	        }
	        for (int i = 1; i < listaFiadores.size();) {
	        	range.replaceText("#2FIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#2ESTCIVILFIADOR", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#2ACIONALIDADE", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#2PROIADOR", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#2CPF", listaFiadores.get(i).getPessoa().getPesCpfCnpj());
	        	 range.replaceText("#2ENR", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero()+"- CEP: "+this.imovel.getEndereco().getEndCep());
	 	        
	        	break;
	        }
	        for (int i = 2; i < listaFiadores.size(); ) {
	        	range.replaceText("#3FIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#3ESTCIVILFIADOR", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#3CIONALIDADE", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#3PROIADOR", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#3CPF", listaFiadores.get(i).getPessoa().getPesCpfCnpj());
	        	 range.replaceText("#3ENR", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero()+"- CEP: "+this.imovel.getEndereco().getEndCep());
	 	        
	        	break;
	        }
	        for (int i = 3; i < listaFiadores.size(); ) {
	        	range.replaceText("#4FIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#4ESTCIVILFIADOR", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#4CIONALIDADE", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#4PROIADOR", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#4CPF", listaFiadores.get(i).getPessoa().getPesCpfCnpj());
	        	 range.replaceText("#4ENR", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero()+"- CEP: "+this.imovel.getEndereco().getEndCep());
	 	        
	        	break;
	        }
	        for (int i = 4; i < listaFiadores.size(); ) {
	        	range.replaceText("#5FIADOR", listaFiadores.get(i).getPessoa().getPesNome());
	        	range.replaceText("#5ESTCIVILFIADOR", listaFiadores.get(i).getPessoa().getPesEstadoCivil()=='S'? "CASADO":"SOLTEIRO");
	        	range.replaceText("#5CIONALIDADE", listaFiadores.get(i).getPessoa().getPesNacionalidade());
	        	range.replaceText("#5PROIADOR", listaFiadores.get(i).getPessoa().getPesProfissao());
	        	range.replaceText("#5CPF", listaFiadores.get(i).getPessoa().getPesCpfCnpj());
	        	 range.replaceText("#5ENR", listaFiadores.get(i).getPessoa().getEndereco().getEndNome()+"-"+this.imovel.getEndereco().getEndNumero()+"- CEP: "+this.imovel.getEndereco().getEndCep());
	 	        
	        	break;
	        }
	       
	        
	        document.write(new FileOutputStream(new File(caminho, nomeArquivoGerado)));
	                
	        baixarArquivo(caminho + File.separator+nomeArquivoGerado,"Contrato Padrão");
	        
	        this.contextoBean.mostrarAviso("Sucessefully!");
		} catch (IOException e) {
			this.contextoBean.mostrarErro("Error!");
		} 
	}
}