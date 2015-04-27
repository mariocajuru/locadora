package br.com.locadora.rn;


import java.util.List;

import br.com.locadora.dao.CaracteristicasImovelDesejadoDAO;
import br.com.locadora.modelo.CaracteristicasImovelDesejado;
import br.com.locadora.util.DAOFactory;

public class CaracteristicasImovelDesejadoRN {
	private CaracteristicasImovelDesejadoDAO  caracteristicasImovelDesejadoDAO;
	public CaracteristicasImovelDesejadoRN() {
	this.caracteristicasImovelDesejadoDAO=DAOFactory.criarCaracteristicasImovelDesejadoDAO();
	}
	public CaracteristicasImovelDesejado carregar(Integer car_des_Id) {
		return this.caracteristicasImovelDesejadoDAO.carregar(car_des_Id);

	}

	public CaracteristicasImovelDesejado buscarPorImovelDesejado(String caracteristicasImovelDesejado) {
		return this.caracteristicasImovelDesejadoDAO.buscarPorCaracteristicasImovelDesejado(caracteristicasImovelDesejado);
	}

	public void salvar(CaracteristicasImovelDesejado caracteristicasImovelDesejado) {
		/*Integer codigo = complementoImovelDesejado.getId().getDetId();
		if (codigo == null || codigo == 0) {*/
			this.caracteristicasImovelDesejadoDAO.salvar(caracteristicasImovelDesejado);
	/*	} else {
			this.caracteristicasImovelDesejadoDAO.atualizar(complementoImovelDesejado);
		}*/

	}

	public void excluir(CaracteristicasImovelDesejado  caracteristicasImovelDesejado) {
		this.caracteristicasImovelDesejadoDAO.excluir(caracteristicasImovelDesejado);
	}

	public List<CaracteristicasImovelDesejado> listar() {
		return this.caracteristicasImovelDesejadoDAO.listar();
	}
	}
