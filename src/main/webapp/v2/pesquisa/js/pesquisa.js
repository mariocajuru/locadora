/**
 * Created by: Marcos Vinícius (squimer@gmail.com)
 */


/** 
 * ----------------------------------------------------------------------------------------------------------------------------------------
 *
 * PesquisaClass
 * 
 * ----------------------------------------------------------------------------------------------------------------------------------------
 */

function PesquisaClass() {

	/**
	 * Variável pública que armazena todos critérios disponíveis,
	 * tanto fixos como carregados do banco de dados.
	 * 
	 */
	this.criteiros = [
	     {  value: 'Valor-Aluguel', tipo: 'valor'          , id: 0},
	     /**{  value: 'Valor-Compra ', tipo: 'valor'          , id: 0},*/
         {  value: 'Alugar'       , tipo: 'objetivo'       , id: 0},
         {  value: 'Comprar'      , tipo: 'objetivo'       , id: 0},
         {  value: 'CEP'          , tipo: 'endereco'       , id: 0},
         /**
         {  value: 'Rua'          , tipo: 'endereco'       , id: 0},
         {  value: 'Avenida'      , tipo: 'endereco'       , id: 0},
         {  value: 'Bairro'       , tipo: 'endereco'       , id: 0},
         {  value: 'Zona'         , tipo: 'endereco'       , id: 0}
         */
     ];
	

	/**
	 * --------------------------------------------------------------------------------------------
	 * Variável privada que armazena os critérios selecionados.
	 * --------------------------------------------------------------------------------------------
	 * 
	 */
	var criteriosSelecionados = [];
		
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * Insere critérios, valida e executa comandos dependedo da entrada do usuário.
	 * Para o método funcinar corretamente é necesário ter um input do tipo texto com a id 'pesquisa'.
	 * --------------------------------------------------------------------------------------------
	 * 
	 */
	this.selecionarCriterio = function () {
		var valorPesquisado = $("#pesquisa").val();
		
		if (valorPesquisado.trim().length == 0)
			return;
		
		/** Função Limpar */
		if (valorPesquisado.toUpperCase() == "LIMPAR") {
			this.removerTodosCriterioSelecionados();
			return;
		} 

		/** Função Remover */
		else if (valorPesquisado.toUpperCase().indexOf("REMOVER") > -1) {
			var textoSplit = valorPesquisado.split(" ", 2);
			
			if (textoSplit.length < 1)
				return;
			
			var cod = parseInt(textoSplit[1]);
			
			if (isNaN(cod))
				return;
			
			cod--;
			
			if (cod < 0 || cod >= criteriosSelecionados.length)
				return;
			
			criteriosSelecionados[cod] = null;

			this.atualizarCriteriosSelecionados();
		} 
		
		/** Função Selecionar critério */
		else {
			
			for (var i = 0; i < this.criteiros.length; i++) {
				var item = [];

				item['id']    = this.criteiros[i].id;
				item['value'] = this.criteiros[i].value;
				item['tipo']  = this.criteiros[i].tipo;
				item['texto'] = valorPesquisado.toUpperCase();
				
				var valorTratado   = "";
				var valorParametro = "";
				
				/** Trata a entrada do usuário, entradas quando existe espaço entre palavras */
				if (item['tipo'] == "cidade" || item['tipo'] == "bairro" || item['tipo'] == "tipo_imovel") {
					valorTratado = valorPesquisado;
				} else {
					valorTratado = valorPesquisado.split(" ")[0];
				}
				
				
				/** Adiciona aos critérios selecionados */
				if (valorTratado.toLowerCase().indexOf(item.value.toLowerCase()) > -1) {
					criteriosSelecionados.push(item);
					break;
				}
			}
			
			this.atualizarCriteriosSelecionados();
		}
		
	};
	
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * Remove critérios com base no código da posição que ocupam na variável 'criteriosSelecionados'. 
	 * --------------------------------------------------------------------------------------------
	 * 
	 */
	this.removerCriterio = function (cod) {
		var confirmacaoDialog = confirm("Deseja remover o critério?");
		
		if (confirmacaoDialog == true) {
			criteriosSelecionados[cod] = null;
		}
		
		this.atualizarCriteriosSelecionados();
	};

	
	/**
	 * --------------------------------------------------------------------------------------------
	 * Este método necessita de uma div com a id 'itens', para visualização do usuário.
	 * --------------------------------------------------------------------------------------------
	 * 
	 */
	this.atualizarCriteriosSelecionados = function () {
		$("#itens").empty();
		
		for (var i = 0; i < criteriosSelecionados.length; i++) {
			if (criteriosSelecionados[i] == null)
				continue;
						
			var botao = "";
			botao += "<a href='#' onclick='javascript:pesquisa.removerCriterio(" + i + ")' class='btn btn-default btn-sm item' role='button'>";
			
			if (criteriosSelecionados[i].tipo == "tipo_imovel")
				botao += "Tipo Imóvel: ";
			
			if (criteriosSelecionados[i].tipo == "cidade")
				botao += "Cidade: ";

			if (criteriosSelecionados[i].tipo == "caracteristica")
				botao += "Característica: ";
			
			botao += criteriosSelecionados[i].texto;
			botao += "</a>";
		
			$("#itens").append(botao);
		}
		
		this.gerarJson();
	};
	

	/**
	 * --------------------------------------------------------------------------------------------
	 * Converte os criterios selecionados para json e os salva em um formulario oculto na página.
	 * --------------------------------------------------------------------------------------------
	 * 
	 */
	this.gerarJson = function () {
		/** Converte os criterios selecionados para json e os salva em um formulario oculto na página */
		var jsonArray = {};

		var tipo_imoveis		= [];
		var cidades         = [];
		var caracteristicas = [];
		var objetivos       = [];
		var valores         = [];

		for (var i = 0; i < criteriosSelecionados.length; i++) {
			var criterio = { 
				"id"    : criteriosSelecionados[i].id,
				"tipo"  : criteriosSelecionados[i].tipo,
				"texto" : criteriosSelecionados[i].texto,
				"value" : criteriosSelecionados[i].value
			};
			
			if (criteriosSelecionados[i].tipo == "caracteristica")
				caracteristicas.push(criterio);
			
			if (criteriosSelecionados[i].tipo == "tipo_imovel")
				tipo_imoveis.push(criterio);
			
			if (criteriosSelecionados[i].tipo == "cidade")
				cidades.push(criterio);
			
			if (criteriosSelecionados[i].tipo == "objetivo")
				objetivos.push(criterio);

			if (criteriosSelecionados[i].tipo == "valor")
				valores.push(criterio);
		}
		jsonArray.tipo_imoveis	  = tipo_imoveis;
		jsonArray.caracteristicas = caracteristicas;
		jsonArray.cidades         = cidades;
		jsonArray.objetivos       = objetivos;
		jsonArray.valores         = valores;
		
		$("#parameter1").val(JSON.stringify(jsonArray) + "");		
	};
	

	/**
	 * --------------------------------------------------------------------------------------------
	 * Este método necessita de uma div com a id 'itens'.
	 * --------------------------------------------------------------------------------------------
	 * 
	 */
	this.removerTodosCriterioSelecionados = function () {
		$("#itens").empty();
		
		for (var i = 0; i < criteriosSelecionados.length; i++) {
			criteriosSelecionados[i] = null;
		}
	};
	
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * Este método necessita de uma div com a id 'itens'.
	 * --------------------------------------------------------------------------------------------
	 * 
	 */
	this.adicionarCriterio = function (value, tipo, id) {
		var util = new UtilClass();
		
		var item = [];
		item['value'] = util.apenasLetras(value);
		item['tipo']  = tipo;
		item['id']    = id;
		
		this.criteiros.push(item);
	};
	
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * Este método executa a pesquisa :D.
	 * --------------------------------------------------------------------------------------------
	 * 
	 */
	this.pesquisar = function () {
		$('#formPesquisa').submit();
	};
	
	
}


