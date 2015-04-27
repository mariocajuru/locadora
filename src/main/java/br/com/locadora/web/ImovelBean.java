package br.com.locadora.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Caracteristicas;
import br.com.locadora.modelo.ChaveNoQuadro;
import br.com.locadora.modelo.ChaveNoQuadroId;
import br.com.locadora.modelo.Cidade;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Filial;
import br.com.locadora.modelo.Fotoimovel;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.ImovelCaracteristicas;
import br.com.locadora.modelo.ImovelCaracteristicasId;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Proprietario;
import br.com.locadora.modelo.ProprietarioId;
import br.com.locadora.modelo.QuadroDeChaves;
import br.com.locadora.modelo.Situacaoimovel;
import br.com.locadora.modelo.TemPerto;
import br.com.locadora.modelo.Tipoimovel;
import br.com.locadora.rn.BairroRN;
import br.com.locadora.rn.CaracteriscasRN;
import br.com.locadora.rn.ChaveNoQuadroRN;
import br.com.locadora.rn.CidadeRN;
import br.com.locadora.rn.EnderecoRN;
import br.com.locadora.rn.FilialRN;
import br.com.locadora.rn.FotoimovelRN;
import br.com.locadora.rn.FuncionarioRN;
import br.com.locadora.rn.ImovelCaracteristicasRN;
import br.com.locadora.rn.ImovelProcuradoRN;
import br.com.locadora.rn.ImovelRN;
import br.com.locadora.rn.PessoaRN;
import br.com.locadora.rn.ProprietarioRN;
import br.com.locadora.rn.QuadroDeChavesRN;
import br.com.locadora.rn.TemPertoRN;
import br.com.locadora.util.ErroValidacaoException;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "imovelBean")
@ViewScoped
public class ImovelBean implements Serializable {

	@Getter private static final long serialVersionUID = -4256935298400261586L;

	@Getter @Setter private boolean                  alteracao            = false;
	@Getter @Setter private Imovel                   imovel               = new Imovel();
	@Getter @Setter private Tipoimovel               tipoimovel           = new Tipoimovel();
	@Getter @Setter private Situacaoimovel           situacaoimovel       = new Situacaoimovel();
	@Getter @Setter private Funcionario              funcionario          = new Funcionario();
	@Getter @Setter private Endereco                 endereco             = new Endereco();
	@Getter @Setter private Bairro                   bairro               = new Bairro();
	@Getter @Setter private Cidade                   cidade               = new Cidade();
	@Getter @Setter private Funcionario              indicacao            = new Funcionario();
	@Getter @Setter private Funcionario              captador             = new Funcionario();
	@Getter @Setter private Filial            		 filial    		      = new Filial();
	@Getter @Setter private Fotoimovel 				 foto				  = new Fotoimovel();
	@Setter private List<Fotoimovel> 		 listaFotos			  = new ArrayList<Fotoimovel>(); 
	
	/** Campos Escondidos */
	
	@Getter @Setter private String                   proprietariosJson    = "";
	@Getter @Setter private String                   filiaisJson    = "";
	
	/** Listas */
	
	@Getter @Setter private List<Imovel>             listaImoveis         = new ArrayList<Imovel>();
	@Getter @Setter private List<Pessoa>             listaPessoas         = new ArrayList<Pessoa>();
	@Getter @Setter private List<CaracteriticasTemp> listaDetalhesImovel  = new ArrayList<CaracteriticasTemp>();
	@Getter @Setter private List<TemPertoTemp>       listaOQueTemPerto    = new ArrayList<TemPertoTemp>();
	@Getter @Setter private List<Filial>      		 listaFiliais         = new ArrayList<Filial>();
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	/* ######################################################################################################
	 * 
	 * CONSTRUTOR
	 * 
	 * ###################################################################################################### */
	
	public ImovelBean() { 
		String paginaAtual = this.contextoBean.getPaginaAtual();
		
		if (paginaAtual.contains("imovel/cadastro")) {
			carregar();
		}
		
		carregarListasParaConsulta();
	}

