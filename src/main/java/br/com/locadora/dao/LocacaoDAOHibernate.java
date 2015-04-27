package br.com.locadora.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Fiador;
import br.com.locadora.modelo.Locacao;
import br.com.locadora.web.util.GenericBean;

public class LocacaoDAOHibernate extends GenericBean implements LocacaoDAO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4137246336265972865L;
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	
	public void salvar(Locacao locacao) {
		this.session.save(locacao);
	}

	@Override
	public void atualizar(Locacao locacao) {
		this.session.update(locacao);
	}

	@Override
	public void excluir(Locacao locacao) {
		this.session.delete(locacao);
	}

	@Override
	public Locacao carregar(Integer locId) {
		return (Locacao) this.session.get(Locacao.class, locId);
	}

	@Override
	public Locacao buscarPorLocacao(String locacao) {
		String hql = "select s from Locacao s where s.pessoa.pesName = :locacao";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("locacao", locacao);
		
		return (Locacao) consulta.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Locacao> listar() {
		Criteria crit= this.session.createCriteria(Locacao.class);
		crit.addOrder(Order.asc("locId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Fiador> carregarFiadores(Locacao locacao) {
	/*	Query c = session.createQuery("SELECT p FROM Fiador AS l " +  
	            "INNER JOIN l.FIADOR_LOCACAO g WHERE g.LOC_ID = " +   
	            locacao.getLocId());  
		    List<Fiador> beans = c.list();*/
		Criteria crit = session.createCriteria(Fiador.class);
		crit.createAlias("locacaos", "l");
		crit.add(Restrictions.eq("l.locId", locacao.getLocId()));
		List<Fiador> lista=crit.list();
		    return lista;
	}
	/*@SuppressWarnings("unchecked")
	@Override
	public List<SituacaoLocacao> carregarSituacaoLocacao(Locacao locacao) {
		Criteria crit = session.createCriteria(SituacaoLocacao.class);
		crit.createAlias("locacaos", "l");
		crit.add(Restrictions.eq("l.locId", locacao.getLocId()));
		List<SituacaoLocacao> lista=crit.list();
		    return lista;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PermissoesLocacao> carregarPermissoesLocacao(Locacao locacao) {
		Criteria crit = session.createCriteria(PermissoesLocacao.class);
		crit.createAlias("locacaos", "l");
		crit.add(Restrictions.eq("l.locId", locacao.getLocId()));
		List<PermissoesLocacao> lista=crit.list();
		    return lista;
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<Locacao> contratosVencimentroMes(Integer mes) {
		Calendar inicioMes = Calendar.getInstance();
		inicioMes.set(Calendar.YEAR, 2000);
		inicioMes.set(Calendar.MONTH,0);
		inicioMes.set(Calendar.DAY_OF_MONTH, 1);
		
		Calendar fimMes = Calendar.getInstance();
		fimMes.set(Calendar.MONTH, mes-1);
		fimMes.setTime(super.ultimoDiaMes(fimMes.getTime()));
		
		/*SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy"); 		
		System.out.println("Data Inicio: "+fmt.format(inicioMes.getTime())+" Data Fim: "+ fmt.format(fimMes.getTime()));*/

		Criteria crit = session.createCriteria(Locacao.class);
		crit.addOrder(Order.asc("locId"));
		crit.add(Restrictions.between("locLancamentoMesAno", inicioMes.getTime(),fimMes.getTime()));
		List<Locacao> lista=crit.list();
		return lista;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}