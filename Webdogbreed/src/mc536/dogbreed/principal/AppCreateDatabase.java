package mc536.dogbreed.principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mc536.dogbreed.configuration.Configuration;
import mc536.dogbreed.dao.DogBreedDao;
import mc536.dogbreed.dao.PictureDao;
import mc536.dogbreed.dao.ResearcherDao;
import mc536.dogbreed.entities.DogBreed;
import mc536.dogbreed.entities.Picture;
import mc536.dogbreed.entities.Researcher;
import mc536.dogbreed.log.Console;

/**
 * Application to create the database used in the project.
 */
public class AppCreateDatabase {
	
	/**
	 * Called when the application is executed.
	 * 
	 * @param args Command line parameters.
	 */
	public static void main(String args[]) {
		System.out.println("Dog breed application started");
		AppCreateDatabase.createDatabase("org.gjt.mm.mysql.Driver", Configuration.CONNECTION_URI,
				Configuration.CONNECTION_USER, Configuration.CONNECTION_PASSWORD);
	}

	/**
	 * Creates the application database.
	 * 
	 * @param driver JDBC driver to connect to the database server.
	 * @param db Database URI.
	 * @param user Database user name.
	 * @param password Database user password.
	 */
	private static void createDatabase(String driver, String db, String user, String password) {
		Connection connection = null;
		ArrayList<String> statements = null;
		Statement stmt = null;
		
		try {
			// Initialize the database driver
			Class.forName(driver);

			// Establish a database connection
			connection = DriverManager.getConnection(db, user, password);
			
			// Retrieve a list of all statements to be executed on the database
			statements = (ArrayList<String>) AppCreateDatabase.getCreateDatabaseStatementList();

			// Create the database
			Console.log("Creating database");
			stmt = connection.createStatement();
			
			for (String currentStatement : statements) {
				stmt.executeUpdate(currentStatement);
			}
			
			stmt.close();
			connection.close();
			Console.log("Database successfully created");
			
			Console.log("Populating database");
			AppCreateDatabase.populateDatabase();
			
//			/* find picture teste */
//			JdbcPictureDao newPic = new JdbcPictureDao();
//			Picture pic = new Picture();
//			pic = newPic.find("teste2.jpg");
//			System.out.println("Encontrado : " + pic.getImage());
//
//			/* insere, delete , list DogBreed teste */
//			JdbcDogBreedDao novaRaca = new JdbcDogBreedDao();
//			novaRaca.save(new DogBreed(-1, "chowchow", 1, "none", 13));
//			novaRaca.save(new DogBreed(-2, "boxer", 1, "none", 13));
//			DogBreed pitbull = new DogBreed(-3, "pitbull", 2, "none", 13);
//			novaRaca.save(pitbull);
//			novaRaca.delete(pitbull);
//			DogBreed d = novaRaca.find("boxer");
//			if (d != null) {
//				System.out.println(d.getName() + " achado");
//			}
//
//			/* behavior do boxer teste */
//			JdbcBehavioralFeaturesDao novoComport = new JdbcBehavioralFeaturesDao();
//			BehavioralFeatures behaviorboxer = new BehavioralFeatures(-1, -2,
//					3, 5, 1, 2, 3, 4, 2, 3);
//			novoComport.save(behaviorboxer);
//
//			stmt.close();
//			connection.close();
		} catch (ClassNotFoundException erro) {
			System.out.println(erro.getMessage());
		} catch (SQLException erro) {
			System.out.println("Erro no SQL: " + erro.getMessage());
		}
	}
	
