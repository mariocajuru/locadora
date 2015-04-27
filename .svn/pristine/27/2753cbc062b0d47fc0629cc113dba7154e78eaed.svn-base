package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Telefone;

public class TelefoneDAOHibernate implements TelefoneDAO{
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(Telefone telefone) {
		this.session.save(telefone);
		
	}

	@Override
	public void atualizar(Telefone telefone) {
		this.session.update(telefone);
		
	}

	@Override
	public void excluir(Telefone telefone) {
		this.session.delete(telefone);
		
	}

	@Override
	public Telefone carregar(Integer telId) {
		return (Telefone)this.session.get(Telefone.class, telId);
	}

	@Override
	public Telefone buscarPorTelefone(String telefone) {
		String hql="select t from Telefone t where t.telNumero = :telefone";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("telefone", telefone);
		return (Telefone) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Telefone> listar() {
		return this.session.createCriteria(Telefone.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Telefone> carregarPessoa(Pessoa pessoa) {
		Criteria c = session.createCriteria(Telefone.class);
		c.add(Restrictions.eq("pessoa.pesId", pessoa.getPesId()));

		return (List<Telefone>) c.list();
	}

}
