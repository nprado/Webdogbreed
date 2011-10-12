package mc536.dogbreed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import mc536.dogbreed.connection.ConnectionManager;
import mc536.dogbreed.entities.Prophylaxy;
import mc536.dogbreed.entities.Researcher;
import mc536.dogbreed.log.Console;

/**
 * Manages researcher data.
 */
public class ResearcherDao implements BaseDao<Researcher> {
	
	/**
	 * Statement to insert a row.
	 */
	private static final String SQL_INSERT = "INSERT INTO Researcher(Name, " +
			"Password, idBreed) VALUES (?, md5(?), ?)";
	
	/**
	 * Statement to update a row.
	 */
	private static final String SQL_UPDATE = "UPDATE Researcher SET Name = ?, Password = ?, " +
			"idBreed = ? WHERE idResearcher = ?";
	
	/**
	 * Statement to delete a row.
	 */
	private static final String SQL_DELETE = "DELETE FROM Researcher WHERE idResearcher = ?";

	/**
	 * Statement to query for all rows.
	 */
	private static final String SQL_SELECT = "SELECT * FROM Researcher";
	
	/**
	 * Statement to query to query a specific row.
	 */
	private static final String SQL_FIND = "SELECT * FROM Researcher WHERE idResearcher = ?";
	
	/**
	 * statement to query a researcher authentication.
	 */
	private static final String SQL_AUTH = "SELECT * FROM Researcher WHERE Name = ? and Password " +
			"= md5(?)";

	/**
	 * Statement to query rows with a custom filter.
	 */
	private static final String SQL_CUSTOM = "SELECT * FROM Researcher WHERE 1 = 1 ";

	/**
	 * @see {@link BaseDao}{@link #save(Researcher)}
	 */
	@Override
	public void save(Researcher entity) {
		if (entity.getidResearcher() > 0) {
			this.update(entity);
		} else {
			this.insert(entity);
		}
	}

	/**
	 * @see {@link BaseDao}{@link #delete(Researcher)}
	 */
	@Override
	public void delete(Researcher entity) {
		PreparedStatement stmt = null;
		
		try{
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_DELETE);
			stmt.setInt(1, entity.getidResearcher());
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
	public List<Researcher> list() {
		List<Researcher> list = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try{
			list = new ArrayList<Researcher>();
			
			// Execute query
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_SELECT);
			result = stmt.executeQuery();
			
			// Fill the list
			while (result.next()) {
				list.add(new Researcher(result.getInt("idResearcher"), result.getString("Name"), 
						result.getString("Password"), result.getInt("idBreed")));
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
	public Researcher find(int id) {
		Researcher entity = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_FIND);
			stmt.setInt(1, id);

			// Execute the query
			result = stmt.executeQuery();
			
			if (result.next()) {
				entity = new Researcher(result.getInt("idResearcher"), result.getString("Name"), 
						result.getString("Password"), result.getInt("idBreed"));
			}
			
			stmt.close();
			result.close();
		} catch (SQLException e) {
			Console.log(e);
		}
		
		return entity;
	}

	/**
	 * Authenticates a researcher into the system.
	 * 
	 * @param researcher Researcher name.
	 * @param password Researcher password.
	 * @return Authenticated researcher ID or -1 if the researcher was not authenticated.
	 */
	public static int authenticate(String researcher, String password) {
		int id = -1;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try{
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_AUTH);
			stmt.setString(1, researcher);
			stmt.setString(2, password);
			
			// Execute the query
			result = stmt.executeQuery();
			
			if (result.next()) {
				id = result.getInt("idResearcher");
			}
		} catch(SQLException e) {
			Console.log(e);
		}
		
		return id;		
	}
	
	/**
	 * @see {@link BaseDao}{@link #customFilter(Hashtable)}
	 */
	@Override
	public List<Researcher> customFilter(Hashtable<String, Object> params) {
		PreparedStatement stmt = null;
		StringBuilder builder = null;
		ArrayList<Object> values = null;
		ResultSet result = null;
		List<Researcher> list = null;
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
				list = new ArrayList<Researcher>();
				
				// Fill the list
				while (result.next()) { 
					list.add(new Researcher(result.getInt("idResearcher"), result.getString("Name"), 
							result.getString("Password"), result.getInt("idBreed")));
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
	private void insert(Researcher entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_INSERT);
			stmt.setString(1, entity.getName());
			stmt.setString(2, entity.getPassword());
			stmt.setInt(3, entity.getidBreed());
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
	private void update(Researcher entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_UPDATE);
			stmt.setString(1, entity.getName());
			stmt.setString(2, entity.getPassword());
			stmt.setInt(3, entity.getidBreed());
			stmt.setInt(4, entity.getidResearcher());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Console.log(e);
	    }
	}

}
