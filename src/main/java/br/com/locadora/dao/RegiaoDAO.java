package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Regiao;
import br.com.locadora.modelo.RegiaoCoordenada;

public interface RegiaoDAO {
	public void salvar(Regiao regiao);

	public void atualizar(Regiao regiao);

	public void excluir(Regiao regiao);

	public Regiao carregar(Integer regId);

	public Regiao buscarPorRegiao(String regiao);

	public List<Regiao> listar();
	
	public List<Bairro> carregarBairrosPorRegiao(Regiao regiao);
	
	public List<RegiaoCoordenada> carregarCoordenadasPorRegiao(Regiao regiao);
	
	public boolean dependecias(Regiao regiao);
	
	public void salvarCoordenada(RegiaoCoordenada regiaoCoordenada);
	
	public void excluirCoordenada(RegiaoCoordenada regiaoCoordenada);
	
	public RegiaoCoordenada carregarRegiaoCoordenada(Integer id);
}
