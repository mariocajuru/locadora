package br.com.locadora.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import br.com.locadora.modelo.CaracteristicasImovelDesejado;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.ImovelCaracteristicas;
import br.com.locadora.modelo.ImovelDesejado;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.rn.ImovelCaracteristicasRN;

public class ImovelDAOHibernate implements ImovelDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}



	@Override
	public void salvar(Imovel imovel) {
		this.session.save(imovel);
	}

	@Override
	public void atualizar(Imovel imovel) {
		//imovel = (Imovel) this.session.merge(imovel);
		this.session.update(imovel);

	}

	@Override
	public void excluir(Imovel imovel) {
		this.session.delete(imovel);

	}

	@Override
	public Imovel carregar(Integer imoId) {
		return (Imovel) this.session.get(Imovel.class, imoId);
	}

	@Override
	public Imovel buscarPorImovel(String imovel) {
		String hql = "select i from Imovel i where i.imoId = :imovel";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("imovel", imovel);
		return (Imovel) consulta.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Imovel> listar() {
		return this.session.createCriteria(Imovel.class).addOrder(Order.asc("imoId")).list();
	}
	
	@Override
	public Imovel carregarImovelAtravesDoEndereco(Endereco endereco) {
		Criteria crit = session.createCriteria(Imovel.class,"i");
		crit.add(Restrictions.eq("endereco.endId", endereco.getEndId()));	
		Imovel imovel=(Imovel) crit.uniqueResult();
		return imovel;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void verificarExistenciaImovelDesejado(Imovel imoNovo){
		//this.session.beginTransaction().commit();
		Criteria crit = session.createCriteria(ImovelDesejado.class,"i");
		boolean teste=false;
		
	/*	Disjunction ou=Restrictions.disjunction();
		//Conjunction e = Restrictions.conjunction();// representar uma expressÃ£o
		// AND
		
		crit.add(Restrictions.eq("tipoimovel.tipId", imoNovo.getTipoimovel().getTipId()));
		//crit.add(Restrictions.eq("tipoimovel.tipId", imoNovo.getTipoimovel().getTipId()));
		
		//TODO: Quando for pesquisar o endereço de imovel desejado, tomar cuidado com o endereço de imovel desejado, por há cadastro null
		
		crit.createCriteria("enderecoImovelDesejado", "end");
		ou.add(Restrictions.ilike("end.endImoDesNome",imoNovo.getEndereco().getEndNome(),MatchMode.ANYWHERE));
		
		crit.createCriteria("end.cidade", "cid");
		ou.add(Restrictions.eq("cid.cidId",imoNovo.getEndereco().getCidade().getCidId()));
		
		crit.createCriteria("end.bairro", "bai");
		ou.add(Restrictions.eq("bai.baiId",imoNovo.getEndereco().getBairro().getBaiId()));*/
		
		//crit.add(Restrictions.between("valor", imoDesValorAluguelDe, imoDesValorAluguelAte));
		
		ImovelCaracteristicasRN imovelCaracteristicasRN = new ImovelCaracteristicasRN();
		List<ImovelCaracteristicas> a = imovelCaracteristicasRN.listaImovelCaracteristicas(imoNovo);
		
		// esse FOR verifica os imoveis que contem quartos e numeros de quartos iguais aos imoveis desejados
		for(ImovelCaracteristicas car: a){
			if(car.getCaracteristicas().getCarNome().equals("QUARTOS")){
				crit.createCriteria("caracteristicasImovelDesejados", "c");
				crit.createAlias("c.caracteristicas", "cCaract");
				crit.add(Subqueries.exists(DetachedCriteria.forClass(CaracteristicasImovelDesejado.class,"d")
						.setProjection(Projections.id())
						.add(Restrictions.eq("cCaract.carNome", "QUARTOS"))
						.add(Restrictions.eq("c.carImoDesQtd", car.getImoCarQtd()))));
				teste=true;
			}
		}
		
		
		
	//	crit.add(ou);
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<ImovelDesejado> listaImoveis=crit.list();
		for(ImovelDesejado desejado: listaImoveis){
			if(teste){
				System.out.println("Codigo imovel desejado: "+desejado.getImoDesId()+" Imovel normal: "+imoNovo.getImoId());
			}
		}
		
	/*	SELECT IMOVEL_CARACTERISTICAS.IMO_ID as CodigoImovel, IMOVEL_CARACTERISTICAS.IMO_CAR_QTD as QTD
		  FROM [renovarsistemas].[dbo].[IMOVEL_CARACTERISTICAS]
		where IMOVEL_CARACTERISTICAS.IMO_CAR_QTD=4
		 and IMOVEL_CARACTERISTICAS.CAR_ID=1*/
		
/*		for(ImovelDesejado i: listaImoveis){
			System.out.println("Imovel desejado na tabela: "+i.getImoDesId());
			
			List<Email>listaEmail=new EmailRN().carregarPessoa(i.getPessoa());
			GmailBean bean=new GmailBean();
			for(Email email: listaEmail){
			bean.setAssunto("Envio de um novo imóvel encontrado");
			bean.setMensagem("Imovel encontrado: "+ i.getImoDesId());
			bean.setPara(email.getEmaEndereco());
			bean.enviarEmail();}
		}*/
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Imovel> listarImoveisPorPropritario(Pessoa pessoa) {
		Criteria crit = session.createCriteria(Imovel.class,"i");
		crit.addOrder(Order.asc("imoId"));
		crit.createAlias("proprietarios", "pro");
		crit.add(Restrictions.eq("pro.id.pesId", pessoa.getPesId()));
		List<Imovel> lista=crit.list();
		if(lista==null){
			lista=new ArrayList<Imovel>();
		}
		return lista;
	}

}
