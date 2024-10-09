package com.aprendec.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import com.aprendec.dao.ProductoDAO;
import com.aprendec.model.Producto;

/**
 * Servlet implementation class ProductoController
 */
@WebServlet(description = "administra peticiones para la tabla productos", urlPatterns = { "/productos" })
public class ProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */


	// Gestiona las url llevandote a ellas /Crear/Listar/meditar?
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		String opcion = request.getParameter("opcion");

		if (opcion.equals("crear")) {
			System.out.println("Usted a presionado la opcion crear");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
			requestDispatcher.forward(request, response);
			
		} else if (opcion.equals("listar")) {
			ProductoDAO productoDAO = new ProductoDAO();
			List<Producto> lista = new ArrayList<>();
			try {
				lista = productoDAO.obtenerProductos();

				for (Producto producto : lista) {
					System.out.println(producto);
					System.out.println(producto.getHoraActualizacion());
				}

				request.setAttribute("lista", lista);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
				requestDispatcher.forward(request, response);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				session.setAttribute("Error", "Error: No se ha podido listar los productos.");

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
			}

			System.out.println("Usted ha presionado la opcion listar");
			
		} else if (opcion.equals("meditar")) {
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println("Editar id: " + id);
			ProductoDAO productoDAO = new ProductoDAO();
			Producto p = new Producto();
			try {
				p = productoDAO.obtenerProducto(id);
				System.out.println(p);
				request.setAttribute("producto", p);
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);	

			} catch (SQLException e) {
				session.setAttribute("Error", "Error: No se ha podido editar el producto.");
			
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);

				e.printStackTrace();
			}
		} else if (opcion.equals("eliminar")) {
			ProductoDAO productoDAO = new ProductoDAO();
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				productoDAO.eliminar(id);
				System.out.println("Registro eliminado satisfactoriamente...");
			} catch (SQLException e) {
			
				session.setAttribute("Error", "Error: No se ha podido eliminar el producto");
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
			}
			
			
			session.setAttribute("Error", "El producto se ha borrado correctamente");
			response.sendRedirect(request.getContextPath() + "/productos?opcion=listar");

		}
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	// Realiza las operaciones
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String opcion = request.getParameter("opcion");
		Date fechaActual = new Date();
		
		HttpSession session = request.getSession();

		if (opcion.equals("guardar")) {

			try {
				ProductoDAO productoDAO = new ProductoDAO();
				Producto producto = new Producto();
				
				if (request.getParameter("cantidad").isEmpty() || request.getParameter("precio").isEmpty()) {

					session.setAttribute("Error", "Error: Todos los campos deben ser rellenados");
					
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/crear.jsp");
					requestDispatcher.forward(request, response);
								
				} else if(productoDAO.existeProducto(request.getParameter("nombre"))) {
					
					session.setAttribute("Error", "Error: El producto ya existe");
					
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/crear.jsp");
					requestDispatcher.forward(request, response);
				}else {
					producto.setNombre(request.getParameter("nombre"));
					producto.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
					producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
					producto.setFechaCrear(new java.sql.Date(fechaActual.getTime()));
					

					productoDAO.guardar(producto);
					System.out.println("Registro guardado satisfactoriamente...");
					
					session.setAttribute("Error", "El producto se ha guardado correctamente");
				
					response.sendRedirect(request.getContextPath() + "/productos?opcion=listar");
					
					 
					 //No funciona. Se queda en la misma URL y no controla parametros de consulta
					//request.sestAttribute("opcion","listar"); Si funcionaria
					 //RequestDispatcher requestDispatcher = request.getRequestDispatcher("/productos?opcion=listar");
					//requestDispatcher.forward(request, response);
				}
				


			} catch (Exception e) {
				
				session.setAttribute("Error", "Error: No se ha podido guardar el producto");
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
			}
			
			
			
			
		} else if (opcion.equals("editar")) {
			try {
				Producto producto = new Producto();
				Producto productoOld = new Producto();
				ProductoDAO productoDAO = new ProductoDAO();
				
				int id = Integer.parseInt(request.getParameter("id")); 
				
				productoOld = productoDAO.obtenerProducto(id); 
				
				if(request.getParameter("nombre").isEmpty() || request.getParameter("cantidad").isEmpty() || request.getParameter("precio").isEmpty()) {
					
					session.setAttribute("Error", "Error: Todos los campos deben ser rellenados");
					response.sendRedirect(request.getContextPath() + "/productos?opcion=meditar&id=" + id);
  					
				}else if ((productoOld.getCantidad() == Double.parseDouble(request.getParameter("cantidad")) && (productoOld.getNombre().equals(request.getParameter("nombre"))) && (productoOld.getPrecio() == Double.parseDouble(request.getParameter("precio"))))) {
					
					session.setAttribute("Error", "Error: No has modificado nada");				
					response.sendRedirect(request.getContextPath() + "/productos?opcion=meditar&id=" + id);

				}else if(productoDAO.existeProducto(request.getParameter("nombre")) && (!productoOld.getNombre().equals(request.getParameter("nombre")))) {
					System.out.println(productoOld.getNombre() + request.getParameter("nombre"));
					session.setAttribute("Error", "Error: Ya existe este producto");
					response.sendRedirect(request.getContextPath() + "/productos?opcion=meditar&id=" + id);

				}else {
					
					producto.setId(Integer.parseInt(request.getParameter("id")));
					producto.setNombre(request.getParameter("nombre"));
					producto.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
					producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
					producto.setFechaActualizar(new java.sql.Date(fechaActual.getTime()));
					
					String horaActualizacion = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
		            producto.setHoraActualizacion(horaActualizacion);
					
		            productoDAO.editar(producto);
					System.out.println("Registro editado satisfactoriamente...");
					
					session.setAttribute("Error", "El producto se ha editado correctamente");
	
					response.sendRedirect(request.getContextPath() + "/productos?opcion=listar");	
				}
				
			} catch (Exception e) {
				session.setAttribute("Error", "Error: No se ha podido editar el producto");
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
			}				
			
			

			
		}

		// doGet(request, response);
	}

}