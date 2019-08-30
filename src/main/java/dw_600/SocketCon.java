package dw_600;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;


import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class SocketCon implements Runnable{

	int puesto;
	Socket socket;
	String path;
	Actuador actuador;
	private static final Logger LOGGER = Logger.getLogger(SocketCon.class.getName());
	
	public SocketCon(Actuador a, int puesto, String path) {
		this.actuador = a;
		this.puesto = puesto;
		this.path = path;

	}

	@Override
	public void run() {

		
		try {
			socket = IO.socket(path);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

		  @Override
		  public void call(Object... args) {
			  
			JSONObject obj = new JSONObject();
			try {
				obj.put("puesto", puesto);
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
		    socket.emit("handshake", obj);
		    
		    LOGGER.log(Level.INFO, "Conexion exitosa con el servidor");
		    
		  }
		  
		  

		}).on("setModo", new Emitter.Listener() {

		  @Override
		  public void call(Object... args) {
			 
			  
			  
			 
			  JSONObject obj = (JSONObject) args[0];
			  int puestoRecibido = -1;
			  
			  try {
				puestoRecibido = obj.getInt("puesto");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			  int modo = 0;
			try {
				modo = obj.getInt("modo");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			if(actuador.puedeCambiar(modo) ) {
				if(comprobarPuesto(puestoRecibido)) {
					System.out.println("Modo recibido:");
					System.out.println(modo);
					ModeSafeGuard.instance().bloquearCambioDeModo();
					actuador.setPattern(modo);
				}
			}
			  
		  }

		}).on("startTX", new Emitter.Listener() {

			  @Override
			  public void call(Object... args) {
				 
				  JSONObject obj = (JSONObject) args[0];
				  int puestoRecibido = -1;
				  
				  try {
					puestoRecibido = obj.getInt("puesto");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				if(comprobarPuesto(puestoRecibido)) {
					actuador.finalizarCount();
				}
				  
			  }

		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

		  @Override
		  public void call(Object... args) {
			  LOGGER.log(Level.INFO, "Servidor desconectado");
		  }

		});
//		  .on("reset", new Emitter.Listener() {
//
//			  @Override
//			  public void call(Object... args) {
//				 
//				  JSONObject obj = (JSONObject) args[0];
//				  int puestoRecibido = -1;
//				  
//				  try {
//					puestoRecibido = obj.getInt("puesto");
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				
//				if(comprobarPuesto(puestoRecibido)) {
//					actuador.sendCount();
//				}
//				  
//			  }
//
//		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
//
//		  @Override
//		  public void call(Object... args) {
//			  LOGGER.log(Level.INFO, "Servidor desconectado");
//		  }
//
//		});
		socket.connect();
		
		
		
	}
	
	boolean comprobarPuesto(int puesto) {
		return this.puesto == puesto;
	}

}