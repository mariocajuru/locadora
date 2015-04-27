package br.com.locadora.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.Pessoa;


public class FuncionarioDAOHibernate implements FuncionarioDAO{
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}
	@Override
	public void salvar(Funcionario funcionario) {
		this.session.save(funcionario);
		
	}

	@Override
	public void atualizar(Funcionario funcionario) {
		/*if (funcionario.getPermissao() == null || funcionario.getPermissao().size() == 0) {
			Funcionario usuarioPermissao = this.carregar(funcionario.getFunId());
			funcionario.setPermissao(usuarioPermissao.getPermissao());
			this.session.evict(usuarioPermissao);
		}*/
		/*Funcionario usuarioPermissao = this.carregar(funcionario.getFunId());
		this.session.evict(usuarioPermissao);*/
		this.session.update(funcionario);
	}

	@Override
	public void excluir(Funcionario funcionario) {
		this.session.delete(funcionario);
	}

	@Override
	public Funcionario carregar(Integer funId) {
		return (Funcionario)this.session.get(Funcionario.class, funId);
	}

	@Override
	public Funcionario buscarPorLogin(String login) {
		String hql="select p from Funcionario p join fetch p.permissao where p.funLoguin = :pessoa";
		Query consulta= this.session.createQuery(hql);
		consulta.setString("pessoa", login);
		return (Funcionario) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> listar() {
		/*Criteria criteria= this.session.createCriteria(Funcionario.class);
		criteria.add(Restrictions.eq("pessoa", pessoa));
		return criteria.list();*/
		List<Funcionario> listaFuncionarios=new ArrayList<Funcionario>();
		listaFuncionarios = this.session.createCriteria(Funcionario.class).list();
		return listaFuncionarios;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean loguinIgualEntreFuncionarios(Funcionario funcionario) {
		Criteria criteria= this.session.createCriteria(Funcionario.class);
		criteria.add(Restrictions.eq("funLoguin", funcionario.getFunLoguin()));
		List<Pessoa> p=criteria.list();
		if(p.size()>1){
			return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean pessoaCadastradaComoFuncionario(Pessoa pessoa) {
		Criteria criteria= this.session.createCriteria(Funcionario.class);
		criteria.add(Restrictions.eq("pessoa.pesId", pessoa.getPesId()));
		List<Funcionario> p=criteria.list();
		if((p!=null)&&(p.size()>=1)){
			return true;
		}
		return false;
	}
}
