package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Bairro;
import br.com.locadora.modelo.Caracteristicas;
import br.com.locadora.modelo.Cidade;
import br.com.locadora.modelo.Endereco;
import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Regiao;
import br.com.locadora.modelo.Situacaoimovel;
import br.com.locadora.modelo.Tipoimovel;
import br.com.locadora.web.PesquisaImovelBean.CaracteriticasTempPesquisa;
public interface PesquisaDAO {
	public List<Imovel> locacao(List<Tipoimovel> listaTipoImovel, List<Bairro> listaBairro, List<Cidade> listaCidade, List<Caracteristicas> listaDetalhesImovel,String valorMinimo,String valorMaximo, Integer qtdQuartos, List<Regiao> listaRegioes, int quartoDe, int quartoAte);
	public boolean encontrarEndereco(Endereco endereco);
	public boolean existeCPF(String cpf);
	
public List<Imovel> pesquisarImoveis(Imovel imovel, List<CaracteriticasTempPesquisa> listaCaracteristiscas,
		List<Tipoimovel> listaTipos,
		List<Situacaoimovel> listaSituacoes,
		List<Bairro> listaBairros);
}
