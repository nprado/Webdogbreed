package mc536.dogbreed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mc536.dogbreed.connection.ConnectionManager;
import mc536.dogbreed.entities.RelDiseaseSymptom;
import mc536.dogbreed.log.Console;

/**
 * Manages relations between disease and symptom.
 */
public class RelDiseaseSymptomDao implements BaseRelationDao<RelDiseaseSymptom> {
	
	/**
	 * Statement to insert a row.
	 */
	private static final String SQL_INSERT = "INSERT INTO RelDiseaseSymptom(idDisease, " +
			"idSymptom) VALUES (?, ?)";
	
	/**
	 * Statement to update a row.
	 */
	private static final String SQL_UPDATE = "UPDATE RelDiseaseSymptom SET idSymptom = ? WHERE " +
			"idDisease = ? AND IdSymptom = ?";
	
	/**
	 * Statement to delete a row.
	 */
	private static final String SQL_DELETE = "DELETE FROM RelDiseaseSymptom WHERE idDisease = ? " +
			"AND idSymptom = ?";
	
	/**
	 * Statement to query for all rows.
	 */
	private static final String SQL_SELECT = "SELECT * FROM RelDiseaseSymptom";
	
	/**
	 * Statement to query to query a specific row.
	 */
	private static final String SQL_FIND = "SELECT * FROM Disease WHERE idDisease = ? AND " +
			"idSymptom = ?";
	
	/**
	 * @see {@link BaseRelationDao}{@link #save(RelDiseaseSymptom)}
	 */
	@Override
	public void save(RelDiseaseSymptom rel) {
		if (rel.getidDisease() > 0 || rel.getidSymptom() > 0) {
			this.update(rel);
		} else {
			this.insert(rel);
		}
	}
	
	/**
	 * @see {@link BaseRelationDao}{@link #delete(RelDiseaseSymptom))}
	 */
	@Override
	public void delete(RelDiseaseSymptom rel) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_DELETE);
			stmt.setInt(1, rel.getidDisease());
			stmt.setInt(2, rel.getidSymptom());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Console.log(e);
        }
	}
	
	/**
	 * @see {@link BaseRelationDao}{@link #list()}
	 */
	@Override
	public List<RelDiseaseSymptom> list() {
		List<RelDiseaseSymptom> list = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
			list = new ArrayList<RelDiseaseSymptom>();
			
			// Execute query
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_SELECT);
			resultSet = stmt.executeQuery();
			
			// Fill the list
			while (resultSet.next()) {
				list.add(new RelDiseaseSymptom(resultSet.getInt("idDisease"), 
						resultSet.getInt("idSymptom")));
			}

			stmt.close();
			resultSet.close();
		} catch (SQLException e) {
			Console.log(e);
        }
		
		return list;
	}
	
	/**
	 * @see {@link BaseRelationDao}{@link #find(int, int)}
	 */
	@Override
	public RelDiseaseSymptom find(int idBreed, int idPicture) {
		RelDiseaseSymptom rel = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_FIND);
			stmt.setInt(1, idBreed);
			stmt.setInt(1, idPicture);

			// Execute the query
			resultSet = stmt.executeQuery();
			
			if (resultSet.next()) {
				rel = new RelDiseaseSymptom(resultSet.getInt("idDisease"), 
						resultSet.getInt("idSymptom"));
			}
			
			stmt.close();
			resultSet.close();
		} catch(SQLException e) {
			Console.log(e);
		}
		
		return rel;
	}
	
	/**
	 * Inserts a new relation.
	 * 
	 * @param rel Relation to be inserted.
	 */
	private void insert(RelDiseaseSymptom rel) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_INSERT);
			stmt.setInt(1, rel.getidDisease());
			stmt.setInt(2, rel.getidSymptom());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Console.log(e);
        }
	}
	
	/**
	 * Updates a relation between dog breed and picture.
	 * 
	 * @param rel Relation to be updated.
	 */
	private void update(RelDiseaseSymptom rel) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_UPDATE);
			stmt.setInt(1, rel.getidDisease());
			stmt.setInt(2, rel.getidSymptom());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Erro de SQL: " + e.getMessage() );
            e.printStackTrace();
        }
	}

}
