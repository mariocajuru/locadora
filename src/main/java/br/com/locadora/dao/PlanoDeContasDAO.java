package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.GrupoDeContas;
import br.com.locadora.modelo.PlanoDeContas;

public interface PlanoDeContasDAO {
	public void salvar(PlanoDeContas planoDeContas);

	public void atualizar(PlanoDeContas planoDeContas);

	public void excluir(PlanoDeContas planoDeContas);

	public PlanoDeContas carregar(Integer plaConId);

	public PlanoDeContas buscarPorPlanoDeContas(String planoDeContas);

	public List<PlanoDeContas> listar();
	
	public List<PlanoDeContas> listarPorCredito();
	
	public List<PlanoDeContas> listarPorDebito();
	
	public boolean dependecias(PlanoDeContas planoDeContas);
	
	public PlanoDeContas carregarPlanoDeContasCreditoValor(GrupoDeContas grupoDeContas);
	
	public PlanoDeContas carregarPlanoDeContasDebitoValor(GrupoDeContas grupoDeContas);
	
	public PlanoDeContas carregarPlanoDeContasCreditoMulta(GrupoDeContas grupoDeContas);
	
	public PlanoDeContas carregarPlanoDeContasDebitoMulta(GrupoDeContas grupoDeContas);
	
	public PlanoDeContas carregarPlanoDeContasCreditoJuros(GrupoDeContas grupoDeContas);
	
	public PlanoDeContas carregarPlanoDeContasDebitoJuros(GrupoDeContas grupoDeContas);
	
	public List<PlanoDeContas> listaPlanoValorPorGrupo(GrupoDeContas grupoDeContas);
}
