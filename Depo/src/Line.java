import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 porydok robotu:
1. Stvoriti liniu: Line redLine1=new Line(1); dozvoleno tilki 1,2,3 Line
2. Priznachin maschinistiv na potygi: potyag1.setMashinist(new Mashinist("Ivanenko", "1111111111"));
   abo: Mashinist petrenko=new Mashinist("Petrenko", "5555555555"); potyag1.setMashinist(petrenko);
   bez mashinista potyag ne vijde na liniu
3. Vipustiti potyag (potyagi) na liniu: redLine1.vipuskNaLiniu (potyag1, potyag2...);
*/
public class Line {
	private String nazvaLinii;
	private int line_T [];//masiv elementiv tuda, 1 element=100 m = dovgina potyaga
	private int line_N [];//masiv elementiv nazad, 1 element=100 m = dovgina potyaga
	private ArrayList <Potyag> potyagNaLinii;//masiv potyagiv na linii
	private int zagalnaL, tmp, distanziya, firstTime, period, prapor, 
		        praporSt_T,praporSt_N, lichilnikTimer;
	private int []indexSt_T;//index Stanzij v masive elementov tuda
	private int []indexSt_N;//index Stanzij v masive elementov nazad
	private Timer timer;
	SchemaMetro schemaMetro;
	ArrayList <Potyag> getPotyagNaLinii (){return potyagNaLinii;}
	String getNazvaLinii (){return nazvaLinii;}
	int []  getline_T (){return line_T; }
	int []  getline_N (){return line_N; }
	Timer getTimer (){return timer;}
	void setLichilnikTimer(int lt){lichilnikTimer=lt;}
	
