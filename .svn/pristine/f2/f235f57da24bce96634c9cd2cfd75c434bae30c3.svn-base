package br.com.locadora.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.dom4j.DocumentException;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Caracteristicas;
import br.com.locadora.modelo.CaracteristicasImovelDesejado;
import br.com.locadora.modelo.CaracteristicasImovelDesejadoId;
import br.com.locadora.modelo.ChaveNoQuadro;
import br.com.locadora.modelo.Cidade;
import br.com.locadora.modelo.Email;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.EnderecoImovelDesejado;
import br.com.locadora.modelo.Filial;
import br.com.locadora.modelo.Fotoimovel;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.ImovelCaracteristicas;
import br.com.locadora.modelo.ImovelDesejado;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.QuadroDeChaves;
import br.com.locadora.modelo.Situacaoimovel;
import br.com.locadora.modelo.Telefone;
import br.com.locadora.modelo.Tipoimovel;
import br.com.locadora.rn.BairroRN;
import br.com.locadora.rn.CaracteriscasRN;
import br.com.locadora.rn.CaracteristicasImovelDesejadoRN;
import br.com.locadora.rn.ChaveNoQuadroRN;
import br.com.locadora.rn.EmailRN;
import br.com.locadora.rn.FilialRN;
import br.com.locadora.rn.FotoimovelRN;
import br.com.locadora.rn.FuncionarioRN;
import br.com.locadora.rn.ImovelCaracteristicasRN;
import br.com.locadora.rn.ImovelDesejadoRN;
import br.com.locadora.rn.ImovelRN;
import br.com.locadora.rn.PesquisaRN;
import br.com.locadora.rn.PessoaRN;
import br.com.locadora.rn.QuadroDeChavesRN;
import br.com.locadora.rn.SituacaoImovelRN;
import br.com.locadora.rn.TelefoneRN;
import br.com.locadora.rn.TipoImovelRN;
import br.com.locadora.util.UtilException;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "pesquisaImovelBean")
@ViewScoped
public class PesquisaImovelBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3454176146065170791L;

	@Getter @Setter	private Pessoa pessoa=new Pessoa();
	@Getter @Setter	private Endereco endereco=new Endereco();
	@Getter @Setter	private Bairro bairro=new Bairro();
	@Getter @Setter	private Cidade cidade=new Cidade();
	@Getter @Setter	private Telefone telefone=new Telefone();
	@Getter @Setter	private Telefone telPesquisa=new Telefone();
	@Getter @Setter	private boolean primeiroTelefone=true;
	@Getter @Setter private String telefoneParamentro=new String();// string que conterá o parametro para telefone, esse valor será enviado como parametro
	@Getter @Setter private String tipoTelParamentro=new String();// string que conterá o parametro para telefone, esse valor será enviado como parametro
	@Getter @Setter	private Email email=new Email();
	@Getter @Setter	private Imovel imovel=new Imovel();
	@Getter @Setter	private Situacaoimovel situacaoimovel=new Situacaoimovel();
	@Getter @Setter private Tipoimovel tipoimovel=new Tipoimovel();
	
	//objetos para registrar imoveis desejados
	@Getter @Setter	private ImovelDesejado imovelDesejado=new ImovelDesejado();
	@Getter @Setter private EnderecoImovelDesejado enderecoImovelDesejado=new EnderecoImovelDesejado();
	@Getter @Setter	private CaracteristicasImovelDesejado caracteristicasImovelDesejado=new CaracteristicasImovelDesejado();
	@Getter @Setter	private CaracteristicasImovelDesejadoId caracteristicasImovelDesejadoId=new CaracteristicasImovelDesejadoId();
	@Getter @Setter	private List<CaracteristicasImovelDesejado> listaCaracteristicasImovelDesejado=new ArrayList<CaracteristicasImovelDesejado>();
	@Getter @Setter	private List<CaracteriticasTempPesquisa> listaCaracteristicasDesejadas=new ArrayList<CaracteriticasTempPesquisa>();
	@Getter @Setter	private List<Bairro> listaBairrosDesejador=new ArrayList<Bairro>();
	@Getter @Setter private List<Tipoimovel> listaTipoImovelDesejador=new ArrayList<Tipoimovel>();

	
	@Getter @Setter	Boolean pessoaJaExiste=false;
	@Getter @Setter Boolean registrarPesquisa=false;
	@Getter @Setter Boolean botaoEmprestimoChave=false;
	@Getter @Setter Boolean botaoEmprestimoChaveEntreFiliais=false;

	//variaveis do mapa
	private MapModel advancedModel=new DefaultMapModel();
	private Marker marker;
	@Getter @Setter	private String longitude=new String("-44.8851127");
	@Getter @Setter	private String latitude=new String("-20.1378218");

	@Getter @Setter	private List<Tipoimovel> listaTipos=new ArrayList<Tipoimovel>();
	@Getter @Setter	private List<Situacaoimovel> listaSituacoes=new ArrayList<Situacaoimovel>();
	@Getter @Setter	private List<Bairro> listaBairros=new ArrayList<Bairro>();
	@Getter @Setter	private List<Telefone> listaTelefones=new ArrayList<Telefone>();
	@Getter @Setter	private List<Email> listaEmais=new ArrayList<Email>();
	@Getter @Setter	private List<CaracteriticasTempPesquisa> listaCaracteristicas=new ArrayList<CaracteriticasTempPesquisa>();
	@Getter @Setter	private List<Imovel> listaImoveisEncontrados=new ArrayList<Imovel>();
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public void carregar(){
		this.bairro=new Bairro();
		this.caracteristicasImovelDesejado=new CaracteristicasImovelDesejado();
		this.caracteristicasImovelDesejadoId=new CaracteristicasImovelDesejadoId();
		this.cidade=new Cidade();
		this.email=new Email();
		this.endereco=new Endereco();
		this.enderecoImovelDesejado=new EnderecoImovelDesejado();
		this.imovel=new Imovel();
		this.imovelDesejado=new ImovelDesejado();
		this.latitude=new String("-20.1378218");
		this.longitude=new String("-44.8851127");
		this.pessoa=new Pessoa();
		this.pessoaJaExiste=false;
		this.situacaoimovel=new Situacaoimovel();
		this.telefone=new Telefone();
		this.tipoimovel=new Tipoimovel();
	}
	
	public void carregarLista(){
		this.listaBairros=new ArrayList<Bairro>();
		this.listaBairrosDesejador=new ArrayList<Bairro>();
		this.listaCaracteristicas=new ArrayList<PesquisaImovelBean.CaracteriticasTempPesquisa>();
		this.listaCaracteristicasDesejadas=new ArrayList<PesquisaImovelBean.CaracteriticasTempPesquisa>();
		this.listaEmais=new ArrayList<Email>();
		this.listaImoveisEncontrados=new ArrayList<Imovel>();
		this.listaSituacoes=new ArrayList<Situacaoimovel>();
		this.listaTelefones=new ArrayList<Telefone>();
		this.listaTipoImovelDesejador=new ArrayList<Tipoimovel>();
		this.listaTipos=new ArrayList<Tipoimovel>();
	}
	
	public void addTel(){
		if(this.primeiroTelefone==true){
			this.telefone=this.telPesquisa;
			this.primeiroTelefone=false;
		}
		this.telefone.setTelSms('S');
		String tel = telefone.getTelNumero();
		tel = tel.replaceAll("[.-]", "");
		tel = tel.replaceAll("[)]", "");
		tel = tel.replaceAll("[(]", "");
		if(tel.equals("")){
			this.contextoBean.mostrarAviso("Insira o número de telefone");
			return;
		}
		TelefoneRN telefoneRN=new TelefoneRN();
		Telefone telefoneTeste=telefoneRN.buscarPorTelefone(tel);
		this.telefoneParamentro=tel;
		this.tipoTelParamentro=this.telefone.getTelTipo();
		this.telefone.setTelNumero(tel);
		if(telefoneTeste == null){
			this.listaTelefones.add(this.telefone);
			this.telefone = new Telefone();
			return;
		}
		
		this.pessoaJaExiste=true;
		this.pessoa = new PessoaRN().carregar(telefoneTeste.getPessoa().getPesId());
		FacesContext
		.getCurrentInstance()
		.addMessage(
				null,
				new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"Cadastro não efetuado - Telefone já existente !",
						"Pessoa: "+this.pessoa.getPesNome()+" Codigo: "+this.pessoa.getPesId()));
		this.listaTelefones.add(telefone);
	}

	public void removerEmail(Email e) {
		if(listaEmais == null)
			return;

		listaEmais.remove(e);
		pessoa.getEmails().remove(e);
		email = new Email();
	}

	public void addEmail(){
		EmailRN emailRN = new EmailRN();
		Email emailTeste = emailRN.buscarPorEmail(email.getEmaEndereco());

		if(emailTeste == null){
			this.listaEmais.add(email);
			this.email = new Email();
			return;
		}
		
		this.pessoaJaExiste=true;
		this.pessoa = new PessoaRN().carregar(emailTeste.getPessoa().getPesId());

		FacesContext
		.getCurrentInstance()
		.addMessage(
				null,
				new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"Cadastro não efetuado - Email já existente!",
						"Email pertencente ao Sr. " + this.pessoa.getPesNome() + " Codigo: " + this.pessoa.getPesId()));
	}

	public void removerTel(Telefone t){
		if(listaTelefones != null){
			this.listaTelefones.remove(t);
			pessoa.getTelefones().remove(t);
			this.telefone = new Telefone();
		}
	}

	public MapModel getAdvancedModel() {
		return advancedModel;
	}

	public Marker getMarker() {
		return marker;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void carregarDetalhesDoImovel() {
		CaracteriscasRN detalheImovelRN = new CaracteriscasRN();

		this.imovel.setImoLocacao(true);

		List<CaracteriticasTempPesquisa> listaTodasCaracteristiscas = new ArrayList<CaracteriticasTempPesquisa>();

		for (Caracteristicas c : detalheImovelRN.listar()) {
			if(!c.getCarNome().equals("QUARTOS")){
				listaTodasCaracteristiscas.add(new CaracteriticasTempPesquisa(c.getCarId(), c.getCarUnitario(), c.getCarNome()));}		
		}

		/*		if (imovel.getImoId() != 0) {
			List<ImovelCaracteristicas> listaDetalhesDoImovel = new ImovelCaracteristicasRN().listaImovelCaracteristicas(imovel);

			//Faz um merge das duas listas de detalhes
			for (CaracteriticasTempPesquisa detalhe : listaTodasCaracteristiscas) {

				detalhe.quantidade = 0;
				detalhe.selecionado = false;

				for (ImovelCaracteristicas detalheDoImovel : listaDetalhesDoImovel) {	

					if (detalhe.id == detalheDoImovel.getId().getCarId()) {
						detalhe.quantidade = detalheDoImovel.getId().getComQtd();

						if (detalhe.unitario)
							detalhe.selecionado = true;

						continue;
					}
				}
			}
		}*/

		this.listaCaracteristicas = listaTodasCaracteristiscas;
	}
	/**
	 * Classe Temporaria usada para armazenar as caracteristicas dos imoveis
	 */	
	public class CaracteriticasTempPesquisa implements Serializable {

		private static final long serialVersionUID = 4008135420293913986L;

		public CaracteriticasTempPesquisa() { 
			
		}

		public CaracteriticasTempPesquisa(int id, boolean unitario, String nome) {
			this.id = id;
			this.unitario = unitario;
			this.nome = nome;
		}

		private int id = 0;
		private boolean unitario = false;
		private String nome = "";
		private int quantidade = 0;
		private boolean selecionado = false;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public boolean getUnitario() {
			return unitario;
		}

		public void setUnitario(boolean unitario) {
			this.unitario = unitario;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public int getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(int quantidade) {
			this.quantidade = quantidade;
		}

		public boolean getSelecionado() {
			return selecionado;
		}

		public void setSelecionado(boolean selecionado) {
			this.selecionado = selecionado;
		}	

	}

	public void onMarkerSelect(OverlaySelectEvent event) {
		this.marker = (Marker) event.getOverlay();
	}

	public void pesquisarMapa(Imovel imo)throws MalformedURLException, DocumentException{
		if(imo.getImoId()==0){
			return;
		}

		this.imovel = new ImovelRN().carregar(imo.getImoId());

		String caminhoFoto = new String();
		String complementos = new String();
		this.advancedModel=new DefaultMapModel();

		FotoimovelRN fotoimovelRN = new FotoimovelRN();
		List<Fotoimovel> listafotos = fotoimovelRN.carregarFotosDeImovel(this.imovel);

		ImovelCaracteristicasRN cRN = new ImovelCaracteristicasRN();
		List<ImovelCaracteristicas> listaComplemento = cRN.listaImovelCaracteristicas(this.imovel);

		for (Fotoimovel imov : listafotos) {
			caminhoFoto = imov.getFotArquivo() + imov.getFotNome();
		}

		if ((listafotos == null) || (listafotos.size() == 0)) {
			caminhoFoto = "upload/nao-encontrada.jpg";
		}

		complementos += this.imovel.getTipoimovel().getTipNome() + ": ";

		for (ImovelCaracteristicas c : listaComplemento) {
			complementos += c.getCaracteristicas().getCarNome() + "-Qtd:" + c.getImoCarQtd() + " ";
		}

		this.latitude = this.imovel.getEndereco().getEndLatitude();
		this.longitude = this.imovel.getEndereco().getEndLongitude();

		if ((this.latitude != null) && (this.longitude != null)) {

			LatLng coord1 = new LatLng(Double.parseDouble(this.latitude), Double.parseDouble(this.longitude));
			advancedModel .addOverlay(new Marker(coord1, complementos, caminhoFoto, "http://maps.google.com/mapfiles/ms/icons/ylw-pushpin.png"));

			return;
		}

		String p = "http://maps.googleapis.com/maps/api/geocode/json?address="
				+ this.imovel.getEndereco().getEndNome() + " "
				+ this.imovel.getEndereco().getEndNumero() + ","
				+ this.imovel.getEndereco().getBairro().getBaiNome() + ","
				+ this.imovel.getEndereco().getCidade().getCidNome() + ","
				+ this.imovel.getEndereco().getCidade().getCidUf()
				+ ",brasil&sensor=false";

		String procura = p.replaceAll(" ", "+");
		procura = procura.toLowerCase();

		// procura = "http://maps.googleapis.com/maps/api/geocode/json?address=avenida+primeiro+de+junho+43,centro,divinï¿½polis,mg,brasil&sensor=false";
		procura = Normalizer.normalize(procura, Normalizer.Form.NFD);
		procura = procura.replaceAll("[^\\p{ASCII}]", "");

		try {
			// Create a URL for the desired page
			URL url = new URL(procura);

			// Read all the text returned by the server
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String str;

			String json = "";

			while ((str = in.readLine()) != null) {
				json = json + str;
			}

			in.close();

			String location = json.substring(json.indexOf("\"location\" :"), json.indexOf("\"location_type\"") - 13);
			String lat = location.substring((location.indexOf("\"lat\"") + 8), location.indexOf(","));
			String lng = location.substring((location.indexOf("\"lng\"") + 8), (location.indexOf("}") - 12));

			this.latitude = lat;
			this.longitude = lng;

			LatLng coord1 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

			advancedModel.addOverlay(new Marker(coord1, complementos, caminhoFoto, "http://maps.google.com/mapfiles/ms/icons/ylw-pushpin.png"));

		} catch (StringIndexOutOfBoundsException e) {
			this.contextoBean.mostrarAviso("Endereço" + "Não encontrado, endereço não existente ou erro ao digitar");
			e.printStackTrace();

		} catch (MalformedURLException e) {
			this.contextoBean.mostrarAviso("Erro ao conectar" + "Problemas internos");
			e.printStackTrace();

		} catch (IOException e) {
			this.contextoBean.mostrarAviso("Erro ao conectar" + "Problemas internos");
			e.printStackTrace();
		}

	}
	public void pesquisar(){
		this.registrarPesquisa=false;
		
		List<Bairro> listab=this.listaBairros;
		this.listaBairros=new ArrayList<Bairro>();
		BairroRN bairroRN=new BairroRN();
		for(Object sit : listab){
			int d = Integer.parseInt(sit.toString());
			this.listaBairros.add(bairroRN.carregar(d));
		}
		
		List<Situacaoimovel> listaS=this.listaSituacoes;
		this.listaSituacoes=new ArrayList<Situacaoimovel>();
		SituacaoImovelRN situacaoImovelRN=new SituacaoImovelRN();
		for(Object sit : listaS){
			int d = Integer.parseInt(sit.toString());
			this.listaSituacoes.add(situacaoImovelRN.carregar(d));
		}
		List<Tipoimovel> lisTipo=this.listaTipos;
		this.listaTipos=new ArrayList<Tipoimovel>();
		TipoImovelRN tipoImovelRN=new TipoImovelRN();
		for(Object sit : lisTipo){
			int d = Integer.parseInt(sit.toString());
			this.listaTipos.add(tipoImovelRN.carregar(d));
		}
		if((this.listaBairros.size()>1)&&(this.cidade.getCidNome()!="" 
				|| this.endereco.getEndCep()!=""
				|| this.endereco.getEndNome()!=""
				|| this.endereco.getEndNumero()!=null
				|| this.cidade.getCidUf()!=""
				|| this.endereco.getEndComplemento()!=""
				)){
			this.contextoBean.mostrarErro("Pesquisa de endereço não pode haver varios bairros com dados de endereço juntos, pesquise novamente com um unico bairro para um endereço.");
			return;
		}
		if(this.listaBairros.size()==1){
			Bairro b= this.listaBairros.get(0);
			this.endereco.setBairro(b);
		}
		//Cep
		String cep = this.endereco.getEndCep().replaceAll("[.-]", "");
		this.endereco.setEndCep(cep);
		this.endereco.setCidade(this.cidade);
		this.imovel.setEndereco(this.endereco);
		this.listaImoveisEncontrados=new ArrayList<Imovel>();
		

		PesquisaRN pesquisaRN=new PesquisaRN();
		
		Imovel imoTeste=new Imovel();
		imoTeste=this.imovel;
		this.imovel=new Imovel();

		this.listaImoveisEncontrados=pesquisaRN.pesquisarImoveis(imoTeste, this.listaCaracteristicas, this.listaTipos, this.listaSituacoes, this.listaBairros);
		if(this.listaImoveisEncontrados.size()==0){
			//adicionando as propriedades da pesquisa ao imovelDesejado
			this.imovelDesejado.setImoDesAreaImovelDe(imoTeste.getImoAreaImovel());
			this.imovelDesejado.setImoDesAreaImovelAte(imoTeste.getImoAreaTerreno());
			
			//this.imovelDesejado.setImoDesAreaTerrenoDe();
			//this.imovelDesejado.setImoDesAreaTerrenoAte();
			
			this.imovelDesejado.setImoDesLocacao(imoTeste.getImoLocacao());
			this.imovelDesejado.setImoDesVenda(imoTeste.getImoVenda());
			
			this.imovelDesejado.setImoDesQuartosDe(imoTeste.getImoDiasDeCadastro());
			this.imovelDesejado.setImoDesQuartosAte(imoTeste.getImoIdFuncionarioIndicacao());
			
			this.imovelDesejado.setImoDesValorAluguelDe(imoTeste.getImoChaveQtd().doubleValue());
			this.imovelDesejado.setImoDesValorAluguelAte(imoTeste.getImoChavePosicao().doubleValue());
			
			this.imovelDesejado.setImoDesValorMercadoDe(imoTeste.getImoValorMercado());
			this.imovelDesejado.setImoDesValorMercadoAte(imoTeste.getImoPosicao()+0.0);
			
			this.enderecoImovelDesejado.setBairro(this.endereco.getBairro());
			this.enderecoImovelDesejado.setCidade(this.endereco.getCidade());
			this.enderecoImovelDesejado.setEndImoDesCep(this.endereco.getEndCep());
			this.enderecoImovelDesejado.setEndImoDesNome(this.endereco.getEndNome());
			this.enderecoImovelDesejado.setEndImoDesNumero(this.endereco.getEndNumero());
			this.enderecoImovelDesejado.setEndImoDesZona(this.endereco.getEndZona());
			
			this.listaCaracteristicasDesejadas=this.listaCaracteristicas;
			this.listaBairrosDesejador=this.listaBairros;
			this.listaTipoImovelDesejador=this.listaTipos;
						
			Imovel imo=new Imovel();
			Tipoimovel tipoimovel=new Tipoimovel();
			tipoimovel.setTipNome("Não há imóvel encontrado");
			imo.setTipoimovel(tipoimovel);
			this.listaImoveisEncontrados.add(imo);
			
		}
		this.listaTipoImovelDesejador=this.listaTipos;
		//ativar o botão registrar Pesquisa
		if(listaImoveisEncontrados.size()==1){
		this.registrarPesquisa=true;
		}

		this.imovel=new Imovel();
		this.imovel.setImoLocacao(true);
		this.listaCaracteristicas=new ArrayList<PesquisaImovelBean.CaracteriticasTempPesquisa>();
		this.tipoimovel=new Tipoimovel();
		this.situacaoimovel=new Situacaoimovel();

		this.endereco=new Endereco();
		this.cidade=new Cidade();
		this.bairro=new Bairro();
		this.listaBairros=new ArrayList<Bairro>();
		this.listaSituacoes=new ArrayList<Situacaoimovel>();
		this.listaTipos=new ArrayList<Tipoimovel>();

	}

	public String mudarPagina(Imovel imov){
		Imovel imo=new ImovelRN().carregar(imov.getImoId());

		//verifica se o funcionario estÃ¡ logado
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		if(contextoBean == null){
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							"Usuario não logado",
							" Faça o loguin"));
			return "";
		}
		String funcionario = contextoBean.getFuncionarioLogado().getFunLoguin();
		Funcionario fun=new FuncionarioRN().buscarPorLogin(funcionario);
		QuadroDeChaves quadroDeChaves=new QuadroDeChavesRN().buscarPorFilial(fun.getFilial().getFilId());

		ChaveNoQuadroRN chaveNoQuadroRN=new ChaveNoQuadroRN();
		ChaveNoQuadro chaveNoQuadro=chaveNoQuadroRN.buscarChaveNoQuadro(quadroDeChaves.getQuaId(), imo.getImoId());
		//verifica se a chave estÃ¡ na filial
		if(chaveNoQuadro==null){
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							"A filial "+ fun.getFilial().getFilNome()+" não está com essa chave",
							" Procure em outra filial"));
			return "";
		}

		//se a estiver tudo certo , Ã© chamada a pagina
		return "emprestimochave";
	}

	public String chaveNaFilial(Imovel imov){
		if(imov.getImoId()==0){
			this.botaoEmprestimoChave=false;
			this.botaoEmprestimoChaveEntreFiliais=false;
			return"NÃO";
		}
		Imovel imo=new ImovelRN().carregar(imov.getImoId());

		//verifica se o funcionario estÃ¡ logado

		Funcionario fun = this.contextoBean.getFuncionarioLogado();
		if(fun.getPesId()==0){
			this.contextoBean.mostrarErro("Faça loguin novamente");
			return "Usuario não logado";
		}
		//TODO: fazer verificação se a chave está emprestada, retornado a string 
		
		QuadroDeChavesRN quadroDeChavesRN=new QuadroDeChavesRN();
		
		QuadroDeChaves quadroDeChaves=quadroDeChavesRN.buscarPorFilial(fun.getFilial().getFilId());
		
		if(quadroDeChaves==null){
			this.botaoEmprestimoChave=false;
			this.botaoEmprestimoChaveEntreFiliais=false;
			if(imo.getImoChaveProprietario()!=null && imo.getImoChaveProprietario()==true){
				return "Chave com o proprietario.";
			}
			return "Chave não cadastrada.";
		}

		ChaveNoQuadroRN chaveNoQuadroRN=new ChaveNoQuadroRN();
		ChaveNoQuadro chaveNoQuadro=chaveNoQuadroRN.buscarChaveNoQuadro(quadroDeChaves.getQuaId(), imo.getImoId());
		//verifica se a chave está na filial
		
		if(chaveNoQuadro == null) {
			this.botaoEmprestimoChave=false;
			this.botaoEmprestimoChaveEntreFiliais=false;
			if(imo.getImoChaveProprietario()!=null && imo.getImoChaveProprietario()==true){
				return "Chave com o proprietario.";
			}
			// pesquisar a chave em outras filiais
			List<QuadroDeChaves> lisst= quadroDeChavesRN.listar();
			boolean chavesEmOutrasFiliaisEmprestadas=false;
			for(QuadroDeChaves c: lisst){
				ChaveNoQuadro ch=chaveNoQuadroRN.buscarChaveNoQuadro(c.getQuaId(), imo.getImoId());
				if(ch!=null){
					if(ch.getEmprestimoChave()==null){
					this.botaoEmprestimoChaveEntreFiliais=true;
					return "Nâo, chave em outra filial";}
					//se todas as chaves nas outras filiais estarem emprestadas será retornada uma menssagem.
					chavesEmOutrasFiliaisEmprestadas=true;
				}
			}
			if(chavesEmOutrasFiliaisEmprestadas){
				return "NÃO, todas chaves cadastradas nas filiais emprestadas";
			}
			return "NÃO, chave não cadastrada em nenhuma filial";
		}
		if(chaveNoQuadro.getEmprestimoChave()==null){
			this.botaoEmprestimoChaveEntreFiliais=false;
			this.botaoEmprestimoChave=true;
			return "Sim";
		}
		if (chaveNoQuadro.getEmprestimoChave().getEmpId() > 0) {
			if(imo.getImoChaveProprietario()!=null && imo.getImoChaveProprietario()==true){
				return "Chave com o proprietario.";
			}
			this.botaoEmprestimoChave=false;
			this.botaoEmprestimoChaveEntreFiliais=true;
			//verificar se existe a chave em outras filiais
			List<Filial> verificarFilial=new ArrayList<Filial>();
			for(Filial f: new FilialRN().listar()){
				QuadroDeChaves quadroDeCh= quadroDeChavesRN.buscarPorFilial(f.getFilId());
				ChaveNoQuadro chaveNoQ= chaveNoQuadroRN.buscarChaveNoQuadro(quadroDeCh.getQuaId(), imo.getImoId());
				if(chaveNoQ!=null){
					if(chaveNoQ.getEmprestimoChave()==null){
						verificarFilial.add(f);
					}
				}
			}
			if(verificarFilial.size()==0){
				this.botaoEmprestimoChaveEntreFiliais=false;
			}
			//fim da verificação se existe a chave em outras filiais
			return "Chave emprestada, chave com "+ chaveNoQuadro.getEmprestimoChave().getPessoa().getPesNome();
		}
		this.botaoEmprestimoChave=true;
		this.botaoEmprestimoChaveEntreFiliais=false;
		return "Sim";
	}
	
	public void salvar() throws UtilException{
		//todo imovel tem que ter um tipo, deve ser escolhido um tipo de imovel para um imovel desejado
		if(this.listaTipoImovelDesejador.size()==0){
			this.contextoBean.mostrarAviso("Erro, selecione um tipo de imovel");
			return;
		}
		PessoaRN pessoaRN=new PessoaRN();
		ImovelDesejadoRN imovelDesejadoRN=new ImovelDesejadoRN();
		CaracteristicasImovelDesejadoRN caracteristicasImovelDesejadoRN=new CaracteristicasImovelDesejadoRN();
		TelefoneRN telefoneRN=new TelefoneRN();
		EmailRN emailRN=new EmailRN();
		
		if(this.imovelDesejado.getImoDesLocacao()!=true){
			this.imovelDesejado.setImoDesLocacao(false);
		}
		if(this.imovelDesejado.getImoDesVenda()!=true){
			this.imovelDesejado.setImoDesVenda(false);
		}
		
		this.pessoa.setPesPreCadastro(true);
		this.pessoa.setPesTipo('F');
		this.pessoa.setFuncionario(this.contextoBean.getFuncionarioLogado());
		pessoaRN.salvar(this.pessoa);
		
		for(Telefone t: this.listaTelefones){
			t.setPessoa(this.pessoa);
			String tel = t.getTelNumero();
			tel = tel.replaceAll("[.-]", "");
			tel = tel.replaceAll("[)]", "");
			tel = tel.replaceAll("[(]", "");
			t.setTelNumero(tel);
			telefoneRN.salvar(t);
		}
		
		for(Email mail: this.listaEmais){
			mail.setPessoa(this.pessoa);
			emailRN.salvar(mail);
		}
		
		this.imovelDesejado.setPessoa(this.pessoa);
		ImovelDesejado imovelDesejadoTemp=this.imovelDesejado;
		for(Tipoimovel tipo:this.listaTipoImovelDesejador){
			this.imovelDesejado.setTipoimovel(tipo);

			//salvar endereço do imovel desejado 
			/*
			this.imovelDesejado.setEnderecoImovelDesejado(this.enderecoImovelDesejado);
			if((this.enderecoImovelDesejado.getEndImoDesNome()!=null)
					||(this.enderecoImovelDesejado.getEndImoDesNumero()!=null)
					||(this.enderecoImovelDesejado.getEndImoDesCep()!=null)){
				///adiconar bairro e cidade aqui
			enderecoImovelDesejadoRN.salvar(this.enderecoImovelDesejado);
			}*/
			imovelDesejadoRN.salvar(this.imovelDesejado);
			
			//Caracteristicas do imovel desejado
			if ((this.listaCaracteristicasDesejadas.size() != 0)) {			
				for (CaracteriticasTempPesquisa caract  : this.listaCaracteristicasDesejadas) {
					if(!(((caract.getUnitario()==false)&&(caract.getQuantidade()==0))))
						if((caract.getSelecionado()==false)&&(caract.getUnitario()==false)){
							this.caracteristicasImovelDesejadoId.setCarId(caract.id);
							this.caracteristicasImovelDesejadoId.setImoDesId(this.imovelDesejado.getImoDesId());
							this.caracteristicasImovelDesejado.setImovelDesejado(this.imovelDesejado);
							this.caracteristicasImovelDesejado.setId(this.caracteristicasImovelDesejadoId);
							this.caracteristicasImovelDesejado.setCarImoDesQtd(caract.getQuantidade());
						}else if(caract.getSelecionado()==true){
							this.caracteristicasImovelDesejadoId.setCarId(caract.id);
							this.caracteristicasImovelDesejadoId.setImoDesId(this.imovelDesejado.getImoDesId());
							this.caracteristicasImovelDesejado.setImovelDesejado(this.imovelDesejado);
							this.caracteristicasImovelDesejado.setId(this.caracteristicasImovelDesejadoId);
							this.caracteristicasImovelDesejado.setCarImoDesQtd(1);
						}
				
					this.listaCaracteristicasImovelDesejado.add(this.caracteristicasImovelDesejado);
					this.caracteristicasImovelDesejado=new CaracteristicasImovelDesejado();
					this.caracteristicasImovelDesejadoId=new CaracteristicasImovelDesejadoId();
				}
			}
			this.imovelDesejado=new ImovelDesejado();
			//this.imovelDesejado.setEnderecoImovelDesejado();
			this.imovelDesejado.setImoDesAreaImovelDe(imovelDesejadoTemp.getImoDesAreaImovelDe());
			this.imovelDesejado.setImoDesAreaImovelAte(imovelDesejadoTemp.getImoDesAreaImovelAte());
			this.imovelDesejado.setImoDesAreaTerrenoDe(imovelDesejadoTemp.getImoDesAreaTerrenoDe());
			this.imovelDesejado.setImoDesAreaTerrenoAte(imovelDesejadoTemp.getImoDesAreaTerrenoAte());
			this.imovelDesejado.setImoDesDataConstrucao(imovelDesejadoTemp.getImoDesDataConstrucao());
			this.imovelDesejado.setImoDesLocacao(imovelDesejadoTemp.getImoDesLocacao());
			this.imovelDesejado.setImoDesObservacao(imovelDesejadoTemp.getImoDesObservacao());
			this.imovelDesejado.setImoDesQuartosDe(imovelDesejadoTemp.getImoDesQuartosDe());
			this.imovelDesejado.setImoDesQuartosAte(imovelDesejadoTemp.getImoDesQuartosAte());
			this.imovelDesejado.setImoDesValorAluguelDe(imovelDesejadoTemp.getImoDesValorAluguelDe());
			this.imovelDesejado.setImoDesValorAluguelAte(imovelDesejadoTemp.getImoDesValorAluguelAte());
			this.imovelDesejado.setImoDesValorCondominioDe(imovelDesejadoTemp.getImoDesValorCondominioDe());
			this.imovelDesejado.setImoDesValorCondominioAte(imovelDesejadoTemp.getImoDesValorCondominioAte());
			this.imovelDesejado.setImoDesValorMercadoDe(imovelDesejadoTemp.getImoDesValorMercadoDe());
			this.imovelDesejado.setImoDesValorMercadoAte(imovelDesejadoTemp.getImoDesValorMercadoAte());
			this.imovelDesejado.setImoDesVenda(imovelDesejadoTemp.getImoDesVenda());
			this.imovelDesejado.setPessoa(this.pessoa);
			
			for(CaracteristicasImovelDesejado c: this.listaCaracteristicasImovelDesejado){
				if((c.getImovelDesejado()!=null)){
				caracteristicasImovelDesejadoRN.salvar(c);
				}
			}
		}

		this.contextoBean.mostrarAviso("Cadastro com sucesso .....");
		
		carregar();
		carregarLista();
	}

}
