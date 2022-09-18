<%@page import="java.net.URLEncoder"%>
<%@page import="com.helper.UserModel"%>
<%@page import="com.helper.HistoryModel"%>
<%@page import="java.util.List"%>
<%@page import="com.helper.ConnectionManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">
<head>
<jsp:include page="../tiles/header.jsp"></jsp:include>
</head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/theme/font-awesome-4.7.0/css/font-awesome.min.css">

<body class="h-100">

<%
	UserModel um = (UserModel)session.getAttribute("USER_MODEL");
	List<HistoryModel> list = ConnectionManager.getHistory(um.getUid());
%>

	<div class="container h-100">
		<div class="row h-100">
			<div class="mt-4">
				<h1 class="text-center text-white">Phishing URL Detection</h1>

				<div class="offset-md-2	col-md-8 container d-flex h-100">
					<div class="row justify-content-center align-self-center w-100"
						style="margin-top: -180px;">
						<div class="card">
							<div class="card-body py-5">

								<h2 class="text-center">History</h2>

								<div class="mb-4">
								<%if(list.size()>0){ %>
									<table class="table">
										<thead class="thead-dark">
											<tr>
												<th scope="col">#</th>
												<th scope="col">URL</th>
												<th scope="col">Check Phishing</th>
												<th scope="col">Delete</th>
											</tr>
										</thead>
										<tbody>
											<%for(int i=0; i<list.size(); i++){ 
												HistoryModel hm = (HistoryModel) list.get(i);
												String encoded = URLEncoder.encode(hm.getUrl().toString().trim());
												String rowid = hm.getId();
												%>
												<tr>
													<th scope="row"><%=hm.getId() %></th>
													<td><%=hm.getUrl().toString() %></td>
													<td><a href="<%=request.getContextPath()%>/pages/index.jsp?url=<%=encoded%>">Check Again</a></td>
													<td class="text-center text-danger"><i class="fa fa-trash" aria-hidden="true" style="cursor:pointer" onclick="fnDelete(<%=hm.getId() %>)"></i></td> 
												</tr>
											<%} %>
										</tbody>
									</table>
									<%}else{ %>
										<h3 class="text-center text-info">No History Yet!</h3>
									<%} %>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<jsp:include page="../tiles/footer.jsp"></jsp:include>



</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#loaderss").hide();
});

function fnSubmit(){	
	fnResultShow("Detecting...");
	$("#btns").hide();
	$("#loaderss").show();
	var str = $("#form_url").serialize();

	$.post("<%=request.getContextPath()%>/connector/ajax.jsp?methodId=checkURL",
						str, function(data) {

							data = $.trim(data);

							fnResultShow(data);

							$("#result").text(data);

							$("#btns").show();
							$("#loaderss").hide();
						});

	}

	function fnResultShow(data) {
		if (data.toLowerCase() == 'phishing') {
			$("#result").css('border', '2px solid red');
		} else if (data.toLowerCase() == 'normal') {
			$("#result").css('border', '2px solid green');
		} else if (data == 'Detecting...') {
			$("#result").text("Detecting...");
			$("#result").css('border', 'none');
		} else {
			$("#result").css('border', '2px solid blue');
		}
	}
	
	function fnDelete(rowid){	
	
		var str ="id="+rowid;

		$.post("<%=request.getContextPath()%>/tiles/ajax.jsp?methodId=deletebyid",
							str, function(data) {

								data = $.trim(data);

							if(data==1){
								alert("Data Deleted Successfully....");
								window.location.href="<%=request.getContextPath()%>/pages/history.jsp";
							}
							});
		 
	}
</script>
</html>