package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.ParametroReferenciaDAO;
import br.com.locadora.modelo.ParametroReferencia;
import br.com.locadora.util.DAOFactory;

public class ParametroReferenciaRN {
	private ParametroReferenciaDAO parametroReferenciaDAO;
	public ParametroReferenciaRN() {
		this.parametroReferenciaDAO = DAOFactory.criarParametroReferenciaDAO();
	}
	public ParametroReferencia carregar(Integer parametroReferenciaId) {
		return this.parametroReferenciaDAO.carregar(parametroReferenciaId);
	}

	public ParametroReferencia buscarPorParametroReferencia(String parametroReferencia) {
		return this.parametroReferenciaDAO.buscarPorParametroReferencia(parametroReferencia);
	}

	public void salvar(ParametroReferencia parametroReferencia) {
		Integer codigo = parametroReferencia.getParRefId();
		if (codigo == null || codigo == 0) {
			this.parametroReferenciaDAO.salvar(parametroReferencia);
		} else {
			this.parametroReferenciaDAO.atualizar(parametroReferencia);
		}

	}

	public void excluir(ParametroReferencia parametroReferencia) {
		this.parametroReferenciaDAO.excluir(parametroReferencia);
	}

	public List<ParametroReferencia> listar() {
		return this.parametroReferenciaDAO.listar();
	}
	
}
