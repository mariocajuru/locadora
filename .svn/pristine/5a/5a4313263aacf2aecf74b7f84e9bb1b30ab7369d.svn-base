package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import br.com.locadora.modelo.DescontoLocacao;

public class DescontoLocacaoDAOHibernate implements DescontoLocacaoDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(DescontoLocacao descontoLocacao) {
		this.session.save(descontoLocacao);

	}

	@Override
	public void atualizar(DescontoLocacao descontoLocacao) {
		this.session.update(descontoLocacao);
	}

	@Override
	public void excluir(DescontoLocacao descontoLocacao) {
		this.session.delete(descontoLocacao);
	}

	@Override
	public DescontoLocacao carregar(Integer descLocId) {
		return (DescontoLocacao)this.session.get(DescontoLocacao.class, descLocId);
	}

	@Override
	public DescontoLocacao buscarPorDescontoLocacao(Double descontoLocacao) {
		String hql="select b from DescontoLocacao b where b.descLocValor = :descontoLocacao";
		Query consulta= this.session.createQuery(hql);
		consulta.setDouble("descontoLocacao", descontoLocacao);
		return (DescontoLocacao) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<DescontoLocacao> listar() {
		Criteria crit= this.session.createCriteria(DescontoLocacao.class);
		crit.addOrder(Order.asc("descLocId"));
		return crit.list();
	}

	
}
