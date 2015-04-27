package br.com.locadora.web;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.ContaCorrente;
import br.com.locadora.modelo.DestinacaoLocacao;
import br.com.locadora.modelo.FaixaImpostoDeRenda;
import br.com.locadora.modelo.Fiador;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.GrupoDeContas;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.IndicesReajustes;
import br.com.locadora.modelo.InformacaoAdicional;
import br.com.locadora.modelo.Iptu;
import br.com.locadora.modelo.Locacao;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Proprietario;
import br.com.locadora.modelo.ProprietariosLocacao;
import br.com.locadora.modelo.SeguroFianca;
import br.com.locadora.modelo.SeguroIncendio;
import br.com.locadora.modelo.SituacaoLocacao;
import br.com.locadora.modelo.StatusLocacao;
import br.com.locadora.rn.ContaCorrenteRN;
import br.com.locadora.rn.DestinacaoLocacaoRN;
import br.com.locadora.rn.FaixaImpostoDeRendaRN;
import br.com.locadora.rn.FiadorRN;
import br.com.locadora.rn.FuncionarioRN;
import br.com.locadora.rn.GrupoDeContasRN;
import br.com.locadora.rn.ImovelRN;
import br.com.locadora.rn.InformacaoAdicionalRN;
import br.com.locadora.rn.IptuRN;
import br.com.locadora.rn.LocacaoRN;
import br.com.locadora.rn.PessoaRN;
import br.com.locadora.rn.ProprietarioRN;
import br.com.locadora.rn.ProprietariosLocacaoRN;
import br.com.locadora.rn.SeguroFiancaRN;
import br.com.locadora.rn.SeguroIncendioImovelComercialRN;
import br.com.locadora.rn.SeguroIncendioImovelResidencialRN;
import br.com.locadora.rn.SeguroIncendioRN;
import br.com.locadora.rn.SituacaoImovelRN;
import br.com.locadora.rn.SituacaoLocacaoRN;
import br.com.locadora.rn.StatusLocacaoRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "contratoLocacaoBean")
@ViewScoped
public class ContratoLocacaoBean implements Serializable {
	
	private static final long serialVersionUID = -3440017386938878151L;
	
	@Getter @Setter private Locacao       locacao        = new Locacao();
	@Getter @Setter private IndicesReajustes  indicesReajustes=new IndicesReajustes();
	@Getter @Setter private Iptu					 iptu				  = new Iptu();
	@Getter @Setter private SeguroFianca seguroFianca=new SeguroFianca();
	@Getter @Setter private SeguroIncendio seguroIncendio=new SeguroIncendio();
	@Getter @Setter private DestinacaoLocacao destinacaoLocacao= new DestinacaoLocacao();
	@Getter @Setter private StatusLocacao statusLocacao=new StatusLocacao();
	@Getter @Setter private SituacaoLocacao situacaoLocacao=new SituacaoLocacao();
	@Getter @Setter private InformacaoAdicional informacaoAdicional=new InformacaoAdicional();
	@Getter @Setter private Funcionario	  indicacao		 =new Funcionario();
	@Getter @Setter private Imovel        imovel         = new Imovel();
	@Getter @Setter private Pessoa        inquilino      = new Pessoa();
	@Setter         private List<Fiador>  listaFiador;
	@Setter         private List<Imovel>  listaImoveis;
	@Setter         private List<Pessoa>  listaInquilino;
	@Getter @Setter private List<Locacao> listaLocacoes  = new ArrayList<Locacao>();
	@Getter @Setter private List<Iptu>				 listaIptu			  = new ArrayList<Iptu>();
	@Getter @Setter private List<InformacaoAdicional> listaInformacaoAdicinal;
	@Getter @Setter private List<Proprietario> listaProprietarioImovel;//lista dos proprietario do imovel, irá ser carregada após salvar o contrato, a lista será necessario para geração dos contratos.
	
	/** Variaveis locais e campos ocultos */
	@Getter @Setter private String       fiadorJson = "";
	@Getter @Setter private boolean      alteracao  = false;
	@Getter @Setter private boolean      preenchimentoSeguroIncendio  = false;
	@Getter @Setter private boolean      bloquearPreenchimentoSeguroIncendio  = false;
	@Getter @Setter private boolean      bloquearPreenchimentoIptu  = false;
	@Getter @Setter private boolean      preenchimentoSeguroIncendioDatas  = false;
	@Getter @Setter private boolean      gerarFinanceiroIRRF  = false;//variavel para geração do imposto de renda após gravar o contrato da locação

	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public ContratoLocacaoBean() {
		carregarListas();
		carregar();		
	}

	public void carregarListas() {
		this.listaFiador    = null;
		this.listaInquilino = null;
		this.listaImoveis=null;
		this.listaInformacaoAdicinal=null;
		this.listaLocacoes  = new LocacaoRN().listar();
	}

	public void carregar() {
		try {
			boolean carregouInformacoesPorParametro = false;
			this.iptu=new Iptu();
			this.informacaoAdicional=new InformacaoAdicional();
			Calendar anoTual = Calendar.getInstance();			
			this.iptu.setIptAno(anoTual.get(Calendar.YEAR));
			
			int locacaoID = this.contextoBean.getParametro("id", -1);
			int pessoaID = this.contextoBean.getParametro("pessoa-id", -1);
			int imovelID = this.contextoBean.getParametro("imovel-id", -1);
			
			/** Usado para carregar informações de imóvel e inquilino por parametro */
			if (pessoaID > 0 && imovelID > 0) {
				Imovel imovelCarregado = new ImovelRN().carregar(imovelID);
				
				if (imovelCarregado == null)
					imovelCarregado = new Imovel();
				
				this.imovel = imovelCarregado;
				
				Pessoa inquilinoCarregado = new PessoaRN().carregar(pessoaID);
				
				if (inquilinoCarregado == null)
					inquilinoCarregado = new Pessoa();
				
				this.inquilino = inquilinoCarregado;
				
				carregouInformacoesPorParametro = true;
			}
			
			/** Usado para carregar um cadastro já existente */
			if (locacaoID <= 0) {
				this.locacao.setLocCategoria("RESIDENCIAL");
				this.locacao.setLocMesesDeContrato(30);
				this.locacao.setLocDataInicialContrato(new Date());
				calcularDestinacao();
				this.locacao.setLocTipo("PADRÃO");
				ContextoBean contextoBean = ContextoUtil.getContextoBean();
				String fun = contextoBean.getFuncionarioLogado().getFunLoguin();
				Funcionario funcionarioLogado = new FuncionarioRN().buscarPorLogin(fun);
				this.locacao.setLocResponsavel(funcionarioLogado.getPessoa().getPesNome());
				modeloContrato();
				this.alteracao = false;
			} else if (carregouInformacoesPorParametro == false) {
				this.alteracao = true;
				LocacaoRN locacaoRN= new LocacaoRN();
				Locacao locacaoCarregado = locacaoRN.carregar(locacaoID);

				this.locacao = locacaoCarregado;
				
				if (this.locacao == null) {
					this.locacao   = new Locacao();
					this.locacao.setLocCategoria("RESIDENCIAL");
					this.locacao.setLocDataInicialContrato(new Date());
					this.locacao.setLocMesesDeContrato(30);
					calcularDestinacao();
					ContextoBean contextoBean = ContextoUtil.getContextoBean();
					String fun = contextoBean.getFuncionarioLogado().getFunLoguin();
					Funcionario funcionarioLogado = new FuncionarioRN().buscarPorLogin(fun);
					this.locacao.setLocResponsavel(funcionarioLogado.getPessoa().getPesNome());
					this.alteracao = false;
				}
				
				this.imovel    = this.locacao.getImovel();
				this.iptu=new Iptu();
				this.indicesReajustes=this.locacao.getIndicesReajustes();
				this.inquilino = this.locacao.getPessoa();
				if(this.locacao.getLocFuncionarioIndicacao()!=null){
				this.indicacao= new FuncionarioRN().carregar(this.locacao.getLocFuncionarioIndicacao());}
				else{
					this.locacao.setLocFuncionarioIndicacao(0);
				}
				
				if(this.locacao.getDestinacaoLocacao()!=null){
					this.destinacaoLocacao=new DestinacaoLocacaoRN().carregar(this.locacao.getDestinacaoLocacao().getDesLocId());
				}else{
					this.destinacaoLocacao=new DestinacaoLocacao();
				}
				
				if(this.locacao.getStatusLocacao()!=null){
					this.statusLocacao=new StatusLocacaoRN().carregar(this.locacao.getStatusLocacao().getStaLocId());
				}else{
					this.statusLocacao=new StatusLocacao();
				}
				
				if(this.locacao.getSituacaoLocacao()!=null){
					this.situacaoLocacao=new SituacaoLocacaoRN().carregar(this.locacao.getSituacaoLocacao().getSitLocId());
				}else{
					this.situacaoLocacao=new SituacaoLocacao();
				}
				
				if(this.locacao.getSeguroFianca()!=null){
					this.seguroFianca=this.locacao.getSeguroFianca();
				}else{
					this.seguroFianca=new SeguroFianca();
				}
				
				if(this.locacao.getSeguroIncendio()!=null){
					this.seguroIncendio=this.locacao.getSeguroIncendio();
				}else{
					this.seguroIncendio=new SeguroIncendio();
					this.seguroIncendio.setSegIncSeguroParticular(this.locacao.getLocSeguroIncendioParticular());
				}
				
				if(this.indicacao==null)
					this.indicacao=new Funcionario();
				
				if (this.imovel == null)
					this.imovel = new Imovel();
				
				if (this.inquilino == null)
					this.inquilino = new Pessoa();
				if(this.listaInformacaoAdicinal==null){
					this.listaInformacaoAdicinal=new ArrayList<InformacaoAdicional>();
					InformacaoAdicionalRN informacaoAdicionalRN=new InformacaoAdicionalRN();
					this.listaInformacaoAdicinal=informacaoAdicionalRN.carregarPorLocacao(this.locacao);
				}

				this.listaIptu= new IptuRN().carregarPorLocacao(this.locacao);
		
				carregarFiadores();
			}
		} catch (NumberFormatException e) {
			this.alteracao = false;
		}
	}

