import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "mashinist")
public class Mashinist implements Serializable{
	
	//@DatabaseField(version = true, columnName = "vers")
	@DatabaseField(persisted = false)
	private static final long sVer = 1L;
	
	@DatabaseField(id = true)
	private String pIB;
	
	@DatabaseField(canBeNull = false)
	private String iPN;

Mashinist(String pr,  String nom){
	super();
	pIB=pr;
	iPN=nom;
}
Mashinist(){}

String getPIB (){return pIB; }
String getIPN(){return iPN;}
void setPIB (String p){pIB=p;}
void setIPN (String n){iPN=n;}

long getSerialVersionUID(){return sVer;} 
}
