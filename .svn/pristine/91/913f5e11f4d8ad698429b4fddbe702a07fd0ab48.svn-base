<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Pesquisa de Imóveis</title>
    <link href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/v2/pesquisa/css/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/v2/pesquisa/css/pesquisa.css" rel="stylesheet">
</head>
<body>

	<div class="topo"></div>
	
	<div class="container container-pesquisa">
		
		<div class="logo">
			<img src="img/pesquisa-logo.png" />
		</div>
		
		<div id="pesquisa-box">
			<input type="text" class="form-control input-lg" name="pesquisa" id="pesquisa" placeholder="Adicione critérios (Ex: quartos 2)">
		</div>
		
		<div id="itens" class="row"></div>
		
		<div class="acoes">
			<a class="btn btn-lg btn3d btn-success" href="javascript:void(0);" id="btnPesquisar">Pesquisar&nbsp;&nbsp;&nbsp;<i class="fa fa-search"></i></a>
			<a class="btn btn-lg btn3d btn-success" href="javascript:void(0);" id="btnAjuda">Como funciona&nbsp;&nbsp;&nbsp;<i class="fa fa-question"></i></a>
		</div>
		
		<div class="ajuda" style="display: none;">
			<h4>Como funciona?</h4>
			<p>Icon classes cannot be directly combined with other components. They should not be used along with other classes on the same element.</p>
			
			<p><code>Quarto 4</code> Apenas serão exibidos imóveis com 4 (quatro) quartos</p>
			<p><code>Banheiro 1-2</code> Apenas serão exibidos imóveis que tenham 1 (um) ou 2 (dois) banheiros</p>
			<p><code>Valor-Aluguel 1000-2000</code> Apenas serão exibidos imóveis que tenham valor entre R$ 1.000 e R$ 2.000</p>
			
			<br>
			
			<h4>Critérios disponíveis</h4>
			
			<p><code>Limpar</code> Remove todos os critérios.</p>
			<p><code>Remover (Número Inteiro)</code> Remove critério na posição escolhida. Exemplo: Remover 1</p>
			<p><code>Alugar</code> Mostrar apenas imóveis para alugar</p>
			<p><code>Valor-Aluguel</code> Exemplo: Valor aluguel 700</p>
			<p><code>Comprar</code> Mostrar apenas imóveis a venda</p>
			<!-- 
			<p><code>Valor-Compra</code> Exemplo: Valor de compra 80000-120000</p>
			<p><code>Cep (Número Inteiro)</code> Exemplo: 35505000</p>
			<p><code>Rua (Texto)</code> Exemplo: Rua Goais</p>
			<p><code>Bairro (Texto)</code> Exemplo: Bairro Centro</p> 
			-->
		</div>
	</div>
	
	<!-- Formulario oculto da pesquisa -->
	<form id="formPesquisa" action="resultados.jsf" method="post">
		<input type="hidden" id="parameter1" name="parameter1" />
	</form>
	
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.formalize.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/v2/pesquisa/js/jquery.autocomplete.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/v2/pesquisa/js/pesquisa.js" charset="UTF-8"></script>
    
</body>
</html>