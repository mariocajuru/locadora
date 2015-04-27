package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Situacaoimovel;
import br.com.locadora.rn.SituacaoImovelRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "situacaoimovelBean")
@ViewScoped
public class SituacaoImovelBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5715678591763533274L;
	@Getter @Setter	private Situacaoimovel situacaoImovel;
	@Getter @Setter	private List<Situacaoimovel> listaSituacaoImovels;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public SituacaoImovelBean() {
		this.situacaoImovel= new Situacaoimovel();
		carregarListas();

		carregar();
	}
	public void carregarListas() {
		this.listaSituacaoImovels = new SituacaoImovelRN().listar();
	}

	public void carregar() {
		int sitID = this.contextoBean.getParametro("id", -1);
		if (sitID > 0) {
			Situacaoimovel sitCarregado = new SituacaoImovelRN().carregar(sitID);
			if (sitCarregado == null)
				sitCarregado = new Situacaoimovel();
			this.situacaoImovel = sitCarregado;
		}
	}

	public void excluir() {
		if (this.situacaoImovel.getSitId() <= 0) {
			this.contextoBean.mostrarAviso("Esse registro ainda não foi salvo!");
			return;
		}
		boolean resposta=new SituacaoImovelRN().excluir(this.situacaoImovel);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Situação não excluído, essa situação está ligada a um registro.");
			return;
		}
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void salvar() {
		SituacaoImovelRN situacaoImovelRN = new SituacaoImovelRN();	
		Situacaoimovel sit=situacaoImovelRN.buscarPorSede(this.situacaoImovel.getSitNome());
		if(sit!=null){
			/*this.ramo.setRamId(ram.getRamId());
			ramoatuacaoRN.salvar(this.ramo);*/
			this.contextoBean.mostrarAviso("Esta situação já existe");
			return;
		}
		situacaoImovelRN.salvar(this.situacaoImovel);	
		carregar();
		carregarListas();
		this.contextoBean.redirecionarParaPagina("admin/imovel-situacao/consulta.jsf");
	}

}
