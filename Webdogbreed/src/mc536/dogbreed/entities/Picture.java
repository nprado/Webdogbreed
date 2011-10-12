package mc536.dogbreed.entities;

public class Picture {
	private int idPicture;
	private String image ;
	
	public Picture(){
		this.idPicture = 0;
		this.image = "";
	}
	public Picture(int id, String img){
		this.idPicture = id;
		this.image = img;
	}
	public int getidPicture(){
		return idPicture;
	}
	public String getImage(){
		return image;
	}
	public void setidPicture(int id ){
		idPicture = id;
	}
	public void setImage(String img){
		image = img;
	}
}
