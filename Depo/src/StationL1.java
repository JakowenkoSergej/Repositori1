
public enum StationL1 {Station11 (6,24,1500,0),Station12 (6,24,1500,0), 
	                   Station13 (6,24,1500,0), Station14 (6,24,1500,0), 
	                   Revers (0,24,0,0), VDorozi (0,24,0,0) ;
	
	private int kilkistPasag;//moge odnochasno perebuvati na stanzii
	
	private double timeP, timeK;
	
	private int potochnaKP;//potochna kilkist pasagiriv 
	
	StationL1 (double tP,double tK, int kP, int pKP){
		timeP=tP; 
		timeK=tK;
		kilkistPasag=kP;
		potochnaKP=pKP;
	}

	StationL1 (){}//pustoj dlya ormlite
	
double getTimePochatok (){return timeP;}
double getTimeKinez (){return timeK;}
int getKilkistPasag(){return kilkistPasag;}
int getPotochnaKPas(){return potochnaKP;}

void setPotochnaKPas(int pkp){potochnaKP=pkp;}

}
