package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Tipoimovel;;

public interface TipoImovelDAO {
	public void salvar(Tipoimovel tipoImovel);

	public void atualizar(Tipoimovel tipoImovel);

	public void excluir(Tipoimovel tipoImovel);

	public Tipoimovel carregar(Integer tipId);

	public Tipoimovel buscarPorTipoImovel(String tipoImovel);

	public List<Tipoimovel> listar();
	
	public boolean dependecias(Tipoimovel tipoimovel);
}
