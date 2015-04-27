package br.com.locadora.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import br.com.locadora.modelo.EmprestimoChave;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.rn.EmprestimoChaveRN;
import br.com.locadora.rn.PessoaRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "pendenciasUsuarioBean")
@ViewScoped
public class PendenciasUsuarioBean implements Serializable{

	/**
	 * 
	 */
	@Getter	private static final long serialVersionUID = 7978045969978349942L;
	@Getter @Setter private List<Pessoa> listaPessoaDevendoChave;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public PendenciasUsuarioBean() {
		carregar();
		carregarLista();
	}
	public void carregar(){

	}
	public void carregarLista(){
		
		// carregando pessoas que estam devendo a chave acima da 24 horas....
		this.listaPessoaDevendoChave=new ArrayList<Pessoa>();
		PessoaRN pessoaRN=new PessoaRN();
		List<EmprestimoChave> lista=new EmprestimoChaveRN().listar();
		for(EmprestimoChave c: lista){
			//verificando se a chave está com a devolução pendente
			if(c.getEmpDevolucao()!=true){
				Date dataContrato=c.getEmpDataLimiteDevolucao();
				if(dataContrato!=null){
					long dateInicio=dataContrato.getTime(), dataFinal=new Date().getTime();
					int horas=(int)((dataFinal-dateInicio)/(1000*60*60));
					if(horas>24){
						Pessoa pessoa=pessoaRN.carregar(c.getPessoa().getPesId());
						this.listaPessoaDevendoChave.add(pessoa);
					}
				}
			}
		}
		
	}
	
	public String imovelPendente(int id){
		String imovel=new String();
		List<EmprestimoChave> lista=new EmprestimoChaveRN().listar();
		for(EmprestimoChave c: lista){
			if((c.getPessoa().getPesId()==id)&&(c.getEmpDevolucao()==false)){
				imovel+=" Codigo: "+c.getImovel().getImoId()+" - Tipo: "+c.getImovel().getTipoimovel().getTipNome()+"<br/>";
			}
		}
		return imovel;
	}
}
