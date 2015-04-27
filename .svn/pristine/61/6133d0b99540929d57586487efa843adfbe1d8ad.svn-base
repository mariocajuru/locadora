package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Proposta;

public class PropostaDAOHibernate implements PropostaDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(Proposta proposta) {
		this.session.save(proposta);

	}

	@Override
	public void atualizar(Proposta proposta) {
		this.session.update(proposta);

	}

	@Override
	public void excluir(Proposta proposta) {
		this.session.delete(proposta);

	}

	@Override
	public Proposta carregar(Integer proId) {
		return (Proposta)this.session.get(Proposta.class, proId);
	}

	@Override
	public Proposta buscarPorProposta(String proposta) {
		String hql="select s from Proposta s where s.sedName = :proposta";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("proposta", proposta);
		return (Proposta) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Proposta> listar() {
		return this.session.createCriteria(Proposta.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Proposta> carregarPorImovel(Imovel imovel) {
		Criteria cri= this.session.createCriteria(Proposta.class);
		cri.add(Restrictions.eq("imovel.imoId", imovel.getImoId()));
		cri.addOrder(Order.asc("proId")); 
		List<Proposta> listaProposta=cri.list();
		return listaProposta;
	}

}
