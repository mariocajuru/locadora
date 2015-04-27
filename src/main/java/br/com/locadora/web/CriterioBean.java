package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Caracteristicas;
import br.com.locadora.modelo.CaracteristicasImovelDesejado;
import br.com.locadora.modelo.CaracteristicasImovelDesejadoId;
import br.com.locadora.modelo.Cidade;
import br.com.locadora.modelo.Email;
import br.com.locadora.modelo.EnderecoImovelDesejado;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.ImovelDesejado;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Ramoatuacao;
import br.com.locadora.modelo.Regiao;
import br.com.locadora.modelo.Telefone;
import br.com.locadora.modelo.Tipoimovel;
import br.com.locadora.rn.CaracteristicasImovelDesejadoRN;
import br.com.locadora.rn.CaracteriscasRN;
import br.com.locadora.rn.EmailRN;
import br.com.locadora.rn.EnderecoImovelDesejadoRN;
import br.com.locadora.rn.ImovelDesejadoRN;
import br.com.locadora.rn.PesquisaRN;
import br.com.locadora.rn.PessoaRN;
import br.com.locadora.rn.RegiaoRN;
import br.com.locadora.rn.TelefoneRN;
import br.com.locadora.rn.TipoImovelRN;
import br.com.locadora.util.UtilException;

//import javax.validation.constraints.Pattern;

