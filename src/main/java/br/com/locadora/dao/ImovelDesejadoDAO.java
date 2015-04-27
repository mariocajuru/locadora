package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.ImovelDesejado;

public interface ImovelDesejadoDAO {
	public void salvar(ImovelDesejado imovelDesejado);

	public void atualizar(ImovelDesejado imovelDesejado);

	public void excluir(ImovelDesejado imovelDesejado);

	public ImovelDesejado carregar(Integer imo_des_Id);

	public ImovelDesejado buscarPorImovelDesejado(String imovelDesejado);

	public List<ImovelDesejado> listar();
}
