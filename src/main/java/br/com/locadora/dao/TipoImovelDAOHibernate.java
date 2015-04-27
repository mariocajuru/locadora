package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;



import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Caracteristicas;
import br.com.locadora.modelo.Tipoimovel;

public class TipoImovelDAOHibernate implements TipoImovelDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}

	@Override
	public void salvar(Tipoimovel tipoImovel) {
		this.session.save(tipoImovel);

	}

	@Override
	public void atualizar(Tipoimovel tipoImovel) {
		this.session.update(tipoImovel);

	}

	@Override
	public void excluir(Tipoimovel tipoImovel) {
		this.session.delete(tipoImovel);

	}

	@Override
	public Tipoimovel carregar(Integer tipId) {
		return (Tipoimovel)this.session.get(Tipoimovel.class, tipId);
	}

	@Override
	public Tipoimovel buscarPorTipoImovel(String tipoImovel) {
		String hql="select c from Tipoimovel c where c.tipNome = :tipoImovel";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("tipoImovel", tipoImovel);
		return (Tipoimovel) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Tipoimovel> listar() {
		return this.session.createCriteria(Tipoimovel.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(Tipoimovel tipoimovel) {
		int cont=0;		
		Criteria crit= this.session.createCriteria(Tipoimovel.class);		
		crit.createAlias("imovelDesejados", "imoc").add(Restrictions.eq("imoc.tipoimovel.tipId", tipoimovel.getTipId()));
		List<Caracteristicas> lista=crit.list();
		cont=lista.size();

		Criteria crit2= this.session.createCriteria(Tipoimovel.class);		
		crit2.createAlias("imovels", "imoc2").add(Restrictions.eq("imoc2.tipoimovel.tipId", tipoimovel.getTipId()));
		List<Caracteristicas> lista2=crit2.list();
		cont+=lista2.size();

		if(cont==0){
			return true;
		}
		return false;
	}

}
