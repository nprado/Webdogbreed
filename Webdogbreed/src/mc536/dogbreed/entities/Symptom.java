package mc536.dogbreed.entities;

public class Symptom {
	private int idSymptom;
	private String name;
	private String description;
	
	public Symptom (int id, String name, String descrip){
		this.idSymptom = id;
		this.name = name;
		this.description = descrip;
		
	}
	public int getidSymptom(){
		return idSymptom;
	}
	public String getName(){
		return name;
	}
	public String getDescription(){
		return description;
	}
	
}
