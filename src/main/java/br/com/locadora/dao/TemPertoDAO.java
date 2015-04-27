package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.TemPerto;

public interface TemPertoDAO {
	public void salvar(TemPerto temPerto);

	public void atualizar(TemPerto temPerto);

	public void excluir(TemPerto temPerto);

	public TemPerto carregar(Integer temPerId);

	public TemPerto buscarPorTemPerto(String temPerto);

	public List<TemPerto> listar();
	
	public List<TemPerto> carregarListaPorImovel(Imovel imovel);
	
	public boolean dependecias(TemPerto temPerto);
}
