package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.ChaveNoQuadro;
import br.com.locadora.modelo.EmprestimoChave;
import br.com.locadora.modelo.Imovel;
public interface ChaveNoQuadroDAO {
	public void salvar(ChaveNoQuadro chaveNoQuadro);

	public void atualizar(ChaveNoQuadro chaveNoQuadro);

	public void excluir(ChaveNoQuadro chaveNoQuadro);

	public ChaveNoQuadro carregar(Integer codigo);

	public ChaveNoQuadro buscarChaveNoQuadro(int idQuadro, int idImovel);

	public List<ChaveNoQuadro> listar();
	
	public List<ChaveNoQuadro> carregarChaveNoQuadroPorFilial(Integer idQuadro);
	
	public ChaveNoQuadro buscarChaveAtravesDoEmprestimo(EmprestimoChave  emprestimoChave);
	
	public List<ChaveNoQuadro> carregarChaveNoQuadroPorImovel(Imovel imovel);
	
	public List<Imovel> carregarImoveisPorPosicao(int posicao);
}
