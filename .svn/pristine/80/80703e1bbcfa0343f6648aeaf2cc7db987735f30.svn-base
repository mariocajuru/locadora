package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.ContasFinanceiro;
import br.com.locadora.modelo.GrupoDeContas;

public class ContasFinanceiroDAOHibernate implements ContasFinanceiroDAO {
	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(ContasFinanceiro centroDeCusto) {
		this.session.save(centroDeCusto);

	}

	@Override
	public void atualizar(ContasFinanceiro centroDeCusto) {
		this.session.update(centroDeCusto);

	}

	@Override
	public void excluir(ContasFinanceiro centroDeCusto) {
		this.session.delete(centroDeCusto);

	}

	@Override
	public ContasFinanceiro carregar(Integer conFinId) {
		return (ContasFinanceiro)this.session.get(ContasFinanceiro.class, conFinId);
	}

	@Override
	public ContasFinanceiro buscarContasFinanceiro(String centroDeCusto) {
		String hql="select b from ContasFinanceiro b where b.conFinNome = :centroDeCusto";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("centroDeCusto", centroDeCusto);
		return (ContasFinanceiro) consulta.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContasFinanceiro> listar() {
		Criteria crit= this.session.createCriteria(ContasFinanceiro.class);
		crit.addOrder(Order.asc("conFinId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(ContasFinanceiro centroDeCusto) {
		 int cont=0;
			
			Criteria crit= this.session.createCriteria(ContasFinanceiro.class);		
			crit.createAlias("locacaos", "loc").add(Restrictions.eq("loc.centroDeCusto.conFinId", centroDeCusto.getConFinId()));
			
			List<ContasFinanceiro> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}
			return false;
	}
	@Override
	public ContasFinanceiro carregarContasFinanceiroCredito(
			GrupoDeContas grupoDeContas) {
		ContasFinanceiro contasFinanceiro=new ContasFinanceiro();
		Criteria crit= this.session.createCriteria(ContasFinanceiro.class);
		crit.createAlias("grupoFinanceiroCredito", "valor");
		crit.add(Restrictions.eq("valor.gruConId", grupoDeContas.getGruConId()));
		// eliminando resultados repetidos
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		contasFinanceiro= (ContasFinanceiro) crit.uniqueResult();
		return contasFinanceiro;
	}
	@Override
	public ContasFinanceiro carregarContasFinanceiroDebito(
			GrupoDeContas grupoDeContas) {
		ContasFinanceiro contasFinanceiro=new ContasFinanceiro();
		Criteria crit= this.session.createCriteria(ContasFinanceiro.class);
		crit.createAlias("grupoFinanceiroDebito", "valor");
		crit.add(Restrictions.eq("valor.gruConId", grupoDeContas.getGruConId()));
		// eliminando resultados repetidos
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		contasFinanceiro= (ContasFinanceiro) crit.uniqueResult();
		return contasFinanceiro;
	}

}
