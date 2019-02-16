<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- <div id="home"> -->
	<div class="section-container">
		<div id="myCarousel" class="carousel slide" data-ride="carousel"> <!-- data-interval="false" -->
			<!-- <h3 align="center"><i>AAA1A</i></h3> -->
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner" align="center">
					<div class="item active">
						<div class="row">
							<!-- <div class="col-xs-2"> -->
								<img class="picture-size" src="images/sl2.jpg" alt="Slide 1">
								<%-- <label>${bestseller.food.name}</label> --%>
							<!-- </div> -->
						</div>
					</div>
					<div class="item">
						<div class="row">
							<!-- <div class="col-xs-2"> -->
								<img class="picture-size" src="images/sl3.jpg" alt="Slide 2">
								<%-- <label>${bestseller.food.name}</label> --%>
							<!-- </div> -->
						</div>
					</div>
					<div class="item">
						<div class="row">
							<!-- <div class="col-xs-2"> -->
								<img class="picture-size" src="images/sl1.jpg" alt="Slide 3">
								<%-- <label>${bestseller.food.name}</label> --%>
							<!-- </div> -->
						</div>
					</div>
			</div>

			<!-- Left and right controls -->
			<a class="left carousel-control" href="#myCarousel" data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left"></span>
				<span class="sr-only"><%-- <spring:message code="carousel.previous"/> --%>Prethodna</span>
			</a>
			<a class="right carousel-control" href="#myCarousel" data-slide="next">
				<span class="glyphicon glyphicon-chevron-right"></span>
				<span class="sr-only"><%-- <spring:message code="carousel.next"/> --%>Sledeca</span>
			</a>
		</div>	
<!-- 		<section id="about" class="section-white">
			<div class="container text-center">
				<div class="row">
	                <div class="col-md-6 col-md-offset-3">
						<p>This is a public wall. You can share your content with others.</p>
						<br/>
						<a id="get-started" href="my/posts" class="btn btn-primary btn-lg">Get started</a>
	                </div>
	            </div>
			</div>
		</section> -->
	</div>
<!-- </div> -->

<script type="text/javascript">
	// default navigation	
	if(window.location.href == '<c:out value="${baseurl}"/>#about') {
		setActiveHeader("#about");
	} else {
		setActiveHeader("#home");
	}
	// animate navigation
	$('.nav').onePageNav({
	    currentClass: 'active',
	    changeHash: true,
	    easing: 'swing'
	});
	
	$(".btn-down a").click(function(e) {
		e.preventDefault();
		$('html, body').animate({
            scrollTop: $("#info").offset().top
        }, 750, function() { $(".btn-down").remove(); });
	});

</script>