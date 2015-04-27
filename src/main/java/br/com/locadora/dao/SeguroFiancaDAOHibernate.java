package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.SeguroFianca;

public class SeguroFiancaDAOHibernate implements SeguroFiancaDAO {
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}


	@Override
	public void salvar(SeguroFianca seguroFianca) {
		this.session.save(seguroFianca);

	}

	@Override
	public void atualizar(SeguroFianca seguroFianca) {
		this.session.update(seguroFianca);

	}

	@Override
	public void excluir(SeguroFianca seguroFianca) {
		this.session.delete(seguroFianca);

	}

	@Override
	public SeguroFianca carregar(Integer segFiaId) {
		return (SeguroFianca)this.session.get(SeguroFianca.class, segFiaId);
	}

	@Override
	public SeguroFianca buscarPorSeguroFianca(String seguroFianca) {
		String hql="select b from SeguroFianca b where b.segFiaSeguradora = :seguroFianca";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("seguroFianca", seguroFianca);
		return (SeguroFianca) consulta.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SeguroFianca> listar() {
		Criteria crit= this.session.createCriteria(SeguroFianca.class);
		crit.addOrder(Order.asc("staLocId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(SeguroFianca seguroFianca) {
		  int cont=0;
			
			Criteria crit= this.session.createCriteria(SeguroFianca.class);		
			crit.createAlias("locacaos", "loc").add(Restrictions.eq("loc.seguroFianca.segFiaId", seguroFianca.getSegFiaId()));
			
			List<SeguroFianca> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}
			return false;
	}

}
