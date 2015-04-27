package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;

import br.com.locadora.modelo.Email;
import br.com.locadora.modelo.Filial;
import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Cidade;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.QuadroDeChaves;
import br.com.locadora.rn.BairroRN;
import br.com.locadora.rn.CidadeRN;
import br.com.locadora.rn.EnderecoRN;
import br.com.locadora.rn.FilialRN;
import br.com.locadora.rn.FuncionarioRN;
import br.com.locadora.rn.QuadroDeChavesRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "filialBean")
@ViewScoped
public class FilialBean  implements Serializable{

	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = -5410553341568302844L;
	@Getter @Setter private Filial sede = new Filial();
	@Getter @Setter private Cidade cidade = new Cidade();
	@Getter @Setter private Bairro bairro = new Bairro();
	@Getter @Setter	private Email email = new Email();
	@Getter @Setter private String cnpj = null;
	@Getter @Setter private Endereco endereco = new Endereco();
	@Getter @Setter private List<Filial> listaSedes;
	@Getter @Setter private boolean alteracao         = false;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();

	public FilialBean() {
		carregarListas();
		carregar();
	}
	public void carregarListas() {
		this.listaSedes=new FilialRN().listar();
	}
	public void carregar() {
		int filialID = this.contextoBean.getParametro("id", -1);
		this.bairro=new Bairro();
		this.cidade=new Cidade();
		this.sede=new Filial();
		this.email=new Email();
		this.cnpj=new String();
		this.endereco=new Endereco();
		if (filialID > 0) {
			FilialRN filialRN=new FilialRN();
			Filial filialCarregado=filialRN.carregar(filialID);			
			if (filialCarregado != null){
				this.sede = filialCarregado;
				this.endereco=new EnderecoRN().carregar(this.sede.getEndereco().getEndId());
				this.cidade=new CidadeRN().carregar(this.sede.getEndereco().getCidade().getCidId());
				this.bairro=new BairroRN().carregar(this.sede.getEndereco().getBairro().getBaiId());
				this.cnpj=this.sede.getFilCnpj();
				//this.email=new EmailRN().carregarFilial(this.sede).get(0);
				this.alteracao=true;
			}
		}
	}


	public void salvar() {
		EnderecoRN endercoRN = new EnderecoRN();
		FilialRN filialRN = new FilialRN();
		
		CidadeRN cidadaRN = new CidadeRN();
		if(cidadaRN.buscarPorCidade(cidade.getCidNome())==null){
		cidadaRN.salvar(this.cidade);}else{
			this.cidade=cidadaRN.buscarPorCidade(cidade.getCidNome());
		}
		
		BairroRN bairroRN=new BairroRN();
		if(bairroRN.buscarPorBairro(bairro.getBaiNome())==null){
			bairroRN.salvar(this.bairro);
		}else{
			this.bairro=bairroRN.buscarPorBairro(this.bairro.getBaiNome());
		}
		
		Filial filTep=null;
		if(this.sede.getFilId()<1){
			filTep=filialRN.buscarPorSede(this.sede.getFilNome());
		}
		if(filTep!=null||(this.sede.getFilId()>0)){
			if(alteracao){
				String cep=endereco.getEndCep();
				cep=cep.replaceAll("[.-]", "");
				
				this.endereco.setEndCep(cep);

				this.endereco.setCidade(cidade);
				this.endereco.setBairro(bairro);

				endereco.setFilial(sede);

				this.sede.setEndereco(endereco);

				String im=sede.getFilIm();
				if(im==""){
				im=im.replaceAll("[./]", "");
				sede.setFilIm(im);
				}
				im=sede.getFilIe();
				if(im==""){
				im=im.replaceAll("[.-]", "");
				sede.setFilIe(im);
				}

				endercoRN.salvar(this.endereco);
				filialRN.salvar(this.sede);	
				carregar();
				carregarListas();
				this.contextoBean.redirecionarParaPagina("admin/filial/consulta.jsf");
				return;			
		}else{
			this.contextoBean.mostrarAviso("Esta filial já existe");
			return;}
		}
		
		String cep=endereco.getEndCep();
		cep=cep.replaceAll("[.-]", "");
		
		this.endereco.setEndCep(cep);

		this.endereco.setCidade(cidade);
		this.endereco.setBairro(bairro);

		endereco.setFilial(sede);

		this.sede.setEndereco(endereco);

		String im=sede.getFilIm();
		if(im==""){
		im=im.replaceAll("[./]", "");
		sede.setFilIm(im);
		}
		im=sede.getFilIe();
		if(im==""){
		im=im.replaceAll("[.-]", "");
		sede.setFilIe(im);
		}

		endercoRN.salvar(this.endereco);

		filialRN.salvar(this.sede);
		
		QuadroDeChaves quadro=new QuadroDeChaves();
		quadro.setFilial(sede);
		
		new QuadroDeChavesRN().salvar(quadro);

		this.contextoBean.redirecionarParaPagina("admin/filial/consulta.jsf");
		carregarListas();
		carregar();
		return ;
	}

