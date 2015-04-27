package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Iptu;
import br.com.locadora.modelo.Locacao;

public class IptuDAOHibernate implements IptuDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(Iptu iptu) {
		this.session.save(iptu);

	}

	@Override
	public void atualizar(Iptu iptu) {
		this.session.update(iptu);

	}

	@Override
	public void excluir(Iptu iptu) {
		this.session.delete(iptu);

	}

	@Override
	public Iptu carregar(Integer iptId) {
		return (Iptu)this.session.get(Iptu.class, iptId);
	}

	public Iptu buscarPorAnoIptu(Integer iptuAno) {
		String hql="select b from Iptu b where b.iptAno = :iptu";
		Query consulta= this.session.createQuery(hql);
		consulta.setInteger("iptu", iptuAno);
		return (Iptu) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Iptu> listar() {
		Criteria crit= this.session.createCriteria(Iptu.class);
		crit.addOrder(Order.asc("iptId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(Iptu iptu) {
		 int cont=0;
			
			Criteria crit= this.session.createCriteria(Iptu.class);		
			crit.createAlias("locacaos", "loc").add(Restrictions.eq("loc.statusLocacao.staLocId", iptu.getIptId()));
			
			List<Iptu> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}
			return false;
	}
	@SuppressWarnings("unchecked")
	public List<Iptu> carregarPorLocacao(Locacao locacao){
		Criteria crit= this.session.createCriteria(Iptu.class);	
		crit.add(Restrictions.isNotNull("locacao")).add(Restrictions.eq("locacao.locId", locacao.getLocId()));
		crit.addOrder(Order.asc("iptId"));
		List<Iptu> lista=crit.list();
		return lista;
	}

}
