package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.SeguroIncendioDAO;
import br.com.locadora.modelo.SeguroIncendio;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class SeguroIncendioRN {

	private SeguroIncendioDAO seguroIncendioDAO;
	public SeguroIncendioRN() {
		this.seguroIncendioDAO = DAOFactory.criarSeguroIncendioDAO();
	}
	public SeguroIncendio carregar(Integer segIncId) {
		return this.seguroIncendioDAO.carregar(segIncId);

	}

	public SeguroIncendio buscarPorSeguroIncendio(String seguroIncendio) {
		return this.seguroIncendioDAO.buscarPorSeguroIncendio(seguroIncendio);
	}

	public void salvar(SeguroIncendio seguroIncendio) {
		Integer codigo = seguroIncendio.getSegIncId();
		if (codigo == null || codigo == 0) {
			this.seguroIncendioDAO.salvar(seguroIncendio);
		} else {
			this.seguroIncendioDAO.atualizar(seguroIncendio);
		}

	}

	public boolean excluir(SeguroIncendio seguroIncendio) {
		if(this.seguroIncendioDAO.dependecias(seguroIncendio)){
		this.seguroIncendioDAO.excluir(seguroIncendio);
		return true;
	}else{
		try {
			throw new UtilException("Erro ao excluir. Esse Seguro Incendio tem ligações com uma locação e não pode ser deletado.");
		} catch (UtilException e) {
				return false;
		}
	}
	}

	public List<SeguroIncendio> listar() {
		return this.seguroIncendioDAO.listar();
	}

}
