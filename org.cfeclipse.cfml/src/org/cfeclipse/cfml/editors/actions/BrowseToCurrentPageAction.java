/*
 * Created on Jun 20, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package org.cfeclipse.cfml.editors.actions;

import org.cfeclipse.cfml.editors.CFMLEditor;
import org.cfeclipse.cfml.properties.CFMLPropertyManager;
import org.cfeclipse.cfml.util.ResourceUtils;
import org.cfeclipse.cfml.views.browser.BrowserView;
import org.cfeclipse.cfml.views.snips.SnipVarParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Rob
 * 
 *         Simple action to refresh the browser view
 */
public class BrowseToCurrentPageAction implements
		IWorkbenchWindowActionDelegate, IEditorActionDelegate {
	protected ITextEditor editor = null;

	private CFMLPropertyManager propertyManager;

	public BrowseToCurrentPageAction() {
		propertyManager = new CFMLPropertyManager();
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor instanceof ITextEditor
				|| targetEditor instanceof CFMLEditor) {
			editor = (ITextEditor) targetEditor;
		}
	}

	public void run() {
		run(null);
	}

	public void run(IAction action) {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		try {

			// IDocument doc =
			// editor.getDocumentProvider().getDocument(editor.getEditorInput());
			// ISelection sel = editor.getSelectionProvider().getSelection();
				
			FileEditorInput input = (FileEditorInput) page.getActiveEditor().getEditorInput();
			
			// String currentpath = ( (IResource) input.getFile()
			// ).getProjectRelativePath().toString();
			// String currentfile = ( (IResource)
			// ((FileEditorInput)editor.getEditorInput()).getFile() ).getName();
			// String URLpath =
			// propertyManager.projectURL(input.getFile().getProject());
			// System.out.println("currentpath: " + currentpath +
			// "; currentfile: " + currentfile + "; URLpath: " + URLpath);

			String url = ResourceUtils.getURL(input.getFile());
						
			// String calculatedURL = URLpath + "/" + currentpath;

			BrowserView browser = (BrowserView) page
					.showView(BrowserView.ID_BROWSER);
			browser.setUrl(url);
			browser.setFocus();
			// browser.refresh();

			editor.setFocus();

		} catch (Exception e) {
		//	e.printStackTrace();  //TODO: remove this stack trace, we should know what the error is, for example, if you aren't editing a resource. 
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (editor != null && editor.getSite() != null && editor.getSite().getPage() != null) {
			setActiveEditor(null, editor.getSite().getPage().getActiveEditor());
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		IEditorPart activeEditor = window.getActivePage().getActiveEditor();
		if (activeEditor instanceof ITextEditor) {
			editor = (ITextEditor) activeEditor;
		}

	}

}
