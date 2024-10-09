<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Crear Producto</title>
<link rel="stylesheet" type="text/css" href="css//listar.css">
<link rel="stylesheet" type="text/css" href="css//boton_guardar.css">
<link rel="stylesheet" type="text/css" href="css//enlace_a.css">
<link rel="stylesheet" type="text/css" href="css//text_error.css">

</head>
<body>
 <h1>Crear Producto</h1>
 <form action="productos" method="post">
  <input type="hidden" name="opcion" value="guardar">
  <table>
   <tr>
    <td>Nombre:</td>
    <td class="td_form"><input type="text" name="nombre" size="" required></td>
   </tr>
   <tr>
    <td>Cantidad:</td>
    <td class="td_form"><input type="number" name="cantidad" size=""></td>
   </tr>
   <tr>
    <td>Precio:</td>
    <td class="td_form"><input type="text" name="precio" size="" pattern="\d+(\.\d{1,2})?" title="Poner un numero con 2 decimales como maximo"></td>
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