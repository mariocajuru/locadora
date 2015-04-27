package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.RamoatuacaoDAO;
import br.com.locadora.modelo.Ramoatuacao;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class RamoatuacaoRN {
	private RamoatuacaoDAO ramoatuacaoDAO;

	public RamoatuacaoRN() {
		this.ramoatuacaoDAO = DAOFactory.criarRamoatuacaoDAO();
	}

	public Ramoatuacao carregar(Integer ramId) {
		return this.ramoatuacaoDAO.carregar(ramId);

	}

	public Ramoatuacao buscarPorRamoatuacao(String ramo) {
		return this.ramoatuacaoDAO.buscarPorRamoatuacao(ramo);
	}

	public void salvar(Ramoatuacao ramo) {
		Integer codigo = ramo.getRamId();
		if (codigo == null || codigo == 0) {
			this.ramoatuacaoDAO.salvar(ramo);
		} else {
			this.ramoatuacaoDAO.atualizar(ramo);
		}

	}

	public boolean excluir(Ramoatuacao ramo) {
		if(this.ramoatuacaoDAO.dependecias(ramo)){
			this.ramoatuacaoDAO.excluir(ramo);
			return true;
		}else{
			try {
				throw new UtilException("Erro ao excluir. Esse ramo tem ligações com outros elementos e não pode ser deletado.");
			} catch (UtilException e) {				
				return false;
			}
		}
	}

	public List<Ramoatuacao> listar() {
		return this.ramoatuacaoDAO.listar();
	}
}
