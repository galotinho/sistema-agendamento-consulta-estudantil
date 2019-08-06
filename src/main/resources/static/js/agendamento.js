/**
 * busca as especialidades com auto-complete
 */
$("#especialidade").autocomplete({
    source: function (request, response) {
        $.ajax({
            method: "GET",
            url: "/especialidades/titulo",
            data: {
            	termo: request.term
			},
            success: function (data) {
            	response(data);            	
            }
		});    
    },
    /**
     * após a especialidade ser selecionada busca
     * os especialistas referentes e os adiciona na página com
     * radio
     */
    select: function (event, ui){
        $('div').remove(".custom-radio");
        $("#horarios").empty();
    	$("#datapicker-custom").val("");
    	var titulo = ui.item.label;
    	if ( titulo != '' ) {			
    		$.get( "/profissionais/especialidade/titulo/" + titulo , function( result ) {
    				
    			var ultimo = result.length - 1; 
    			
    			$.each(result, function (k, v) {
    				
    				if ( k == ultimo ) {
    	    			$("#profissionais").append( 
    	    				 '<div class="custom-control custom-radio">'	
    	    				+  '<input class="custom-control-input" type="radio" id="customRadio'+ k +'" name="profissional.id" value="'+ v.id +'" required>'
    						+  '<label class="custom-control-label" for="customRadio'+ k +'">'+ v.nome +'</label>'
    						+  '<div class="invalid-feedback">Profissional é obrigatório</div>'
    						+'</div>'
    	    			);
    				} else {
    	    			$("#profissionais").append( 
    	    				 '<div class="custom-control custom-radio">'	
    	    				+  '<input class="custom-control-input" type="radio" id="customRadio'+ k +'" name="profissional.id" value="'+ v.id +'" required>'
    						+  '<label class="custom-control-label" for="customRadio'+ k +'">'+ v.nome +'</label>'
    						+'</div>'
    	        		);	            				
    				}
    		    });
    		});
    	}
    }
});

/** 
 * busca os horários livres para consulta conforme a data e o profissional.
 * os horários são adicionados a página como um select:option.	
*/
$('#datapicker-custom').on('change', function () {
	$("#horarios").empty();
	var data = $(this).val().split('/');
	data = data[2]+'-'+data[1]+'-'+data[0];
	var profissional = $('input[name="profissional.id"]:checked').val();
   	$.get('/profissionais/horarios/hora/disponivel/'+data+'/'+profissional , function( result ) {
    		$.each(result, function (k, v) {
    			$("#horarios").append( 
    				'<option class="op" value="'+ v.id +'">'+ v.horaMinuto + '</option>'
    			);	            			
    	    });
    	});
 });

/**
 * Datatable histórico de consultas
*/
$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-paciente-historico').DataTable({
    	language: {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Portuguese-Brasil.json"
        },
        searching : false,
        lengthMenu : [ 5, 10 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [2, 'desc'],
        ajax : {
            url : '/agendamentos/datatables/server/historico',
            data : 'data'
        },
        columns : [
            {data : 'id'},
            {data : 'paciente.nome'},
            {data: 'dataConsulta', render:
                function( dataConsulta ) {
                    return moment(dataConsulta).format('LLL');
                }
            },
            {data : 'profissional.nome'},
            {data : 'especialidade.titulo'},
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/agendamentos/editar/consulta/'
                            + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/agendamentos/excluir/consulta/'
                    + id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }
            }
        ]
    });
});