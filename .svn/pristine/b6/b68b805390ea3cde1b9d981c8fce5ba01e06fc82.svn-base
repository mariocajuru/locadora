package br.com.locadora.rn;
import java.util.Date;
import java.util.List;

import br.com.locadora.dao.ChaveNoQuadroDAO;
import br.com.locadora.modelo.ChaveNoQuadro;
import br.com.locadora.modelo.EmprestimoChave;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.util.DAOFactory;

public class ChaveNoQuadroRN {
	private ChaveNoQuadroDAO chaveNoQuadroDAO;
	public ChaveNoQuadroRN() {
		this.chaveNoQuadroDAO=DAOFactory.criarChaveNoQuadroDAO();
	}
	public ChaveNoQuadro carregar(Integer codigo) {
		return this.chaveNoQuadroDAO.carregar(codigo);

	}

	public ChaveNoQuadro buscarChaveNoQuadro(int idQuadro, int idImovel) {
		return this.chaveNoQuadroDAO.buscarChaveNoQuadro(idQuadro, idImovel);
	}

	public void salvar(ChaveNoQuadro chave) {
		chave.setChaQuaDataEntrada(new Date());
		this.chaveNoQuadroDAO.salvar(chave);


	}

	public void excluir(ChaveNoQuadro chave) {
		this.chaveNoQuadroDAO.excluir(chave);
	}

	public List<ChaveNoQuadro> listar() {
		return this.chaveNoQuadroDAO.listar();
	}

	public List<ChaveNoQuadro> carregarChaveNoQuadroPorFilial(Integer idQuadro) {

		return this.chaveNoQuadroDAO.carregarChaveNoQuadroPorFilial(idQuadro);
	}
	public ChaveNoQuadro buscarChaveAtravesDoEmprestimo(
			EmprestimoChave emprestimoChave) {
		return this.chaveNoQuadroDAO.buscarChaveAtravesDoEmprestimo(emprestimoChave);
	}

	public List<ChaveNoQuadro> carregarChaveNoQuadroPorImovel(Imovel imovel){
		return this.chaveNoQuadroDAO.carregarChaveNoQuadroPorImovel(imovel);
	}

	public void excluirChaveNoQuadroDeImovel(Imovel imovel) {
		List<ChaveNoQuadro> lista = this.chaveNoQuadroDAO.carregarChaveNoQuadroPorImovel(imovel);

		for (ChaveNoQuadro p : lista) {
			this.chaveNoQuadroDAO.excluir(p);
		}
	}
	
	public List<Imovel> carregarImoveisPorPosicao(int posicao) {
		return this.chaveNoQuadroDAO.carregarImoveisPorPosicao(posicao);
	}
}
