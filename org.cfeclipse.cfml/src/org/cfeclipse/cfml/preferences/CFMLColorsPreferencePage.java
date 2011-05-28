/*
 * Created on Jun 17, 2004
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
package org.cfeclipse.cfml.preferences;

import org.cfeclipse.cfml.CFMLPlugin;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.internal.dialogs.WorkbenchPreferenceDialog;


/**
 * @author Rob
 */
public class CFMLColorsPreferencePage extends FieldEditorPreferencePage implements 
	IWorkbenchPreferencePage {
	
	private CFMLPreferenceManager cfmlpm;
	private IWorkbench workbench;

	public CFMLColorsPreferencePage() 
	{
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("On this page you can change the colour preferences for CFML sections.");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors()
	{
ColorFieldEditor daCol = new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_DEFAULT_TEXT,"Default &Text:",getFieldEditorParent());
		addField(daCol);
		addField(new ColorFieldEditor(EditorPreferenceConstants.P_COLOR_BACKGROUND, "Back&ground:", getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_CFCOMMENT,"CFML &Comment:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_BACKGROUND_CFCOMMENT,"CFML Comment &Background:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_JAVADOC, "CFScript JavaDoc:", getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_BACKGROUND_JAVADOC, "CFScript JavaDoc Background:",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_CFTAG,"CFML &Tag:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_TAGLIB_TAG,"Taglib &Tag:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_CFSTRING,"CFML St&ring:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_CFKEYWORD,"CFML &Keyword:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_CFOPPERATOR,"CFML &Opperators (gt, lt, eq, etc.):",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_CFNUMBER,"CFML &Number:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_CFBUILTINSCOPE,"CFML B&uilt-in Scope (request,variables,url, etc.):",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_CFSCOPE,"CFML &Scope:",getFieldEditorParent()));
		
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_CFSCRIPT_TEXT,"CFScript Te&xt:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_CFSCRIPT_KEYWORD,"CFScript Key&word:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_CFSCRIPT_FUNCTION,"CFScript Funct&ion:",getFieldEditorParent()));
		addField(new ColorFieldEditor(CFMLColorsPreferenceConstants.P_COLOR_CFSCRIPT_STRING,"CFScript Strin&g:",getFieldEditorParent()));

		IPreferenceNode node = this.workbench.getPreferenceManager().find("org.cfeclipse.cfml.preferences.CFMLPreferencePage").findSubNode("org.cfeclipse.cfml.preferences.EditorPreferencePage").findSubNode("org.cfeclipse.cfml.preferences.CFMLColorsPreferencePage");
		getFieldEditorParent().getParent().traverse(SWT.TRAVERSE_TAB_NEXT);
		getFieldEditorParent().getParent().redraw();
		System.err.println(node.toString());
		
		WorkbenchPreferenceDialog woot = (WorkbenchPreferenceDialog)getContainer();
		Object wee = woot.getSelectedPage();
		ISelection borg = woot.getTreeViewer().getSelection();
		woot.getTreeViewer().setSelection(borg);
	}

	protected void performDefaults() {
		super.performDefaults();
	}
	
	
	public void init(IWorkbench workbench){
		this.workbench = workbench;
		//this.getControl().getParent().traverse(SWT.TRAVERSE_TAB_NEXT);
	}
}
