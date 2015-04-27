package br.com.locadora.modelo.log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.hibernate.envers.RevisionListener;

import br.com.locadora.web.util.GenericBean;

public class Gravar extends GenericBean implements RevisionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8644920676174993870L;

	@SuppressWarnings("static-access")
	@Override
	public void newRevision(Object arg0) {
		Auditoria auditoria=(Auditoria) arg0;
		
		//auditoria.setPagina(super.getPaginaAtual());

		//Funcionario pessoa= new FuncionarioRN().carregar(super.getFuncionarioLogado().getFunId());

		auditoria.setNome(super.getFuncionarioLogado().getPessoa().getPesNome());
		auditoria.setCpf(super.getFuncionarioLogado().getPessoa().getPesCpfCnpj());
		auditoria.setLoguin(super.getFuncionarioLogado().getFunLoguin());
		//auditoria.setNivelAcesso(super.getFuncionarioLogado().getPermissao().toString());
		auditoria.setIp_funcionario(this.getFuncionarioLogado().getPesId());
		
		 @SuppressWarnings("rawtypes")
		Enumeration ee = null;
		try {
			ee = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		boolean achou=false;
	      while (ee.hasMoreElements()) { 
	         NetworkInterface i = (NetworkInterface) ee.nextElement(); 
	         @SuppressWarnings("rawtypes")
			Enumeration ds = i.getInetAddresses(); 
	         while (ds.hasMoreElements()) { 
	            InetAddress myself = (InetAddress) ds.nextElement();
	            if (!myself.isLoopbackAddress() && myself.isSiteLocalAddress()) {
	            	auditoria.setIp_maquina(myself.getHostAddress());
	            	auditoria.setNome_maquina(myself.getHostName());
	            	achou=true;
	            	//http://mariojp.wordpress.com/2012/03/11/obtendo-o-endereco-ip-real-da-maquina-na-rede/
	           // System.out.println("HostName: " + myself.getHostName() + " IP: " + myself.getHostAddress()); 
	            }
	            if(achou)break;
	         } 
	         if(achou)break;
	      }
		
		
		/*String ip=new String();
		String nome_maquina=new String();
		try {  
			java.net.InetAddress i = java.net.InetAddress.getLocalHost();  
			ip = i.getHostAddress();
			nome_maquina=i.getHostName();
		}  
		catch(Exception e){
			e.printStackTrace();
			System.out.println("ERRO na auditoria : "+ e.getMessage());
		}  
		auditoria.setIp_maquina(ip);
		auditoria.setNome_maquina(nome_maquina);*/
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Gravar [getPaginaAtual()=" + getPaginaAtual() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	

}
