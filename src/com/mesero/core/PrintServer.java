package com.mesero.core;

import it.sauronsoftware.base64.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import com.mesero.config.Config;
import com.mesero.web.window.WindowNotification;



public class PrintServer {

	private ServerSocket listener;
	
	private static PrintServer printServer = null;
	private Vector<PrintDemon> printDemons = new Vector<PrintDemon>();
	
	private PrintServer() {
		Thread thread = new Thread() {
			public void run() {
				Start();
			}
		};
		thread.start();
	}
	
	public void removePrintDemon(PrintDemon pd) {
		this.printDemons.remove(pd);
	}
	
	public Vector<PrintDemon> getPrintDemons() {
		return this.printDemons;
	}
	
	public PrintDemon getPrintDemonLocal() {
		for(PrintDemon pd: printDemons) {
			if(pd.getHostAddress().equals(Config.getInstance().getClientIP())) return pd;
		}
		return null;
	}
	
	public PrintDemon getPrintDemon(String hostAdrress) {
		for(PrintDemon pd: printDemons) {
			if(pd.getHostAddress().equals(hostAdrress)) return pd;
		}
		return null;
	}
	
	private void Start() {
		
		try {
			listener = new ServerSocket(new Integer(Config.getInstance().getProperti(Config.PRINT_SERVER_PORT)));
			
			System.out.println("Print Server Started on Port: "+Config.getInstance().getProperti(Config.PRINT_SERVER_PORT));
			
			//serverStatusLabel.setText("Started");
			//started = true;
			try {
		        while (true) {
		        	
		        	Socket socket = listener.accept();
		        	
		        	PrintDemon pd = getPrintDemon(socket.getInetAddress().getHostAddress());
		        	
		        	if(pd != null) {
		        		pd.socket = null;
		        		printDemons.remove(pd);
		        	}
		        	
		        	PrintDemon printDemon = new PrintDemon(socket, this);
			        printDemon.start();
		        	
			        printDemons.add(printDemon);
		        }
		    } finally {
		    	//serverStatusLabel.setText("Stoped");
		    	//button.setText("Arrancar Servidor");
		    	//started = false;
		        listener.close();
		    }
		} catch (IOException e) {
			//serverStatusLabel.setText("Stoped");
			//button.setText("Arrancar Servidor");
			//started = false;
			//JOptionPane.showMessageDialog(frame, e.getMessage(), "Advertencia",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
    }
	
	public static PrintServer getInstance() {
		if(printServer == null) {
			printServer = new PrintServer();
		}
		return printServer;
	}
	
	
	//---------- PRINT DEMON -------------------------------------------------------------------------------------------------
	
	
	public static class PrintDemon extends Thread {
        private Socket socket;
        private PrintServer printServer;
        private String printName;
        
        private ObjectInputStream in;
        private ObjectOutputStream out;
        
        private WindowNotification windowNotification;

        public PrintDemon(Socket socket, PrintServer printServer) {
        	this.printServer = printServer;
            this.socket = socket;
            try {
            	out = new ObjectOutputStream(socket.getOutputStream());
            	out.flush();
            	in = new ObjectInputStream(socket.getInputStream());
				
				
				//printServer.getClientStatusLabel().setText("Connected");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //log("New connection with client# "+ socket.getLocalAddress().getHostAddress());
        }

        public void setWindowNotification(WindowNotification windowNotification) {
        	this.windowNotification = windowNotification;
        }
        
        public void run() {
            try {
                while (true) {
                    String input = (String)in.readObject();
                    if (input != null && input.indexOf("|") != -1) {
                    	if(input.substring(0, input.indexOf("|")).toLowerCase().equals("connect")) {
                    		this.printName = input.substring(input.indexOf("|")+1,input.length());
                    	} else 
                    	if(input.substring(0, input.indexOf("|")).toLowerCase().equals("message")) {
                    		windowNotification.showMessage(input.substring(input.indexOf("|")+1,input.length()));
                    	} else {
                    		throw new Exception("Protocolo de coneccion incorrecto");
                    	}
                    }
                    //System.out.println("closed = "+socket.isClosed());
                }
            } catch (IOException e) {
            	//printServer.getClientStatusLabel().setText("Desconected");
            	e.printStackTrace();
            	
            	 try {
                     socket.close();
                     socket = null;
                 } catch (IOException ex) {
                     log("Couldn't close a socket, what's going on?");
                 }
                 
                 printServer.removePrintDemon(this);
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
            	//printServer.getClientStatusLabel().setText("Desconected");
				e.printStackTrace();
				
				 try {
	                    socket.close();
	                    socket = null;
	                } catch (IOException ex) {
	                    log("Couldn't close a socket, what's going on?");
	                }
	                
	                printServer.removePrintDemon(this);
			} catch (Exception e) {
				e.printStackTrace();
				
				try {
                    socket.close();
                    socket = null;
                } catch (IOException ex) {
                    log("Couldn't close a socket, what's going on?");
                }
                
                printServer.removePrintDemon(this);
			}
        }

        public String getHostAddress() {
        	return this.socket.getInetAddress().getHostAddress();
        }
        
        public String getPrintName() {
        	return this.printName;
        }
        
        private byte[] read(File file) {
    	    
    	    byte[] buffer = new byte[(int) file.length()];
    	    InputStream ios = null;
    	    try {
    	        ios = new FileInputStream(file);
    	        if (ios.read(buffer) == -1) {
    	            throw new IOException("EOF reached while trying to read the whole file");
    	        }
    	    } catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			
    			try {
     	            if (ios != null)
     	                ios.close();
     	        } catch (IOException ex) {}
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			
    			try {
     	            if (ios != null)
     	                ios.close();
     	        } catch (IOException ex) {}
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			
    			try {
     	            if (ios != null)
     	                ios.close();
     	        } catch (IOException ex) {}
    		} 
    	    return buffer;
    	}
        
        public void sendFile(File file) {
        	try {
        	
        		byte [] bytes = read(file);
        		
        		//System.out.println(new String(bytes));
        		
        		String value = new String(Base64.encode(bytes));
        		
        		//System.out.println(value);
        		
        		//System.out.println(new String(Base64.decode(value).getBytes()));
        		
        		//out.writeObject("hola que tal"/*value*/);
        		out.writeObject(value);
				out.flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
                    socket.close();
                    socket = null;
                } catch (IOException ex) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# closed");
                printServer.removePrintDemon(this);
			}
        }
        
        private void log(String message) {
            System.out.println(message);
        }
    }
}
