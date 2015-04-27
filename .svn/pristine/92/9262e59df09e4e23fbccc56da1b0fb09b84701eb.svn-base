package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.StatusLocacao;

public class StatusLocacaoDAOHibernate implements StatusLocacaoDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}


	@Override
	public void salvar(StatusLocacao statusLocacao) {
		this.session.save(statusLocacao);
		
	}

	@Override
	public void atualizar(StatusLocacao statusLocacao) {
		this.session.update(statusLocacao);
		
	}

	@Override
	public void excluir(StatusLocacao statusLocacao) {
		this.session.delete(statusLocacao);
		
	}

	@Override
	public StatusLocacao carregar(Integer staLoccId) {
		return (StatusLocacao)this.session.get(StatusLocacao.class, staLoccId);
	}

	@Override
	public StatusLocacao buscarPorStatusLocacao(String statusLocacao) {
		String hql="select b from StatusLocacao b where b.staLocNome = :statusLocacao";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("statusLocacao", statusLocacao);
		return (StatusLocacao) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StatusLocacao> listar() {
		Criteria crit= this.session.createCriteria(StatusLocacao.class);
		crit.addOrder(Order.asc("staLocId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(StatusLocacao statusLocacao) {
		  int cont=0;
			
			Criteria crit= this.session.createCriteria(StatusLocacao.class);		
			crit.createAlias("locacaos", "loc").add(Restrictions.eq("loc.statusLocacao.staLocId", statusLocacao.getStaLocId()));
			
			List<StatusLocacao> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}
			return false;
	}

}
