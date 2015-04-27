package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.GrupoDeContasDAO;
import br.com.locadora.modelo.GrupoDeContas;
import br.com.locadora.util.DAOFactory;

public class GrupoDeContasRN {
	private GrupoDeContasDAO grupoDeContasDAO;
	public GrupoDeContasRN() {
		this.grupoDeContasDAO = DAOFactory.criarGrupoDeContasDAO();
	}
	public GrupoDeContas carregar(Integer gruConId) {
		return this.grupoDeContasDAO.carregar(gruConId);

	}

	public GrupoDeContas buscarPorGrupoDeContas(String grupoDeContas) {
		return this.grupoDeContasDAO.buscarPorGrupoDeContas(grupoDeContas);
	}

	public void salvar(GrupoDeContas grupoDeContas) {
		Integer codigo = grupoDeContas.getGruConId();
		if (codigo == null || codigo == 0) {
			this.grupoDeContasDAO.salvar(grupoDeContas);
		} else {
			this.grupoDeContasDAO.atualizar(grupoDeContas);
		}

	}

	public boolean excluir(GrupoDeContas grupoDeContas) {
	//	if(this.contasDebitoDAO.dependecias(contasDebito)){
			this.grupoDeContasDAO.excluir(grupoDeContas);
			return true;
	/*	}else{
			try {
				throw new UtilException("Erro ao excluir. Esse ContasDebito tem ligações com outros locação e não pode ser deletado.");
			} catch (UtilException e) {
				return false;
			}
		}*/
	}

	public List<GrupoDeContas> listar() {
		return this.grupoDeContasDAO.listar();
	}

}
