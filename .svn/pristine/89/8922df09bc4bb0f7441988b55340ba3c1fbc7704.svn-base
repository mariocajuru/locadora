package br.com.locadora.web.util;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="configuracoesForm")
@SessionScoped
public class ConfiguracoesForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5390135856953440610L;
	private String meuTema;
    private Map<String, String> themes;

    public ConfiguracoesForm() {

        themes = new TreeMap<String, String>();  
        themes.put("Bootstrap", "bootstrap");
        themes.put("Excite Bike", "excite-bike");
        themes.put("Delta", "delta");        
        themes.put("Redmond", "redmond");
        themes.put("Afternoon", "afternoon");
        themes.put("Afterdark", "afterdark");
        themes.put("Afterwork", "afterwork");
        themes.put("Bluesky", "bluesky");        
      /*  themes.put("All Themes", "all-themes");*/
        themes.put("Aristo", "aristo");
        themes.put("Casablanca", "casablanca");
        themes.put("Blitzer", "blitzer");
        themes.put("Black-Tie", "black-tie");        
        themes.put("Cruze", "cruze");
        themes.put("Cupertino", "cupertino");
        themes.put("Flick", "flick");
        themes.put("Eggplant", "eggplant");
        themes.put("Dot-Luv", "dot-luv");
        themes.put("Dark-Hive", "dark-hive");        
        themes.put("Vader", "vader");
        themes.put("Sunny", "sunny");
        themes.put("Pepper-Grinder", "pepper-grinder");
        themes.put("Home", "home");        
        themes.put("Glass-X", "glass-x");
        themes.put("Ui-Lightness", "ui-lightness");
        themes.put("Start", "start");
        themes.put("Overcast", "overcast");
        themes.put("Ui-Darkness", "ui-darkness");
        themes.put("South-Street", "south-street");
        themes.put("Mint-Choc", "mint-choc");
        themes.put("Smoothness", "smoothness");
        themes.put("Midnight", "midnight");
        themes.put("Sam", "sam");        
        themes.put("Le-Frog", "le-frog");
        themes.put("Trontastic", "trontastic");
        themes.put("Rocket", "rocket");
        themes.put("Humanity", "humanity");
        themes.put("Swanky-Purse", "swanky-purse");
        themes.put("Redmond", "redmond");
        themes.put("Hot-Sneaks", "hot-sneaks");
        
       /* themes.put("South Street", "south-street");  
        themes.put("Casablanca", "casablanca"); 
        themes.put("Cupertino", "cupertino"); 
        themes.put("Vader", "vader"); 
        themes.put("Trontastic", "trontastic"); 
        themes.put("Swanky Purse", "swanky-purse"); 
        themes.put("Overcast", "overcast"); 
        themes.put("Bluesky", "bluesky"); 
        themes.put("Black Tie", "black-tie"); 
        themes.put("Flick", "flick");
        themes.put("Hot Sneaks", "hot-sneaks");
        themes.put("Home", "home");
        themes.put("Sunny", "sunny");
        themes.put("Smoothness", "smoothness");
        themes.put("Glass X", "glass-x");
        themes.put("Excite Bike", "excite-bike");*/

		//aqui voce pode retornar seu tema atual do banco
    }

	public void salvarTema() {  
		//aqui voce pode salvar seu tema no banco
    }

	@PostConstruct
	public void testandoNumeroDaSessao(){
		System.out.println("Numero da Sessao Inicio do ConfiguracoesForm "+hashCode());
	}
	
	@PreDestroy
	public void testandoNumeroDaSessaoFim(){
		System.out.println("Numero da Sessao Fim do ConfiguracoesForm "+hashCode());
	}
	
	public String getMeuTema() {
        if(meuTema == null) 
        	meuTema = "bootstrap";
		return meuTema;
    }

    public void setMeuTema(String meuTema) {
        this.meuTema = meuTema;
    }

	public Map<String, String> getThemes() {
        return themes;
    }

    public void setThemes(Map<String, String> themes) {
        this.themes = themes;
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
