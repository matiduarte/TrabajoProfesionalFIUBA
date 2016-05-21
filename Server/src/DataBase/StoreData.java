package DataBase;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.cfg.Configuration;

import entities.Medicine;
import entities.Study;
import entities.User;
import entities.UserMedicine;
import entities.User.UserRole;  
  
public class StoreData {  
	
	
private static StoreData instance = null;
private SessionFactory factory = null;

private StoreData() {
	 //creating configuration object  
    Configuration cfg=new Configuration();  
    cfg.configure("DataBase/hibernate.cfg.xml");//populates the data of the configuration file  
      
    //creating seession factory object  
    this.factory = cfg.buildSessionFactory();
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
    
    Study study = new Study();
    study.setDoctorId(3);
    study.setPatientId(1);
    study.setObservations("Realizar Rayos X");
    study.setType("Rayos X");
    
    study.save();
	
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
    String query = "SELECT * FROM " + objectClass.getSimpleName() + " WHERE " + field + " = " + value;
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