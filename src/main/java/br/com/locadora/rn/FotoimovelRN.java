package br.com.locadora.rn;
import java.util.List;

import br.com.locadora.dao.FotoimovelDAO;
import br.com.locadora.modelo.Fotoimovel;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.util.DAOFactory;

public class FotoimovelRN {
	private FotoimovelDAO fotoimovelDAO;
	public FotoimovelRN() {
		this.fotoimovelDAO=DAOFactory.criarFotoimovelDAO();
	}
	public Fotoimovel carregar(Integer fotId) {
		return this.fotoimovelDAO.carregar(fotId);

	}

	public Fotoimovel buscarPorFotoimovel(String nomeFoto) {
		return this.fotoimovelDAO.buscarPorFotoimovel(nomeFoto);
	}

	public void salvar(Fotoimovel fotoimovel) {
		Integer codigo = fotoimovel.getFotId();
		if (codigo == null || codigo == 0) {
			this.fotoimovelDAO.salvar(fotoimovel);
		} else {
			this.fotoimovelDAO.atualizar(fotoimovel);
		}

	}

	public void excluir(Fotoimovel fotoimovel) {
		this.fotoimovelDAO.excluir(fotoimovel);
	}

	public List<Fotoimovel> listar() {
		return this.fotoimovelDAO.listar();
	}
	public List<Fotoimovel> carregarFotosDeImovel(Imovel imovel) {
		return this.fotoimovelDAO.carregarFotosDeImovel(imovel);
	}
	
	public Fotoimovel buscarPorNomeEImovel(String fotoimovel, Imovel imovel){
		return this.fotoimovelDAO.buscarPorNomeEImovel(fotoimovel, imovel);
	}
}