	/**
	 * Creates a list of statements to be executed when the database is created.
	 * 
	 * @return List of statements to be executed when the database is created.
	 */
	private static List<String> getCreateDatabaseStatementList() {
		final ArrayList<String> list = new ArrayList<String>();

		list.add("CREATE TABLE IF NOT EXISTS Picture ("
				+ "idPicture int(10) NOT NULL AUTO_INCREMENT, "
				+ "Image varchar(255) NOT NULL, "
				+ "PRIMARY KEY (idPicture))");
		list.add("CREATE TABLE IF NOT EXISTS Symptom ("
				+ "idSymptom INT(10) AUTO_INCREMENT, "
				+ "Name VARCHAR(50) NOT NULL, "
				+ "Description TEXT NOT NULL, " 
				+ "PRIMARY KEY (idSymptom))");
		list.add("CREATE TABLE IF NOT EXISTS Prophylaxy ("
				+ "idProphylaxy INT(10) AUTO_INCREMENT, "
				+ "Description TEXT NOT NULL, "
				+ "PRIMARY KEY (idProphylaxy))");
		list.add("CREATE TABLE IF NOT EXISTS Disease ("
				+ "idDesease INT(10) AUTO_INCREMENT, "
				+ "Description TEXT NOT NULL," 
				+ "PRIMARY KEY (idDesease))");
		list.add("CREATE TABLE IF NOT EXISTS RelDeseaseSympton ("
				+ "idDesease INT(10) NOT NULL, "
				+ "idSymptom INT(10) NOT NULL, "
				+ "PRIMARY KEY (idDesease, idSymptom))");
		list.add("ALTER TABLE RelDeseaseSympton "
				+ "ADD FOREIGN KEY (idDesease) "
				+ "REFERENCES Disease(idDesease) "
				+ "ON DELETE NO ACTION ON UPDATE NO ACTION");
		list.add("ALTER TABLE RelDeseaseSympton "
				+ "ADD FOREIGN KEY (idSymptom) "
				+ "REFERENCES Symptom(idSymptom) "
				+ "ON DELETE NO ACTION ON UPDATE NO ACTION");
		list.add("CREATE TABLE IF NOT EXISTS RelDiseaseProphylaxy ("
				+ "idDesease INT(10) NOT NULL, "
				+ "idProphylaxy INT(10) NOT NULL, "
				+ "PRIMARY KEY (idDesease, idProphylaxy))");
		list.add("ALTER TABLE RelDiseaseProphylaxy "
				+ "ADD FOREIGN KEY (idDesease) "
				+ "REFERENCES Disease(idDesease) "
				+ "ON DELETE NO ACTION ON UPDATE NO ACTION");
		list.add("ALTER TABLE RelDiseaseProphylaxy "
				+ "ADD FOREIGN KEY (idProphylaxy) "
				+ "REFERENCES Prophylaxy(idProphylaxy) "
				+ "ON DELETE NO ACTION ON UPDATE NO ACTION");
		list.add("CREATE TABLE IF NOT EXISTS Dog_Breed ("
				+ "idBreed INT(10) AUTO_INCREMENT, "
				+ "Name VARCHAR(50) NOT NULL UNIQUE, "
				+ "Litter_Size NUMERIC(6,2) NOT NULL, "
				+ "Recognition VARCHAR(50) NOT NULL, "
				+ "Life_Span INT(10) NOT NULL, " 
				+ "PRIMARY KEY (idBreed))");
		list.add("CREATE TABLE IF NOT EXISTS Researcher ("
				+ "idResearcher INT(10) AUTO_INCREMENT, "
				+ "Name VARCHAR(50) UNIQUE NOT NULL, "
				+ "Password VARCHAR(50) NOT NULL, " 
				+ "idBreed INT(10), "
				+ "PRIMARY KEY (idResearcher, idBreed))");
		list.add("ALTER TABLE Researcher "
				+ "ADD FOREIGN KEY (idBreed) "
				+ "REFERENCES Dog_Breed(idBreed) "
				+ "ON DELETE NO ACTION ON UPDATE NO ACTION");
		list.add("CREATE TABLE IF NOT EXISTS Physical_Features ("
				+ "idPhysycal_Feature INT(10) AUTO_INCREMENT, "
				+ "idBreed INT(10) NOT NULL, "
				+ "Average_Height NUMERIC(6, 2) NOT NULL, "
				+ "Average_Length NUMERIC(6, 2) NOT NULL, "
				+ "Average_Weight NUMERIC(6, 2) NOT NULL, "
				+ "Fur_Thickness NUMERIC(6, 2) NOT NULL, "
				+ "Fur_Length NUMERIC(6, 2) NOT NULL, "
				+ "Tail NUMERIC(6, 2) NOT NULL, "
				+ "Size NUMERIC(6, 2) NOT NULL, "
				+ "Ears NUMERIC(6, 2) NOT NULL, "
				+ "Snout NUMERIC(6, 2) NOT NULL, "
				+ "PRIMARY KEY (idPhysycal_Feature, idBreed))");
		list.add("ALTER TABLE Physical_Features "
				+ "ADD FOREIGN KEY (idBreed) "
				+ "REFERENCES Dog_Breed(idBreed) "
				+ "ON DELETE NO ACTION ON UPDATE NO ACTION");
		list.add("CREATE TABLE IF NOT EXISTS Behavioral_Features ("
				+ "idBehavioral_Feature INT(10) AUTO_INCREMENT, "
				+ "idBreed INT(10)  NOT NULL, "
				+ "Good_With_Children TINYINT NOT NULL DEFAULT 0, "
				+ "Sociability TINYINT NOT NULL DEFAULT 0, "
				+ "Agressiveness TINYINT NOT NULL DEFAULT 0, "
				+ "Temperature_Adaptation TINYINT NOT NULL DEFAULT 0, "
				+ "Trainability TINYINT NOT NULL DEFAULT 0, "
				+ "Exercise TINYINT NOT NULL DEFAULT 0, "
				+ "Grooming_Needs TINYINT NOT NULL DEFAULT 0, "
				+ "Noise TINYINT NOT NULL DEFAULT 0, "
				+ "PRIMARY KEY (idBehavioral_Feature, idBreed))");
		list.add("ALTER TABLE Behavioral_Features "
				+ "ADD FOREIGN KEY (idBreed) "
				+ "REFERENCES Dog_Breed(idBreed) "
				+ "ON DELETE NO ACTION ON UPDATE NO ACTION");
		list.add("CREATE TABLE IF NOT EXISTS RelDogBreedPicture ("
				+ "idBreed INT(10) NOT NULL, "
				+ "idPicture INT(10) NOT NULL, "
				+ "PRIMARY KEY (idBreed, idPicture))");
		list.add("ALTER TABLE RelDogBreedPicture "
				+ "ADD FOREIGN KEY (idBreed) "
				+ "REFERENCES Dog_Breed(idBreed) "
				+ "ON DELETE NO ACTION ON UPDATE NO ACTION");
		list.add("ALTER TABLE RelDogBreedPicture "
				+ "ADD FOREIGN KEY (idPicture) "
				+ "REFERENCES Picture(idPicture) "
				+ "ON DELETE NO ACTION ON UPDATE NO ACTION");
		list.add("CREATE TABLE IF NOT EXISTS RelDogBreedDesease ("
				+ "idBreed INT(10) NOT NULL, "
				+ "idDesease INT(10) NOT NULL, "
				+ "PRIMARY KEY (idBreed, idDesease))");
		list.add("ALTER TABLE RelDogBreedDesease "
				+ "ADD FOREIGN KEY (idBreed) "
				+ "REFERENCES Dog_Breed(idBreed) "
				+ "ON DELETE NO ACTION ON UPDATE NO ACTION");
		list.add("ALTER TABLE RelDogBreedDesease "
				+ "ADD FOREIGN KEY (idDesease) "
				+ "REFERENCES Disease(idDesease) "
				+ "ON DELETE NO ACTION ON UPDATE NO ACTION");
		
		return list;
	}
	
	/**
	 * Populates database with random/useless data.
	 */
	private static void populateDatabase() {
		Console.log("Dog breeds");
		DogBreedDao dogBreedDao = new DogBreedDao();
		dogBreedDao.save(new DogBreed(-1, "Pastor alemão", 1.2f, "abc", 15));
		dogBreedDao.save(new DogBreed(-1, "Poodle", 0.3f, "def", 7));
		dogBreedDao.save(new DogBreed(-1, "Bulldog", 2.2f, "abc", 8));
		dogBreedDao.save(new DogBreed(-1, "Vira lata", 3.2f, "abc", 70));
		
		Console.log("Researchers");
		ResearcherDao researcherDao = new ResearcherDao();
		researcherDao.save(new Researcher(-1, "admin", "admin", 1));
	}

}
