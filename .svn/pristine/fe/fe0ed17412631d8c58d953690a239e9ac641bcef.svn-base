package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Proprietario;

public class ProprietarioDAOHibernate implements ProprietarioDAO {

	private Session session;
	public void setSession(Session session) {
	this.session = session;
}

	@Override
	public void salvar(Proprietario proprietario) {
		this.session.saveOrUpdate(proprietario);

	}

	@Override
	public void atualizar(Proprietario proprietario) {
		this.session.update(proprietario);

	}

	@Override
	public void excluir(Proprietario proprietario) {
		this.session.delete(proprietario);

	}

	@Override
	public Proprietario carregar(Integer proId) {
		return (Proprietario)this.session.get(Proprietario.class, proId);
	}

	@Override
	public Proprietario buscarPorProprietario(String proprietario) {
		String hql="select p from Proprietario p where p.proNome = :proprietario";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("proprietario", proprietario);
		return (Proprietario) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Proprietario> listar() {
		return this.session.createCriteria(Proprietario.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Proprietario> carregarProprietarios(Imovel imovel) {

		Criteria c = session.createCriteria(Proprietario.class);
		c.add(Restrictions.eq("imovel.imoId", imovel.getImoId()));

		return (List<Proprietario>) c.list();
	}

	@Override
	public Proprietario carregarProprietarioPorImovelPessoa(Pessoa pessoa,
			Imovel imovel) {
		Criteria c = session.createCriteria(Proprietario.class);
		c.add(Restrictions.eq("imovel.imoId", imovel.getImoId()));
		c.add(Restrictions.eq("pessoa.pesId", pessoa.getPesId()));
		Proprietario proprietario=(Proprietario) c.uniqueResult();
		return proprietario;
	}

}
