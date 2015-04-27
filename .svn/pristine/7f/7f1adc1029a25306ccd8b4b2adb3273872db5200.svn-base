package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Filial;

public class FilialDAOHibernate implements FilialDAO{
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(Filial sede) {
		this.session.save(sede);
		
	}

	@Override
	public void atualizar(Filial sede) {
		this.session.update(sede);
		
	}

	@Override
	public void excluir(Filial sede) {
		this.session.delete(sede);
		
	}

	@Override
	public Filial carregar(Integer sedId) {
		return (Filial)this.session.get(Filial.class, sedId);
	}

	@Override
	public Filial buscarPorSede(String sede) {
		Criteria crit= this.session.createCriteria(Filial.class);
		crit.add(Restrictions.like("filNome", sede));
		Filial filial=(Filial) crit.uniqueResult();
		return filial;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Filial> listar() {
		return this.session.createCriteria(Filial.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(Filial filial) {
		int cont=0;

		Criteria crit= this.session.createCriteria(Filial.class);		
		crit.createAlias("funcionarios", "fun").add(Restrictions.eq("fun.filial.filId", filial.getFilId()));		
		List<Filial> lista=crit.list();
		cont=lista.size();
		
		Criteria crit2= this.session.createCriteria(Filial.class);		
		crit2.createAlias("locacaos", "loc").add(Restrictions.eq("loc.filial.filId", filial.getFilId()));		
		List<Filial> lista2=crit2.list();
		cont+=lista2.size();
		
		/*Criteria crit3= this.session.createCriteria(Filial.class);		
		crit3.createAlias("quadroDeChaveses", "qua").add(Restrictions.eq("qua.filial.filId", filial.getFilId()));		
		List<Filial> lista3=crit3.list();
		cont+=lista3.size();*/

		if(cont==0){
			return true;
		}
		return false;
	}

}
