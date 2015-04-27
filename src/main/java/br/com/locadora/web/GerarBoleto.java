package br.com.locadora.web;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "gerarBoleto")
@ViewScoped
public class GerarBoleto implements Serializable{
	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = 9055267826974455062L;
	@Getter @Setter	private File arquivoPdf;
	@Getter @Setter	private BoletoViewer boletoViewer;
	@Getter @Setter String caminhoPDF;
	@Getter @Setter	 private ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	//Site: http://jrimum.org/
	public GerarBoleto() {
		gerar();
	}
	public void gerar(){
		/*
		 * INFORMANDO DADOS SOBRE O CEDENTE.
		 */
		Cedente cedente = new Cedente("Renovar Sistemas", "00.000.208/0001-00");

		/*
		 * INFORMANDO DADOS SOBRE O SACADO.
		 */
		Sacado sacado = new Sacado("Mario Lucio ", "222.222.222-22");

		// Informando o endereço do sacado.
		Endereco enderecoSac = new Endereco();
		enderecoSac.setUF(UnidadeFederativa.RN);
		enderecoSac.setLocalidade("Natal");
		enderecoSac.setCep(new CEP("59064-120"));
		enderecoSac.setBairro("Grande Centro");
		enderecoSac.setLogradouro("Rua poeta dos programas");
		enderecoSac.setNumero("1");
		sacado.addEndereco(enderecoSac);

		/*
		 * INFORMANDO DADOS SOBRE O SACADOR AVALISTA.
		 */
		SacadorAvalista sacadorAvalista = new SacadorAvalista("Bruno Analista", "00.000.000/0001-91");

		// Informando o endereço do sacador avalista.
		Endereco enderecoSacAval = new Endereco();
		enderecoSacAval.setUF(UnidadeFederativa.DF);
		enderecoSacAval.setLocalidade("Brasília");
		enderecoSacAval.setCep(new CEP("59000-000"));
		enderecoSacAval.setBairro("Grande Centro");
		enderecoSacAval.setLogradouro("Rua Eternamente Principal");
		enderecoSacAval.setNumero("001");
		sacadorAvalista.addEndereco(enderecoSacAval);

		/*
		 * INFORMANDO OS DADOS SOBRE O TÍTULO.
		 */

		// Informando dados sobre a conta bancária do título.
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_BRADESCO.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(123456, "0"));
		contaBancaria.setCarteira(new Carteira(30));
		contaBancaria.setAgencia(new Agencia(1234, "1"));

		Titulo titulo = new Titulo(contaBancaria, sacado, cedente, sacadorAvalista);
		titulo.setNumeroDoDocumento("123456");
		titulo.setNossoNumero("99345678912");
		titulo.setDigitoDoNossoNumero("5");
		titulo.setValor(BigDecimal.valueOf(0.23));
		titulo.setDataDoDocumento(new Date());
		titulo.setDataDoVencimento(new Date());
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(Aceite.A);
		titulo.setDesconto(new BigDecimal(0.05));
		titulo.setDeducao(BigDecimal.ZERO);
		titulo.setMora(BigDecimal.ZERO);
		titulo.setAcrecimo(BigDecimal.ZERO);
		titulo.setValorCobrado(new BigDecimal(9990.05));

		/*
		 * INFORMANDO OS DADOS SOBRE O BOLETO.
		 */
		Boleto boleto = new Boleto(titulo);

		boleto.setLocalPagamento("Pagável preferencialmente na Rede X ou em " +
				"qualquer Banco até o Vencimento.");
		boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor " +
				"cobrado não é o esperado, aproveite o DESCONTÃO!");
		boleto.setInstrucao1("PARA PAGAMENTO 1 até Hoje não cobrar nada!");
		boleto.setInstrucao2("PARA PAGAMENTO 2 até Amanhã Não cobre!");
		boleto.setInstrucao3("PARA PAGAMENTO 3 até Depois de amanhã, OK, não cobre.");
		boleto.setInstrucao4("PARA PAGAMENTO 4 até 04/xx/xxxx de 4 dias atrás COBRAR O VALOR DE: R$ 01,00");
		boleto.setInstrucao5("PARA PAGAMENTO 5 até 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
		boleto.setInstrucao6("PARA PAGAMENTO 6 até 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
		boleto.setInstrucao7("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR QUE VOCÊ QUISER!");
		boleto.setInstrucao8("APÓS o Vencimento, Pagável Somente na Rede X.");

		/*
		 * GERANDO O BOLETO BANCÁRIO.
		 */
		// Instanciando um objeto "BoletoViewer", classe responsável pela
		// geração do boleto bancário.
		BoletoViewer boletoViewer = new BoletoViewer(boleto);

		// Gerando o arquivo. No caso o arquivo mencionado será salvo na mesma
		// pasta do projeto. Outros exemplos:
		// WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
		// LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");
		this.caminhoPDF = servletContext.getRealPath(File.separator+"resources"+File.separator + "upload" + File.separator +"MeuPrimeiroBoleto.pdf" );
		System.out.println("Caminho do PDF gerado: "+this.caminhoPDF);
		this.arquivoPdf = boletoViewer.getPdfAsFile(caminhoPDF);
		this.boletoViewer=boletoViewer;
		// Mostrando o boleto gerado na tela.
		//  mostreBoletoNaTela(arquivoPdf);
	}

	/**
	 * Exibe o arquivo na tela.
	 * 
	 * @param arquivoBoleto
	 */
	/*private static void mostreBoletoNaTela(File arquivoBoleto) {

        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        try {
                desktop.open(arquivoBoleto);
        } catch (IOException e) {
                e.printStackTrace();
        }
}*/

	public String abrirNovaAba(){

		BoletoViewer boletoViewer = this.boletoViewer;

		byte[] pdfAsBytes = boletoViewer.getPdfAsByteArray();

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

		try {

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename=MeuPrimeiroBoleto.pdf");

			OutputStream output = response.getOutputStream();
			output.write(pdfAsBytes);
			response.flushBuffer();

			FacesContext.getCurrentInstance().responseComplete();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String download(){

		BoletoViewer boletoViewer = this.boletoViewer;

		byte[] pdfAsBytes = boletoViewer.getPdfAsByteArray();

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

		try {

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=MeuPrimeiroBoleto.pdf");

			OutputStream output = response.getOutputStream();
			output.write(pdfAsBytes);
			response.flushBuffer();

			FacesContext.getCurrentInstance().responseComplete();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
