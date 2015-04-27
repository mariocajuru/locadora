package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.ImovelCaracteristicasDAO;


import br.com.locadora.modelo.ImovelCaracteristicas;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.util.DAOFactory;

public class ImovelCaracteristicasRN {
private ImovelCaracteristicasDAO  caracteristicasDAO;
	public ImovelCaracteristicasRN() {
		this.caracteristicasDAO= DAOFactory.criarImovelCaracteristicasDAO();
	}

	public ImovelCaracteristicas carregar(Integer codigo) {
		return this.caracteristicasDAO.carregar(codigo);

	}

	public ImovelCaracteristicas buscarPorImovelCaracteristicas(String imovelCaracteristicas) {
		return this.caracteristicasDAO.buscarPorImovelCaracteristicas(imovelCaracteristicas);
	}

	public void salvar(ImovelCaracteristicas complemento) {
		/*Integer codigo = complemento.getId().getDetId();
		if (codigo == null || codigo == 0) {*/
			this.caracteristicasDAO.salvar(complemento);
		/*} else {
			this.caracteristicasDAO.atualizar(complemento);
		}*/

	}

	public void excluir(ImovelCaracteristicas complemento) {
		this.caracteristicasDAO.excluir(complemento);
	}

	public List<ImovelCaracteristicas> listar() {
		return this.caracteristicasDAO.listar();
	}
	

	public List<ImovelCaracteristicas> listaImovelCaracteristicas(Imovel imovel){
		return this.caracteristicasDAO.listaImovelCaracteristicas(imovel);
	}
	
	public void excluirTudo() {
		this.caracteristicasDAO.excluirTudo();
	}
}


