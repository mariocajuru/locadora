package br.com.locadora.web;

import java.io.Serializable;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.StreamedContent;

import br.com.locadora.util.UtilException;
import br.com.locadora.web.util.ContextoUtil;
import br.com.locadora.web.util.RelatorioUtil;

@ManagedBean(name = "relatorioBean")
@ViewScoped
public class RelatorioBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2025261422984135512L;
	private StreamedContent arquivoRetorno;
	private int tipoRelatorio;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StreamedContent getArquivoRetorno() throws UtilException{
		try {
			
		
		FacesContext context = FacesContext.getCurrentInstance();
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		String usuario = contextoBean.getFuncionarioLogado().getFunLoguin();
		String nomeRelatorioJasper = "relatorio1";
		String nomeRelatorioSaida = usuario + "_pes_cad";
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		HashMap parametrosRelatorio = new HashMap();
		parametrosRelatorio.put("codigoUsuario", 1);
		try {
			this.arquivoRetorno = relatorioUtil.geraRelatorio(
					parametrosRelatorio, nomeRelatorioJasper,
					nomeRelatorioSaida, this.tipoRelatorio);
		} catch (UtilException e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
		} catch (Exception ee) {
			throw new UtilException("Tente novamente. Usuario ('loguin') não encontrado no banco de dados - RelatorioBean", ee);
		}
		return this.arquivoRetorno;
	}

	public void setArquivoRetorno(StreamedContent arquivoRetorno) {
		this.arquivoRetorno = arquivoRetorno;
	}

	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

}
