package br.com.locadora.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Cidade;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Fotoimovel;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.ImovelCaracteristicas;
import br.com.locadora.rn.ImovelCaracteristicasRN;
import br.com.locadora.rn.FotoimovelRN;
import br.com.locadora.rn.ImovelRN;
import br.com.locadora.web.util.ContextoUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean(name = "consultaBean")
@ViewScoped
public class ConsultaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Endereco endereco = new Endereco();
	private Bairro bairro = new Bairro();
	private Cidade cidade = new Cidade();
	private MapModel advancedModel=new DefaultMapModel();
	private Marker marker;
    private String longitude=new String("-44.8851127");
    private String latitude=new String("-20.1378218");

    @Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public ConsultaBean() throws MalformedURLException, DocumentException {
	/*	advancedModel = new DefaultMapModel();

		// Shared coordinates
		LatLng coord1 = new LatLng(-20.1378218, -44.8851127);
		LatLng coord2 = new LatLng(-20.1378218, -44.8851127);
		LatLng coord3 = new LatLng(-20.1378218, -44.8851127);
		LatLng coord4 = new LatLng(-20.1378218, -44.8851127);
		// Icons and Data
		advancedModel.addOverlay(new Marker(coord1, "Apartamento com 4 quartos","konyaalti.png",
				"http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
		advancedModel.addOverlay(new Marker(coord2, "Casa com 5 quartos", "http://localhost:8080/Locadora/resources/imagens2/renovar_logo.png",
				"http://maps.google.com/mapfiles/ms/micons/pink-dot.png"));
		advancedModel.addOverlay(new Marker(coord4, "Galbï¿½o 500 m2", "http://localhost:8080/Locadora/resources/imagens2/renovar_logo.png"));
		advancedModel.addOverlay(new Marker(coord3, "Loja com 40 m2","http://localhost:8080/Locadora/resources/imagens2/renovar_logo.png",
				"http://maps.google.com/mapfiles/ms/micons/yellow-dot.png"));*/
		
		ImovelRN imoveRn=new ImovelRN();
		List<Imovel> listaImoveis=imoveRn.listar();
		for(Imovel i: listaImoveis){
			String p = "http://maps.googleapis.com/maps/api/geocode/json?address="+ 
					i.getEndereco().getEndNome()+" "+
					i.getEndereco().getEndNumero()+","+
					i.getEndereco().getBairro().getBaiNome()+","+
					i.getEndereco().getCidade().getCidNome()+","+
					i.getEndereco().getCidade().getCidUf()+
					",brasil&sensor=false";
			String procura=p.replaceAll(" ","+");
			procura=procura.toLowerCase();
			procura = Normalizer.normalize(procura, Normalizer.Form.NFD);//retirar o acento
			procura = procura.replaceAll("[^\\p{ASCII}]", "");//retirar o acento
			try {

				// Create a URL for the desired page
				URL url = new URL(procura);

				// Read all the text returned by the server
				BufferedReader in = new BufferedReader(new InputStreamReader(
						url.openStream()));
				String str;

				String json = "";
				while ((str = in.readLine()) != null) {
					json = json + str;
				}

				in.close();

				String location = json.substring(json.indexOf("\"location\" :"),
						json.indexOf("\"location_type\"") - 13);
				String lat = location.substring((location.indexOf("\"lat\"") + 8),
						location.indexOf(","));
				String lng = location.substring((location.indexOf("\"lng\"") + 8),
						(location.indexOf("}") - 12));
				
				//se já existir o cadastro de lat e long é carregado o do banco de dados do imovel
				String latLog=i.getEndereco().getEndLatitude()+i.getEndereco().getEndLongitude();
				if((latLog!=null)){
					if(!latLog.equals("")){
						lat=i.getEndereco().getEndLatitude();
						lng=i.getEndereco().getEndLongitude();
					}}
				
				ImovelCaracteristicasRN cRN=new ImovelCaracteristicasRN();
				List<ImovelCaracteristicas> listaComplemento=cRN.listaImovelCaracteristicas(i);
				String complementos=new String();
				String caminhoFoto=new String();
				FotoimovelRN fotoimovelRN=new FotoimovelRN();
				List<Fotoimovel> listafotos=fotoimovelRN.carregarFotosDeImovel(i);
				for(Fotoimovel imo: listafotos){
					caminhoFoto=imo.getFotArquivo()+imo.getFotNome();
				}
				if((listafotos==null)||(listafotos.size()==0)){
					caminhoFoto="upload/nao-encontrada.jpg";
				}
				complementos+=i.getImoId()+":"+i.getTipoimovel().getTipNome()+": ";
				for(ImovelCaracteristicas c: listaComplemento){
					complementos+= c.getCaracteristicas().getCarNome()+"-Qtd:"+c.getId().getCarId()+" ";
				}
				
					if(i.getTipoimovel().getTipNome().equals("CASA")){
					LatLng coord1 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
					advancedModel.addOverlay(new Marker(coord1,complementos,caminhoFoto,
							"http://maps.google.com/mapfiles/ms/micons/yellow-dot.png","http://maps.gstatic.com/mapfiles/shadow50.png"));}else
								if(i.getTipoimovel().getTipNome().equals("APARTAMENTO COMERCIAL")){
									LatLng coord1 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
									advancedModel.addOverlay(new Marker(coord1,complementos,caminhoFoto,
											"http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));}else
												if(i.getTipoimovel().getTipNome().equals("APARTAMENTO RESIDENCIAL")){
													LatLng coord1 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
													advancedModel.addOverlay(new Marker(coord1,complementos,caminhoFoto,
															"http://maps.google.com/mapfiles/ms/micons/green-dot.png"));}else												
												if(i.getTipoimovel().getTipNome().equals("GALPÃƒO")){
													LatLng coord1 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
													advancedModel.addOverlay(new Marker(coord1,complementos,caminhoFoto,
															"http://maps.google.com/mapfiles/ms/micons/purple-dot.png"));}else{
																LatLng coord1 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
																advancedModel.addOverlay(new Marker(coord1,complementos,caminhoFoto,
																		"http://maps.google.com/mapfiles/ms/micons/orange-dot.png"));
															}
			
			
			} catch (MalformedURLException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
/*	public void carregarImoveis(String rua, Integer numero, String bairro, String cidade, String uf) throws MalformedURLException, DocumentException {

		String p = "http://maps.googleapis.com/maps/api/geocode/json?address="+ rua+" "+
				numero+","+
				bairro+","+
				cidade+","+uf+",brasil&sensor=false";
		String procura=p.replaceAll(" ","+");
		procura=procura.toLowerCase();
		procura = Normalizer.normalize(procura, Normalizer.Form.NFD);
		procura = procura.replaceAll("[^\\p{ASCII}]", "");
		try {

			// Create a URL for the desired page
			URL url = new URL(procura);

			// Read all the text returned by the server
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String str;

			String json = "";
			while ((str = in.readLine()) != null) {
				json = json + str;
			}

			in.close();

			String location = json.substring(json.indexOf("\"location\" :"),
					json.indexOf("\"location_type\"") - 13);
			String lat = location.substring((location.indexOf("\"lat\"") + 8),
					location.indexOf(","));
			String lng = location.substring((location.indexOf("\"lng\"") + 8),
					(location.indexOf("}") - 12));

			LatLng coord1 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
			advancedModel.addOverlay(new Marker(coord1, "Apartamento com 4 quartos","konyaalti.png",
					"http://maps.google.com/mapfiles/ms/micons/yellow-dot.png"));

		
		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	public MapModel getAdvancedModel() {
		return advancedModel;
	}

	public void onMarkerSelect(OverlaySelectEvent event) {
		marker = (Marker) event.getOverlay();
	}
	 public Marker getMarker() {  
	        return marker;  
	    }  
	public void carregarEnd() throws MalformedURLException, DocumentException {

		String p = "http://maps.googleapis.com/maps/api/geocode/json?address="+ endereco.getEndNome()+" "+
		endereco.getEndNumero()+","+
				bairro.getBaiNome()+","+
				cidade.getCidNome()+","+cidade.getCidUf()+",brasil&sensor=false";
		String procura=p.replaceAll(" ","+");
		procura=procura.toLowerCase(); 
		
		// procura="http://maps.googleapis.com/maps/api/geocode/json?address=avenida+primeiro+de+junho+43,centro,divinï¿½polis,mg,brasil&sensor=false";
		procura = Normalizer.normalize(procura, Normalizer.Form.NFD);
		procura = procura.replaceAll("[^\\p{ASCII}]", "");
		//System.out.println(procura);
		try {

			// Create a URL for the desired page
			URL url = new URL(procura);

			// Read all the text returned by the server
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String str;

			String json = "";
			while ((str = in.readLine()) != null) {
				json = json + str;
			}

			in.close();

			String location = json.substring(json.indexOf("\"location\" :"),
					json.indexOf("\"location_type\"") - 13);
			String lat = location.substring((location.indexOf("\"lat\"") + 8),
					location.indexOf(","));
			String lng = location.substring((location.indexOf("\"lng\"") + 8),
					(location.indexOf("}") - 12));

		/*	System.out.println(location);
			System.out.println(lat);
			System.out.println(lng);*/
			latitude=lat;
			longitude=lng;
			LatLng coord1 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
			advancedModel.addOverlay(new Marker(coord1, "Local escolido na pesquisa","konyaalti.png",
					"http://maps.google.com/mapfiles/ms/icons/ylw-pushpin.png"));

		} catch (StringIndexOutOfBoundsException e) {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Endereï¿½o",
					"Nï¿½o encontrado, endereï¿½o nï¿½o existente ou erro ao digitar"));
			e.printStackTrace();

		
		} catch (MalformedURLException e) {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao conectar",
					"Problemas internos"));
			e.printStackTrace();

		} catch (IOException e) {
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao conectar",
					"Problemas internos"));
			e.printStackTrace();
		}

