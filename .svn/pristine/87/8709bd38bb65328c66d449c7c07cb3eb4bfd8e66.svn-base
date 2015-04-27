package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Fotoimovel;
import br.com.locadora.modelo.Imovel;

public interface FotoimovelDAO {
	public void salvar(Fotoimovel fotoimovel);

	public void atualizar(Fotoimovel fotoimovel);

	public void excluir(Fotoimovel fotoimovel);

	public Fotoimovel carregar(Integer codigo);

	public Fotoimovel buscarPorFotoimovel(String fotoimovel);
	
	public List<Fotoimovel> carregarFotosDeImovel(Imovel imovel);

	public List<Fotoimovel> listar();
	
	public Fotoimovel buscarPorNomeEImovel(String fotoimovel, Imovel imovel);
}
