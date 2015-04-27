package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.locadora.modelo.PessoaFiador;

public class PessoaFiadorDAOHibernate implements PessoaFiadorDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(PessoaFiador pessoaFiador) {
		this.session.save(pessoaFiador);

	}

	@Override
	public void atualizar(PessoaFiador pessoaFiador) {
		this.session.update(pessoaFiador);

	}

	@Override
	public void excluir(PessoaFiador pessoaFiador) {
		this.session.delete(pessoaFiador);

	}

	@Override
	public PessoaFiador carregar(Integer pesFiaId) {
		return (PessoaFiador)this.session.get(PessoaFiador.class, pesFiaId);
	}

	@Override
	public PessoaFiador buscarPorPessoaFiador(String pessoaFiador) {
		String hql="select s from PessoaFiador s where s.pessoa.pesName = :pessoaFiador";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("pessoaFiador", pessoaFiador);
		return (PessoaFiador) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PessoaFiador> listar() {
		return this.session.createCriteria(PessoaFiador.class).list();
	}

}
