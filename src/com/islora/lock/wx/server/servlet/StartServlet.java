package com.islora.lock.wx.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.islora.lock.wx.server.netty.MainServer;



/**
 * Servlet implementation class StartServlet
 */
@WebServlet( value = "/StartServlet",loadOnStartup=1)
public class StartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static char[] cc=new char[128];
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartServlet() {
        super();
    }

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~Starting lock wx api server~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		Thread th2 = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					MainServer.getInstance().service();
//				} catch (Exception ex) {
//					if("java.net.BindException".equals(ex.getClass().getName()));{
//						System.exit(1);
//					}
//				}
//
//			}
//
//		});
//		th2.start();
		//System.out.println(System.getProperty("java.library.path"));
		Thread th=new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					 ServerSocket so = new ServerSocket(8889);
		                System.out.println("Server is starting...");
		                Socket s = so.accept();
		                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		                PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
		                
		                while(true){
		                	int len=br.read(cc);
		                	System.out.println(new String(cc));
		                	Thread.sleep(1000);
		                	
		                	
		                	String[] ss=new String(cc).split(",");
		                	if(ss.length<=8) continue;
		            		String lat=ss[3];
		            		String lng=ss[5];
		            		String gpstime=ss[1];
		            		String temp=ss[7];
		            		
		            		
		            		double du=Double.parseDouble(lat.substring(0, 2));
		    				double fen=Double.parseDouble(lat.substring(2));
		    				lat=String.valueOf((du+fen/60));
		    				
		    				//lng=sb.toString();
		    				
		    				du=Double.parseDouble(lng.substring(0, 3));
		    				fen=Double.parseDouble(lng.substring(3));
		    				lng=String.valueOf((du+fen/60));
		            		
		            		System.out.println("lat:"+lat);
		            		System.out.println("lng:"+lng);
		            		System.out.println("temp:"+temp);
		            		System.out.println("gpstime:"+gpstime);
		            		
		                }
		        } catch (Exception ie) {
		            ie.printStackTrace();
		        }
			}
			
		});
		//th.start();
	}
		

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**/
		String s=request.getParameter("c");
		
		if("1".equals(s)){
			//response.getWriter().write("1");
		}else if("2".equals(s)){
			response.getWriter().write("2");
		}
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}