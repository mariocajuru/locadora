package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.IndicesReajustes;

public class IndicesReajustesDAOHibernate implements IndicesReajustesDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(IndicesReajustes indicesReajustes) {
		this.session.save(indicesReajustes);

	}

	@Override
	public void atualizar(IndicesReajustes indicesReajustes) {
		this.session.update(indicesReajustes);

	}

	@Override
	public void excluir(IndicesReajustes indicesReajustes) {
		this.session.delete(indicesReajustes);

	}

	@Override
	public IndicesReajustes carregar(Integer indReacId) {
		return (IndicesReajustes)this.session.get(IndicesReajustes.class, indReacId);
	}

	@Override
	public IndicesReajustes buscarPorIndicesReajustes(String indicesReajustes) {
		String hql="select b from IndicesReajustes b where b.indReaNome = :indicesReajustes";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("indicesReajustes", indicesReajustes);
		return (IndicesReajustes) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<IndicesReajustes> listar() {
		Criteria crit= this.session.createCriteria(IndicesReajustes.class);
		crit.addOrder(Order.asc("indReaId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(IndicesReajustes indicesReajustes) {
		  int cont=0;
			
			Criteria crit= this.session.createCriteria(IndicesReajustes.class);		
			crit.createAlias("locacaos", "loc").add(Restrictions.eq("loc.indicesReajustes.indReaId", indicesReajustes.getIndReaId()));
			
			List<IndicesReajustes> lista=crit.list();
			cont=lista.size();	
			
			/*Criteria crit2= this.session.createCriteria(IndicesReajustes.class);		
			crit2.createAlias("valorIndiceReajustes", "val").add(Restrictions.eq("val.indicesReajustes.indReaId", indicesReajustes.getIndReaId()));
			
			List<IndicesReajustes> lista2=crit2.list();
			cont=lista2.size();*/
			
			if(cont==0){
			return true;
			}
			return false;
	}

}
