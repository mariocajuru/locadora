package br.com.locadora.dao;

import java.util.List;

import br.com.locadora.modelo.Iptu;
import br.com.locadora.modelo.Locacao;

public interface IptuDAO {
	public void salvar(Iptu iptu);

	public void atualizar(Iptu iptu);

	public void excluir(Iptu iptu);

	public Iptu carregar(Integer iptId);
	
	public List<Iptu> carregarPorLocacao(Locacao locacao);

	public Iptu buscarPorAnoIptu(Integer iptuAno);

	public List<Iptu> listar();
	
	public boolean dependecias(Iptu iptu);
}
