package br.com.locadora.web;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.ContaCorrente;
import br.com.locadora.modelo.DescontoLocacao;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.GrupoDeContas;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Locacao;
import br.com.locadora.modelo.Proprietario;
import br.com.locadora.rn.ContaCorrenteRN;
import br.com.locadora.rn.DescontoLocacaoRN;
import br.com.locadora.rn.FuncionarioRN;
import br.com.locadora.rn.LocacaoRN;
import br.com.locadora.rn.ProprietarioRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "descontoLocacaoBean")
@ViewScoped
public class DescontoLocacaoBean implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = -8655434367047786863L;
	@Getter @Setter private DescontoLocacao descontoLocacao=new DescontoLocacao();
	@Getter @Setter private Locacao locacao=new Locacao();
	@Getter @Setter private GrupoDeContas grupoDeContas=new GrupoDeContas();
	@Getter @Setter private String proprietarios=new String();
	@Getter @Setter private String endereco=new String();
	
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private List<DescontoLocacao> listaDescontoLocacao;
	@Getter @Setter private List<Locacao> listaContratos;
	@Getter @Setter private List<Proprietario> listaProprietarios;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public DescontoLocacaoBean() {

		carregarListas();
		carregar();
	}
	
	public void carregar(){
		this.locacao=new Locacao();
		this.descontoLocacao=new DescontoLocacao();
		int descontoID = this.contextoBean.getParametro("id", -1);
		if (descontoID > 0) {
			DescontoLocacao descontoCarregado=new DescontoLocacaoRN().carregar(descontoID);			
			if (descontoCarregado != null){
				this.descontoLocacao=descontoCarregado;
				this.locacao=this.descontoLocacao.getLocacao();
				this.endereco= this.locacao.getImovel().getEndereco().getEndNome()+", Nº"+this.locacao.getImovel().getEndereco().getEndNumero()+"/"+
						this.locacao.getImovel().getEndereco().getEndComplemento()+"-"+this.locacao.getImovel().getEndereco().getBairro().getBaiNome();
				List<Proprietario> listaPro=new ProprietarioRN().carregarProprietarios(this.locacao.getImovel());
				for(Proprietario p: listaPro){
					this.proprietarios+=p.getPessoa().getPesNome()+". Porcentagem:"+p.getProPorcentagem()+"%.";
				}
				this.alteracao=true;
			}
		}
	}
	
	public void carregarListas(){
		this.listaDescontoLocacao=new DescontoLocacaoRN().listar();
		int descontoID = this.contextoBean.getParametro("id", -1);
		if (descontoID < 1) {
		this.listaContratos=new LocacaoRN().listar();
		}
	}
	
	
	public void gravar(){
		int meses=diferencaEntreMeses(this.descontoLocacao.getDescLocInicio(), this.descontoLocacao.getDescLocFim());		
		if(meses<1){
			this.contextoBean.mostrarErro("A disparidade entre datas com o minimo de 1 mês");
			return;
		}
		
		if(this.grupoDeContas.getGruConId()<1){
			this.contextoBean.mostrarErro("Selecione o grupo de contas");
			return;
		}
		
		this.descontoLocacao.setLocacao(this.locacao);

		DescontoLocacaoRN  descontoLocacaoRN=new DescontoLocacaoRN();		
		descontoLocacaoRN.salvar(this.descontoLocacao);
		
		this.locacao=new LocacaoRN().carregar(this.locacao.getLocId());
		this.listaProprietarios= new ProprietarioRN().carregarProprietarios(this.locacao.getImovel());
		
		gerarFinanceiro();

		this.contextoBean.redirecionarParaPagina("restrito/financeiro/desconto/consulta.jsf");
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
	
	public String formatarData(Date data){
		if(data!=null){
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));
			String retorno=new String(df.format(data));
			return retorno;
		}
		return "";
	}
	
	public void gerarFinanceiro(){
		
		int meses=diferencaEntreMeses(this.descontoLocacao.getDescLocInicio(), this.descontoLocacao.getDescLocFim());
		
		ContaCorrenteRN contaCorrenteRN=new ContaCorrenteRN();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		Calendar venc=Calendar.getInstance();
		venc.setTime(this.descontoLocacao.getDescLocInicio());
		
		for(int i=0; i< meses;i++){
			ContaCorrente contaCorrente=new ContaCorrente();
			//gerado Financeiro do Locatario
			contaCorrente=new ContaCorrente();
			contaCorrente.setConCorIdDocumento(this.descontoLocacao.getDescLocId());
			contaCorrente.setConCorSituacao(false);
			contaCorrente.setConCorTipo(true);
			contaCorrente.setGrupoDeContas(this.grupoDeContas);
			contaCorrente.setConCorDataVencimento(this.contextoBean.primeiroDiaMes(venc.getTime()));
			contaCorrente.setConCorDataEmissao(new Date());
			contaCorrente.setConCorValor(this.contextoBean.arredondarCasasDecimais(this.descontoLocacao.getDescLocValor()));
			contaCorrente.setConCorHistorico((i+1)+"º MÊS"+" DESCONTO ALUGUEL PERIODO "+ dateFormat.format(this.contextoBean.primeiroDiaMes(venc.getTime()))+ " A " + dateFormat.format(this.contextoBean.ultimoDiaMes(venc.getTime())));
			contaCorrente.setPessoa(this.locacao.getPessoa());
			contaCorrente.setConCorTipoCliente("LOCATARIO");
			contaCorrente.setConCorObservacao("");
			contaCorrente.setConCorResponsavel(this.contextoBean.getFuncionarioLogado().getPesId());
			contaCorrenteRN.salvar(contaCorrente);
			
			for(Proprietario p: this.listaProprietarios){				
				contaCorrente=new ContaCorrente();
				contaCorrente.setConCorIdDocumento(this.descontoLocacao.getDescLocId());
				contaCorrente.setConCorSituacao(false);
				contaCorrente.setConCorTipo(false);
				contaCorrente.setGrupoDeContas(this.grupoDeContas);	
				contaCorrente.setConCorDataVencimento(this.contextoBean.primeiroDiaMes(venc.getTime()));
				contaCorrente.setConCorDataEmissao(new Date());
				contaCorrente.setConCorValor((this.contextoBean.arredondarCasasDecimais(this.descontoLocacao.getDescLocValor()*p.getProPorcentagem())/100));
				contaCorrente.setConCorHistorico((i+1)+"º MÊS"+" DESCONTO ALUGUEL PERIODO "+ dateFormat.format(this.contextoBean.primeiroDiaMes(venc.getTime()))+ " A " + dateFormat.format(this.contextoBean.ultimoDiaMes(venc.getTime())));
				contaCorrente.setPessoa(p.getPessoa());
				contaCorrente.setConCorResponsavel(this.contextoBean.getFuncionarioLogado().getPesId());
				contaCorrente.setConCorTipoCliente("LOCADOR");
				contaCorrente.setConCorObservacao("");
				contaCorrenteRN.salvar(contaCorrente);
			}
			
			venc.add(Calendar.MONTH, 1);
		}
	}
	
	public int diferencaEntreMeses(Date ini , Date fi ){  
		Calendar inicial = Calendar.getInstance();
		inicial.setTime(ini); 

		Calendar fim = Calendar.getInstance();
		fim.setTime(fi); 

		int count = 0;  

		while (inicial.get(Calendar.MONTH) != fim.get(Calendar.MONTH)){  
			inicial.add(Calendar.MONTH, 1);  		  
			count ++;  
		}  
		return count;  
	} 

}
