package DataBase;

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.cfg.Configuration;

import entities.Medicine;
import entities.User;  
  
public class StoreData {  
public static void main(String[] args) {  
      
    //creating configuration object  
    Configuration cfg=new Configuration();  
    cfg.configure("DataBase/hibernate.cfg.xml");//populates the data of the configuration file  
      
    //creating seession factory object  
    SessionFactory factory=cfg.buildSessionFactory();  
      
    //creating session object  
    Session session=factory.openSession();  
      
    //creating transaction object  
    Transaction t=session.beginTransaction();  
          
    Medicine e1= new Medicine() ;
    e1.setName("sonoo3");  
    e1.setObservations("jaiswal");  
      
    session.saveOrUpdate(e1);//persisting the object  
      
    t.commit();//transaction is committed  
    session.close();  
      
    System.out.println("successfully saved");  
      
}  

public static Object getById(Class<?> objectClass, int id){
	 //creating configuration object  
    Configuration cfg=new Configuration();  
    cfg.configure("DataBase/hibernate.cfg.xml");//populates the data of the configuration file  
      
    //creating seession factory object  
    SessionFactory factory=cfg.buildSessionFactory();  
    //creating session object  
    Session session=factory.openSession();  
    
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
	// TODO Auto-generated method stub
	
}

}  