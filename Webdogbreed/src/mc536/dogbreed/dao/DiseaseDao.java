package mc536.dogbreed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import mc536.dogbreed.connection.ConnectionManager;
import mc536.dogbreed.entities.Disease;
import mc536.dogbreed.log.Console;

/**
 * Manages diseases data.
 */
public class DiseaseDao implements BaseDao<Disease> {
	
	/**
	 * Statement to insert a row.
	 */
	private static final String SQL_INSERT = "INSERT INTO Disease(idDisease, Description) " +
			"VALUES (?, ?)";
	
	/**
	 * Statement to update a row.
	 */
	private static final String SQL_UPDATE = "UPDATE Disease SET Description = ? WHERE " +
			"idPicture = ?";
	
	/**
	 * Statement to delete a row.
	 */
	private static final String SQL_DELETE = "DELETE FROM Disease WHERE idDisease = ?";
	
	/**
	 * Statement to query for all rows.
	 */
	private static final String SQL_SELECT = "SELECT * FROM Disease";
	
	/**
	 * Statement to query to query a specific row.
	 */
	private static final String SQL_FIND = "SELECT * FROM Disease WHERE image = ?";
	
	/**
	 * Statement to query rows with a custom filter.
	 */
	private static final String SQL_CUSTOM = "SELECT * FROM Disease WHERE 1 = 1 ";

	/**
	 * @see {@link BaseDao}{@link #save(Disease)}
	 */
	@Override
	public void save(Disease entity) {
		if (entity.getidDisease() > 0) {
			this.update(entity);
		} else {
			this.insert(entity);
		}
	}

	/**
	 * @see {@link BaseDao}{@link #delete(Disease)}
	 */
	@Override
	public void delete(Disease entity) {
		PreparedStatement stmt = null;
		
		try{
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_DELETE); 
			stmt.setInt(1, entity.getidDisease());
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
	public List<Disease> list() {
		List<Disease> list = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try{
			list = new ArrayList<Disease>();
			
			// Execute query
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_SELECT);
			resultSet = stmt.executeQuery();
			
			// Fill the list
			while (resultSet.next()) { 
				list.add(new Disease(resultSet.getInt("idDisease"), 
						resultSet.getString("Description")));
			}
			
			stmt.close();
			resultSet.close();
		} catch (SQLException e)	{
			Console.log(e);
		}
		
		return list;
	}

	/**
	 * @see {@link BaseDao}{@link #find(int)}
	 */
	@Override
	public Disease find(int id) {
		Disease entity = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_FIND);
			stmt.setInt(1, id);

			// Execute the query
			resultSet = stmt.executeQuery();
			
			if (resultSet.next()) {
				entity = new Disease(resultSet.getInt("idDisease"), 
						resultSet.getString("Description"));
			}
			
			stmt.close();
			resultSet.close();
		} catch (SQLException e) {
			Console.log(e);
		}
		
		return entity;
	}
	
	/**
	 * @see {@link BaseDao}{@link #customFilter(Hashtable)}
	 */
	@Override
	public List<Disease> customFilter(Hashtable<String, Object> params) {
		PreparedStatement stmt = null;
		StringBuilder builder = null;
		ArrayList<Object> values = null;
		ResultSet result = null;
		List<Disease> list = null;
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
				list = new ArrayList<Disease>();
				
				// Fill the list
				while (result.next()) { 
					list.add(new Disease(result.getInt("idDisease"), 
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
	private void insert(Disease entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_INSERT);
			stmt.setInt(1, entity.getidDisease()); // mudar depois AUTOINCREMENT
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
	private void update(Disease entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_UPDATE);
			stmt.setString(1, entity.getDescription());
			stmt.setInt(2, entity.getidDisease());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Console.log(e);
	    }
	}
	
}