	/* ######################################################################################################
	 * 
	 * METÓDOS DE GRAVAÇÃO
	 * 
	 * ###################################################################################################### */
	
	/**
	 * Grava ou atualiza as informações do imóvel no banco de dados
	 */
	public void gravar() {
		try {
			validarCadastro();
			if (this.imovel.getImoPedencia() != "") {
				this.imovel.setImoEfetivo(true);
				this.imovel.setImoEmNegociacao(false);
				
				if(this.imovel.getImoLocacao() == true)
					this.imovel.setImoVagoDesde(new Date());
			}
			
			/** Chave */
			if (this.imovel.getImoChaveQtd() != null && this.imovel.getImoChaveQtd() > 0)
				this.imovel.setImoChave(true);
			
			/** Cidade */
			CidadeRN cidadeRN = new CidadeRN();
	
			Cidade cidadePesquisa = cidadeRN.buscarPorCidade(cidade.getCidNome());
			
			if (cidadePesquisa == null)
				cidadeRN.salvar(cidade);
			else
				cidade = cidadePesquisa;
	
			/** CEP */
			String cep = endereco.getEndCep().replaceAll("[.-]", "");
			endereco.setEndCep(cep);
			
			imovel.setSituacaoimovel(situacaoimovel);
			
			/** Endereço */
			endereco.setBairro(bairro);
			endereco.setCidade(cidade);
			imovel.setEndereco(endereco);
			
			EnderecoRN endercoRN = new EnderecoRN();
			endercoRN.salvar(imovel.getEndereco());
	
			/** Funcionário */
			if (!isAlteracao()) {
				imovel.setFuncionario(this.contextoBean.getFuncionarioLogado());
			}
			
			/** Indicação */

			this.imovel.setImoIdFuncionarioIndicacao(this.indicacao.getPesId());
			this.imovel.setImoIdCaptador(this.captador.getPesId());
			
			/** Imovel efetivo */
			this.imovel.setImoEfetivo(true);
			
			/** Imóvel */
			ImovelRN imovelRN = new ImovelRN();
			imovelRN.salvar(imovel);
	
			/** */
			gravarDetalhes();
			gravarOTemPerto();
			gravarProprietarios();
			gravarFiliais();
			
			/**Verificar a existencia de imoveis desejados *//*
			imovelRN.verificarExistenciaImovelDesejado(imovel);*/
			new ImovelProcuradoRN().procurarImovel(imovel);
			this.contextoBean.redirecionarParaPagina("restrito/imovel/consulta.jsf");
		} catch (ErroValidacaoException e) {
			this.contextoBean.mostrarAviso(e.getMessage());
		}
	}
	
