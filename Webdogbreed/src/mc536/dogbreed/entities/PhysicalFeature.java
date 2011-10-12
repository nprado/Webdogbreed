package mc536.dogbreed.entities;

public class PhysicalFeature {

	private int idPhysical;
    private int idBreed;
    private float averageHeight;
    private float averageLength;
    private float averageWeight;
    private float furThickness;
    private float furLength;
    private float tail;
    private float size;
    private float ears;
    private float snout;
	
    public PhysicalFeature(int idPhysical, int idBreed, float averageHeight, float averageLength, 
    						float averageWeight, float furThickness, float furLength, float tail,
    						float size, float ears, float snout){
    	this.idPhysical = idPhysical;
    	this.idBreed = idBreed;
    	this.averageHeight = averageHeight;
    	this.averageLength = averageLength;
    	this.averageWeight = averageWeight;
    	this.furThickness = furThickness;
    	this.furLength = furLength;
    	this.tail = tail;
    	this.size = size;
    	this.ears = ears;
    	this.snout = snout;
    	    	
    }
    
    public int getIdPhysical(){
    	return idPhysical;
    }
    
    public int getIdBreed(){
    	return idBreed;
    }
    
    public float getAverageHeight(){
    	return averageHeight;
    }
    
    public float getAverageLength(){
    	return averageLength;
    }
    
    public float getAverageWeigth(){
    	return averageWeight;
    }
    
    public float getFurThickness(){
    	return furThickness;
    }
    
    public float getFurLength(){
    	return furLength;
    }
	
    public float getTail(){
    	return tail;
    }
    public float getSize(){
    	return size;
    }
    
    public float getEars(){
    	return ears;
    }
    
    public float getSnout(){
    	return snout;
    }
    
}
