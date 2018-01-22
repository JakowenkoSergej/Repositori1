
public enum StationL3 {Station31 (6,24,1500,0),Station32 (6,24,1500,0), 
	                   Station33 (6,24,1500,0), Station34 (6,24,1500,0), 
	                   Rozvorot3(0,24,0,0), VDorozi (0,24,0,0);
	private int kilkistPasag;//moge odnochasno perebuvati na stanzii
	private double timeP, timeK;
	private int potochnaKP;//potochna kilkist pasagiriv 
	
	StationL3 (double tP,double tK, int kP, int pkp){
		timeP=tP;
		timeK=tK;
		kilkistPasag=kP;
		potochnaKP=pkp;
	}
	double getTimePochatok (){return timeP;}
	double getTimeKinez (){return timeK;}
	int getKilkistPasag(){return kilkistPasag;}
    int getPotochnaKPas(){return potochnaKP;}
	
	void setPotochnaKPas (int pkp){potochnaKP=pkp;}
}
