package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.ImovelCaracteristicas;
import br.com.locadora.modelo.Imovel;

public interface ImovelCaracteristicasDAO {
	
	public void salvar(ImovelCaracteristicas  imovelCaracteristicas);

	public void atualizar(ImovelCaracteristicas imovelCaracteristicas);

	public void excluir(ImovelCaracteristicas imovelCaracteristicas);
	
	public void excluirTudo();

	public ImovelCaracteristicas carregar(Integer codigo);

	public ImovelCaracteristicas buscarPorImovelCaracteristicas(String imovelCaracteristicas);

	public List<ImovelCaracteristicas> listar();
	
	public List<ImovelCaracteristicas> listaImovelCaracteristicas(Imovel imovel);
}
