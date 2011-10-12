package mc536.dogbreed.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mc536.dogbreed.connection.ConnectionManager;
import mc536.dogbreed.entities.RelDogBreedPicture;
import mc536.dogbreed.log.Console;

/**
 * Manages relations between dog breed and pictures.
 */
public class RelBreedPictureDao implements BaseRelationDao<RelDogBreedPicture> {
	
	/**
	 * Statement to insert a row.
	 */
	private static final String SQL_INSERT = "INSERT INTO RelDogBreedPicture(idBreed,idPicture) " +
			"VALUES (?,?)";
	
	/**
	 * Statement to update a row.
	 */
	private static final String SQL_UPDATE = "UPDATE RelDogBreedPicture SET idPicture = ? WHERE " +
			"idBreed = ? AND idPicture = ?";
	
	/**
	 * Statement to delete a row.
	 */
	private static final String SQL_DELETE = "DELETE FROM relDogBreedPicture WHERE idBreed = ? AND " +
			"idPicture = ?";
	
	/**
	 * Statement to query for all rows.
	 */
	private static final String SQL_SELECT = "SELECT * FROM RelDogBreedPicture";
	
	/**
	 * Statement to query to query a specific row.
	 */
	private static final String SQL_FIND = "SELECT * FROM RelDogBreedPicture WHERE idBreed = ? " +
			"AND idPicture = ?";
	
	/**
	 * @see {@link BaseRelationDao}{@link #save(RelDogBreedPicture)}
	 */
	@Override
	public void save(RelDogBreedPicture rel) {
		if (rel.getIdBreed() > 0 || rel.getIdPicture() > 0) {
			this.update(rel);
		} else {
			this.insert(rel);
		}
	}
	
	/**
	 * @see {@link BaseRelationDao}{@link #delete(RelDogBreedPicture))}
	 */
	@Override
	public void delete(RelDogBreedPicture rel) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_DELETE);
			stmt.setInt(1, rel.getIdBreed());
			stmt.setInt(2, rel.getIdPicture());
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
	public List<RelDogBreedPicture> list() {
		List<RelDogBreedPicture> list = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
			list = new ArrayList<RelDogBreedPicture>();
			
			// Execute query
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_SELECT);
			resultSet = stmt.executeQuery();
			
			// Fill the list
			while (resultSet.next()) {
				list.add(new RelDogBreedPicture(resultSet.getInt("idBreed"), 
						resultSet.getInt("idPicture")));
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
	public RelDogBreedPicture find(int idBreed, int idPicture) {
		RelDogBreedPicture rel = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_FIND);
			stmt.setInt(1, idBreed);
			stmt.setInt(1, idPicture);

			// Execute the query
			resultSet = stmt.executeQuery();
			
			if (resultSet.next()) {
				rel = new RelDogBreedPicture(resultSet.getInt("idBreed"), resultSet.getInt("idPicture"));
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
	private void insert(RelDogBreedPicture rel) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_INSERT);
			stmt.setInt(1, rel.getIdBreed());
			stmt.setInt(2, rel.getIdPicture());
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
	private void update(RelDogBreedPicture rel) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_UPDATE);
			stmt.setInt(1, rel.getIdPicture());
			stmt.setInt(2, rel.getIdBreed());
			stmt.setInt(3, rel.getIdPicture());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Erro de SQL: " + e.getMessage() );
            e.printStackTrace();
        }
	}

}


