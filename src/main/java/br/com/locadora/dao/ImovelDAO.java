package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Pessoa;

public interface ImovelDAO {
	public void salvar(Imovel imovel);

	public void atualizar(Imovel imovel);

	public void excluir(Imovel imovel);

	public Imovel carregar(Integer imoId);

	public Imovel buscarPorImovel(String imovel);

	public List<Imovel> listar();

	public List<Imovel> listarImoveisPorPropritario(Pessoa pessoa);
	
	public Imovel carregarImovelAtravesDoEndereco(Endereco endereco);
	
	public void verificarExistenciaImovelDesejado(Imovel imoNovo);
}
