package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.PermissoesLocacao;
import br.com.locadora.modelo.SituacaoLocacao;

public class SituacaoLocacaoDAOHibernate implements SituacaoLocacaoDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(SituacaoLocacao situacaoLocacao) {
		this.session.save(situacaoLocacao);

	}

	@Override
	public void atualizar(SituacaoLocacao situacaoLocacao) {
		this.session.update(situacaoLocacao);
	}

	@Override
	public void excluir(SituacaoLocacao situacaoLocacao) {
		this.session.delete(situacaoLocacao);

	}

	@Override
	public SituacaoLocacao carregar(Integer sitLocId) {
		return (SituacaoLocacao)this.session.get(SituacaoLocacao.class, sitLocId);
	}

	@Override
	public SituacaoLocacao buscarPorSituacaoLocacao(String situacaoLocacao) {
		String hql="select b from SituacaoLocacao b where b.sitLocNome = :situacaoLocacao";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("situacaoLocacao", situacaoLocacao);
		return (SituacaoLocacao) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SituacaoLocacao> listar() {
		Criteria crit= this.session.createCriteria(SituacaoLocacao.class);
		crit.addOrder(Order.asc("sitLocId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(SituacaoLocacao situacaoLocacao) {
		 int cont=0;
			
			Criteria crit= this.session.createCriteria(SituacaoLocacao.class);		
			crit.createAlias("locacaos", "loc").add(Restrictions.eq("loc.situacaoLocacao.sitLocId", situacaoLocacao.getSitLocId()));
			List<SituacaoLocacao> lista=crit.list();
			cont=lista.size();		
			if(cont==0){
			return true;
			}
			return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SituacaoLocacao> carregarSituacoesLocacoesPorPermissoesLocacoes(
			PermissoesLocacao permissoesLocacao) {
		Criteria crit= this.session.createCriteria(SituacaoLocacao.class);		
		crit.createAlias("permissoesLocacaos", "loc").add(Restrictions.eq("loc.perLocId", permissoesLocacao.getPerLocId()));
		
		List<SituacaoLocacao> lista=crit.list();
		return lista;
	}


}
