package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.GrupoDeContas;
import br.com.locadora.modelo.PlanoDeContas;

public class PlanoDeContasDAOHibernate implements PlanoDeContasDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(PlanoDeContas planoDeContas) {
		this.session.save(planoDeContas);

	}

	@Override
	public void atualizar(PlanoDeContas planoDeContas) {
		this.session.update(planoDeContas);

	}

	@Override
	public void excluir(PlanoDeContas planoDeContas) {
		this.session.delete(planoDeContas);

	}

	@Override
	public PlanoDeContas carregar(Integer plaConId) {
		return (PlanoDeContas)this.session.get(PlanoDeContas.class, plaConId);
	}

	@Override
	public PlanoDeContas buscarPorPlanoDeContas(String planoDeContas) {
		String hql="select b from PlanoDeContas b where b.plaConNome = :planoDeContas";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("planoDeContas", planoDeContas);
		return (PlanoDeContas) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PlanoDeContas> listar() {
		Criteria crit= this.session.createCriteria(PlanoDeContas.class);
		crit.addOrder(Order.asc("plaConId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(PlanoDeContas planoDeContas) {
		 int cont=0;
			
			Criteria crit= this.session.createCriteria(PlanoDeContas.class);		
			crit.createAlias("locacaos", "loc").add(Restrictions.eq("loc.planoDeContas.plaConId", planoDeContas.getPlaConId()));
			
			List<PlanoDeContas> lista=crit.list();
			cont=lista.size();		
			
			if(cont==0){
			return true;
			}
			return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PlanoDeContas> listarPorCredito() {
		Criteria crit= this.session.createCriteria(PlanoDeContas.class);
		crit.add(Restrictions.eq("plaConCredito", true));
		crit.addOrder(Order.asc("plaConId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PlanoDeContas> listarPorDebito() {
		Criteria crit= this.session.createCriteria(PlanoDeContas.class);
		crit.add(Restrictions.eq("plaConDebito", true));
		crit.addOrder(Order.asc("plaConId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public PlanoDeContas carregarPlanoDeContasCreditoValor(
			GrupoDeContas grupoDeContas) {
		PlanoDeContas planoDeContas=new PlanoDeContas();
		Criteria crit= this.session.createCriteria(PlanoDeContas.class);
		crit.createAlias("grupoContasValorOriginal", "valor");
		crit.add(Restrictions.eq("valor.gruConId", grupoDeContas.getGruConId()));
		// eliminando resultados repetidos
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<PlanoDeContas> lista=crit.list();
		for(PlanoDeContas p: lista){
			if(p.getPlaConCredito()==true){
				planoDeContas=p;
			}
		}
		return planoDeContas;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PlanoDeContas carregarPlanoDeContasDebitoValor(
			GrupoDeContas grupoDeContas) {
		PlanoDeContas planoDeContas=new PlanoDeContas();
		Criteria crit= this.session.createCriteria(PlanoDeContas.class);
		crit.createAlias("grupoContasValorOriginal", "valor");
		crit.add(Restrictions.eq("valor.gruConId", grupoDeContas.getGruConId()));
		// eliminando resultados repetidos
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<PlanoDeContas> lista=crit.list();
		for(PlanoDeContas p: lista){
			if(p.getPlaConDebito()==true){
				planoDeContas=p;
			}
		}
		return planoDeContas;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PlanoDeContas carregarPlanoDeContasCreditoMulta(
			GrupoDeContas grupoDeContas) {
		PlanoDeContas planoDeContas=new PlanoDeContas();
		Criteria crit= this.session.createCriteria(PlanoDeContas.class);
		crit.createAlias("grupoContasMultas", "valor");
		crit.add(Restrictions.eq("valor.gruConId", grupoDeContas.getGruConId()));
		// eliminando resultados repetidos
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<PlanoDeContas> lista=crit.list();
		for(PlanoDeContas p: lista){
			if(p.getPlaConCredito()==true){
				planoDeContas=p;
			}
		}
		return planoDeContas;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PlanoDeContas carregarPlanoDeContasDebitoMulta(
			GrupoDeContas grupoDeContas) {
		PlanoDeContas planoDeContas=new PlanoDeContas();
		Criteria crit= this.session.createCriteria(PlanoDeContas.class);
		crit.createAlias("grupoContasMultas", "valor");
		crit.add(Restrictions.eq("valor.gruConId", grupoDeContas.getGruConId()));
		// eliminando resultados repetidos
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<PlanoDeContas> lista=crit.list();
		for(PlanoDeContas p: lista){
			if(p.getPlaConDebito()==true){
				planoDeContas=p;
			}
		}
		return planoDeContas;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PlanoDeContas carregarPlanoDeContasCreditoJuros(
			GrupoDeContas grupoDeContas) {
		PlanoDeContas planoDeContas=new PlanoDeContas();
		Criteria crit= this.session.createCriteria(PlanoDeContas.class);
		crit.createAlias("grupoContasJuros", "valor");
		crit.add(Restrictions.eq("valor.gruConId", grupoDeContas.getGruConId()));
		// eliminando resultados repetidos
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<PlanoDeContas> lista=crit.list();
		for(PlanoDeContas p: lista){
			if(p.getPlaConCredito()==true){
				planoDeContas=p;
			}
		}
		return planoDeContas;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PlanoDeContas carregarPlanoDeContasDebitoJuros(
			GrupoDeContas grupoDeContas) {
		PlanoDeContas planoDeContas=new PlanoDeContas();
		Criteria crit= this.session.createCriteria(PlanoDeContas.class);
		crit.createAlias("grupoContasJuros", "valor");
		crit.add(Restrictions.eq("valor.gruConId", grupoDeContas.getGruConId()));
		// eliminando resultados repetidos
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<PlanoDeContas> lista=crit.list();
		for(PlanoDeContas p: lista){
			if(p.getPlaConDebito()==true){
				planoDeContas=p;
			}
		}
		return planoDeContas;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PlanoDeContas> listaPlanoValorPorGrupo(
			GrupoDeContas grupoDeContas) {
		Criteria crit= this.session.createCriteria(PlanoDeContas.class);
		crit.createAlias("grupoContasValorOriginal", "valor");
		crit.add(Restrictions.eq("valor.gruConId", grupoDeContas.getGruConId()));
		return crit.list();
	}

}
