package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.RegiaoDAO;
import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Regiao;
import br.com.locadora.modelo.RegiaoCoordenada;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class RegiaoRN {

	private RegiaoDAO regiaoDAO;

	public RegiaoRN() {
		this.regiaoDAO = DAOFactory.criarRegiaoDAO();
	}

	public Regiao carregar(Integer regId) {
		return this.regiaoDAO.carregar(regId);

	}

	public Regiao buscarPorRegiao(String regiao) {
		return this.regiaoDAO.buscarPorRegiao(regiao);
	}

	public void salvar(Regiao regiao) {
		Integer codigo = regiao.getRegId();
		if (codigo == null || codigo == 0) {
			this.regiaoDAO.salvar(regiao);
		} else {
			this.regiaoDAO.atualizar(regiao);
		}

	}

	public boolean excluir(Regiao regiao) {		
		if(this.regiaoDAO.dependecias(regiao)){
			List<RegiaoCoordenada> lista=this.regiaoDAO.carregarCoordenadasPorRegiao(regiao);
			for(RegiaoCoordenada coordenada: lista)
				this.regiaoDAO.excluirCoordenada(coordenada);
			this.regiaoDAO.excluir(regiao);
			return true;
		}else{
			try {
				throw new UtilException("Erro ao excluir. Essa região tem ligações com outros elementos e não pode ser deletado.");
			} catch (UtilException e) {				
				return false;
			}
		}
	}

	public List<Regiao> listar() {
		return this.regiaoDAO.listar();
	}
	public List<Bairro> carregarBairrosPorRegiao(Regiao regiao){
		return this.regiaoDAO.carregarBairrosPorRegiao(regiao);
	}

	public List<RegiaoCoordenada> carregarCoordenadasPorRegiao(Regiao regiao){
		return this.regiaoDAO.carregarCoordenadasPorRegiao(regiao);
	}

	public void salvarCoordenada(RegiaoCoordenada regiaoCoordenada){
		this.regiaoDAO.salvarCoordenada(regiaoCoordenada);
	}

	public void excluirCoordenada(RegiaoCoordenada regiaoCoordenada){
		this.regiaoDAO.excluirCoordenada(regiaoCoordenada);
	}
	
	public RegiaoCoordenada carregarRegiaoCoordenada(Integer id){
		return this.regiaoDAO.carregarRegiaoCoordenada(id);
	}
}
