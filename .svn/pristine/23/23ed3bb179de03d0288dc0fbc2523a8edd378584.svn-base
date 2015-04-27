package br.com.locadora.rn;

import java.util.List;

import br.com.locadora.dao.PesquisaDAO;
import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Caracteristicas;
import br.com.locadora.modelo.Cidade;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Regiao;
import br.com.locadora.modelo.Situacaoimovel;
import br.com.locadora.modelo.Tipoimovel;
import br.com.locadora.util.DAOFactory;
import br.com.locadora.web.PesquisaImovelBean.CaracteriticasTempPesquisa;

public class PesquisaRN {
	private PesquisaDAO pesquisaDAO;

	public PesquisaRN() {
		this.pesquisaDAO = DAOFactory.criarPesquisaDAO();
	}

	public List<Imovel> locacao(List<Tipoimovel> listaTipoImovel,
			List<Bairro> listaBairro, List<Cidade> listaCidade,
			List<Caracteristicas> listaDetalhesImovel,String valorMinimo,String valorMaximo,Integer qtdQuartos, List<Regiao> listaRegioes, 
			int quartoDe, int quartoAte) {
		
		return this.pesquisaDAO.locacao(listaTipoImovel, listaBairro,
				listaCidade, listaDetalhesImovel,valorMinimo, valorMaximo, qtdQuartos,listaRegioes,  quartoDe,  quartoAte);
	}
	public boolean encontrarEndereco(Endereco endereco){
		return this.pesquisaDAO.encontrarEndereco(endereco);
	}
	
	public boolean existeCPF(String cpf){
		return this.pesquisaDAO.existeCPF(cpf);
	}
	public List<Imovel> pesquisarImoveis(Imovel imovel, List<CaracteriticasTempPesquisa> listaCaracteristiscas,
			List<Tipoimovel> listaTipos,
			List<Situacaoimovel> listaSituacoes,
			List<Bairro> listaBairros) {
		
		return this.pesquisaDAO.pesquisarImoveis(imovel, listaCaracteristiscas, listaTipos, listaSituacoes, listaBairros);
	}
}
