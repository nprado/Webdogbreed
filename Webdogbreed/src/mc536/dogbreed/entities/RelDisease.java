package mc536.dogbreed.entities;

// essa eh uam tabela de relacionamento de doenca para sintoma
// talvez nao seja necessaria como objeto.

public class RelDisease {
	private int idDisease;
	private int idSymptom;
	
	public RelDisease(int idDisease, int idSymptom){
		this.idDisease = idDisease;
		this.idSymptom = idSymptom;
	}
	
	public int  getidDisease(){
		return this.idDisease;
	}
	
	public int getidSymptom(){
		return this.idSymptom;
	}
}

