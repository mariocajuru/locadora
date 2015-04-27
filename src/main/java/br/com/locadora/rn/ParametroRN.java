package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.ParametroDAO;
import br.com.locadora.modelo.Parametro;
import br.com.locadora.util.DAOFactory;

public class ParametroRN {
	private ParametroDAO parametroDAO;
	public ParametroRN() {
		this.parametroDAO = DAOFactory.criarParametroDAO();
	}
	public Parametro carregar(Integer parametroId) {
		return this.parametroDAO.carregar(parametroId);
	}

	public Parametro buscarPorParametro(String parametro) {
		return this.parametroDAO.buscarPorParametro(parametro);
	}

	public void salvar(Parametro parametro) {
		Integer codigo = parametro.getParId();
		if (codigo == null || codigo == 0) {
			this.parametroDAO.salvar(parametro);
		} else {
			this.parametroDAO.atualizar(parametro);
		}

	}

	public void excluir(Parametro parametro) {
		this.parametroDAO.excluir(parametro);
	}

	public List<Parametro> listar() {
		return this.parametroDAO.listar();
	}
	
}
