package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.PessoaFiadorDAO;
import br.com.locadora.modelo.PessoaFiador;
import br.com.locadora.util.DAOFactory;

public class PessoaFiadorRN {
	private PessoaFiadorDAO pessoaFiadorDAO;

	public PessoaFiadorRN() {
		this.pessoaFiadorDAO = DAOFactory.criarPessoaFiadorDAO();
	}

	public PessoaFiador carregar(Integer pesFiaId) {
		return this.pessoaFiadorDAO.carregar(pesFiaId);

	}

	public PessoaFiador buscarPorPessoaFiador(String pessoaFiador) {
		return this.pessoaFiadorDAO.buscarPorPessoaFiador(pessoaFiador);
	}

	public void salvar(PessoaFiador pessoaFiador) {
		/*Integer codigo = pessoaFiador.getId();
		if (codigo == null || codigo == 0) {*/
			this.pessoaFiadorDAO.salvar(pessoaFiador);
	/*	} else {
			this.pessoaFiadorDAO.atualizar(pessoaFiador);
		}*/

	}

	public void excluir(PessoaFiador pessoaFiador) {
		this.pessoaFiadorDAO.excluir(pessoaFiador);
	}

	public List<PessoaFiador> listar() {
		return this.pessoaFiadorDAO.listar();
	}
}
