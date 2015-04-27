package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.ChaveNoQuadro;
import br.com.locadora.modelo.EmprestimoChave;
import br.com.locadora.modelo.Filial;
import br.com.locadora.modelo.Pessoa;

public class EmprestimoChaveDAOHibernate implements EmprestimoChaveDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(EmprestimoChave emprestimoChave) {
		this.session.save(emprestimoChave);

	}

	@Override
	public void atualizar(EmprestimoChave emprestimoChave) {
		this.session.update(emprestimoChave);

	}

	@Override
	public void excluir(EmprestimoChave emprestimoChave) {
		this.session.delete(emprestimoChave);

	}

	@Override
	public EmprestimoChave carregar(Integer empId) {
		return (EmprestimoChave) this.session.get(EmprestimoChave.class, empId);
	}

	@Override
	public EmprestimoChave buscarPorEmprestimo(String emprestimo) {
		String hql = "select e from EmprestimoChave e where e.? = :emprestimo";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("emprestimo", emprestimo);
		return (EmprestimoChave) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<EmprestimoChave> listar() {
		return this.session.createCriteria(EmprestimoChave.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<EmprestimoChave> carregarEmprestimoPorFilial(Filial filial) {
		Criteria crit = session.createCriteria(ChaveNoQuadro.class);
		crit.createAlias("quadroDeChaves.filial", "q");
		crit.add(Restrictions.eq("q", filial));
		List<EmprestimoChave>lista=crit.list();
		return lista;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<EmprestimoChave> carregarEmprestimoPorPessoa(Pessoa pessoa) {
		Criteria crit = session.createCriteria(EmprestimoChave.class);
		crit.add(Restrictions.eq("pessoa", pessoa));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<EmprestimoChave> listaEmprestimoOrdenadaPorPessoa() {
		Criteria crit = session.createCriteria(EmprestimoChave.class);
		crit.addOrder(Order.asc("pessoa.pesId"));
		return crit.list();
	}

}
