package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.MensagemFuncionario;

public class MensagemFuncionarioDAOHibernate implements MensagemFuncionarioDAO {

	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(MensagemFuncionario mensagemFuncionario) {
		this.session.save(mensagemFuncionario);
	}

	@Override
	public void atualizar(MensagemFuncionario mensagemFuncionario) {
		this.session.update(mensagemFuncionario);
	}

	@Override
	public void excluir(MensagemFuncionario mensagemFuncionario) {
		this.session.delete(mensagemFuncionario);
	}

	@Override
	public MensagemFuncionario carregar(Integer menFunId) {
		return (MensagemFuncionario) this.session.get(MensagemFuncionario.class, menFunId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MensagemFuncionario> buscarPorFuncionarioVisualizada(
			Funcionario funcionario) {
		/*Criteria crit = session.createCriteria(MensagemFuncionario.class);
		crit.add(Restrictions.eq("funcionario.funId", funcionario.getFunId()));	
		crit.add(Restrictions.eq("menFunVisualizada", false));
		List<MensagemFuncionario> lista=crit.list();*/
		Query q = this.session.getNamedQuery("consultaMensagemFuncionario");
		q.setInteger("codFun", funcionario.getPesId());
		q.setBoolean("visualizada", false);
		List<MensagemFuncionario> lista = q.list();
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MensagemFuncionario> listar() {
		Criteria crit= this.session.createCriteria(MensagemFuncionario.class);
		crit.addOrder(Order.asc("menFunDataCriacao"));
		return crit.list();
	}

}
