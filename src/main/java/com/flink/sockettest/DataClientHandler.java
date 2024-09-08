package com.flink.sockettest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;



/**
 * File Receiver this class provide to us obtain File from Server,
 * it created based on Socket client Programming. the buffer size is 1MB, 
 * so you will adjust it's buffer size according to your network stream size when you use it.
 * the default server host is localhost, according to your actual evn to change
 * the final static member variable SERVER_HOST. this class is abstract because of 
 * it's handleClient method need to override when you use this class.
 * see the FileSender java class. 
 * 
 * @author Ding Peng
 * @version 1.0 27/01/2011
 * @see class FileSender
 * @see method handleClient
 */
public abstract class DataClientHandler {

	
	/**
	 * init zhe Socket Client, this socket used to call Server.
	 */
	Socket socketClient = new Socket();
	
	/**
	 * the socket client outputstream, used to write parameter to 
	 * Server.
	 */
	DataOutputStream socketOutputStream;
	
	/**
	 * the socket client inputstream, used to read data from Server.
	 */
	DataInputStream socketInputStream;
	
	/**
	 * this is the stream buffer size.
	 */
	private final static int BUFFER_SIZE = 1024*1024;
	
	/**
	 * the file's count that you want to convert file stream to bytes array. 
	 * default value is ALL, that represents that you will convert all the files
	 * to byte array. 
	 */
	private final static int ALL2BYTES = -1;
	
	/**
	 * the server host which you need call. default is 'localhost'.
	 */
	public final static String SERVER_HOST = "172.16.33.158";
	
	/**
	 * the server port which you need call. default is '8979'.
	 */
	public final static int SERVER_PORT = 8979;
	
	/**
	 * it will connect to server when you new this class.
	 * @param address server host
	 * @param port server port
	 */
	public DataClientHandler(String address, int port) {
		connect(new InetSocketAddress(address, port));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DataClientHandler(SERVER_HOST, SERVER_PORT){
			@Override
			protected void handleClient() {
					writeTransferCode(HandlerTag.LITERAL_HANDLE_TAG);
					Map<String, String> map = new HashMap<String, String>();
					map.put("name", "tom");
					map.put("sex", "girl");
					map.put("age", "10");
					writePairParameterMap(map);
					System.out.println(handleLiteralTransfer() + "--------------");
					
			}
		}.handleClient();
	}

	/**
	 * connect to server, when connect faild it won't be stop until connect success
	 * and the connect period is 1s.
	 * @param socketAddress
	 */
	private void connect(SocketAddress socketAddress) {
		while(!socketClient.isConnected()) {
			try {
				socketClient = new Socket();
				socketClient.connect(socketAddress);
				System.out.println("connect success " + socketAddress.toString());
				socketOutputStream = getSocketDataOuputStream();
				socketInputStream = getSocketDataInputStream();
			} catch (UnknownHostException e) {
				System.err.println(e.getMessage() + " server " + socketAddress.toString() + " not found.");
				socketConnectPeriod();
			} catch (IOException e) {
				System.err.println(e.getMessage() + " server " + socketAddress.toString() + " cannot be conntect.");
				socketConnectPeriod();
			}
		}
	}
	
