package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.ImpostoDeRenda;

public class ImpostoDeRendaDAOHibernate implements ImpostoDeRendaDAO {
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(ImpostoDeRenda impostoDeRenda) {
		this.session.save(impostoDeRenda);

	}

	@Override
	public void atualizar(ImpostoDeRenda impostoDeRenda) {
		this.session.update(impostoDeRenda);

	}

	@Override
	public void excluir(ImpostoDeRenda impostoDeRenda) {
		this.session.delete(impostoDeRenda);

	}

	@Override
	public ImpostoDeRenda carregar(Integer impRenId) {
		return (ImpostoDeRenda)this.session.get(ImpostoDeRenda.class, impRenId);
	}

	@Override
	public ImpostoDeRenda buscarPorImpostoDeRenda(Integer mes, Integer ano) {
		String hql="select b from ImpostoDeRenda b where b.impRenAno = :impRenAno"
				+ " and b.impRenMes = :impRenMes";
		Query consulta= this.session.createQuery(hql);
		consulta.setInteger("impRenMes", mes);
		consulta.setInteger("impRenAno", ano);
		return (ImpostoDeRenda) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ImpostoDeRenda> listar() {
		Criteria crit= this.session.createCriteria(ImpostoDeRenda.class);
		crit.addOrder(Order.asc("impRenId"));
		// eliminando resultados repetidos
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(ImpostoDeRenda impostoDeRenda) {
		  int cont=0;
			
			Criteria crit= this.session.createCriteria(ImpostoDeRenda.class);		
			crit.createAlias("faixaImpostoDeRendas", "loc").add(Restrictions.eq("loc.impostoDeRenda.impRenId", impostoDeRenda.getImpRenId()));
			
			List<ImpostoDeRenda> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}
			return false;
	}

}