	Line (SchemaMetro schemaMetro) {this.schemaMetro = schemaMetro;}
	
Line (int nomerLinii){
if ((nomerLinii>=1)&&(nomerLinii<=3)){
	switch (nomerLinii){
	case 1:{int kSt=VidstanMigSt1.values().length;
		    indexSt_T=new int[kSt];indexSt_N=new int [kSt];
		    nazvaLinii="RedLine1_T";
			for(VidstanMigSt1 l:VidstanMigSt1.values()){
			    zagalnaL+=l.getVidstanMigStanziami();
			    indexSt_T[tmp]=(zagalnaL/100)+tmp+1;
				tmp++;
				}
			//vidlik vid Station11 index 0;   
			for (int i=indexSt_T.length-2;i>=0;i--) {indexSt_T[i+1]=indexSt_T[i];}	    
			    indexSt_T[0]=0;
			    zagalnaL=((zagalnaL)/100)+kSt;
			    indexSt_N[0]=0;
	} break;
	case 2:{int kSt=VidstanMigSt2.values().length;
		        indexSt_T=new int[kSt];indexSt_N=new int [kSt];
	            nazvaLinii="BlueLine2_T";
	            for(VidstanMigSt2 l:VidstanMigSt2.values()){
	        	   zagalnaL+=l.getVidstanMigStanziami();
	        	   indexSt_T[tmp]=(zagalnaL/100)+tmp+1;
				   tmp++;
				   }
	          //vidlik vid Station21 index 0;   
				for (int i=indexSt_T.length-2;i>=0;i--) {indexSt_T[i+1]=indexSt_T[i];}	    
				    indexSt_T[0]=0;
	                zagalnaL=((zagalnaL)/100)+kSt;
    } break;
	case 3:{int kSt=VidstanMigSt3.values().length;
		        indexSt_T=new int[kSt];indexSt_N=new int [kSt];
	            nazvaLinii="GreenLine3_T";
	            for(VidstanMigSt3 l:VidstanMigSt3.values()){
	            	zagalnaL+=l.getVidstanMigStanziami();
	            	indexSt_T[tmp]=(zagalnaL/100)+tmp+1;
					tmp++;
	            }
	          //vidlik vid Station31 index 0;   
				for (int i=indexSt_T.length-2;i>=0;i--){indexSt_T[i+1]=indexSt_T[i];}	    
				    indexSt_T[0]=0;
	                zagalnaL=((zagalnaL)/100)+kSt;
	 } break;
		}
	    //System.out.println(zagalnaL);	
	    line_T=new int[zagalnaL];
		line_N=new int[zagalnaL];
		potyagNaLinii=new ArrayList <>(zagalnaL-1);
		//perevertau index Station v line nazad 
		int kSt=indexSt_T.length;
		indexSt_N [0]=0;
		if (kSt>0) for (int i=1; i<kSt; i++) {indexSt_N [i]=indexSt_N [i-1]+(indexSt_T[kSt-i]-indexSt_T[kSt-i-1]);}		
		//System.out.println(Arrays.toString(indexSt_T));
		//System.out.println(Arrays.toString(indexSt_N));
}
else System.out.println("3 <= Nomer Line <= 1");	
}

void vipuskNaLiniu (Potyag ... potyag){

if ((potyag.length>0)&&((potyag.length+potyagNaLinii.size())<zagalnaL-1)){

	for (int i=0;i<potyag.length;i++){	
		//perevirka nayavnosti mashinista
	if ((potyag[i].getMashinist().getPIB()!="Jon Dow") &&
		(potyag[i].getMashinist().getPIB()!=null)      &&
		(!potyag[i].getMashinist().getPIB().isEmpty())){
//perevirka nayavnosti 5 vagoniv MPPPM abo MXXXM
	    if((5==potyag[i].getPotyg().size())&&
	       (potyag[i].getPotyg().get(0).getNazvaVagona().equals("VagonMashinist")) &&
	       (potyag[i].getPotyg().get(4).getNazvaVagona().equals("VagonMashinist"))){
//perevirka nayavnosti RIZNICH machinistiv v potyagax na linii
		//if(0==potyagNaLinii.size()){potyagNaLinii.add(potyag[i]); prapor=1;}
	   // else {if (unikalMashinist (potyag[i].getMashinist().getPIB(), potyagNaLinii)){
	   //                     potyagNaLinii.add(potyag[i]); prapor=1;
	    	//                }
	    //       else     System.out.println("Povtor Mashinista v potyazi N"+potyag[i].getNomerPotyga());
	    	// }
	   
	    potyagNaLinii.add(potyag[i]);
		prapor=1;
	    }
	    else System.out.println("Vidsutnij 5 vagoniv MPPPM v potyazi N"+potyag[i].getNomerPotyga());
	}
	else {System.out.println("Vidsutnij mashinist v potyazi N"+potyag[i].getNomerPotyga());}
	}	
//distanziya mig potygami, elementiv, 1 element = 100 m.
if (potyagNaLinii.size()>0) distanziya=(int)((2*zagalnaL)/(potyagNaLinii.size()));	
//viznachau startPosition potyagiv na linii z uraxuvannyam distanzii,
for (int i=0; i<potyagNaLinii.size(); i++){
	int dst=(potyagNaLinii.size()-1-i)*distanziya;
	potyagNaLinii.get(i).setCurrentPosition(dst);
//nomeri potyagiv v masiv Line_T abo Line_N dlya ruchu 
	if (dst<zagalnaL) line_T[dst]=potyagNaLinii.get(i).getNomerPotyga(); 
	else              line_N[dst-zagalnaL]=potyagNaLinii.get(i).getNomerPotyga();
}
//start  ruchu potyagiv 144km/h=>2,5s=100m; 60km/h=>6,0s=100m; 0,5s-otladka	
    if (1==prapor){
    	 firstTime=0;
	     period=500;//6000, 0,5s-dlya otladki
	     timer=new Timer("Timer1");
	     timer.schedule(new ZapuskPotyaga(0, distanziya),firstTime, period);
	     }
}
else {System.out.println("Vidsutnij potyag abo kilkist potygiv > zagalna dovgina linii");}
}	

class ZapuskPotyaga extends TimerTask {
	int currentPosition, distanziya;	
ZapuskPotyaga(int cP, int d){
		currentPosition=cP;
		distanziya=d;
}
public void run() {
                   
if(lichilnikTimer>120){timer.cancel();lichilnikTimer=0;}
else{ System.out.println("T "+Arrays.toString(line_T)); System.out.println("N "+Arrays.toString(line_N)); 
   
for (int k=0; k<line_T.length; k++){ 
	  if (0!=line_T[k]) SchemaMetro.printRedLine1_T(k, line_T[k]);
	  else              SchemaMetro.deleteRedLine1_T(k);
	  if (0!=line_N[k]) SchemaMetro.printRedLine1_N(k, line_N[k]);
	  else              SchemaMetro.deleteRedLine1_N(k);	  
  }

	//obraxunok line_T 
    for (int i=line_T.length-2; i>=0; i--) {
		if ((0!=line_T[i])&&(0==line_T[i+1])) {
			int tmp3=0;//viznachau nomer potyaga na elementi line_T[i]
			for(Potyag p: potyagNaLinii){
				if(line_T[i]==p.getNomerPotyga()) break;
				tmp3++;
				}
			
			for (int j=0; j<indexSt_T.length; j++){
			     if(i==indexSt_T[j]){
			     praporSt_T=1; 
			     potyagNaLinii.get(tmp3).setLichilnikSt(potyagNaLinii.get(tmp3).getLichilnikSt()+1);
			     break;
}
			     else praporSt_T=0;
}				
			// 8 kilkist chasu zupinki potyaga na statzii, 8x6s(6000 timer)=48s
			if ((potyagNaLinii.get(tmp3).getLichilnikSt()>=8) ||
				(0==praporSt_T)){
				line_T[i+1]=line_T[i];
				line_T[i]=0;
				potyagNaLinii.get(tmp3).setCurrentPosition(potyagNaLinii.get(tmp3).getCurentPosition()+1);
}
			if (potyagNaLinii.get(tmp3).getLichilnikSt()>=8) potyagNaLinii.get(tmp3).setLichilnikSt(0);
}
}  //potyag dosyag ostanjogo elementa line_T -> perexid na line_N 
    if((0!=line_T [line_T.length-1]) &&
   		 (0==line_N[0])) {
   	    line_N[0]=line_T [line_T.length-1];
   	    line_T [line_T.length-1]=0;
   }       
    //obraxunok line_N 
    for (int i=line_N.length-2; i>=0; i--) {
		if ((0!=line_N[i])&&(0==line_N[i+1])) {
			int tmp4=0;
			for(Potyag p: potyagNaLinii){
				if(line_N[i]==p.getNomerPotyga()) break;
				tmp4++;
				}
			
			for (int j=0; j<indexSt_N.length; j++){
			     if(i==indexSt_N[j]){
			     praporSt_N=1; 
			     potyagNaLinii.get(tmp4).setLichilnikSt(potyagNaLinii.get(tmp4).getLichilnikSt()+1);
			     break;
}
			     else praporSt_N=0;
}				
			if ((potyagNaLinii.get(tmp4).getLichilnikSt()>=5) ||
				(0==praporSt_N)){
				line_N[i+1]=line_N[i];
				line_N[i]=0;
				potyagNaLinii.get(tmp4).setCurrentPosition(potyagNaLinii.get(tmp4).getCurentPosition()+1);
}
			if (potyagNaLinii.get(tmp4).getLichilnikSt()>=5) potyagNaLinii.get(tmp4).setLichilnikSt(0);
}
}	
  //potyag dosyag ostanjogo elementa line_N -> perexid na line_T, currentPosition=0, LichilnikSt=0; 
    if((0!=line_N [line_N.length-1]) &&
   	   (0==line_T[0])) {
   	    line_T[0]=line_N [line_N.length-1];
   	    line_N [line_N.length-1]=0;
   	    potyagNaLinii.forEach(p-> {
   	    	if(line_T[0]==p.getNomerPotyga()) {p.setCurrentPosition(0);p.setLichilnikSt(0);}});
} 
    lichilnikTimer++;   
}
}}
boolean unikalMashinist (String pib, ArrayList <Potyag> pnl){
	for(Potyag p: pnl){if (pib.equals(p.getMashinist().getPIB())) return false;}
	return true;	
}

}
//Arrays.sort(zPlata);