package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Bairro;

public interface BairroDAO {
	public void salvar(Bairro bairro);

	public void atualizar(Bairro bairro);

	public void excluir(Bairro bairro);

	public Bairro carregar(Integer baiId);

	public Bairro buscarPorBairro(String bairro);

	public List<Bairro> listar();
	
	public boolean dependecias(Bairro bairro);
}
