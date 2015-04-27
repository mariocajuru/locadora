package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.ContasAPagar;

public class ContasAPagarDAOHibernate implements ContasAPagarDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(ContasAPagar contasAPagar) {
		this.session.save(contasAPagar);
	}

	@Override
	public void atualizar(ContasAPagar contasAPagar) {
		this.session.update(contasAPagar);
	}

	@Override
	public void excluir(ContasAPagar contasAPagar) {
		this.session.delete(contasAPagar);
	}

	@Override
	public ContasAPagar carregar(Integer conPagId) {
		return (ContasAPagar)this.session.get(ContasAPagar.class, conPagId);
		}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContasAPagar> listar() {
		Criteria crit= this.session.createCriteria(ContasAPagar.class);
		crit.addOrder(Order.asc("conPagId"));
		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(ContasAPagar contasAPagar) {
		int cont=0;
		Criteria crit= this.session.createCriteria(ContasAPagar.class);		
		crit.createAlias("lancamentoContasAPagars", "bai").add(Restrictions.eq("bai.contasAPagar.conPagId", contasAPagar.getConPagId()));
		List<ContasAPagar> lista=crit.list();
		cont=lista.size();
		if(cont==0){
			return true;
		}
		return false;
	}

}