	public void gravar() {
		
		calcularDestinacao();
		
		if(this.locacao.getLocCorretor()==null){
			this.contextoBean.mostrarErro("Adicione o corretor!");
			return;	
		}
		if (fiadorJson.equalsIgnoreCase("")) {
			this.contextoBean.mostrarErro("Adicione pelo menos um fiador!");
			return;
		}
		int imovelID = imovel.getImoId();
		this.imovel = new ImovelRN().carregar(imovelID);
		
		if (this.imovel == null) {
			this.contextoBean.mostrarErro("Imóvel inválido!");
			return;
		}
		
		if(this.inquilino==null||this.inquilino.getPesId()<1){
			this.contextoBean.mostrarErro("Adicione o inquilino!");
			return;
		}
		
		if(this.inquilino.getPesId()==0){
			this.contextoBean.mostrarErro("Adicione o inquilino!");
			return;
		}

		int inquilinoID = inquilino.getPesId();
		this.inquilino = new PessoaRN().carregar(inquilinoID);
		
		if (this.inquilino == null) {
			this.inquilino=new Pessoa();
			this.contextoBean.mostrarErro("Inquilino inválido!");
			return;
		}
		/** Verificando os dias recebimento Locador e Locatario */
		if(this.locacao.getLocDiaPagamentoLocatario()==null
				||this.locacao.getLocDiaPagamentoLocador()==null){
			//caso dia pagamento Locatario e Locador forem null irá tratar o vencimento na geração do contrato

		}else{
			if((this.locacao.getLocDiaPagamentoLocatario()>30)||(this.locacao.getLocDiaPagamentoLocatario()<=0)){
				this.contextoBean.mostrarErro("DIA RECEBIMENTO LOCATÁRIO entre(1-30)");
				return;
			}
			if(this.locacao.getLocDiaPagamentoLocatario()==null
					||this.locacao.getLocDiaPagamentoLocatario()==0){
				this.contextoBean.mostrarErro("Confirme novamente o DIA RECEBIMENTO LOCATÁRIO");
				return;
			}
			if(this.locacao.getLocDiaPagamentoLocador()==null
					||this.locacao.getLocDiaPagamentoLocador()==0){
				int cont=this.locacao.getLocDiaPagamentoLocatario()+7;
				if(cont <=30){
					this.locacao.setLocDiaPagamentoLocador(cont);
				}else{
					cont=cont-30;
					this.locacao.setLocDiaPagamentoLocador(cont);
				}
			}
			int diaLocador=this.locacao.getLocDiaPagamentoLocador();
			int diaLocatario=this.locacao.getLocDiaPagamentoLocatario();
			if(diaLocador>=8){
				if((diaLocador-diaLocatario)!=7){
					this.locacao.setLocDiaPagamentoLocador(null);
					this.locacao.setLocDiaPagamentoLocatario(null);
					this.contextoBean.mostrarErro("Confirme novamente o DIA RECEBIMENTO LOCATÁRIO E LOCADOR");
					return ;
				}
			}else{
				int contDia=(31-diaLocatario)+diaLocador;
				if(contDia!=7){
					this.locacao.setLocDiaPagamentoLocador(null);
					this.locacao.setLocDiaPagamentoLocatario(null);
					this.contextoBean.mostrarErro("Confirme novamente o DIA RECEBIMENTO LOCATÁRIO E LOCADOR");
					return ;
				}
			}
		}

		/** Verificando os dados do IPTU */
		if(!this.bloquearPreenchimentoIptu){
			Calendar anoTual = Calendar.getInstance();
			if(this.iptu.getIptAno()<anoTual.get(Calendar.YEAR)){
				this.contextoBean.mostrarErro("Ano do IPTU tem que ser acima de "+anoTual.get(Calendar.YEAR));
				return;
			}

			Calendar dataInicio = Calendar.getInstance();
			dataInicio.setTime(this.locacao.getLocDataInicialContrato());
			int mesesRestantes=12-(dataInicio.get(Calendar.MONTH));

			if(this.iptu.getIptParcelas() > mesesRestantes){
				this.contextoBean.mostrarErro("Numero de parcelas maior que o numero de meses restantes("+mesesRestantes+") para fim de ano.");
				return;
			}
			if(this.iptu.getIptParcelas()>1){
				Calendar venc =Calendar.getInstance();
				venc.setTime(this.locacao.getLocDataInicialContrato());
				int diasRestantesAno=(365-venc.get(Calendar.DAY_OF_YEAR));
				double val=arredondarCasasDecimais(((this.iptu.getIptValor()/365)*diasRestantesAno)/this.iptu.getIptParcelas());
				if(val<50){
					int parcelaIdeal=this.iptu.getIptParcelas();
					boolean fim=true;
					for(;fim;){
						double v=arredondarCasasDecimais(((this.iptu.getIptValor()/365)*diasRestantesAno)/parcelaIdeal);
						if(v>=50)
							fim=false;
						parcelaIdeal--;
						if(parcelaIdeal<=1)
							fim=false;
					}
					this.contextoBean.mostrarErro("O valor das parcelas do IPTU não pode ser menor que R$50,00. Máximo "+parcelaIdeal+" parcela(s) para este contrato.");
					return;	
				}
			}
		}
		
		/** Verificando a situação do imovel */
			if(this.imovel.getImoTaxa()!=null){
				if(this.imovel.getImoValorAluguel()==null){
					this.contextoBean.mostrarErro("Preencha o campo de valor do aluguel no cadastro de imovel para efetuar o cadastro de locação. Codigo do imóvel: "+this.imovel.getImoId());
					return;
				}
				double conta=(this.imovel.getImoValorAluguel().doubleValue()*this.imovel.getImoTaxa())/100;
				this.locacao.setLocTaxaAdministrativa(conta);
				if(this.locacao.getLocCategoria().equalsIgnoreCase("RESIDENCIAL")){
					if(conta<39)
						this.locacao.setLocTaxaAdministrativa(39d);
				}
				if(this.locacao.getLocCategoria().equalsIgnoreCase("COMERCIAL")){
					if(conta<30)
						this.locacao.setLocTaxaAdministrativa(30d);
				}

			}else{
				this.contextoBean.mostrarErro("Preencha o campo da taxa administrativa no cadastro de imovel para efetuar o cadastro de locação. Codigo do imóvel: "+this.imovel.getImoId());
				return;
			}
		if(this.imovel.getImoCategoria()!= this.locacao.getLocCategoria()){
			this.imovel.setImoCategoria(this.locacao.getLocCategoria());
		}

		/** Altera informações no imóvel */
		imovel.setImoDataInicioLocacao(new Date());
		this.situacaoLocacao=new SituacaoLocacaoRN().carregar(this.situacaoLocacao.getSitLocId());
		imovel.setSituacaoimovel(new SituacaoImovelRN().carregar(this.situacaoLocacao.getSituacaoimovel().getSitId()));
		
		/** Seguro Incendio Particular */
		if(this.seguroIncendio.getSegIncSeguroParticular()){
			this.locacao.setLocSeguroIncendioParticular(this.seguroIncendio.getSegIncSeguroParticular());
		}
		
		new ImovelRN().salvar(imovel);		
		
		/** Grava contrato */
		
		//super.g
		locacao.setImovel(imovel);
		locacao.setLocFuncionario(this.contextoBean.getFuncionarioLogado().getPesId());
		locacao.setFilial(this.contextoBean.getFuncionarioLogado().getFilial());
		locacao.setLocDataCadastro(new Date());
		locacao.setPessoa(inquilino);
		locacao.setStatusLocacao(this.statusLocacao);
		locacao.setSituacaoLocacao(this.situacaoLocacao);
		locacao.setIndicesReajustes(this.indicesReajustes);	
		
		// Seguro Fiança
		if ((this.seguroFianca.getSegFiaApolice() != null)
				&& (this.seguroFianca.getSegFiaCorretar() != null)
				&& (this.seguroFianca.getSegFiaSeguradora() != null)				
				&& (this.seguroFianca.getSegFiaValorTotal() != null)
				&& (this.seguroFianca.getSegFiaPlano() != null)		
				&& (this.seguroFianca.getSegFiaValorParcela() != null)
				) {
			if ((this.seguroFianca.getSegFiaApolice() != "")
					&& (this.seguroFianca.getSegFiaCorretar() != "")
					&& (this.seguroFianca.getSegFiaSeguradora() != "")				
					&& (this.seguroFianca.getSegFiaValorTotal() != 0.0)
					&& (this.seguroFianca.getSegFiaPlano() != 0)		
					&& (this.seguroFianca.getSegFiaValorParcela() != 0.0)
					) {
				if(this.seguroFianca.getSegFiaPlano()<11){
					this.contextoBean.mostrarErro("O plano seguro fiança deve está entre (11-12) parcelas");
					return;	
				}
				if((arredondarCasasDecimais(this.seguroFianca.getSegFiaValorParcela()))!=(arredondarCasasDecimais(this.seguroFianca.getSegFiaValorTotal()/this.seguroFianca.getSegFiaPlano()))){
					this.contextoBean.mostrarErro("Calculo das parcelas seguro fiança errado.");
					return;	
				}
				this.locacao.setSeguroFianca(this.seguroFianca);
			}
		}
		//Seguro Incendio
		this.destinacaoLocacao=new DestinacaoLocacaoRN().carregar(this.destinacaoLocacao.getDesLocId());
		locacao.setDestinacaoLocacao(this.destinacaoLocacao);
		if((this.destinacaoLocacao!=null)&&(this.destinacaoLocacao.getDesLocSeguro().equalsIgnoreCase("Passível de Seguro"))){
			if(!this.bloquearPreenchimentoSeguroIncendio){
				if((this.seguroIncendio.getSegIncSeguradora() != "")
						|| (this.seguroIncendio.getSegIncApolice() != "")){
					this.locacao.setSeguroIncendio(this.seguroIncendio);
				}
			}
		}
			
		//	carregar Corretor
		Funcionario funcionario=new FuncionarioRN().carregar(locacao.getLocCorretor());
		if(funcionario==null){
			this.contextoBean.mostrarErro("Corretor não encontrado.");
			return;
		}else{
			locacao.setLocCorretorNome(funcionario.getPessoa().getPesNome());
		}

		try {
			JSONArray array = new JSONArray(fiadorJson);
			
			Fiador fiad = new Fiador();
			FiadorRN fiadorRN = new FiadorRN();
			
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				
				int fiadorId = Integer.parseInt(jsonObject.getString("fiadorId") + "");
				fiad = fiadorRN.carregar(fiadorId);
				
				locacao.setLocFuncionarioIndicacao(this.indicacao.getPesId());
				
				locacao.getFiadors().add(fiad);
				fiad.getLocacaos().add(locacao);
				
				new FiadorRN().salvar(fiad);
				new LocacaoRN().salvar(locacao);
				fiad=new Fiador();
			}
			
		} catch (JSONException | NumberFormatException e) {
			this.contextoBean.mostrarAviso("Ocorreu um problema ao salvar os fiadores!");
			return;
		}
		
