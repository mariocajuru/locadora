package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.PlanoDeContasDAO;
import br.com.locadora.modelo.GrupoDeContas;
import br.com.locadora.modelo.PlanoDeContas;
import br.com.locadora.util.DAOFactory;

public class PlanoDeContasRN {

	private PlanoDeContasDAO planoDeContasDAO;
	public PlanoDeContasRN() {
		this.planoDeContasDAO = DAOFactory.criarPlanoDeContasDAO();
	}
	public PlanoDeContas carregar(Integer plaConId) {
		return this.planoDeContasDAO.carregar(plaConId);

	}

	public PlanoDeContas buscarPorPlanoDeContas(String planoDeContas) {
		return this.planoDeContasDAO.buscarPorPlanoDeContas(planoDeContas);
	}

	public void salvar(PlanoDeContas planoDeContas) {
		Integer codigo = planoDeContas.getPlaConId();
		if (codigo == null || codigo == 0) {
			this.planoDeContasDAO.salvar(planoDeContas);
		} else {
			this.planoDeContasDAO.atualizar(planoDeContas);
		}

	}

	public boolean excluir(PlanoDeContas planoDeContas) {
		//	if(this.contasDebitoDAO.dependecias(contasDebito)){
		this.planoDeContasDAO.excluir(planoDeContas);
		return true;
		/*	}else{
			try {
				throw new UtilException("Erro ao excluir. Esse ContasDebito tem ligações com outros locação e não pode ser deletado.");
			} catch (UtilException e) {
				return false;
			}
		}*/
	}

	public List<PlanoDeContas> listar() {
		return this.planoDeContasDAO.listar();
	}

	public List<PlanoDeContas> listarPorCredito() {
		return this.planoDeContasDAO.listarPorCredito();
	}

	public List<PlanoDeContas> listarPorDebito() {
		return this.planoDeContasDAO.listarPorDebito();
	}

	public PlanoDeContas carregarPlanoDeContasCreditoValor(GrupoDeContas grupoDeContas){
		return this.planoDeContasDAO.carregarPlanoDeContasCreditoValor(grupoDeContas);
	}

	public PlanoDeContas carregarPlanoDeContasDebitoValor(GrupoDeContas grupoDeContas){
		return this.planoDeContasDAO.carregarPlanoDeContasDebitoValor(grupoDeContas);
	}

	public PlanoDeContas carregarPlanoDeContasCreditoMulta(GrupoDeContas grupoDeContas){
		return this.planoDeContasDAO.carregarPlanoDeContasCreditoMulta(grupoDeContas);
	}

	public PlanoDeContas carregarPlanoDeContasDebitoMulta(GrupoDeContas grupoDeContas){
		return this.planoDeContasDAO.carregarPlanoDeContasDebitoMulta(grupoDeContas);
	}

	public PlanoDeContas carregarPlanoDeContasCreditoJuros(GrupoDeContas grupoDeContas){
		return this.planoDeContasDAO.carregarPlanoDeContasCreditoJuros(grupoDeContas);
	}

	public PlanoDeContas carregarPlanoDeContasDebitoJuros(GrupoDeContas grupoDeContas){
		return this.planoDeContasDAO.carregarPlanoDeContasDebitoJuros(grupoDeContas);
	}
	
	public List<PlanoDeContas> listaPlanoValorPorGrupo(GrupoDeContas grupoDeContas){
		return this.planoDeContasDAO.listaPlanoValorPorGrupo(grupoDeContas);
	}

}
