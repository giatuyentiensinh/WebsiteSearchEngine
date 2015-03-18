<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="UTF-8" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
		<style type="text/css">
			#hg {
				height: 300px;
			}
		</style>
	</head>
	<body>
		<div class="container">
			<!--banner-->
			<div class="row">
				<div class="col-md-12">
					<div class="jumbotron">
						<h1>Search</h1>
						<a href="https://groups.google.com/forum/#!forum/bigdatahpcc" class="">XForce</a>
					</div>
				</div>
			</div>
			<!--/banner-->
			<!--main-->		
			<h1 class="text-center"><span class="text-primary">Elastic</span><span class="text-danger">Search</span></h1>			
			<br />
			
			<form action="response">
				<div class="row" id="hg">
					<div class="col-md-2"></div>
					<div class="col-md-8">
						<input class="form-control" type="text" name="search" placeholder="you know, for search..."/>
					</div>								
					<div class="col-md-2">
						<input class="btn btn-primary" type="submit" value="search"/>
					</div>				
				</div>
			</form>
			
			<!--/main-->			
			<!--footer-->
			<div class="row">
				<div class="col-md-12">
					<p class="text-center">Copyright &copy; by TuyenNG</p>
				</div>
			</div>
			<!--/footer-->
		</div>
	
	</body>

</html>