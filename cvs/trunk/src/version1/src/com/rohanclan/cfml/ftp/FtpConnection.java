/*
 * Created on 07-Nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rohanclan.cfml.ftp;

import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewPart;

import com.enterprisedt.net.ftp.*;

import com.rohanclan.cfml.views.explorer.IFileProvider;
import com.rohanclan.cfml.views.explorer.FileNameFilter;
import com.rohanclan.cfml.util.AlertUtils;



/**
 * @author spike
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FtpConnection implements IFileProvider {

	//FTPClient ftpClient = null;
    FTPClient ftpClient = null;
	FtpConnectionProperties connectionProperties;
	private static FtpConnection instance = null;
	private LogListener listener = null;
	private IViewPart viewPart = null;
	private int fConnectionTimeout = 1000;
	
	public static FtpConnection getInstance() {
	    if (instance == null) {
	        instance = new FtpConnection();
	    }
	    return instance;
	}
	
	/**
	 * 
	 */
	public FtpConnection() {
		ftpClient = null;
	}
	
	public void setViewPart(IViewPart viewPart) {
	    this.viewPart = viewPart;
	}
	
	public BufferedInputStream getInputStream(String filepath) {
	    connect();
	    try {
	        
		    byte[] contents = ftpClient.get(filepath);
		    ByteArrayInputStream ins = new ByteArrayInputStream(contents);
		    BufferedInputStream bis = new BufferedInputStream(ins);
		    return bis;
	    }
	    catch (Exception e) {
	        AlertUtils.alertUser(e);
	        return null;
	    }
	}
	
	public void saveFile(byte[] content, String remotefile) {
	    connect();
	    try {
	        ftpClient.put(content,remotefile);
	    }
	    catch (Exception e) {
	        AlertUtils.alertUser(e);
	    }
	}

	
	public void disconnect() {
	    try {
	        ftpClient.quit();
	    }
	    catch (Exception e) {
	        AlertUtils.alertUser(e);
	    }
	}
	
	public void connect(FtpConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
		connect();
	}
	
	public void connect() {
	    if (isConnected()) {
		    return;
		}
		try {
			ftpClient = new FTPClient(connectionProperties.getHost(),connectionProperties.getPort(),fConnectionTimeout);
			listener = new LogListener();
	        ftpClient.setMessageListener(listener);
	        
	        // login
	       ftpClient.login(connectionProperties.getUsername(), connectionProperties.getPassword());

           ftpClient.setConnectMode(FTPConnectMode.PASV);
           
           /*
            * Spike:: Removed this because active mode hangs the client.
            *
	       if (connectionProperties.getPassive()) {
	           ftpClient.setConnectMode(FTPConnectMode.PASV);
	       }
	       else {
	           ftpClient.setConnectMode(FTPConnectMode.ACTIVE);
	       }
	       */
           
	       ftpClient.setType(FTPTransferType.ASCII);
	       
		}
		catch (Exception e) {
	        AlertUtils.alertUser(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.explorer.IFileProvider#getRoots()
	 */
	public Object[] getRoots() {

	    return new String[] {connectionProperties.getPath()};
	    
	}

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.explorer.IFileProvider#getChildren(java.io.File, java.io.FileFilter)
	 */
	public Object[] getChildren(String parent, FileNameFilter filter) {
		
		try {
		    connect();
		    
		    FTPFile[] files = ftpClient.dirDetails(parent);

		    if (files == null) {
				files = new FTPFile[0];
			}
		    
		    // Check if we've got back the directory itself.
		    if (files.length == 1 
		            && parent.endsWith("/"+files[0].getName())) {
		       
		        FTPFile[] test = ftpClient.dirDetails(parent+"/"+files[0].getName());
		        if (test == null 
		                || test.length == 0) {
		            files = new FTPFile[0];
		        }
		    }
		    
		    ArrayList filteredFileList = new ArrayList();
		    for (int i=0;i<files.length;i++) {
		        if (filter.accept(files[i])) {
		            RemoteFile file = new RemoteFile(files[i],parent + "/" + files[i].getName());
		            filteredFileList.add(file);
		        }
		    }

		    Object[] filteredFiles = filteredFileList.toArray();

			return filteredFiles;
		}

		catch (Exception e) {
	        AlertUtils.alertUser(e);
		}
		return new String[0];
	}
	
	

	/* (non-Javadoc)
	 * @see com.rohanclan.cfml.views.explorer.IFileProvider#dispose()
	 */
	public void dispose() {
		try {
			if (ftpClient != null) {			
				//System.out.println("Disconnecting FTP client.");
			    if (this.isConnected()) {
			        ftpClient.quit();
			    }
				
			}
		}
		catch (Exception e) {
	        AlertUtils.alertUser(e);
		}
	}
	
	private boolean isConnected() {
	    if (ftpClient == null) {
	        return false;
	    }
	    try {
	        ftpClient.quote("NOOP",new String[] {"200"});
	    }
	    catch (Exception e) {
	        //e.printStackTrace();
	        return false;
	    }
	    return true;
	}
	
	
	public IEditorInput getEditorInput(String filename) {
	    try {
	        connect();
		    FTPFile[] files = ftpClient.dirDetails(filename);
		    RemoteFile remoteFile = new RemoteFile(files[0],filename);
		    FtpFileEditorInput input = new FtpFileEditorInput(remoteFile);
	        return input;
	    }
	    catch (Exception e) {
	        AlertUtils.alertUser(e);
	        return null;
	    }
	}
	
	
	public void addLogListener(FTPMessageListener listener) {
	    this.listener.addListener(listener);
	}
	
	
	public void removeLogListener(FTPMessageListener listener) {
	    this.listener.removeListener(listener);
	}
	
	public String getLog() {
	    if (listener == null) {
	        return null;
	    }
	    return listener.getLog();
	}

}
