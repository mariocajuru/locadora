package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.SeguroIncendioImovelComercialDAO;
import br.com.locadora.modelo.SeguroIncendioImovelComercial;
import br.com.locadora.util.DAOFactory;

public class SeguroIncendioImovelComercialRN {
	private SeguroIncendioImovelComercialDAO seguroImovelComercialDAO;
	public SeguroIncendioImovelComercialRN() {
		this.seguroImovelComercialDAO = DAOFactory.criarSeguroImovelComercialDAO();
	}
	public SeguroIncendioImovelComercial carregar(Integer segImoComId) {
		return this.seguroImovelComercialDAO.carregar(segImoComId);

	}

	public SeguroIncendioImovelComercial buscarPorSeguroImovelComercial(String valorVenal) {
		return this.seguroImovelComercialDAO.buscarPorSeguroImovelComercial(valorVenal);
	}

	public void salvar(SeguroIncendioImovelComercial seguroImovelComercial) {
		Integer codigo = seguroImovelComercial.getSegIncImoComId();
		if (codigo == null || codigo == 0) {
			this.seguroImovelComercialDAO.salvar(seguroImovelComercial);
		} else {
			this.seguroImovelComercialDAO.atualizar(seguroImovelComercial);
		}

	}

	public void excluir(SeguroIncendioImovelComercial seguroImovelComercial) {
		this.seguroImovelComercialDAO.excluir(seguroImovelComercial);
	}

	public List<SeguroIncendioImovelComercial> listar() {
		return this.seguroImovelComercialDAO.listar();
	}
	
	public double bucarValorSeguroPorValorVenal(double valorVenal) {
		return this.seguroImovelComercialDAO.bucarValorSeguroPorValorVenal(valorVenal);
	}
}
