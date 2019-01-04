function setActiveHeader(url) {
	$(".nav a[href='"+url+"']").parent().attr("class", "active");
}
function makeInputDate() {
	$(".datepicker").datepicker({ dateFormat: 'dd.mm.yy.', firstDay: 1,dayNamesMin: [ "Ne", "Po", "Ut", "Sr", "&#268;e", "Pe", "Su" ], monthNames: [ "Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar" ] })/*.datepicker("setDate", new Date())*/;
}