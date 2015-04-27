package br.com.locadora.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Fiador;
import br.com.locadora.modelo.Locacao;
import br.com.locadora.modelo.Pessoa;

public class FiadorDAOHibernate implements FiadorDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(Fiador fiador) {
		this.session.save(fiador);

	}

	@Override
	public void atualizar(Fiador fiador) {
		this.session.update(fiador);

	}

	@Override
	public void excluir(Fiador fiador) {
		this.session.delete(fiador);

	}

	@Override
	public Fiador carregar(Integer filId) {
		return (Fiador)this.session.get(Fiador.class, filId);
	}

	@Override
	public Fiador buscarPorFiador(String fiador) {
		String hql="select s from Fiador s where s.pessoa.pesName = :fiador";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("fiador", fiador);
		return (Fiador) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Fiador> listar() {
		return this.session.createCriteria(Fiador.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Fiador> carregarFiadoresPorImovel(Locacao locacao) {
		Criteria criteria= this.session.createCriteria(Fiador.class);
		criteria.createAlias("locacaos", "l");
		criteria.add(Restrictions.eq("l.locId", locacao.getLocId()));
		List<Fiador> listaFiadores=new ArrayList<Fiador>();
		listaFiadores= criteria.list();
		return listaFiadores;
	}
	@Override
	public Fiador carregarPorPessoa(Pessoa pessoa) {
		Criteria criteria= this.session.createCriteria(Fiador.class);
		criteria.add(Restrictions.eq("pessoa", pessoa));
		Fiador fiador=(Fiador) criteria.uniqueResult();
		return fiador;
	}

}
