package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.locadora.modelo.ContaCorrente;

public class ContaCorrenteDAOHibernate implements ContaCorrenteDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(ContaCorrente contaCorrente) {
		this.session.save(contaCorrente);

	}

	@Override
	public void atualizar(ContaCorrente contaCorrente) {
		this.session.update(contaCorrente);

	}

	@Override
	public void excluir(ContaCorrente contaCorrente) {
		this.session.delete(contaCorrente);

	}

	@Override
	public ContaCorrente carregar(Integer conCorId) {
		return (ContaCorrente)this.session.get(ContaCorrente.class, conCorId);
	}

	@Override
	public ContaCorrente buscarContaCorrente(String contaCorrente) {
		String hql="select b from ContaCorrente b where b.conCorValor = :contaCorrente";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("contaCorrente", contaCorrente);
		return (ContaCorrente) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ContaCorrente> listar() {
		Criteria crit= this.session.createCriteria(ContaCorrente.class);
		crit.addOrder(Order.asc("conCorId"));
		return crit.list();
	}

}
