<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
	<h3 align="center">Kontni plan</h3>
	<p><a href="kontos/new" class="btn btn-info">Novi konto</a></p>
<%-- 	<label>Filter by category</label>
	<select id="category" onchange="filterFoodByCategory()">
	    <c:forEach items="${categories}" var="category">
            <option value="${category.id}" ${category.id == selectedCategoryId ? 'selected' : ''}>${category.name}</option>
	    </c:forEach>
	</select> --%>
	<table class="${tableClass}">
		<thead>
			<tr>
				<!-- <th class="text-center" scope="col">#</th> -->
				<th class="text-center" scope="col">Sifra</th>
				<th class="text-center" scope="col">Naziv</th>
				<!-- <th class="text-center" scope="col">Napomena</th> -->
				<!-- <th class="text-center" scope="col">Akcija</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${kontos}" var="konto" varStatus="loop">	
				<tr>
					<%-- <td align="center">${loop.count}</td> --%>
					<td align="center">${konto.sifra}</td>
					<td align="center">${konto.naziv}</td>
					<%-- <td align="center">${konto.napomena}</td> --%>
					<%-- <td align="center">
						<a href="kontos/edit/${konto.id}" title="Edit"><i class="glyphicon glyphicon-pencil"></i></a>
						&nbsp;
						<a href="#" data-href="kontos/delete/${konto.id}" data-toggle="modal" data-target="#confirmDeleteId" title="Delete"><i class="glyphicon glyphicon-remove"></i></a>
					</td> --%>
				</tr>
			</c:forEach>
		</tbody>
    </table>
</div>
<div class="modal fade" id="confirmDeleteId" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" align="center"><b>Confirm Delete</b></div>
            <div class="modal-body" align="center">Are you sure you want to delete this item?</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a class="btn btn-danger btn-ok">Delete</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		setActiveHeader("#sifarnici");
		setActiveHeader("kontos");
	});
	$('#confirmDeleteId').on('show.bs.modal', function(e) {
	    $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	});
	
	function filterFoodByCategory() {
		var baseUrl = "${baseurl}";
		location.href = baseUrl + "foods?categoryId=" + $("#category").val();
	}
</script>
