package br.com.locadora.rn;

import java.util.Date;
import java.util.List;

import javax.el.ELException;

import br.com.locadora.dao.PessoaDAO;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class PessoaRN {
	private PessoaDAO pessoaDAO;
	public PessoaRN() {
		this.pessoaDAO=DAOFactory.criarPessoaDAO();
	}

	public Pessoa carregar(Integer codigo) {
		return this.pessoaDAO.carregar(codigo);

	}

	public Pessoa buscarPorPessoa(String pessoa) {
		return this.pessoaDAO.buscarPorPessoa(pessoa);
	}

	public void salvar(Pessoa pessoa) throws UtilException{
		try {
			Integer codigo = pessoa.getPesId();
			if (codigo == null || codigo == 0) {
				pessoa.setPesDataCadastro(new Date());
				/*// /verifica se o cpf já existe no banco
			PesquisaRN pesquisaRN = new PesquisaRN();
			String cpf_cnpj=pessoa.getPesCpfCnpj();
			if((cpf_cnpj != null)){
			if (pesquisaRN.existeCPF(pessoa.getPesCpfCnpj()) == true) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Cadastro não efetuado - CPF já existente !",
										" Esse CPF já foi cadastrado em nosso banco de dados !"));
				return;
			}}*/
				this.pessoaDAO.salvar(pessoa);
			} else {
				/*// /verifica se o cpf já existe no banco
			PesquisaRN pesquisaRN = new PesquisaRN();
			if(pessoa.getPesCpfCnpj()!=""){
			if (pesquisaRN.existeCPF(pessoa.getPesCpfCnpj()) == true) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Cadastro não efetuado - CPF já existente !",
										" Esse CPF já foi cadastrado em nosso banco de dados !"));
				return;
			}}*/
				pessoa.setPesDataAtualizacao(new Date());
				this.pessoaDAO.atualizar(pessoa);
			}

		} catch (ELException e) {
			throw new UtilException("Erro em TransientObjectException.", e);
		} catch (Exception e) {
			throw new UtilException("Erro ao salvar.", e);
		}

	}

	public void excluir(Pessoa pessoa) {
		this.pessoaDAO.excluir(pessoa);
	}

	public List<Pessoa> listar() {
		return this.pessoaDAO.listar();
	}
	public List<Pessoa> listaProprietarios() {
		return this.pessoaDAO.listaProprietarios();
	}
	public List<Pessoa> carregarListaPessoasPreCadastro() {
		return this.pessoaDAO.carregarListaPessoasPreCadastro();
	}
	public List<Pessoa> carregarListaPessoasNaoPreCadastro() {
		return this.pessoaDAO.carregarListaPessoasNaoPreCadastro();
	}
	
	public Pessoa carregarPessoaPorCPF_CNPJ(Pessoa pessoa) {
		return this.pessoaDAO.carregarPessoaPorCPF_CNPJ(pessoa);
	}
	
	public Pessoa carregarPessoaPorCPF_CNPJ(String cpf_cnpf) {
		return this.pessoaDAO.carregarPessoaPorCPF_CNPJ_Com_Caracteres(cpf_cnpf);
	}
}

