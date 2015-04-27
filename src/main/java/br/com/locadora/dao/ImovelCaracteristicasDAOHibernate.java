package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.ImovelCaracteristicas;
import br.com.locadora.modelo.Imovel;


public class ImovelCaracteristicasDAOHibernate implements ImovelCaracteristicasDAO {

	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(ImovelCaracteristicas  imovelCaracteristicas) {
		this.session.save(imovelCaracteristicas);

	}

	@Override
	public void atualizar(ImovelCaracteristicas imovelCaracteristicas) {
		this.session.update(imovelCaracteristicas);

	}

	@Override
	public void excluir(ImovelCaracteristicas imovelCaracteristicas) {
		this.session.delete(imovelCaracteristicas);
	}

	@Override
	public void excluirTudo() {
		String hql = "delete ImovelCaracteristicas";
		Query consulta = this.session.createQuery(hql);
		
		consulta.executeUpdate();
	}

	@Override
	public ImovelCaracteristicas carregar(Integer codigo) {
		return (ImovelCaracteristicas) this.session.get(ImovelCaracteristicas.class, codigo);
	}

	@Override
	public ImovelCaracteristicas buscarPorImovelCaracteristicas(String imovelCaracteristicas) {
		//TODO: pesquisa no hql erro 
		String hql = "select e from ImovelCaracteristicas e where e.detNome = :imovelCaracteristicas";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("imovelCaracteristicas", imovelCaracteristicas);
		return (ImovelCaracteristicas) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ImovelCaracteristicas> listar() {
		return this.session.createCriteria(ImovelCaracteristicas.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ImovelCaracteristicas> listaImovelCaracteristicas(Imovel imovel) {
		Criteria c = session.createCriteria(ImovelCaracteristicas.class);
		c.add(Restrictions.like("imovel.imoId", imovel.getImoId()));
		return (List<ImovelCaracteristicas>) c.list();
	}
}