		/** Grava INFORMAÇÕES ADICIONAIS */
		if((this.informacaoAdicional.getInfAdiObservacao()!=null)&&(!this.informacaoAdicional.getInfAdiObservacao().equalsIgnoreCase(""))){
			this.informacaoAdicional.setLocacao(this.locacao);
			this.informacaoAdicional.setInfAdiFuncionario(this.contextoBean.getFuncionarioLogado().getPesId());
			this.informacaoAdicional.setInfAdiSetor(this.contextoBean.getFuncionarioLogado().getFunCargo());
			this.informacaoAdicional.setInfAdiData(new Date());
			InformacaoAdicionalRN informacaoAdicionalRN=new InformacaoAdicionalRN();
			informacaoAdicionalRN.salvar(this.informacaoAdicional);
		}
		
		//IPTU
		if(!this.bloquearPreenchimentoIptu){		
			IptuRN iptuRN=new IptuRN();

			this.iptu.setLocacao(this.locacao);
			iptuRN.salvar(this.iptu);
		}
		
		//Seguro Incendio
		if((this.destinacaoLocacao!=null)&&(this.destinacaoLocacao.getDesLocSeguro().equalsIgnoreCase("Passível de Seguro"))){
			if(!this.bloquearPreenchimentoSeguroIncendio){
				if((this.seguroIncendio.getSegIncSeguradora() != "")
						|| (this.seguroIncendio.getSegIncApolice() != "")){
					this.seguroIncendio.setLocacao(this.locacao);
					new SeguroIncendioRN().salvar(this.seguroIncendio);
				}
			}
		}
		
		// Seguro Fiança
		if ((this.locacao.getSeguroFianca() != null)){			
				this.seguroFianca.setLocacao(this.locacao);
				new SeguroFiancaRN().salvar(this.seguroFianca);
		}

		//salvar na tabela proprietarios_Locacao os proprietarios do imovel no momento do contrato
		this.listaProprietarioImovel=new ProprietarioRN().carregarProprietarios(this.imovel);
		ProprietariosLocacaoRN proprietariosLocacaoRN=new ProprietariosLocacaoRN();
		ProprietariosLocacao proprietariosLocacao=new ProprietariosLocacao();
		for(Proprietario p: this.listaProprietarioImovel){
			if(p.getProAtivo()){
			proprietariosLocacao.setLocacao(locacao);
			proprietariosLocacao.setProLocIdpessoa(p.getPessoa().getPesId());
			proprietariosLocacao.setProLocIdimovel(p.getImovel().getImoId());
			proprietariosLocacaoRN.salvar(proprietariosLocacao);
			proprietariosLocacao=new ProprietariosLocacao();
			}
			if((p.getPessoa().getPesTipo() == 'F')
					&&(this.inquilino.getPesTipo() == 'J')
					&&(this.imovel.getImoValorAluguel().doubleValue()>(1778.77))){
				this.gerarFinanceiroIRRF=true;
			}
		}
		
		if((this.destinacaoLocacao!=null)&&(this.destinacaoLocacao.getDesLocSeguro().equalsIgnoreCase("Passível de Seguro"))){
			if(!this.bloquearPreenchimentoSeguroIncendio)
				gerarFinanceiroSeguroIncendio();
		}

		gerarFinanceiroPrimeiroAluguel();
		gerarFinanceiroLocacao();
		
		if(!this.bloquearPreenchimentoIptu){	
			gerarFinceiroIPTU();
		}
		
