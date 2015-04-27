package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.DragDropEvent;

import br.com.locadora.modelo.ChaveNoQuadro;
import br.com.locadora.modelo.ChaveNoQuadroId;
import br.com.locadora.modelo.Filial;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.QuadroDeChaves;
import br.com.locadora.rn.ChaveNoQuadroRN;
import br.com.locadora.rn.FilialRN;
import br.com.locadora.rn.ImovelRN;
import br.com.locadora.rn.QuadroDeChavesRN;

@ManagedBean(name = "quadroChavesBean")
@ViewScoped
public class QuadroChavesBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1762971011201588374L;
	private QuadroDeChaves quadroDeChaves = new QuadroDeChaves();
	private List<QuadroDeChaves> listaQuadroDeChaves;
	private Imovel imovel = new Imovel();
	private Filial filial = new Filial();
	private List<Imovel> selectImoveis = new ArrayList<Imovel>();
	private List<Imovel> listaImoveis;
	private List<Imovel> listaChavesNoQuadro=new ArrayList<Imovel>();
	
private boolean filialSelecionada=false;

	public QuadroDeChaves getQuadroDeChaves() {
		return quadroDeChaves;
	}

	public void setQuadroDeChaves(QuadroDeChaves quadroDeChaves) {
		this.quadroDeChaves = quadroDeChaves;
	}

	public List<QuadroDeChaves> getListaQuadroDeChaves() {
		if (this.listaQuadroDeChaves == null) {
			QuadroDeChavesRN quaChavesRN = new QuadroDeChavesRN();
			this.listaQuadroDeChaves = quaChavesRN.listar();
		}
		return listaQuadroDeChaves;
	}

	public List<Imovel> getListaImoveis() {
		if (this.listaImoveis == null) {
			ImovelRN imovelRN = new ImovelRN();
			List<Imovel> lis=new ArrayList<Imovel>();
			this.listaImoveis =new ArrayList<Imovel>();
			lis= imovelRN.listar();
			
			  for(Imovel i: lis){ // se o imovel estiver em negocia√ß√£o n√£o poder√° entrar na lista para inclus√£o das chaves no quadro
			  if((i.getImoChave()!=null)&&(i.getImoChave().equals(true))){ 
				 this.listaImoveis.add(i);
			 } }
			 
		}
		return listaImoveis;
	}

	public void setListaImoveis(List<Imovel> listaImoveis) {
		this.listaImoveis = listaImoveis;
	}

	public void setListaQuadroDeChaves(List<QuadroDeChaves> listaQuadroDeChaves) {
		this.listaQuadroDeChaves = listaQuadroDeChaves;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public List<Imovel> getSelectImoveis() {
		return selectImoveis;
	}

	public void setSelectImoveis(List<Imovel> selectImoveis) {
		this.selectImoveis = selectImoveis;
	}

	public String onDrop(DragDropEvent event) {
		Imovel imo = (Imovel) event.getData();
		int posicaoChaves=0;
		for(Imovel i: this.listaChavesNoQuadro){
			if(i.getImoId()==imo.getImoId()){
				FacesContext
				.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"ImÛvel j· adicionado ao quadro.",
								" Este quadro j· contem a chave do imÛvel!"));
				return null;
			}
		}

		QuadroDeChaves q = new QuadroDeChavesRN().buscarPorFilial(this.filial
				.getFilId());

	
		ChaveNoQuadro chaveNoQuadro = new ChaveNoQuadro();
		ChaveNoQuadroId chaveNoQuadroId = new ChaveNoQuadroId();

		chaveNoQuadroId.setImoId(imo.getImoId());
		chaveNoQuadroId.setQuaId(q.getQuaId());

		chaveNoQuadro.setId(chaveNoQuadroId);
		chaveNoQuadro.setImovel(imo);

		chaveNoQuadro.setQuadroDeChaves(q);
		
		ChaveNoQuadroRN teste=new ChaveNoQuadroRN();
		List<ChaveNoQuadro> listaTesteChaveNoQuadro=teste.carregarChaveNoQuadroPorFilial(q.getQuaId());
		for(ChaveNoQuadro c: listaTesteChaveNoQuadro){
			posicaoChaves=c.getImovel().getImoChavePosicao();
		}

		if ((posicaoChaves) < 231) { /// aqui √© limitado a quantidade de chaves por quadro, que no caso contem 231 vagas
			
			if(imo.getImoChavePosicao()==null){
				imo.setImoChavePosicao(posicaoChaves + 1);}

			new ImovelRN().salvar(imo);
			new ChaveNoQuadroRN().salvar(chaveNoQuadro);
			selectImoveis.add(imo);
			this.listaQuadroDeChaves = null;
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(imo.getImoObservacao()
							+ " ImÛvel adicionado...Ok", "PosiÁ„o adicionada:"
							+ imo.getImoChavePosicao()));
			return null;
		} else {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Quadro cheio",
									"n„o h· mais posiÁıes livres no quandro, h· somente 231 vagas no quadro!"));
			return null;

		}
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}



	public void escolhaFilial() {
		this.filial = new FilialRN().carregar(filial.getFilId());
		this.filialSelecionada=true;
		// System.out.println("escolhaFilial : Id filial : --> "+this.filial.getFilId());
	}

	/*public List<Chave> getListaChavesNoQuadro() {
		ChaveNoQuadroRN chaveNoQuadroRN = new ChaveNoQuadroRN();
		 List<ChaveNoQuadro> lista=chaveNoQuadroRN.listar(); 
		// System.out.println("Id filial : --> "+this.filial.getFilId());
		QuadroDeChaves quadro = new QuadroDeChavesRN()
				.buscarPorFilial(this.filial.getFilId());
		List<ChaveNoQuadro> lista = new ArrayList<ChaveNoQuadro>();
		if (!((quadro == null) || (quadro.getQuaId() == 0))) {
			lista = chaveNoQuadroRN.carregarChaveNoQuadroPorFilial(quadro
					.getQuaId());
		}
		this.listaChavesNoQuadro = new ArrayList<Chave>();
		for (ChaveNoQuadro c : lista) {
			this.listaChavesNoQuadro.add(c.getChave());
		}
		return listaChavesNoQuadro;
	}*/



	public boolean isFilialSelecionada() {
		return filialSelecionada;
	}

	public void setFilialSelecionada(boolean filialSelecionada) {
		this.filialSelecionada = filialSelecionada;
	}

	public List<Imovel> getListaChavesNoQuadro() {
		ChaveNoQuadroRN chaveNoQuadroRN = new ChaveNoQuadroRN();
		// List<ChaveNoQuadro> lis=chaveNoQuadroRN.listar();
		QuadroDeChaves quadro = new QuadroDeChavesRN()
				.buscarPorFilial(this.filial.getFilId());
		List<ChaveNoQuadro> l = new ArrayList<ChaveNoQuadro>();
		if (!((quadro == null) || (quadro.getQuaId() == 0))) {
			l = chaveNoQuadroRN.carregarChaveNoQuadroPorFilial(quadro
					.getQuaId());
		}
		this.listaChavesNoQuadro = new ArrayList<Imovel>();
		for (ChaveNoQuadro c : l) {
			this.listaChavesNoQuadro.add(c.getImovel());
		}
		
		ordenaPorPosicao(this.listaChavesNoQuadro);
		return listaChavesNoQuadro;
	}
	  // Para ordenar por posiÁ„o  
    private static void ordenaPorPosicao(List<Imovel> lista) {  
        Collections.sort(lista, new Comparator<Imovel>() {  
            @Override  
            public int compare(Imovel o1, Imovel o2) {  
                return o1.getImoChavePosicao().compareTo(o2.getImoChavePosicao());  
            }  
           
     });  
    } 

	public void setListaChavesNoQuadro(List<Imovel> listaChavesNoQuadro) {
		this.listaChavesNoQuadro = listaChavesNoQuadro;
	}



}
