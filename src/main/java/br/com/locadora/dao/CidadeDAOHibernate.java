package br.com.locadora.dao;

import java.text.Normalizer;
import java.util.List;




import org.hibernate.Query;
import org.hibernate.Session;

import br.com.locadora.modelo.Cidade;

public class CidadeDAOHibernate implements CidadeDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(Cidade cidade) {
		cidade.setCidNome(removerAcentos(cidade.getCidNome()));
		this.session.save(cidade);// aqui era somente o metodo saveOrUpdate	
	}

	@Override
	public void atualizar(Cidade cidade) {
		cidade.setCidNome(removerAcentos(cidade.getCidNome()));
		this.session.update(cidade);

	}

	@Override
	public void excluir(Cidade cidade) {
		this.session.delete(cidade);

	}

	@Override
	public Cidade carregar(Integer codigo) {
		return (Cidade) this.session.get(Cidade.class, codigo);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Cidade> listar() {
		return this.session.createCriteria(Cidade.class).list();
	}

	@Override
	public Cidade buscarPorCidade(String cidade) {
		cidade=removerAcentos(cidade);
		String hql = "select c from Cidade c where c.cidNome = :cidade";
		Query consulta = this.session.createQuery(hql);
		
		//TODO: em caso de erro no cadastro troque as linhas abaixo
		//consulta.setString("cidade", "DIVINÓPOLIS");
		consulta.setString("cidade", cidade);
		
		return (Cidade) consulta.uniqueResult();
	}

	public Cidade buscarPorEstado(String uf) {
		String hql = "select e from Cidade e where e.cidUf = :uf";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("uf", uf);
		return (Cidade) consulta.uniqueResult();
	}
	
	public  String removerAcentos(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

}
