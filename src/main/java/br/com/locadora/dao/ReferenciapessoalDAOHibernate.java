package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Referenciapessoal;

public class ReferenciapessoalDAOHibernate implements ReferenciapessoalDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}
	
	

	@Override
	public void salvar(Referenciapessoal referencia) {
		this.session.save(referencia);

	}

	@Override
	public void atualizar(Referenciapessoal referencia) {
		this.session.update(referencia);

	}

	@Override
	public void excluir(Referenciapessoal referencia) {
		this.session.delete(referencia);

	}

	@Override
	public Referenciapessoal carregar(Integer refId) {
		return (Referenciapessoal) this.session.get(Referenciapessoal.class, refId);
	}

	@Override
	public Referenciapessoal buscarPorReferencia(String referencia) {
		String hql = "select e from Referenciapessoal e where e.carNome = :referencia";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("referencia", referencia);
		return (Referenciapessoal) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Referenciapessoal> listar() {
		return this.session.createCriteria(Referenciapessoal.class).list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Referenciapessoal> carregarPessoa(Pessoa pessoa) {
		Criteria c = session.createCriteria(Referenciapessoal.class);
		c.add(Restrictions.eq("pessoa.pesId", pessoa.getPesId()));

		return (List<Referenciapessoal>) c.list();
	}

}
