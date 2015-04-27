package br.com.locadora.web;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.ContaCorrente;
import br.com.locadora.modelo.GrupoDeContas;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Locacao;
import br.com.locadora.modelo.Proprietario;
import br.com.locadora.modelo.ValorIndiceReajuste;
import br.com.locadora.rn.ContaCorrenteRN;
import br.com.locadora.rn.GrupoDeContasRN;
import br.com.locadora.rn.LocacaoRN;
import br.com.locadora.rn.ProprietarioRN;
import br.com.locadora.rn.ValorIndiceReajusteRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "contaCorrenteBean")
@ViewScoped
public class ContaCorrenteBean implements Serializable{
	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = 4527398434961607L;
	@Getter @Setter	private ContaCorrente contaCorrente=new ContaCorrente();
	@Getter @Setter	private List<ContaCorrente> listaContaCorrentes;
	@Getter @Setter private boolean alteracao         = false;

	//mês para gerar o reajuste dos contratos vencendo
	@Getter @Setter private Integer mes;
	@Getter @Setter private List<Locacao> contratosPesquisados;
	@Getter @Setter private List<Locacao> contratosSelecionados;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public ContaCorrenteBean() {
		carregarListas();
		carregar();
	}

	public void carregarListas(){
		this.listaContaCorrentes=new ContaCorrenteRN().listar();
		this.contratosSelecionados=new ArrayList<Locacao>();
	}

	public void carregar(){
		int contaID = this.contextoBean.getParametro("id", -1);
		this.contaCorrente=new ContaCorrente();
		if (contaID > 0) {
			ContaCorrente contaCarregado=new ContaCorrenteRN().carregar(contaID);			
			if (contaCarregado != null){
				this.contaCorrente = contaCarregado;
				this.alteracao=true;
			}
		}
	}

