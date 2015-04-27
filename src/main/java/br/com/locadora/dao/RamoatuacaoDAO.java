package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Ramoatuacao;;

public interface RamoatuacaoDAO {
	public void salvar(Ramoatuacao ramo);

	public void atualizar(Ramoatuacao ramo);

	public void excluir(Ramoatuacao ramo);

	public Ramoatuacao carregar(Integer ramId);

	public Ramoatuacao buscarPorRamoatuacao(String ramo);

	public List<Ramoatuacao> listar();
	
	public boolean dependecias(Ramoatuacao  ramoatuacao);
}
