package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polygon;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Regiao;
import br.com.locadora.modelo.RegiaoCoordenada;
import br.com.locadora.rn.RegiaoRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "regiaoBean")
@ViewScoped
public class RegiaoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8265132635564662126L;
	@Getter @Setter	private Regiao regiao ;
	@Getter @Setter	private RegiaoCoordenada regiaoCoordenada;
	@Getter @Setter	private List<Regiao> listaRegioes;
	@Getter @Setter	private List<RegiaoCoordenada> listaRegiaoCoordenada;
	@Getter @Setter	private MapModel mapa;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	
public RegiaoBean() {
	this.regiao=new Regiao();
	carregarListas();
	
	carregar();
}
public void carregarListas() {
	this.listaRegioes = new RegiaoRN().listar();
	this.listaRegiaoCoordenada=new ArrayList<RegiaoCoordenada>();
}

public void carregar() {
	this.regiaoCoordenada=new RegiaoCoordenada();
	this.mapa=new DefaultMapModel();
	int regiaoID = this.contextoBean.getParametro("id", -1);

	if (regiaoID > 0) {
		RegiaoRN regiaoRN = new RegiaoRN();
		Regiao regiao = regiaoRN.carregar(regiaoID);

		if (regiao == null){
			regiao = new Regiao();
		}else{
			this.regiao = regiao;
			this.listaRegiaoCoordenada=regiaoRN.carregarCoordenadasPorRegiao(regiao);

			Polygon polygon = new Polygon();
			polygon.setStrokeColor("#"+this.regiao.getRegCorMapa());
			polygon.setFillColor("#"+this.regiao.getRegCorMapa());
			polygon.setStrokeOpacity(0.7);
			polygon.setFillOpacity(0.7);
			for(RegiaoCoordenada coor: this.listaRegiaoCoordenada){
				//http://primefaces.org/showcase/ui/data/gmap/polygons.xhtml
				LatLng coord = new LatLng(Double.parseDouble(coor.getRegCooLatitude()), Double.parseDouble(coor.getRegCooLongitude()));
				polygon.getPaths().add(coord);
			}
			mapa.addOverlay(polygon);

		}
	}
}

public void salvar() {
	
	RegiaoRN regiaoRN=new RegiaoRN();
	Regiao reg=regiaoRN.buscarPorRegiao(this.regiao.getRegNome());
	if(reg!=null){
		if(this.regiao.getRegId()==reg.getRegId()){
			this.contextoBean.evict(reg);
		}else{
			this.contextoBean.mostrarAviso("Esta região já existe");
			return;			
		}		
	}
	regiaoRN.salvar(this.regiao);	
	carregar();
	carregarListas();
	this.contextoBean.redirecionarParaPagina("admin/endereco/regiao/consulta.jsf");
}

public void excluir() {
	if (this.regiao.getRegId() <= 0) {
		this.contextoBean.mostrarAviso("Esse registro ainda não foi salvo!");
		return;
	}
	
	boolean resposta=new RegiaoRN().excluir(this.regiao);
	if(resposta){
		this.contextoBean.mostrarAviso("Excluído com sucesso");
	return;}else{
		this.contextoBean.mostrarErro("Região não excluído, essa região está ligada a um registro.");
		return;
	}
}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String listaBairrosPorRegiao(int id){
		String nomeBairro=new String();
		RegiaoRN regiaoRN=new RegiaoRN();
		List<Bairro> listaBairro=new ArrayList<Bairro>();
		listaBairro=regiaoRN.carregarBairrosPorRegiao(regiaoRN.carregar(id));
		for(Bairro b: listaBairro){
			nomeBairro+=b.getBaiNome()+" ";
		}
		return nomeBairro;
	}

	public void adicionarCoordenada(){
		this.regiaoCoordenada.setRegCooLatitude(this.regiaoCoordenada.getRegCooLatitude().replace(",", "."));
		this.regiaoCoordenada.setRegCooLongitude(this.regiaoCoordenada.getRegCooLongitude().replace(",", "."));
		this.regiaoCoordenada.setRegiao(this.regiao);		
		RegiaoRN regiaoRN=new RegiaoRN();
		regiaoRN.salvarCoordenada(this.regiaoCoordenada);
		
		this.listaRegiaoCoordenada=regiaoRN.carregarCoordenadasPorRegiao(this.regiao);
		
		this.mapa=new DefaultMapModel();
		Polygon polygon = new Polygon();
		polygon.setStrokeColor("#"+this.regiao.getRegCorMapa());
		polygon.setFillColor("#"+this.regiao.getRegCorMapa());
		polygon.setStrokeOpacity(0.7);
		polygon.setFillOpacity(0.7);
		for(RegiaoCoordenada coor: this.listaRegiaoCoordenada){
			//http://primefaces.org/showcase/ui/data/gmap/polygons.xhtml
			LatLng coord = new LatLng(Double.parseDouble(coor.getRegCooLatitude()), Double.parseDouble(coor.getRegCooLongitude()));
			polygon.getPaths().add(coord);
		}
		mapa.addOverlay(polygon);
		this.regiaoCoordenada=new RegiaoCoordenada();
	}
	
	public void excluirCoordenada(){
		RegiaoRN regiaoRN=new RegiaoRN();
		int regiaoCoordenadaID = this.contextoBean.getParametro("idRegiaoCoordenada", -1);
		if (regiaoCoordenadaID > 0) {
			this.regiaoCoordenada=regiaoRN.carregarRegiaoCoordenada(regiaoCoordenadaID);
		}
		if((this.regiaoCoordenada!=null)||(this.regiaoCoordenada.getRegCooId()>0)){
			regiaoRN.excluirCoordenada(this.regiaoCoordenada);
			this.listaRegiaoCoordenada=regiaoRN.carregarCoordenadasPorRegiao(this.regiao);
			this.mapa=new DefaultMapModel();
			Polygon polygon = new Polygon();
			polygon.setStrokeColor("#"+this.regiao.getRegCorMapa());
			polygon.setFillColor("#"+this.regiao.getRegCorMapa());
			polygon.setStrokeOpacity(0.7);
			polygon.setFillOpacity(0.7);
			for(RegiaoCoordenada coor: this.listaRegiaoCoordenada){
				//http://primefaces.org/showcase/ui/data/gmap/polygons.xhtml
				LatLng coord = new LatLng(Double.parseDouble(coor.getRegCooLatitude()), Double.parseDouble(coor.getRegCooLongitude()));
				polygon.getPaths().add(coord);
			}
			mapa.addOverlay(polygon);
		}		
		this.regiaoCoordenada=new RegiaoCoordenada();
	}
	
	public void onPolygonSelect(OverlaySelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.regiao.getRegNome(), null));
    }
	
	public void onCoordSelect(PointSelectEvent event) {
		this.regiaoCoordenada=new RegiaoCoordenada();		
		LatLng latlng = event.getLatLng();
		this.regiaoCoordenada.setRegCooLatitude(String.valueOf(latlng.getLat()));
		this.regiaoCoordenada.setRegCooLongitude(String.valueOf(latlng.getLng()));
	}
}
