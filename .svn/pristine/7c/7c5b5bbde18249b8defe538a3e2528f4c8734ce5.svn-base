package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Parametro;

public class ParametroDAOHibernate implements ParametroDAO {
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(Parametro parametro) {
		this.session.save(parametro);

	}

	@Override
	public void atualizar(Parametro parametro) {
		this.session.update(parametro);

	}

	@Override
	public void excluir(Parametro parametro) {
		this.session.delete(parametro);

	}

	@Override
	public Parametro carregar(Integer parametroId) {
		return (Parametro)this.session.get(Parametro.class, parametroId);
	}

	@Override
	public Parametro buscarPorParametro(String parametro) {
		String hql="select b from Parametro b where b.parNome = :parametro";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("parametro", parametro);
		return (Parametro) consulta.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Parametro> listar() {
		Criteria crit= this.session.createCriteria(Parametro.class);
		crit.addOrder(Order.asc("parId"));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(Parametro parametro) {
		 int cont=0;
			
			Criteria crit= this.session.createCriteria(Parametro.class);		
			crit.createAlias("tipoDeParametros", "loc").add(Restrictions.eq("loc.parametro.parId", parametro.getParId()));
			
			List<Parametro> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}
			return false;
	}

}
