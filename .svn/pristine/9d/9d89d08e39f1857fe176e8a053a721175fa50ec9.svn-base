package br.com.locadora.rn;

import java.util.Date;
import java.util.List;

import br.com.locadora.dao.EnderecoDAO;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.util.DAOFactory;

public class EnderecoRN {
	private EnderecoDAO enderecoDAO;

	public EnderecoRN() {
		this.enderecoDAO = DAOFactory.criarEnderecoDAO();
	}

	public Endereco carregar(Integer endId) {
		return this.enderecoDAO.carregar(endId);

	}

	public Endereco buscarPorEndereco(String endereco) {
		return this.enderecoDAO.buscarPorEndereco(endereco);
	}

	public void salvar(Endereco endereco) {
		Integer codigo = endereco.getEndId();
		if (codigo == null || codigo == 0) {
			endereco.setEndDataCadastro(new Date());
			this.enderecoDAO.salvar(endereco);
		} else {
			endereco.setEndDataCadastro(new Date());
			this.enderecoDAO.atualizar(endereco);
		}

	}

	public void excluir(Endereco endereco) {
		this.enderecoDAO.excluir(endereco);
	}

	public List<Endereco> listar() {
		return this.enderecoDAO.listar();
	}
}
