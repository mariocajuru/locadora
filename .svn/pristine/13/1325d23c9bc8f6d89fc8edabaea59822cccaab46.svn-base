package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.SituacaoLocacaoPermissoesDAO;
import br.com.locadora.modelo.PermissoesLocacao;
import br.com.locadora.modelo.SituacaoLocacao;
import br.com.locadora.modelo.SituacaoLocacaoPermissoes;
import br.com.locadora.util.DAOFactory;

public class SituacaoLocacaoPermissoesRN {
	
	private SituacaoLocacaoPermissoesDAO situacaoLocacaoPermissoesDAO;
	
	public SituacaoLocacaoPermissoesRN() {
		this.situacaoLocacaoPermissoesDAO=DAOFactory.criarSituacaoLocacaoPermissoesDAO();
	}
	public SituacaoLocacaoPermissoes carregar(Integer codigo) {
		return this.situacaoLocacaoPermissoesDAO.carregar(codigo);

	}

	
	public void salvar(SituacaoLocacaoPermissoes situacaoLocacaoPermissoes) {
		/*Integer codigo = complemento.getId().getDetId();
		if (codigo == null || codigo == 0) {*/
			this.situacaoLocacaoPermissoesDAO.salvar(situacaoLocacaoPermissoes);
		/*} else {
			this.caracteristicasDAO.atualizar(complemento);
		}*/

	}

	public void excluir(SituacaoLocacaoPermissoes  situacaoLocacaoPermissoes) {
		this.situacaoLocacaoPermissoesDAO.excluir(situacaoLocacaoPermissoes);
	}

	public List<SituacaoLocacaoPermissoes> listar() {
		return this.situacaoLocacaoPermissoesDAO.listar();
	}
	

	public List<PermissoesLocacao> listarPermissoesLocacaoPorSituacao(SituacaoLocacao situacaoLocacao){
		return this.situacaoLocacaoPermissoesDAO.listarPermissoesLocacaoPorSituacao(situacaoLocacao);
	}
	
	public void excluirTudo() {
		this.situacaoLocacaoPermissoesDAO.excluirTudo();
	}
}
