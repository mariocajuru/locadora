package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.locadora.modelo.TemPerto;
import br.com.locadora.rn.TemPertoRN;
import br.com.locadora.web.util.ContextoUtil;
import lombok.Getter;
import lombok.Setter;
@ManagedBean(name = "temPertoBean")
@ViewScoped
public class TemPertoBean implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = -1051933022315622758L;
	@Getter @Setter private TemPerto temPerto=new TemPerto();
	@Getter @Setter private List<TemPerto> listaTemperto;
	@Getter @Setter private boolean alteracao=false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public TemPertoBean() {
		carregar();
		carregarLista();
	}
	public void carregar(){
		try {
			int temPertoID = this.contextoBean.getParametro("id", -1);

			if (temPertoID <= 0) {
				alteracao = false;
			} else {
				alteracao = true;

				TemPerto temPer = new TemPertoRN().carregar(temPertoID);

				this.temPerto = temPer;

				if (this.temPerto == null) {
					this.temPerto = new TemPerto();
					setAlteracao(false);
				}

			}

		} catch (NumberFormatException e) {
			alteracao = false;
		}
	}
	public void carregarLista(){
		this.listaTemperto=new TemPertoRN().listar();
	}

	public void salvar(){
		TemPertoRN temPertoRN=new TemPertoRN();
		temPertoRN.salvar(this.temPerto);
		carregar();
		carregarLista();
		this.contextoBean.redirecionarParaPagina("admin/imovel-temperto/consulta.jsf");
	}

	public void excluir(){
		if (this.temPerto.getTemPerId() <= 0) {
			this.contextoBean.mostrarAviso("Esse registro ainda não foi salvo!");
			return;
		}

		boolean resposta=new TemPertoRN().excluir(this.temPerto);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("O que tem perto não excluído, esse item está ligada a um registro.");
			return;
		}
	}

}
