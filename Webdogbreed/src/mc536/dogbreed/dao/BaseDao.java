package mc536.dogbreed.dao;

import java.util.Hashtable;
import java.util.List;


/**
 * DAO base inteface with basic CRUD operations.
 */
public interface BaseDao<T> {
	
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
	 * @param id Entity ID.
	 * @return Entity with the given ID or null, if no row matched on the database.
	 */
	public T find(int id);
	
	/**
	 * Returns a list of filtered rows of a table.
	 * 
	 * @param params Filter parameters.
	 * @return List of filtered rows.
	 */
	public List<T> customFilter(Hashtable<String, Object> params);

}
