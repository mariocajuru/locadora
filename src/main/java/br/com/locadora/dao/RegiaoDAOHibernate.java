package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Regiao;
import br.com.locadora.modelo.RegiaoCoordenada;

public class RegiaoDAOHibernate implements RegiaoDAO {

	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public void salvar(Regiao regiao) {
		this.session.save(regiao);

	}

	@Override
	public void atualizar(Regiao regiao) {
		this.session.update(regiao);

	}

	@Override
	public void excluir(Regiao regiao) {
		this.session.delete(regiao);

	}

	@Override
	public Regiao carregar(Integer regId) {
		return (Regiao)this.session.get(Regiao.class, regId);
	}

	@Override
	public Regiao buscarPorRegiao(String regiao) {
		String hql="select s from Regiao s where s.regNome = :regiao";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("regiao", regiao);
		return (Regiao) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Regiao> listar() {
		return this.session.createCriteria(Regiao.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Bairro> carregarBairrosPorRegiao(Regiao regiao) {
		Criteria crit = session.createCriteria(Bairro.class);
		crit.add(Restrictions.eq("regiao",regiao));
		crit.addOrder(Order.asc("baiNome"));
		List<Bairro> listaBairro=crit.list();
		return listaBairro;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(Regiao regiao) {
		int cont=0;		
		Criteria crit= this.session.createCriteria(Regiao.class);		
		crit.createAlias("bairros", "bai").add(Restrictions.eq("bai.regiao.regId", regiao.getRegId()));
		List<Regiao> lista=crit.list();
		cont=lista.size();	

		if(cont==0){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegiaoCoordenada> carregarCoordenadasPorRegiao(Regiao regiao) {
		Criteria crit= this.session.createCriteria(RegiaoCoordenada.class);	
		crit.add(Restrictions.eq("regiao.regId", regiao.getRegId()));
		List<RegiaoCoordenada> lista=crit.list();
		return lista;
	}
	
	public void salvarCoordenada(RegiaoCoordenada regiaoCoordenada){
		this.session.save(regiaoCoordenada);
	}
	
	public void excluirCoordenada(RegiaoCoordenada regiaoCoordenada){
		this.session.delete(regiaoCoordenada);
	}
	
	public RegiaoCoordenada carregarRegiaoCoordenada(Integer id){
		return (RegiaoCoordenada)this.session.get(RegiaoCoordenada.class, id);
	}
}
