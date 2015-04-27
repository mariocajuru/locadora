package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.locadora.modelo.Email;
import br.com.locadora.modelo.HistoricoPessoa;

public class HistoricoPessoaDAOHibernate implements HistoricoPessoaDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(HistoricoPessoa historicoPessoa) {
		this.session.save(historicoPessoa);
	}

	@Override
	public void atualizar(HistoricoPessoa historicoPessoa) {
		this.session.update(historicoPessoa);
		
	}

	@Override
	public void excluir(HistoricoPessoa historicoPessoa) {
		this.session.delete(historicoPessoa);
		
	}

	@Override
	public HistoricoPessoa carregar(Integer hisId) {
		return (HistoricoPessoa) this.session.get(Email.class, hisId);
	}

	@Override
	public HistoricoPessoa buscarPorHistoricoPessoa(String historicoPessoa) {
		String hql = "select e from HistoricoPessoa e where e.hisDescricao = :historicoPessoa";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("historicoPessoa", historicoPessoa);
		return (HistoricoPessoa) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoPessoa> listar() {
		return this.session.createCriteria(HistoricoPessoa.class).list();
	}

}
