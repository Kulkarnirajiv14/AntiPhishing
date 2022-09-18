<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">
<head>
<jsp:include page="../tiles/header.jsp"></jsp:include>
</head>
<body class="h-100">
	
	<div class="flex justify-between">
		 <a class="dropdown-item"
			href="<%=request.getContextPath()%>/connector/ajax.jsp?methodId=logout"> Logout</a>
	</div>

	<div class="container h-100">
		<div class="row h-100">
			<div class="mt-4">
				<h1 class="text-center text-white">Phishing URL Detection</h1>
				
				<h6 class="text-center"><a href="<%=request.getContextPath()%>/pages/history.jsp">View History</a></h6>

				<div class="offset-md-2	col-md-8 container d-flex h-100">
					<div class="row justify-content-center align-self-center w-100"
						style="margin-top: -180px;">
						<div class="card">
							<!-- <div class="card-header bg-dark text-white">Phishing URL -->
							<!-- Detection</div> -->
							<div class="card-body py-5">
								<form name="form_url" id="form_url"
									action="javascript:fnSubmit();">


									<div class="mb-4">
										<label for="exampleInputURL1" class="form-label">URL
											address</label> <input type="url" class="form-control" id="url"
											name="url" aria-describedby="URL" pattern="https?://.+"
											required placeholder="https://example.com" /> <span
											class="badge bg-info text-white" for="url">Enter an
											https:// URL </span>
									</div>
									<div class="mb-3">
										<h4 class="text-info text-center">
											<span class="text-capitalize px-4 py-1" id="result"> <span>
										</h4>
									</div>
									<div id="loaderss" class="row mx-auto  justify-content-center">
										<div class="spinner-grow col-2 ml-1 text-primary"
											role="status">
											<span class="visually-hidden">Loading...</span>
										</div>
										<div class="spinner-grow col-2 ml-1 text-secondary"
											role="status">
											<span class="visually-hidden">Loading...</span>
										</div>
										<div class="spinner-grow col-2 ml-1 text-success"
											role="status">
											<span class="visually-hidden">Loading...</span>
										</div>
										<div class="spinner-grow ml-1 text-danger" role="status">
											<span class="visually-hidden">Loading...</span>
										</div>
										<div class="spinner-grow ml-1 text-warning" role="status">
											<span class="visually-hidden">Loading...</span>
										</div>
										<div class="spinner-grow ml-1 text-info" role="status">
											<span class="visually-hidden">Loading...</span>
										</div>
										<div class="spinner-grow ml-1 text-dark" role="status">
											<span class="visually-hidden">Loading...</span>
										</div>
									</div>
									<div id="btns" class="gap-2 col-6 mx-auto">
										<button type="submit" class="btn btn-outline-dark w-100 mt-4">
											Check Phishing</button>
									</div>

								</form>
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
	
	var url = window.location.href;
	console.log("1: " + url);
	var url = new URL(url);
	console.log("2: " + url);
	var url = url.searchParams.get("url");
	console.log("3: " + url);
	$("#url").val(url);
	
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

	function fnResultShow(data)
	{
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
</script>
</html>