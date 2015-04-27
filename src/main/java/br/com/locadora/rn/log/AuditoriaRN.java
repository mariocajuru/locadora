package br.com.locadora.rn.log;


import java.util.List;

import br.com.locadora.dao.log.AuditoriaDAO;
import br.com.locadora.util.DAOFactory;

public class AuditoriaRN {
	private AuditoriaDAO auditoriaDAO;
	public AuditoriaRN() {
		this.auditoriaDAO = DAOFactory.criarAuditoriaDAO();
	}
	public List<Object[]> listar() {
		return this.auditoriaDAO.listar();
	}
}
