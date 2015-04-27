package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.locadora.modelo.ImovelDesejado;

public class ImovelDesejadoDAOHibernate implements ImovelDesejadoDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(ImovelDesejado imovelDesejado) {
		this.session.save(imovelDesejado);

	}

	@Override
	public void atualizar(ImovelDesejado imovelDesejado) {
		this.session.update(imovelDesejado);

	}

	@Override
	public void excluir(ImovelDesejado imovelDesejado) {
		this.session.delete(imovelDesejado);

	}

	@Override
	public ImovelDesejado carregar(Integer imo_des_Id) {
		return (ImovelDesejado) this.session.get(ImovelDesejado.class, imo_des_Id);
	}

	@Override
	public ImovelDesejado buscarPorImovelDesejado(String imovelDesejado) {
		String hql = "select e from ImovelDesejado e where e.emaEndereco = :imovelDesejado";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("imovelDesejado", imovelDesejado);
		return (ImovelDesejado) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ImovelDesejado> listar() {
		return this.session.createCriteria(ImovelDesejado.class).list();
	}

}
