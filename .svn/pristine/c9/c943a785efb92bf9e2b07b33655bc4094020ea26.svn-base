package br.com.locadora.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Caracteristicas;
import br.com.locadora.modelo.Cidade;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.ImovelCaracteristicas;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Regiao;
import br.com.locadora.modelo.Situacaoimovel;
import br.com.locadora.modelo.Tipoimovel;
import br.com.locadora.rn.CaracteriscasRN;
import br.com.locadora.web.PesquisaImovelBean.CaracteriticasTempPesquisa;

public class PesquisaDAOHibernate implements PesquisaDAO {

	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}
	@SuppressWarnings("unchecked")
	public List<Imovel> teste(List<Tipoimovel> listaTipoImovel,
			List<Bairro> listaBairro, List<Cidade> listaCidade,
			List<Caracteristicas> listaCaracteriscas, String valorMinimo,
			String valorMaximo, Integer qtdQuartos, List<Regiao> listaRegioes, int quartoDe, int quartoAte){
		List<Imovel> resultado = new ArrayList<Imovel>();
		Criteria crit = session.createCriteria(Imovel.class,"i");
		//Disjunction e=Restrictions.disjunction();
		Disjunction ou=Restrictions.disjunction();
		Conjunction e = Restrictions.conjunction();// representar uma expressÃ£o
		// AND

		crit.createCriteria("endereco", "end");
		if (listaBairro.size() != 0) {

			//utilizando pesquisa OR para bairro
			for (Bairro bai  : listaBairro) {
				ou.add(Subqueries.exists(
						DetachedCriteria.forClass(Endereco.class, "b")
						.setProjection(Projections.id())
						.add(Restrictions.eqProperty("end.bairro.baiId", "b.bairro.baiId"))
						.add(Restrictions.eq("b.bairro",bai))));
			}
			crit.add(ou);
		}

		if (listaCidade.size() != 0) {
			//utilizando pesquisa OR para cidade
			for (Cidade cid  : listaCidade) {
				ou.add(Subqueries.exists(
						DetachedCriteria.forClass(Endereco.class, "b")
						.setProjection(Projections.id())
						.add(Restrictions.eqProperty("end.cidade.cidId", "b.cidade.cidId"))
						.add(Restrictions.eq("b.cidade",cid))));
			}
			crit.add(ou);
		}

		if (listaRegioes.size() != 0) {
			crit.createAlias("end.bairro", "bai").add(Subqueries.exists(DetachedCriteria.forClass(Regiao.class,"r")
					.setProjection(Projections.id())
					.add(Restrictions.in("bai.regiao", listaRegioes))));}
		if (listaTipoImovel.size() != 0) {
			e.add(Subqueries.exists(DetachedCriteria.forClass(Tipoimovel.class,"t")
					.setProjection(Projections.id())
					.add(Restrictions.in("i.tipoimovel", listaTipoImovel))));
		}

		/*	// esse if Ã© porque a pesquisa nÃ£o dar certo com os atributos qtdQuartos e lista de detalhes juntos, tem que add um quarto Ã  lista de detalhes para dar certo. Ass: MÃ¡rio #Gambiarra
		if (!((qtdQuartos.equals(null)) || (qtdQuartos.equals(0)))) {
			Caracteristica det=new CaracteriscasRN().buscarPorDetalhesImovel("QUARTOS");
			listaCaracteriscas.add(det);
		}*/
		
		
		
		//pesquisa com base em: http://blog.caelum.com.br/divisions-com-hibernate-uso-avancado-da-criteria-api/
		crit.createCriteria("imovelCaracteristicases", "c");
		if ((listaCaracteriscas.size() != 0)&&((quartoDe == 0)) && ((quartoAte == 0))) {			
			for (Caracteristicas caract  : listaCaracteriscas) {
				e.add(Subqueries.exists(
						DetachedCriteria.forClass(ImovelCaracteristicas.class, "m")
						.setProjection(Projections.id())
						.add(Restrictions.eqProperty("i.imoId", "m.imovel.imoId"))
						.add(Restrictions.eq("m.caracteristicas",caract))));
			}
		}

		if ((listaCaracteriscas.size() != 0)&&(!(quartoDe == 0)) && (!(quartoAte == 0))) {
			Caracteristicas carat=new CaracteriscasRN().buscarPorCaracteriscas("QUARTOS");
			if(carat==null){
				return new ArrayList<Imovel>();
			}
			listaCaracteriscas.add(carat);
			//Conjunction and = Restrictions.conjunction();
			for (Caracteristicas caract  : listaCaracteriscas) {
				e.add(Subqueries.exists(
						DetachedCriteria.forClass(ImovelCaracteristicas.class, "m")
						.setProjection(Projections.id())
						.add(Restrictions.eqProperty("i.imoId", "m.imovel.imoId"))
						.add(Restrictions.eq("m.caracteristicas",caract))));
			}
			//	crit.add(and);
			crit.createAlias("c.caracteristicas", "cCaract");
			e.add(Subqueries.exists(DetachedCriteria.forClass(Caracteristicas.class,"d")
					.setProjection(Projections.id())
					.add(Restrictions.eq("cCaract.carNome", "QUARTOS")).add(Restrictions.between("c.imoCarQtd", quartoDe, quartoAte))));
		}

		if ((listaCaracteriscas.size() != 0)&&((quartoDe == 0)) && (!(quartoAte == 0))) {
			Caracteristicas carat=new CaracteriscasRN().buscarPorCaracteriscas("QUARTOS");
			if(carat==null){
				return new ArrayList<Imovel>();
			}
			listaCaracteriscas.add(carat);
			//Conjunction and = Restrictions.conjunction();
			for (Caracteristicas caract  : listaCaracteriscas) {
				e.add(Subqueries.exists(
						DetachedCriteria.forClass(ImovelCaracteristicas.class, "m")
						.setProjection(Projections.id())
						.add(Restrictions.eqProperty("i.imoId", "m.imovel.imoId"))
						.add(Restrictions.eq("m.caracteristicas",caract))));
			}
			//	crit.add(and);
			crit.createAlias("c.caracteriscas", "cCaract");
			e.add(Subqueries.exists(DetachedCriteria.forClass(Caracteristicas.class,"d")
					.setProjection(Projections.id())
					.add(Restrictions.eq("cCaract.carNome", "QUARTOS")).add(Restrictions.between("c.imoCarQtd", 0, quartoAte))));
		}

		if ((listaCaracteriscas.size() != 0)&&(!(quartoDe == 0)) && ((quartoAte == 0))) {
			Caracteristicas carat=new CaracteriscasRN().buscarPorCaracteriscas("QUARTOS");
			if(carat==null){
				return new ArrayList<Imovel>();
			}
			listaCaracteriscas.add(carat);
			//Conjunction and = Restrictions.conjunction();
			for (Caracteristicas deta  : listaCaracteriscas) {
				e.add(Subqueries.exists(
						DetachedCriteria.forClass(ImovelCaracteristicas.class, "m")
						.setProjection(Projections.id())
						.add(Restrictions.eqProperty("i.imoId", "m.imovel.imoId"))
						.add(Restrictions.eq("m.caracteristicas",deta))));
			}
			//	crit.add(and);
			crit.createAlias("c.caracteristicas", "cCaract");
			e.add(Subqueries.exists(DetachedCriteria.forClass(Caracteristicas.class,"d")
					.setProjection(Projections.id())
					.add(Restrictions.eq("cCaract.carNome", "QUARTOS")).add(Restrictions.between("c.imoCarQtd", quartoDe, 99999999))));
		}
		
		if ((listaCaracteriscas.size() == 0)&&(!(quartoDe == 0)) && (!(quartoAte == 0))) {
			crit.createAlias("c.caracteristicas", "cCaract");
			e.add(Subqueries.exists(DetachedCriteria.forClass(Caracteristicas.class,"d")
					.setProjection(Projections.id())
					.add(Restrictions.eq("cCaract.carNome", "QUARTOS")).add(Restrictions.between("c.imoCarQtd", quartoDe, quartoAte))));
		}
		if ((listaCaracteriscas.size() == 0)&&(quartoDe == 0) && (!(quartoAte == 0))) {
			crit.createAlias("c.caracteristicas", "cCaract");
			e.add(Subqueries.exists(DetachedCriteria.forClass(Caracteristicas.class,"d")
					.setProjection(Projections.id())
					.add(Restrictions.eq("cCaract.carNome", "QUARTOS")).add(Restrictions.between("c.imoCarQtd", 0, quartoAte))));
		}
		if ((listaCaracteriscas.size() == 0)&&(!(quartoDe == 0)) && (quartoAte == 0)) {
			crit.createAlias("c.caracteristicas", "cCaract");
			e.add(Subqueries.exists(DetachedCriteria.forClass(Caracteristicas.class,"d")
					.setProjection(Projections.id())
					.add(Restrictions.eq("cCaract.carNome", "QUARTOS")).add(Restrictions.between("c.imoCarQtd", quartoDe, 9999999))));
		}

		/*		if (!((qtdQuartos.equals(null)) || (qtdQuartos.equals(0)))) {
			if (qtdQuartos.equals(5)) {
				// A leitura Ã© a seguinte: retorne o imovel com o quantida igual
				// qtdQuarto e (AND) que tenha o nome â€œquartosâ€�.
				crit.createAlias("c.detalhesimovel", "cDetalhe");
				// crit.add(Restrictions.eq("c.id.detId", 2));//2 Ã© o id do item
				// quarto. AtenÃ§Ã£o para quando o id do quarto mudar
				e.add(Subqueries.exists(DetachedCriteria.forClass(Caracteristica.class,"d")
						.setProjection(Projections.id())
				.add(Restrictions.eq("cDetalhe.carNome", "QUARTOS")).add(Restrictions.ge("c.id.comQtd", qtdQuartos))));//ge() = maior ou igual que
			} else {
				// A leitura Ã© a seguinte: retorne o imovel com o quantida igual
				// qtdQuarto e (AND) que tenha o nome â€œquartosâ€�.
				crit.createAlias("c.detalhesimovel", "cDetalhe");
				// crit.add(Restrictions.eq("c.id.detId", 2));//2 Ã© o id do item
				// quarto. AtenÃ§Ã£o para quando o id do quarto mudar
				e.add(Subqueries.exists(DetachedCriteria.forClass(Caracteristica.class,"d")
						.setProjection(Projections.id())
				.add(Restrictions.eq("cDetalhe.carNome", "QUARTOS")).add(
						Restrictions.eq("c.id.comQtd", qtdQuartos))));
			}
		}*/



		int maximo = 0;
		int minimo = 0;
		if (!valorMaximo.equals("")) {
			maximo = Integer.parseInt(valorMaximo);
		}
		if (!valorMinimo.equals("")) {
			minimo = Integer.parseInt(valorMinimo);
		}
		if ((!(minimo == 0)) && (!(maximo == 0))) {
			e.add(Restrictions.between("imoValorMercado", minimo, maximo));
		}
		if ((minimo == 0) && (!(maximo == 0))) {
			e.add(Restrictions.between("imoValorMercado", 0, maximo));
		}
		if ((!(minimo == 0)) && (maximo == 0)) {
			e.add(Restrictions.between("imoValorMercado", minimo, 9999999));
		}
		crit.add(e);
		int i = (listaCaracteriscas.size() + listaBairro.size()
				+ listaCidade.size() + listaTipoImovel.size() + 
				qtdQuartos+listaRegioes.size()
				+quartoAte+quartoDe);
		if (i == 0) {
			return resultado;// quando nÃ£o hÃ¡ item selecionado Ã© retornado lista
			// vazia.
		}  


		// eliminando resultados repetidos
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		resultado = crit.list();
		// resultado =(List<Imovel>) crit.list();
		return resultado;
	}

	@Override
	public List<Imovel> locacao(List<Tipoimovel> listaTipoImovel,
			List<Bairro> listaBairro, List<Cidade> listaCidade,
			List<Caracteristicas> listaCaracteriscas, String valorMinimo,
			String valorMaximo, Integer qtdQuartos, List<Regiao> listaRegioes, int quartoDe, int quartoAte) throws HibernateException {

		return teste(listaTipoImovel, listaBairro, listaCidade, listaCaracteriscas, valorMinimo, valorMaximo, qtdQuartos, listaRegioes,  quartoDe,  quartoAte);

		/*	// A diferenÃ§a entre o like e o ilike Ã© case-insensitive
		List<Imovel> resultado = new ArrayList<Imovel>();

		Criteria crit = session.createCriteria(Imovel.class);
		crit.createAlias("endereco", "end");
		crit.createAlias("complementoDoImovels", "c");
		Conjunction e = Restrictions.conjunction();// representar uma expressÃ£o
		// AND



		 * crit.setComment(
		 * "Quando temos trÃªs OR ou mais podemos representar isso de uma forma melhor utilizado o objeto Disjunction"
		 * ); crit.setComment(
		 * "Para representar uma expressÃ£o AND com mais que dois Criteria podemos usar o mÃ©todo conjunction()"
		 * ); crit.setComment(
		 * "Leia mais em: www.devmedia.com.br/hibernate-api-criteria-realizando-consultas/29627#ixzz2surVBhkm"
		 * ); Disjunction disjunction = Restrictions.disjunction();
		 * disjunction.add(tipos); disjunction.add(detalhes);
		 * disjunction.add(cidades); disjunction.add(bairros);
		 * crit.add(disjunction);

		// selecionando imovel com esses bairros
		int maximo = 0;
		int minimo = 0;
		if (!valorMaximo.equals("")) {
			maximo = Integer.parseInt(valorMaximo);
		}
		if (!valorMinimo.equals("")) {
			minimo = Integer.parseInt(valorMinimo);
		}
		if ((!(minimo == 0)) && (!(maximo == 0))) {
			e.add(Restrictions.between("imoValorMercado", minimo, maximo));
		}
		if ((minimo == 0) && (!(maximo == 0))) {
			e.add(Restrictions.between("imoValorMercado", 0, maximo));
		}
		if ((!(minimo == 0)) && (maximo == 0)) {
			e.add(Restrictions.between("imoValorMercado", minimo, 9999999));
		}

		if (listaBairro.size() != 0) {
			e.add(Restrictions.in("end.bairro", listaBairro));
		}
		if (listaCidade.size() != 0) {
			e.add(Restrictions.in("end.cidade", listaCidade));
		}
		if (listaTipoImovel.size() != 0) {
			e.add(Restrictions.in("tipoimovel", listaTipoImovel));
		}
		if (listaRegioes.size() != 0) {
			crit.createAlias("end.bairro", "bai");
			e.add(Restrictions.in("bai.regiao", listaRegioes));
		}

		// esse if Ã© porque a pesquisa nÃ£o dar certo com os atributos qtdQuartos e lista de detalhes juntos, tem que add um quarto Ã  lista de detalhes para dar certo. Ass: MÃ¡rio #Gambiarra
		if (!((qtdQuartos.equals(null)) || (qtdQuartos.equals(0)))) {
		Caracteristica det=new CaracteriscasRN().buscarPorDetalhesImovel("QUARTOS");
		listaCaracteriscas.add(det);
		}
		if (listaCaracteriscas.size() != 0) {
			e.add(Restrictions.in("c.detalhesimovel", listaCaracteriscas));
		}
		if (!((qtdQuartos.equals(null)) || (qtdQuartos.equals(0)))) {
			if (qtdQuartos.equals(5)) {
				// A leitura Ã© a seguinte: retorne o imovel com o quantida igual
				// qtdQuarto e (AND) que tenha o nome â€œquartosâ€�.
				crit.createAlias("c.detalhesimovel", "cDetalhe");
				// crit.add(Restrictions.eq("c.id.detId", 2));//2 Ã© o id do item
				// quarto. AtenÃ§Ã£o para quando o id do quarto mudar
				e.add(Restrictions.eq("cDetalhe.carNome", "QUARTOS")).add(Restrictions.ge("c.id.comQtd", qtdQuartos));//ge() = maior ou igual que
			} else {
				// A leitura Ã© a seguinte: retorne o imovel com o quantida igual
				// qtdQuarto e (AND) que tenha o nome â€œquartosâ€�.
				crit.createAlias("c.detalhesimovel", "cDetalhe");
				// crit.add(Restrictions.eq("c.id.detId", 2));//2 Ã© o id do item
				// quarto. AtenÃ§Ã£o para quando o id do quarto mudar
				e.add(Restrictions.eq("cDetalhe.carNome", "QUARTOS")).add(
						Restrictions.eq("c.id.comQtd", qtdQuartos));
			}
		}
		// crit.setComment("A diferenÃ§a entre o like e o ilike Ã© case-insensitive. nÃ£o Ã© recomendado adcinar uma lista para consulta se ela estiver fazia.");

		crit.add(e);
		int i = (listaCaracteriscas.size() + listaBairro.size()
				+ listaCidade.size() + listaTipoImovel.size() + qtdQuartos+listaRegioes.size());
		if (i == 0) {
			return resultado;// quando nÃ£o hÃ¡ item selecionado Ã© retornado lista
								// vazia.
		} else {
			// eliminando resultados repetidos
			crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			resultado = crit.list();
			// resultado =(List<Imovel>) crit.list();
			return resultado;
		}*/
	}

	@Override
	public boolean encontrarEndereco(Endereco endereco) {
		Criteria crit = session.createCriteria(Endereco.class);
		Conjunction e = Restrictions.conjunction();
		e.add(Restrictions.ilike("endNome",endereco.getEndNome()));
		e.add(Restrictions.eq("endCep",endereco.getEndCep()));
		e.add(Restrictions.eq("endNumero",endereco.getEndNumero()));
		e.add(Restrictions.eq("cidade",endereco.getCidade()));
		e.add(Restrictions.eq("bairro",endereco.getBairro()));		
		crit.add(e);
		int cont=crit.list().size();
		if(cont!=0){
			return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean existeCPF(String cpf) {
		Criteria crit = session.createCriteria(Pessoa.class);
		crit.add(Restrictions.eq("pesCpfCnpj", cpf));
		List<Pessoa> lista=crit.list();
		int cont=lista.size();
		if(cont!=0){
			return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Imovel> pesquisarImoveis(Imovel imovel,
			List<CaracteriticasTempPesquisa> listaCaracteristiscas,
			List<Tipoimovel> listaTipos,
			List<Situacaoimovel> listaSituacoes,
			List<Bairro> listaBairros) {
		List<Imovel> resultado = new ArrayList<Imovel>();
		Criteria crit = session.createCriteria(Imovel.class,"i");
		Disjunction ou=Restrictions.disjunction();// representar uma expressÃ£o OR
		Conjunction e = Restrictions.conjunction();// representar uma expressÃ£o AND
		
		int cont=0;//contador de itens de pesquisa, o contador Ã© para nÃ£o lista todos os imoveis
		String ruas =imovel.getEndereco().getEndNome();
		
		//estou usando as variaveis imoChaveQtd e imoChavePosicao para facilitar a passagem de valores , usando assim um objeto imovel
				if(imovel.getImoChaveQtd()==null){
					imovel.setImoChaveQtd(0);
				}
				if(imovel.getImoChavePosicao()==null){
					imovel.setImoChavePosicao(0);
				}
				if ((!(imovel.getImoChaveQtd() == 0)) && (!(imovel.getImoChavePosicao() == 0))) {
					e.add(Restrictions.between("imoValorAluguel", BigDecimal.valueOf(imovel.getImoChaveQtd()), BigDecimal.valueOf(imovel.getImoChavePosicao())));
					cont++;
				}
				if ((imovel.getImoChaveQtd() == 0) && (!(imovel.getImoChavePosicao() == 0))) {
					e.add(Restrictions.between("imoValorAluguel", BigDecimal.valueOf(0), BigDecimal.valueOf(imovel.getImoChavePosicao())));
					cont++;
				}
				if ((!(imovel.getImoChaveQtd() == 0)) && (imovel.getImoChavePosicao() == 0)) {
					e.add(Restrictions.between("imoValorAluguel", BigDecimal.valueOf(imovel.getImoChaveQtd()), BigDecimal.valueOf(9999999)));
					cont++;
				}
		
		//estou usando as variaveis imoAreaTerreno e imoAreaImovel para facilitar a passagem de valores , usando assim um objeto imovel
		if(imovel.getImoAreaImovel()==null){
			imovel.setImoAreaImovel(0);
		}
		if(imovel.getImoFoto()==null){
			imovel.setImoFoto(false);
		}
		if(imovel.getImoAreaTerreno()==null){
			imovel.setImoAreaTerreno(0);
		}
		if ((!(imovel.getImoAreaImovel() == 0)) && (!(imovel.getImoAreaTerreno() == 0))) {
			e.add(Restrictions.between("imoAreaImovel", imovel.getImoAreaImovel(), imovel.getImoAreaTerreno()));
			cont++;
		}
		if ((imovel.getImoAreaImovel() == 0) && (!(imovel.getImoAreaTerreno() == 0))) {
			e.add(Restrictions.between("imoAreaImovel", 0, imovel.getImoAreaTerreno()));
			cont++;
		}
		if ((!(imovel.getImoAreaImovel() == 0)) && (imovel.getImoAreaTerreno() == 0)) {
			e.add(Restrictions.between("imoAreaImovel", imovel.getImoAreaImovel(), 9999999));
			cont++;
		}
		
		//estou usando as variaveis imoValorMercado e imoPosicao para facilitar a passagem de valores , usando assim um objeto imovel
				if(imovel.getImoValorMercado()==null){
					imovel.setImoValorMercado(0.0);
				}
				if(imovel.getImoPosicao()==null){
					imovel.setImoPosicao(0);
				}
				if ((!(imovel.getImoValorMercado() == 0)) && (!(imovel.getImoPosicao() == 0))) {
					e.add(Restrictions.between("imoValorMercado", imovel.getImoValorMercado(), imovel.getImoPosicao()));
					cont++;
				}
				if ((imovel.getImoValorMercado() == 0) && (!(imovel.getImoPosicao() == 0))) {
					e.add(Restrictions.between("imoValorMercado", 0, imovel.getImoPosicao()));
					cont++;
				}
				if ((!(imovel.getImoValorMercado() == 0)) && (imovel.getImoPosicao() == 0)) {
					e.add(Restrictions.between("imoValorMercado", imovel.getImoValorMercado(), 9999999));
					cont++;
				}
		
		if(imovel.getImoLocacao().equals(true)){
			e.add(Restrictions.eq("i.imoLocacao", true));
			cont++;
		}
		if(imovel.getImoVenda().equals(true)){
			e.add(Restrictions.eq("i.imoVenda", true));
			cont++;
		}
		if(imovel.getImoId()!=0){
			e.add(Restrictions.eq("i.imoId", imovel.getImoId()));
			cont++;
		}
		if(imovel.getImoFoto()==true){
			e.add(Restrictions.eq("i.imoFoto", true));
			cont++;
		}
		
		//Pesquiar Caracteristicas do imovel
		if ((listaCaracteristiscas.size() != 0)) {			
			for (CaracteriticasTempPesquisa caract  : listaCaracteristiscas) {
				if(!(((caract.getUnitario()==false)&&(caract.getQuantidade()==0))))
					if((caract.getSelecionado()==false)&&(caract.getUnitario()==false)){
						e.add(Subqueries.exists(
								DetachedCriteria.forClass(ImovelCaracteristicas.class, "m")
								.setProjection(Projections.id())
								.add(Restrictions.eqProperty("i.imoId", "m.imovel.imoId"))
								.add(Restrictions.eq("m.caracteristicas.carId",caract.getId()))
								.add(Restrictions.eq("m.imoCarQtd",caract.getQuantidade()))
								));
					cont++;	
					}else if(caract.getSelecionado()==true){
						cont++;
									e.add(Subqueries.exists(
											DetachedCriteria.forClass(ImovelCaracteristicas.class, "m")
											.setProjection(Projections.id())
											.add(Restrictions.eqProperty("i.imoId", "m.imovel.imoId"))
											.add(Restrictions.eq("m.caracteristicas.carId",caract.getId()))
											));
								}
			}
		}
		
		//estou usando as variaveis imoDiasDeCadastro e imoIdFuncionarioIndicacao para facilitar a passagem de valores , usando assim um objeto imovel
		if(imovel.getImoDiasDeCadastro()==null){
			imovel.setImoDiasDeCadastro(0);
		}
		if(imovel.getImoIdFuncionarioIndicacao()==null){
			imovel.setImoIdFuncionarioIndicacao(0);
		}
		
		if ((!(imovel.getImoDiasDeCadastro() == 0)) && (!(imovel.getImoIdFuncionarioIndicacao() == 0))) {
			crit.createCriteria("imovelCaracteristicases", "c");
			crit.createAlias("c.caracteristicas", "cCaract");
			e.add(Subqueries.exists(DetachedCriteria.forClass(Caracteristicas.class,"d")
					.setProjection(Projections.id())
					.add(Restrictions.eq("cCaract.carNome", "QUARTOS"))
					.add(Restrictions.between("c.imoCarQtd", imovel.getImoDiasDeCadastro(), imovel.getImoIdFuncionarioIndicacao()))));	
			cont++;
		}
		if ((imovel.getImoDiasDeCadastro() == 0) && (!(imovel.getImoIdFuncionarioIndicacao() == 0))) {
			crit.createCriteria("imovelCaracteristicases", "c");			
			crit.createAlias("c.caracteristicas", "cCaract");
			e.add(Subqueries.exists(DetachedCriteria.forClass(Caracteristicas.class,"d")
					.setProjection(Projections.id())
					.add(Restrictions.eq("cCaract.carNome", "QUARTOS"))
					.add(Restrictions.between("c.imoCarQtd", 0, imovel.getImoIdFuncionarioIndicacao()))));		
			cont++;
		}
		if ((!(imovel.getImoDiasDeCadastro() == 0)) && (imovel.getImoIdFuncionarioIndicacao() == 0)) {
			crit.createCriteria("imovelCaracteristicases", "c");			
			crit.createAlias("c.caracteristicas", "cCaract");
			e.add(Subqueries.exists(DetachedCriteria.forClass(Caracteristicas.class,"d")
					.setProjection(Projections.id())
					.add(Restrictions.eq("cCaract.carNome", "QUARTOS"))
					.add(Restrictions.between("c.imoCarQtd", imovel.getImoDiasDeCadastro(), 999999))));		
			cont++;
		}
		
		if (listaBairros.size() != 0) {
			crit.createCriteria("endereco", "end");
			
			//utilizando pesquisa OR para bairro
			if(listaBairros.size()>1){
				for (Bairro bai  : listaBairros) {
					ou.add(Subqueries.exists(
							DetachedCriteria.forClass(Endereco.class, "b")
							.setProjection(Projections.id())
							.add(Restrictions.eqProperty("end.bairro.baiId", "b.bairro.baiId"))
							.add(Restrictions.eq("b.bairro",bai))
							));
					cont++;
				}
			}
			/*if((listaBairros.size()==1)&&((imovel.getEndereco().getEndNumero()==null)||
					(imovel.getEndereco().getEndNome()==null)
					||(imovel.getEndereco().getEndComplemento()==null)
					||(imovel.getEndereco().getEndZona()==null)
					||(imovel.getEndereco().getCidade().getCidNome()==null)
					||(imovel.getEndereco().getCidade().getCidUf()==null)
					)){
				for (Bairro bai  : listaBairros) {
					ou.add(Subqueries.exists(
							DetachedCriteria.forClass(Endereco.class, "b")
							.setProjection(Projections.id())
							.add(Restrictions.eqProperty("end.bairro.baiId", "b.bairro.baiId"))
							.add(Restrictions.eq("b.bairro",bai))
							));
					cont++;
				
				}
			}*/
		else if((listaBairros.size()==1)){
				//pesquisa imovel com diferentes casos de endereços caso haja um bairro selecionado
				crit.createCriteria("end.cidade", "cid");
				e.add(Restrictions.in("end.bairro", listaBairros));
				if(imovel.getEndereco().getEndNumero()!=null){
					e.add(Restrictions.eq("end.endNumero",imovel.getEndereco().getEndNumero()));
					cont++;
				}
				if(imovel.getEndereco().getEndCep()!=""){
					e.add(Restrictions.eq("end.endCep",imovel.getEndereco().getEndCep()));
					cont++;
				}
				String chaar=new String();
				chaar=Character.toString(imovel.getEndereco().getEndZona());
				if(!chaar.equalsIgnoreCase("S")){
					e.add(Restrictions.eq("end.endZona",imovel.getEndereco().getEndZona()));
					cont++;
				}
				if(imovel.getEndereco().getEndComplemento()!=""){
					e.add(Restrictions.ilike("end.endComplemento",imovel.getEndereco().getEndComplemento(),MatchMode.START));
					cont++;
				}
				if(imovel.getEndereco().getEndNome()!=""){
					e.add(Restrictions.ilike("end.endNome",imovel.getEndereco().getEndNome(),MatchMode.ANYWHERE));
					cont++;
				}
				if(imovel.getEndereco().getCidade().getCidNome()!=""){
					e.add(Restrictions.ilike("cid.cidNome",imovel.getEndereco().getCidade().getCidNome(),MatchMode.ANYWHERE));
					cont++;
				}
				if(imovel.getEndereco().getCidade().getCidUf()!=""){
					e.add(Restrictions.ilike("cid.cidUf",imovel.getEndereco().getCidade().getCidUf(),MatchMode.ANYWHERE));
					cont++;
				}
				cont++;
			}
		}
		if((listaBairros.size()==0)){
			//pesquisa imovel com diferentes casos de endereços se não há bairros selecionados 
			crit.createCriteria("endereco", "end");
			crit.createCriteria("end.cidade", "cid");
			if(imovel.getEndereco().getEndNumero()!=null){
				e.add(Restrictions.eq("end.endNumero",imovel.getEndereco().getEndNumero()));
				cont++;
			}
			if(imovel.getEndereco().getEndCep()!=""){
				e.add(Restrictions.eq("end.endCep",imovel.getEndereco().getEndCep()));
				cont++;
			}
			String chaar=new String();
			chaar=Character.toString(imovel.getEndereco().getEndZona());
			if(!chaar.equalsIgnoreCase("S")){
				e.add(Restrictions.eq("end.endZona",imovel.getEndereco().getEndZona()));
				cont++;
			}
			if(imovel.getEndereco().getEndComplemento()!=""){
				e.add(Restrictions.ilike("end.endComplemento",imovel.getEndereco().getEndComplemento(),MatchMode.START));
				cont++;
			}
			if(imovel.getEndereco().getEndNome()!=""){
				 StringBuilder sb = new StringBuilder();
				// String rua=new String();
				for (int index = 0; index < imovel.getEndereco().getEndNome().length(); index++) {
			        char c = imovel.getEndereco().getEndNome().charAt(index);
			       String UNICODE ="ÀàÈèÌìÒòÙùÁáÉéÍíÓóÚúÝýÂâÊêÎîÔôÛûYyÃãÕõÑñÄäËëÏïÖöÜüŸÿÅåÇçOoUu";
			       String PLAIN_ASCII ="AaEeIiOoUuAaEeIiOoUuYyAaEeIiOoUuYyAaOoNnAaEeIiOoUuYyAaCcOoUu";			      
			        int pos = UNICODE.indexOf(c);
			        if (pos > -1)
			            sb.append(PLAIN_ASCII.charAt(pos));
			        else {
			           sb.append(c);
			        }
				}
				//rua=sb.toString();
				e.add(Restrictions.ilike("end.endNome",imovel.getEndereco().getEndNome(),MatchMode.ANYWHERE));
				cont++;
			}
			if(imovel.getEndereco().getCidade().getCidNome()!=""){
				e.add(Restrictions.ilike("cid.cidNome",imovel.getEndereco().getCidade().getCidNome(),MatchMode.ANYWHERE));
				cont++;
			}
			if(imovel.getEndereco().getCidade().getCidUf()!=""){
				e.add(Restrictions.ilike("cid.cidUf",imovel.getEndereco().getCidade().getCidUf(),MatchMode.ANYWHERE));
				cont++;
			}
			
		}
		if (listaSituacoes.size() != 0) {
			cont++;
			ou.add(Restrictions.in("situacaoimovel", listaSituacoes));
		}
		if (listaTipos.size() != 0) {
			cont++;
			ou.add(Restrictions.in("tipoimovel", listaTipos));
		}
		if(cont==0){
			return resultado;
		}
		
	
		
		crit.add(ou);
		crit.add(e);		
		/*// eliminando resultados repetidos
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);*/
		resultado = crit.list();
		
		//pesquisa ignorando acentuação em rua
		if(ruas!=""){
			String sql = "SELECT *  FROM [renovarsistemas].[dbo].[ENDERECO] e where e.END_NOME   = '"+ruas+"' COLLATE LATIN1_GENERAL_CI_AI";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Endereco.class);
			List<Endereco> lisEnd=query.list();
			for(Endereco en: lisEnd){
				Criteria c = session.createCriteria(Imovel.class);
				c.add(Restrictions.eq("endereco", en));	
				Imovel imo=(Imovel) c.uniqueResult();
				if(imo!=null){
					boolean ok=true;
					if(resultado.size()==0){
						resultado.add(imo);
					}
					for(Imovel i: resultado){
						if(imo.getImoId()==i.getImoId()){
							ok=false;
						}
					}
					if(ok){
						resultado.add(imo);
					}
				}
			}

		}
		return resultado;
	}
}
