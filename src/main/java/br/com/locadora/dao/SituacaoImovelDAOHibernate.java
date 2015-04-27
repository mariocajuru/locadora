package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Situacaoimovel;

public class SituacaoImovelDAOHibernate implements SituacaoImovelDAO {

	private Session session;
	public void setSession(Session session) {
	this.session = session;
}


	@Override
	public void salvar(Situacaoimovel situacaoImovel) {
		this.session.save(situacaoImovel);

	}

	@Override
	public void atualizar(Situacaoimovel situacaoImovel) {
		this.session.update(situacaoImovel);

	}

	@Override
	public void excluir(Situacaoimovel situacaoImovel) {
		this.session.delete(situacaoImovel);

	}

	@Override
	public Situacaoimovel carregar(Integer sitId) {
		return (Situacaoimovel)this.session.get(Situacaoimovel.class, sitId);
	}

	@Override
	public Situacaoimovel buscarPorSituacao(String situacaoImovel) {
		String hql="select c from Situacaoimovel c where c.sitNome = :situacaoImovel";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("situacaoImovel", situacaoImovel);
		return (Situacaoimovel) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Situacaoimovel> listar() {
		return this.session.createCriteria(Situacaoimovel.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(Situacaoimovel situacaoimovel) {
		int cont=0;		
		Criteria crit= this.session.createCriteria(Situacaoimovel.class);		
		crit.createAlias("imovels", "imoc").add(Restrictions.eq("imoc.situacaoimovel.sitId", situacaoimovel.getSitId()));
		List<Situacaoimovel> lista=crit.list();
		cont=lista.size();

		if(cont==0){
			return true;
		}
		return false;
	}

}
