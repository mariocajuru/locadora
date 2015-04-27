package br.com.locadora.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.Ramoatuacao;

public class RamoatuacaoDAOHibernate implements RamoatuacaoDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(Ramoatuacao ramo) {
		this.session.save(ramo);

	}

	@Override
	public void atualizar(Ramoatuacao ramo) {
		this.session.update(ramo);

	}

	@Override
	public void excluir(Ramoatuacao ramo) {
		this.session.delete(ramo);

	}

	@Override
	public Ramoatuacao carregar(Integer ramId) {
		return (Ramoatuacao) this.session.get(Ramoatuacao.class, ramId);
	}

	@Override
	public Ramoatuacao buscarPorRamoatuacao(String ramo) {
		String hql = "select r from Ramoatuacao r where r.ramNome = :ramo";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("ramo", ramo);
		return (Ramoatuacao) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Ramoatuacao> listar() {
		return this.session.createCriteria(Ramoatuacao.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean dependecias(Ramoatuacao ramoatuacao) {
		int cont=0;		
		Criteria crit= this.session.createCriteria(Ramoatuacao.class);		
		crit.createAlias("pessoas", "pes").add(Restrictions.eq("pes.ramoatuacao.ramId", ramoatuacao.getRamId()));
		List<Ramoatuacao> lista=crit.list();
		cont=lista.size();		
		if(cont==0){
			return true;
		}
		return false;
	}

}
