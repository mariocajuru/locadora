package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.VistoriaDAO;
import br.com.locadora.modelo.Vistoria;
import br.com.locadora.util.DAOFactory;

public class VistoriaRN {
private VistoriaDAO vistoriaDAO;

	public VistoriaRN() {
		this.vistoriaDAO=DAOFactory.criarVistoriaDAO();
	}
	public Vistoria carregar(Integer baiId) {
		return this.vistoriaDAO.carregar(baiId);

	}

	public Vistoria buscarPorBairro(String vistoria) {
		return this.vistoriaDAO.buscarPorVistoria(vistoria);
	}

	public void salvar(Vistoria vistoria) {
		Integer codigo = vistoria.getVisId();
		if (codigo == null || codigo == 0) {
			this.vistoriaDAO.salvar(vistoria);
		} else {
			this.vistoriaDAO.atualizar(vistoria);
		}

	}

	public void excluir(Vistoria vistoria) {
		this.vistoriaDAO.excluir(vistoria);
	}

	public List<Vistoria> listar() {
		return this.vistoriaDAO.listar();
	}
}
