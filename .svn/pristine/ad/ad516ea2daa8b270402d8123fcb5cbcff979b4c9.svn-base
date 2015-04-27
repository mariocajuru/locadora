package br.com.locadora.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.locadora.modelo.ChaveNoQuadro;
import br.com.locadora.modelo.EmprestimoChave;
import br.com.locadora.modelo.Imovel;

public class ChaveNoQuadroDAOHibernate implements ChaveNoQuadroDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void salvar(ChaveNoQuadro chaveNoQuadro) {
		this.session.saveOrUpdate(chaveNoQuadro);

	}

	@Override
	public void atualizar(ChaveNoQuadro chaveNoQuadro) {
		this.session.update(chaveNoQuadro);

	}

	@Override
	public void excluir(ChaveNoQuadro chaveNoQuadro) {
		this.session.delete(chaveNoQuadro);

	}

	@Override
	public ChaveNoQuadro carregar(Integer codigo) {
		return (ChaveNoQuadro) this.session.get(ChaveNoQuadro.class, codigo);
	}

	@Override
	public ChaveNoQuadro buscarChaveNoQuadro(int idQuadro, int idImovel) {
		Criteria crit = session.createCriteria(ChaveNoQuadro.class);
		crit.add(Restrictions.eq("id.quaId", idQuadro));
		crit.add(Restrictions.eq("id.imoId", idImovel));
		return (ChaveNoQuadro) crit.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ChaveNoQuadro> listar() {
		return this.session.createCriteria(ChaveNoQuadro.class).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ChaveNoQuadro> carregarChaveNoQuadroPorFilial(Integer idQuadro) {
		List<ChaveNoQuadro> resultado = new ArrayList<ChaveNoQuadro>();
		Criteria crit = session.createCriteria(ChaveNoQuadro.class);
		crit.add(Restrictions.eq("id.quaId", idQuadro));
		resultado = crit.list();
	
		return resultado;
	}
	@Override
	public ChaveNoQuadro buscarChaveAtravesDoEmprestimo(
			EmprestimoChave emprestimoChave) {
		Criteria crit = session.createCriteria(ChaveNoQuadro.class);
		crit.add(Restrictions.eq("emprestimoChave.empId", emprestimoChave.getEmpId()));
		return (ChaveNoQuadro) crit.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ChaveNoQuadro> carregarChaveNoQuadroPorImovel(Imovel imovel) {
		Criteria crit = session.createCriteria(ChaveNoQuadro.class);
		crit.add(Restrictions.eq("imovel", imovel));
		List<ChaveNoQuadro> lista=crit.list();
		return lista;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Imovel> carregarImoveisPorPosicao(int posicao) {
		Criteria crit = session.createCriteria(ChaveNoQuadro.class);
		crit.add(Restrictions.eq("chaQuaPosicao", posicao));
		List<Imovel> lista=crit.list();
		return lista;
	}

}
