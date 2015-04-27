package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.SeguroIncendioImovelComercial;

public class SeguroIncendioImovelComercialDAOHibernate implements
		SeguroIncendioImovelComercialDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(SeguroIncendioImovelComercial seguroImovelComercial) {
		this.session.save(seguroImovelComercial);

	}

	@Override
	public void atualizar(SeguroIncendioImovelComercial seguroImovelComercial) {
		this.session.update(seguroImovelComercial);

	}

	@Override
	public void excluir(SeguroIncendioImovelComercial seguroImovelComercial) {
		this.session.delete(seguroImovelComercial);

	}

	@Override
	public SeguroIncendioImovelComercial carregar(Integer segImoComId) {
		return (SeguroIncendioImovelComercial)this.session.get(SeguroIncendioImovelComercial.class, segImoComId);
	}

	@Override
	public SeguroIncendioImovelComercial buscarPorSeguroImovelComercial(
			String seguroImovelComercial) {
		String hql="select b from SeguroIncendioImovelComercial b where b.segIncImoComValorVenal = :seguroImovelComercial";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("seguroImovelComercial", seguroImovelComercial);
		return (SeguroIncendioImovelComercial) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SeguroIncendioImovelComercial> listar() {
		Criteria crit= this.session.createCriteria(SeguroIncendioImovelComercial.class);
		crit.addOrder(Order.asc("segIncImoComValorVenal"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public double bucarValorSeguroPorValorVenal(double valorVenal) {
		Criteria crit= this.session.createCriteria(SeguroIncendioImovelComercial.class);
		crit.addOrder(Order.asc("segIncImoComValorVenal"));
		//ge() = maior ou igual que: http://blog.camilolopes.com.br/criteria-hibernate-parte-ii/
		crit.add(Restrictions.ge("segIncImoComValorVenal",valorVenal));
		List<SeguroIncendioImovelComercial> lista= crit.list();
		if(lista.size()==0){
			return 0.0;
		}
		SeguroIncendioImovelComercial seguroIncendioImovelComercial=lista.get(0);
		if(seguroIncendioImovelComercial==null){
			return 0.0;
		}
		return seguroIncendioImovelComercial.getSegIncImoComTotalCusto();
	}

}
