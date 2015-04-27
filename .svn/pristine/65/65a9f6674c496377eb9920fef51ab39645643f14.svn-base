package br.com.locadora.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.TemPerto;

public class TemPertoDAOHibernate implements TemPertoDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}

	@Override
	public void salvar(TemPerto temPerto) {
		this.session.save(temPerto);

	}

	@Override
	public void atualizar(TemPerto temPerto) {
		this.session.update(temPerto);

	}

	@Override
	public void excluir(TemPerto temPerto) {
		this.session.delete(temPerto);

	}

	@Override
	public TemPerto carregar(Integer temPerId) {
		return (TemPerto)this.session.get(TemPerto.class, temPerId);
	}

	@Override
	public TemPerto buscarPorTemPerto(String temPerto) {
		String hql="select p from TemPerto p where p.temPerNome = :temPerto";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("temPerto", temPerto);
		return (TemPerto) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<TemPerto> listar() {
		return this.session.createCriteria(TemPerto.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<TemPerto> carregarListaPorImovel(Imovel imovel) {
		List<TemPerto> resultado = new ArrayList<TemPerto>();
		Criteria crit = session.createCriteria(TemPerto.class);
		crit.createAlias("imovels", "imo");
		crit.add(Restrictions.eq("imo.imoId", imovel.getImoId()));
		resultado = crit.list();
		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(TemPerto temPerto) {
		int cont=0;		
		Criteria crit= this.session.createCriteria(TemPerto.class);
		crit.createAlias("imovels", "imoc");
		crit.createAlias("imoc.temPertos", "imoTemPerto").add(Restrictions.eq("imoTemPerto.temPerId", temPerto.getTemPerId()));
		List<TemPerto> lista=crit.list();
		cont=lista.size();

		if(cont==0){
			return true;
		}
		return false;
	}

}
