package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.DadosBancariosProprietario;
import br.com.locadora.modelo.Pessoa;

public class DadosBancariosProprietarioDAOHibernate implements
		DadosBancariosProprietarioDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(DadosBancariosProprietario dadosBancariosProprietario) {
		this.session.save(dadosBancariosProprietario);

	}

	@Override
	public void atualizar(DadosBancariosProprietario dadosBancariosProprietario) {
		this.session.update(dadosBancariosProprietario);

	}

	@Override
	public void excluir(DadosBancariosProprietario dadosBancariosProprietario) {
		this.session.delete(dadosBancariosProprietario);

	}

	@Override
	public DadosBancariosProprietario carregar(Integer dadBanProId) {
		return (DadosBancariosProprietario) this.session.get(DadosBancariosProprietario.class, dadBanProId);
	}

	@Override
	public DadosBancariosProprietario buscarPorDadosBancariosProprietario(
			String dadosBancariosProprietario) {
		String hql = "select c from DadosBancariosProprietario c where c.cidNome = :dadosBancariosProprietario";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("dadosBancariosProprietario", dadosBancariosProprietario);
		
		return (DadosBancariosProprietario) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<DadosBancariosProprietario> listar() {
		return this.session.createCriteria(DadosBancariosProprietario.class).list();
	}

	@Override
	public DadosBancariosProprietario buscarPorPessoa(Pessoa pessoa) {
		Criteria c = session.createCriteria(DadosBancariosProprietario.class);
		c.createAlias("pessoas", "pessoa");
		c.add(Restrictions.eq("pessoa.pesId", pessoa.getPesId()));
		DadosBancariosProprietario dadosBancariosProprietario= (DadosBancariosProprietario) c.uniqueResult();
		return dadosBancariosProprietario;
	}

}
