package br.com.locadora.web.log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.envers.RevisionType;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.log.Auditoria;
import br.com.locadora.rn.log.AuditoriaRN;

@ManagedBean(name = "auditoriaBean")
@ViewScoped
public class AuditoriaBean implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = -2709107162159750836L;
	@Getter @Setter	private Auditoria auditoria=new Auditoria();
	@Getter @Setter	private List<Pesquisa> listaAuditorias;
	public AuditoriaBean() {
		carregarListas();
		carregar();
	}

	public void carregarListas(){
		this.listaAuditorias=new ArrayList<AuditoriaBean.Pesquisa>();
		List<Object[]> results =new AuditoriaRN().listar();

		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		for(Object[] objects: results ){
			Pesquisa pesquisa=new Pesquisa();
			if(objects[1] instanceof Auditoria){
				Auditoria auditoria=(Auditoria) objects[1];
				pesquisa.setNomeFun(auditoria.getNome());
				pesquisa.setData(fmt.format(auditoria.getRevisionDate()));
				pesquisa.setIp(auditoria.getIp_maquina());
				pesquisa.setLoguin(auditoria.getLoguin());
				pesquisa.setNome_maquina(auditoria.getNome_maquina());
			}

			if(objects[0] instanceof Bairro){
				Bairro bairro=(Bairro) objects[0];
				pesquisa.setId_item(""+bairro.getBaiId());
				pesquisa.setItem(" Codigo: "+bairro.getBaiId()+" - Nome:"+bairro.getBaiNome());
				pesquisa.setNome_item("Bairro");
			}

			if(objects[2] instanceof RevisionType){
				RevisionType revisionType=(RevisionType) objects[2];
				if(revisionType.name().equalsIgnoreCase("DEL"))
					pesquisa.setTipo("DELETADO");

				if(revisionType.name().equalsIgnoreCase("MOD"))
					pesquisa.setTipo("MODIFICADO");

				if(revisionType.name().equalsIgnoreCase("ADD"))
					pesquisa.setTipo("ADICIONADO");
			}
			this.listaAuditorias.add(pesquisa);
		}
	}

	public void carregar(){
		this.auditoria=new Auditoria();
	}

	/* ######################################################################################################
	 * 
	 * CLASSES USADAS SOMENTE NESTE BEAN
	 * 
	 * ###################################################################################################### */

	/**
	 * Classe Temporaria usada para armazenar as caracteristicas das auditorias
	 */	
	public class Pesquisa implements Serializable {

		private static final long serialVersionUID = 7000008299134150181L;
		@Getter @Setter private String nomeFun=new String();
		@Getter @Setter private String data=new String();
		@Getter @Setter private String loguin=new String();
		@Getter @Setter private String ip=new String();
		@Getter @Setter private String item=new String();
		@Getter @Setter private String id_item=new String();
		@Getter @Setter private String nome_item= new String();
		@Getter @Setter private String tipo=new String();
		@Getter @Setter private String nome_maquina=new String();

		public Pesquisa() {  }

		public Pesquisa(String nomeFun,String data,String loguin,String ip,String item,String id_item,String nome_item,String tipo,String nome_maquina){
			this.nomeFun=nomeFun;
			this.data=data;
			this.loguin=loguin;
			this.ip=ip;
			this.item=item;
			this.id_item=id_item;
			this.nome_item=nome_item;
			this.tipo=tipo;
			this.nome_maquina=nome_maquina;
		}

	}

}