	/**
	 * loop to connect server period time. it's use of Thread sleep.
	 */
	@SuppressWarnings("static-access")
	private void socketConnectPeriod() {
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * obtain outputstream from socket.
	 * @return dataoutputstream
	 */
	private DataOutputStream getSocketDataOuputStream() {
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(
						new BufferedOutputStream(
								socketClient.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dos;
	}
	
	/**
	 * obtain intputstream from socket
	 * @return datainputstream
	 */
	private DataInputStream getSocketDataInputStream() {
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(
						new BufferedInputStream(
								socketClient.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dis;
	}
	
	/**
	 * write parameters map to server, the parameter fromat pattern is pair.
	 * one pair joined with '=', mutil pair joind with '&' 
	 * @param paramMap
	 */
	@SuppressWarnings("unchecked")
	protected void writePairParameterMap(Map<String, String> paramMap) {
		StringBuffer sbf = new StringBuffer();
		for (Iterator iter = paramMap.entrySet().iterator(); iter.hasNext();) {
			Entry<String, String> entry = (Entry<String, String>) iter.next();
			sbf.append(entry.getKey())
			   .append("=")
			   .append(entry.getValue())
			   .append("&");
		}
		sbf.deleteCharAt(sbf.lastIndexOf("&"));
		try {
			socketOutputStream.writeUTF(sbf.toString());
			socketOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get accepted result pair value by map key.
	 * @param name param key
	 * @return value
	 */
	public String getResultValue(String name) {
		Map<String, String> resultMap = getResultMap();
		return resultMap.get(name);
	}
	
	/**
	 * get accepted result map from server.
	 * @return parameter map
	 */
	public Map<String, String> getResultMap() {
		Map<String, String> resultMap = new HashMap<String, String>();
		
		String result = readStringData();
		StringTokenizer tokenizer = new StringTokenizer(result, "&");
		resultMap = new HashMap<String, String>(tokenizer.countTokens());
		
		while(tokenizer.hasMoreTokens()) {
			String pair = tokenizer.nextToken();
			String[] pairArr = pair.split("=");
			resultMap.put(pairArr[0], pairArr[1]);
		}
		
		return resultMap;
	}
	
	/**
	 * write one pair to server. the pair pattern is key = value.
	 * @param key param name
	 * @param value param value
	 */
	protected void writePairParameter(String key, String value) {
		try {
			socketOutputStream.writeUTF(
						StringUtils.join(new String[]{key, value}, "="));
			socketOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * read a string data from server.
	 * @return return the data.
	 */
	private String readStringData() {
		String result = null;
		try {
			result = socketInputStream.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * the input stream data from server contains multiple files, accroding to 
	 * your needs, you can convert it to files and you can convert it to file byte array yet.  
	 * @param path the path you wanna save.
	 * @param bytesSpliter the count you will convert to file byte array.
	 * @return the byte array map. the key is file name, value is file byte array.
	 */
	private Map<String, byte[]> fileParser(String path, int bytesSpliter) {
		String fileNames = readStringData();
		String fileLens = readStringData();
		
		byte[] readBytes = new byte[BUFFER_SIZE];
		
		StringTokenizer token = new  StringTokenizer(fileNames, ",");
		StringTokenizer fileLenToken = new  StringTokenizer(fileLens, ",");
		
		Map<String, byte[]> fileBytesMap = new HashMap<String, byte[]>(token.countTokens());
		
		try {
			if(bytesSpliter == -1) 
				bytesSpliter = token.countTokens();
				
			int i = 0;
			while (token.hasMoreTokens() && fileLenToken.hasMoreTokens()) {
				String fileName = token.nextToken();
				System.out.println("the receive file name:" + fileName);
				
				int fileLen = Integer.valueOf(fileLenToken.nextToken());
				System.out.println("the receive file length:" + fileLen + "KB");
				
				int offset = 0;
				int readNum = 0;
				
				if(i < bytesSpliter) {
					byte[] fileBytes = new byte[fileLen];
					while(offset < fileLen) {
						if(fileLen - offset > BUFFER_SIZE) 
							readNum = socketInputStream.read(readBytes, 0, BUFFER_SIZE);
						else 
							readNum = socketInputStream.read(readBytes, 0, fileLen - offset);
						
						System.arraycopy(readBytes, 0, fileBytes, offset, readNum);
						offset += readNum;
					}
					fileBytesMap.put(fileName, fileBytes);
					i ++;
				} else {
					DataOutputStream fdos = new DataOutputStream(
							new BufferedOutputStream(
									new FileOutputStream(new File(path + fileName))));
					while(offset < fileLen) {
						if(fileLen - offset > BUFFER_SIZE) 
							readNum = socketInputStream.read(readBytes, 0, BUFFER_SIZE);
						else 
							readNum = socketInputStream.read(readBytes, 0, fileLen - offset);
						offset += readNum;
						fdos.write(readBytes, 0, readNum);
					}
					fdos.flush();
					fdos.close();
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return fileBytesMap;
	}
	
	/**
	 * write transfer code to server
	 */
	protected void writeTransferCode(int transCode){
		try {
			socketOutputStream.writeInt(transCode);
			socketOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * close the socket stream.
	 */
	protected void close(){
		try {
			socketOutputStream.close();
			socketInputStream.close();
			socketClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * convert all the file stream to byte array map.
	 * @return the array map.
	 */
	protected Map<String, byte[]> handleFileTransfer() {
		return fileParser(null, ALL2BYTES);
	}
	
	/**
	 * literal transfer handler, it's in charge of data write.
	 * @param dataMap literal pair map.
	 */
	protected Map<String, String> handleLiteralMapTransfer() {
		Map<String, String> resultMap = getResultMap();
		close();
		return resultMap;
	}
	
	/**
	 * literal transfer handler, it's in charge of data write.
	 * @param dataMap literal pair map.
	 */
	protected String handleLiteralTransfer() {
		String resultLiteral = readStringData();
		close();
		return resultLiteral;
	}
	
	protected String handleLiteralTransfer(String key) {
		String resultLiteral = getResultValue(key);
		close();
		return resultLiteral;
	}
	
	/**
	 * convert all the file stream to files.
	 * @param path the path you will save.
	 */
	protected void handleFileTransfer(String path) {
		fileParser(path, 0);
	}
	
	/**
	 * the client handler if you want to use this class, you must override it.
	 */
	protected abstract void handleClient();

}


