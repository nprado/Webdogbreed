package mc536.dogbreed.entities;

public class DogBreed {
	private int idDogBreed;
	private String name;
	private float litterSize ;
	private String recognition;
	private int lifeSpan;
	
	public DogBreed(int id , String name , float litterSize, String recog, int lifeSp ){
		this.idDogBreed = id;
		this.name = name;
		this.litterSize = litterSize;
		this.recognition = recog;
		this.lifeSpan = lifeSp;
		
	}
	public int getidDogBreed(){
		return idDogBreed;
	}
	public String getName(){
		return name;
	}
	public float getSize(){
		return litterSize;
	}
	public String getRecognition(){
		return recognition;
	}
	public int getLifeSpan(){
		return lifeSpan;
	}
}
