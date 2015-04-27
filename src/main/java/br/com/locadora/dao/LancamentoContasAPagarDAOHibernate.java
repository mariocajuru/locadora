package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import br.com.locadora.modelo.LancamentoContasAPagar;

public class LancamentoContasAPagarDAOHibernate implements
		LancamentoContasAPagarDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(LancamentoContasAPagar lancamentoContasAPagar) {
		this.session.save(lancamentoContasAPagar);
	}

	@Override
	public void atualizar(LancamentoContasAPagar lancamentoContasAPagar) {
		this.session.update(lancamentoContasAPagar);
	}

	@Override
	public void excluir(LancamentoContasAPagar lancamentoContasAPagar) {
		this.session.delete(lancamentoContasAPagar);
	}

	@Override
	public LancamentoContasAPagar carregar(Integer lanConPagId) {
		return (LancamentoContasAPagar)this.session.get(LancamentoContasAPagar.class, lanConPagId);
	}

	@Override
	public LancamentoContasAPagar buscarLancamentoContasAPagar(
			String lancamentoContasAPagar) {
		String hql="select b from LancamentoContasAPagar b where b.lanConPagIdContaCorrente = :lancamentoContasAPagar";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("lancamentoContasAPagar", lancamentoContasAPagar);
		return (LancamentoContasAPagar) consulta.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LancamentoContasAPagar> listar() {
		Criteria crit= this.session.createCriteria(LancamentoContasAPagar.class);
		crit.addOrder(Order.asc("lanConPagId"));
		return crit.list();
	}


}
