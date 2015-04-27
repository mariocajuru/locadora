package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.SeguroIncendioImovelComercial;

public interface SeguroIncendioImovelComercialDAO {
	public void salvar(SeguroIncendioImovelComercial seguroImovelComercial);

	public void atualizar(SeguroIncendioImovelComercial seguroImovelComercial);

	public void excluir(SeguroIncendioImovelComercial seguroImovelComercial);

	public SeguroIncendioImovelComercial carregar(Integer segImoComId);

	public SeguroIncendioImovelComercial buscarPorSeguroImovelComercial(String seguroImovelComercial);

	public List<SeguroIncendioImovelComercial> listar();
	
	public double bucarValorSeguroPorValorVenal(double valorVenal);
}
