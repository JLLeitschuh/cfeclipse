/*
 * Created on Jul 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.editors.actions;


import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.core.resources.*;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.swt.widgets.MessageBox;

import com.rohanclan.cfml.editors.CFMLEditor;
import com.rohanclan.cfml.CFMLPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import com.rohanclan.cfml.preferences.ICFMLPreferenceConstants;
import com.rohanclan.cfml.views.browser.BrowserView;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LoadScribblePadAction implements IEditorActionDelegate {
    
    protected ITextEditor editor = null;
    
    private String scribbleFileName, scribbleProjectName, scribbleURL;
    boolean clearOnLoad,loadBrowser;
    private IPreferenceStore store;
    
    public LoadScribblePadAction () {
        store = CFMLPlugin.getDefault().getPreferenceStore();
    }
    
    public void run(IAction action) 
	{

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        scribbleFileName = store.getString(ICFMLPreferenceConstants.P_SCRIBBLE_PAD_FILE);
        scribbleProjectName = store.getString(ICFMLPreferenceConstants.P_SCRIBBLE_PROJECT_NAME);
        clearOnLoad = store.getBoolean(ICFMLPreferenceConstants.P_SCRIBBLE_CLEAR_ON_LOAD);
        loadBrowser = store.getBoolean(ICFMLPreferenceConstants.P_SCRIBBLE_LOAD_BROWSER);
        scribbleURL = store.getString(ICFMLPreferenceConstants.P_SCRIBBLE_URL);
        
        try {
        
	        IProject project = root.getProject(scribbleProjectName);
	
	        if (!project.exists()) {
	            MessageBox msg = new MessageBox(editor.getEditorSite().getShell());
	            msg.setText("Error!");
	            msg.setMessage("The scribble project could not be found. You can modify the scribble project in the preferences area for cfeclipse \n (Window > preferences > CFEclipse >Scribble pad ).");
	            msg.open();
	        }
	        else {
	            if (!project.isOpen()) {
	                project.open(null);
	            }
                IFile scribbleFile = project.getFile(scribbleFileName);
                if (!scribbleFile.exists()) {
                    MessageBox msg = new MessageBox(editor.getEditorSite().getShell());
    	            msg.setText("Error!");
    	            msg.setMessage("The scribble pad file could not be found. You can modify the scribble pad in the preferences area for cfeclipse \n (Window > preferences > CFEclipse >Scribble pad ).");
    	            msg.open();
                }
                else { 
                    FileEditorInput input = new FileEditorInput(scribbleFile);
                	IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	               
                	
                	String currentFile = ( (IResource) ((FileEditorInput)editor.getEditorInput()).getFile() ).getName();
                	
                	if (currentFile.equalsIgnoreCase(scribbleFile.getName())) {
                	    System.out.println("Yep, saved and refreshed");
                	    editor.doSave(new NullProgressMonitor());
                	    BrowserView browser = (BrowserView)page.showView(BrowserView.ID_BROWSER);
		         		browser.setUrl(scribbleURL);
                	}
                	else {
		                IEditorPart editorPart = IDE.openEditor(page,scribbleFile,true);
		                if (clearOnLoad) {
		                    editor.getDocumentProvider().getDocument(editor.getEditorInput()).set("");
		                }
		                if (loadBrowser) {
	
			         		   BrowserView browser = (BrowserView)page.showView(BrowserView.ID_BROWSER);
			         		   browser.setUrl(scribbleURL);
			         		   //browser.refresh();
			             }
                	}
	                editor.setFocus();
                }
	        }
        }
        catch (Exception e) {
            MessageBox msg = new MessageBox(editor.getEditorSite().getShell());
            msg.setText("Error!");
            msg.setMessage(e.getLocalizedMessage());
            msg.open();
        }
	}
    
    public void setActiveEditor(IAction action, IEditorPart targetEditor) 
	{

		if( targetEditor instanceof ITextEditor || targetEditor instanceof CFMLEditor ){
			editor = (ITextEditor)targetEditor;
		}
	}
    
    public void selectionChanged(IAction action, ISelection selection){;}
}