	public String formatarData(Date data){
		if(data!=null){
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));
			String retorno=new String(df.format(data));
			return retorno;
		}
		return "";
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
		return "restrito/locacao/cadastro-contrato.jsf";
	}

	public void pesquisarContratos(){
		Calendar calendar=Calendar.getInstance();
		int mesAtual=calendar.get(Calendar.MONTH)+1;
		if((this.mes<mesAtual)||(this.mes>(mesAtual+1))){
			this.mes=null;
			this.contratosPesquisados=null;
			this.contextoBean.mostrarErro("O MÊS DEVE ESTÁ ENTRE O MÊS ATUAL E PROXIMO MÊS");
			return;
		}
		this.contratosPesquisados= new LocacaoRN().contratosVencimentroMes(this.mes);
		
		if((this.contratosPesquisados==null)||(this.contratosPesquisados.size()==0))
			this.contextoBean.mostrarErro("Não encontrado contrato(s) com vencimento com o mês "+this.mes);

		this.mes=null;
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

	public void gerarReajuste(){
		/*				LocacaoRN locacaoRN=new LocacaoRN();
			
		//recuperando os contratos selecionados
	List<Locacao> listeTemp=this.contratosSelecionados;
		this.contratosSelecionados=new ArrayList<Locacao>();
		for(Locacao l: listeTemp){			
			this.contratosSelecionados.add(locacaoRN.carregar(l.getLocId()));
		}*/
		
		gerarFinanceiro();

		this.contextoBean.mostrarAviso("Geração ok");
		
		this.contratosPesquisados=null;
		this.mes=null;
	}

	public void gerarFinanceiro(){
		ValorIndiceReajusteRN valorIndiceReajusteRN=new ValorIndiceReajusteRN();
		
		for(Locacao locacao: this.contratosPesquisados){
			ValorIndiceReajuste valorIndiceReajuste=valorIndiceReajusteRN.buscarValorIndicePorData(locacao.getIndicesReajustes(), locacao.getLocLancamentoMesAno());
			
			if(valorIndiceReajuste==null){
				this.contextoBean.mostrarErro("Indice de Reajuste não encontrado na tabela. Verifique valores e datas cadastradas para o contrato Cod: "+locacao.getLocId());
				return;
			}
			
			List<Proprietario> listaProprietarioImovel=new ProprietarioRN().carregarProprietarios(locacao.getImovel());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");		

			//Grupo de Contas Temporario
			GrupoDeContas deContasAluguel=new GrupoDeContasRN().buscarPorGrupoDeContas("ALUGUEL");	

			ContaCorrenteRN contaCorrenteRN=new ContaCorrenteRN();

			Calendar c = Calendar.getInstance();
			c.setTime(locacao.getLocLancamentoMesAno());//add a data do fim do ultimo contrato locação
			c.setTime(this.contextoBean.ultimoDiaMes(c.getTime()));//pegando o ultimo dia do mês
			c.add(Calendar.DATE, 1);//ultimo dia do mes + 1 dia, 
			c.setTime(this.contextoBean.primeiroDiaMes(c.getTime()));//tendo assim o 1º dia do segundo mes !

			if(locacao.getLocModoPamento().equalsIgnoreCase("GARANTIDO POSTERIOR")){
				c.setTime(this.contextoBean.ultimoDiaMes(c.getTime()));//pegando o ultimo dia do mês
				c.add(Calendar.DATE, 1);//ultimo dia do mes + 1 dia, 
				c.setTime(this.contextoBean.primeiroDiaMes(c.getTime()));//tendo assim o 1º dia do segundo mes !
			}

			//add LANÇAMENTO(MÊS/ANO) no contrato da locação
			Calendar lancamentoMes=c;
			lancamentoMes.add(Calendar.MONTH, 12);
			locacao.setLocLancamentoMesAno(lancamentoMes.getTime());
			new LocacaoRN().salvar(locacao);

			//atendendo o chamado 2001, gerar somente os 12 primeiros  meses aparti do inicio do contrato
			for(int i=0; i<(12-1);i++){
				ContaCorrente contaCorrente=new ContaCorrente();
				//gerado Financeiro do Locatario
				contaCorrente=new ContaCorrente();
				contaCorrente.setConCorIdDocumento(locacao.getLocId());
				contaCorrente.setConCorSituacao(false);
				contaCorrente.setConCorTipo(true);
				contaCorrente.setGrupoDeContas(deContasAluguel);			

				Calendar venc =Calendar.getInstance();
				venc.setTime(c.getTime());

				venc.add(Calendar.DATE,(locacao.getLocDiaPagamentoLocatario()-1));

				contaCorrente.setConCorDataVencimento(venc.getTime());
				contaCorrente.setConCorDataEmissao(new Date());
				contaCorrente.setConCorValor(this.contextoBean.arredondarCasasDecimais((locacao.getImovel().getImoValorAluguel().doubleValue()*valorIndiceReajuste.getValIndReaValor())/100));
				contaCorrente.setConCorHistorico((i+1)+"º"+" ALUGUEL PERIODO "+ dateFormat.format(this.contextoBean.primeiroDiaMes(c.getTime()))+ " A " + dateFormat.format(this.contextoBean.ultimoDiaMes(c.getTime())));
				contaCorrente.setPessoa(locacao.getPessoa());
				contaCorrente.setConCorTipoCliente("LOCATARIO");
				contaCorrente.setConCorObservacao("");
				contaCorrenteRN.salvar(contaCorrente);

				for(Proprietario p: listaProprietarioImovel){
					contaCorrente=new ContaCorrente();
					Calendar vencimento =Calendar.getInstance();
					vencimento.setTime(c.getTime());
					if((p.getProDiaPagamentoFixo()==null)||(p.getProDiaPagamentoFixo()==0)){
						vencimento.add(Calendar.DATE, 7);
					}else{
						if(p.getProDiaPagamentoFixo()==locacao.getLocDiaPagamentoLocador()){
							vencimento.add(Calendar.DATE,(p.getProDiaPagamentoFixo()-1));
						}else{
							vencimento.add(Calendar.DATE,(locacao.getLocDiaPagamentoLocador()-1));
						}
					}
					contaCorrente.setConCorIdDocumento(locacao.getLocId());
					contaCorrente.setConCorSituacao(false);
					contaCorrente.setConCorTipo(false);
					contaCorrente.setGrupoDeContas(deContasAluguel);	
					contaCorrente.setConCorDataVencimento(vencimento.getTime());
					contaCorrente.setConCorDataEmissao(new Date());
					contaCorrente.setConCorValor(this.contextoBean.arredondarCasasDecimais((((locacao.getImovel().getImoValorAluguel().doubleValue()*p.getProPorcentagem())/100)*valorIndiceReajuste.getValIndReaValor())/100));
					contaCorrente.setConCorHistorico((i+1)+"º"+" ALUGUEL PERIODO "+ dateFormat.format(this.contextoBean.primeiroDiaMes(c.getTime()))+ " A " + dateFormat.format(this.contextoBean.ultimoDiaMes(c.getTime())));
					contaCorrente.setPessoa(p.getPessoa());
					contaCorrente.setConCorTipoCliente("LOCADOR");
					contaCorrente.setConCorObservacao("");
					contaCorrenteRN.salvar(contaCorrente);
				}
				c.setTime(this.contextoBean.ultimoDiaMes(c.getTime()));
				c.add(Calendar.DATE, 1);
				c.setTime(this.contextoBean.primeiroDiaMes(c.getTime()));
			}
		}
	}


}
