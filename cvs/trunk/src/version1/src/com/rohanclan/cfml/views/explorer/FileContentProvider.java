/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorInput;



import com.rohanclan.cfml.ftp.FtpConnection;
import com.rohanclan.cfml.ftp.FtpConnectionProperties;

import java.io.*;

class FileContentProvider implements IStructuredContentProvider {
    
    File[] contents = new File[] {};
    IFileProvider fileProvider;

    private FileNameFilter fileFilter = new FileNameFilter();
    
    public FileContentProvider() {
        fileFilter.allowDirectories(false);
    }
    
    public Object[] getElements(Object inputElement) {

    	try {
	        if (fileProvider == null) {
	            return new Object[] {IFileProvider.INVALID_FILESYSTEM};
	        }
	        if (inputElement != null) {
	            
	            if (inputElement instanceof LocalFileSystem
	                    || inputElement instanceof FtpConnectionProperties) {
		            return new String[0];
	            }
	            
	            String directoryName = inputElement.toString();
	            if (directoryName.indexOf("[") == 0) {
	                directoryName = directoryName.substring(1,directoryName.length()-1);
	            }
	            //System.out.println("File provider is " + fileProvider.getClass().getName());
	            Object[] files = fileProvider.getChildren(directoryName,fileFilter);
	            
	            return files;
	            
	        }
	        else {
	            return new String[] {"Null input element"};
	        }
    	}
        catch (Exception e) {
        	e.printStackTrace();
            return new Object[] {"Error! " + e.getMessage()};
        }
    }
    public void dispose() {
    	if (fileProvider != null) {
    		fileProvider.dispose();
    	}
    }
    
    
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        try {
	    	//System.out.println("File viewer input changed to ." + newInput.getClass().getName());
	        if (newInput instanceof IFileProvider) {
	            fileProvider = (IFileProvider)newInput;
	        }
	        else if (newInput instanceof FtpConnectionProperties) {
	        	fileProvider = FtpConnection.getInstance();
	        	((FtpConnection)fileProvider).connect((FtpConnectionProperties)newInput);
	        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public IEditorInput getEditorInput(String filename) {
        if (filename.indexOf("[") == 0) {
            filename = filename.substring(1,filename.length()-1);
        }
        
        return fileProvider.getEditorInput(filename);
        
    }
    
}