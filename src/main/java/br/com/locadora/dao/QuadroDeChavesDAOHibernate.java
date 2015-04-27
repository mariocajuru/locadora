package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.locadora.modelo.QuadroDeChaves;

public class QuadroDeChavesDAOHibernate implements QuadroDeChavesDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(QuadroDeChaves quaChaves) {
		this.session.save(quaChaves);

	}

	@Override
	public void atualizar(QuadroDeChaves quaChaves) {
		this.session.update(quaChaves);

	}

	@Override
	public void excluir(QuadroDeChaves quaChaves) {
		this.session.delete(quaChaves);

	}

	@Override
	public QuadroDeChaves carregar(Integer quaId) {
		return (QuadroDeChaves) this.session.get(QuadroDeChaves.class, quaId);
	}

	@Override
	public QuadroDeChaves buscarPorQuadroDeChaves(String quaChaves) {
		String hql = "select e from QuadroDeChaves e where e.quaPosicao = :quaChaves";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("quaChaves", quaChaves);
		return (QuadroDeChaves) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<QuadroDeChaves> listar() {
		return this.session.createCriteria(QuadroDeChaves.class).list();
	}
	@Override
	public QuadroDeChaves buscarPorFilial(Integer idFilial) {
		String hql = "select e from QuadroDeChaves e where e.filial.filId = :idFilial";
		Query consulta = this.session.createQuery(hql);
		consulta.setInteger("idFilial", idFilial);
		return (QuadroDeChaves) consulta.uniqueResult();
	}

}
