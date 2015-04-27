package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.ParametroReferencia;

public class ParametroReferenciaDAOHibernate implements ParametroReferenciaDAO {
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(ParametroReferencia parametroReferencia) {
		this.session.save(parametroReferencia);
	}

	@Override
	public void atualizar(ParametroReferencia parametroReferencia) {
		this.session.update(parametroReferencia);
	}

	@Override
	public void excluir(ParametroReferencia parametroReferencia) {
		this.session.delete(parametroReferencia);

	}

	@Override
	public ParametroReferencia carregar(Integer parametroReferenciaId) {
		return (ParametroReferencia)this.session.get(ParametroReferencia.class, parametroReferenciaId);
	}

	@Override
	public ParametroReferencia buscarPorParametroReferencia(
			String parametroReferencia) {
		String hql="select b from ParametroReferencia b where b.parRefNome = :parametroReferencia";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("parametroReferencia", parametroReferencia);
		return (ParametroReferencia) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ParametroReferencia> listar() {
		Criteria crit= this.session.createCriteria(ParametroReferencia.class);
		crit.addOrder(Order.asc("parRefId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(ParametroReferencia parametroReferencia) {
		 int cont=0;
			
			Criteria crit= this.session.createCriteria(ParametroReferencia.class);		
			crit.createAlias("tipoDeParametros", "loc").add(Restrictions.eq("loc.parametroReferencia.parRefId", parametroReferencia.getParRefId()));
			
			List<ParametroReferencia> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}
			return false;
	}

}
