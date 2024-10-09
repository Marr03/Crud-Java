<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu de Opciones</title>
<link rel="stylesheet" type="text/css" href="css//index.css">
<link rel="stylesheet" type="text/css" href="css//text_error.css">


</head>
<body>
    <h1>Menu de Opciones Productos</h1>
    <table>
        <tr>
            <td class="text_main">
                <a href="productos?opcion=crear">
                    <img src="img//crear.png">
                    <span>Crear un Producto</span>
                </a>
            </td>
        </tr>
        <tr>
            <td class="text_main">
                <a href="productos?opcion=listar">
                    <img src="img//listar.png">
                    <span>Listar Productos</span>
                </a>
            </td>
        </tr>
<%
  		if (session.getAttribute("Error") != null){
  			  %> 
  			  <br>			
  			<tr>
            <td id="text_error">
                <%= session.getAttribute("Error") %>
            </td>
        </tr>
  		<%}
  
  session.removeAttribute("Error");
  session.invalidate();
%>


	</table>
</body>
</html>