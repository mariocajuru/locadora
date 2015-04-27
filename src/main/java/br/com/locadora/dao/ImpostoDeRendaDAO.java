package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.ImpostoDeRenda;

public interface ImpostoDeRendaDAO {
	public void salvar(ImpostoDeRenda  impostoDeRenda);

	public void atualizar(ImpostoDeRenda impostoDeRenda);

	public void excluir(ImpostoDeRenda impostoDeRenda);

	public ImpostoDeRenda carregar(Integer impRenId);

	public ImpostoDeRenda buscarPorImpostoDeRenda(Integer mes, Integer ano);

	public List<ImpostoDeRenda> listar();
	
	public boolean dependecias(ImpostoDeRenda impostoDeRenda);
}
