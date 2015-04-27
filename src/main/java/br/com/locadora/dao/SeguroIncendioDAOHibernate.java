package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.SeguroIncendio;

public class SeguroIncendioDAOHibernate implements SeguroIncendioDAO {
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(SeguroIncendio seguroIncendio) {
		this.session.save(seguroIncendio);

	}

	@Override
	public void atualizar(SeguroIncendio seguroIncendio) {
		this.session.update(seguroIncendio);

	}

	@Override
	public void excluir(SeguroIncendio seguroIncendio) {
		this.session.delete(seguroIncendio);

	}

	@Override
	public SeguroIncendio carregar(Integer segIncId) {
		return (SeguroIncendio)this.session.get(SeguroIncendio.class, segIncId);
	}

	@Override
	public SeguroIncendio buscarPorSeguroIncendio(String seguroIncendio) {
		String hql="select b from SeguroIncendio b where b.segIncSeguradora = :seguroIncendio";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("seguroIncendio", seguroIncendio);
		return (SeguroIncendio) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SeguroIncendio> listar() {
		Criteria crit= this.session.createCriteria(SeguroIncendio.class);
		crit.addOrder(Order.asc("segIncId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(SeguroIncendio seguroIncendio) {
		  int cont=0;
			
			Criteria crit= this.session.createCriteria(SeguroIncendio.class);		
			crit.createAlias("locacaos", "loc").add(Restrictions.eq("loc.seguroIncendio.segIncId", seguroIncendio.getSegIncId()));
			
			List<SeguroIncendio> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}
			return false;
	}

}
