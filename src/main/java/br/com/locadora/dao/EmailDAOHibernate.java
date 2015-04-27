package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Email;
import br.com.locadora.modelo.Filial;
import br.com.locadora.modelo.Pessoa;

public class EmailDAOHibernate implements EmailDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(Email email) {
		this.session.save(email);

	}

	@Override
	public void atualizar(Email email) {
		this.session.update(email);

	}

	@Override
	public void excluir(Email email) {
		this.session.delete(email);

	}

	@Override
	public Email carregar(Integer emaId) {
		return (Email) this.session.get(Email.class, emaId);
	}

	@Override
	public Email buscarPorEmail(String email) {
		String hql = "select e from Email e where e.emaEndereco = :email";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("email", email);
		return (Email) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Email> listar() {
		return this.session.createCriteria(Email.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Email> carregarPessoa(Pessoa pessoa) {

		Criteria c = session.createCriteria(Email.class);
		c.add(Restrictions.eq("pessoa.pesId", pessoa.getPesId()));

		return (List<Email>) c.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Email> carregarFilial(Filial filial) {
		Criteria c = session.createCriteria(Email.class);
		c.add(Restrictions.eq("filial.filId", filial.getFilId()));

		return (List<Email>) c.list();
	}
}
