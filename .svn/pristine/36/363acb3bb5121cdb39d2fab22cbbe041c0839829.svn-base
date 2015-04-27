package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.MensagemFuncionarioDAO;
import br.com.locadora.modelo.Funcionario;
import br.com.locadora.modelo.MensagemFuncionario;
import br.com.locadora.util.DAOFactory;

public class MensagemFuncionarioRN {
	private MensagemFuncionarioDAO mensagemFuncionarioDAO;
	public MensagemFuncionarioRN() {
		this.mensagemFuncionarioDAO= DAOFactory.criarMensagemFuncionarioDAO();
	}
	
	public void salvar(MensagemFuncionario mensagemFuncionario){
		Integer codigo = mensagemFuncionario.getMenFunId();
		if (codigo == null || codigo == 0) {
			this.mensagemFuncionarioDAO.salvar(mensagemFuncionario);
		} else {
			this.mensagemFuncionarioDAO.atualizar(mensagemFuncionario);
		}
	}

	public void excluir(MensagemFuncionario mensagemFuncionario){
		this.mensagemFuncionarioDAO.excluir(mensagemFuncionario);
	}

	public MensagemFuncionario carregar(Integer menFunId){
		return this.mensagemFuncionarioDAO.carregar(menFunId);
	}

	public List<MensagemFuncionario> buscarPorFuncionarioVisualizada(Funcionario funcionario){
		return this.mensagemFuncionarioDAO.buscarPorFuncionarioVisualizada(funcionario);
	}

	public List<MensagemFuncionario> listar(){
		return this.mensagemFuncionarioDAO.listar();
	}

}
