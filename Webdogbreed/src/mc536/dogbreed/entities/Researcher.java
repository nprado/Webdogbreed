package mc536.dogbreed.entities;

public class Researcher {
	private int idResearcher;
	private String Name;
	private String Password;
	private int idBreed;

	public Researcher(int id, String name , String password, int idBreed) {
		this.idResearcher = id;
		this.Name = name;
		this.Password = password;
		this.idBreed = idBreed;
	}
	public int getidResearcher()
	{
		return idResearcher;
	}
	public String getName(){
		return Name;
	}
	
	public String getPassword(){
		return Password;
	}
	public int getidBreed(){
		return idBreed;
	}
	
	
	}