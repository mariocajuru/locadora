package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.InformacaoAdicional;
import br.com.locadora.modelo.Locacao;

public class InformacaoAdicionalDAOHibernate implements InformacaoAdicionalDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}

	@Override
	public void salvar(InformacaoAdicional informacaoAdicional) {
		this.session.save(informacaoAdicional);

	}

	@Override
	public void atualizar(InformacaoAdicional informacaoAdicional) {
		this.session.update(informacaoAdicional);

	}

	@Override
	public void excluir(InformacaoAdicional informacaoAdicional) {
		this.session.delete(informacaoAdicional);

	}

	@Override
	public InformacaoAdicional carregar(Integer infAdiId) {
		return (InformacaoAdicional)this.session.get(InformacaoAdicional.class, infAdiId);
	}

	@Override
	public InformacaoAdicional buscarPorInformacaoAdicional(
			String informacaoAdicional) {
		String hql="select b from InformacaoAdicional b where b.infAdiObservacao = :informacaoAdicional";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("informacaoAdicional", informacaoAdicional);
		return (InformacaoAdicional) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<InformacaoAdicional> listar() {
		Criteria crit= this.session.createCriteria(InformacaoAdicional.class);
		crit.addOrder(Order.asc("infAdiId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<InformacaoAdicional> carregarPorLocacao(Locacao locacao) {
		Criteria crit= this.session.createCriteria(InformacaoAdicional.class);
		crit.add(Restrictions.eq("locacao.locId", locacao.getLocId()));
		crit.addOrder(Order.asc("infAdiId"));
		return crit.list();
	}

}
