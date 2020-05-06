<%@page import="model.Notice"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Notice Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="components/jquery-3.2.1.min.js"></script>
<script src="components/notice.js"></script>
</head>
<body>
<div class="container"> 
		<div class="row">  
		
			<div class="col-8">       
				<h1 class="m-3">Notice Management</h1>        
				
				<form id="formNotice" name="formNotice" method="post" action="notice.jsp">  
					Notice Type:  
					<input id="noticeType" name="noticeType" type="text" class="form-control form-control-sm">  
					
					 
					 <br> 
					 Notice Description:  
					 <input id="noticeDesc" name="noticeDesc" type="text" class="form-control form-control-sm">  
					
		
					 
					 <br>  
					 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">  
					 <input type="hidden" id="hidNotIDSave" name="hidNotIDSave" value=""> 
					 
				</form> 
				
				<div id="alertSuccess" class="alert alert-success"></div>  
				<div id="alertError" class="alert alert-danger"></div> 
				
				<br>  
				<div id="divItemsGrid">   
					<%
   						Notice notObj = new Notice();
   									out.print(notObj.readNotice());
   					%>  
					
				</div> 
				  
 			</div>
 		 
 		</div>    
 		
 
	</div> 

</body>

</html>