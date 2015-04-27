package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Banco;

public class BancoDAOHibernate implements BancoDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(Banco banco) {
		this.session.save(banco);

	}

	@Override
	public void atualizar(Banco banco) {
		this.session.update(banco);

	}

	@Override
	public void excluir(Banco banco) {
		this.session.delete(banco);
	}

	@Override
	public Banco carregar(Integer banId) {
		return (Banco)this.session.get(Banco.class, banId);
	}

	@Override
	public Banco buscarPorBanco(String banco) {
		String hql="select b from Banco b where b.banNome = :banco";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("banco", banco);
		return (Banco) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Banco> listar() {
		Criteria crit= this.session.createCriteria(Banco.class);
		crit.addOrder(Order.asc("banId"));
		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(Banco banco) {
		int cont=0;

		Criteria crit= this.session.createCriteria(Banco.class);		
		crit.createAlias("dadosBancariosProprietarios", "dad").add(Restrictions.eq("dad.banco.banId", banco.getBanId()));		
		List<Banco> lista=crit.list();
		cont=lista.size();

		if(cont==0){
			return true;
		}
		return false;
	}
		

}
