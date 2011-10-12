package mc536.dogbreed.entities;

public class RelDiseaseProphylaxy {
	private int idDisease;
	private int idProphylaxy;
	
	public RelDiseaseProphylaxy(int idDisease, int idProphylaxy){
		this.idDisease = idDisease;
		this.idProphylaxy = idProphylaxy;
	}
	
	public int  getidDisease(){
		return this.idDisease;
	}
	
	public int getidProphylaxy(){
		return this.idProphylaxy;
	}
}
