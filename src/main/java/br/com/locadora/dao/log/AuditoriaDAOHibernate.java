package br.com.locadora.dao.log;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.criteria.AuditConjunction;
import org.hibernate.envers.query.criteria.AuditDisjunction;

import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.log.Auditoria;

public class AuditoriaDAOHibernate implements AuditoriaDAO {
	private Session session;
	public void setSession(Session session) {
	this.session = session;
}

	@Override
	public List<Object[]> listar() {
		
		//http://docs.jboss.org/envers/api-new/org/hibernate/envers/query/AuditEntity.html
		//https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/6/html/Development_Guide/sect-Queries.html
		//http://stackoverflow.com/questions/6946101/get-complete-envers-revisions-where-a-particular-object-is-affected
		
		//listaDeletados();
		listaTudo();
		
		List<Object[]> lista=listaTudo();
		return lista;
			
}
	@SuppressWarnings("unchecked")
	public List<Object[]> listaTudo(){
		//http://alvinalexander.com/java/jwarehouse/hibernate/hibernate-envers/src/test/java/org/hibernate/envers/test/integration/query/RevisionConstraintQuery.java.shtml
		
		//http://trabalhandocomti.blogspot.com/2013/04/geracao-de-log-e-auditoria-de-dados.html
		
		
		//http://stackoverflow.com/questions/11449611/how-not-to-audit-a-join-table-and-related-entities-using-hibernate-envers/11453076#11453076
		AuditReader reader = AuditReaderFactory.get(this.session);
		AuditQuery query = reader.createQuery().forRevisionsOfEntity(Bairro.class, false, true)
			    .add(AuditEntity.revisionNumber().eq(2));
		
		//query.add(AuditEntity.revisionType().eq(RevisionType.DEL));
		//query.add(AuditEntity.revisionType().eq(RevisionType.ADD));
				//.addProjection(AuditEntity.revisionNumber().max());
		List<Object[]> results =query.getResultList();
		
		/*	SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		for(Object[] objects: results ){
			if(objects[1] instanceof Auditoria){
				Auditoria auditoria=(Auditoria) objects[1];
				System.out.print(auditoria.getNome()+" : "+fmt.format(auditoria.getRevisionDate()));
			}
			
			if(objects[0] instanceof Bairro){
				Bairro bairro=(Bairro) objects[0];
				System.out.print("  - Bairro: "+bairro.getBaiId()+" : "+bairro.getBaiNome());
			}
			
			if(objects[2] instanceof RevisionType){
				RevisionType revisionType=(RevisionType) objects[2];
				System.out.println(" - "+revisionType);
			}
		}*/
		
/*		int revision = 23;
		AuditReader reader = AuditReaderFactory.get(this.session);
		AuditQuery query = reader.createQuery().forRevisionsOfEntity(Bairro.class, false, true)
				.addProjection(AuditEntity.revisionNumber().min());			
		query.add(AuditEntity.id().eq(revision));
		query.add(AuditEntity.revisionNumber().gt(42));
		Number teste = (Number)query.getSingleResult();*/
		
		return results;
		
	}
	
	@SuppressWarnings("unused")
	public void listaDeletados(){
		List<Auditoria> listaAuditoria=new ArrayList<Auditoria>();
		List<Bairro> listaBairros=new ArrayList<Bairro>();
		List<RevisionType> listaRevisionType=new ArrayList<RevisionType>();
		
		AuditDisjunction disjunction=AuditEntity.disjunction();//or
		AuditConjunction conjunction=AuditEntity.conjunction();//  AND

		AuditReader reader = AuditReaderFactory.get(this.session);
		AuditQuery query = reader.createQuery().forRevisionsOfEntity(Bairro.class, false, true);

		query.add(AuditEntity.revisionProperty("loguin").eq("admin"));

		query.add(AuditEntity.revisionType().eq(RevisionType.DEL));
		/*query.add(AuditEntity.revisionType().ge(RevisionType.ADD)); 
				query.add(AuditEntity.revisionType().eq(RevisionType.MOD));*/

		//	query.add(conjunction);
		//	query.add(disjunction);
		
		@SuppressWarnings("unchecked")
		List<Object[]> results =query.getResultList();
		
		for(Object[] objects: results ){
			listaAuditoria.add((Auditoria) objects[1]);
			listaBairros.add((Bairro) objects[0]);
			listaRevisionType.add((RevisionType) objects[2]);
		}
		
		for(Auditoria auditoria: listaAuditoria)
			System.out.println(auditoria.getNome()+" Data: "+auditoria.getRevisionDate());
		
		for(Bairro bairro: listaBairros)
			System.out.println("Bairro Nome: "+bairro.getBaiNome());
		
		for(RevisionType revisionType: listaRevisionType)
			System.out.println(revisionType);
	}

}
