package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Proposta;

public interface PropostaDAO {
	public void salvar(Proposta proposta);

	public void atualizar(Proposta proposta);

	public void excluir(Proposta proposta);

	public Proposta carregar(Integer proId);

	public Proposta buscarPorProposta(String proposta);

	public List<Proposta> listar();
	
	public List<Proposta> carregarPorImovel(Imovel imovel);
}
