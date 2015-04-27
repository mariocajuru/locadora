package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.ImovelProcuradoDAO;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.ImovelProcurado;
import br.com.locadora.util.DAOFactory;

public class ImovelProcuradoRN {
	private ImovelProcuradoDAO imovelProcuradoDAO;
	public ImovelProcuradoRN() {
		this.imovelProcuradoDAO=DAOFactory.criarImovelProcuradoDAO();
	}
	public ImovelProcurado carregar(Integer imoProId) {
		return this.imovelProcuradoDAO.carregar(imoProId);

	}

	public ImovelProcurado buscarPorImovelDesejado(String imovelProcurado) {
		return this.imovelProcuradoDAO.buscarPorImovelProcurado(imovelProcurado);
	}

	public void salvar(ImovelProcurado imovelProcurado) {
		Integer codigo = imovelProcurado.getImoProId();
		if (codigo == null || codigo == 0) {
			this.imovelProcuradoDAO.salvar(imovelProcurado);
		} else {
			this.imovelProcuradoDAO.atualizar(imovelProcurado);
		}

	}

	public void excluir(ImovelProcurado  imovelProcurado) {
		this.imovelProcuradoDAO.excluir(imovelProcurado);
	}

	public List<ImovelProcurado> listar() {
		return this.imovelProcuradoDAO.listar();
	}
	
	public void procurarImovel(Imovel imovel){
		this.imovelProcuradoDAO.procurarImovel(imovel);
	}
}
