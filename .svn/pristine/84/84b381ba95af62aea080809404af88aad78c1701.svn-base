package br.com.locadora.rn;

import java.util.Date;
import java.util.List;

import br.com.locadora.dao.ImovelDAO;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.util.DAOFactory;

public class ImovelRN {
private ImovelDAO imovelDAO;
	public ImovelRN() {
		this.imovelDAO= DAOFactory.criarImovelDAO();
	}

	public Imovel carregar(Integer imoId) {
		return this.imovelDAO.carregar(imoId);

	}

	public Imovel buscarPorImovel(String imovel) {
		return this.imovelDAO.buscarPorImovel(imovel);
	}

	public void salvar(Imovel imovel) {
		Integer codigo = imovel.getImoId();
		if (codigo == null || codigo == 0) {
			imovel.setImoDataCadastro(new Date());
			this.imovelDAO.salvar(imovel);
		} else {
			imovel.setImoDataUltimaAlteracao(new Date());
			this.imovelDAO.atualizar(imovel);
		}

	}

	public void excluir(Imovel imovel) {
		this.imovelDAO.excluir(imovel);
	}

	public List<Imovel> listar() {
		return this.imovelDAO.listar();
	}
	
	public Imovel carregarImovelAtravesDoEndereco(Endereco endereco){
		return this.imovelDAO.carregarImovelAtravesDoEndereco(endereco);
	}
	
	public void verificarExistenciaImovelDesejado(Imovel imoNovo){
		this.imovelDAO.verificarExistenciaImovelDesejado(imoNovo);
	}
	
	public List<Imovel> listarImoveisPorPropritario(Pessoa pessoa) {
		return this.imovelDAO.listarImoveisPorPropritario(pessoa);
	}
}
