package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Caracteristicas;
import br.com.locadora.rn.CaracteriscasRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "caracteristicasBean")
@ViewScoped
public class CaracteriscasBean implements Serializable{

	@Getter private static final long serialVersionUID = -2719800901985421142L;
	
	@Getter @Setter private Caracteristicas       carcteristica = new Caracteristicas();
	@Getter @Setter private List<Caracteristicas> lista         = new ArrayList<Caracteristicas>();
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	

	public CaracteriscasBean() {		
		carregarListas();
		
		carregar();
	}
	
	public void carregarListas() {
		lista = new CaracteriscasRN().listar();
	}

	public void carregar() {
		int parametroID = this.contextoBean.getParametro("id", -1);
		
		if (parametroID > 0) {
			Caracteristicas objetoCarregado = new CaracteriscasRN().carregar(parametroID);
			
			if (objetoCarregado == null)
				objetoCarregado = new Caracteristicas();
			
			carcteristica = objetoCarregado;
		}
	}

	public void salvar() {
		if (carcteristica.getCarUnitario() == null)
			carcteristica.setCarUnitario(false);
		
		new CaracteriscasRN().salvar(carcteristica);
		this.contextoBean.redirecionarParaPagina("admin/imovel-caracteristicas/consulta.jsf");
		
	}

	public void excluir() {
		if (carcteristica.getCarId() <= 0) {
			this.contextoBean.mostrarAviso("Esse registro ainda não foi salvo!");
			return;
		}
		
		boolean resposta=new CaracteriscasRN().excluir(carcteristica);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Caracteristica não excluído, esse caracteristica está ligada a um registro.");
			return;
		}
	}

}