package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.GrupoDeContas;

public class GrupoDeContasDAOHibernate implements GrupoDeContasDAO{
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(GrupoDeContas grupoDeContas) {
		this.session.save(grupoDeContas);
		
	}

	@Override
	public void atualizar(GrupoDeContas grupoDeContas) {
		this.session.update(grupoDeContas);
		
	}

	@Override
	public void excluir(GrupoDeContas grupoDeContas) {
		this.session.delete(grupoDeContas);
		
	}

	@Override
	public GrupoDeContas carregar(Integer gruConId) {
		return (GrupoDeContas)this.session.get(GrupoDeContas.class, gruConId);
	}

	@Override
	public GrupoDeContas buscarPorGrupoDeContas(String grupoDeContas) {
		String hql="select b from GrupoDeContas b where b.gruConNome = :grupoDeContas";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("grupoDeContas", grupoDeContas);
		return (GrupoDeContas) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoDeContas> listar() {
		Criteria crit= this.session.createCriteria(GrupoDeContas.class);
		crit.addOrder(Order.asc("gruConId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(GrupoDeContas grupoDeContas) {
		 int cont=0;
			
			Criteria crit= this.session.createCriteria(GrupoDeContas.class);		
			crit.createAlias("locacaos", "loc").add(Restrictions.eq("loc.grupoDeContas.gruConId", grupoDeContas.getGruConId()));
			
			List<GrupoDeContas> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}
			return false;
	}

}
