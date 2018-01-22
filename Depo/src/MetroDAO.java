import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.TableUtils;

public class MetroDAO {
	static ConnectionSource connectionSource;

	private static final String login = "user1";
	private static final String password = "pass1";
	
	static String databaseURL = "jdbc:mysql://localhost/classicmodels?" + 
	                            "user=" + 
			                     login + 
			                     "&password=" + 
			                     password;
	
	static Dao<Vagon, Integer> vagonDao;
	static Dao<Mashinist, String> mashinistDao;
	static Dao<Potyag, Integer> potyagDao;
	static Dao<Depo, Integer> depoDao;
	
	static CloseableIterator<String[]> closeableIterator;
	
public static void main(String[] args) {
			
try {connectionSource = new JdbcConnectionSource(databaseURL);
	 vagonDao = DaoManager.createDao(connectionSource, Vagon.class);
	 mashinistDao = DaoManager.createDao(connectionSource, Mashinist.class);
	 potyagDao = DaoManager.createDao(connectionSource, Potyag.class);
	 depoDao = DaoManager.createDao(connectionSource, Depo.class);	
//vidalennya tabliz 
	 TableUtils.dropTable(connectionSource, Vagon.class,true);
	 TableUtils.dropTable(connectionSource, Mashinist.class,true);
	 TableUtils.dropTable(connectionSource, Potyag.class,true);
	 TableUtils.dropTable(connectionSource, Depo.class,true);
//stvorennya tabliz 
	 TableUtils.createTable(connectionSource, Vagon.class);
	 TableUtils.createTable(connectionSource, Mashinist.class);
	 TableUtils.createTable(connectionSource, Potyag.class);
	 TableUtils.createTable(connectionSource, Depo.class);
		 
//2.Stvoruu Depo -stvoruu vagoni 6-VagonPas, 4-VagonMach.
	 Depo depo1=new Depo (6,4, NazvaDepo.DepoDarniza);
//Stvoruu tablizu vagoniv			
	 depo1.getDepo().forEach(ob->{if (ob instanceof Vagon){Vagon v=(Vagon)ob;
				try {vagonDao.createIfNotExists(v);}
				catch (SQLException e) {e.printStackTrace();} 
				}});
//Stvoruu potygi z vagoni ne depo				
	 Potyag potyag1= new Potyag(1,NazvaDepo.DepoDarniza); 
	 potyag1.getPotyg().forEach(v->{
		 try {vagonDao.createIfNotExists(v);}
		 catch (SQLException e) {e.printStackTrace();}
	 });
	 
	 Potyag potyag2= new Potyag(2,NazvaDepo.DepoGeroivDnipra); 
	 potyag2.getPotyg().forEach(v->{
		 try {vagonDao.createIfNotExists(v);}
		 catch (SQLException e) {e.printStackTrace();}
	 });
	 //vagonDao.create(potyag2.getPotyg());
	 Potyag potyag3= new Potyag(3,NazvaDepo.DepoDarniza);
	 potyag3.getPotyg().forEach(v->{
		 try {vagonDao.createIfNotExists(v);}
		 catch (SQLException e) {e.printStackTrace();}
	 });
	 //vagonDao.create(potyag3.getPotyg());
//priznachiv Mashinistiv
	 potyag1.setMashinist(new Mashinist("Ivanenko", "1111111111"));
	 mashinistDao.createIfNotExists(potyag1.getMashinist());
	 potyag2.setMashinist(new Mashinist("Petrenko", "2222222222"));
	 mashinistDao.create(potyag2.getMashinist());
	 potyag3.setMashinist(new Mashinist("Kozak", "4444444444"));
	 mashinistDao.create(potyag3.getMashinist());
	 Mashinist xXX=new Mashinist("XXX", "5555555555");
	 mashinistDao.create(xXX);
//Dodau potyagi v depo
	 depo1.dodatVdepo(potyag1);
	 depo1.dodatVdepo(potyag2);
	 depo1.dodatVdepo(potyag3);
//Stvoruu potygi z vagoni depo
	 Potyag potyag4= new Potyag(depo1);
	 potyag4.setMashinist(new Mashinist("Sidorenko", "3333333333"));
	 mashinistDao.create(potyag4.getMashinist());
	 //vagonDao.delete(potyag4.getPotyg());
	 //vagonDao.create(potyag4.getPotyg());
//Dodau potyag v depo
	 depo1.dodatVdepo(potyag4);
//stvoruu tablizu potyagiv
	 potyagDao.create(potyag1);
	 potyagDao.create(potyag2);
	 potyagDao.create(potyag3);
	 potyagDao.create(potyag4);
//stvoruu tablizu depo
	 depoDao.create(depo1);

	 for (int i=1; i<16;i++){ Vagon v = vagonDao.queryForId(i);
	 if (v!=null)
		 System.out.println(v.getNomerVagona()+" "+
	                        v.getNazvaVagona()+" "+
				            v.getNomerPotyagaKey()+" "+
	                        v.getNazvaDepoKey()+" "+
				            v.hashCode());
	 }
	 for (int j=9000; j<9012;j++){ Vagon vT = vagonDao.queryForId(j);
	 if (vT!=null)
		 System.out.println(vT.getNomerVagona()+" "+
	                        vT.getNazvaVagona()+" "+
				            vT.getNomerPotyagaKey()+" "+
	                        vT.getNazvaDepoKey()+" "+
				            vT.hashCode());
	 }
}	
			catch (SQLException e) {e.printStackTrace();} 
			//catch (IOException e) {e.printStackTrace();}
	}
		private static void printResults(DatabaseResults resultSet) throws SQLException {
			String[] columns = resultSet.getColumnNames();
		// String[] types = getColumnTypes(resultSet);
		   String[] types = { "VARCHAR", "INT", "DATE", "VARCHAR" };
			int cnt = 0;
		for (int i = 0; i < columns.length; i++) {
			System.out.print(columns[i] + " | ");
			cnt += columns[i].length();
		}
		System.out.println("");
		for (int i = 0; i < cnt + 3 * columns.length - 1; i++) System.out.print("-");
		System.out.println("");

		while (resultSet.next()) {
			for (int i = 0; i < columns.length; i++) {System.out.print(getData(resultSet, columns[i], types[i]) + " | ");
		}
		System.out.println("");
		}
		}

		private static String getData(DatabaseResults resultSet, String columnName, String columnType) {
			String res;
		try {switch (columnType) {
			case "INT":
			case "BIGINT":res = "" + resultSet.getInt(resultSet.findColumn(columnName));break;
			case "DATE":res = "" + resultSet.getTimestamp(resultSet.findColumn(columnName));break;
			case "CHAR":
			case "VARCHAR":res = "" + resultSet.getString(resultSet.findColumn(columnName));break;
			case "FLOAT":res = "" + resultSet.getFloat(resultSet.findColumn(columnName));break;
			case "DOUBLE":res = "" + resultSet.getDouble(resultSet.findColumn(columnName));break;
			case "BOOLEAN":res = "" + resultSet.getBoolean(resultSet.findColumn(columnName));break;
			default:res = "";
					}
			return res;
			} 
		catch (SQLException e) {e.printStackTrace();return "";}	
}
}

//vagonDao.createIfNotExists(v);