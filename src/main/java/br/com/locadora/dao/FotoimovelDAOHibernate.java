package br.com.locadora.dao;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Fotoimovel;
import br.com.locadora.modelo.Imovel;

public class FotoimovelDAOHibernate implements FotoimovelDAO {
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(Fotoimovel fotoimovel) {
		this.session.save(fotoimovel);

	}

	@Override
	public void atualizar(Fotoimovel fotoimovel) {
		fotoimovel = (Fotoimovel) this.session.merge(fotoimovel);
		this.session.update(fotoimovel);

	}

	@Override
	public void excluir(Fotoimovel fotoimovel) {
		this.session.delete(fotoimovel);

	}

	@Override
	public Fotoimovel carregar(Integer codigo) {
		return (Fotoimovel)this.session.get(Fotoimovel.class, codigo);
	}

	@Override
	public Fotoimovel buscarPorFotoimovel(String fotoimovel) {
		String hql="select s from Fotoimovel s where s.fotNome = :fotoimovel";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("fotoimovel", fotoimovel);
		return (Fotoimovel) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Fotoimovel> listar() {
		return this.session.createCriteria(Fotoimovel.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Fotoimovel> carregarFotosDeImovel(Imovel imovel) {
		Criteria crit = session.createCriteria(Fotoimovel.class);
		crit.add(Restrictions.eq("imovel", imovel));
		/*
		//eliminando resultados repetidos
		  crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);*/
		return 	crit.list();
	
	}
	public Fotoimovel buscarPorNomeEImovel(String fotoimovel, Imovel imovel){
		String hql="select s from Fotoimovel s where s.fotNome = :fotoImovel and s.imovel.imoId = :idImovel";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("fotoImovel", fotoimovel);
		consulta.setInteger("idImovel", imovel.getImoId());
		return (Fotoimovel) consulta.uniqueResult();
	}
}
