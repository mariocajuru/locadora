package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.locadora.modelo.Endereco;

public class EnderecoDAOHibernate implements EnderecoDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(Endereco endereco) {
		this.session.save(endereco);

	}

	@Override
	public void atualizar(Endereco endereco) {
		this.session.update(endereco);

	}

	@Override
	public void excluir(Endereco endereco) {
				this.session.delete(endereco);
	}

	@Override
	public Endereco carregar(Integer endId) {
		return (Endereco) this.session.get(Endereco.class, endId);
	}

	@Override
	public Endereco buscarPorEndereco(String endereco) {
		String hql = "select e from Endereco e where e.endNome = :endereco";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("endereco", endereco);
		return (Endereco) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Endereco> listar() {
		return this.session.createCriteria(Endereco.class).list();
	}

}
