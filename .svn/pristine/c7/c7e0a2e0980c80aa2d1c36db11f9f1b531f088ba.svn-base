package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.SeguroIncendioImovelResidencialDAO;
import br.com.locadora.modelo.SeguroIncendioImovelResidencial;
import br.com.locadora.util.DAOFactory;

public class SeguroIncendioImovelResidencialRN {
	private SeguroIncendioImovelResidencialDAO seguroImovelResidencialDAO;
	public SeguroIncendioImovelResidencialRN() {
		this.seguroImovelResidencialDAO = DAOFactory.criarSeguroImovelResidencialDAO();
	}
	public SeguroIncendioImovelResidencial carregar(Integer segImoResId) {
		return this.seguroImovelResidencialDAO.carregar(segImoResId);

	}

	public SeguroIncendioImovelResidencial buscarPorSeguroImovelResidencial(String valorVenal) {
		return this.seguroImovelResidencialDAO.buscarPorSeguroImovelResidencial(valorVenal);
	}

	public void salvar(SeguroIncendioImovelResidencial seguroImovelComercial) {
		Integer codigo = seguroImovelComercial.getSegIncImoResId();
		if (codigo == null || codigo == 0) {
			this.seguroImovelResidencialDAO.salvar(seguroImovelComercial);
		} else {
			this.seguroImovelResidencialDAO.atualizar(seguroImovelComercial);
		}

	}

	public void excluir(SeguroIncendioImovelResidencial seguroImovelComercial) {
		this.seguroImovelResidencialDAO.excluir(seguroImovelComercial);
	}

	public List<SeguroIncendioImovelResidencial> listar() {
		return this.seguroImovelResidencialDAO.listar();
	}
	
	public double bucarValorSeguroPorValorVenal(double valorVenal) {
		return this.seguroImovelResidencialDAO.bucarValorSeguroPorValorVenal(valorVenal);
	}
}