@ManagedBean(name = "criterioBean")
@ViewScoped
public class CriterioBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3719785736741837096L;

	private DualListModel<String> dualTipoImovel;
	private DualListModel<String> dualBairro;
	
	private ImovelDesejado imovelDesejado=new ImovelDesejado();
	private Email email=new Email();
	private Telefone telefone=new Telefone();
	private ArrayList<Telefone> listaTel=new ArrayList<Telefone>();
	private List<Email> listaEmail=new ArrayList<Email>();
	private Pessoa pessoa=new Pessoa();
	private Ramoatuacao ramo=new Ramoatuacao();
	private EnderecoImovelDesejado enderecoImovelDesejado=new EnderecoImovelDesejado();
	private Cidade cidade=new Cidade();
	private Bairro bairro=new Bairro();
	
	private List<Regiao> listaRegioes = new ArrayList<Regiao>();
	private List<Bairro> listaBairros = new ArrayList<Bairro>();
	private List<Cidade> listaCidades = new ArrayList<Cidade>();
	private List<Tipoimovel> listaTipos = new ArrayList<Tipoimovel>();
	private List<Caracteristicas> listaCaracteriscas = new ArrayList<Caracteristicas>();
	private List<Imovel> listImoveisPesquisadosLocacao = new ArrayList<Imovel>();
	private List<Imovel> listImoveisPesquisadosVenda = new ArrayList<Imovel>();
	private List<Caracteristicas> listaCaracteriscasSemQuarto;// lista sem o detalhe
														// quarto
	private Integer qtdQuartos=new Integer(0);
	private Integer quartoDe=new Integer(0);
	private Integer quartoAte=new Integer(0);
	private boolean regiaoBairro = false;
	
	
	private List<Bairro> listaBairrosDesejado = new ArrayList<Bairro>();
	private List<Cidade> listaCidadesDesejado = new ArrayList<Cidade>();
	private List<Tipoimovel> listaTiposDesejado = new ArrayList<Tipoimovel>();
	private List<Caracteristicas> listaCaracteriscasDesejado = new ArrayList<Caracteristicas>();
	private List<Caracteristicas> listaCaracteriscasSemQuartoDesejado=new ArrayList<Caracteristicas>();
	private Integer qtdQuartosDesejado=new Integer(0);
	private boolean visivelBotaoRegistraPesquisa = false;

	//@Pattern(regexp = "(^[1-9]{6}$)")
	private String valorMinimo =new String("");
	//@Pattern(regexp = "(^[1-9]{6}$)")
	private String valorMaximo =new String("");


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void onTransfer(TransferEvent event) {
		StringBuilder builder = new StringBuilder();
		for (Object item : event.getItems()) {
			System.out.println(((Tipoimovel) item));
			String f = ((Tipoimovel) item).getTipNome();
			System.out.println("Tipo: " + f);
			builder.append(f + "<br />");
		}

		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Items Transferred");
		msg.setDetail(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public DualListModel<String> getDualTipoImovel() {
		return dualTipoImovel;
	}

	public void setDualTipoImovel(DualListModel<String> dualTipoImovel) {
		this.dualTipoImovel = dualTipoImovel;
	}

	public DualListModel<String> getDualBairro() {
		return dualBairro;
	}

	public void setDualBairro(DualListModel<String> dualBairro) {
		this.dualBairro = dualBairro;
	}

	public List<Bairro> getListaBairros() {
		return listaBairros;
	}

	public void setListaBairros(List<Bairro> listaBairros) {
		this.listaBairros = listaBairros;
	}

	public List<Cidade> getListaCidades() {
		return listaCidades;
	}

	public void setListaCidades(List<Cidade> listaCidades) {
		this.listaCidades = listaCidades;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public List<Tipoimovel> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<Tipoimovel> listaTipos) {
		this.listaTipos = listaTipos;
	}

	public Integer getQuartoDe() {
		return quartoDe;
	}

	public void setQuartoDe(Integer quartoDe) {
		this.quartoDe = quartoDe;
	}

	public Integer getQuartoAte() {
		return quartoAte;
	}

	public void setQuartoAte(Integer quartoAte) {
		this.quartoAte = quartoAte;
	}

	public List<Caracteristicas> getListaDetalhes() {
		return listaCaracteriscas;
	}

	public void setListaDetalhes(List<Caracteristicas> listaCaracteriscas) {
		this.listaCaracteriscas = listaCaracteriscas;
	}

	@SuppressWarnings("unused")
	public String pesquisaLocacao() {
		restaurarLista();// restaurar lista de tipos e detalhes
		this.listImoveisPesquisadosLocacao = new ArrayList<Imovel>();
		if(this.qtdQuartos==null){
			this.qtdQuartos=new Integer(0);
		}
		int cont = (this.listaCaracteriscas.size() + this.listaBairros.size()
				+ this.listaCidades.size() + this.listaTipos.size()
				+this.qtdQuartos+this.listaRegioes.size()
				+this.quartoDe
				+this.quartoAte);
		if ((cont == 0)) {
			Imovel i = new Imovel();
			Tipoimovel tipo = new Tipoimovel();
			tipo.setTipNome("Não há iténs selecinados para pesquisa");
			i.setTipoimovel(tipo);
			this.listImoveisPesquisadosLocacao.add(i);

			this.listaBairros = new ArrayList<Bairro>();
			this.listaCidades = new ArrayList<Cidade>();
			this.listaCaracteriscas = new ArrayList<Caracteristicas>();
			this.listaRegioes = new ArrayList<Regiao>();
			this.listaTipos = new ArrayList<Tipoimovel>();
			this.listImoveisPesquisadosVenda = new ArrayList<Imovel>();
			this.qtdQuartos =new Integer(0);
			this.valorMaximo=new String("");
			this.valorMinimo=new String("");
			return null;
		}

		if (!this.valorMaximo.equals("")) {
			if (!isInt(this.valorMaximo)) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_WARN,
										"Atenção",
										"o valor máximo não pode ser texto! Altere para um valor aceitavel"));
				return null;
			}
		}
		if (!this.valorMinimo.equals("")) {
			if (!isInt(this.valorMinimo)) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_WARN,
										"Atenção",
										"o valor minimo não pode ser texto! Altere para um valor aceitavel"));
				return null;
			}
		}
		PesquisaRN pesquisaRN = new PesquisaRN();
		List<Imovel> listaImoveisEncontrados=pesquisaRN.locacao(this.listaTipos, this.listaBairros,
				this.listaCidades, this.listaCaracteriscas, this.valorMinimo,
				this.valorMaximo, this.qtdQuartos, this.listaRegioes, this.quartoDe, this.quartoAte);
		for (Imovel i : listaImoveisEncontrados) {
			if ((i.getImoLocacao()!=null)&&(i.getImoLocacao().equals(true))) {
				this.listImoveisPesquisadosLocacao.add(i);
				this.visivelBotaoRegistraPesquisa=false;
			}
		}

		int x = this.listImoveisPesquisadosLocacao.size();
		if (x == 0) {
			Imovel i = new Imovel();
			Tipoimovel tipo = new Tipoimovel();
			tipo.setTipNome("Não há imóvel encontrado");
			i.setTipoimovel(tipo);
			this.listImoveisPesquisadosLocacao.add(i);
			//botão cadastrar imovel desejado visivel
			this.visivelBotaoRegistraPesquisa=true;
		}
		//tratamento do opção de registro de um imovel desejado pelo cliente
		this.listaBairrosDesejado=listaBairros;
		this.listaCidadesDesejado=listaCidades;
		this.listaCaracteriscasDesejado=listaCaracteriscas;
		this.listaTiposDesejado=listaTipos;
		this.imovelDesejado.setImoDesQuartosDe(this.quartoDe);
		this.imovelDesejado.setImoDesQuartosAte(this.quartoAte);
		if(!this.valorMaximo.equals("")){
			Double val=Double.parseDouble(this.valorMaximo);
			//this.imovelDesejado.setImoDesValorMaximo(val);
		}
		
		if(!this.valorMinimo.equals("")){
			Double val=Double.parseDouble(this.valorMinimo);
		//this.imovelDesejado.setImoDesValorMinimo(val);
		}
		if(this.qtdQuartos!=0){
			this.listaCaracteriscasDesejado.add(new CaracteriscasRN().buscarPorCaracteriscas("QUARTOS"));
			this.qtdQuartosDesejado=qtdQuartos;
		}
		
		  this.listaBairros = new ArrayList<Bairro>(); this.listaCidades = new
		  ArrayList<Cidade>(); this.listaCaracteriscas = new
		  ArrayList<Caracteristicas>(); this.listaTipos = new
		  ArrayList<Tipoimovel>();
		 
		 this.listaBairros = new ArrayList<Bairro>();
			this.listaCidades = new ArrayList<Cidade>();
			this.listaCaracteriscas = new ArrayList<Caracteristicas>();
			this.listaRegioes = new ArrayList<Regiao>();
			this.listaTipos = new ArrayList<Tipoimovel>();
			this.listImoveisPesquisadosVenda = new ArrayList<Imovel>();
			this.qtdQuartos =new Integer(0);
			this.valorMaximo=new String("");
			this.valorMinimo=new String("");
			this.quartoDe=new Integer(0);
			this.quartoAte=new Integer(0);
		return null;
	}

	public void restaurarLista() {
		if (this.listaTipos.size() != 0) {
			List<Tipoimovel> lTipo = new ArrayList<Tipoimovel>();
			lTipo = this.listaTipos;
			this.listaTipos = new ArrayList<Tipoimovel>();
			TipoImovelRN imovelRN = new TipoImovelRN();
			/*
			 * for(int i=1;i<=lTipo.size();i++){ Tipoimovel x =
			 * imovelRN.carregar(i); this.listaTipos.add(x); }
			 */

			for (Object t : lTipo) {
				int d = Integer.parseInt(t.toString());
				Tipoimovel i = imovelRN.carregar(d);
				this.listaTipos.add(i);
			}
		}
		if (this.listaCaracteriscas.size() != 0) {
			List<Caracteristicas> lDet = new ArrayList<Caracteristicas>();
			lDet = this.listaCaracteriscas;
			this.listaCaracteriscas = new ArrayList<Caracteristicas>();
			CaracteriscasRN detalhesImovelRN = new CaracteriscasRN();
			for (Object t : lDet) {
				int d = Integer.parseInt(t.toString());
				Caracteristicas i = detalhesImovelRN.carregar(d);
				this.listaCaracteriscas.add(i);
			}
		}

		if (this.listaRegioes.size() != 0) {
			List<Regiao> lRegiao = new ArrayList<Regiao>();
			lRegiao = this.listaRegioes;
			this.listaRegioes = new ArrayList<Regiao>();
			RegiaoRN regiaoRN = new RegiaoRN();
			for (Object t : lRegiao) {
				int d = Integer.parseInt(t.toString());
				Regiao i = regiaoRN.carregar(d);
				this.listaRegioes.add(i);
			}
		}

	}

	public String pesquisaVenda() {
		restaurarLista();// restaurar lista de tipos e detalhes
		this.listImoveisPesquisadosVenda = new ArrayList<Imovel>();
		int cont = (this.listaCaracteriscas.size() + this.listaBairros.size()
				+ this.listaCidades.size() + this.listaTipos.size()
				+ this.listaRegioes.size() + this.qtdQuartos);
		if (cont == 0) {
			Imovel i = new Imovel();
			Tipoimovel tipo = new Tipoimovel();
			tipo.setTipNome("Não há iténs selecinados para pesquisa");
			i.setTipoimovel(tipo);
			this.listImoveisPesquisadosVenda.add(i);
			return null;
		}
		if (!this.valorMaximo.equals("")) {
			if (!isInt(this.valorMaximo)) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_WARN,
										"Atenção",
										"o valor máximo não pode ser texto! Altere para um valor aceitavel"));
				return null;
			}
		}
		if (!this.valorMinimo.equals("")) {
			if (!isInt(this.valorMinimo)) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_WARN,
										"Atenção",
										"o valor minimo não pode ser texto! Altere para um valor aceitavel"));
				return null;
			}
		}

		PesquisaRN pesquisaRN = new PesquisaRN();
		List<Imovel> listaImoveisEncontrados=pesquisaRN.locacao(this.listaTipos, this.listaBairros,
				this.listaCidades, this.listaCaracteriscas,this.valorMinimo, 
				this.valorMaximo, this.qtdQuartos, this.listaRegioes, this.quartoDe, this.quartoAte);
		for (Imovel i : listaImoveisEncontrados) {
			if ((i.getImoVenda()!=null)&&(i.getImoVenda().equals(true))) {
				this.listImoveisPesquisadosVenda.add(i);
			}
		}

		int x = this.listImoveisPesquisadosVenda.size();
		if (x == 0) {
			Imovel i = new Imovel();
			Tipoimovel tipo = new Tipoimovel();
			tipo.setTipNome("Não há imóvel encontrado");
			i.setTipoimovel(tipo);
			this.listImoveisPesquisadosVenda.add(i);
		}

		
		  this.listaBairros = new ArrayList<Bairro>(); this.listaCidades = new
		  ArrayList<Cidade>(); this.listaCaracteriscas = new
		  ArrayList<Caracteristicas>(); this.listaTipos = new
		  ArrayList<Tipoimovel>();
		 
		this.listImoveisPesquisadosLocacao = new ArrayList<Imovel>();
		 this.listaRegioes=new ArrayList<Regiao>();
		 this.qtdQuartos=0;
		 this.valorMaximo="";
		 this.valorMinimo="";
		return null;
	}

	public List<Imovel> getListImoveisPesquisadosLocacao() {
		return listImoveisPesquisadosLocacao;
	}

	public void setListImoveisPesquisadosLocacao(
			List<Imovel> listImoveisPesquisadosLocacao) {
		this.listImoveisPesquisadosLocacao = listImoveisPesquisadosLocacao;
	}

	public List<Imovel> getListImoveisPesquisadosVenda() {
		return listImoveisPesquisadosVenda;
	}

	public void setListImoveisPesquisadosVenda(
			List<Imovel> listImoveisPesquisadosVenda) {
		this.listImoveisPesquisadosVenda = listImoveisPesquisadosVenda;
	}

	public String getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(String valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public String getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(String valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public boolean isInt(String str) {
		boolean isInteger = true;
		int size = str.length();
		for (int i = 0; (i < size) && isInteger; i++) {
			// Para caracter individual, Java tem um método para avaliar
			isInteger = Character.isDigit(str.charAt(i));
		}
		return isInteger;
	}

	public List<Caracteristicas> getListaDetalhesSemQuarto() {
		if (this.listaCaracteriscasSemQuarto == null) {
			CaracteriscasRN detalheImovelRN = new CaracteriscasRN();
			List<Caracteristicas> detalhesSemQuarto = detalheImovelRN.listar();
			this.listaCaracteriscasSemQuarto = new ArrayList<Caracteristicas>();
			for (Caracteristicas d : detalhesSemQuarto) {
				if (!d.getCarNome().equals("QUARTOS")) {
					this.listaCaracteriscasSemQuarto.add(d);
				}
			}
		}
		return listaCaracteriscasSemQuarto;
	}

	public void setListaDetalhesSemQuarto(
			List<Caracteristicas> listaCaracteriscasSemQuarto) {
		this.listaCaracteriscasSemQuarto = listaCaracteriscasSemQuarto;
	}

	public Integer getQtdQuartos() {
		return qtdQuartos;
	}

	public void setQtdQuartos(Integer qtdQuartos) {
		this.qtdQuartos = qtdQuartos;
	}

	public boolean isRegiaoBairro() {
		return regiaoBairro;
	}

	public void setRegiaoBairro(boolean regiaoBairro) {
		this.regiaoBairro = regiaoBairro;
	}

	public List<Regiao> getListaRegioes() {
		return listaRegioes;
	}

	public void setListaRegioes(List<Regiao> listaRegioes) {
		this.listaRegioes = listaRegioes;
	}

	public void mudarTap() {
		this.listaBairros = new ArrayList<Bairro>();
		this.listaCidades = new ArrayList<Cidade>();
		this.listaCaracteriscas = new ArrayList<Caracteristicas>();
		this.listaRegioes = new ArrayList<Regiao>();
		this.listaTipos = new ArrayList<Tipoimovel>();
		this.listImoveisPesquisadosLocacao = new ArrayList<Imovel>();
		this.listImoveisPesquisadosVenda = new ArrayList<Imovel>();
		this.qtdQuartos =new Integer(0);
		this.valorMaximo=new String("");
		this.valorMinimo=new String("");
	}

	public ArrayList<Telefone> getListaTel() {
		return listaTel;
	}

	public void setListaTel(ArrayList<Telefone> listaTel) {
		this.listaTel = listaTel;
	}

	public EnderecoImovelDesejado getEnderecoImovelDesejado() {
		return enderecoImovelDesejado;
	}

	public void setEnderecoImovelDesejado(
			EnderecoImovelDesejado enderecoImovelDesejado) {
		this.enderecoImovelDesejado = enderecoImovelDesejado;
	}

	public List<Email> getListaEmail() {
		return listaEmail;
	}

	public void setListaEmail(List<Email> listaEmail) {
		this.listaEmail = listaEmail;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public ImovelDesejado getImovelDesejado() {
		return imovelDesejado;
	}

	public void setImovelDesejado(ImovelDesejado imovelDesejado) {
		this.imovelDesejado = imovelDesejado;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}



	public List<Bairro> getListaBairrosDesejado() {
		return listaBairrosDesejado;
	}

	public void setListaBairrosDesejado(List<Bairro> listaBairrosDesejado) {
		this.listaBairrosDesejado = listaBairrosDesejado;
	}

	public List<Cidade> getListaCidadesDesejado() {
		return listaCidadesDesejado;
	}

	public void setListaCidadesDesejado(List<Cidade> listaCidadesDesejado) {
		this.listaCidadesDesejado = listaCidadesDesejado;
	}

	public List<Tipoimovel> getListaTiposDesejado() {
		return listaTiposDesejado;
	}

	public void setListaTiposDesejado(List<Tipoimovel> listaTiposDesejado) {
		this.listaTiposDesejado = listaTiposDesejado;
	}

	public List<Caracteristicas> getListaDetalhesDesejado() {
		return listaCaracteriscasDesejado;
	}

	public void setListaDetalhesDesejado(List<Caracteristicas> listaCaracteriscasDesejado) {
		this.listaCaracteriscasDesejado = listaCaracteriscasDesejado;
	}

	public List<Caracteristicas> getListaDetalhesSemQuartoDesejado() {
		return listaCaracteriscasSemQuartoDesejado;
	}

	public void setListaDetalhesSemQuartoDesejado(
			List<Caracteristicas> listaCaracteriscasSemQuartoDesejado) {
		this.listaCaracteriscasSemQuartoDesejado = listaCaracteriscasSemQuartoDesejado;
	}

	public Integer getQtdQuartosDesejado() {
		return qtdQuartosDesejado;
	}

	public void setQtdQuartosDesejado(Integer qtdQuartosDesejado) {
		this.qtdQuartosDesejado = qtdQuartosDesejado;
	}

	public boolean isVisivelBotaoRegistraPesquisa() {
		return visivelBotaoRegistraPesquisa;
	}

	public void setVisivelBotaoRegistraPesquisa(boolean visivelBotaoRegistraPesquisa) {
		this.visivelBotaoRegistraPesquisa = visivelBotaoRegistraPesquisa;
	}

	public Ramoatuacao getRamo() {
		return ramo;
	}

	public void setRamo(Ramoatuacao ramo) {
		this.ramo = ramo;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void addTel(){
		String tel = telefone.getTelNumero();
		tel = tel.replaceAll("[.-]", "");
		tel = tel.replaceAll("[)]", "");
		tel = tel.replaceAll("[(]", "");
		TelefoneRN telefoneRN=new TelefoneRN();
		Telefone telefoneTeste=telefoneRN.buscarPorTelefone(tel);
		if(telefoneTeste==null){
		this.listaTel.add(telefone);
		this.telefone = new Telefone();
		return;}
		this.telefone = new Telefone();
		Pessoa pes=new PessoaRN().carregar(telefoneTeste.getPessoa().getPesId());
		FacesContext
		.getCurrentInstance()
		.addMessage(
				null,
				new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"Cadastro não efetuado - Telefone já existente !",
						"Telefone pertencente ao Sr. "+pes.getPesNome()+" Codigo: "+pes.getPesId()));
	}
	
	public void removerEmail(Email e){
		if(listaEmail!=null){
		this.listaEmail.remove(e);
		pessoa.getEmails().remove(e);
		this.email=new Email();}
	}
public void addEmail(){
	EmailRN emailRN=new EmailRN();
	Email emailTeste=emailRN.buscarPorEmail(email.getEmaEndereco());
	if(emailTeste==null){
	this.listaEmail.add(email);
	this.email = new Email();
	return;}
	this.email = new Email();
	Pessoa pes=new PessoaRN().carregar(emailTeste.getPessoa().getPesId());
	FacesContext
	.getCurrentInstance()
	.addMessage(
			null,
			new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					"Cadastro não efetuado - Email já existente !",
					"Email pertencente ao Sr. "+pes.getPesNome()+" Codigo: "+pes.getPesId()));
	}
	
	public void removerTel(Telefone t){

	//	System.out.println(t.getTelNumero());
		if(listaTel!=null){
		this.listaTel.remove(t);
		pessoa.getTelefones().remove(t);
		this.telefone=new Telefone();}
	}
	public void salvar(){
		Funcionario funcionario=new Funcionario();		
		funcionario.setPesId(1);
		this.pessoa.setFuncionario(funcionario);
		
		this.pessoa.setRamoatuacao(this.ramo);
		
		this.pessoa.setPesFiador(false);
		this.pessoa.setPesFuncionario(false);
		this.pessoa.setPesInquilino(false);
		this.pessoa.setPesProprietario(false);
		this.pessoa.setPesPreCadastro(true);
		
		PessoaRN pessoaRN = new PessoaRN();
		try{
			pessoaRN.salvar(this.pessoa);
		} catch (UtilException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return ;
		}

		EmailRN emailRN = new EmailRN();
		TelefoneRN telefoneRN = new TelefoneRN();
		for(Telefone f: listaTel){
			String tel = f.getTelNumero();
			tel = tel.replaceAll("[.-]", "");
			tel = tel.replaceAll("[)]", "");
			tel = tel.replaceAll("[(]", "");
			f.setTelNumero(tel);
			/*	telefone.setPessoa(pessoa);
			this.telefone.setPessoa(pessoa);*/
			f.setPessoa(pessoa);
			telefoneRN.salvar(f);
			//pessoaRN.salvar(this.pessoa);
			}
		
		for(Email e: listaEmail){
		e.setPessoa(pessoa);
		emailRN.salvar(e);
		//pessoaRN.salvar(this.pessoa);
		}
		
		if(this.listaTiposDesejado.size()==0){
			this.listaTiposDesejado=new TipoImovelRN().listar();
		}
	//	Integer areaImovel=this.imovelDesejado.getImoDesAreaImovel();
	//	Integer areaTerreno=this.imovelDesejado.getImoDesAreaTerreno();
	//	Double valCondominio=this.imovelDesejado.getImoDesValorCondominio();
		Date dataConstrucao=this.imovelDesejado.getImoDesDataConstrucao();
		String obs=this.imovelDesejado.getImoDesObservacao();
		Integer de=this.imovelDesejado.getImoDesQuartosDe();
		Integer ate=this.imovelDesejado.getImoDesQuartosAte();
		
		this.enderecoImovelDesejado.setBairro(bairro);
		this.enderecoImovelDesejado.setCidade(cidade);
		
		ImovelDesejadoRN imovelDesejadoRN=new ImovelDesejadoRN();
		CaracteristicasImovelDesejadoRN complementoRN=new CaracteristicasImovelDesejadoRN();
	/*	String rua=this.enderecoImovelDesejado.getEndImoDesNome();
		int numero=this.enderecoImovelDesejado.getEndImoDesNumero();
		String cep=this.enderecoImovelDesejado.getEndImoDesCep();
		Character zona=this.enderecoImovelDesejado.getEndImoDesZona();*/
		
		
	//	double valMinimo=this.imovelDesejado.getImoDesValorMinimo();
	//double valMaximo=this.imovelDesejado.getImoDesValorMaximo();
		
		
		EnderecoImovelDesejadoRN enderecoImovelDesejadoRN=new EnderecoImovelDesejadoRN();
		
		enderecoImovelDesejadoRN.salvar(this.enderecoImovelDesejado);
		
		for(Tipoimovel t: this.listaTiposDesejado){
		this.imovelDesejado.setTipoimovel(t);	
		this.imovelDesejado.setPessoa(this.pessoa);
		this.imovelDesejado.setImoDesDataCadastro(new Date());
		this.imovelDesejado.setImoDesLocacao(true);
		this.imovelDesejado.setImoDesVenda(false);
		
		this.imovelDesejado.setEnderecoImovelDesejado(this.enderecoImovelDesejado);
		
		imovelDesejadoRN.salvar(this.imovelDesejado);
		
		for (Caracteristicas localDetalhe : this.listaCaracteriscasDesejado) {
			CaracteristicasImovelDesejadoId  localComplementoImovelId = new CaracteristicasImovelDesejadoId();				
			localComplementoImovelId.setImoDesId(this.imovelDesejado.getImoDesId());
			localComplementoImovelId.setCarId(localDetalhe.getCarId());				
			CaracteristicasImovelDesejado localComplementoImovel = new CaracteristicasImovelDesejado();
			localComplementoImovel.setCaracteristicas(localDetalhe);
			localComplementoImovel.setImovelDesejado(this.imovelDesejado);
			localComplementoImovel.setId(localComplementoImovelId);
			complementoRN.salvar(localComplementoImovel);
	}

		
		this.imovelDesejado=new ImovelDesejado();
	//	this.imovelDesejado.setImoDesValorMaximo(valMaximo);
	//	this.imovelDesejado.setImoDesValorMinimo(valMinimo);
		this.imovelDesejado.setImoDesQuartosDe(de);
		this.imovelDesejado.setImoDesQuartosAte(ate);
	//	this.imovelDesejado.setImoDesAreaTerreno(areaTerreno);
	//	this.imovelDesejado.setImoDesAreaImovel(areaImovel);
	//	this.imovelDesejado.setImoDesValorCondominio(valCondominio);
		this.imovelDesejado.setImoDesDataConstrucao(dataConstrucao);
		this.imovelDesejado.setImoDesObservacao(obs);
		this.imovelDesejado.setEnderecoImovelDesejado(this.enderecoImovelDesejado);
		}
	//	enderecoImovelDesejadoRN.salvar(enderecoImovelDesejadoUnico);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Salvo com sucesso"));
		
		this.imovelDesejado=new ImovelDesejado();
		this.listaBairrosDesejado=new ArrayList<Bairro>();
		this.listaCidadesDesejado=new ArrayList<Cidade>();
		this.listaCaracteriscasDesejado=new ArrayList<Caracteristicas>();
		this.listaCaracteriscasSemQuartoDesejado=new ArrayList<Caracteristicas>();
		this.listaTel=new ArrayList<Telefone>();
		this.listaEmail=new ArrayList<Email>();
		this.valorMaximo=new String();
		this.valorMinimo=new String();
		this.listaTiposDesejado=new ArrayList<Tipoimovel>();
		this.qtdQuartosDesejado=0;
		this.visivelBotaoRegistraPesquisa=false;
		this.ramo=new Ramoatuacao();
		this.enderecoImovelDesejado=new EnderecoImovelDesejado();
		this.listaTipos=new ArrayList<Tipoimovel>();
		this.enderecoImovelDesejado=new EnderecoImovelDesejado();
		
		
	}
}
