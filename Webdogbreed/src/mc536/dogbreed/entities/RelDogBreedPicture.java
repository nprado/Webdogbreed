package mc536.dogbreed.entities;

public class RelDogBreedPicture {
	
	private int idBreed;
    private int idPicture;
    
    public RelDogBreedPicture(int idBreed, int idPicture){
    	this.idBreed = idBreed;
    	this.idPicture = idPicture;
    }
    
    public int getIdBreed(){
    	return idBreed;    	
    }
    
    public int getIdPicture(){
    	return idPicture;
    }

}
