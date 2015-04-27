package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.EnderecoImovelDesejadoDAO;
import br.com.locadora.modelo.EnderecoImovelDesejado;
import br.com.locadora.util.DAOFactory;

public class EnderecoImovelDesejadoRN {
	private EnderecoImovelDesejadoDAO enderecoImovelDesejadoDAO;

	public EnderecoImovelDesejadoRN() {
		this.enderecoImovelDesejadoDAO = DAOFactory.criarEnderecoImovelDesejadoDAO();
	}

	public EnderecoImovelDesejado carregar(Integer end_imovel_desejado_Id) {
		return this.enderecoImovelDesejadoDAO.carregar(end_imovel_desejado_Id);

	}

	public EnderecoImovelDesejado buscarPorSede(String rua) {
		return this.enderecoImovelDesejadoDAO.buscarPorEnderecoImovelDesejado(rua);
	}

	public void salvar(EnderecoImovelDesejado enderecoImovelDesejado) {
		Integer codigo = enderecoImovelDesejado.getEndImoDesId();
		if (codigo == null || codigo == 0) {
			this.enderecoImovelDesejadoDAO.salvar(enderecoImovelDesejado);
		} else {
			this.enderecoImovelDesejadoDAO.atualizar(enderecoImovelDesejado);
		}

	}

	public void excluir(EnderecoImovelDesejado enderecoImovelDesejado) {
		this.enderecoImovelDesejadoDAO.excluir(enderecoImovelDesejado);
	}

	public List<EnderecoImovelDesejado> listar() {
		return this.enderecoImovelDesejadoDAO.listar();
	}
}
