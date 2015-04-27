package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.EmprestimoChave;
import br.com.locadora.modelo.Filial;
import br.com.locadora.modelo.Pessoa;

public interface EmprestimoChaveDAO {
	public void salvar(EmprestimoChave emprestimoChave);

	public void atualizar(EmprestimoChave emprestimoChave);

	public void excluir(EmprestimoChave emprestimoChave);

	public EmprestimoChave carregar(Integer empId);

	public EmprestimoChave buscarPorEmprestimo(String emprestimo);

	public List<EmprestimoChave> listar();
	
	public List<EmprestimoChave> carregarEmprestimoPorFilial(Filial filial);
	
	public List<EmprestimoChave> carregarEmprestimoPorPessoa(Pessoa pessoa);
	
	public List<EmprestimoChave> listaEmprestimoOrdenadaPorPessoa();
}
