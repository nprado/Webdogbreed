package mc536.dogbreed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import mc536.dogbreed.connection.ConnectionManager;
import mc536.dogbreed.entities.Researcher;
import mc536.dogbreed.entities.Symptom;
import mc536.dogbreed.log.Console;

/**
 * Manages symptoms data.
 */
public class SymptomDao implements BaseDao<Symptom> {
	
	/**
	 * Statement to insert a row.
	 */
	private static final String SQL_INSERT = "INSERT INTO Symptom(idSymptom,Name, Description) " +
			"VALUES (?, ?, ?)";
	
	/**
	 * Statement to update a row.
	 */
	private static final String SQL_UPDATE = "UPDATE Symptom SET Name = ?, Description = ? WHERE " +
			"idSymptom = ?";
	
	/**
	 * Statement to delete a row.
	 */
	private static final String SQL_DELETE = "DELETE FROM Symptom WHERE idSymptom = ?";

	/**
	 * Statement to query for all rows.
	 */
	private static final String SQL_SELECT = "SELECT * FROM Symptom";
	
	/**
	 * Statement to query to query a specific row.
	 */
	private static final String SQL_FIND = "SELECT * FROM Symptom WHERE idSymptom = ?";

	/**
	 * Statement to query rows with a custom filter.
	 */
	private static final String SQL_CUSTOM = "SELECT * FROM Symptom WHERE 1 = 1 ";

	/**
	 * @see {@link BaseDao}{@link #save(Symptom)}
	 */
	@Override
	public void save(Symptom entity) {
		if (entity.getidSymptom() > 0) {
			this.update(entity);
		} else {
			this.insert(entity);
		}
	}

	/**
	 * @see {@link BaseDao}{@link #delete(Symptom)}
	 */
	@Override
	public void delete(Symptom entity) {
		PreparedStatement stmt = null;
		
		try{
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_DELETE);
			stmt.setInt(1, entity.getidSymptom());
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
	public List<Symptom> list() {
		List<Symptom> list = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try{
			list = new ArrayList<Symptom>();
			
			// Execute query
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_SELECT);
			result = stmt.executeQuery();
			
			// Fill the list
			while (result.next()) {
				list.add(new Symptom(result.getInt("idSymptom"), result.getString("Name"), 
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
	public Symptom find(int id) {
		Symptom entity = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_FIND);
			stmt.setInt(1, id);

			// Execute the query
			result = stmt.executeQuery();
			
			if (result.next()) {
				entity = new Symptom(result.getInt("idSymptom"),result.getString("Name"), 
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
	public List<Symptom> customFilter(Hashtable<String, Object> params) {
		PreparedStatement stmt = null;
		StringBuilder builder = null;
		ArrayList<Object> values = null;
		ResultSet result = null;
		List<Symptom> list = null;
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
				list = new ArrayList<Symptom>();
				
				// Fill the list
				while (result.next()) { 
					list.add(new Symptom(result.getInt("idSymptom"),result.getString("Name"), 
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
	private void insert(Symptom entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_INSERT);
			stmt.setInt(1, entity.getidSymptom());
			stmt.setString(2, entity.getName());
			stmt.setString(3, entity.getDescription());
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
	private void update(Symptom entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_UPDATE);
			stmt.setString(1, entity.getName());
			stmt.setString(2, entity.getDescription());
			stmt.setInt(3, entity.getidSymptom());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Console.log(e);
	    }
	}

}
