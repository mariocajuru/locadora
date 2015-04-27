package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.EnderecoImovelDesejado;

public interface EnderecoImovelDesejadoDAO {
	public void salvar(EnderecoImovelDesejado enderecoImovelDesejado);

	public void atualizar(EnderecoImovelDesejado enderecoImovelDesejado);

	public void excluir(EnderecoImovelDesejado enderecoImovelDesejado);

	public EnderecoImovelDesejado carregar(Integer endId);

	public EnderecoImovelDesejado buscarPorEnderecoImovelDesejado(String rua);

	public List<EnderecoImovelDesejado> listar();
}
