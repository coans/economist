function setActiveHeader(url) {
	$(".nav a[href='"+url+"']").parent().attr("class", "active");
}
function makeInputDate() {
	$(".datepicker").datepicker({ dateFormat: 'dd.mm.yy.', firstDay: 1,dayNamesMin: [ "Ne", "Po", "Ut", "Sr", "&#268;e", "Pe", "Su" ], monthNames: [ "Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar" ] })/*.datepicker("setDate", new Date())*/;
}

function setPaginationTableLabels(tableName) {
	$(tableName).DataTable({
		"language": {
            "lengthMenu": "Prika\u017Ei _MENU_ stavki po stranici",
            "zeroRecords": "Ni\u0161ta nije prona\u0111eno",
            "info": "Prikaz stranice _PAGE_ od _PAGES_",
            "infoEmpty": "Nema dostupnih stavki",
            "infoFiltered": "(pretra\u017Eivano ukupno _MAX_ stavki)",
            "search": "Pretra\u017Ei:",
            "paginate": {
                "first":      "Prva",
                "last":       "Poslednja",
                "next":       "Slede\u0107a",
                "previous":   "Prethodna"
            },
        }
	});
}