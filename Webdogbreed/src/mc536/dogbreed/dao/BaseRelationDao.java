package mc536.dogbreed.dao;

import java.util.List;

/**
 * DAO base relation interface with basic CRUD operations.
 */
public interface BaseRelationDao<T> {
	
	/**
	 * Saves an entity.
	 * 
	 * @param entity Entity to be saved.
	 */
	public void save (T entity);
	
	/**
	 * Deletes and entity.
	 * 
	 * @param entity Entity to be deleted.
	 */
	public void delete (T entity);
	
	/**
	 * Returns a list with all rows of a table.
	 * 
	 * @return List of entities from rows of a table.
	 */
	public List<T> list();
	
	/**
	 * Finds a specific row of the table and returns it's entity.
	 *  
	 * @param idEntity1 First entity ID.
	 * @param idEntity2 Second entity ID.
	 * @return Entity with the given IDs or null, if no row matched on the database.
	 */
	public T find(int idEntity1, int idEntity2);
}
