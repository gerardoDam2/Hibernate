package gerardo.conexion;

import org.hibernate.SessionFactory;

import gerardo.main.MainController;

public class ConnectionStatusThread extends Thread {
	
	private MainController mainController;
	private int segundos;
	public boolean terminar;

	

	public ConnectionStatusThread(MainController mainController,int segundos) {
		this.mainController=mainController;
		this.segundos=segundos;
		terminar=false;
	}

	@Override
	public void run() {
		while(!terminar)
			try {
				SessionFactory sesionFactory = HibernateUtil.buildSessionFactory();
				if (sesionFactory==null) {
					mainController.statusOff();
				}else{
					if (!mainController.connStatusProperty().get()) {
						mainController.statusOn(sesionFactory);
					 }else{
						 sesionFactory.close();
					 }
				}
				
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	
	
	

}
