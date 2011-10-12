package mc536.dogbreed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import mc536.dogbreed.connection.ConnectionManager;
import mc536.dogbreed.entities.Picture;
import mc536.dogbreed.entities.Prophylaxy;
import mc536.dogbreed.log.Console;

/**
 * Manages prophylaxies data.
 */
public class ProphylaxyDao implements BaseDao<Prophylaxy> {
	
	/**
	 * Statement to insert a row.
	 */
	private static final String SQL_INSERT = "INSERT INTO Prophylaxy(idProphylaxy, Description) " +
			"VALUES (?, ?)";
	
	/**
	 * Statement to update a row.
	 */
	private static final String SQL_UPDATE = "UPDATE Prophylaxy SET Description = ? WHERE " +
			"idProphylaxy = ?";
	
	/**
	 * Statement to delete a row.
	 */
	private static final String SQL_DELETE = "DELETE FROM Prophylaxy WHERE idProphylaxy = ?";

	/**
	 * Statement to query for all rows.
	 */
	private static final String SQL_SELECT = "SELECT * FROM Prophylaxy";
	
	/**
	 * Statement to query to query a specific row.
	 */
	private static final String SQL_FIND = "SELECT * FROM Prophylaxy WHERE idProphylaxy = ?";

	/**
	 * Statement to query rows with a custom filter.
	 */
	private static final String SQL_CUSTOM = "SELECT * FROM Prophylaxy WHERE 1 = 1 ";

	/**
	 * @see {@link BaseDao}{@link #save(Prophylaxy)}
	 */
	@Override
	public void save(Prophylaxy entity) {
		if (entity.getidProphylaxy() > 0) {
			this.update(entity);
		} else {
			this.insert(entity);
		}
	}

	/**
	 * @see {@link BaseDao}{@link #delete(Prophylaxy)}
	 */
	@Override
	public void delete(Prophylaxy entity) {
		PreparedStatement stmt = null;
		
		try{
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_DELETE);
			stmt.setInt(1, entity.getidProphylaxy());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Console.log(e);
		}
	}

	/**
	 * @see {@link BaseDao}{@link #list()}
	 */
	@Override
	public List<Prophylaxy> list() {
		List<Prophylaxy> list = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try{
			list = new ArrayList<Prophylaxy>();
			
			// Execute query
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_SELECT);
			result = stmt.executeQuery();
			
			// Fill the list
			while (result.next()) {
				list.add(new Prophylaxy(result.getInt("idProphylaxy"), 
						result.getString("Description")));
			}
			
			stmt.close();
			result.close();
		} catch (SQLException e)	{
			Console.log(e);
		}
		
		return list;
	}

	/**
	 * @see {@link BaseDao}{@link #find(int)}
	 */
	@Override
	public Prophylaxy find(int id) {
		Prophylaxy entity = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_FIND);
			stmt.setInt(1, id);

			// Execute the query
			result = stmt.executeQuery();
			
			if (result.next()) {
				entity = new Prophylaxy(result.getInt("idProphylaxy"),
						result.getString("Description"));
			}
			
			stmt.close();
			result.close();
		} catch (SQLException e) {
			Console.log(e);
		}
		
		return entity;
	}
	
	/**
	 * @see {@link BaseDao}{@link #customFilter(Hashtable)}
	 */
	@Override
	public List<Prophylaxy> customFilter(Hashtable<String, Object> params) {
		PreparedStatement stmt = null;
		StringBuilder builder = null;
		ArrayList<Object> values = null;
		ResultSet result = null;
		List<Prophylaxy> list = null;
		int i = 1;
		
		if (params != null && params.size() > 0) {
			try {
				builder = new StringBuilder(SQL_CUSTOM);
				values = new ArrayList<Object>();
					
				for (Entry<String, Object> entry : params.entrySet()) {
					builder.append(" AND " + entry.getKey() + " = ?");
					values.add(entry.getValue());
				}
				
				stmt = ConnectionManager.getConnection().prepareStatement(builder.toString());
				
				for (Object o : values) {
					if (o instanceof String) {
						stmt.setString(i++, String.valueOf(o));
					} else if (o instanceof Integer) {
						stmt.setInt(i++, (Integer) o);
					} else if (o instanceof Float) {
						stmt.setFloat(i++, (Float) o);
					}
				}
				
				result = stmt.executeQuery();
				list = new ArrayList<Prophylaxy>();
				
				// Fill the list
				while (result.next()) { 
					list.add(new Prophylaxy(result.getInt("idProphylaxy"),
							result.getString("Description")));
				}
				
				stmt.close();
				result.close();
			} catch (SQLException e) {
				Console.log(e);
			}
		}
		
		return list;
	}
	
	/**
	 * Inserts a new row.
	 * 
	 * @param entity Row to be inserted into the database.
	 */
	private void insert(Prophylaxy entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_INSERT);
			stmt.setInt(1, entity.getidProphylaxy());
			stmt.setString(2, entity.getDescription());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Console.log(e);
        }
	}
	
	/**
	 * Updates a row.
	 * 
	 * @param entity Row to be updated.
	 */
	private void update(Prophylaxy entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_UPDATE);
			stmt.setString(1, entity.getDescription());
			stmt.setInt(2, entity.getidProphylaxy());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Console.log(e);
	    }
	}	

}
