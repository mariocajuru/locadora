package br.com.locadora.rn;

import java.util.Date;
import java.util.List;

import br.com.locadora.dao.EmprestimoChaveDAO;
import br.com.locadora.modelo.EmprestimoChave;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.util.DAOFactory;

public class EmprestimoChaveRN {
	private EmprestimoChaveDAO emprestimoChaveDAO;

	public EmprestimoChaveRN() {
		this.emprestimoChaveDAO = DAOFactory.criarEmprestimoChaveDAO();
	}

	public EmprestimoChave carregar(Integer sedId) {
		return this.emprestimoChaveDAO.carregar(sedId);

	}

	public EmprestimoChave buscarPorEmprestimo(String emprestimo) {
		return this.emprestimoChaveDAO.buscarPorEmprestimo(emprestimo);
	}

	public void salvar(EmprestimoChave emprestimoChave) {
		Integer codigo = emprestimoChave.getEmpId();
		if (codigo == null || codigo == 0) {
			emprestimoChave.setEmpDataCadastro(new Date());
			this.emprestimoChaveDAO.salvar(emprestimoChave);
		} else {
			this.emprestimoChaveDAO.atualizar(emprestimoChave);
		}

	}

	public void excluir(EmprestimoChave emprestimoChave) {
		this.emprestimoChaveDAO.excluir(emprestimoChave);
	}

	public List<EmprestimoChave> listar() {
		return this.emprestimoChaveDAO.listar();
	}
	
	public List<EmprestimoChave> carregarEmprestimoPorPessoa(Pessoa pessoa){
		return this.emprestimoChaveDAO.carregarEmprestimoPorPessoa(pessoa);
	}
	
	public List<EmprestimoChave> listaEmprestimoOrdenadaPorPessoa(){
		return this.emprestimoChaveDAO.listaEmprestimoOrdenadaPorPessoa();
	}
}
