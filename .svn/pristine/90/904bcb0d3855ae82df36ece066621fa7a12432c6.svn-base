package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.PermissoesLocacao;
import br.com.locadora.modelo.SituacaoLocacao;
import br.com.locadora.modelo.SituacaoLocacaoPermissoes;

public class SituacaoLocacaoPermissoesDAOHibernate implements
		SituacaoLocacaoPermissoesDAO {

	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	
	@Override
	public void salvar(SituacaoLocacaoPermissoes situacaoLocacaoPermissoes) {
		this.session.save(situacaoLocacaoPermissoes);

	}

	@Override
	public void atualizar(SituacaoLocacaoPermissoes situacaoLocacaoPermissoes) {
		this.session.update(situacaoLocacaoPermissoes);

	}

	@Override
	public void excluir(SituacaoLocacaoPermissoes situacaoLocacaoPermissoes) {
		this.session.delete(situacaoLocacaoPermissoes);

	}

	@Override
	public void excluirTudo() {
		String hql = "delete SituacaoLocacaoPermissoes";
		Query consulta = this.session.createQuery(hql);
		
		consulta.executeUpdate();

	}

	@Override
	public SituacaoLocacaoPermissoes carregar(Integer codigo) {
		return (SituacaoLocacaoPermissoes) this.session.get(SituacaoLocacaoPermissoes.class, codigo);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<SituacaoLocacaoPermissoes> listar() {
		return this.session.createCriteria(SituacaoLocacaoPermissoes.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PermissoesLocacao> listarPermissoesLocacaoPorSituacao(
			SituacaoLocacao situacaoLocacao) {
		Criteria c = session.createCriteria(PermissoesLocacao.class);
		c.createAlias("situacaoLocacaoPermissoeses", "si");
		c.add(Restrictions.eq("si.situacaoLocacao.sitLocId", situacaoLocacao.getSitLocId()));
		return (List<PermissoesLocacao>) c.list();
	}

}
