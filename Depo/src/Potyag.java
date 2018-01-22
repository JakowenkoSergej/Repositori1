import java.util.List;
import java.io.Serializable;
//import java.util.Deque;
import java.util.LinkedList;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "potyag")
public class Potyag implements Serializable {
	
	@DatabaseField(persisted = false)
	private static final long sVer = 1L;
	
	@DatabaseField(persisted = false)
	private List <Vagon> potyag = new LinkedList<Vagon> ();
	
	@DatabaseField(persisted = false)
	private Vagon vagon;
	
	@DatabaseField(generatedId = true) 
	private  int idPotyaga;
	
	@DatabaseField(index = true)
	private int nomerPotyga;
	
	@DatabaseField()
	private int maxNomerPotyaga;
	
	@DatabaseField()
	private int currentPosition=0;
	
	@DatabaseField()
	private int lichilnikSt=0;
	
	@DatabaseField()
    final  int dlinaPotyga=100;
   
	@DatabaseField(persisted = false)
    private Enum station; 
   
    @DatabaseField(persisted = false)
    private Mashinist mashinist;
    
    @DatabaseField(canBeNull = false)
    private String mashinistKey;
    
    @DatabaseField()
    private String depoKey;

    @DatabaseField()
    private String lineKey;
    
    @DatabaseField()
    private String stationString;
    
