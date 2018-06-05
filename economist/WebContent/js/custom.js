function setActiveHeader(url) {
	$(".nav a[href='"+url+"']").parent().attr("class", "active");
}