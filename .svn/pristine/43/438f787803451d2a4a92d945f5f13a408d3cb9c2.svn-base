package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.SeguroIncendioImovelResidencial;

public class SeguroIncendioImovelResidencialDAOHibernate implements
		SeguroIncendioImovelResidencialDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(SeguroIncendioImovelResidencial seguroImovelResidencial) {
		this.session.save(seguroImovelResidencial);

	}

	@Override
	public void atualizar(SeguroIncendioImovelResidencial seguroImovelResidencial) {
		this.session.update(seguroImovelResidencial);

	}

	@Override
	public void excluir(SeguroIncendioImovelResidencial seguroImovelResidencial) {
		this.session.delete(seguroImovelResidencial);

	}

	@Override
	public SeguroIncendioImovelResidencial carregar(Integer segImoResId) {
		return (SeguroIncendioImovelResidencial)this.session.get(SeguroIncendioImovelResidencial.class, segImoResId);
	}

	@Override
	public SeguroIncendioImovelResidencial buscarPorSeguroImovelResidencial(
			String seguroImovelResidencial) {
		String hql="select b from SeguroIncendioImovelResidencial b where b.segIncImoResValorVenal = :seguroImovelResidencial";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("seguroImovelResidencial", seguroImovelResidencial);
		return (SeguroIncendioImovelResidencial) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SeguroIncendioImovelResidencial> listar() {
		Criteria crit= this.session.createCriteria(SeguroIncendioImovelResidencial.class);
		crit.addOrder(Order.asc("segIncImoResValorVenal"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public double bucarValorSeguroPorValorVenal(double valorVenal) {
		Criteria crit= this.session.createCriteria(SeguroIncendioImovelResidencial.class);
		crit.addOrder(Order.asc("segIncImoResValorVenal"));
		//ge() = maior ou igual que: http://blog.camilolopes.com.br/criteria-hibernate-parte-ii/
		crit.add(Restrictions.ge("segIncImoResValorVenal",valorVenal));
		List<SeguroIncendioImovelResidencial> lista= crit.list();
		if(lista.size()==0){
			return 0.0;
		}
		SeguroIncendioImovelResidencial seguroIncendioImovelComercial=lista.get(0);
		if(seguroIncendioImovelComercial==null){
			return 0.0;
		}
		return seguroIncendioImovelComercial.getSegIncImoResTotalCusto();
	}

}
