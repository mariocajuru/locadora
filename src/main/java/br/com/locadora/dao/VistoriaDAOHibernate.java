package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.locadora.modelo.Vistoria;

public class VistoriaDAOHibernate implements VistoriaDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
	}
	@Override
	public void salvar(Vistoria vistoria) {
		this.session.save(vistoria);

	}

	@Override
	public void atualizar(Vistoria vistoria) {
		this.session.update(vistoria);

	}

	@Override
	public void excluir(Vistoria vistoria) {
		this.session.delete(vistoria);

	}

	@Override
	public Vistoria carregar(Integer visId) {
		return (Vistoria)this.session.get(Vistoria.class, visId);
	}

	@Override
	public Vistoria buscarPorVistoria(String vistoria) {
		String hql="select s from Vistoria s where s.visPendente = :vistoria";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("vistoria", vistoria);
		return (Vistoria) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Vistoria> listar() {
		return this.session.createCriteria(Vistoria.class).list();
	}

}
