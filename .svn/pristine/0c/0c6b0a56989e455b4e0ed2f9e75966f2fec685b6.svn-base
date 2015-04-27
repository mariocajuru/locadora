package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.CentroDeCusto;
import br.com.locadora.modelo.GrupoDeContas;

public class CentroDeCustoDAOHibernate implements CentroDeCustoDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(CentroDeCusto centroDeCusto) {
		this.session.save(centroDeCusto);

	}

	@Override
	public void atualizar(CentroDeCusto centroDeCusto) {
		this.session.update(centroDeCusto);

	}

	@Override
	public void excluir(CentroDeCusto centroDeCusto) {
		this.session.delete(centroDeCusto);

	}

	@Override
	public CentroDeCusto carregar(Integer cenCusId) {
		return (CentroDeCusto)this.session.get(CentroDeCusto.class, cenCusId);
	}

	@Override
	public CentroDeCusto buscarPorCentroDeCusto(String centroDeCusto) {
		String hql="select b from CentroDeCusto b where b.cenCusNome = :centroDeCusto";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("centroDeCusto", centroDeCusto);
		return (CentroDeCusto) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CentroDeCusto> listar() {
		Criteria crit= this.session.createCriteria(CentroDeCusto.class);
		crit.addOrder(Order.asc("cenCusId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(CentroDeCusto centroDeCusto) {
		 int cont=0;
			
			Criteria crit= this.session.createCriteria(CentroDeCusto.class);		
			crit.createAlias("locacaos", "loc").add(Restrictions.eq("loc.centroDeCusto.cenCusId", centroDeCusto.getCenCusId()));
			
			List<CentroDeCusto> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}
			return false;
	}
	@Override
	public CentroDeCusto carregarPorGrupoDeContas(GrupoDeContas grupoDeContas) {
		// EM teste
		CentroDeCusto centroDeCusto=null;
		return centroDeCusto;
	}

}