	public void excluir() {
		if (this.sede.getFilId() <= 0) {
			this.contextoBean.mostrarAviso("Essa filial ainda não foi salvo!");
			return;
		}
		boolean resposta=new FilialRN().excluir(this.sede);
		if(resposta){
			this.contextoBean.mostrarAviso("Excluído com sucesso");
		return;}else{
			this.contextoBean.mostrarErro("Filial não excluído, essa filial está ligado a um registro.");
			return;
		}
	}

	public static boolean isCnpjValido(String cnpj) {
		if (!cnpj.substring(0, 1).equals("")) {
			try {
				cnpj = cnpj.replace('.', ' ');// onde hï¿½ ponto coloca espaï¿½o
				cnpj = cnpj.replace('/', ' ');// onde hï¿½ barra coloca espaï¿½o
				cnpj = cnpj.replace('-', ' ');// onde hï¿½ traï¿½o coloca espaï¿½o
				cnpj = cnpj.replaceAll(" ", "");// retira espaï¿½o
				int soma = 0, dig;
				String cnpj_calc = cnpj.substring(0, 12);

				if (cnpj.length() != 14) {
					return false;
				}
				char[] chr_cnpj = cnpj.toCharArray();
				/* Primeira parte */
				for (int i = 0; i < 4; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
						soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
					}
				}
				dig = 11 - (soma % 11);
				cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer
						.toString(dig);
				/* Segunda parte */
				soma = 0;
				for (int i = 0; i < 5; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
						soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
					}
				}
				dig = 11 - (soma % 11);
				cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer
						.toString(dig);
				return cnpj.equals(cnpj_calc);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public void confirmaCNPJ(String CNPJ) {
		CNPJ = CNPJ.replaceAll("[.-]", "");
		CNPJ = CNPJ.replaceAll("[/]", "");
		// System.out.println("CNPJ 0 Convertido: "+CNPJ);
		if(CNPJ==""){
			return;
		}
		if (isCnpjValido(CNPJ) == true) {
			if (CNPJ != null && !CNPJ.equals("") && CNPJ.length() == 14) {
				/*
				 * CNPJ = CNPJ.substring(0, 3) + "." + CNPJ.substring(3, 6) +
				 * "." + CNPJ.substring(6, 9) + "-" + CNPJ.substring(9, 11);
				 */
				// System.out.println("CNPJ Convertido: "+CNPJ);
				this.sede.setFilCnpj(CNPJ);
				// System.out.println("PesCpfCnpj: "+pessoa.getPesCpfCnpj());
			}

		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Campos invalidos", "CNPJ invalido !");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
			// System.out.println("CPF invalido !"+CPF);
		}

	}
	
	public String funcionarioPorFilial(int id){
		String nome=new String();
		List<Funcionario> listaFun=new FuncionarioRN().listar();
		for(Funcionario f: listaFun){
			if(f.getFilial().getFilId()==id){
				nome+=f.getPessoa().getPesNome()+". ";
			}
		}
		return nome;
	}
	
}
