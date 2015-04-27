package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Pessoa;

public class PessoaDAOHibernate implements PessoaDAO {

	private Session session;
	public void setSession(Session session) {
	this.session = session;
}

	@Override
	public void salvar(Pessoa pessoa) {
		this.session.save(pessoa);

	}

	@Override
	public void atualizar(Pessoa pessoa) {
		this.session.update(pessoa);

	}

	@Override
	public void excluir(Pessoa pessoa) {
		this.session.delete(pessoa);

	}

	@Override
	public Pessoa carregar(Integer pesId) {
		return (Pessoa)this.session.get(Pessoa.class, pesId);
	}

	@Override
	public Pessoa buscarPorPessoa(String pessoa) {
		String hql="select c from Pessoa c where c.pesNome = :pessoa";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("pessoa", pessoa);
		return (Pessoa) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> listar() {
		return this.session.createCriteria(Pessoa.class).addOrder(Order.asc("pesId")).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> listaProprietarios() {
		Criteria crit = session.createCriteria(Pessoa.class);
		crit.add(Restrictions.eq("pesProprietario", true));
		
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> carregarListaPessoasNaoPreCadastro() {
		Criteria crit = session.createCriteria(Pessoa.class);
		crit.add(Restrictions.eq("pesPreCadastro", false));
		crit.addOrder(Order.asc("pesId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> carregarListaPessoasPreCadastro() {
		Criteria crit = session.createCriteria(Pessoa.class);
		crit.add(Restrictions.eq("pesPreCadastro", true));
		crit.addOrder(Order.asc("pesId"));
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Pessoa carregarPessoaPorCPF_CNPJ(Pessoa pessoa) {
		Criteria crit = session.createCriteria(Pessoa.class);
		crit.add(Restrictions.eq("pesCpfCnpj", pessoa.getPesCpfCnpj()));
		crit.addOrder(Order.asc("pesId"));
		Pessoa pes= (Pessoa) crit.uniqueResult();
		if(pes==null){
			List<Pessoa> lista=crit.list();
		if(lista.size()>0)
			pes=lista.get(0);
		}
		return pes;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Pessoa carregarPessoaPorCPF_CNPJ_Com_Caracteres(String cpf_cnpf) {
		Criteria crit = session.createCriteria(Pessoa.class);
		crit.add(Restrictions.like("pesCpfCnpj", cpf_cnpf, MatchMode.START));
		crit.addOrder(Order.asc("pesId"));
		List<Pessoa> lista=crit.list();
		Pessoa pes=lista.get(0);
		/*Pessoa pes= (Pessoa) crit.uniqueResult();
		if(pes==null){
			List<Pessoa> lista=crit.list();
		if(lista.size()>0)
			pes=lista.get(0);
		}*/
		return pes;
	}

}
