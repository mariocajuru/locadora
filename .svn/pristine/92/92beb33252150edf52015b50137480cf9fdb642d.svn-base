package br.com.locadora.web.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.locadora.web.util.MenuUtil;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "menuBean")
@SessionScoped
public class MenuBean extends MenuUtil implements  Serializable {
	
	@Getter private static final long serialVersionUID = -2954567199177420579L;
	//@Getter @Setter private static Funcionario funcionarioLogado = new Funcionario();
	@Getter @Setter private Set<String> permissoes=new HashSet<String>();
	
	@PostConstruct
	public void testandoNumeroDaSessao(){
		System.out.println("Numero da Sessao Inicio do MenuBean "+hashCode());
	}
	
	@PreDestroy
	public void testandoNumeroDaSessaoFim(){
		System.out.println("Numero da Sessao Fim do MenuBean "+hashCode());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void configurarMenu() {		
		permissoes=new HashSet<String>();
		List<GrantedAuthority> auth = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for(GrantedAuthority p: auth){
			String i=p.toString();
			permissoes.add(i);
		}
		
		/*FacesContext facesContext = FacesContext.getCurrentInstance();
		ContextoBean contextoBean = ContextoUtil.getContextoBean();

		String login=new String();
		if (contextoBean == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext external = context.getExternalContext();			 
			login = external.getRemoteUser();	
			funcionarioLogado = new FuncionarioRN().buscarPorLogin(login);
		}else{
			*//** Grava o funcionário logado em um várivel estática *//*
			funcionarioLogado = contextoBean.getFuncionarioLogado();
		}


		*//** Bloqueia a página de login caso um funcionário esteja logado*//*
		if (facesContext.getViewRoot().getViewId().contains("login") && funcionarioLogado != null) {

		}*/

		addMenu(new MenuItem("Principal", "restrito/principal.jsf","fa-home"));
		menuAdministracao();
		menuCadastros();
		menuLocacao();
		menuFinanceiro();
		menuTestes();
		addMenu(new MenuItem("Pesquisar Pessoa", "restrito/pessoa/consulta.jsf", "fa-search"));
		addMenu(new MenuItem("Pesquisar Imóvel", "restrito/consulta/pesquisaimovel.jsf", "fa-search"));
		addMenu(new MenuItem("Pesquisar Imóvel 2", "v2/pesquisa/index.jsp", "fa-search"));
		addMenu(new MenuItem("Pendências do Usuário", "restrito/pendencias/consulta.jsf", "fa-search"));
	}
	
	private void menuAdministracao() {
		Set<MenuItem> itens = new HashSet<MenuItem>();

		//Set<String> permissoes=funcionarioLogado.getPermissao();
		sairFor:
		for(String p: this.permissoes){
			if(p.equalsIgnoreCase("ROLE_ADMIN")){
				itens = new HashSet<MenuItem>();
				itens.add( new MenuItem("BAIRROS","admin/endereco/bairro/consulta.jsf"));
				itens.add( new MenuItem("BANCO",                    "admin/banco/consulta.jsf") );
				itens.add( new MenuItem("CARACTERÍSTICAS DE IMÓVEIS", "admin/imovel-caracteristicas/consulta.jsf") );
				itens.add( new MenuItem("FILIAIS",                    "admin/filial/consulta.jsf") );
				itens.add( new MenuItem("FUNCIONÁRIOS",               "admin/filial/funcionario/consulta.jsf") );
				itens.add( new MenuItem("QUADROS DE CHAVES",          "admin/cadquadrodechaves.jsf") );
				itens.add( new MenuItem("REGIÕES",                    "admin/endereco/regiao/consulta.jsf") );
				itens.add( new MenuItem("SITUAÇÃO IMÓVEL",            "admin/imovel-situacao/consulta.jsf") );
				itens.add( new MenuItem("TIPO IMÓVEL",            	  "admin/imovel-tipo/consulta.jsf") );
				itens.add( new MenuItem("FORMA DE PAGAMENTO DO PROPRIETÁRIO",            	  "admin/imovel-proprietario/consulta.jsf") );
				itens.add( new MenuItem("O QUE TEM PERTO DO IMÓVEL",  "admin/imovel-temperto/consulta.jsf") );
				itens.add( new MenuItem("RAMO DE ATIVIDADE",  "restrito/pessoa/ramo-de-atuacao/consulta.jsf") );
				itens.add( new MenuItem("DESTINAÇÃO DE LOCAÇÃO",  "admin/locacao/destinacao-locacao/consulta.jsf") );
				itens.add( new MenuItem("STATUS DE LOCAÇÃO",  "admin/locacao/status-locacao/consulta.jsf") );
				itens.add( new MenuItem("PERMISSÕES DE LOCAÇÃO",  "admin/locacao/permissoes-locacao/consulta.jsf") );
				itens.add( new MenuItem("SITUAÇÃO LOCAÇÃO",  "admin/locacao/situacao-locacao/consulta.jsf") );
				itens.add( new MenuItem("INDICE REAJUSTE LOCAÇÃO",  "admin/locacao/indices-reajuste/consulta.jsf") );
				itens.add( new MenuItem("IMPOSTO DE RENDA",  "admin/locacao/imposto-de-renda-locacao/consulta.jsf") );
				itens.add( new MenuItem("PLANO DE CONTAS",  "admin/locacao/contas-locacao/plano/consulta.jsf") );
				itens.add( new MenuItem("CONTAS FINANCEIRO",  "admin/locacao/contas-locacao/financeiro/consulta.jsf") );
				itens.add( new MenuItem("CENTRO DE CUSTO",  "admin/locacao/contas-locacao/custo/consulta.jsf") );
				itens.add( new MenuItem("GRUPO DE CONTAS",  "admin/locacao/contas-locacao/grupo/consulta.jsf") );
				itens.add( new MenuItem("PARAMETROS",  "admin/parametro/consulta.jsf") );
				itens.add( new MenuItem("TABELA SEGURO INCÊNDIO IMÓVEL RESIDENCIAL",  "admin/locacao/seguro-incendio/residencial/consulta.jsf") );
				itens.add( new MenuItem("TABELA SEGURO INCÊNDIO IMÓVEL COMERCIAL",  "admin/locacao/seguro-incendio/comercial/consulta.jsf") );
				break sairFor;
			}

			if(p.equalsIgnoreCase("ROLE_BAIRRO_CONSULTAR")){
				itens.add( new MenuItem("BAIRROS","admin/endereco/bairro/consulta.jsf"));
			}

			if(p.equalsIgnoreCase("ROLE_BANCO_CONSULTAR")){
				itens.add( new MenuItem("BANCO",                    "admin/banco/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_CARACTERISTICAS_IMOVEL_CONSULTAR")){
				itens.add( new MenuItem("CARACTERÍSTICAS DE IMÓVEIS", "admin/imovel-caracteristicas/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_FILIAIS_CONSULTAR")){
				itens.add( new MenuItem("FILIAIS",                    "admin/filial/consulta.jsf") );
			}
			
			if(p.equalsIgnoreCase("ROLE_FUNCIONARIOS_CONSULTAR")){
				itens.add( new MenuItem("FUNCIONÁRIOS",               "admin/filial/funcionario/consulta.jsf") );
			}

			/*if(p.equalsIgnoreCase("ROLE_FUNCIONARIOS_CONSULTAR")|| p.equalsIgnoreCase("ROLE_ADMIN")){
				itens.add( new MenuItem("FUNCIONÁRIOS",               "admin/filial/funcionario/cadfuncionario.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_PERMISSAO_LOCACAO_CONSULTAR")|| p.equalsIgnoreCase("ROLE_ADMIN")){
				itens.add( new MenuItem("PERMISSÕES",               "admin/filial/funcionario/permissoes.jsf") );
			}*/

			if(p.equalsIgnoreCase("ROLE_QUADRO_CHAVES_CONSULTAR")){
				itens.add( new MenuItem("QUADROS DE CHAVES",          "admin/cadquadrodechaves.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_REGIAO_CONSULTAR")){
				itens.add( new MenuItem("REGIÕES",                    "admin/endereco/regiao/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_SITUACAO_IMOVEL_CONSULTAR")){
				itens.add( new MenuItem("SITUAÇÃO IMÓVEL",            "admin/imovel-situacao/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_TIPO_IMOVEL_CONSULTAR")){
				itens.add( new MenuItem("TIPO IMÓVEL",            	  "admin/imovel-tipo/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_FORMA_PAGAMENTO_PROPRIETARIO_CONSULTAR")){
				itens.add( new MenuItem("FORMA DE PAGAMENTO DO PROPRIETÁRIO",            	  "admin/imovel-proprietario/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_PERTO_IMOVEL_CONSULTAR")){
				itens.add( new MenuItem("O QUE TEM PERTO DO IMÓVEL",  "admin/imovel-temperto/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_RAMO_ATIVIDADE_CONSULTAR")){
				itens.add( new MenuItem("RAMO DE ATIVIDADE",  "restrito/pessoa/ramo-de-atuacao/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_DESTINACAO_LOCACAO_CONSULTAR")){
				itens.add( new MenuItem("DESTINAÇÃO DE LOCAÇÃO",  "admin/locacao/destinacao-locacao/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_STATUS_LOCACAO_CONSULTAR")){
				itens.add( new MenuItem("STATUS DE LOCAÇÃO",  "admin/locacao/status-locacao/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_PERMISSAO_LOCACAO_CONSULTAR")){
				itens.add( new MenuItem("PERMISSÕES DE LOCAÇÃO",  "admin/locacao/permissoes-locacao/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_SITUACAO_LOCACAO_CONSULTAR")){
				itens.add( new MenuItem("SITUAÇÃO LOCAÇÃO",  "admin/locacao/situacao-locacao/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_INDICE_REAJUSTRE_LOCACAO_CONSULTAR")){
				itens.add( new MenuItem("INDICE REAJUSTE LOCAÇÃO",  "admin/locacao/indices-reajuste/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_IMPOSTO_RENDA_CONSULTAR")){
				itens.add( new MenuItem("IMPOSTO DE RENDA",  "admin/locacao/imposto-de-renda-locacao/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_PLANO_CONTAS_CONSULTAR")){
				itens.add( new MenuItem("PLANO DE CONTAS",  "admin/locacao/contas-locacao/plano/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_CONTAS_FINANCEIRO_CONSULTAR")){
				itens.add( new MenuItem("CONTAS FINANCEIRO",  "admin/locacao/contas-locacao/financeiro/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_CENTRO_CUSTO_CONSULTAR")){
				itens.add( new MenuItem("CENTRO DE CUSTO",  "admin/locacao/contas-locacao/custo/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_GRUPO_CONTAS_CONSULTAR")){	
				itens.add( new MenuItem("GRUPO DE CONTAS",  "admin/locacao/contas-locacao/grupo/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_PARAMETROS_CONSULTAR")){
				itens.add( new MenuItem("PARAMETROS",  "admin/parametro/consulta.jsf") );
			}

			if(p.equalsIgnoreCase("ROLE_SEGURO_INCENDIO_RESIDENCIAL_CONSULTAR")){
				itens.add( new MenuItem("TABELA SEGURO INCÊNDIO IMÓVEL RESIDENCIAL",  "admin/locacao/seguro-incendio/residencial/consulta.jsf") );

			}

			if(p.equalsIgnoreCase("ROLE_SEGURO_INCENDIO_COMERCIAL_CONSULTAR")){
				itens.add( new MenuItem("TABELA SEGURO INCÊNDIO IMÓVEL COMERCIAL",  "admin/locacao/seguro-incendio/comercial/consulta.jsf") );
			}	
		}
		
		ArrayList<MenuItem> array=new ArrayList<MenuItem>();		
		for(MenuItem menu:itens)
			array.add(menu);
		addMenu( new MenuItem("Administração","fa-cogs", array) );
	}
	private void menuCadastros() {
		Set<MenuItem> itens = new HashSet<MenuItem>();
		sairFor:
		for(String p: this.permissoes){
			if(p.equalsIgnoreCase("ROLE_ADMIN")){
				itens = new HashSet<MenuItem>();
				itens.add( new MenuItem("DEVOLUÇÃO DE CHAVE",  "restrito/imovel/devolucaochave.jsf") );
				itens.add( new MenuItem("IMÓVEIS",             "restrito/imovel/consulta.jsf") );
				itens.add( new MenuItem("PESSOAS",             "restrito/pessoa/consulta.jsf") );
				itens.add( new MenuItem("VISTORIA",            "restrito/vistoria.jsf") );
				break sairFor;
			}
			if(p.equalsIgnoreCase("ROLE_DEVOLUCAO_CHAVE_CONSULTAR")){
				itens.add( new MenuItem("DEVOLUÇÃO DE CHAVE",  "restrito/imovel/devolucaochave.jsf") );
			}
			if(p.equalsIgnoreCase("ROLE_IMOVEL_CONSULTAR")){
				itens.add( new MenuItem("IMÓVEIS",             "restrito/imovel/consulta.jsf") );
			}
			if(p.equalsIgnoreCase("ROLE_PESSOA_CONSULTAR")){
				itens.add( new MenuItem("PESSOAS",             "restrito/pessoa/consulta.jsf") );
			}
			if(p.equalsIgnoreCase("ROLE_VISTORIA_CONSULTAR")){
				itens.add( new MenuItem("VISTORIA",            "restrito/vistoria.jsf") );
			}
		}

		ArrayList<MenuItem> array=new ArrayList<MenuItem>();		
		for(MenuItem menu:itens)
			array.add(menu);
		addMenu( new MenuItem("Cadastros","fa-users", array) );
	}
	
	private void menuLocacao() {
		Set<MenuItem> itens = new HashSet<MenuItem>();
		sairFor:
		for(String p: this.permissoes){
			if(p.equalsIgnoreCase("ROLE_ADMIN")){
				itens = new HashSet<MenuItem>();
				itens.add( new MenuItem("CONTRATOS",               "restrito/locacao/consulta-contrato.jsf") );	
				break sairFor;
			}
			if(p.equalsIgnoreCase("ROLE_CONTRATO_CONSULTAR")){
				itens.add( new MenuItem("CONTRATOS",               "restrito/locacao/consulta-contrato.jsf") );	
			}
		}
		ArrayList<MenuItem> array=new ArrayList<MenuItem>();		
		for(MenuItem menu:itens)
			array.add(menu);
		addMenu( new MenuItem("Locação","fa-university", array) );
	}

	private void menuFinanceiro() {
		Set<MenuItem> itens = new HashSet<MenuItem>();
		sairFor:
		for(String p: this.permissoes){
			if(p.equalsIgnoreCase("ROLE_ADMIN")){
				itens = new HashSet<MenuItem>();
				itens.add( new MenuItem("CONTA CORRENTE",                        "restrito/financeiro/consulta/consulta.jsf") );
				itens.add( new MenuItem("CONTAS A PAGAR",                        "restrito/financeiro/contas-a-pagar/gerar.jsf") );		
				itens.add( new MenuItem("GERAR",                        "restrito/financeiro/manualmente/gerar.jsf") );
				itens.add( new MenuItem("LANÇAR DESCONTO",                        "restrito/financeiro/desconto/consulta.jsf") );
				itens.add( new MenuItem("REAJUSTE",                        "restrito/financeiro/reajuste/cadastro.jsf") );
				break sairFor;
			}
			if(p.equalsIgnoreCase("ROLE_CONTA_CORRENTE_CONSULTAR")){		
				itens.add( new MenuItem("CONTA CORRENTE",                        "restrito/financeiro/consulta/consulta.jsf") );
			}
			if(p.equalsIgnoreCase("ROLE_CONTA_PAGAR_CONSULTAR")){
				itens.add( new MenuItem("CONTAS A PAGAR",                        "restrito/financeiro/contas-a-pagar/gerar.jsf") );
			}
			if(p.equalsIgnoreCase("ROLE_GERAR_CONSULTAR")){
				itens.add( new MenuItem("GERAR",                        "restrito/financeiro/manualmente/gerar.jsf") );
			}
			if(p.equalsIgnoreCase("ROLE_LANCAR_DESCONTO_CONSULTAR")){
				itens.add( new MenuItem("LANÇAR DESCONTO",                        "restrito/financeiro/desconto/consulta.jsf") );
			}
			if(p.equalsIgnoreCase("ROLE_REAJUSTE_CONSULTAR")){
				itens.add( new MenuItem("REAJUSTE",                        "restrito/financeiro/reajuste/cadastro.jsf") );
			}

		}
		ArrayList<MenuItem> array=new ArrayList<MenuItem>();		
		for(MenuItem menu:itens)
			array.add(menu);
		addMenu( new MenuItem("Financeiro","fa-money", array) );
	}
	
	private void menuTestes() {
		ArrayList<MenuItem> itens = new ArrayList<MenuItem>();				
		itens.add( new MenuItem("ENVIO DE EMAIL",               "restrito/gmail.jsf") );
		itens.add( new MenuItem("ENVIO DE MENSAGEM",            "restrito/enviosms.jsf") );
		//itens.add( new MenuItem("CAPTURA LATITUDE & LONGITUDE", "restrito/capturaendereco.jsf") );
		itens.add( new MenuItem("RELÁTORIO",                    "restrito/relatorios/relatorio.jsf") );
		itens.add( new MenuItem("MAPAS",                        "restrito/pesconsulta.jsf") );	
		itens.add( new MenuItem("AUDITORIA",                         "admin/log/auditoria.jsf") );	
		addMenu( new MenuItem("Testes","fa-flask", itens) );
	}
	
}