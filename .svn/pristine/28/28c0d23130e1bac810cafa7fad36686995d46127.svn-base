package br.com.locadora.web;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.locadora.web.util.ContextoUtil;

import com.human.gateway.client.bean.Response;
import com.human.gateway.client.bean.SimpleMessage;
import com.human.gateway.client.exception.ClientHumanException;
import com.human.gateway.client.service.SimpleMessageService;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "smsBean")
@ViewScoped
public class Sms implements Serializable{
	/**
	 * 
	 */
	@Getter private static final long serialVersionUID = 8669766382854948324L;
	@Getter @Setter private String numero=new String();
	@Getter @Setter private String menssagem=new String();
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	
	public Sms() {
		carregar();
	}

	public void carregar(){
		this.menssagem=new String();
		this.numero=new String();
	}
	
	public void enviar() throws ClientHumanException{
		// Configura usuario e senha
    	String account = "renovar.api";
		String password = "hi2pIt45fD";
			
		Random r = new Random();
		int randomInt = r.nextInt(999) + 1;
    	String id = Integer.toString(randomInt);
		
    	// Cria uma instancia do cliente de conexao
		SimpleMessageService clienteHuman = new SimpleMessageService(account, password);

        // Cria uma mensagem individual
    	SimpleMessage mensagem = new SimpleMessage();    	
    	
    	//Id da mensagem
    	mensagem.setId(id);
    	
        // Numero do celular de destino
    	this.numero="55"+this.numero.replaceAll("[().-]", "");
        mensagem.setTo(this.numero);
        
        // Conteudo do SMS
        mensagem.setMessage(this.menssagem);
        
        // Obtem o retorno da integracao (codigo/descricao)
        List<Response> retornos = clienteHuman.send(mensagem);
        for(Response retorno : retornos) {
            System.out.print(retorno.getReturnCode());
            System.out.print(":");
            System.out.println(retorno.getReturnDescription());
        }
        
        
        // para fazer alguma consulta de status de sms, favor utilizar o 
        // metodo query passando o id do sms
    	retornos = clienteHuman.query(id);
        
        for(Response retorno : retornos) {
            System.out.print(retorno.getReturnCode());
            System.out.print(":");
            System.out.println(retorno.getReturnDescription());
        }
        carregar();
        this.contextoBean.mostrarAviso("Menssagem Enviada");
	}
}
