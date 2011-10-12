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
import mc536.dogbreed.entities.DogBreed;
import mc536.dogbreed.log.Console;

/**
 * Manages dog breeds data.
 */
public class DogBreedDao implements BaseDao<DogBreed> {
	
	/**
	 * Statement to insert a row.
	 */
	private static final String SQL_INSERT = "INSERT INTO Dog_Breed(Name, Litter_Size, " +
			"Recognition, Life_Span) VALUES (?, ?, ?, ?)";
	
	/**
	 * Statement to update a row.
	 */
	private static final String SQL_UPDATE = "UPDATE Dog_Breed SET Name = ?,  Litter_Size= ?, " +
			"Recognition = ?, Life_Span= ? WHERE idBreed = ?";
	
	/**
	 * Statement to delete a row.
	 */
	private static final String SQL_DELETE =  "DELETE FROM Dog_Breed WHERE idBreed = ?";
	
	/**
	 * Statement to query for all rows.
	 */
	private static final String SQL_SELECT = "SELECT * FROM Dog_Breed";
	
	/**
	 * Statement to query to query a specific row.
	 */
	private static final String SQL_FIND = "SELECT * FROM Dog_Breed WHERE idBreed = ?";

	/**
	 * Statement to query rows with a custom filter.
	 */
	private static final String SQL_CUSTOM = "SELECT * FROM Dog_Breed WHERE 1 = 1 ";

	/**
	 * @see {@link BaseDao}{@link #save(DogBreed)}
	 */
	@Override
	public void save(DogBreed entity) {
		if (entity.getidDogBreed() > 0) {
			this.update(entity);
		} else {
			this.insert(entity);
		}
	}

	/**
	 * @see {@link BaseDao}{@link #delete(DogBreed)}
	 */
	@Override
	public void delete(DogBreed entity) {
		PreparedStatement stmt = null;
		
		try{
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_DELETE);
			stmt.setInt(1, entity.getidDogBreed());
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
	public List<DogBreed> list() {
		List<DogBreed> list = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try{
			list = new ArrayList<DogBreed>();
			
			// Execute query
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_SELECT);
			result = stmt.executeQuery();
			
			// Fill the list
			while (result.next()) {
				list.add(new DogBreed(result.getInt("idBreed"), result.getString("Name"),
						result.getInt("Litter_Size"), result.getString("Recognition"), result.getInt("Life_Span")));
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
	public DogBreed find(int id) {
		DogBreed entity = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_FIND);
			stmt.setInt(1, id);

			// Execute the query
			result = stmt.executeQuery();
			
			if (result.next()) {
				entity = new DogBreed(result.getInt("idBreed"), result.getString("Name"),
						result.getInt("Litter_Size"), result.getString("Recognition"), 
						result.getInt("Life_Span"));
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
	public List<DogBreed> customFilter(Hashtable<String, Object> params) {
		PreparedStatement stmt = null;
		StringBuilder builder = null;
		ArrayList<Object> values = null;
		ResultSet result = null;
		List<DogBreed> list = null;
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
				list = new ArrayList<DogBreed>();
				
				// Fill the list
				while (result.next()) { 
					list.add(new DogBreed(result.getInt("idBreed"), result.getString("Name"),
							result.getInt("Litter_Size"), result.getString("Recognition"), 
							result.getInt("Life_Span")));
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
	private void insert(DogBreed entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_INSERT);
			stmt.setString(1, entity.getName());
			stmt.setFloat(2, entity.getSize());
			stmt.setString(3, entity.getRecognition());
			stmt.setInt(4, entity.getLifeSpan());
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
	private void update(DogBreed entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_UPDATE);
			stmt.setString(1, entity.getName());
			stmt.setFloat(2, entity.getSize());
			stmt.setString(3, entity.getRecognition());
			stmt.setInt(4, entity.getLifeSpan());
			stmt.setInt(5, entity.getidDogBreed());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Console.log(e);
	    }
	}
		
}


