package com.aprendec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.aprendec.conexion.Conexion;
import com.aprendec.model.*;

public class ProductoDAO {
	private Connection connection;
	private PreparedStatement statement;
	private boolean estadoOperacion;
	
	/**
	 * Guarda el objeto producto creado en el formulario
	 * @param producto
	 * @return
	 * @throws SQLException
	 */
	public boolean guardar(Producto producto) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			connection.setAutoCommit(false);
			sql = "INSERT INTO productos (id, nombre, cantidad, precio, fecha_crear,fecha_actualizar) VALUES(?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);

			statement.setString(1, null);
			statement.setString(2, producto.getNombre());
			statement.setDouble(3, producto.getCantidad());
			statement.setDouble(4, producto.getPrecio());
			statement.setDate(5, producto.getFechaCrear());
			statement.setDate(6, producto.getFechaActualizar());

			estadoOperacion = statement.executeUpdate() > 0;

			connection.commit();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	/**
	 * Edita el producto recibido
	 * @param producto
	 * @return
	 * @throws SQLException
	 */
	public boolean editar(Producto producto) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();
		try {
			connection.setAutoCommit(false);
			sql = "UPDATE productos SET nombre=?, cantidad=?, precio=?, fecha_actualizar=?, horaActualizacion = ? WHERE id=?";
			statement = connection.prepareStatement(sql);

			statement.setString(1, producto.getNombre());
			statement.setDouble(2, producto.getCantidad());
			statement.setDouble(3, producto.getPrecio());
			statement.setDate(4, producto.getFechaActualizar());
			statement.setTime(5, Time.valueOf(producto.getHoraActualizacion()));
			statement.setInt(6, producto.getId());
			
			System.out.println(producto.getHoraActualizacion());

			estadoOperacion = statement.executeUpdate() > 0;
			connection.commit();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	/**
	 * Elimina un producto por su id
	 * @param idProducto
	 * @return
	 * @throws SQLException
	 */
	// eliminar producto
	public boolean eliminar(int idProducto) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();
		try {
			connection.setAutoCommit(false);
			sql = "DELETE FROM productos WHERE id=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, idProducto);

			estadoOperacion = statement.executeUpdate() > 0;
			connection.commit();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}
	/**
	 * Obtiene una lista con todos los productos existentes
	 * @return
	 * @throws SQLException
	 */
	// obtener lista de productos
	public List<Producto> obtenerProductos() throws SQLException {
		ResultSet resultSet = null;
		List<Producto> listaProductos = new ArrayList<>();

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
					 
			sql = "SELECT *, " +
					"CONCAT(UPPER(SUBSTRING(DATE_FORMAT(fecha_actualizar, '%W', 'es_ES'), 1, 1)), " +
					"LOWER(SUBSTRING(DATE_FORMAT(fecha_actualizar, '%W', 'es_ES'), 2)), ', ', " +
					"DATE_FORMAT(fecha_actualizar, '%e de %M de %Y', 'es_ES')) AS fecha_formateada " +
					"FROM productos;";
			
			
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				Producto p = new Producto();
				p.setId(resultSet.getInt(1));
				p.setNombre(resultSet.getString(2));
				p.setCantidad(resultSet.getDouble(3)); 
				p.setPrecio(resultSet.getDouble(4));
				p.setFechaCrear(resultSet.getDate(5));
				p.setFechaActualizar(resultSet.getDate(6));
				
				String fechaFormateada = resultSet.getString("fecha_formateada");
				p.setFechaFormateada(fechaFormateada);
				
				
				// Obtener la horaActualizacion y convertir a String
	            Time horaActualizacionTime = resultSet.getTime("horaActualizacion");
	            String horaActualizacion = horaActualizacionTime != null ? horaActualizacionTime.toString() : null;
	           
	            p.setHoraActualizacion(horaActualizacion); // Ahora se usa un String
				
				
				listaProductos.add(p);

				System.out.println("Fecha formateada: " + fechaFormateada);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaProductos;
	}

	/**
	 * Obtiene todos los parametros del producto por el id recibido
	 * @param idProducto
	 * @return
	 * @throws SQLException
	 */
	// obtener producto
	public Producto obtenerProducto(int idProducto) throws SQLException {
		ResultSet resultSet = null;
		Producto p = new Producto();

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			sql = "SELECT * FROM productos WHERE id =?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, idProducto);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				p.setId(resultSet.getInt(1));
				p.setNombre(resultSet.getString(2));
				p.setCantidad(resultSet.getDouble(3));
				p.setPrecio(resultSet.getDouble(4));
				p.setFechaCrear(resultSet.getDate(5));
				p.setFechaActualizar(resultSet.getDate(6));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	// obtener conexion pool
	private Connection obtenerConexion() throws SQLException {
		return Conexion.getConnection();
	}

	/**
	 * Comprueba si en la db hay un producto con el nombre recibido
	 * @param nombrep
	 * @return
	 * @throws SQLException
	 */
	// comprobar si existe el producto
	public boolean existeProducto(String nombrep) throws SQLException {
		ResultSet resultSet = null;
		// Producto p = new Producto();

		String sql = null;
		connection = obtenerConexion();

		try {
			sql = "SELECT * FROM productos WHERE nombre ='" + nombrep + "'";

			Statement st = connection.createStatement();
//   statement = connection.prepareStatement(sql);
//   statement.setString(2, nombrep);

			resultSet = st.executeQuery(sql);

			if (resultSet.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}