package br.com.locadora.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.IndicesReajustes;
import br.com.locadora.modelo.ValorIndiceReajuste;

public class ValorIndiceReajusteDAOHibernate implements ValorIndiceReajusteDAO {

	private Session session;
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(ValorIndiceReajuste valorIndiceReajuste) {
		this.session.save(valorIndiceReajuste);

	}

	@Override
	public void atualizar(ValorIndiceReajuste valorIndiceReajuste) {
		this.session.update(valorIndiceReajuste);

	}

	@Override
	public void excluir(ValorIndiceReajuste valorIndiceReajuste) {
		this.session.delete(valorIndiceReajuste);

	}

	@Override
	public ValorIndiceReajuste carregar(Integer valIndReaId) {
		return (ValorIndiceReajuste)this.session.get(ValorIndiceReajuste.class, valIndReaId);
	}

	@Override
	public ValorIndiceReajuste buscarPorValorIndiceReajuste(String valor) {
		String hql="select b from ValorIndiceReajuste b where b.valIndReaValor = :valor";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("valor", valor);
		return (ValorIndiceReajuste) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ValorIndiceReajuste> listar() {
		Criteria crit= this.session.createCriteria(ValorIndiceReajuste.class);
		crit.addOrder(Order.asc("valIndReaId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ValorIndiceReajuste> listarPorIndicesReajuste(
			IndicesReajustes indicesReajustes) {
		Criteria crit= this.session.createCriteria(ValorIndiceReajuste.class);
		crit.add(Restrictions.eq("indicesReajustes.indReaId", indicesReajustes.getIndReaId()));
		crit.addOrder(Order.asc("valIndReaData"));
		return crit.list();
	}
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public ValorIndiceReajuste buscarValorIndicePorData(
			IndicesReajustes indicesReajustes, Date data) {
/*		SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
		
		System.out.println(dateFormat.format(data));
*/		
		List<ValorIndiceReajuste> lista=new ArrayList<ValorIndiceReajuste>();
		List<ValorIndiceReajuste> lista2=new ArrayList<ValorIndiceReajuste>();
		
		Criteria crit= this.session.createCriteria(ValorIndiceReajuste.class);
		crit.add(Restrictions.eq("indicesReajustes.indReaId", indicesReajustes.getIndReaId()));
		crit.addOrder(Order.asc("valIndReaData"));
		crit.add(Restrictions.le("valIndReaData", data));
		lista=crit.list();
		
		Criteria crit2= this.session.createCriteria(ValorIndiceReajuste.class);
		crit2.add(Restrictions.eq("indicesReajustes.indReaId", indicesReajustes.getIndReaId()));
		crit2.addOrder(Order.asc("valIndReaData"));
		crit2.add(Restrictions.ge("valIndReaData", data));
		lista2=crit2.list();
		
		if(lista==null)
			lista=new ArrayList<ValorIndiceReajuste>();
		
		if(lista2==null)
			lista2=new ArrayList<ValorIndiceReajuste>();
		
		ValorIndiceReajuste valorIndiceReajusteTemp1=new ValorIndiceReajuste();
			for(ValorIndiceReajuste v: lista){
				valorIndiceReajusteTemp1=lista.get(lista.size()-1);
				break;
			}
			
			ValorIndiceReajuste valorIndiceReajusteTemp2=new ValorIndiceReajuste();
			for(ValorIndiceReajuste v: lista2){
				if(lista.size()==0){
					valorIndiceReajusteTemp2=lista2.get(0);
					break;
				}
				valorIndiceReajusteTemp2=v;
				break;
			}
			
			lista=new ArrayList<ValorIndiceReajuste>();
			if((valorIndiceReajusteTemp1!=null)&&(valorIndiceReajusteTemp1.getValIndReaId()!=0))
			lista.add(valorIndiceReajusteTemp1);
			
			if((valorIndiceReajusteTemp2!=null)&&(valorIndiceReajusteTemp2.getValIndReaId()!=0))
			lista.add(valorIndiceReajusteTemp2);
			
			
			if(lista.size()!=0){
				return lista.get(0);
			}
			
			if(lista2.size()!=0){
				return lista2.get(0);
			}

		
		return new ValorIndiceReajuste();
	}
	
}