/** 
 * ----------------------------------------------------------------------------------------------------------------------------------------
 * InformacoesClass
 * ----------------------------------------------------------------------------------------------------------------------------------------
 * 
 */
function InformacoesClass() {
		
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * ...
	 * --------------------------------------------------------------------------------------------
	 * 
	 */
	this.carregar = function () {
		
		/** Tipo Imovel */
		$.getJSON("./json/json-tipo-imovel.jsf", function( data ) {
			var items = [];
			
			$.each( data, function(key, val) {
			    //console.log(val.value);
			    
			    pesquisa.adicionarCriterio(val.value, "tipo_imovel", val.id);
			});
		});
		
		/** Cidades */
		$.getJSON("./json/json-cidades.jsf", function( data ) {
			var items = [];
			
			$.each( data, function(key, val) {
			    //console.log(val.value);
			    
			    pesquisa.adicionarCriterio(val.value, "cidade", val.id);
			});
		});
		
		
		/** Caracteristicas */
		$.getJSON("./json/json-caracteristicas.jsf", function( data ) {
			var items = [];
			
			$.each( data, function(key, val) {
			    //console.log(val.value);
			    
			    pesquisa.adicionarCriterio(val.value, "caracteristica", val.id);
			});
		});
		
		
	};
	
}


/** 
 * ----------------------------------------------------------------------------------------------------------------------------------------
 *
 * UtilClass
 * 
 * ----------------------------------------------------------------------------------------------------------------------------------------
 */
function UtilClass() {
	
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * Remove acentos de string
	 * --------------------------------------------------------------------------------------------
	 * 
	 */
	this.apenasLetras = function (strAccents) {
		var strAccents = strAccents.split('');
		var strAccentsOut = new Array();
		var strAccentsLen = strAccents.length;
		var accents = 'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž';
		var accentsOut = "AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz";
		for (var y = 0; y < strAccentsLen; y++) {
			if (accents.indexOf(strAccents[y]) != -1) {
				strAccentsOut[y] = accentsOut.substr(accents.indexOf(strAccents[y]), 1);
			} else
				strAccentsOut[y] = strAccents[y];
		}
		strAccentsOut = strAccentsOut.join('');
		return strAccentsOut;
	}
	
	
}



/** 
 * ----------------------------------------------------------------------------------------------------------------------------------------
 *
 * Funções da página 
 * 
 * ----------------------------------------------------------------------------------------------------------------------------------------
 */


var pesquisa = new PesquisaClass();
var info     = new InformacoesClass();
info.carregar();


$('#pesquisa').autocomplete({
	lookup: pesquisa.criteiros
});


/** 
 * ----------------------------------------------------------------------------------------------------------------------------------------
 *
 * Listener's
 * 
 * ----------------------------------------------------------------------------------------------------------------------------------------
 */


$("#pesquisa").keypress(function(event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    
    if(keycode == '13') {
    	pesquisa.selecionarCriterio();
        $(this).val("");
    }
    
});


$("#btnPesquisar").click(function() {
	pesquisa.pesquisar();
});


$("#btnAjuda").click(function() {
	$(".ajuda").slideToggle();
});