/*	tipo de marcadores do google
	POI.png
	POI.shadow.png
	arts.png
	arts.shadow.png
	bar.png
	bar.shadow.png
	blue-dot.png
	blue-pushpin.png
	blue.png
	bus.png
	bus.shadow.png
	cabs.png
	cabs.shadow.png
	camera.png
	camera.shadow.png
	campfire.png
	campfire.shadow.png
	campground.png
	campground.shadow.png
	caution.png
	caution.shadow.png
	coffeehouse.png
	coffeehouse.shadow.png
	convienancestore.png
	convienancestore.shadow.png
	cycling.png
	cycling.shadow.png
	dollar.png
	dollar.shadow.png
	drinking_water.png
	drinking_water.shadow.png
	earthquake.png
	earthquake.shadow.png
	electronics.png
	electronics.shadow.png
	euro.png
	euro.shadow.png
	fallingrocks.png
	fallingrocks.shadow.png
	ferry.png
	ferry.shadow.png
	firedept.png
	firedept.shadow.png
	fishing.png
	fishing.shadow.png
	flag.png
	flag.shadow.png
	gas.png
	gas.shadow.png
	golfer.png
	golfer.shadow.png
	green-dot.png
	green.png
	grn-pushpin.png
	grocerystore.png
	grocerystore.shadow.png
	groecerystore.png
	groecerystore.shadow.png
	helicopter.png
	helicopter.shadow.png
	hiker.png
	hiker.shadow.png
	homegardenbusiness.png
	homegardenbusiness.shadow.png
	horsebackriding.png
	horsebackriding.shadow.png
	hospitals.png
	hospitals.shadow.png
	hotsprings.png
	hotsprings.shadow.png
	info.png
	info.shadow.png
	info_circle.png
	info_circle.shadow.png
	landmarks-jp.png
	landmarks-jp.shadow.png
	lightblue.png
	lodging.png
	lodging.shadow.png
	ltblu-pushpin.png
	ltblue-dot.png
	man.png
	man.shadow.png
	marina.png
	marina.shadow.png
	mechanic.png
	mechanic.shadow.png
	motorcycling.png
	motorcycling.shadow.png
	movies.png
	movies.shadow.png
	msmarker.shadow.png
	orange-dot.png
	orange.png
	parkinglot.png
	parkinglot.shadow.png
	partly_cloudy.png
	partly_cloudy.shadow.png
	pharmacy-us.png
	pharmacy-us.shadow.png
	phone.png
	phone.shadow.png
	picnic.png
	picnic.shadow.png
	pink-dot.png
	pink-pushpin.png
	pink.png
	plane.png
	plane.shadow.png
	police.png
	police.shadow.png
	postoffice-jp.png
	postoffice-jp.shadow.png
	postoffice-us.png
	postoffice-us.shadow.png
	purple-dot.png
	purple-pushpin.png
	purple.png
	pushpin_shadow.png
	question.png
	question.shadow.png
	rail.png
	rail.shadow.png
	rainy.png
	rainy.shadow.png
	rangerstation.png
	rangerstation.shadow.png
	realestate.png
	realestate.shadow.png
	recycle.png
	recycle.shadow.png
	red-dot.png
	red-pushpin.png
	red.png
	restaurant.png
	restaurant.shadow.png
	sailing.png
	sailing.shadow.png
	salon.png
	salon.shadow.png
	shopping.png
	shopping.shadow.png
	ski.png
	ski.shadow.png
	snack_bar.png
	snack_bar.shadow.png
	snowflake_simple.png
	snowflake_simple.shadow.png
	sportvenue.png
	sportvenue.shadow.png
	subway.png
	subway.shadow.png
	sunny.png
	sunny.shadow.png
	swimming.png
	swimming.shadow.png
	toilets.png
	toilets.shadow.png
	trail.png
	trail.shadow.png
	tram.png
	tram.shadow.png
	tree.png
	tree.shadow.png
	truck.png
	truck.shadow.png
	volcano.png
	volcano.shadow.png
	water.png
	water.shadow.png
	waterfalls.png
	waterfalls.shadow.png
	webcam.png
	webcam.shadow.png
	wheel_chair_accessible.png
	wheel_chair_accessible.shadow.png
	woman.png
	woman.shadow.png
	yellow-dot.png
	yellow.png
	yen.png
	yen.shadow.png
	ylw-pushpin.png*/
	}

	public Document getDocumento(URL url) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);

		return document;
	}

	public void onStateChange(StateChangeEvent event) {
		LatLngBounds bounds = event.getBounds();
		int zoomLevel = event.getZoomLevel();

		addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "NÃ­vel de zoom",
				String.valueOf(zoomLevel)));
		addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Centro", event
				.getCenter().toString()));
		addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Nordeste",
				bounds.getNorthEast().toString()));
		addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sudoeste",
				bounds.getSouthWest().toString()));
	}

	public void onPointSelect(PointSelectEvent event) {
		LatLng latlng = event.getLatLng();

		addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Point Selected", "Lat:" + latlng.getLat() + ", Lng:"
						+ latlng.getLng()));
	}

	public void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	 public void onMarkerDrag(MarkerDragEvent event) {  
	        marker = event.getMarker();  
	          
	        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Dragged", "Lat:" + marker.getLatlng().getLat() + ", Lng:" + marker.getLatlng().getLng()));  
	    }

	

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
	public void detalhes(String endereco){
		String x=new String();
		for (int i =0;i< endereco.length();i++) {
			if(endereco.charAt(i)==':'){
				break;
			}
			x+=endereco.charAt(i);			
		} 
		this.contextoBean.redirecionarParaPagina("restrito/imovel/cadastro.jsf?id="+x);
	}
}
