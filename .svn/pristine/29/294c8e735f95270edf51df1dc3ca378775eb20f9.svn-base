package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Banco;
import br.com.locadora.rn.BancoRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "bancoBean")
@ViewScoped
public class BancoBean implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = 4722636896068917961L;
	@Getter @Setter	private Banco banco=new Banco();
	@Getter @Setter	private List<Banco> listaBancos;
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
public BancoBean() {
	carregarListas();
	carregar();
}
public void carregarListas() {
	this.listaBancos = new BancoRN().listar();
}
public void carregar() {
	int bancoID = this.contextoBean.getParametro("id", -1);
	this.banco=new Banco();
	if (bancoID > 0) {
		Banco bancoCarregado=new BancoRN().carregar(bancoID);			
		if (bancoCarregado != null){
			this.banco = bancoCarregado;
			this.alteracao=true;
		}
	}
}
public void salvar() {
	
	BancoRN bancoRN=new BancoRN();
	Banco ban=bancoRN.buscarPorBanco(this.banco.getBanNome());
	if(ban==null){
		if(alteracao){
			bancoRN.salvar(this.banco);	
			carregar();
			carregarListas();
			this.contextoBean.redirecionarParaPagina("admin/banco/consulta.jsf");
			return;
		}
	}else{
		this.contextoBean.mostrarAviso("Este banco já existe");
		return;
	}
	bancoRN.salvar(this.banco);	
	carregar();
	carregarListas();
	this.contextoBean.redirecionarParaPagina("admin/banco/consulta.jsf");
}

public void excluir() {
	if (this.banco.getBanId() <= 0) {
		this.contextoBean.mostrarAviso("Esse banco ainda não foi salvo!");
		return;
	}
	boolean resposta=new BancoRN().excluir(this.banco);
	if(resposta){
		this.contextoBean.mostrarAviso("Excluído com sucesso");
	return;}else{
		this.contextoBean.mostrarErro("Banco não excluído, esse banco está ligado a um registro.");
		return;
	}
}

}
