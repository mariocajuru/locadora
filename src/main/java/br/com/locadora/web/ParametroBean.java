package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.GrupoDeContas;
import br.com.locadora.modelo.Parametro;
import br.com.locadora.modelo.ParametroReferencia;
import br.com.locadora.rn.ParametroRN;
import br.com.locadora.rn.ParametroReferenciaRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "parametroBean")
@ViewScoped
public class ParametroBean implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = -85281980746882278L;
	@Getter @Setter	private Parametro parametro=new Parametro();
	
	@Getter @Setter	private List<Parametro> listaParametros;
	@Getter @Setter	private List<ParametroReferencia> listaParametroReferencias;
	@Getter @Setter	private List<ReferenciaTemp> listaReferenciaTemps;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public ParametroBean() {
		carregarLista();
		carregar();
	}
	
	public void carregarLista(){
		this.listaParametros=new ParametroRN().listar();
		this.listaReferenciaTemps=new ArrayList<ParametroBean.ReferenciaTemp>();
	}
	public void carregar(){
		carregarParametroReferencias();
	}
	/* ######################################################################################################
	 * 
	 * CLASSES USADAS SOMENTE NESTE BEAN
	 * 
	 * ###################################################################################################### */
	
	/**
	 * Classe Temporaria usada para armazenar as referencia do parametro
	 */	
	public class ReferenciaTemp implements Serializable {

		private static final long serialVersionUID = 4144992389922414084L;
		@Getter @Setter private int id = 0;
		@Getter @Setter private String nome = "";
		@Getter @Setter private GrupoDeContas grupoDeContas=new GrupoDeContas();		

		public ReferenciaTemp(int parRerId, String parRefNome,
				GrupoDeContas grupoDeContas) {
			this.id=parRerId;
			this.nome=parRefNome;
			this.grupoDeContas=grupoDeContas;
		}
		
	}
	/**
	 * Carrega todas as características do imóvel com a quantidade que cada uma tem no banco.
	 * Todos dados são salvos na classe temporária CaracteriticasTemp.
	 */
	public void carregarParametroReferencias() {
		ParametroReferenciaRN parametroReferenciaRN = new ParametroReferenciaRN();

		List<ReferenciaTemp> listaTodasReferenciaTemp = new ArrayList<ReferenciaTemp>();
					
		for (ParametroReferencia c : parametroReferenciaRN.listar())
			listaTodasReferenciaTemp.add(new ReferenciaTemp(c.getParRefId(), c.getParRefNome(), new GrupoDeContas()));
		
		this.listaReferenciaTemps=listaTodasReferenciaTemp;
	}
	public void salvar() {
		
	}
	public void excluir() {
		
	}

}
