<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Editar Producto</title>
<link rel="stylesheet" type="text/css" href="css//listar.css">
<link rel="stylesheet" type="text/css" href="css//boton_guardar.css">
<link rel="stylesheet" type="text/css" href="css//enlace_a.css">
<link rel="stylesheet" type="text/css" href="css//text_error.css">
</head>
<body>
 <h1>Editar Producto</h1>
 <form action="productos" method="post">
  <c:set var="producto" value="${producto}"></c:set>
  <input type="hidden" name="opcion" value="editar">
  <input type="hidden" name="id" value="${producto.id}">
  <table>
   <tr>
    <td>Nombre:</td>
    <td><input type="text" name="nombre" size="50" value="${producto.nombre}"></td>
   </tr>
   <tr>
    <td>Cantidad:</td>
    <td><input type="number" name="cantidad" size="50" value="${producto.cantidad}"></td>
   </tr>
   <tr>
    <td>Precio:</td>
    <td><input type="number" name="precio" size="50" value="${producto.precio}"></td>
   </tr>
  </table>
  <br>
  <input type="submit" id="submit" value="Guardar" class="button-10">
 </form>
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
<br><br>
  <a href="index.jsp" >Volver</a>
</body>
</html>              