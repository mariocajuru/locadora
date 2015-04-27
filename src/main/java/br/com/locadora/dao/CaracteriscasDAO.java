package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Caracteristicas;


public interface CaracteriscasDAO {

	public void salvar(Caracteristicas caracteriscas);

	public void atualizar(Caracteristicas caracteriscas);

	public void excluir(Caracteristicas caracteriscas);

	public Caracteristicas carregar(Integer detId);

	public Caracteristicas buscarPorCaracteriscas(String caracteriscas);

	public List<Caracteristicas> listar();
	
	public List<Caracteristicas> listarCaracteriscasUnitarias();
	
	public List<Caracteristicas> listarCaracteriscasQtd();
	
	public boolean dependecias(Caracteristicas caracteristicas);
}
