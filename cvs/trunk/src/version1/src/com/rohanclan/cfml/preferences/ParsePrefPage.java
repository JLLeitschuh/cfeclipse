package com.rohanclan.cfml.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.rohanclan.cfml.CFMLPlugin;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Parser preference page
 * @author Oliver Tupman
 *
 */
public class ParsePrefPage	
		extends FieldEditorPreferencePage 
		implements IWorkbenchPreferencePage, ICFMLPreferenceConstants 
{

	CFMLPreferenceManager cfmlpm;
	
	public ParsePrefPage() 
	{
		super(GRID);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		setDescription("Here you can manage the behaviour of the auto-insertion of characters such as double quotes, as well as the auto-closing of tags");
		cfmlpm = new CFMLPreferenceManager();
	}
	
	public void createFieldEditors()
	{
		addField(new BooleanFieldEditor(P_AUTOCLOSE_DOUBLE_QUOTES, "Auto close \"s", getFieldEditorParent()));
		addField(new BooleanFieldEditor(P_AUTOCLOSE_SINGLE_QUOTES, "Auto close \'s", getFieldEditorParent()));
		addField(new BooleanFieldEditor(P_AUTOCLOSE_HASHES, "Auto close #s", getFieldEditorParent()));
		addField(new BooleanFieldEditor(P_AUTOCLOSE_TAGS, "Auto close tags (i.e. insert a closing \'>\')", getFieldEditorParent()));
		addField(new BooleanFieldEditor(P_AUTOINSERT_CLOSE_TAGS, "Auto insert a closing tag", getFieldEditorParent()));
		
	}
	
	 protected void performDefaults() 
	 {
        super.performDefaults();
    }
	
	 
	public void init(IWorkbench workbench){;}
}