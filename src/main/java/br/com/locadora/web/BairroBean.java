package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Regiao;
import br.com.locadora.rn.BairroRN;
import br.com.locadora.rn.RegiaoRN;
import br.com.locadora.web.util.ContextoUtil;


@ManagedBean(name = "bairroBean")
@ViewScoped
public class BairroBean  implements Serializable{

	/**
	 * Bean de Bairro
	 * 
	 */
	private static final long serialVersionUID = 6894661044899483446L;
	@Getter @Setter	private Bairro bairro=new Bairro();
	@Getter @Setter	private Regiao regiao=new Regiao();
	@Getter @Setter	private List<Bairro> listaBairros;
	@Getter @Setter private boolean alteracao         = false;
	@Getter @Setter private ContextoBean genericBean=ContextoUtil.getContextoBean();
	
	public BairroBean() {
	carregarListas();

	carregar();
	}
	public void carregarListas() {
		this.listaBairros = new BairroRN().listar();
	}

	public void carregar() {
		int bairroID = this.genericBean.getParametro("id", -1);
		this.bairro=new Bairro();
		this.regiao=new Regiao();
		if (bairroID > 0) {
			Bairro bairroCarregado=new BairroRN().carregar(bairroID);			
			if (bairroCarregado != null){
				this.bairro = bairroCarregado;
				if(this.bairro.getRegiao()!=null){
					this.regiao=new RegiaoRN().carregar(this.bairro.getRegiao().getRegId());
				}
			this.alteracao=true;
			}
		}
	}

	public void salvar() {
		
		BairroRN bairroRN=new BairroRN();
		Bairro bai=bairroRN.buscarPorBairro(this.bairro.getBaiNome());
		if(bai!=null){
			if(alteracao){
				if(this.bairro.getRegiao()!=null){
				this.regiao=new RegiaoRN().carregar(this.bairro.getRegiao().getRegId());
				}
				bai.setRegiao(this.regiao);
				bairroRN.salvar(bai);	
				carregar();
				carregarListas();
				this.genericBean.redirecionarParaPagina("admin/endereco/bairro/consulta.jsf");
				return;
			}
			this.genericBean.mostrarAviso("Este bairro já existe");
			return;
		}
		this.bairro.setRegiao(this.regiao);
		bairroRN.salvar(this.bairro);	
		carregar();
		carregarListas();
		this.genericBean.redirecionarParaPagina("admin/endereco/bairro/consulta.jsf");
	}

	public void excluir() {
		if (this.bairro.getBaiId() <= 0) {
			this.genericBean.mostrarAviso("Esse registro ainda não foi salvo!");
			return;
		}
		boolean resposta=new BairroRN().excluir(this.bairro);
		if(resposta){
			this.genericBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.genericBean.mostrarErro("Bairro não excluído, esse bairro está ligado a um registro.");
			return;
		}
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
