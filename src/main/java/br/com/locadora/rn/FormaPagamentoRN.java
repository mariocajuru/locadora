package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.FormaPagamentoDAO;
import br.com.locadora.modelo.FormaPagamento;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.util.UtilException;

public class FormaPagamentoRN {
	private FormaPagamentoDAO formaPagamentoDAO;

	public FormaPagamentoRN() {
		this.formaPagamentoDAO = DAOFactory.criarFormaPagamentoDAO();
	}

	public FormaPagamento carregar(Integer codigo) {
		return this.formaPagamentoDAO.carregar(codigo);

	}

	public FormaPagamento buscarFormaPagamento(String formaPagamento) {
		return this.formaPagamentoDAO.buscarFormaPagamento(formaPagamento);
	}

	public void salvar(FormaPagamento formaPagamento) {
		Integer codigo = formaPagamento.getForPagId();
		if (codigo == null || codigo == 0) {
			this.formaPagamentoDAO.salvar(formaPagamento);
		} else {
			this.formaPagamentoDAO.atualizar(formaPagamento);
		}

	}

	public boolean excluir(FormaPagamento formaPagamento) {
		if(this.formaPagamentoDAO.dependecias(formaPagamento)){
			this.formaPagamentoDAO.excluir(formaPagamento);
			return true;
		}else{
			try {
				throw new UtilException("Erro ao excluir. Essa forma de pagamento tem ligações com outros elementos e não pode ser deletado.");
			} catch (UtilException e) {				
				return false;
			}
		}
	}

	public List<FormaPagamento> listar() {
		return this.formaPagamentoDAO.listar();
	}
}