 Potyag (){}//pustoj dlya ormlite
 //konstruktor potyaga z vagoniv ne z depo
 Potyag ( int nomerP, Enum st){
	if (nomerP>0){
	for (int i=1; i<=5; i++){
		if ((1==i)||(5==i)){
			vagon=new Vagon (i+5*(nomerP-1),"VagonMashinist"); 
			vagon.setEmnistVagona(210);
		} 
		else {
			vagon=new Vagon (i+5*(nomerP-1),"VagonPasagir");
			vagon.setEmnistVagona(220);
		}
		vagon.setNomerPotyagaKey(nomerP);
		vagon.setNazvaDepoKey("DodanVPotyag");
		potyag.add(vagon);
	    }
	    nomerPotyga=nomerP;
	    station=st;
	    stationString=station.toString();
	    mashinist=new Mashinist ("Jon Dow","0000000000");
	    mashinistKey=mashinist.getPIB();
    }
	else System.out.println("kilkistVagoniv ta/abo nomerPotyga < 0");
}
 //konstruktor potyaga 5 vagoniv=MPPPM z vagoniv danogo depo
 Potyag(Depo d){
if ((d.getKilkistVMDepo()<2) &&
	 ((d.getKilkistVPDepo()+d.getKilkistVMDepo())<5))
	System.out.println("V depo VagonM<2 abo (VM+VP)<5");
	
else {  //poshuk maxNomerPotyaga v depo
		 for(Object ob:d.getDepo()){
			 if (ob instanceof Potyag){Potyag p=(Potyag)ob;
			 if (maxNomerPotyaga<p.nomerPotyga){maxNomerPotyaga=p.nomerPotyga;}
			 }}
		 nomerPotyga=maxNomerPotyaga+1;	
		 int lichilnik=0;
		 for(Object ob:d.getDepo()){
		     if (ob instanceof Vagon){ Vagon v=(Vagon)ob;
             if (v.getNazvaVagona().equalsIgnoreCase("VagonMashinist")) {
            	 if (0==lichilnik) {
            		 potyag.add(0,v); 
            		 v.setNomerPotyagaKey(nomerPotyga);
            		 v.setNazvaDepoKey("DodanVPotyag");	 
            	 }
                  	 lichilnik++;
 }}
		     if (1==lichilnik) break;   
}
		 for(Object ob:d.getDepo()){
		     if (ob instanceof Vagon){ Vagon v=(Vagon)ob;
		     if (v.getNazvaVagona().equalsIgnoreCase("VagonPasagir")&&
                ((lichilnik>0)&&(lichilnik<4))){
		    	 potyag.add(lichilnik,v); 
		    	 v.setNomerPotyagaKey(nomerPotyga);
		    	 v.setNazvaDepoKey("DodanVPotyag");
            	 lichilnik++;
}}
		     if (4==lichilnik) break;
}
potyag.forEach(v-> d.getDepo().remove(v));

for(Object ob:d.getDepo()){
    if (ob instanceof Vagon){ Vagon v=(Vagon)ob;
    if (v.getNazvaVagona().equalsIgnoreCase("VagonMashinist")) {
   	 if (4==lichilnik) {
   		 potyag.add(4,v); 
   		 v.setNomerPotyagaKey(nomerPotyga);
   		 v.setNazvaDepoKey("DodanVPotyag");	 
   	 }
         	 lichilnik++;
}}
    if (5==lichilnik) break;   
}
    d.getDepo().remove(potyag.get(4));
    station=d.getNazvaDepo(); 
    stationString=station.toString();
	d.setKilkistVPDepo(d.getKilkistVPDepo()-3);
	d.setKilkistVMDepo(d.getKilkistVMDepo()-2);
	mashinist=new Mashinist ("Jon Dow","0000000000");
	mashinistKey=mashinist.getPIB();
}}
 
//klonuvannya potyaga
 Potyag (Potyag pot){this (pot.getNomerPotyga(), pot.getStation());}

int getNomerPotyga(){return nomerPotyga;}
int getMaxNomerPotyaga(){return maxNomerPotyaga;}
Enum getStation (){return station;}
List <Vagon> getPotyg (){return potyag;}
Mashinist getMashinist(){return mashinist;}
int getCurentPosition (){return currentPosition;}
int getLichilnikSt (){return lichilnikSt;}
String getMashinistKey(){return mashinistKey;}
String getDepoKey(){return depoKey;}
String getLineKey(){return lineKey;}
String getStationString(){return stationString;}

void setStation(Enum st){station=st;}
void setMashinist(Mashinist m){mashinist=m; mashinistKey=m.getPIB();}
void setNomerPotyga (int nP){nomerPotyga=nP;}
void setCurrentPosition (int cP){currentPosition=cP;}
void setLichilnikSt (int lSt){lichilnikSt=lSt;}
//setMashinistKey vidsutnij - annotaziya
void setMashinistKey (String mk){mashinistKey=mk;}
void setDepoKey(String dk){depoKey=dk;}
void setLineKey(String lk){lineKey=lk;}
void setStationString(String ss){stationString=ss;}

public static void main(String[] args) {
	//1. Создайте классы Вагон, Поезд	
	Potyag potyag1= new Potyag(1,StationL1.Station11);
	Potyag potyag2= new Potyag(potyag1);
	Potyag potyag3= new Potyag(3,StationL3.Station31);
	
	if (("Revers"!=potyag1.getStation().name())&&
		("VDorozi"!=potyag1.getStation().name())){
	StationL1.valueOf(potyag1.getStation().name()).setPotochnaKPas(1000);
	}
	
	for(Vagon v:potyag1.potyag){System.out.println(potyag1.nomerPotyga
			+" "+v.getNazvaVagona()+ " "+v.getNomerVagona()+" "+potyag1.hashCode()+" "+potyag1.getStation());}
	System.out.println();
	for(Vagon v:potyag2.potyag){System.out.println(potyag2.nomerPotyga+
			" "+v.getNazvaVagona()+ " "+v.getNomerVagona()+" "+potyag2.hashCode()+" "+potyag1.getStation());}
	System.out.println();
	for(Vagon v:potyag3.potyag){System.out.println(potyag3.nomerPotyga+
			" "+v.getNazvaVagona()+ " "+v.getNomerVagona()+" "+potyag3.hashCode()+" "+potyag1.getStation());}
	System.out.println();
		
		potyag2.nomerPotyga=2;
		potyag2.setStation(StationL2.Station21);
		int n=1;
		for(Vagon v:potyag2.potyag) {v.setNomerVagona(5+n);n++;}
		
		for(Vagon v:potyag1.potyag){System.out.println(potyag1.nomerPotyga+" "+v.getNazvaVagona()+ " "+v.getNomerVagona());}
		System.out.println();
		for(Vagon v:potyag2.potyag){System.out.println(potyag2.nomerPotyga+" "+v.getNazvaVagona()+ " "+v.getNomerVagona());}
		System.out.println();
		for(Vagon v:potyag3.potyag){System.out.println(potyag3.nomerPotyga+" "+v.getNazvaVagona()+ " "+v.getNomerVagona());}
		System.out.println();
		
		System.out.println(potyag1.nomerPotyga+" "+potyag1.potyag.get(1).getNomerVagona()+ " "+potyag1.hashCode());
		System.out.println(potyag2.nomerPotyga+" "+potyag2.potyag.get(1).getNomerVagona()+ " "+potyag2.hashCode());
		System.out.println(potyag3.nomerPotyga+" "+potyag3.potyag.get(1).getNomerVagona()+ " "+potyag3.hashCode());
		
}
}


