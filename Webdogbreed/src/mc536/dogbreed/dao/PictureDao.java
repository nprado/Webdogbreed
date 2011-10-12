package mc536.dogbreed.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import mc536.dogbreed.connection.ConnectionManager;
import mc536.dogbreed.entities.DogBreed;
import mc536.dogbreed.entities.Picture;
import mc536.dogbreed.log.Console;

/**
 * Manages pictures data.
 */
public class PictureDao implements BaseDao<Picture> {
	
	/**
	 * Statement to insert a row.
	 */
	private static final String SQL_INSERT = "INSERT INTO Picture(image)  VALUES (?)";
	
	/**
	 * Statement to update a row.
	 */
	private static final String SQL_UPDATE = "UPDATE Picture SET image = ? WHERE idPicture = ?";
	
	/**
	 * Statement to delete a row.
	 */
	private static final String SQL_DELETE = "DELETE FROM Picture WHERE idPicture = ?";

	/**
	 * Statement to query for all rows.
	 */
	private static final String SQL_SELECT = "SELECT * FROM Picture";
	
	/**
	 * Statement to query to query a specific row.
	 */
	private static final String SQL_FIND = "SELECT * FROM Picture WHERE idPicture = ?";

	/**
	 * Statement to query rows with a custom filter.
	 */
	private static final String SQL_CUSTOM = "SELECT * FROM Picture WHERE 1 = 1 ";

	/**
	 * @see {@link BaseDao}{@link #save(Picture)}
	 */
	@Override
	public void save(Picture entity) {
		if (entity.getidPicture() > 0) {
			this.update(entity);
		} else {
			this.insert(entity);
		}
	}

	/**
	 * @see {@link BaseDao}{@link #delete(Picture)}
	 */
	@Override
	public void delete(Picture entity) {
		PreparedStatement stmt = null;
		
		try{
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_DELETE);
			stmt.setInt(1, entity.getidPicture());
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
	public List<Picture> list() {
		List<Picture> list = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try{
			list = new ArrayList<Picture>();
			
			// Execute query
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_SELECT);
			result = stmt.executeQuery();
			
			// Fill the list
			while (result.next()) {
				list.add(new Picture(result.getInt("idPicture"), result.getString("image")));
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
	public Picture find(int id) {
		Picture entity = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_FIND);
			stmt.setInt(1, id);

			// Execute the query
			result = stmt.executeQuery();
			
			if (result.next()) {
				entity = new Picture(result.getInt("idPicture"), result.getString("image"));
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
	public List<Picture> customFilter(Hashtable<String, Object> params) {
		PreparedStatement stmt = null;
		StringBuilder builder = null;
		ArrayList<Object> values = null;
		ResultSet result = null;
		List<Picture> list = null;
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
				list = new ArrayList<Picture>();
				
				// Fill the list
				while (result.next()) { 
					list.add(new Picture(result.getInt("idPicture"), result.getString("image")));
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
	private void insert(Picture entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_INSERT);
			stmt.setString(1, entity.getImage());
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
	private void update(Picture entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_UPDATE);
			stmt.setString(1, entity.getImage());
			stmt.setInt(2, entity.getidPicture());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Console.log(e);
	    }
	}
	
}
