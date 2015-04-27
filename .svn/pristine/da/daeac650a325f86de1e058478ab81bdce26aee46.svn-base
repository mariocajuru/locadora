package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.locadora.modelo.CaracteristicasImovelDesejado;

public class CaracteristicasImovelDesejadoDAOHibernate implements
		CaracteristicasImovelDesejadoDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(CaracteristicasImovelDesejado  caracteristicasImovelDesejado) {
		this.session.save(caracteristicasImovelDesejado);

	}

	@Override
	public void atualizar(CaracteristicasImovelDesejado caracteristicasImovelDesejado) {
		this.session.update(caracteristicasImovelDesejado);

	}

	@Override
	public void excluir(CaracteristicasImovelDesejado caracteristicasImovelDesejado) {
		this.session.delete(caracteristicasImovelDesejado);

	}

	@Override
	public CaracteristicasImovelDesejado carregar(Integer com_des_Id) {
		return (CaracteristicasImovelDesejado) this.session.get(CaracteristicasImovelDesejado.class, com_des_Id);
	}

	@Override
	public CaracteristicasImovelDesejado buscarPorCaracteristicasImovelDesejado(
			String caracteristicasImovelDesejado) {
		String hql = "select e from CaracteristicasImovelDesejado e where e.emaEndereco = :caracteristicasImovelDesejado";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("caracteristicasImovelDesejado", caracteristicasImovelDesejado);
		return (CaracteristicasImovelDesejado) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CaracteristicasImovelDesejado> listar() {
		return this.session.createCriteria(CaracteristicasImovelDesejado.class).list();
	}

}
