package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.HistoricoPessoa;

public interface HistoricoPessoaDAO {
	public void salvar(HistoricoPessoa historicoPessoa);

	public void atualizar(HistoricoPessoa historicoPessoa);

	public void excluir(HistoricoPessoa historicoPessoa);

	public HistoricoPessoa carregar(Integer hisId);

	public HistoricoPessoa buscarPorHistoricoPessoa(String historicoPessoa);

	public List<HistoricoPessoa> listar();
}
