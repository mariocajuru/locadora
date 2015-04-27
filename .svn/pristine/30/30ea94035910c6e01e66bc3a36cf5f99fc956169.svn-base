package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.DestinacaoLocacao;

public class DestinacaoLocacaoDAOHibernate implements DestinacaoLocacaoDAO {
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}


	@Override
	public void salvar(DestinacaoLocacao destinacaoLocacao) {
		this.session.save(destinacaoLocacao);

	}

	@Override
	public void atualizar(DestinacaoLocacao destinacaoLocacao) {
		this.session.update(destinacaoLocacao);

	}

	@Override
	public void excluir(DestinacaoLocacao destinacaoLocacao) {
		this.session.delete(destinacaoLocacao);

	}

	@Override
	public DestinacaoLocacao carregar(Integer desLocId) {
		return (DestinacaoLocacao)this.session.get(DestinacaoLocacao.class, desLocId);
	}

	@Override
	public DestinacaoLocacao buscarPorDestinacaoLocacao(String destinacaoLocacao) {
		String hql="select b from DestinacaoLocacao b where b.desLocNome = :destinacaoLocacao";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("destinacaoLocacao", destinacaoLocacao);
		return (DestinacaoLocacao) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<DestinacaoLocacao> listar() {
		Criteria crit= this.session.createCriteria(DestinacaoLocacao.class);
		crit.addOrder(Order.asc("desLocId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(DestinacaoLocacao destinacaoLocacao) {
        int cont=0;
		
		Criteria crit= this.session.createCriteria(DestinacaoLocacao.class);		
		crit.createAlias("locacaos", "loc").add(Restrictions.eq("loc.destinacaoLocacao.desLocId", destinacaoLocacao.getDesLocId()));
		
		List<DestinacaoLocacao> lista=crit.list();
		cont=lista.size();		
		
		if(cont==0){
		return true;
		}
		return false;
	}

}
