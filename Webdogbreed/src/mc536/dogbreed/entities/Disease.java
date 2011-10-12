package mc536.dogbreed.entities;

public class Disease {
	private int idDisease;
	private String description;
	
	public Disease(int id , String descrip){
		this.idDisease = id;
		this.description = descrip;
	}
	public int getidDisease(){
		return idDisease;
	}
	public String getDescription(){
		return description;
	}
}
