/*
 * Created on 07-Nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rohanclan.cfml.views.explorer.ftp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.*;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import com.rohanclan.cfml.ftp.FtpConnectionProperties;


/**
 * @author spike
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FtpConnectionDialog extends Dialog  implements ISelectionChangedListener {


	public FtpConnectionProperties connectionProperties;
	private Text host,path,username,password,connectionid;
	private int DELETE_ID = 3242;
	private Button deleteButton = null;
	private Button okButton = null;
	private TableViewer connectionTable = null;
	private Label errorMessageLabel = null;
	
	/**
	 * @param parent
	 */
	public FtpConnectionDialog(Shell parent,String connectionId) {
		super(parent);
		// TODO Auto-generated constructor stub
		
		connectionProperties = new FtpConnectionProperties(connectionId);
		
	}
	
	protected Control createContents(Composite parent) {
	    Control contents = super.createContents(parent);
	    deleteButton = createButton((Composite)buttonBar, DELETE_ID, "Delete",false);
	    deleteButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                buttonPressed(((Integer) event.widget.getData()).intValue());
            }
        });
		okButton = getButton(IDialogConstants.OK_ID);
		okButton.setText("Create");
	    return contents;
	}
	
	public void selectionChanged(SelectionChangedEvent event) {
	    String connectionId = event.getSelection().toString();
	    if (connectionId.indexOf("[") == 0) {
	        connectionId = connectionId.substring(1,connectionId.length()-1);
        }
	    if (connectionId.equals(ConnectionsContentProvider.NEW_CONNECTION)) {
	        connectionId = null;
	    }
	    
	    connectionProperties = new FtpConnectionProperties(connectionId);
	    redraw();
    }

	private void redraw() {
	    connectionid.setText(connectionProperties.getConnectionid());
	    host.setText(connectionProperties.getHost());
	    path.setText(connectionProperties.getPath());
	    username.setText(connectionProperties.getUsername());
	    password.setText(connectionProperties.getPassword());
	    if (connectionProperties.getConnectionid().length() == 0) {
	        
			okButton.setText("Create");
			connectionid.setEditable(true);
			deleteButton.setEnabled(false);
			
	    }
	    else {
	        
			okButton.setText("Save");
			connectionid.setEditable(false);
			deleteButton.setEnabled(true);
	    }
	}
	
	
	protected Control createDialogArea(Composite parent) {

		Composite container = (Composite)super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		

		final GridData tableData = new GridData(GridData.FILL_BOTH);
		tableData.widthHint = 200;
	
		Composite tableArea = new Composite(container,SWT.NONE);
		tableArea.setLayoutData(tableData);
		GridLayout tableLayout = new GridLayout();
		tableLayout.numColumns = 1;
		tableArea.setLayout(tableLayout);

		final GridData gridData = new GridData(GridData.FILL_BOTH);
		Label connectionLabel = new Label(tableArea,SWT.RIGHT);
		connectionLabel.setText("Connections");
		
		connectionTable = new TableViewer(tableArea,SWT.SINGLE|SWT.BORDER);
		final Table table = connectionTable.getTable();
        table.setLayoutData(new GridData(GridData.FILL_BOTH));
		connectionTable.setContentProvider( new ConnectionsContentProvider());
		connectionTable.addSelectionChangedListener(this);
		
		connectionTable.setInput(new Object());
		
		

		
		
		Composite editArea = new Composite(container,SWT.NONE);
		editArea.setLayoutData(gridData);
		editArea.setLayout(layout);

		Label editLabel = new Label(editArea,SWT.RIGHT);
		editLabel.setText("Edit connection:");
		GridData labelData = new GridData();
		labelData.horizontalSpan = 2;
		editLabel.setLayoutData(labelData);

		// Connectionid
		connectionid = createTextControl(editArea,"Connection Name:",connectionProperties.getHost(),50);
		
		// Host name
		host = createTextControl(editArea,"Host Name:",connectionProperties.getHost(),50);
		
		// Path
		path = createTextControl(editArea,"Path:",connectionProperties.getPath(),20);
		
		// Username
		username = createTextControl(editArea,"Username:",connectionProperties.getUsername(),20);
		
		// Password
		password = createPasswordControl(editArea,"Password:",connectionProperties.getPassword(),20);


		
		errorMessageLabel = new Label(container, SWT.LEFT);
		errorMessageLabel.setFont(parent.getFont());
		Color color = new Color(Display.getCurrent(),255,0,0);
		errorMessageLabel.setForeground(color);
		GridData errorLabelData = new GridData(
				GridData.GRAB_HORIZONTAL |
				GridData.HORIZONTAL_ALIGN_FILL);
		errorLabelData.horizontalSpan = 2;
		errorMessageLabel.setLayoutData(errorLabelData);
		

		
		connectionid.addModifyListener(
				new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						validateInput();
					}
				}
			);
		
		host.addModifyListener(
				new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						validateInput();
					}
				}
			);
		
		
		selectItem();
		
		return container;
	}
	
	
	
	private void selectItem() {
	    int selectedItem = -1;
		TableItem[] items = connectionTable.getTable().getItems();
		String connectionId = connectionProperties.getConnectionid();
		if (connectionId.length() == 0) {
		    connectionId = ConnectionsContentProvider.NEW_CONNECTION;
		}
		
		for (int i=0;i<items.length;i++) {
		    if(items[i].getText().equals(connectionId)) {;
		    	selectedItem = i;
		    	break;
		    }
		}
		if (selectedItem >= 0) {
			
			connectionTable.getTable().setSelection(selectedItem);
		}
	}
	
	private Text createTextControl(Composite parent, String labelText, String text, int width) {
		Label label = new Label(parent,SWT.RIGHT );
		label.setText(labelText);
		Text control = new Text(parent,SWT.LEFT | SWT.BORDER);
		GridData data = new GridData();
		data.widthHint = convertWidthInCharsToPixels(width);
		control.setLayoutData(data);
		control.setText(text);
		return control;
	}
	
	
	private Text createPasswordControl(Composite parent, String labelText, String text, int width) {
		Label label = new Label(parent,SWT.RIGHT);
		label.setText(labelText);
		Text control = new Text(parent,SWT.LEFT | SWT.PASSWORD | SWT.BORDER);
		GridData data = new GridData();
		data.widthHint = convertWidthInCharsToPixels(width);
		control.setLayoutData(data);
		control.setText(text);
		return control;
	}
	
	

	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			try {
			connectionProperties.setHost(host.getText());
			connectionProperties.setPath(path.getText());
			connectionProperties.setUsername(username.getText());
			connectionProperties.setPassword(password.getText());
			connectionProperties.setConnectionid(connectionid.getText());
			connectionProperties.save();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} else if (buttonId == DELETE_ID) {
		    
			FtpConnectionProperties.deleteConnection(connectionProperties.getConnectionid());
			connectionTable.setInput(new Object());
		}
		super.buttonPressed(buttonId);
	}

	
	
	protected void validateInput() {
		

		String errorMessage = null;
		
		String test = connectionid.getText();
		if (!test.matches(".*[\\S]+.*")) {
		    errorMessage = "You must specify a connection name.";
		}
		else if (!host.getText().matches(".*[\\S]+.*")) {
		    errorMessage = "You must specify a host name";
		}
		errorMessageLabel.setText(errorMessage == null ? "" : errorMessage); //$NON-NLS-1$
	    
		okButton.setEnabled(errorMessage == null);
	
		errorMessageLabel.getParent().update();
		
	}
	
	
	
}
