package DataBase;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.cfg.Configuration;

import entities.MedicalShift;
import entities.Medicine;
import entities.Study;
import entities.User;
import entities.UserMedicine;
import entities.UserTreatment;
import entities.User.UserRole;  
  
public class StoreData {  
	
	
private static StoreData instance = null;
private SessionFactory factory = null;

private StoreData() {
	 //creating configuration object  
    Configuration cfg=new Configuration();  
    
    URI dbUri;
	try {
		String dbUrl = System.getenv("DATABASE_URL");
		if(dbUrl == null){
			cfg.configure("DataBase/hibernate.cfg.xml");//populates the data of the configuration file  
		}else{
			cfg.configure("DataBase/hibernate.cfg.xml.sample");  
			dbUri = new URI(dbUrl);
			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];
			dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
			
			cfg.setProperty("hibernate.connection.username", username);
			cfg.setProperty("hibernate.connection.password", password); 
			cfg.setProperty("hibernate.connection.url", dbUrl); 
		}
		
		//creating seession factory object  
	    this.factory = cfg.buildSessionFactory();
	    
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static StoreData getInstance() {
	if(instance == null){
		instance = new StoreData();
	}
    return instance;
}
	
public static void main(String[] args) {  
      
    //creating configuration object  
//    Configuration cfg=new Configuration();  
//    cfg.configure("DataBase/hibernate.cfg.xml");//populates the data of the configuration file  
//      
//    //creating seession factory object  
//    SessionFactory factory=cfg.buildSessionFactory();  
//      
//    //creating session object  
//    Session session=factory.openSession();  
//      
//    //creating transaction object  
//    Transaction t=session.beginTransaction();  
      
	//
	//list = null;
    /*UserMedicine e1= new UserMedicine() ;
    e1.setPatientId(1);
    e1.setMedicineId(1);
    e1.setDoctorId(4);
    e1.setObservations("20mg");  
    
    UserMedicine e2= new UserMedicine() ;
    e1.setPatientId(1);
    e1.setMedicineId(2);
    e1.setDoctorId(4);
    e1.setObservations("1mg"); 
      
    e1.save();  
    e2.save()*/;
    
	/*Medicine m2 = new Medicine();
	//m2.setId(2);
	m2.setName("Clonazepan");
	m2.setObservations("1mg");
    
	m2.save();*/
    
    UserTreatment m = new UserTreatment();
    m.setDate(21/05/2016);
    m.setDoctorId(1);
    m.save();
	
    //t.commit();//transaction is committed  
    //session.close();  
      
    System.out.println("successfully saved");  
      
}  

public static Object getById(Class<?> objectClass, int id){ 
    //creating session object  
    Session session = StoreData.getInstance().factory.openSession();  
    
    Object obj = null;
    
    try{
    	obj = (Object) session.get(objectClass, id);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    return obj;
}

public static void save(Object obj) {
	Session session = StoreData.getInstance().factory.openSession();  
	
	Transaction t=session.beginTransaction();  
	try{
		session.saveOrUpdate(obj);
		t.commit();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
	
}

public static List<?> getByField(Class<?> objectClass, String field, String value){ 
    //creating session object  
    Session session = StoreData.getInstance().factory.openSession();  
    
    List<Object> obj = null;
    String query = "SELECT * FROM '" + objectClass.getSimpleName() + "' WHERE " + field + " = '" + value + "'";
    try{
    	return session.createSQLQuery(query).addEntity(objectClass).list();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    return obj;
}

}  
