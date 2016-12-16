package gerardo.conexion;



import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.loader.GeneratedCollectionAliases;

public class HibernateUtil {

//	private static final SessionFactory sessionFactory = buildSessionFactory(); 

	public static SessionFactory buildSessionFactory() {
		
		try {			
			// Create the SessionFactory from hibernate.cfg.xml
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
					.configure(HibernateUtil.class.getResource("mysql_hibernate.cfg.xml")).build();
			Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
			return metadata.getSessionFactoryBuilder().build();
			
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			return null;
//			throw new ExceptionInInitializerError(ex);
		}
	}

	
	
}