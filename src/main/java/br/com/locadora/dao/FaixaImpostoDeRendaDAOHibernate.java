package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.FaixaImpostoDeRenda;

public class FaixaImpostoDeRendaDAOHibernate implements FaixaImpostoDeRendaDAO {
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(FaixaImpostoDeRenda faixaImpostoDeRenda) {
		this.session.save(faixaImpostoDeRenda);

	}

	@Override
	public void atualizar(FaixaImpostoDeRenda faixaImpostoDeRenda) {
		this.session.update(faixaImpostoDeRenda);

	}

	@Override
	public void excluir(FaixaImpostoDeRenda faixaImpostoDeRenda) {
		this.session.delete(faixaImpostoDeRenda);

	}

	@Override
	public FaixaImpostoDeRenda carregar(Integer faiImpId) {
		return (FaixaImpostoDeRenda)this.session.get(FaixaImpostoDeRenda.class, faiImpId);
	}

	@Override
	public FaixaImpostoDeRenda buscarPorFaixaImpostoDeRenda(
			String faixaImpostoDeRenda) {
		String hql="select b from FaixaImpostoDeRenda b where b.faiImpRenFaixa = :faixaImpostoDeRenda";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("faixaImpostoDeRenda", faixaImpostoDeRenda);
		return (FaixaImpostoDeRenda) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<FaixaImpostoDeRenda> listar() {
		Criteria crit= this.session.createCriteria(FaixaImpostoDeRenda.class);
		crit.addOrder(Order.asc("faiImpRenId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(FaixaImpostoDeRenda faixaImpostoDeRenda) {
		  int cont=0;
			
			Criteria crit= this.session.createCriteria(FaixaImpostoDeRenda.class);		
			crit.createAlias("impostoDeRenda", "loc").add(Restrictions.eq("loc.faixaImpostoDeRenda.faiImpRenId", faixaImpostoDeRenda.getFaiImpRenId()));
			
			List<FaixaImpostoDeRenda> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}
			return false;
	}

}
