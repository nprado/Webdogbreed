package mc536.dogbreed.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mc536.dogbreed.connection.ConnectionManager;
import mc536.dogbreed.entities.RelDiseaseProphylaxy;
import mc536.dogbreed.log.Console;

/**
 * Manages relation between disease and prophylaxy.
 */
public class RelDiseaseProphylaxyDao implements BaseRelationDao<RelDiseaseProphylaxy> {
	
	/**
	 * Statement to insert a row.
	 */
	private static final String SQL_INSERT = "INSERT INTO RelDiseaseProphylaxy(idDisease, " +
			"idProphylaxy)  VALUES (?, ?)";
	
	/**
	 * Statement to update a row.
	 */
	private static final String SQL_UPDATE = "UPDATE RelDiseaseProphylaxy SET idProphylaxy = ? " +
			"WHERE idDisease = ? AND IdProphylaxy = ?";
	
	/**
	 * Statement to delete a row.
	 */
	private static final String SQL_DELETE = "DELETE FROM RelDiseaseProphylaxy WHERE idDisease = " +
			"? AND idProphylaxy = ?";
	
	/**
	 * Statement to query for all rows.
	 */
	private static final String SQL_SELECT = "SELECT * FROM RelDiseaseProphylaxy";
	
	/**
	 * Statement to query to query a specific row.
	 */
	private static final String SQL_FIND = "SELECT * FROM RelDiseaseProphylaxy WHERE idDisease = " +
			"? AND idProphylaxy = ?";
	
	/**
	 * @see {@link BaseRelationDao}{@link #save(RelDiseaseProphylaxy)}
	 */
	@Override
	public void save(RelDiseaseProphylaxy entity) {
		if (entity.getidDisease() > 0 || entity.getidProphylaxy() > 0) {
			this.update(entity);
		} else {
			this.insert(entity);
		}
	}
	
	/**
	 * @see {@link BaseRelationDao}{@link #delete(RelDiseaseProphylaxy))}
	 */
	@Override
	public void delete(RelDiseaseProphylaxy rel) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_DELETE);
			stmt.setInt(1, rel.getidDisease());
			stmt.setInt(2, rel.getidProphylaxy());
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
	public List<RelDiseaseProphylaxy> list() {
		List<RelDiseaseProphylaxy> list = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
			list = new ArrayList<RelDiseaseProphylaxy>();
			
			// Execute query
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_SELECT);
			result = stmt.executeQuery();
			
			// Fill the list
			while (result.next()) {
				list.add(new RelDiseaseProphylaxy(result.getInt("idDisease"), 
						result.getInt("idProphylaxy")));
			}

			stmt.close();
			result.close();
		} catch (SQLException e) {
			Console.log(e);
        }
		
		return list;
	}
	
	/**
	 * @see {@link BaseRelationDao}{@link #find(int, int)}
	 */
	@Override
	public RelDiseaseProphylaxy find(int idBreed, int idPicture) {
		RelDiseaseProphylaxy rel = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_FIND);
			stmt.setInt(1, idBreed);
			stmt.setInt(1, idPicture);

			// Execute the query
			resultSet = stmt.executeQuery();
			
			if (resultSet.next()) {
				rel = new RelDiseaseProphylaxy(resultSet.getInt("idDisease"), 
						resultSet.getInt("idProphylaxy"));
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
	private void insert(RelDiseaseProphylaxy rel) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_INSERT);
			stmt.setInt(1, rel.getidDisease());
			stmt.setInt(2, rel.getidProphylaxy());
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
	private void update(RelDiseaseProphylaxy rel) {
		PreparedStatement stmt = null;
		
		try {
			stmt = ConnectionManager.getConnection().prepareStatement(SQL_UPDATE);
			stmt.setInt(1, rel.getidDisease());
			stmt.setInt(2, rel.getidProphylaxy());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Erro de SQL: " + e.getMessage() );
            e.printStackTrace();
        }
	}
	
}
