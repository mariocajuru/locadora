package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.ImovelProcurado;

public interface ImovelProcuradoDAO {
	public void salvar(ImovelProcurado imovelProcurado);

	public void atualizar(ImovelProcurado imovelProcurado);

	public void excluir(ImovelProcurado imovelProcurado);

	public ImovelProcurado carregar(Integer imoProId);

	public ImovelProcurado buscarPorImovelProcurado(String imovelProcurado);

	public List<ImovelProcurado> listar();
	
	public void procurarImovel(Imovel imovel);
}
