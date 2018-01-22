
public enum StationL2 {Station21 (6,24,1500,0),Station22 (6,24,1500,0), 
	                   Station23 (6,24,1500,0), Station24 (6,24,1500,0), 
	                   Revers(0,24,0,0), VDorozi (0,24,0,0);
	private int kilkistPasag;//moge odnochasno perebuvati na stanzii
	private double timeP, timeK;
	private int potochnaKP;//potochna kilkist pasagiriv 
	
	StationL2 (double tP,double tK, int kP, int pkp){
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
