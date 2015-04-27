package br.com.locadora.dao;

import java.util.List;




import br.com.locadora.modelo.Imovel;
import br.com.locadora.modelo.Pessoa;
import br.com.locadora.modelo.Proprietario;

public interface ProprietarioDAO {
	public void salvar(Proprietario proprietario);

	public void atualizar(Proprietario proprietario);

	public void excluir(Proprietario proprietario);

	public Proprietario carregar(Integer proId);

	public Proprietario buscarPorProprietario(String proprietario);

	public List<Proprietario> listar();
	
	public List<Proprietario> carregarProprietarios(Imovel imovel);
	
	public Proprietario carregarProprietarioPorImovelPessoa(Pessoa pessoa, Imovel imovel);
}
