package mc536.dogbreed.entities;

public class BehavioralFeature {
	private int idBehavioralFeature;
	private int idBreed;
	private int goodWithChildren;
	private int sociability;
	private int agressiveness;
	private int temperatureAdaptation;
	private int trainability;
	private int exercise;
	private int groomingNeeds;
	private int noise;
	
	public BehavioralFeature(int idBehavioralFeature, int idBreed, int goodWithChildren, int sociability,
							  int agressiveness, int temperatureAdaptation, int trainability, int exercise,
							  int groomingNeeds, int noise){
		this.idBehavioralFeature = idBehavioralFeature;
		this.idBreed = idBreed;
		this.goodWithChildren = goodWithChildren;
		this.sociability = sociability;
		this.agressiveness = agressiveness;
		this.temperatureAdaptation = temperatureAdaptation;
		this.trainability = trainability;
		this.exercise = exercise;
		this.groomingNeeds = groomingNeeds;
		this.noise = noise;
		
	}
	
	public int getIdBehavioralFeature(){
		return idBehavioralFeature;
	}
	
	public int getIdBreed(){
		return idBreed;
	}
	
	public int getGoodWithChildren(){
		return goodWithChildren;
	}
	
	public int getSociability(){
		return sociability;
	}
	
	public int getAgressiveness(){
		return agressiveness;
	}
	
	public int getTemperatureAdaptation(){
		return temperatureAdaptation;
	}
	
	public int getTrainability(){
		return trainability;
		
	}
	
	public int getExercise(){
		return exercise;
	}
	
	public int getGroomingNeeds(){
		return groomingNeeds;
	}
	
	public int getNoise(){
		return noise;
	}
}
