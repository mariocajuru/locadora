package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.CaracteriscasDAO;
import br.com.locadora.modelo.Caracteristicas;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class CaracteriscasRN {
private CaracteriscasDAO  caracteriscasDAO;
	public CaracteriscasRN() {
		this.caracteriscasDAO= DAOFactory.criarDetalhesImovelDAO();
	}

	public Caracteristicas carregar(Integer codigo) {
		return this.caracteriscasDAO.carregar(codigo);

	}

	public Caracteristicas buscarPorCaracteriscas(String caracteriscas) {
		return this.caracteriscasDAO.buscarPorCaracteriscas(caracteriscas);
	}

	public void salvar(Caracteristicas caracteriscas) {
		Integer codigo = caracteriscas.getCarId();
		if (codigo == null || codigo == 0) {
			this.caracteriscasDAO.salvar(caracteriscas);
		} else {
			this.caracteriscasDAO.atualizar(caracteriscas);
		}

	}

	public boolean excluir(Caracteristicas caracteriscas) {
		if(this.caracteriscasDAO.dependecias(caracteriscas)){
			this.caracteriscasDAO.excluir(caracteriscas);
			return true;
		}else{
			try {
				throw new UtilException("Erro ao excluir. Essa caracteristica tem ligações com outros elementos e não pode ser deletado.");
			} catch (UtilException e) {				
				return false;
			}
		}
	}

	public List<Caracteristicas> listar() {
		return this.caracteriscasDAO.listar();
	}
	public List<Caracteristicas> listarCaracteriscasUnitarias() {
		return this.caracteriscasDAO.listarCaracteriscasUnitarias();
	}

	public List<Caracteristicas> listarCaracteriscasQtd() {
		return this.caracteriscasDAO.listarCaracteriscasQtd();
	}
}

