<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Productos</title>
<link rel="stylesheet" type="text/css" href="css//listar.css">
<link rel="stylesheet" type="text/css" href="css//boton_guardar.css">
<link rel="stylesheet" type="text/css" href="css//enlace_a.css">
<link rel="stylesheet" type="text/css" href="css//text_error.css">
</head>
<body>
 <h1>Listar Productos</h1>
 <table border="1">
  <tr class="color_th">

   <td>Nombre</td>
   <td>Cantidad</td>
   <td>Precio</td>
   <td>Fecha Creacion</td>
   <td>Fecha Actualizacion</td>
   <td>Accion</td>
   <td>Accion</td>
  </tr>
  <c:forEach var="producto" items="${lista}">
  <tr>
    <td><c:out value="${ producto.nombre}"></c:out></td>
    <td><c:out value="${ producto.cantidad}"></c:out></td>
    <td><c:out value="${ producto.precio}"></c:out></td>
    <td><c:out value="${ producto.fechaCrear}"></c:out></td>
    <td>
    <c:out value="${ producto.fechaFormateada}"></c:out>
	<span style="color: gray;">
	    <c:out value="${producto.horaActualizacion}" />
	</span>
    </td>
    <td>
     <a href="productos?opcion=eliminar&id=<c:out value="${ producto.id}"></c:out>">
      Eliminar 
     </a>
     </td>
     <td>
     <a href="productos?opcion=meditar&id=<c:out value="${ producto.id}"></c:out>">
      Editar 
     </a>
     </td>
  </tr>
  </c:forEach>
 </table>
 <br><br><br>
 <h1>Crear Producto</h1>
 <form action="productos" method="post">
  <input type="hidden" name="opcion" value="guardar">
  <table border="1">
   <tr>
	<td>Nombre:</td>
    <td><input type="text" name="nombre" size="" required></td>
   </tr>
   <tr>
    <td>Cantidad:</td>
    <td><input type="number" name="cantidad" size=""></td>
   </tr>
   <tr>
    <td>Precio:</td>
    <td><input type="number" name="precio" size=""></td>
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
	<br> <br>
 <a href="index.jsp" >Volver</a>
</body>
</html>