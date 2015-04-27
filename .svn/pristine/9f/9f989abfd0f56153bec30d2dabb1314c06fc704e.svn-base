package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Caracteristicas;


public class CaracteriscasDAOHibernate implements CaracteriscasDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}
	

	@Override
	public void salvar(Caracteristicas caracteriscas) {
		this.session.save(caracteriscas);

	}

	@Override
	public void atualizar(Caracteristicas caracteriscas) {
		this.session.update(caracteriscas);

	}

	@Override
	public void excluir(Caracteristicas caracteriscas) {
		this.session.delete(caracteriscas);

	}

	@Override
	public Caracteristicas carregar(Integer detId) {
		return (Caracteristicas) this.session.get(Caracteristicas.class, detId);
	}

	@Override
	public Caracteristicas buscarPorCaracteriscas(String caracteriscas) {
		String hql = "select e from Caracteristicas e where e.carNome = :caracteriscas";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("caracteriscas", caracteriscas);
		return (Caracteristicas) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Caracteristicas> listar() {
		return this.session.createCriteria(Caracteristicas.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Caracteristicas> listarCaracteriscasUnitarias() {
		Criteria crit = session.createCriteria(Caracteristicas.class);
		crit.add(Restrictions.eq("detUnitario", true));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Caracteristicas> listarCaracteriscasQtd() {
		Criteria crit = session.createCriteria(Caracteristicas.class);
		crit.add(Restrictions.eq("detUnitario", false));
		return crit.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(Caracteristicas caracteristicas) {
		int cont=0;		
		Criteria crit= this.session.createCriteria(Caracteristicas.class);		
		crit.createAlias("imovelCaracteristicases", "imoc").add(Restrictions.eq("imoc.caracteristicas.carId", caracteristicas.getCarId()));
		List<Caracteristicas> lista=crit.list();
		cont=lista.size();

		Criteria crit2= this.session.createCriteria(Caracteristicas.class);		
		crit2.createAlias("caracteristicasImovelDesejados", "imoc2").add(Restrictions.eq("imoc2.caracteristicas.carId", caracteristicas.getCarId()));
		List<Caracteristicas> lista2=crit2.list();
		cont+=lista2.size();

		if(cont==0){
			return true;
		}
		return false;
	}



}
