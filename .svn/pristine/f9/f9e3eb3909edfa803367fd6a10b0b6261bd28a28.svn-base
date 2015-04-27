package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.InformacaoAdicional;
import br.com.locadora.modelo.Locacao;

public interface InformacaoAdicionalDAO {
	public void salvar(InformacaoAdicional informacaoAdicional);

	public void atualizar(InformacaoAdicional informacaoAdicional);

	public void excluir(InformacaoAdicional informacaoAdicional);

	public InformacaoAdicional carregar(Integer infAdiId);

	public InformacaoAdicional buscarPorInformacaoAdicional(String informacaoAdicional);

	public List<InformacaoAdicional> listar();
	
	public List<InformacaoAdicional> carregarPorLocacao(Locacao locacao);
}
