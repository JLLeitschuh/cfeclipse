/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import java.util.ArrayList;
import java.io.File;
// Removed this because it doesn't work on OS X
//import javax.swing.filechooser.*;
import org.eclipse.ui.internal.editors.text.JavaFileEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewPart;

import com.rohanclan.cfml.util.AlertUtils;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LocalFileSystem implements IFileProvider {

    File[] systemroot = File.listRoots();
    
    private static Object[] roots = null;
    private static IViewPart viewpart = null;
    
    /**
     * 
     */
    public LocalFileSystem() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Object[] getRoots() {
    	/*
    	ArrayList tmpRoots = new ArrayList();
    	FileSystemView view = FileSystemView.getFileSystemView();
    	for (int i=0;i<systemroot.length;i++) {
    		if (!view.isFloppyDrive(systemroot[i])) {
	    		String name = view.getSystemDisplayName(systemroot[i]);
	    		
				if (name == null) {
					name = "";
				}
				name = name.trim();
				if (name == null || name.length() < 1) {
					name = "";
				}
				
				
				
				//int index = name.lastIndexOf(" (");
				//if (index > 0) {
				//	name = name.substring(0, index);
				//}
				
				if (name.length() > 0) {
					FileSystemRoot drive = new FileSystemRoot(name);
					drive.setPath(systemroot[i].toString());
					tmpRoots.add(drive);
				}
				else {
					String driveLetter =  systemroot[i].toString();
					int index =driveLetter.indexOf("\\"); 
					if (index > 0) {
						driveLetter = driveLetter.substring(0,index);
					}
					FileSystemRoot drive = new FileSystemRoot(view.getSystemTypeDescription(systemroot[i]) + " (" + driveLetter + ")");
					drive.setPath(systemroot[i].toString());
					tmpRoots.add(drive);
				}
				
    		}
		}
    	*/
    	if (roots == null) {
	    	ArrayList tmpRoots = new ArrayList();
	    	for (int i=0;i<systemroot.length;i++) {
	    		String driveLetter =  systemroot[i].toString();
	    		FileSystemRoot drive = new FileSystemRoot(driveLetter);
	    		drive.setPath(systemroot[i].toString());
	    		tmpRoots.add(drive);
	    	}
	    	roots = tmpRoots.toArray();
    	}
        return roots;
    }
    
    public void connect() {
        // Shouldn't need to do anything here.
        AlertUtils.showStatusMessage("Connected to: Local Filesystem",viewpart);
        return;
    }
    
    public void disconnect() {
        
    }
    
    public void setViewPart(IViewPart part) {
        viewpart = part;
    }
    
    public Object[] getChildren(String parent, FileNameFilter filter) {
    	
        File[] children = new File(parent).listFiles(filter);
        if (children == null) {
            return new File[0];
        }
        else {
            return children;
        }
    }
   
    public IEditorInput getEditorInput(String filename) {

        JavaFileEditorInput input = new JavaFileEditorInput(new File(filename));
        return input;
    }
    
    
    public void dispose() {};
    
    public String toString() {
        return "Local Filesystem";
    }
}
