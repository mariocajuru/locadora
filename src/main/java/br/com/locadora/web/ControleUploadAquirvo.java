package br.com.locadora.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.locadora.modelo.Fotoimovel;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.rn.FotoimovelRN;
import br.com.locadora.rn.ImovelRN;
import br.com.locadora.web.util.ContextoUtil;

@ManagedBean(name = "uploadAquirvo")
@ViewScoped
public class ControleUploadAquirvo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3008532606938325719L;
	@SuppressWarnings("unused")
	private String destination = "C:\\Users\\desenvjava01\\Documents\\Projetos Eclipse\\Locadora\\WebContent\\resources\\upload\\";
	UploadedFile uploadedFile;
	private Imovel imovel = new Imovel();
	private Fotoimovel fotoimovel = new Fotoimovel();
	private List<Fotoimovel> listaFotos = new ArrayList<Fotoimovel>();
	private ServletContext servletContext=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	private List<Fotoimovel> listaFotosPorImovel;
	
	@Getter @Setter private ContextoBean contextoBean=ContextoUtil.getContextoBean();
	   
	public void carregarFotos() {
		this.listaFotos = null;
		FotoimovelRN fotoimovelRN = new FotoimovelRN();
		this.listaFotos = fotoimovelRN.carregarFotosDeImovel(this.imovel);
	}

	public void upload(FileUploadEvent event) throws Exception{
		uploadedFile = event.getFile();
		//FacesMessage msg = new FacesMessage("Sucesso! ", "caminho e imagem gravado com sucesso !");
		//FacesContext.getCurrentInstance().addMessage(null, msg);
		// System.out.println("Imovel id:"+imovel.getImoId());
		// Faça o que quiser com o arquivo
		FotoimovelRN fotoimovelRN = new FotoimovelRN();
		this.fotoimovel=fotoimovelRN.buscarPorNomeEImovel(event.getFile().getFileName(), imovel);
		if(this.fotoimovel==null){
			this.fotoimovel=new Fotoimovel();}else{
				if(this.fotoimovel.getFotId()>0){
					return;
				}
			}
		try {
			copyFile(event.getFile().getFileName(), event.getFile()
					.getInputstream());			
			
			this.fotoimovel.setImovel(imovel);
			this.fotoimovel.setFotArquivo("upload/"+imovel.getTipoimovel().getTipNome().trim()+ "/" + imovel.getImoId()+"/");
			
		//	System.out.println("Quantas vezes isso vai rodar?"+event.getFile().getFileName());
			this.fotoimovel.setFotNome(event.getFile().getFileName());
			try {
				fotoimovelRN.salvar(fotoimovel);
				//addMenssagem(fotoimovel.getFotNome());
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO , ", e
								.getMessage()));	}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.imovel.setImoFoto(true);
		new ImovelRN().salvar(this.imovel);
	}
	/*public void addMenssagem(String menssagem){
		FacesContext context = FacesContext.getCurrentInstance();
		//menssagem+=this.fotoimovel.getFotNome();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
		"Arquivos", menssagem);
//context.addMessage(null, message);
	}*/

	public void copyFile(String fileName, InputStream in) {
		try {
			// escrever o inputStream a um FileOutputStrea
			/*String caminho = destination + imovel.getTipoimovel().getTipNome()
					+ "\\" + imovel.getImoId();*/
			
			// Atenção; pode-se mudar o caminho da gravação do arquivo nessa String caminho.
			String caminho=servletContext.getRealPath(File.separator+"resources"+File.separator + "upload" + File.separator + (imovel.getTipoimovel().getTipNome().trim())
					+ File.separator +imovel.getImoId());
			System.out.println(caminho);
			File diretorio = new File(caminho);
			diretorio.mkdirs();
			
			 /** if( diretorio.mkdir() ){
			 * System.out.println("diretório criado com sucesso"); }else{
			 * System.out.println("não foi possível criar o diretorio"); }*/
			 
			OutputStream out = new FileOutputStream(new File(caminho + "\\"
					+ fileName));
			
			int read = 0;
			byte[] bytes = new byte[1024];//poderia ser byte[] bytes = event.getFile().getContents(); 
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();			
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO:", e
							.getMessage()));
		}
	}

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Fotoimovel getFotoimovel() {
		return fotoimovel;
	}

	public void setFotoimovel(Fotoimovel fotoimovel) {
		this.fotoimovel = fotoimovel;
	}

	public List<Fotoimovel> getListaFotos() {
		return listaFotos;
	}

	public void setListaFotos(List<Fotoimovel> listaFotos) {
		this.listaFotos = listaFotos;
	}

	public List<Fotoimovel> getListaFotosPorImovel() {	
		this.listaFotosPorImovel=null;
		if(this.imovel.getImoId()!=0){
		this.imovel=new ImovelRN().carregar(imovel.getImoId());
		this.listaFotosPorImovel=new	FotoimovelRN().carregarFotosDeImovel(this.imovel);
		}
		return listaFotosPorImovel;
	}

	public void setListaFotosPorImovel(List<Fotoimovel> listaFotosPorImovel) {
		this.listaFotosPorImovel = listaFotosPorImovel;
	}
	

	public void ativarFotoWeb(Imovel imo){
		this.imovel=new ImovelRN().carregar(imo.getImoId());
	}
	
	public void fotoWeb(){
		if(this.fotoimovel.getFotWeb()==null){
			this.fotoimovel.setFotWeb(false);
		}
		if(	this.fotoimovel.getFotWeb()){
			this.fotoimovel.setFotWeb(false);
		}else{
			this.fotoimovel.setFotWeb(true);
		}
		
		new FotoimovelRN().salvar(this.fotoimovel);
	}
	
/*	visualizar as imagens dentro de upload.xhtml


	<img src="file:///C:/teste/d.jpg" />
	<p:galleria value="#{uploadAquirvo.listaFotos}" var="image"
	panelWidth="500" panelHeight="313" showCaption="true"
	rendered="#{not empty uploadAquirvo.listaFotos}">
	<p:graphicImage library="#{image.fotArquivo}"
	name="#{image.fotNome}"
	alt="Descrição da imagem #{image.fotArquivo}"
	title="#{image.fotNome}" />
	</p:galleria>*/
}
