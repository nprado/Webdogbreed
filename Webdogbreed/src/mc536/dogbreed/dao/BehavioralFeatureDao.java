package mc536.dogbreed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import mc536.dogbreed.connection.ConnectionManager;
import mc536.dogbreed.entities.BehavioralFeature;

import mc536.dogbreed.log.Console;

/**
 * Manages behavioral features data.
 */
public class BehavioralFeatureDao implements BaseDao<BehavioralFeature> {
	
	/**
	 * Statement to insert a row.
	 */
	private static final String SQL_INSERT = "INSERT INTO Behavioral_Features(" +
			"idBehavioral_Feature, idBreed, Good_With_Children, Sociability, Agressiveness, " +
			"Temperature_Adaptation, Trainability, Exercise, Grooming_Needs, Noise) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	/**
	 * Statement to update a row.
	 */
	private static final String SQL_UPDATE = "UPDATE Behavioral_Features SET " +
			"Good_With_Children = ?, Sociability = ?, Agressiveness = ?, " +
			"Temperature_Adaptation = ?, Trainability = ?, Exercise = ?, Grooming_Needs = ?, " +
			"Noise = ? WHERE idBehavioral_Feature = ? AND idBreed = ?";
	
	/**
	 * Statement to delete a row.
	 */
	private static final String SQL_DELETE = "DELETE FROM Behavioral_Features WHERE " +
			"idBehavioral_Feature = ? AND idBreed = ?";
	
	/**
	 * Statement to query for all rows.
	 */
	private static final String SQL_SELECT = "SELECT * FROM Behavioral_Features";
	
	/**
	 * Statement to query to query a specific row.
	 */
	private static final String SQL_FIND = "SELECT * FROM Behavioral_Features WHERE " +
			"idBehavioral_Feature = ?";
	
	/**
	 * Statement to query rows with a custom filter.
	 */
	private static final String SQL_CUSTOM = "SELECT * FROM Behavioral_Features WHERE 1 = 1 ";

	/**
	 * @see {@link BaseDao}{@link #save(BehavioralFeature)}
	 */
	@Override
	public void save(BehavioralFeature entity) {
		if (entity.getIdBehavioralFeature() > 0) {
			this.update(entity);
		} else {
			this.insert(entity);
		}
	}

	/**
	 * @see {@link BaseDao}{@link #delete(BehavioralFeature)}
	 */
	@Override
	public void delete(BehavioralFeature entity) {
		PreparedStatement stmt = null;
		
		try{
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_DELETE); 
			stmt.setInt(1, entity.getIdBehavioralFeature());
			stmt.setInt(2, entity.getIdBreed());
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
	public List<BehavioralFeature> list() {
		List<BehavioralFeature> list = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try{
			list = new ArrayList<BehavioralFeature>();
			
			// Execute query
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_SELECT);
			resultSet = stmt.executeQuery();
			
			// Fill the list
			while (resultSet.next()) { 
				list.add(new BehavioralFeature(resultSet.getInt("idBehavioral_Feature"), 
						resultSet.getInt("idBreed"), resultSet.getInt("Good_With_Children"), 
						resultSet.getInt("Sociability"), resultSet.getInt("Agressiveness"), 
						resultSet.getInt("Temperature_Adaptation"), resultSet.getInt("Trainability"),
						resultSet.getInt("Exercise"), resultSet.getInt("Grooming_Needs"), 
						resultSet.getInt("Noise")));
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
	public BehavioralFeature find(int id) {
		BehavioralFeature entity = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_FIND);
			stmt.setInt(1, id);

			// Execute the query
			resultSet = stmt.executeQuery();
			
			if (resultSet.next()) {
				entity = new BehavioralFeature(resultSet.getInt("idBehavioral_Feature"), 
						resultSet.getInt("idBreed"), resultSet.getInt("Good_With_Children"), 
						resultSet.getInt("Sociability"), resultSet.getInt("Agressiveness"), 
						resultSet.getInt("Temperature_Adaptation"), 
						resultSet.getInt("Trainability"), resultSet.getInt("Exercise"), 
						resultSet.getInt("Grooming_Needs"), resultSet.getInt("Noise"));
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
	public List<BehavioralFeature> customFilter(Hashtable<String, Object> params) {
		PreparedStatement stmt = null;
		StringBuilder builder = null;
		ArrayList<Object> values = null;
		ResultSet result = null;
		List<BehavioralFeature> list = null;
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
				list = new ArrayList<BehavioralFeature>();
				
				// Fill the list
				while (result.next()) { 
					list.add(new BehavioralFeature(result.getInt("idBehavioral_Feature"), 
							result.getInt("idBreed"), result.getInt("Good_With_Children"), 
							result.getInt("Sociability"), result.getInt("Agressiveness"), 
							result.getInt("Temperature_Adaptation"), 
							result.getInt("Trainability"), result.getInt("Exercise"), 
							result.getInt("Grooming_Needs"), result.getInt("Noise")));
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
	private void insert(BehavioralFeature entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_INSERT);
			stmt.setInt(1, entity.getIdBehavioralFeature());
			stmt.setInt(2, entity.getIdBreed());
			stmt.setInt(3, entity.getGoodWithChildren());
			stmt.setInt(4, entity.getSociability());
			stmt.setInt(5, entity.getAgressiveness());
			stmt.setInt(6, entity.getTemperatureAdaptation());
			stmt.setInt(7, entity.getTrainability());
			stmt.setInt(8, entity.getExercise());
			stmt.setInt(9, entity.getGroomingNeeds());
			stmt.setInt(10, entity.getNoise());			
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
	private void update(BehavioralFeature entity) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_UPDATE);
			stmt.setInt(1, entity.getIdBehavioralFeature());
			stmt.setInt(2, entity.getIdBreed());
			stmt.setInt(3, entity.getGoodWithChildren());
			stmt.setInt(4, entity.getSociability());
			stmt.setInt(5, entity.getAgressiveness());
			stmt.setInt(6, entity.getTemperatureAdaptation());
			stmt.setInt(7, entity.getTrainability());
			stmt.setInt(8, entity.getExercise());
			stmt.setInt(9, entity.getGroomingNeeds());
			stmt.setInt(10, entity.getNoise());
			stmt.execute();
		} catch (SQLException e) {
			Console.log(e);
	    }
	}

}
