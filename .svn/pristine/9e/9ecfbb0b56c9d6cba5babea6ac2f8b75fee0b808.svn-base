package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Caracteristicas;
import br.com.locadora.modelo.FormaPagamento;

public class FormaPagamentoDAOHibernate implements FormaPagamentoDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(FormaPagamento formaPagamento) {
		this.session.save(formaPagamento);

	}

	@Override
	public void atualizar(FormaPagamento formaPagamento) {
		this.session.update(formaPagamento);

	}

	@Override
	public void excluir(FormaPagamento formaPagamento) {
		this.session.delete(formaPagamento);

	}

	@Override
	public FormaPagamento carregar(Integer codigo) {
		return (FormaPagamento) this.session.get(FormaPagamento.class, codigo);
	}

	@Override
	public FormaPagamento buscarFormaPagamento(String formaPagamento) {
		String hql="select b from FormaPagamento b where b.forPagNome = :formaPagamento";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("formaPagamento", formaPagamento);
		return (FormaPagamento) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<FormaPagamento> listar() {
		return this.session.createCriteria(FormaPagamento.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(FormaPagamento formaPagamento) {
		int cont=0;		
		Criteria crit= this.session.createCriteria(FormaPagamento.class);		
		crit.createAlias("dadosBancariosProprietarios", "imoc").add(Restrictions.eq("imoc.formaPagamento.forPagId", formaPagamento.getForPagId()));
		List<Caracteristicas> lista=crit.list();
		cont=lista.size();

		if(cont==0){
			return true;
		}
		return false;
	}

}
