package mc536.dogbreed.entities;

public class Prophylaxy {
	private int idProphylaxy;
	private String description;
	
	public Prophylaxy(int id, String descrip){
		this.idProphylaxy = id;
		this.description = descrip;
	}
	public int getidProphylaxy(){
		return idProphylaxy;
	}
	public String getDescription(){
		return description;
	}
}