	private void gravarFiliais() throws ErroValidacaoException {
		/** TODO: Verifica situação em que se altera as filiais de um imóvel. */
		try {
			int posicaoChaves = 0;
			boolean primeiraVez=true; // variavel booleana que ferifica quando o for roda na 1 ª vez
			
			if((this.filiaisJson=="")||(this.filiaisJson.equals(""))){
				return;
			}
			
			JSONArray array = new JSONArray(this.filiaisJson);
			
			ChaveNoQuadroRN chaveNoQuadroRN=new ChaveNoQuadroRN();
			
			chaveNoQuadroRN.excluirChaveNoQuadroDeImovel(this.imovel);
								
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				
				int    filialId       = Integer.parseInt(jsonObject.getString("id") + "");
				String filialNome     = jsonObject.getString("nome");
				int    posicao       = Integer.parseInt(jsonObject.getString("posicao") + "");
				int    copias       = Integer.parseInt(jsonObject.getString("copias") + "");
				
				if (posicao < 0)
					throw new ErroValidacaoException("A posição da chave no quadro da filial " + filialNome + " tem que está entre 1 e 231");
				
				if (copias < 0)
					throw new ErroValidacaoException("A posição da chave no quadro da filial " + filialNome + " tem que ter acima de 1 cópia");
				
				if (posicao == 0)
					throw new ErroValidacaoException("A posição da chave no quadro da filial " + filialNome + " tem que está entre 1 e 231");
				
				if (copias == 0)
					throw new ErroValidacaoException("A posição da chave no quadro da filial " + filialNome + " tem que ter acima de 1 cópia");
				
				if (posicao > 231)
					throw new ErroValidacaoException("A posição da chave no quadro da filial " + filialNome + " tem que está entre 1 e 231");
				
				List<Imovel> listaImoveisNaPosicacao= chaveNoQuadroRN.carregarImoveisPorPosicao(posicao);
				if(listaImoveisNaPosicacao.size()<=10){// verifica quantas vagas por posição há 
						this.imovel.setImoChavePosicao(posicao);
				}else{
					throw new ErroValidacaoException("A posição da chave no quadro da filial na posição "+posicao+" não pode ser inserida, o limite por posição é de 10 chaves, escolha outra posição");
				}
				if(!primeiraVez){
				if(posicaoChaves != posicao){
					throw new ErroValidacaoException("A posição da chave no quadro da filial tem que ser igual em todas as filais");}
				}
				primeiraVez=false;
				posicaoChaves = posicao;
				
				/* Grava */
				FilialRN filialRN=new FilialRN();
				
				Filial fil=new Filial();
				
				fil=filialRN.carregar(filialId);
				
				QuadroDeChaves q = new QuadroDeChavesRN().buscarPorFilial(fil.getFilId());
				
				ChaveNoQuadro chaveNoQuadro = new ChaveNoQuadro();
				ChaveNoQuadroId chaveNoQuadroId = new ChaveNoQuadroId();

				chaveNoQuadroId.setImoId(this.imovel.getImoId());
				chaveNoQuadroId.setQuaId(q.getQuaId());

				chaveNoQuadro.setId(chaveNoQuadroId);
				chaveNoQuadro.setImovel(this.imovel);

				chaveNoQuadro.setQuadroDeChaves(q);
				chaveNoQuadro.setChaQuaPosicao(posicao);
				chaveNoQuadro.setChaQuaQtdChave(copias);
				
				// se a opção chave com o proprietario for true não é salvo a chave na filial
				if(!this.imovel.getImoChaveProprietario()){
				new ImovelRN().salvar(this.imovel);
				new ChaveNoQuadroRN().salvar(chaveNoQuadro);
				}

			}
			
		} catch (JSONException | NumberFormatException e) {
			throw new ErroValidacaoException("Ocorreu um problema ao salvar Filiais!");
		}
	}

	private void gravarProprietarios() throws ErroValidacaoException {
		/** TODO: Verifica situação em que se altera os proprietários de um imóvel. */
		try {
			int soma = 0;
			
			JSONArray array = new JSONArray(proprietariosJson);
			
			ProprietarioRN proprietairoRN = new ProprietarioRN();
			
			//Não excluir o proprietario, porque tem que haver um historico de proprietarios do imovel
			//proprietairoRN.excluirProprietariosDeImovel(imovel);
			List<Proprietario> lista=proprietairoRN.carregarProprietarios(this.imovel);
			//carregando o proprietarios, os antigos ficaram salvos com o proprietarioAtivo Falso com a data final.
			for(Proprietario p: lista){
				p.setProAtivo(false);
				//se não existir uma data final, então é add aos proprietario
				if(p.getProDataFinal()==null){
				p.setProDataFinal(new Date());
				}
				proprietairoRN.salvar(p, true);
			}
								
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				
				int    pessoaId       = Integer.parseInt(jsonObject.getString("id") + "");
				int    dia       = Integer.parseInt(jsonObject.getString("diaPagamentoFixo") + "");
				String pessoaNome     = jsonObject.getString("nome");
				float  porcentagem    = Float.parseFloat(jsonObject.getString("porcentagem") + "");
				
				if (dia > 31)
					throw new ErroValidacaoException("O dia fixo de pagamento ao proprietário " + pessoaNome + " deve está entre 1-31");
				
				if (porcentagem <= 0)
					throw new ErroValidacaoException("A porcetagem do proprietário " + pessoaNome + " é menor ou igual a zero!");
				
				soma += porcentagem;
				
				/* Grava */
				ProprietarioId proprietarioId = new ProprietarioId();
				proprietarioId.setImoId(imovel.getImoId());
				proprietarioId.setPesId(pessoaId);
				
				Proprietario proprietario = new Proprietario();
				proprietario.setId(proprietarioId);
				proprietario.setProDiaPagamentoFixo(dia);
				proprietario.setPessoa(new Pessoa(pessoaId, pessoaNome));
				proprietario.setImovel(imovel);
				//se não é uma alteração no imovel
				proprietario.setProAtivo(true);
				proprietario.setProDataInicio(new Date());
				
				Proprietario proprietario2=proprietairoRN.carregarProprietarioPorImovelPessoa(proprietario.getPessoa(), imovel);
				if(proprietario2!=null){
					proprietario=proprietario2;
					proprietario.setProDataFinal(null);
					proprietario.setProAtivo(true);
				}
				proprietario.setProPorcentagem(porcentagem);
				
				proprietairoRN.salvar(proprietario, this.isAlteracao());
			}
			
			if (soma != 100){
				throw new ErroValidacaoException("A porcetagem entre os proprietários não é igual a 100%.");
			}
			
	
		} catch (JSONException | NumberFormatException e) {
			throw new ErroValidacaoException("Ocorreu um problema ao salvar os proprietários!");
		}
	}

	private void gravarOTemPerto() {
		TemPertoRN temPertoRN = new TemPertoRN();

		if (isAlteracao()) {
			this.imovel.getTemPertos().clear();
			
			new ImovelRN().salvar(this.imovel);
		}
		
		if (listaOQueTemPerto != null) {
			for (TemPertoTemp tp : listaOQueTemPerto) {				
				if (tp.selecionado == true) {
					
					TemPerto temPerto = temPertoRN.carregar(tp.getId());
					
					temPerto.getImovels().add(this.imovel);
					new TemPertoRN().salvar(temPerto);
					
					this.imovel.getTemPertos().add(temPerto);
					new ImovelRN().salvar(imovel);
				}
			}
		}
	}

	private void gravarDetalhes() {
		/** Detalhes */
		ImovelCaracteristicasRN imovelCaracteristicasRN = new ImovelCaracteristicasRN();

		if (isAlteracao()) {
			List<ImovelCaracteristicas> a = imovelCaracteristicasRN.listaImovelCaracteristicas(imovel);
			
			for (ImovelCaracteristicas x : a)
				imovelCaracteristicasRN.excluir(x);
		}
		
		if (listaDetalhesImovel != null) {
			for (CaracteriticasTemp localDetalhe : listaDetalhesImovel) {
				if (localDetalhe.unitario && localDetalhe.selecionado == true)
					localDetalhe.quantidade = 1;
				
				if ((localDetalhe.selecionado == false && localDetalhe.quantidade > 0) ||
					localDetalhe.selecionado == true) {
					
					ImovelCaracteristicasId localComplementoImovelId = new ImovelCaracteristicasId();
	
					localComplementoImovelId.setImoId(imovel.getImoId());
					localComplementoImovelId.setCarId(localDetalhe.id);
	
					ImovelCaracteristicas localComplementoImovel = new ImovelCaracteristicas();
	
					localComplementoImovel.setCaracteristicas(new Caracteristicas(localDetalhe.id, localDetalhe.nome));
					localComplementoImovel.setImovel(imovel);
					localComplementoImovel.setId(localComplementoImovelId);
					localComplementoImovel.setImoCarQtd(localDetalhe.quantidade);
					
					imovelCaracteristicasRN.salvar(localComplementoImovel);
				}
			}
		}
	}

	/* ######################################################################################################
	 * 
	 * METÓDOS DE VALIDAÇÃO
	 * 
	 * ###################################################################################################### */
	
	/**
	 * Valições necessárias ao gravar um novo imóvel
	 */
	private void validarCadastro() throws ErroValidacaoException {
		/** Verifica proprietários */
		if (proprietariosJson.equalsIgnoreCase(""))
			throw new ErroValidacaoException("Adicione pelo menos um proprietário!");
		
		/** Bairro */
		BairroRN bairroRN = new BairroRN();
		
		Bairro bairroPesquisa = bairroRN.buscarPorBairro(bairro.getBaiNome());

		if (bairroPesquisa == null)//throw new ErroValidacaoException("O bairro não está relacionado a nenhuma região");
			bairroRN.salvar(this.bairro);
			else
				bairro = bairroPesquisa;

		/** Situação */
		if (situacaoimovel == null || situacaoimovel.getSitId() <= 0)
			throw new ErroValidacaoException("A situação não foi selecionada!");

		/** Tipo */
		if (tipoimovel == null || tipoimovel.getTipId() <= 0)
			throw new ErroValidacaoException("O tipo não foi selecionado!");
		
		imovel.setTipoimovel(tipoimovel);
		
		/**validar proprietario*/
		try {
			int soma = 0;
			
			JSONArray array = new JSONArray(proprietariosJson);
								
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				String pessoaNome     = jsonObject.getString("nome");
				Integer dia			  =Integer.parseInt(jsonObject.getString("diaPagamentoFixo")+"");
				float  porcentagem    = Float.parseFloat(jsonObject.getString("porcentagem") + "");
				
				if (dia > 31)
					throw new ErroValidacaoException("O dia fixo de pagamento ao proprietário " + pessoaNome + " deve está entre 1-31");
				
				if (porcentagem <= 0)
					throw new ErroValidacaoException("A porcetagem do proprietário " + pessoaNome + " é menor ou igual a zero!");
				
				soma += porcentagem;
			
			}
			
			if (soma != 100)
				throw new ErroValidacaoException("A porcetagem entre os proprietários não é igual a 100%.");
		} catch (JSONException | NumberFormatException e) {
			throw new ErroValidacaoException("Ocorreu um problema em validar os proprietários!");
		}
	}
	
	/* ######################################################################################################
	 * 
	 * METÓDOS DE CARREGAR
	 * 
	 * ###################################################################################################### */

	/**
	 * Verifica se o parametro GET 'id' foi usado na página e carrega o imóvel com o código passado
	 */
	public void carregar() {
		try {
			int imovelID = this.contextoBean.getParametro("id", -1);

			if (imovelID <= 0) {
				setAlteracao(false);
			} else {
				setAlteracao(true);

				Imovel imovelCarregado = new ImovelRN().carregar(imovelID);

				this.imovel = imovelCarregado;
				
				if (this.imovel == null) {
					this.imovel = new Imovel();
					setAlteracao(false);
				}
				this.tipoimovel = this.imovel.getTipoimovel();
				this.situacaoimovel = this.imovel.getSituacaoimovel();
				this.funcionario = this.imovel.getFuncionario();
				this.endereco = imovel.getEndereco();
				
				if (endereco != null) {
					bairro = endereco.getBairro();
					cidade = endereco.getCidade();
				}
				
				if (imovel.getImoIdFuncionarioIndicacao() != null && imovel.getImoIdFuncionarioIndicacao() > 0)
					this.indicacao = new FuncionarioRN().carregar(imovel.getImoIdFuncionarioIndicacao());
				else
					this.indicacao = new Funcionario();
				
				if (imovel.getImoIdCaptador() != null && imovel.getImoIdCaptador() > 0)
					this.captador = new FuncionarioRN().carregar(imovel.getImoIdCaptador());
				else
					this.captador = new Funcionario();
				
				this.listaFotos=new FotoimovelRN().carregarFotosDeImovel(this.imovel);
				this.foto=new Fotoimovel();
				
				carregarProprietarios();
				carregarFiliais();
			}
			carregarCaracteristicas();
			carregarOQueTemPerto();
		} catch (NumberFormatException e) {
			setAlteracao(false);
		}
	}
	
	public void carregarListasParaConsulta() {
		this.listaFiliais=new FilialRN().listar();
		this.listaImoveis = new ImovelRN().listar();
		this.listaPessoas = new ArrayList<Pessoa>();
		List<Pessoa> lista= new PessoaRN().carregarListaPessoasNaoPreCadastro();
		for(Pessoa p: lista){
			if(p.getPesProprietario()==true){
				this.listaPessoas.add(p); 
			}
		}
	}
	
	/**
	 * Carrega todas as características do imóvel com a quantidade que cada uma tem no banco.
	 * Todos dados são salvos na classe temporária CaracteriticasTemp.
	 */
	public void carregarCaracteristicas() {
		CaracteriscasRN detalheImovelRN = new CaracteriscasRN();

		List<CaracteriticasTemp> listaTodosDetalhes = new ArrayList<CaracteriticasTemp>();
					
		for (Caracteristicas c : detalheImovelRN.listar())
			listaTodosDetalhes.add(new CaracteriticasTemp(c.getCarId(), c.getCarUnitario(), c.getCarNome()));
		
		if (imovel.getImoId() != 0) {
			List<ImovelCaracteristicas> listaDetalhesDoImovel = new ImovelCaracteristicasRN().listaImovelCaracteristicas(imovel);
			
			/** Faz um merge das duas listas */
			for (CaracteriticasTemp detalhe : listaTodosDetalhes) {
				
				detalhe.quantidade = 0;
				detalhe.selecionado = false;
									
				for (ImovelCaracteristicas detalheDoImovel : listaDetalhesDoImovel) {	
					
					if (detalhe.id == detalheDoImovel.getId().getCarId()) {
						detalhe.quantidade = detalheDoImovel.getImoCarQtd();
						
						if (detalhe.unitario)
							detalhe.selecionado = true;
						
						continue;
					}
					
				}
			}
		}
		
		/** Organiza lista com características não unitárias exibidas primeiro */
		ArrayList<CaracteriticasTemp> caracterisicasOrganizadas = new ArrayList<CaracteriticasTemp>();
		
		for (CaracteriticasTemp c : listaTodosDetalhes)
			if (c.isUnitario() == false)
				caracterisicasOrganizadas.add(c);
		
		for (CaracteriticasTemp c : listaTodosDetalhes)
			if (c.isUnitario() == true)
				caracterisicasOrganizadas.add(c);
		
		listaDetalhesImovel = caracterisicasOrganizadas;
	}
	
	private void carregarOQueTemPerto() {
		TemPertoRN temPertoRN = new TemPertoRN();

		List<TemPertoTemp> listaTodosTemPerto = new ArrayList<TemPertoTemp>();
					
		for (TemPerto t : temPertoRN.listar())
			listaTodosTemPerto.add(new TemPertoTemp(t.getTemPerId(), t.getTemPerNome()));
		
		if (imovel.getImoId() != 0) {
			List<TemPerto> listaTemPertoDoImovel = new TemPertoRN().carregarListaPorImovel(this.imovel);
			
			/** Faz um merge das duas listas */
			for (TemPertoTemp tp : listaTodosTemPerto) {									
				for (TemPerto temPerto : listaTemPertoDoImovel) {	
					
					if (tp.id == temPerto.getTemPerId()) {
						tp.selecionado = true;
						continue;
					}
					
				}
			}
		}
				
		listaOQueTemPerto = listaTodosTemPerto;
	}
	
	public void carregarFiliais(){
		List<ChaveNoQuadro> lista = new ArrayList<ChaveNoQuadro>();
		ChaveNoQuadroRN chaveNoQuadroRN=new ChaveNoQuadroRN();
		lista=chaveNoQuadroRN.carregarChaveNoQuadroPorImovel(this.imovel);

		JSONArray array = new JSONArray();
		
		for (ChaveNoQuadro p : lista) {
			
			try {
				JSONObject obj = new JSONObject();
				obj.put("id", p.getQuadroDeChaves().getFilial().getFilId());
				obj.put("nome", p.getQuadroDeChaves().getFilial().getFilNome());
				obj.put("cpfcnpj", p.getQuadroDeChaves().getFilial().getFilCnpj());
				obj.put("posicao", p.getChaQuaPosicao());
				obj.put("copias", p.getChaQuaQtdChave());
				
				array.put(obj);
			} catch (JSONException e) { }
			
		}
		
		if (array.length() > 0)
			filiaisJson = array.toString();
	}
	
	public void carregarProprietarios() {
		ArrayList<Proprietario> lista = (ArrayList<Proprietario>) new ProprietarioRN().carregarProprietarios(imovel);
		
		JSONArray array = new JSONArray();
		
		for (Proprietario p : lista) {
			//carregando proprietarios que estejam ativos
			if(p.getProAtivo()){
			
			try {
				JSONObject obj = new JSONObject();
				obj.put("id", p.getPessoa().getPesId());
				obj.put("nome", p.getPessoa().getPesNome());
				obj.put("cpfcnpj", p.getPessoa().getPesCpfCnpj());
				obj.put("porcentagem", p.getProPorcentagem());
				if(p.getProDiaPagamentoFixo()==null){
					obj.put("diaPagamentoFixo",0);
				}else{
					obj.put("diaPagamentoFixo", p.getProDiaPagamentoFixo());
				}
				array.put(obj);
			} catch (JSONException e) { }
			
			}
			
		}
		
		if (array.length() > 0)
			proprietariosJson = array.toString();
	}
	
	/* ######################################################################################################
	 * 
	 * CLASSES USADAS SOMENTE NESTE BEAN
	 * 
	 * ###################################################################################################### */
	
	/**
	 * Classe Temporaria usada para armazenar as caracteristicas dos imoveis
	 */	
	public class CaracteriticasTemp implements Serializable {
		
		private static final long serialVersionUID = 4008135420293913986L;
		
		@Getter @Setter private int id = 0;
		@Getter @Setter private boolean unitario = false;
		@Getter @Setter private String nome = "";
		@Getter @Setter private int quantidade = 0;
		@Getter @Setter private boolean selecionado = false;
		
		public CaracteriticasTemp() {  }
				
		public CaracteriticasTemp(int id, boolean unitario, String nome) {
			this.id = id;
			this.unitario = unitario;
			this.nome = nome;
		}
		
	}

	/**
	 * Classe Temporaria usada para armazenar o que tem perto dos imovel
	 */	
	public class TemPertoTemp implements Serializable {
		
		private static final long serialVersionUID = 4008135420293913986L;
		
		@Getter @Setter private int id = 0;
		@Getter @Setter private String nome = "";
		@Getter @Setter private boolean selecionado = false;
		
		public TemPertoTemp() {  }
				
		public TemPertoTemp(int id, String nome) {
			this.id = id;
			this.nome = nome;
		}
				
	}

	public String mostraDadosBancariosProprietarios(Integer id){
		Pessoa pes=new PessoaRN().carregar(id);
		if(pes.getDadosBancariosProprietario()!=null){
			if(pes.getDadosBancariosProprietario().getDadBanProEmCarteria()){
				return "Banco: "+pes.getDadosBancariosProprietario().getBanco().getBanNome()+
						" Agencia :"+pes.getDadosBancariosProprietario().getDadBanProAgencia()+
						" Conta: "+pes.getDadosBancariosProprietario().getDadBanProConta()+
						" Pagamento em carteira";
			}else{
				return "Banco: "+pes.getDadosBancariosProprietario().getBanco().getBanNome()+
						" Agencia :"+pes.getDadosBancariosProprietario().getDadBanProAgencia()+
						" Conta: "+pes.getDadosBancariosProprietario().getDadBanProConta()+
						" Depósito bancario";
			}
		}
		return "Dados bancários não cadastrados.";
	}

	public List<Fotoimovel> getListaFotos() {
		
		if((this.listaFotos==null & this.imovel!=null)||((this.listaFotos.size()==0)&(this.imovel!=null))){
			this.listaFotos= new FotoimovelRN().carregarFotosDeImovel(this.imovel);
			if((this.listaFotos==null)||(this.listaFotos.size()==0)){
				Fotoimovel foto=new Fotoimovel();
				foto.setFotArquivo("upload");
				foto.setFotId(0);
				foto.setFotNome("nao-encontrada.jpg");
				foto.setImovel(this.imovel);
				this.listaFotos=new ArrayList<Fotoimovel>();
				this.listaFotos.add(foto);
			}
		}
		return listaFotos;
	}

	public void excluirFotoImovel(){
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String idObjeto = params.get("paramId");
		FotoimovelRN fotoimovelRN=new FotoimovelRN();
		this.foto=fotoimovelRN.carregar(Integer.valueOf(idObjeto));
		if(this.foto!=null){
			ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator +this.foto.getFotArquivo());   
			File destino = new File(caminho, this.foto.getFotNome());   
			try {  
				destino.delete();  
			} catch (Exception e) {  
				throw new RuntimeException("Erro ao deletar imagem", e);  
			}
			fotoimovelRN.excluir(this.foto);
			this.listaFotos=null;}
	}
	
	public void addFotoImovelWeb(){
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String idObjeto = params.get("paramIdWeb");
		FotoimovelRN fotoimovelRN=new FotoimovelRN();
		this.foto=fotoimovelRN.carregar(Integer.valueOf(idObjeto));		
		if(this.foto!=null){
			if(this.foto.getFotWeb()!=null){
				if(this.foto.getFotWeb().equals(false)){
					this.foto.setFotWeb(true);
				}else{
					this.foto.setFotWeb(false);
				}
			}else{
				this.foto.setFotWeb(true);
			}
			fotoimovelRN.salvar(this.foto);
			this.listaFotos=null;}
	}
	public void upload(FileUploadEvent event) throws Exception{
		FotoimovelRN fotoimovelRN = new FotoimovelRN();
		this.foto=fotoimovelRN.buscarPorNomeEImovel(event.getFile().getFileName(), imovel);
		if(this.foto==null){
			this.foto=new Fotoimovel();}else{
				if(this.foto.getFotId()>0){
					return;
				}
			}
		try {
			copyFile(event.getFile().getFileName(), event.getFile()
					.getInputstream());			
			
			this.foto.setImovel(imovel);
			this.foto.setFotArquivo("upload/"+imovel.getTipoimovel().getTipNome().trim()+ "/" + imovel.getImoId()+"/");
			
			this.foto.setFotNome(event.getFile().getFileName());
			try {
				fotoimovelRN.salvar(this.foto);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO , ", e
								.getMessage()));	}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.imovel.setImoFoto(true);
		//new ImovelRN().salvar(this.imovel);
	}
	public void copyFile(String fileName, InputStream in) {
		try {
			ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "upload" + File.separator + (imovel.getTipoimovel().getTipNome().trim())
					+ File.separator +imovel.getImoId()+File.separator);
			System.out.println(caminho);
			File diretorio = new File(caminho);
			diretorio.mkdirs();
			 
			OutputStream out = new FileOutputStream(new File(caminho + File.separator
					, fileName));
			
			int read = 0;
			byte[] bytes = new byte[1024];//poderia ser byte[] bytes = event.getFile().getContents(); 
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();			
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO:", e
							.getMessage()));
		}
	}
}