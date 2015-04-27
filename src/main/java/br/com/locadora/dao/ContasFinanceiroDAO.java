package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.ContasFinanceiro;
import br.com.locadora.modelo.GrupoDeContas;

public interface ContasFinanceiroDAO {
	public void salvar(ContasFinanceiro centroDeCusto);

	public void atualizar(ContasFinanceiro centroDeCusto);

	public void excluir(ContasFinanceiro centroDeCusto);

	public ContasFinanceiro carregar(Integer conFinId);

	public ContasFinanceiro buscarContasFinanceiro(String centroDeCusto);

	public List<ContasFinanceiro> listar();

	public boolean dependecias(ContasFinanceiro centroDeCusto);

	public ContasFinanceiro carregarContasFinanceiroCredito(GrupoDeContas grupoDeContas);

	public ContasFinanceiro carregarContasFinanceiroDebito(GrupoDeContas grupoDeContas);
}
