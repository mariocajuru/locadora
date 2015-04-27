package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.locadora.modelo.EnderecoImovelDesejado;

public class EnderecoImovelDesejadoDAOHibernate implements
		EnderecoImovelDesejadoDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(EnderecoImovelDesejado enderecoImovelDesejado) {
		this.session.save(enderecoImovelDesejado);

	}

	@Override
	public void atualizar(EnderecoImovelDesejado enderecoImovelDesejado) {
		this.session.update(enderecoImovelDesejado);

	}

	@Override
	public void excluir(EnderecoImovelDesejado enderecoImovelDesejado) {
		this.session.delete(enderecoImovelDesejado);

	}

	@Override
	public EnderecoImovelDesejado carregar(Integer endId) {
		return (EnderecoImovelDesejado) this.session.get(EnderecoImovelDesejado.class, endId);
	}

	@Override
	public EnderecoImovelDesejado buscarPorEnderecoImovelDesejado(String rua) {
		String hql = "select e from EnderecoImovelDesejado e where e.endImoDesNome = :rua";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("rua", rua);
		return (EnderecoImovelDesejado) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<EnderecoImovelDesejado> listar() {
		return this.session.createCriteria(EnderecoImovelDesejado.class).list();
	}

}
