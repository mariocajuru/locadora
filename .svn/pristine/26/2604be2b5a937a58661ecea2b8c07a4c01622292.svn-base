package br.com.locadora.rn;
import java.util.Date;
import java.util.List;


import br.com.locadora.dao.ImovelDesejadoDAO;
import br.com.locadora.modelo.ImovelDesejado;
import br.com.locadora.util.DAOFactory;

public class ImovelDesejadoRN {
	private ImovelDesejadoDAO imovelDesejadoDAO;
	
public ImovelDesejadoRN() {
	this.imovelDesejadoDAO=DAOFactory.criarImovelDesejadoDAO();
}
public ImovelDesejado carregar(Integer imo_des_Id) {
	return this.imovelDesejadoDAO.carregar(imo_des_Id);

}

public ImovelDesejado buscarPorImovelDesejado(String imovelDesejado) {
	return this.imovelDesejadoDAO.buscarPorImovelDesejado(imovelDesejado);
}

public void salvar(ImovelDesejado imovelDesejado) {
	Integer codigo = imovelDesejado.getImoDesId();
	if (codigo == null || codigo == 0) {
		imovelDesejado.setImoDesDataCadastro(new Date());
		this.imovelDesejadoDAO.salvar(imovelDesejado);
	} else {
		this.imovelDesejadoDAO.atualizar(imovelDesejado);
	}

}

public void excluir(ImovelDesejado  imovelDesejado) {
	this.imovelDesejadoDAO.excluir(imovelDesejado);
}

public List<ImovelDesejado> listar() {
	return this.imovelDesejadoDAO.listar();
}
}
