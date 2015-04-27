package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.locadora.modelo.ProprietariosLocacao;
import br.com.locadora.modelo.StatusLocacao;

public class ProprietariosLocacaoDAOHibernate implements
		ProprietariosLocacaoDAO {
	

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}


	@Override
	public void salvar(ProprietariosLocacao proprietariosLocacao) {
		this.session.save(proprietariosLocacao);

	}

	@Override
	public void atualizar(ProprietariosLocacao proprietariosLocacao) {
		this.session.update(proprietariosLocacao);

	}

	@Override
	public void excluir(ProprietariosLocacao proprietariosLocacao) {
		this.session.delete(proprietariosLocacao);

	}

	@Override
	public ProprietariosLocacao carregar(Integer proLocId) {
		return (ProprietariosLocacao)this.session.get(StatusLocacao.class, proLocId);
	}

		@SuppressWarnings("unchecked")
	@Override
	public List<ProprietariosLocacao> listar() {
		Criteria crit= this.session.createCriteria(ProprietariosLocacao.class);
		crit.addOrder(Order.asc("proLocId"));
		return crit.list();
	}
	
}
