// processa o auto-complete
$(function() {
	// remove o espaco depois da virgula
	function split(val) {
		return val.split(/,\s*/);
	}

	function extractLast(term) {
		return split(term).pop();
	}
	
	function addEspecializacao(titulo) {
		$('#listaEspecializacoes')
			.append('<input type="hidden" value="'+ titulo +'" name="especialidades">');
	}

    function exibeMessagem(texto) {
        $('.add-toast').append(""
          .concat('<div class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="2800">',
                  '<div class="toast-header">',
                  '<strong class="mr-auto">Atenção</strong>',
                  '<button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">',
                  '  <span aria-hidden="true">&times;</span>',
                  '</button>',
              '</div>',
              '<div class="toast-body">', texto, '</div>',
          '</div>')
        );
        $('.toast').toast('show');
        $('.toast').on('hidden.bs.toast', function (e) {
            $(e.currentTarget).remove();
        });
    }

	$("#autocomplete-especialidades")
		.on("keydown",	function(event) {
			if (event.keyCode === $.ui.keyCode.TAB
					&& $(this).autocomplete("instance").menu.active) {
				event.preventDefault();
			}
		})
		.autocomplete({
			source : function(request, response) {
				$.getJSON("/especialidades/titulo", {
					termo : extractLast(request.term)
				}, response);
			},
			search : function() {
				// custom minLength
				var term = extractLast(this.value);
				if (term.length < 1) {
					return false;
				}
			},
			focus : function() {
				// prevent value inserted on focus
				return false;
			},
			select : function(event, ui) {
				var terms = split(this.value);
				console.log('1. this.value: ' + this.value)
				console.log('2. terms: ' + terms)
				console.log('3. ui.item.value: ' + ui.item.value)
				// remove the current input
				terms.pop();
				console.log('4. terms: ' + terms)
				// testa se valor já foi inserido no array
				var exists = terms.includes(ui.item.value);				
				if (exists === false) {				
					// add the selected item
					terms.push(ui.item.value);
					console.log('5. terms: ' + terms)
					terms.push("");
					console.log('6. terms: ' + terms)
					this.value = terms.join(", ");
					console.log('7. this.value: ' + this.value)
					console.log('8. ui.item.value: ' + ui.item.value)
					// adiciona especializacao na pagina para envio ao controller
					addEspecializacao(ui.item.value);
				} else {
					exibeMessagem('A Especialização <b>'+ ui.item.value +'</b> já foi selecionada.');
				}
				return false;
			}
		});
});


/**
 * Datatable histórico de consultas
*/
$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-horarios').DataTable({
    	language: {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Portuguese-Brasil.json"
        },
        searching : false,
        lengthMenu : [ 5, 10 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [1, 'desc'],
        ajax : {
            url : '/profissionais/datatables/server/disponibilidade',
            data : 'data'
        },
        columns : [
            {data : 'id'},
            {data: 'dataDisponivel', render:
                function( dataDisponivel ) {
                    return moment(dataDisponivel).format('LL');
                }
            },
            {data : 'horarios',
            render : function(horarios) {
				var aux = new Array();
				$.each(horarios, function( index, value ){
					  var hora = " ";
					  hora = hora.concat(value.horaMinuto.substring(0,5));
					 					  
					  aux.push(hora);
				});
				return aux;
			}, orderable : false,
            },
            {orderable : false,	data : 'id', "render" : function(id) {

                    return '<a class="btn btn-success btn-sm btn-block" href="/profissionais/editar/disponibilidade/'
                            + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/profissionais/excluir/disponibilidade/'
                    + id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }
            }
        ]
    });
});