		if(this.seguroFianca.getSegFiaId()>0)
			gerarFinanceiroSeguroFianca();
		
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("restrito/locacao/consulta-contrato.jsf");
	}
	
	public void gerarFinanceiroPrimeiroAluguel(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		

		ContaCorrenteRN contaCorrenteRN=new ContaCorrenteRN();
		Calendar c = Calendar.getInstance();
		c.setTime(this.locacao.getLocDataInicialContrato());//add a data do inicio da locação
		
		Double valorLocatario;//inquilino
		
		//Gerando o Locatario
		Double val=this.imovel.getImoValorAluguel().doubleValue();
		valorLocatario=(arredondarCasasDecimais((val/30)*this.imovel.getImoDiasDeCadastro()));		
		ContaCorrente contaCorrente=new ContaCorrente();
		contaCorrente.setConCorValor(valorLocatario);
		contaCorrente.setConCorDataEmissao(new Date());
		if(this.locacao.getLocModoPamento().equalsIgnoreCase("GARANTIDO POSTERIOR")){
		c.add(Calendar.DATE, 30);
		}
		Date venc=c.getTime();
		contaCorrente.setConCorDataVencimento(c.getTime());
		c.setTime(this.locacao.getLocDataInicialContrato());
		c.add(Calendar.DATE, (this.imovel.getImoDiasDeCadastro()-1));
		contaCorrente.setConCorHistorico("DIAS DE CADASTRO PERIODO "+ dateFormat.format(this.locacao.getLocDataInicialContrato())+ " A " +dateFormat.format(c.getTime()));
		contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
		contaCorrente.setConCorTipo(true);
		contaCorrente.setConCorSituacao(false);
		contaCorrente.setPessoa(this.inquilino);
		contaCorrente.setConCorTipoCliente("LOCATARIO");
		//Grupo de Contas Temporario
		GrupoDeContas deContasDiasDeCadastro=new GrupoDeContasRN().buscarPorGrupoDeContas("DIAS DE CADASTRO");
		contaCorrente.setGrupoDeContas(deContasDiasDeCadastro);
		contaCorrente.setConCorObservacao("");
		contaCorrenteRN.salvar(contaCorrente);
		
		//Gerar financeiro IRRF Locatario
		//Grupo de Contas Temporario
		GrupoDeContas deContasIRRF=new GrupoDeContasRN().buscarPorGrupoDeContas("IRRF");				

		double valIRRFTotal=calcularIRRFPrimeiroAluguel(this.imovel.getImoValorAluguel().doubleValue()-valorLocatario);
		if((this.gerarFinanceiroIRRF)&&(valorLocatario>(1787.77))&&(valIRRFTotal>=10)){
			//Valores inferiores a R$ 10,00 não será deduzido o Imposto de Renda, ficando isento locatário/locador.
			contaCorrente=new ContaCorrente();
		contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
		contaCorrente.setConCorSituacao(false);
		contaCorrente.setConCorTipo(true);
		contaCorrente.setGrupoDeContas(deContasIRRF);
		contaCorrente.setConCorDataVencimento(this.locacao.getLocDataInicialContrato());
		contaCorrente.setConCorDataEmissao(new Date());
		contaCorrente.setConCorValor(valIRRFTotal);
		contaCorrente.setConCorHistorico((1)+"º"+" IRRF PERIODO "+ dateFormat.format(primeiroDiaMes(this.locacao.getLocDataInicialContrato()))+ " A " + dateFormat.format(ultimoDiaMes(this.locacao.getLocDataInicialContrato())));
		contaCorrente.setPessoa(this.inquilino);
		contaCorrente.setConCorTipoCliente("LOCATARIO");
		contaCorrente.setConCorObservacao("");
		contaCorrenteRN.salvar(contaCorrente);
		}
			
		//Gerando Locatario - Aluguel
		contaCorrente=new ContaCorrente();
		contaCorrente.setConCorValor(arredondarCasasDecimais(this.imovel.getImoValorAluguel().doubleValue()-valorLocatario));
		contaCorrente.setConCorDataEmissao(new Date());
		contaCorrente.setConCorDataVencimento(venc);
		contaCorrente.setConCorHistorico("1º ALUGUEL PERIODO "+ dateFormat.format(this.locacao.getLocDataInicialContrato())+ " A " +dateFormat.format(ultimoDiaMes(this.locacao.getLocDataInicialContrato())));
		contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
		contaCorrente.setConCorTipo(true);
		contaCorrente.setConCorSituacao(false);
		contaCorrente.setPessoa(this.inquilino);
		contaCorrente.setConCorTipoCliente("LOCATARIO");
		//Grupo de Contas Temporario
		GrupoDeContas deContasAluguel=new GrupoDeContasRN().buscarPorGrupoDeContas("ALUGUEL");	
		contaCorrente.setGrupoDeContas(deContasAluguel);
		contaCorrente.setConCorObservacao("");
		contaCorrenteRN.salvar(contaCorrente);
		
		//Gerando Locador
		Double valorLocador=0.0;//proprietario
		for(Proprietario p: this.listaProprietarioImovel){
			contaCorrente=new ContaCorrente();
			c = Calendar.getInstance();
			c.setTime(this.locacao.getLocDataInicialContrato());//add a data do inicio da locação		
			Double v=this.imovel.getImoValorAluguel().doubleValue();
			valorLocador=(v-valorLocatario);

			contaCorrente=new ContaCorrente();
			contaCorrente.setConCorValor(arredondarCasasDecimais((arredondarCasasDecimais(valorLocador)*p.getProPorcentagem())/100));
			contaCorrente.setConCorDataEmissao(new Date());
			if(this.locacao.getLocModoPamento().equalsIgnoreCase("GARANTIDO POSTERIOR")){
			c.add(Calendar.DATE, 37);
			}else{
				c.add(Calendar.DATE, 7);	
			}
			contaCorrente.setConCorDataVencimento(c.getTime());
			c.setTime(this.locacao.getLocDataInicialContrato());
			c.add(Calendar.DATE, (this.imovel.getImoDiasDeCadastro()-1));
			c.add(Calendar.DATE, 1);
			contaCorrente.setConCorHistorico("1º ALUGUEL PERIODO "+ dateFormat.format(c.getTime())+ " A " + dateFormat.format(ultimoDiaMes(c.getTime())));
			contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
			contaCorrente.setConCorTipo(false);
			contaCorrente.setConCorSituacao(false);
			contaCorrente.setGrupoDeContas(deContasAluguel);		
			contaCorrente.setConCorTipoCliente("LOCADOR");
			contaCorrente.setConCorObservacao("");
			contaCorrente.setPessoa(p.getPessoa());
			contaCorrenteRN.salvar(contaCorrente);

			//Gerando taxa
			contaCorrente=new ContaCorrente();
			c = Calendar.getInstance();
			c.setTime(this.locacao.getLocDataInicialContrato());//add a data do inicio da locação

			contaCorrente=new ContaCorrente();
			contaCorrente.setConCorValor(arredondarCasasDecimais(((arredondarCasasDecimais((valorLocador*this.imovel.getImoTaxa())/100))*p.getProPorcentagem())/100));
			contaCorrente.setConCorDataEmissao(new Date());
			if(this.locacao.getLocModoPamento().equalsIgnoreCase("GARANTIDO POSTERIOR")){
				c.add(Calendar.DATE, 37);
			}else{
				c.add(Calendar.DATE, 7);
			}
			contaCorrente.setConCorDataVencimento(c.getTime());
			c.setTime(this.locacao.getLocDataInicialContrato());
			c.add(Calendar.DATE, (this.imovel.getImoDiasDeCadastro()-1));
			c.add(Calendar.DATE, 1);
			contaCorrente.setConCorHistorico("TAXA 1º ALUGUEL PERIODO "+ dateFormat.format(c.getTime())+ " A " + dateFormat.format(ultimoDiaMes(c.getTime())));
			contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
			contaCorrente.setConCorTipo(true);
			contaCorrente.setConCorSituacao(false);
			contaCorrente.setPessoa(p.getPessoa());
			//Grupo de Contas Temporario
			GrupoDeContas deContasTaxa=new GrupoDeContasRN().buscarPorGrupoDeContas("TAXA ADM");	
			contaCorrente.setGrupoDeContas(deContasTaxa);
			contaCorrente.setConCorTipoCliente("LOCADOR");
			contaCorrente.setConCorObservacao("");
			contaCorrenteRN.salvar(contaCorrente);
			
			//Gerar financeiro IRRF LOCADOR
			double valPro =calcularIRRFBaseTabela(((this.imovel.getImoValorAluguel().doubleValue()-valorLocatario)*p.getProPorcentagem())/100);			
			if((this.gerarFinanceiroIRRF)&&(valorLocatario>(1787.77)&&(valPro>0))){
				if(p.getPessoa().getPesTipo() == 'F'){
					contaCorrente=new ContaCorrente();
					contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
					contaCorrente.setConCorSituacao(false);
					contaCorrente.setConCorTipo(false);
					contaCorrente.setGrupoDeContas(deContasIRRF);
					Calendar temp=Calendar.getInstance();
					temp.setTime(this.locacao.getLocDataInicialContrato());
					temp.add(Calendar.DATE, 7);
					contaCorrente.setConCorDataVencimento(temp.getTime());
					contaCorrente.setConCorDataEmissao(new Date());
					contaCorrente.setConCorValor(arredondarCasasDecimais(valPro));
					contaCorrente.setConCorHistorico((1)+"º"+" IRRF PERIODO "+ dateFormat.format(primeiroDiaMes(c.getTime()))+ " A " + dateFormat.format(ultimoDiaMes(c.getTime())));
					contaCorrente.setPessoa(p.getPessoa());			
					contaCorrente.setConCorTipoCliente("LOCADOR");
					contaCorrente.setConCorObservacao("");
					contaCorrenteRN.salvar(contaCorrente);
				}
			}
		}
	}
	
	public void gerarFinanceiroLocacao(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		//Grupo de Contas Temporario
		GrupoDeContas deContasIRRF=new GrupoDeContasRN().buscarPorGrupoDeContas("IRRF");				

		//Grupo de Contas Temporario
		GrupoDeContas deContasAluguel=new GrupoDeContasRN().buscarPorGrupoDeContas("ALUGUEL");	

		//Grupo de Contas Temporario
		GrupoDeContas deContasTaxa=new GrupoDeContasRN().buscarPorGrupoDeContas("TAXA ADM");	

		Calendar dataVenIRRF = Calendar.getInstance();
		dataVenIRRF.setTime(this.locacao.getLocDataInicialContrato());
		dataVenIRRF.add(Calendar.MONTH, 1);

		ContaCorrenteRN contaCorrenteRN=new ContaCorrenteRN();
		Calendar c = Calendar.getInstance();
		c.setTime(this.locacao.getLocDataInicialContrato());//add a data do inicio da locação
		c.setTime(ultimoDiaMes(c.getTime()));//pegando o ultimo dia do mês
		c.add(Calendar.DATE, 1);//ultimo dia do mes + 1 dia, 
		c.setTime(primeiroDiaMes(c.getTime()));//tendo assim o 1º dia do segundo mes !
		
		if(this.locacao.getLocModoPamento().equalsIgnoreCase("GARANTIDO POSTERIOR")){
		c.setTime(ultimoDiaMes(c.getTime()));//pegando o ultimo dia do mês
		c.add(Calendar.DATE, 1);//ultimo dia do mes + 1 dia, 
		c.setTime(primeiroDiaMes(c.getTime()));//tendo assim o 1º dia do segundo mes !
		}
		
		//add LANÇAMENTO(MÊS/ANO) no contrato da locação
		Calendar lancamentoMes=c;
		lancamentoMes.add(Calendar.MONTH, 12);
		this.locacao.setLocLancamentoMesAno(lancamentoMes.getTime());
		new LocacaoRN().salvar(this.locacao);
		
		//atendendo o chamado 2001, gerar somente os 12 primeiros  meses aparti do inicio do contrato
		for(int i=0; i<(12-1);i++){
			ContaCorrente contaCorrente=new ContaCorrente();
			//gerado Financeiro do Locatario
			contaCorrente=new ContaCorrente();
			contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
			contaCorrente.setConCorSituacao(false);
			contaCorrente.setConCorTipo(true);
			contaCorrente.setGrupoDeContas(deContasAluguel);			
			
			Calendar venc =Calendar.getInstance();
			venc.setTime(c.getTime());
			
			//add o dia escolhi do locatario para vencimento
			
			//Se o locador não tiver dia fixo informado e o locatário também não, fazer o tratamento normal pela inicio da locação
			if((this.locacao.getLocDiaPagamentoLocador()==null) && (this.locacao.getLocDiaPagamentoLocatario()==null)){
				Calendar dia =Calendar.getInstance();
				dia.setTime(this.locacao.getLocDataInicialContrato());
				this.locacao.setLocDiaPagamentoLocatario(dia.get(Calendar.DAY_OF_MONTH));
				dia.add(Calendar.DATE, 7);
				this.locacao.setLocDiaPagamentoLocador(dia.get(Calendar.DAY_OF_MONTH));
			}
			if(this.locacao.getLocDiaPagamentoLocador()==null){
				int cont=this.locacao.getLocDiaPagamentoLocatario()+7;
				/*if(cont <=31){
					this.locacao.setLocDiaPagamentoLocador(cont);
					return;
				}
				cont=cont-31;*/
				this.locacao.setLocDiaPagamentoLocador(cont);
			}
			//se o locador escolher algum dia abaixo do dia 7 irá caí no mes anterior
			if(this.locacao.getLocDiaPagamentoLocatario()==null){
				this.locacao.setLocDiaPagamentoLocatario(this.locacao.getLocDiaPagamentoLocador()-7);	
			}
			if(this.locacao.getLocDiaPagamentoLocador()<7){
				venc.add(Calendar.MONTH, -1);
			}
			venc.add(Calendar.DATE,(this.locacao.getLocDiaPagamentoLocatario()-1));
			
			contaCorrente.setConCorDataVencimento(venc.getTime());
			contaCorrente.setConCorDataEmissao(new Date());
			contaCorrente.setConCorValor(arredondarCasasDecimais(this.imovel.getImoValorAluguel().doubleValue()));
			contaCorrente.setConCorHistorico((i+2)+"º"+" ALUGUEL PERIODO "+ dateFormat.format(primeiroDiaMes(c.getTime()))+ " A " + dateFormat.format(ultimoDiaMes(c.getTime())));
			contaCorrente.setPessoa(this.inquilino);
			contaCorrente.setConCorTipoCliente("LOCATARIO");
			contaCorrente.setConCorObservacao("");
			contaCorrenteRN.salvar(contaCorrente);
			
			//Gerar financeiro IRRF Locatario
			double valIRRFTotal=calcularIRRF();
			//Valores inferiores a R$ 10,00 não será deduzido o Imposto de Renda, ficando isento locatário/locador.
			if(valIRRFTotal<10){
				this.gerarFinanceiroIRRF=false;				
			}
			if(this.gerarFinanceiroIRRF){
			contaCorrente=new ContaCorrente();
			contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
			contaCorrente.setConCorSituacao(false);
			contaCorrente.setConCorTipo(true);
			contaCorrente.setGrupoDeContas(deContasIRRF);
			contaCorrente.setConCorDataVencimento(dataVenIRRF.getTime());
			contaCorrente.setConCorDataEmissao(new Date());
			contaCorrente.setConCorValor(calcularIRRF());
			contaCorrente.setConCorHistorico((i+2)+"º"+" mês IRRF PERIODO "+ dateFormat.format(primeiroDiaMes(c.getTime()))+ " A " + dateFormat.format(ultimoDiaMes(c.getTime())));
			contaCorrente.setPessoa(this.inquilino);
			contaCorrente.setConCorTipoCliente("LOCATARIO");
			contaCorrente.setConCorObservacao("");
			contaCorrenteRN.salvar(contaCorrente);
			}
			
			for(Proprietario p: this.listaProprietarioImovel){
				contaCorrente=new ContaCorrente();
				Calendar vencimento =Calendar.getInstance();
				vencimento.setTime(c.getTime());
				if((p.getProDiaPagamentoFixo()==null)||(p.getProDiaPagamentoFixo()==0)){
					vencimento.add(Calendar.DATE, 7);
				}else{
					if(p.getProDiaPagamentoFixo()==this.locacao.getLocDiaPagamentoLocador()){
						vencimento.add(Calendar.DATE,(p.getProDiaPagamentoFixo()-1));
					}else{
						vencimento.add(Calendar.DATE,(this.locacao.getLocDiaPagamentoLocador()-1));
					}
				}
				contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
				contaCorrente.setConCorSituacao(false);
				contaCorrente.setConCorTipo(false);
				contaCorrente.setGrupoDeContas(deContasAluguel);	
				contaCorrente.setConCorDataVencimento(vencimento.getTime());
				contaCorrente.setConCorDataEmissao(new Date());
				contaCorrente.setConCorValor(arredondarCasasDecimais((this.imovel.getImoValorAluguel().doubleValue()*p.getProPorcentagem())/100));
				contaCorrente.setConCorHistorico((i+2)+"º"+" ALUGUEL PERIODO "+ dateFormat.format(primeiroDiaMes(c.getTime()))+ " A " + dateFormat.format(ultimoDiaMes(c.getTime())));
				contaCorrente.setPessoa(p.getPessoa());
				contaCorrente.setConCorTipoCliente("LOCADOR");
				contaCorrente.setConCorObservacao("");
				contaCorrenteRN.salvar(contaCorrente);

			//Gerando taxa
			
			contaCorrente=new ContaCorrente();
			contaCorrente.setConCorValor(arredondarCasasDecimais((((this.imovel.getImoValorAluguel().doubleValue()*this.imovel.getImoTaxa())/100)*p.getProPorcentagem())/100));
			contaCorrente.setConCorDataEmissao(new Date());
			//contaCorrente.setConCorDataVencimento(c.getTime());
			contaCorrente.setConCorDataVencimento(vencimento.getTime());
			contaCorrente.setConCorHistorico("TAXA "+(i+2)+"º"+" ALUGUEL  PERIODO "+dateFormat.format(primeiroDiaMes(c.getTime()))+ " A " + dateFormat.format(ultimoDiaMes(c.getTime())));
			contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
			contaCorrente.setConCorTipo(true);
			contaCorrente.setConCorSituacao(false);
			contaCorrente.setPessoa(p.getPessoa());			
			contaCorrente.setConCorTipoCliente("LOCADOR");
			contaCorrente.setConCorObservacao("");
			contaCorrente.setGrupoDeContas(deContasTaxa);
			contaCorrenteRN.salvar(contaCorrente);
			
			//Gerar financeiro IRRF LOCADOR
			if(this.gerarFinanceiroIRRF){
				if(p.getPessoa().getPesTipo() == 'F'){
					contaCorrente=new ContaCorrente();
					contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
					contaCorrente.setConCorSituacao(false);
					contaCorrente.setConCorTipo(false);
					contaCorrente.setGrupoDeContas(deContasIRRF);
					Calendar temp=dataVenIRRF;
					temp.add(Calendar.DATE, 7);
					contaCorrente.setConCorDataVencimento(temp.getTime());
					contaCorrente.setConCorDataEmissao(new Date());
					contaCorrente.setConCorValor(arredondarCasasDecimais(calcularIRRFBaseTabela((this.imovel.getImoValorAluguel().doubleValue()*p.getProPorcentagem())/100)));
					contaCorrente.setConCorHistorico((i+2)+"º"+" mês IRRF PERIODO "+ dateFormat.format(primeiroDiaMes(c.getTime()))+ " A " + dateFormat.format(ultimoDiaMes(c.getTime())));
					contaCorrente.setPessoa(p.getPessoa());			
					contaCorrente.setConCorTipoCliente("LOCADOR");
					contaCorrente.setConCorObservacao("");
					contaCorrenteRN.salvar(contaCorrente);
				}
			}
			}
			
			c.setTime(ultimoDiaMes(c.getTime()));
			c.add(Calendar.DATE, 1);
			c.setTime(primeiroDiaMes(c.getTime()));
			
			dataVenIRRF.add(Calendar.MONTH, 1);
			
		}
	}
	
	public Date primeiroDiaMes(Date date){
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.set(Calendar.HOUR_OF_DAY, 0);
		 calendar.set(Calendar.MINUTE, 0);
		 calendar.set(Calendar.SECOND, 0);
		 calendar.set(Calendar.MILLISECOND, 0);
		 return calendar.getTime();
		}
	
	public Date ultimoDiaMes(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	
	public double arredondarCasasDecimais(double value) {
	    long factor = (long) Math.pow(10, 2);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public void gerarFinanceiroSeguroIncendio(){
		//Grupo de Contas Temporario
		GrupoDeContas deContasSeguroIncendio=new GrupoDeContasRN().buscarPorGrupoDeContas("SEGURO INCEDIO");	
		
		ContaCorrenteRN contaCorrenteRN=new ContaCorrenteRN();
		Calendar c = Calendar.getInstance();
		c.setTime(this.locacao.getLocDataInicialContrato());//add a data do inicio da locação
		for(int i=0; i<this.seguroIncendio.getSegIncPlano();i++){
		ContaCorrente contaCorrente=new ContaCorrente();
		contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
		contaCorrente.setConCorSituacao(false);
		contaCorrente.setConCorTipo(true);
		contaCorrente.setGrupoDeContas(deContasSeguroIncendio);
		contaCorrente.setConCorDataEmissao(new Date());
		contaCorrente.setConCorValor(this.seguroIncendio.getSegIncValorParcela());
		contaCorrente.setPessoa(this.inquilino);
		contaCorrente.setConCorTipoCliente("LOCATÁRIO");
	/*	contaCorrente.setConCorDataCopetenciaFinal(this.locacao.getLocDataInicialContrato());
		contaCorrente.setConCorDataCopetenciaInicial(c.getTime());*/
		contaCorrente.setConCorDataVencimento(c.getTime());
		c.add(Calendar.DATE, 30);//add 30 dias as datas de vencimentos
		if(this.seguroIncendio.getSegIncPlano()==1){
			contaCorrente.setConCorHistorico("SEGURO INCÊNDIO");
		}else{
			contaCorrente.setConCorHistorico("SEGURO INCÊNDIO "+(i+1)+"/"+this.seguroIncendio.getSegIncPlano());
		}
		contaCorrente.setConCorObservacao("");
		contaCorrenteRN.salvar(contaCorrente);
		}
	}
	
	public void gerarFinanceiroSeguroFianca(){	
		//Grupo de Contas Temporario
		GrupoDeContas deContasSeguroFianca=new GrupoDeContasRN().buscarPorGrupoDeContas("SEGURO FIANCA");	
		
		ContaCorrenteRN contaCorrenteRN=new ContaCorrenteRN();
		Calendar c = Calendar.getInstance();
		c.setTime(this.locacao.getLocDataInicialContrato());//add a data do inicio da locação
		for(int i=0; i<this.seguroFianca.getSegFiaPlano();i++){
		ContaCorrente contaCorrente=new ContaCorrente();
		contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
		contaCorrente.setConCorSituacao(false);
		contaCorrente.setConCorTipo(true);
		contaCorrente.setGrupoDeContas(deContasSeguroFianca);
		contaCorrente.setConCorDataEmissao(new Date());
		contaCorrente.setConCorValor(this.seguroFianca.getSegFiaValorParcela());
		contaCorrente.setPessoa(this.inquilino);
		contaCorrente.setConCorTipoCliente("LOCATÁRIO");
	/*	contaCorrente.setConCorDataCopetenciaFinal(this.locacao.getLocDataInicialContrato());
		contaCorrente.setConCorDataCopetenciaInicial(c.getTime());*/
		contaCorrente.setConCorDataVencimento(c.getTime());
		c.add(Calendar.DATE, 30);//add 30 dias as datas de vencimentos
		if(this.seguroFianca.getSegFiaPlano()==1){
			contaCorrente.setConCorHistorico("SEGURO FIANÇA");
		}else{
			contaCorrente.setConCorHistorico("SEGURO FIANÇA "+(i+1)+"/"+this.seguroFianca.getSegFiaPlano());
		}
		contaCorrente.setConCorObservacao("");
		contaCorrenteRN.salvar(contaCorrente);
		}
	}

	private void carregarFiadores() {
		LocacaoRN locacaoRN=new LocacaoRN();
		List<Fiador> listaFiador=locacaoRN.carregarFiadores(this.locacao);
		JSONArray array = new JSONArray();

		for (Fiador p : listaFiador) {

			try {
				JSONObject obj = new JSONObject();
				
				obj.put("id", p.getPessoa().getPesId());
				obj.put("nome", p.getPessoa().getPesNome());
				obj.put("cpfcnpj", p.getPessoa().getPesCpfCnpj());
				obj.put("fiadorId", p.getFiaId());

				array.put(obj);
			} catch (JSONException e) { }

		}

		if (array.length() > 0)
			fiadorJson = array.toString();
		
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Fiador> getListaFiador() {
		if(this.listaFiador==null){
			this.listaFiador=new ArrayList<Fiador>();
			this.listaFiador= new FiadorRN().listar();
		}
		return listaFiador;
	}

	public List<Pessoa> getListaInquilino() {
		if(this.listaInquilino==null){
			this.listaInquilino=new ArrayList<Pessoa>();
			List<Pessoa> list= new PessoaRN().carregarListaPessoasNaoPreCadastro();
			for(Pessoa p:list){
				if(p.getPesInquilino()==true){
					this.listaInquilino.add(p);
				}
			}
		}
		return this.listaInquilino;
	}
	
	
	
	public String quemIndicou(Integer i){
		Funcionario funcionario=new FuncionarioRN().carregar(i);
		if(funcionario != null){
			return funcionario.getPessoa().getPesNome();
		}
		return "";
	}

	public List<Imovel> getListaImoveis() {
		if(this.listaImoveis==null){
			this.listaImoveis=new ArrayList<Imovel>();
			List<Imovel> lista=new ImovelRN().listar();
			for(Imovel imovel: lista){
				if(imovel.getImoLocacao()){
					this.listaImoveis.add(imovel);
				}
			}
		}
		return listaImoveis;
	}
	
	public void calcularDestinacao(){
		if(this.locacao.getLocDataInicialContrato()==null){
			this.locacao.setLocMesesDeContrato(null);
			this.contextoBean.mostrarErro("Escolha a DATA INÍCIO LOCAÇÃO");
			return;
			
		}
		if(this.locacao.getLocCategoria().equals("COMERCIAL")){
			if((this.locacao.getLocMesesDeContrato()<12) ||(this.locacao.getLocMesesDeContrato()>60)){
				this.locacao.setLocMesesDeContrato(null);
				this.contextoBean.mostrarErro("DESTINAÇÃO selecionado 'COMERCIAL' os meses de contrato deve está entre 12-60 meses");
				return;
			}			
			Date dt = this.locacao.getLocDataInicialContrato();
			Calendar calendar = Calendar.getInstance();
			if(dt!=null){
				calendar.setTime(dt); }else{
					calendar.setTime(new Date());
				}
			calendar.add(Calendar.MONTH, this.locacao.getLocMesesDeContrato());
			dt=calendar.getTime(); 
			locacao.setLocDataTerminioContrato(dt);
		}else if(this.locacao.getLocCategoria().equals("RESIDENCIAL")){
			if((this.locacao.getLocMesesDeContrato()<30) ||(this.locacao.getLocMesesDeContrato()>60)){
				this.locacao.setLocMesesDeContrato(null);
				this.contextoBean.mostrarErro("DESTINAÇÃO selecionado 'RESIDENCIAL' os meses de contrato deve está entre 30-60 meses");
				return;
			}
			Date dt = this.locacao.getLocDataInicialContrato();
			Calendar calendar = Calendar.getInstance();    
			if(dt!=null){
				calendar.setTime(dt); }else{
					calendar.setTime(new Date());
				}
			calendar.add(Calendar.MONTH, this.locacao.getLocMesesDeContrato());
			dt=calendar.getTime(); 
			locacao.setLocDataTerminioContrato(dt);
		}
	}
	
	public void modeloContrato(){
		//regra de negocio ainda sendo analisada, OBS: bruno
	}
	
	public void gravarInformacaoAdiconal(){
		this.informacaoAdicional.setLocacao(this.locacao);
		Funcionario funcionario= this.contextoBean.getFuncionarioLogado();
		this.informacaoAdicional.setInfAdiFuncionario(funcionario.getPesId());
		this.informacaoAdicional.setInfAdiSetor(funcionario.getFunCargo());
		this.informacaoAdicional.setInfAdiData(new Date());
		InformacaoAdicionalRN informacaoAdicionalRN=new InformacaoAdicionalRN();
		informacaoAdicionalRN.salvar(this.informacaoAdicional);
		this.informacaoAdicional=new InformacaoAdicional();
		this.listaInformacaoAdicinal=new ArrayList<InformacaoAdicional>();
		this.listaInformacaoAdicinal=informacaoAdicionalRN.carregarPorLocacao(this.locacao);
		this.contextoBean.mostrarAviso("Informação adicional salva com sucesso !");
	}
	
	public String funcionario(int idFun){
		Funcionario funcionario=new FuncionarioRN().carregar(idFun);
		if(funcionario!=null){
			return funcionario.getPessoa().getPesNome();
		}
		return "Funcionario não encontrato";
	}
	
	public void calcularDiaPagamentoLocador(){
		if((this.locacao.getLocDiaPagamentoLocatario()>30)||(this.locacao.getLocDiaPagamentoLocatario()<=0)){
			this.locacao.setLocDiaPagamentoLocatario(null);
			this.locacao.setLocDiaPagamentoLocador(null);
			this.contextoBean.mostrarErro("DIA RECEBIMENTO LOCATÁRIO entre 1-30");
			return;
		}
		int cont=this.locacao.getLocDiaPagamentoLocatario()+7;
		if(cont <=31){
			this.locacao.setLocDiaPagamentoLocador(cont);
			return;
		}
		cont=cont-31;
		this.locacao.setLocDiaPagamentoLocador(cont);
	}
	
	/** Regra de negocio do Ramo da Destinação. Chamado: 1405 */
	public void regraRamoDestinacaoLocacao(){
		
		if(this.destinacaoLocacao==null){
			this.contextoBean.mostrarErro("selecione o RAMO DA DESTINAÇÃO da locação");
			return;
		}
		
		DestinacaoLocacao destLoc=new DestinacaoLocacaoRN().carregar(this.destinacaoLocacao.getDesLocId());
		
		if(destLoc.getDesLocSeguro()==null){
			this.preenchimentoSeguroIncendio=true;
			this.preenchimentoSeguroIncendioDatas=true;
			this.bloquearPreenchimentoSeguroIncendio=false;
			return;
		}
		
		switch (destLoc.getDesLocSeguro()) {
		case "Não Possui Seguro":
			this.preenchimentoSeguroIncendio=true;
			this.preenchimentoSeguroIncendioDatas=true;
			this.bloquearPreenchimentoSeguroIncendio=true;
			break;
		case "Passível de Seguro":
			this.preenchimentoSeguroIncendio=false;
			this.preenchimentoSeguroIncendioDatas=false;
			this.bloquearPreenchimentoSeguroIncendio=true;
			RequestContext.getCurrentInstance().execute("PF('confirmacao').show()");
			break;
		default:
			this.preenchimentoSeguroIncendio=true;
			this.preenchimentoSeguroIncendioDatas=true;
			this.bloquearPreenchimentoSeguroIncendio=true;
			break;
		}
	}

	public void confirmacaoDialogSeguroSim(){
		this.preenchimentoSeguroIncendio=true;
		this.preenchimentoSeguroIncendioDatas=true;
		this.bloquearPreenchimentoSeguroIncendio=false;
	}

	public void botaoSeguroIncendioParticular(){
		
		DestinacaoLocacao destLoc=new DestinacaoLocacaoRN().carregar(this.destinacaoLocacao.getDesLocId());
		if(destLoc==null){
			this.preenchimentoSeguroIncendio=true;
			this.preenchimentoSeguroIncendioDatas=true;
			this.bloquearPreenchimentoSeguroIncendio=false;
			return;
		}
		
		if(destLoc.getDesLocSeguro()==null){
			this.preenchimentoSeguroIncendio=true;
			this.preenchimentoSeguroIncendioDatas=true;
			this.bloquearPreenchimentoSeguroIncendio=false;
			return;
		}
		
		switch (destLoc.getDesLocSeguro()) {
		case "Não Possui Seguro":
			this.preenchimentoSeguroIncendio=false;
			this.preenchimentoSeguroIncendioDatas=true;
			this.bloquearPreenchimentoSeguroIncendio=false;
			//RequestContext.getCurrentInstance().execute("PF('confirmacao').show()");
			break;
		case "Passível de Seguro":
			this.preenchimentoSeguroIncendio=false;
			this.preenchimentoSeguroIncendioDatas=false;
			this.bloquearPreenchimentoSeguroIncendio=true;
			if(this.seguroIncendio.getSegIncSeguroParticular()){
				this.preenchimentoSeguroIncendio=true;
				this.preenchimentoSeguroIncendioDatas=true;
				this.bloquearPreenchimentoSeguroIncendio=false;
			}else{
				this.preenchimentoSeguroIncendio=false;
				this.preenchimentoSeguroIncendioDatas=false;
				this.bloquearPreenchimentoSeguroIncendio=true;
			}
			break;
		default:
			this.preenchimentoSeguroIncendio=true;
			this.preenchimentoSeguroIncendioDatas=true;
			this.bloquearPreenchimentoSeguroIncendio=false;
			break;
		}
	}
	public void botaoIPTUIsento(){
		this.iptu=new Iptu();
		if(!this.bloquearPreenchimentoIptu){
			Calendar anoTual = Calendar.getInstance();			
			this.iptu.setIptAno(anoTual.get(Calendar.YEAR));
		}
	}
	//função que calcular o valor do seguro incendio de cada imovel carregado no cadastro de contratos
	public void valTotalSeguroIncendio(){
		if(this.seguroIncendio.getSegIncValorVenalTotal()==null){
			this.contextoBean.mostrarErro("Selecione um imóvel para efetuar o calculo do seguro incêndio");
			return;
		}
		
		if(this.seguroIncendio.getSegIncValorVenalTotal()>2000000.01){
			this.contextoBean.mostrarErro("Imóveis com Valor Venal acima de R$2.000.000,01 obedecer à regra: campo Seguro Especial ativo");
			this.seguroIncendio.setSegIncSeguroEspecial(true);
			return;
		}
		
		if(this.seguroIncendio.getSegIncPlano()==null){
			this.contextoBean.mostrarErro("Preencha a Quantidade de Parcela (Plano) para efetuar o calculo do seguro incêndio");
			return;
		}
		
		if(this.locacao.getLocCategoria().equals("RESIDENCIAL")){
			SeguroIncendioImovelResidencialRN seguroIncendioImovelResidencialRN=new SeguroIncendioImovelResidencialRN();			
			double val=seguroIncendioImovelResidencialRN.bucarValorSeguroPorValorVenal(this.seguroIncendio.getSegIncValorVenalTotal());
			if(val==0.0){
				this.contextoBean.mostrarErro("Valor Venal não encontrado na Tabela seguro incêndio de imóveis residencial");
				}
			this.seguroIncendio.setSegIncValorTotal(val);
			this.seguroIncendio.setSegIncValorParcela(val/this.seguroIncendio.getSegIncPlano());
			return;
			}
		
		if(this.locacao.getLocCategoria().equals("COMERCIAL")){
		SeguroIncendioImovelComercialRN seguroIncendioImovelComercialRN=new SeguroIncendioImovelComercialRN();
		double val=seguroIncendioImovelComercialRN.bucarValorSeguroPorValorVenal(this.seguroIncendio.getSegIncValorVenalTotal());
		if(val==0.0){
			this.contextoBean.mostrarErro("Valor Venal não encontrado na Tabela seguro incêndio de imóveis comerciais");
			}
		this.seguroIncendio.setSegIncValorTotal(val);
		this.seguroIncendio.setSegIncValorParcela(val/this.seguroIncendio.getSegIncPlano());
		return;
		}
	}
	
	public static int dataDiff(java.util.Date dataLow, java.util.Date dataHigh){  
		  
	     GregorianCalendar startTime = new GregorianCalendar();  
	     GregorianCalendar endTime = new GregorianCalendar();  
	       
	     GregorianCalendar curTime = new GregorianCalendar();  
	     GregorianCalendar baseTime = new GregorianCalendar();  
	  
	     startTime.setTime(dataLow);  
	     endTime.setTime(dataHigh);  
	       
	     int dif_multiplier = 1;  
	       
	     // Verifica a ordem de inicio das datas  
	     if( dataLow.compareTo( dataHigh ) < 0 ){  
	         baseTime.setTime(dataHigh);  
	         curTime.setTime(dataLow);  
	         dif_multiplier = 1;  
	     }else{  
	         baseTime.setTime(dataLow);  
	         curTime.setTime(dataHigh);  
	         dif_multiplier = -1;  
	     }  
	       
	     int result_years = 0;  
	     int result_months = 0;  
	     int result_days = 0;  
	  
	     // Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import acumulando  
	     // no total de dias. Ja leva em consideracao ano bissesto  
	     while( curTime.get(GregorianCalendar.YEAR) < baseTime.get(GregorianCalendar.YEAR) ||  
	            curTime.get(GregorianCalendar.MONTH) < baseTime.get(GregorianCalendar.MONTH)  )  
	     {  
	           
	         int max_day = curTime.getActualMaximum( GregorianCalendar.DAY_OF_MONTH );  
	         result_months += max_day;  
	         curTime.add(GregorianCalendar.MONTH, 1);  
	           
	     }  
	       
	     // Marca que é um saldo negativo ou positivo  
	     result_months = result_months*dif_multiplier;  
	       
	       
	     // Retirna a diferenca de dias do total dos meses  
	     result_days += (endTime.get(GregorianCalendar.DAY_OF_MONTH) - startTime.get(GregorianCalendar.DAY_OF_MONTH));  
	       
	     return result_years+result_months+result_days;  
	}  
	
	public void adicionarIPTU(){
		
		if(this.iptu.getIptAno()==null
				||this.iptu.getIptAno()==0
				||this.iptu.getIptParcelas()==null
						||this.iptu.getIptParcelas()==0
								||this.iptu.getIptValor()==null
										||this.iptu.getIptValor()==0
												||this.iptu.getIptCodigoReduzido()==null
														||this.iptu.getIptCodigoReduzido()==""
				){
			this.contextoBean.mostrarErro("Todos os campo para calculo do IPTU são obrigatorios.");
			return;
		}
		Calendar anoTual = Calendar.getInstance();
		if(this.iptu.getIptAno()<anoTual.get(Calendar.YEAR)){
			this.contextoBean.mostrarErro("Ano do IPTU tem que ser acima de "+anoTual.get(Calendar.YEAR));
			return;
		}
		
		if(this.locacao.getLocDataInicialContrato()==null){
			this.contextoBean.mostrarErro("Selecione data inicial do contrato para calculo do IPTU.");
			return;
		}		
		
		// Data inicial  
		Calendar dataInicio = Calendar.getInstance();
		dataInicio.setTime(this.locacao.getLocDataInicialContrato());
		int mesesRestantes=12-(dataInicio.get(Calendar.MONTH));
		if(this.iptu.getIptParcelas()==null){
			this.contextoBean.mostrarErro("Numero de parcelas obrigatorio para calculo do IPTU.");
			return;
		}else if(this.iptu.getIptParcelas() > mesesRestantes){
			this.contextoBean.mostrarErro("Numero de parcelas maior que o numero de meses restantes("+mesesRestantes+") para fim de ano.");
			return;
		}
		
		this.iptu.setLocacao(this.locacao);
		IptuRN iptuRN=new IptuRN();
		if(this.alteracao){
		iptuRN.salvar(this.iptu);
		this.iptu=new Iptu();
		this.listaIptu=iptuRN.carregarPorLocacao(this.locacao);
		}else{

			this.listaIptu.add(this.iptu);
			this.iptu=new Iptu();
		}
	}
	
	public void removerIPTU(){
		if(this.alteracao){
		new IptuRN().excluir(this.iptu);
		this.iptu=new Iptu();
		this.listaIptu= new IptuRN().carregarPorLocacao(this.locacao);
		}else{
			this.listaIptu.remove(this.iptu);
			this.iptu=new Iptu();
		}
	}
	public void gerarFinceiroIPTU(){
		//Grupo de Contas Temporario
		GrupoDeContas deContasIPTU=new GrupoDeContasRN().buscarPorGrupoDeContas("IPTU");	
		
		ContaCorrenteRN contaCorrenteRN=new ContaCorrenteRN();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				
		if(this.alteracao){
			
		}		
		
		Calendar venc =Calendar.getInstance();
		venc.setTime(this.locacao.getLocDataInicialContrato());
		
		Calendar perido =Calendar.getInstance();
		perido.setTime(this.locacao.getLocDataInicialContrato());
		
		int diasRestantesAno=(365-venc.get(Calendar.DAY_OF_YEAR));
		
		if(this.locacao.getLocModoPamento().equalsIgnoreCase("GARANTIDO POSTERIOR")){
			venc.setTime(ultimoDiaMes(venc.getTime()));//pegando o ultimo dia do mês
			venc.add(Calendar.DATE, 1);//ultimo dia do mes + 1 dia, 
			venc.setTime(primeiroDiaMes(venc.getTime()));//tendo assim o 1º dia do segundo mes !
			}
		
		for(int i=0; i<this.iptu.getIptParcelas();i++){
			ContaCorrente contaCorrente=new ContaCorrente();
			//gerado Financeiro do IPTU
			contaCorrente=new ContaCorrente();
			contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
			contaCorrente.setConCorSituacao(false);
			contaCorrente.setConCorTipo(true);
			contaCorrente.setGrupoDeContas(deContasIPTU);
			contaCorrente.setConCorDataVencimento(venc.getTime());
			contaCorrente.setConCorDataEmissao(new Date());
			double valIptu=arredondarCasasDecimais(((this.iptu.getIptValor()/365)*diasRestantesAno)/this.iptu.getIptParcelas());
			contaCorrente.setConCorValor(valIptu);
			contaCorrente.setConCorHistorico((i+1)+"º"+" IPTU PERIODO "+ dateFormat.format(primeiroDiaMes(perido.getTime()))+ " A " + dateFormat.format(ultimoDiaMes(perido.getTime())));
			contaCorrente.setPessoa(this.inquilino);
			contaCorrente.setConCorTipoCliente("LOCATARIO");
			contaCorrente.setConCorObservacao("");
			contaCorrenteRN.salvar(contaCorrente);

			//Gerando taxa
			
			for(Proprietario p: this.listaProprietarioImovel){			
			contaCorrente=new ContaCorrente();
			contaCorrente.setConCorValor(arredondarCasasDecimais((((valIptu*this.imovel.getImoTaxa())/100)*p.getProPorcentagem())/100));
			contaCorrente.setConCorDataEmissao(new Date());
			//contaCorrente.setConCorDataVencimento(c.getTime());
			contaCorrente.setConCorDataVencimento(venc.getTime());
			contaCorrente.setConCorHistorico("TAXA "+(i+2)+"º"+" IPTU  PERIODO "+dateFormat.format(primeiroDiaMes(perido.getTime()))+ " A " + dateFormat.format(ultimoDiaMes(perido.getTime())));
			contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
			contaCorrente.setConCorTipo(true);
			contaCorrente.setConCorSituacao(false);
			contaCorrente.setPessoa(p.getPessoa());			
			contaCorrente.setConCorTipoCliente("LOCADOR");
			contaCorrente.setConCorObservacao("");
			contaCorrente.setGrupoDeContas(deContasIPTU);
			contaCorrenteRN.salvar(contaCorrente);
			}
			
			perido.add( Calendar.MONTH, 1 ); //somar um mês apos da data inicio locação
			perido.set(Calendar.DAY_OF_MONTH, 1);
		}

	}
	
	public Integer calculandoDiaRecebimentoLocatario(Integer id){
		Integer dia=31;
		boolean diaNaoEscolhido=false;
		Imovel imovel=new Imovel();
		imovel.setImoId(id);
		List<Proprietario> lista=new ProprietarioRN().carregarProprietarios(imovel);
		for(Proprietario p: lista){
			if((p.getProDiaPagamentoFixo()!=null)||(p.getProDiaPagamentoFixo()!=0)){
				if(p.getProDiaPagamentoFixo()<dia)
					dia=p.getProDiaPagamentoFixo();
			}else{
				diaNaoEscolhido=true;
			}
		}
		if(dia==0){
			diaNaoEscolhido=true;
		}else if((dia-7)<=0){
			for(int i=7;i>=1;i--){
				dia--;
				if(dia==0)
					dia=30;
			}
		}else{
			dia=dia-7;
		}

		if(diaNaoEscolhido)
			dia=null;
		
	//	this.locacao.setLocDiaPagamentoLocatario(dia);
		return dia;
	}
	
	public Integer calculandoDiaRecebimentoLocador(Integer id){
		Integer dia=31;
		boolean diaNaoEscolhido=false;
		Imovel imovel=new Imovel();
		imovel.setImoId(id);
		List<Proprietario> lista=new ProprietarioRN().carregarProprietarios(imovel);
		for(Proprietario p: lista){
			if((p.getProDiaPagamentoFixo()!=null)||(p.getProDiaPagamentoFixo()!=0)){
				if(p.getProDiaPagamentoFixo()<dia)
					dia=p.getProDiaPagamentoFixo();
			}else{
				diaNaoEscolhido=true;
			}
		}
		
		if(dia==0)
			dia=null;
		
		if(diaNaoEscolhido)
			dia=null;
		//this.locacao.setLocDiaPagamentoLocador(dia);
		return dia;
	}
	
	public double calcularIRRF(){
		double valor = 0;
		for(Proprietario p: this.listaProprietarioImovel){
			if(p.getPessoa().getPesTipo() == 'F'){
				valor+=calcularIRRFBaseTabela(((this.imovel.getImoValorAluguel().doubleValue()*p.getProPorcentagem())/100));
			}
		}
		return arredondarCasasDecimais(valor);
	}
	public double calcularIRRFPrimeiroAluguel(double aluguel){
		double valor = 0;
		for(Proprietario p: this.listaProprietarioImovel){
			if(p.getPessoa().getPesTipo() == 'F'){
				valor+=calcularIRRFBaseTabela(((aluguel*p.getProPorcentagem())/100));
			}
		}
		return arredondarCasasDecimais(valor);
	}
	
	public double calcularIRRFBaseTabela(double valor){
		Calendar data= Calendar.getInstance();
		data.setTime(this.locacao.getLocDataInicialContrato());
		int anoContrato=data.get(Calendar.YEAR);
		double cont=0;
		List<FaixaImpostoDeRenda> lista=new FaixaImpostoDeRendaRN().listar();
		for(FaixaImpostoDeRenda i: lista){
			if(i.getImpostoDeRenda().getImpRenAno()==anoContrato){
				if((valor>=i.getFaiImpRenLimiteInferior())&&(valor<=i.getFaiImpRenLimiteSuperior())){
					cont=arredondarCasasDecimais(((valor*i.getFaiImpRenAliquota())/100)-i.getFaiImpRenDeducao());
				}else if((valor>=i.getFaiImpRenLimiteInferior())&&(i.getFaiImpRenLimiteSuperior()==0)){
					cont=arredondarCasasDecimais(((valor*i.getFaiImpRenAliquota())/100)-i.getFaiImpRenDeducao());
				}
			}
		}
		return cont;
	}
	public String carregarProprietarios(String id){
		Integer idd=Integer.valueOf(id);
		String nomes=new String();
		if(idd>0){
			Imovel imovel=new Imovel();
			imovel.setImoId(idd);
			List<Proprietario> lista=new ProprietarioRN().carregarProprietarios(imovel);
			for(Proprietario p: lista){
				nomes+=p.getPessoa().getPesNome()+". Porcentagem: "+p.getProPorcentagem()+"%"+". ";
			}
			return nomes;
		}else{
			return "Não encontrado proprietário do imóvel";
		}
	}
	
	public void validarParcelasIPTU(){
		Calendar calendar=Calendar.getInstance();
		if(this.locacao.getLocDataInicialContrato()==null){
			this.iptu.setIptParcelas(null);
			this.contextoBean.mostrarErro("DATA INÍCIO LOCAÇÃO Obrigatório");
			return;
		}
		calendar.setTime(this.locacao.getLocDataInicialContrato());
		if(iptu.getIptParcelas()>=(12-(calendar.get(Calendar.MONTH)))){
			RequestContext.getCurrentInstance().execute("PF('confirmIPTU').show()");
			return;
		}
	}
	
	public void limparMesesContrato(){
		if(this.locacao.getLocCategoria().equalsIgnoreCase("RESIDENCIAL"))
			this.locacao.setLocMesesDeContrato(30);
		
		if(this.locacao.getLocCategoria().equalsIgnoreCase("COMERCIAL"))
			this.locacao.setLocMesesDeContrato(12);
		
		calcularDestinacao();
	}
	
	public void alterarCategoria(){
		if(this.locacao.getLocCategoria().equalsIgnoreCase("RESIDENCIAL"))
			this.locacao.setLocMesesDeContrato(30);
		
		if(this.locacao.getLocCategoria().equalsIgnoreCase("COMERCIAL"))
			this.locacao.setLocMesesDeContrato(12);
		
		calcularDestinacao();
	}

}