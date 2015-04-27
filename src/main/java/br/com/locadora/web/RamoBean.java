package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Ramoatuacao;
import br.com.locadora.rn.RamoatuacaoRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "ramoBean")
@ViewScoped
public class RamoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4169019763459385743L;
	@Getter @Setter	private Ramoatuacao ramo= new Ramoatuacao();
	@Getter @Setter	private List<Ramoatuacao> listaRamos;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public RamoBean() {
		this.ramo= new Ramoatuacao();
		carregarListas();

		carregar();
	}
	public void carregarListas() {
		this.listaRamos = new RamoatuacaoRN().listar();
	}

	public void carregar() {
		int ramoID = this.contextoBean.getParametro("id", -1);
		this.ramo=new Ramoatuacao();
		if (ramoID > 0) {

			Ramoatuacao ramoatuacaoCarregado = new RamoatuacaoRN().carregar(ramoID);

			if (ramoatuacaoCarregado == null)
				ramoatuacaoCarregado = new Ramoatuacao();

			this.ramo = ramoatuacaoCarregado;
		}
	}
	public void salvar() {

		RamoatuacaoRN ramoatuacaoRN=new RamoatuacaoRN();
		Ramoatuacao ram=ramoatuacaoRN.buscarPorRamoatuacao(this.ramo.getRamNome());
		if(ram!=null){
			/*this.ramo.setRamId(ram.getRamId());
			ramoatuacaoRN.salvar(this.ramo);*/
			this.contextoBean.mostrarAviso("Este ramo de atividade já existe");
			return;
		}
		ramoatuacaoRN.salvar(this.ramo);	
		carregar();
		carregarListas();		
		this.contextoBean.redirecionarParaPagina("restrito/pessoa/ramo-de-atuacao/consulta.jsf");
	}

	public void excluir() {
		if (this.ramo.getRamId() <= 0) {
			this.contextoBean.mostrarAviso("Esse registro ainda não foi salvo!");
			return;
		}

		boolean resposta=new RamoatuacaoRN().excluir(this.ramo);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Ramo de atividade não excluído, esse ramo está ligada a um registro.");
			return;
		}
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 public void salvarDialogPessoaBean(){
		RamoatuacaoRN ramoatuacaoRN=new RamoatuacaoRN();
		Ramoatuacao ram=ramoatuacaoRN.buscarPorRamoatuacao(this.ramo.getRamNome());
		if(ram!=null){
			/*this.ramo.setRamId(ram.getRamId());
			ramoatuacaoRN.salvar(this.ramo);*/
			this.contextoBean.mostrarAviso("Este ramo de atividade já existe");
			return;
		}
		ramoatuacaoRN.salvar(this.ramo);	
		carregar();
		carregarListas();	
 }
}
