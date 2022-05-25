<%@page import="java.util.List"%>
<%@page import="com.managerauthrizationfunction.model.ManagerAuthrizationFunctionService"%>
<%@page import="com.managerauthrizationfunction.model.ManagerAuthrizationFunctionVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@include file="/backend/share.jsp"%>
 
 <%
// 取得所有資料
ManagerAuthrizationFunctionService managerAuthrizationFunctionServiceService = new ManagerAuthrizationFunctionService();
List<ManagerAuthrizationFunctionVO> list = managerAuthrizationFunctionServiceService.getAll();
pageContext.setAttribute("list", list);


%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改管理員權限</title>
</head>
<body>
<section id="main-content">
	<section class="wrapper">


	<div id="bid-content">

		<table id="table-1">
			<tr>
				<td>
			 		<h3>修改管理員權限</h3>
				</td>
			</tr>
		</table>

		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		
<!-- 		主要修改資訊區 -->

		<form method="post"
			action="<%=request.getContextPath()%>/managerauthrizationfunction/managerAuthrizationFunctionEdit"
			name="form1" enctype="multipart/form-data">
			<table>
				<tr>
				
					
					<td>原管理員權限</td>
					<td><select name="managerAuthrizationFunctionNo" size="1">   <!-- -->
						<c:forEach var="managerAuthrizationFunctionVO" items="${list}">
						<option value="${managerAuthrizationFunctionVO.managerAuthrizationFunctionNo}">
						${managerAuthrizationFunctionVO.managerAuthrizationFunction}
						</c:forEach>
						</select>
					</td>
						
					<td>新管理員權限</td>
					<td><input type="text" name="managerAuthrizationFunction" size="20"
						value="${managerAuthrizationFunctionVO.managerAuthrizationFunction}" /></td>	
						
				</tr>
				<tr>
					<td>
			<input type="submit" value="修改">
			<input type="reset" value="重設">
					</td>
				</tr>
<!-- 	上傳圖片區 
				<tr>
					<td>
	        		<input type="file" name="upfile1" onclick="previewImage()" multiple id="upfile"
	        		style="position: relative; left:480px ;bottom: 530px;">
					</td>
				</tr> -->
			</table>
		</form>
		<div id="picPreview" style="display: flex; width: 400px ;flex-wrap:wrap;
		position: relative; left:480px ;bottom: 530px"></div>

	</div>

	</section>

	<!--main content end-->

</section>

<script type="text/javascript">

		var filereader_support = typeof FileReader != 'undefined';

		if (!filereader_support) {
			alert("No FileReader support");
		}

		acceptedTypes = {
				'image/png' : true,
				'image/jpeg' : true,
				'image/gif' : true
		};
		
		
		function previewImage() {
			var upfile = document.getElementById("upfile");
			upfile.addEventListener("change", function(event) {
				var files = event.target.files || event.dataTransfer.files;
				for (var i = 0; i < files.length; i++) {
					previewfile(files[i])
				}
			}, false);
		}
		
		function previewfile(file) {
			if (filereader_support === true && acceptedTypes[file.type] === true) {
				var reader = new FileReader();
				reader.onload = function(event) {
					var image = new Image();
					image.src = event.target.result;
					image.width = 128;
					picPreview.appendChild(image);
				};
				reader.readAsDataURL(file);
			} else {
				picPreview.innerHTML += "<p>" + "filename: <strong>" + file.name
						+ "</strong><br>" + "ContentTyp: <strong>" + file.type
						+ "</strong><br>" + "size: <strong>" + file.size
						+ "</strong> bytes</p>";
			}
		}
		// 當upload重新選擇 清空舊有資料
		$("#upload").change(function(){
		    $("#picPreview").empty() // 清空當下預覽
		    previewfile(this.files) // this即為<input>元素
		})
	</script>
</body>
</html>