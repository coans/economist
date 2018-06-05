<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="section-container">
	<section class="section-home-background">
		<div class="container text-center">
			<h1>
				<span class="h1grey">Welcome</span>
				<span class="h1grey">to</span>
				<br/>
				<span class="h1brown">Olgica-Restaurant</span>
			</h1>	
		</div>
	</section>
	<div class="row">
		<h3 style="width:100%" align="center"><b>List of ordered items</b></h3>
			<div class="col-sm-8 col-sm-offset-2 text-justify">
			<table style="width:100%; margin-left: 20px;" align="center" border="2" class="table table-bordered">
				  <tr>
				    <th>Product name</th>
				    <th>Price</th>
				    <th>Quantity</th>
				  </tr>
				<c:forEach var="item" items="${items}">
					<tr>
						<td>${item.name }</td>
						<td>${item.price }</td>
						<td>${item.quantity }</td>
					</tr>
				</c:forEach>
				<tr>
					<td>&nbsp;</td>
					<td align="center">In total:</td>
					<td class="success">1560 RSD</td>						
				</tr>					
			</table>
			<a href="policies/confirm" class="btn btn-primary btn-small" style="margin-left: 89%;">Confirm order</a>
			</div>
	</div>
</div>