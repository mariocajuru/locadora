package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.PermissoesLocacao;
import br.com.locadora.modelo.SituacaoLocacao;

public class PermissoesLocacaoDAOHibernate implements PermissoesLocacaoDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}


	@Override
	public void salvar(PermissoesLocacao permissoesLocacao) {
		this.session.save(permissoesLocacao);

	}

	@Override
	public void atualizar(PermissoesLocacao permissoesLocacao) {
		this.session.update(permissoesLocacao);

	}

	@Override
	public void excluir(PermissoesLocacao permissoesLocacao) {
		this.session.delete(permissoesLocacao);

	}

	@Override
	public PermissoesLocacao carregar(Integer perLocId) {
		return (PermissoesLocacao)this.session.get(PermissoesLocacao.class, perLocId);
	}

	@Override
	public PermissoesLocacao buscarPorPermissoesLocacao(String permissoesLocacao) {
		String hql="select b from PermissoesLocacao b where b.perLocNome = :permissoesLocacao";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("permissoesLocacao", permissoesLocacao);
		return (PermissoesLocacao) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PermissoesLocacao> listar() {
		Criteria crit= this.session.createCriteria(PermissoesLocacao.class);
		crit.addOrder(Order.asc("perLocId"));
		return crit.list();
	}
	
	@Override
	public boolean dependecias(PermissoesLocacao permissoesLocacao) {
	/*	 int cont=0;
			
			Criteria crit= this.session.createCriteria(PermissoesLocacao.class);		
			crit.createAlias("locacaos", "loc").add(Restrictions.eq("loc.permissoesLocacao.perLocId", permissoesLocacao.getPerLocId()));
			
			List<PermissoesLocacao> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}*/
			return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PermissoesLocacao> carregarPorSituacaoLocacao(
			SituacaoLocacao situacaoLocacao) {
		Criteria crit= this.session.createCriteria(PermissoesLocacao.class);		
		crit.createAlias("situacaoLocacaos", "loc").add(Restrictions.eq("loc.sitLocId", situacaoLocacao.getSitLocId()));
		
		List<PermissoesLocacao> lista=crit.list();
		return lista;
	}

}
