import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import javax.swing.JButton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MetroRuch {
SchemaMetro schemaMetro;
Line line;
MetroRuch (SchemaMetro schemaMetro) {this.schemaMetro = schemaMetro;}

Line  getline(){return line;}

Line metroRuch(){
	Depo depo1=new Depo (18,12, NazvaDepo.DepoDarniza);
	//Stvoruu potygi z vagoni ne depo				
	 Potyag potyag1= new Potyag(1,NazvaDepo.DepoDarniza); 		 
	 Potyag potyag2= new Potyag(2,NazvaDepo.DepoGeroivDnipra);
	 Potyag potyag3= new Potyag(3,NazvaDepo.DepoDarniza);
//priznachiv Mashinistiv
	 potyag1.setMashinist(new Mashinist("Ivanenko", "1111111111"));
	 potyag2.setMashinist(new Mashinist("Petrenko", "2222222222"));
	 potyag3.setMashinist(new Mashinist("Kozak", "4444444444"));
	 Mashinist xXX=new Mashinist("XXX", "5555555555");
//Dodau potyagi v depo
	 depo1.dodatVdepo(potyag1);
	 depo1.dodatVdepo(potyag2);
	 depo1.dodatVdepo(potyag3);
//Stvoruu potygi z vagoni depo
	 Potyag potyag4= new Potyag(depo1);potyag4.setMashinist(new Mashinist("Sidorenko", "3333333333"));
	 depo1.dodatVdepo(potyag4);
	 Potyag potyag5= new Potyag(depo1);potyag5.setMashinist(xXX);
	 depo1.dodatVdepo(potyag5);
	 Potyag potyag6= new Potyag(depo1);potyag6.setMashinist(xXX);
	 depo1.dodatVdepo(potyag6);
	 Potyag potyag7= new Potyag(depo1);potyag7.setMashinist(xXX);
	 depo1.dodatVdepo(potyag7);
	 Potyag potyag8= new Potyag(depo1);
	 potyag8.setMashinist(xXX);
	 //Dodau potyag v depo
	 depo1.dodatVdepo(potyag8);
	 
	 Line redLine1=new Line(1);
	 redLine1.vipuskNaLiniu(potyag1, potyag2, potyag3, potyag4, 
			                potyag5, potyag6, potyag7, potyag8 );
	 Depo depo2=new Depo (18,12, NazvaDepo.DepoGeroivDnipra);
	 Potyag potyag9= new Potyag(depo2);potyag9.setMashinist(new Mashinist("Titarenko", "1234567890"));
	 Potyag potyag10= new Potyag(depo2);potyag10.setMashinist(new Mashinist("Vovk", "1098765432"));
	 
	 return redLine1;
}
void metroStop(Line ln){
	ln.getTimer().cancel();}
}