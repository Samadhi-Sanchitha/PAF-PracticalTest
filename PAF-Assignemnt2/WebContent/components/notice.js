$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validateNoticeForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid------------------------  
	var t = ($("#hidNotIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "NoticeApi",
		type : t,
		data : $("#formNotice").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onNoticeSaveComplete(response.responseText, status);
		}
	});
}); 

function onNoticeSaveComplete(response, status){
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	$("#hidNotIDSave").val("");
	$("#formNotice")[0].reset();
}

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
		{     
	$("#hidNotIDSave").val($(this).closest("tr").find('#hidNotIDUpdate').val());     
	$("#noticeType").val($(this).closest("tr").find('td:eq(0)').text());    
	$("#noticeDesc").val($(this).closest("tr").find('td:eq(1)').text()); 
    
	
});


//Remove Operation
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "NoticeApi",
		type : "DELETE",
		data : "noticeID=" + $(this).data("noticeid"),
		dataType : "text",
		complete : function(response, status)
		{
			onNoticeDeletedComplete(response.responseText, status);
		}
	});
});

function onNoticeDeletedComplete(response, status)
{
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}

//CLIENTMODEL
function validateNoticeForm() {  
	//NOTICE TYPE  
	if ($("#noticeType").val().trim() == "")  {   
		return "Insert Notice Type.";  
		
	} 
	

	// NOTICE DESCRIPTION  
	if ($("#noticeDesc").val().trim() == "")  {   
		return "Insert noticeDesc.";  
		
	} 
	
	

	 return true; 
	 
}
