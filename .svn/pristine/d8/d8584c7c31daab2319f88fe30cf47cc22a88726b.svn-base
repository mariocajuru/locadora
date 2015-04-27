package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.ContaCorrente;
import br.com.locadora.modelo.ContasAPagar;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.GrupoDeContas;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.LancamentoContasAPagar;
import br.com.locadora.modelo.Locacao;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Proprietario;
import br.com.locadora.rn.ContaCorrenteRN;
import br.com.locadora.rn.ContasAPagarRN;
import br.com.locadora.rn.FuncionarioRN;
import br.com.locadora.rn.LancamentoContasAPagarRN;
import br.com.locadora.rn.LocacaoRN;
import br.com.locadora.rn.ProprietarioRN;
import br.com.locadora.web.util.ContextoUtil;


@ManagedBean(name = "financeiroBean")
@ViewScoped
public class FinanceiroBean implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = 7635729447536563448L;
	@Getter @Setter private ContaCorrente contaCorrente=new  ContaCorrente();
	@Getter @Setter private GrupoDeContas grupoDeContas=new GrupoDeContas();
	@Getter @Setter private Locacao locacao=new Locacao();
	@Getter @Setter private String operacao=new String();
	@Getter @Setter private Integer parcelas;

	@Getter @Setter private List<Locacao> listaContratos;
	@Getter @Setter private List<ProprietariosTemp> listaProprietarios;
	@Getter @Setter private double subtrairValorLocatario;
	@Getter @Setter private double valorParcela;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public FinanceiroBean() {
		//	String paginaAtual = super.getPaginaAtual();

		//if (paginaAtual.contains("manualmente/gerar")) {
		carregarListas();
		carregar();
		//}
	}

	public void carregar(){
		this.contaCorrente=new ContaCorrente();
	}

	public void carregarListas(){
		this.listaContratos=new LocacaoRN().listar();
	}


	public void gravar(){
		if(this.locacao.getLocId()<1){
			this.contextoBean.mostrarErro("Selecione o contrato");
			return;
		}else{
			this.locacao=new LocacaoRN().carregar(this.locacao.getLocId());
			if(this.locacao==null){
				this.contextoBean.mostrarErro("Selecione o contrato");
				return;
			}
		}

		if((this.parcelas==null)||(this.parcelas<1)){
			this.contextoBean.mostrarErro("Nº de parcelas obrigatorio");
			return;
		}

		ContaCorrenteRN contaCorrenteRN=new ContaCorrenteRN();

		double valor=this.contaCorrente.getConCorValor();
		String historico=this.contaCorrente.getConCorHistorico();
		String obs=this.contaCorrente.getConCorObservacao();

		Integer responsavel=this.contextoBean.getFuncionarioLogado().getPesId();

		Calendar calendar=Calendar.getInstance();
		calendar.setTime(this.contaCorrente.getConCorDataVencimento());

		switch (this.operacao){
		case "1":
			for(int i=0;i<this.parcelas;i++){
				//Gerando Locador(es)
				for(Proprietario p: new ProprietarioRN().carregarProprietarios(this.locacao.getImovel())){
					contaCorrente=new ContaCorrente();
					contaCorrente.setConCorDataEmissao(new Date());
					contaCorrente.setConCorDataVencimento(calendar.getTime());
					contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
					contaCorrente.setConCorHistorico(historico);
					contaCorrente.setConCorValor(arredondarCasasDecimais(((valor*p.getProPorcentagem())/100)/this.parcelas));
					contaCorrente.setConCorTipo(true);
					contaCorrente.setConCorResponsavel(responsavel);
					contaCorrente.setConCorSituacao(false);
					contaCorrente.setPessoa(this.locacao.getPessoa());
					contaCorrente.setConCorTipoCliente("LOCADOR");
					contaCorrente.setGrupoDeContas(this.grupoDeContas);
					contaCorrente.setConCorObservacao(obs);
					contaCorrenteRN.salvar(contaCorrente);
				}
				//Gerando Locatario 
				contaCorrente=new ContaCorrente();
				contaCorrente.setConCorDataEmissao(new Date());
				calendar.add(Calendar.DATE, 7);
				contaCorrente.setConCorDataVencimento(calendar.getTime());
				contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
				contaCorrente.setConCorHistorico(historico);
				contaCorrente.setConCorValor(valor);
				contaCorrente.setConCorResponsavel(responsavel);
				contaCorrente.setConCorTipo(false);
				contaCorrente.setConCorSituacao(false);
				contaCorrente.setPessoa(this.locacao.getPessoa());
				contaCorrente.setConCorTipoCliente("LOCATARIO");
				contaCorrente.setGrupoDeContas(this.grupoDeContas);
				contaCorrente.setConCorObservacao(obs);
				contaCorrenteRN.salvar(contaCorrente);
			}
			break;
		case "2":
			//Gerando Locador(es)
			for(int i=0;i<this.parcelas;i++){
				for(Proprietario p: new ProprietarioRN().carregarProprietarios(this.locacao.getImovel())){
					contaCorrente=new ContaCorrente();
					contaCorrente.setConCorDataEmissao(new Date());
					contaCorrente.setConCorDataVencimento(calendar.getTime());
					contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
					contaCorrente.setConCorHistorico(historico);
					contaCorrente.setConCorValor(arredondarCasasDecimais(((valor*p.getProPorcentagem())/100)/this.parcelas));
					contaCorrente.setConCorTipo(false);
					contaCorrente.setConCorResponsavel(responsavel);
					contaCorrente.setConCorSituacao(false);
					contaCorrente.setPessoa(p.getPessoa());
					contaCorrente.setConCorTipoCliente("LOCADOR");
					contaCorrente.setGrupoDeContas(this.grupoDeContas);
					contaCorrente.setConCorObservacao(obs);
					contaCorrenteRN.salvar(contaCorrente);
				}
				//Gerando Locatario 
				contaCorrente=new ContaCorrente();
				contaCorrente.setConCorDataEmissao(new Date());
				calendar.add(Calendar.DATE, 7);
				contaCorrente.setConCorDataVencimento(calendar.getTime());
				contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
				contaCorrente.setConCorHistorico(historico);
				contaCorrente.setConCorValor(valor);
				contaCorrente.setConCorResponsavel(responsavel);
				contaCorrente.setConCorTipo(true);
				contaCorrente.setConCorSituacao(false);
				contaCorrente.setPessoa(this.locacao.getPessoa());
				contaCorrente.setConCorTipoCliente("LOCATARIO");
				contaCorrente.setGrupoDeContas(this.grupoDeContas);
				contaCorrente.setConCorObservacao(obs);
				contaCorrenteRN.salvar(contaCorrente);
			}
			break;
		case "3":
			//Gerando Locador(es)
			for(int i=0;i<this.parcelas;i++){
				for(Proprietario p: new ProprietarioRN().carregarProprietarios(this.locacao.getImovel())){
					contaCorrente=new ContaCorrente();
					contaCorrente.setConCorDataEmissao(new Date());
					contaCorrente.setConCorDataVencimento(calendar.getTime());
					contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
					contaCorrente.setConCorHistorico(historico);
					contaCorrente.setConCorValor(arredondarCasasDecimais(((valor*p.getProPorcentagem())/100)/this.parcelas));
					contaCorrente.setConCorTipo(false);
					contaCorrente.setConCorResponsavel(responsavel);
					contaCorrente.setConCorSituacao(false);
					contaCorrente.setPessoa(p.getPessoa());
					contaCorrente.setConCorTipoCliente("LOCADOR");
					contaCorrente.setGrupoDeContas(this.grupoDeContas);
					contaCorrente.setConCorObservacao(obs);
					contaCorrenteRN.salvar(contaCorrente);
				}
			}
			break;
		case "4":
			//Gerando Locatario 
			for(int i=0;i<this.parcelas;i++){
				contaCorrente=new ContaCorrente();
				contaCorrente.setConCorDataEmissao(new Date());
				contaCorrente.setConCorDataVencimento(calendar.getTime());
				contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
				contaCorrente.setConCorHistorico(historico);
				contaCorrente.setConCorValor(valor/this.parcelas);
				contaCorrente.setConCorTipo(false);
				contaCorrente.setConCorResponsavel(responsavel);
				contaCorrente.setConCorSituacao(false);
				contaCorrente.setPessoa(this.locacao.getPessoa());
				contaCorrente.setConCorTipoCliente("LOCATARIO");
				contaCorrente.setGrupoDeContas(this.grupoDeContas);
				contaCorrente.setConCorObservacao(obs);
				contaCorrenteRN.salvar(contaCorrente);
			}
			break;
		case "5":
			//Gerando Locador(es)
			for(int i=0;i<this.parcelas;i++){
				for(Proprietario p: new ProprietarioRN().carregarProprietarios(this.locacao.getImovel())){
					contaCorrente=new ContaCorrente();
					contaCorrente.setConCorDataEmissao(new Date());
					contaCorrente.setConCorDataVencimento(calendar.getTime());
					contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
					contaCorrente.setConCorHistorico(historico);
					contaCorrente.setConCorValor(arredondarCasasDecimais(((valor*p.getProPorcentagem())/100)/this.parcelas));
					contaCorrente.setConCorTipo(true);
					contaCorrente.setConCorResponsavel(responsavel);
					contaCorrente.setConCorSituacao(false);
					contaCorrente.setPessoa(p.getPessoa());
					contaCorrente.setConCorTipoCliente("LOCADOR");
					contaCorrente.setGrupoDeContas(this.grupoDeContas);
					contaCorrente.setConCorObservacao(obs);
					contaCorrenteRN.salvar(contaCorrente);
				}
			}

			break;
		case "6":
			//Gerando Locatario 
			for(int i=0;i<this.parcelas;i++){
				contaCorrente=new ContaCorrente();
				contaCorrente.setConCorDataEmissao(new Date());
				contaCorrente.setConCorDataVencimento(calendar.getTime());
				contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
				contaCorrente.setConCorHistorico(historico);
				contaCorrente.setConCorValor(valor/this.parcelas);
				contaCorrente.setConCorTipo(true);
				contaCorrente.setConCorResponsavel(responsavel);
				contaCorrente.setConCorSituacao(false);
				contaCorrente.setPessoa(this.locacao.getPessoa());
				contaCorrente.setConCorTipoCliente("LOCATARIO");
				contaCorrente.setGrupoDeContas(this.grupoDeContas);
				contaCorrente.setConCorObservacao(obs);
				contaCorrenteRN.salvar(contaCorrente);
			}
			break;
		default:
			this.contextoBean.mostrarErro("Selecione a MOVIMENTAÇÃO");
			return;
		}
		this.contextoBean.redirecionarParaPagina("restrito/financeiro/consulta/consulta.jsf");
	}

	public double arredondarCasasDecimais(double value) {
		long factor = (long) Math.pow(10, 2);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public String direcionarPagina(int id, String tipo){
		if(id>0 && tipo!=null){
			if(tipo.equalsIgnoreCase("SEGURO INCÊNDIO")){
				return("restrito/locacao/cadastro-contrato.jsf?id="+id);
			}
			if(tipo.equalsIgnoreCase("TAXA ADMINISTRATIVA")){
				return("restrito/locacao/cadastro-contrato.jsf?id="+id);
			}
			if(tipo.equalsIgnoreCase("ALUGUEL")){
				return("restrito/locacao/cadastro-contrato.jsf?id="+id);
			}
			if(tipo.equalsIgnoreCase("DIAS DE CADASTRO")){
				return("restrito/locacao/cadastro-contrato.jsf?id="+id);
			}
			if(tipo.equalsIgnoreCase("DESCONTO ALUGUEL")){
				return("restrito/financeiro/desconto/cadastro.jsf?id="+id);
			}
		}
		return "restrito/locacao/cadastro-contrato.jsf?id="+id;
	}

	public String carregarFuncionario(String id){
		Integer idd=Integer.valueOf(id);
		if(idd>0){
			Funcionario funcionario=new FuncionarioRN().carregar(idd);
			if(funcionario!=null){
				return funcionario.getPessoa().getPesNome();
			}else{
				return "Não encontrado";
			}

		}else{
			return "Não encontrado";
		}
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
	/* ######################################################################################################
	 * 
	 * CLASSES USADAS SOMENTE NESTE BEAN
	 * 
	 * ###################################################################################################### */

	/**
	 * Classe Temporaria usada para armazenar valores a serem pagos por cada proprietario
	 */	
	public class ProprietariosTemp implements Serializable {
		private static final long serialVersionUID = 1515117443878877401L;
		@Getter @Setter private double valor;
		@Getter @Setter private Pessoa pessoa=new Pessoa();
		public ProprietariosTemp() {
		}

		public ProprietariosTemp(double valor, Pessoa pessoa) {
			this.valor=valor;
			this.pessoa=pessoa;
		}


	}


	public void abrirDialog(){
		if(this.locacao.getLocId()<1){
			this.contextoBean.mostrarErro("Selecione um contrato");
			return;
		}
		this.locacao=new LocacaoRN().carregar(this.locacao.getLocId());

		if(this.locacao==null){
			this.contextoBean.mostrarErro("Selecione um contrato");
			return;
		}
		this.listaProprietarios=new ArrayList<FinanceiroBean.ProprietariosTemp>();
		List<Proprietario> listTemp= new ProprietarioRN().carregarProprietarios(this.locacao.getImovel());
		ProprietariosTemp proprietariosTemp=new ProprietariosTemp();
		for(Proprietario p: listTemp){
			proprietariosTemp.setPessoa(p.getPessoa());
			proprietariosTemp.setValor(new Double(0));
			this.listaProprietarios.add(proprietariosTemp);
			proprietariosTemp=new ProprietariosTemp();
		}
		this.subtrairValorLocatario=0;
		this.valorParcela=arredondarCasasDecimais(this.contaCorrente.getConCorValor()/this.parcelas);

		RequestContext.getCurrentInstance().execute("PF('dialogVal').show()");
	}
	public void subtrairValorLocatario(){	
		this.valorParcela=arredondarCasasDecimais(this.contaCorrente.getConCorValor()/this.parcelas);
		this.valorParcela-=this.subtrairValorLocatario;

		if((this.subtrairValorLocatario>0)&&(this.subtrairValorLocatario<=valorParcela)
				&&((this.valorParcela-this.subtrairValorLocatario)>= 0)){
			this.valorParcela=arredondarCasasDecimais(this.contaCorrente.getConCorValor()/this.parcelas);	
			this.valorParcela-=this.subtrairValorLocatario;
			for(ProprietariosTemp l: this.listaProprietarios){
				if((l.getValor()>0)&&((this.valorParcela-l.getValor())>=0)){
					this.valorParcela-=l.getValor();
				}					
			}
		}else{
			if((this.subtrairValorLocatario<=0)||(this.subtrairValorLocatario>(arredondarCasasDecimais(this.contaCorrente.getConCorValor()/this.parcelas)))){
				this.subtrairValorLocatario=0;
				this.valorParcela=arredondarCasasDecimais(this.contaCorrente.getConCorValor()/this.parcelas);				
				for(ProprietariosTemp l: this.listaProprietarios){
					if((l.getValor()>0)&&((this.valorParcela-l.getValor())>=0)){
						this.valorParcela-=l.getValor();
					}

				}
			}
		}
	}
	public void subtrairValorLocador(){
		this.valorParcela=arredondarCasasDecimais(this.contaCorrente.getConCorValor()/this.parcelas);
		this.valorParcela-=this.subtrairValorLocatario;

		for(ProprietariosTemp l: this.listaProprietarios){
			if((l.getValor()<0)&&(l.getValor()>this.valorParcela)
					||((this.valorParcela-l.getValor())<0)){
				l.setValor(0);
				break;
			}
			if(l.valor>0){
				this.valorParcela-=l.getValor();
			}
		}
	}
	
	public void gravarContasAPagar(){
		if((this.valorParcela>0)||(this.valorParcela<0)){
			this.contextoBean.mostrarErro("O valor deve ser distribuido entre as partes envolvidas na locação. Para gerar contas a pagar o valor deve está em 0.");
			return;
		}
		ContasAPagarRN contasAPagarRN=new ContasAPagarRN();
		ContasAPagar contasAPagar=new ContasAPagar();
		contasAPagar.setConPagDataCadastro(new Date());
		contasAPagar.setConPagDataVencimento(this.contaCorrente.getConCorDataVencimento());
		contasAPagar.setConPagGrupoDeContas(this.grupoDeContas.getGruConId());
		contasAPagar.setConPagHistorico(this.contaCorrente.getConCorHistorico());
		contasAPagar.setConPagIdContrato(this.locacao.getLocId());
		contasAPagar.setConPagObservacao(this.contaCorrente.getConCorObservacao());
		//contasAPagar.setConPagTipoMovimentacao(this.operacao);
		contasAPagar.setConPagValor(this.contaCorrente.getConCorValor());
		contasAPagarRN.salvar(contasAPagar);
		lancamentoContasAPagar(contasAPagar);
	}
	
	public void lancamentoContasAPagar(ContasAPagar contasAPagar){
		LancamentoContasAPagarRN lancamentoContasAPagarRN=new LancamentoContasAPagarRN();
		LancamentoContasAPagar lancamentoContasAPagar=new LancamentoContasAPagar();
		
		ContaCorrenteRN contaCorrenteRN=new ContaCorrenteRN();

		String historico=this.contaCorrente.getConCorHistorico();
		String obs=this.contaCorrente.getConCorObservacao();

		Integer responsavel=this.contextoBean.getFuncionarioLogado().getPesId();

		Calendar calendar=Calendar.getInstance();
		calendar.setTime(this.contaCorrente.getConCorDataVencimento());

		/*switch (this.operacao){
		case "1":*/
			for(int i=0;i<this.parcelas;i++){
				if(this.subtrairValorLocatario>0){
				contaCorrente=new ContaCorrente();
				contaCorrente.setConCorDataEmissao(new Date());
				contaCorrente.setConCorDataVencimento(calendar.getTime());
				contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
				contaCorrente.setConCorHistorico((i+1)+"ª Parcela. "+historico);
				contaCorrente.setConCorValor(this.subtrairValorLocatario);
				contaCorrente.setConCorTipo(true);
				contaCorrente.setConCorResponsavel(responsavel);
				contaCorrente.setConCorSituacao(false);
				contaCorrente.setPessoa(this.locacao.getPessoa());
				contaCorrente.setConCorTipoCliente("LOCATARIO");
				contaCorrente.setGrupoDeContas(this.grupoDeContas);
				contaCorrente.setConCorObservacao(obs);
				contaCorrenteRN.salvar(contaCorrente);
				
				lancamentoContasAPagar =new LancamentoContasAPagar();
				lancamentoContasAPagar.setContasAPagar(contasAPagar);
				lancamentoContasAPagar.setLanConPagDataCadastro(new Date());
				lancamentoContasAPagar.setLanConPagIdContaCorrente(contaCorrente.getConCorId());
				lancamentoContasAPagarRN.salvar(lancamentoContasAPagar);
				}
				
				for(ProprietariosTemp p: this.listaProprietarios){
					if(p.getValor()>0){
						contaCorrente=new ContaCorrente();
						contaCorrente.setConCorDataEmissao(new Date());
						contaCorrente.setConCorDataVencimento(calendar.getTime());
						contaCorrente.setConCorIdDocumento(this.locacao.getLocId());
						contaCorrente.setConCorHistorico((i+1)+"ª Parcela. "+historico);
						contaCorrente.setConCorValor(p.getValor());
						contaCorrente.setConCorTipo(true);
						contaCorrente.setConCorResponsavel(responsavel);
						contaCorrente.setConCorSituacao(false);
						contaCorrente.setPessoa(p.pessoa);
						contaCorrente.setConCorTipoCliente("LOCADOR");
						contaCorrente.setGrupoDeContas(this.grupoDeContas);
						contaCorrente.setConCorObservacao(obs);
						contaCorrenteRN.salvar(contaCorrente);
						
						lancamentoContasAPagar=new LancamentoContasAPagar();
						lancamentoContasAPagar.setContasAPagar(contasAPagar);
						lancamentoContasAPagar.setLanConPagDataCadastro(new Date());
						lancamentoContasAPagar.setLanConPagIdContaCorrente(contaCorrente.getConCorId());
						lancamentoContasAPagarRN.salvar(lancamentoContasAPagar);
					}
				}
				
				calendar.add(Calendar.MONTH, 1);
			/*}
			break;
		default:
			super.mostrarErro("Selecione a MOVIMENTAÇÃO");
			return;*/
		}
			this.contextoBean.redirecionarParaPagina("restrito/financeiro/consulta/consulta.jsf");
	}

}
