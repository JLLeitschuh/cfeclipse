/*
 * Created on 	: 26-Aug-2004
 * Created by 	: Mark Drew
 * File		  	: SnipSmartDialog.java
 * Description	: This class is used to display SnipDialog on insertion. It should be cleverer to do more functions
 * 
 */
package com.rohanclan.cfml.views.snips;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Shell;

//import org.eclipse.swt.widgets.CCombo;
import java.util.*;
/**
 * @author Administrator
 *
 */
public class SnipSmartDialog {
    
    /*
     * This is the constructor
     */
    public SnipSmartDialog() {
		super();
	}
    
    public static String parse(String str, IFile activeFile, Shell shell ) {
        String newStr = str;
        ArrayList list = new ArrayList();
        int position = 0;
        
        while(newStr.indexOf("$${",position) >= 0) {
			int expressionStart = newStr.indexOf("$${",position)+3;
			int expressionEnd = newStr.indexOf("}",expressionStart);
			String expression = newStr.substring(expressionStart,expressionEnd);
			String stringArray[] = expression.split(":");
			String variable = stringArray[0];
			String defaultValue = "";
			if (stringArray.length > 1) {
			     defaultValue = stringArray[1]; 
			}
			String optionValArray[] = defaultValue.split("\\|");
			
			SnipVarItem item = new SnipVarItem(variable,optionValArray,expression);
			
			Iterator i = list.iterator();
			
			boolean duplicateItem = false;
			while(i.hasNext()) {
			    if (((SnipVarItem)i.next()).getOriginal().equalsIgnoreCase(expression)) {
			        duplicateItem = true;
			    }
			}
			
			if (!duplicateItem) {
				list.add(item);
			}
			
			position = expressionEnd;
        }
        
        
        if (list.iterator().hasNext()) {
	        
	        SnipDialog dia = new SnipDialog(shell);
		    dia.setItemList(list);
		    dia.setTitle("Replace  variables");
		    if(dia.open() == org.eclipse.jface.window.Window.OK){
		        try {
			        Iterator i = list.iterator();
			        while (i.hasNext()) {
			            SnipVarItem item = (SnipVarItem)i.next();
			            String original = item.getOriginal();
			            String replacement = item.getReplacement();
			            
				        String deregexpression = original.replaceAll("\\|","\\\\|");
				        String pattern = "\\$\\$\\{"+deregexpression+"\\}";
				        newStr = newStr.replaceAll(pattern,replacement);
			        }
		        }
		        catch(Exception e) {
		            e.printStackTrace();
		        }
		        
		        dia.close();
		    }
		    else {
		        return null;
		    }
	    
        }
        
        
        
        return newStr;
    }

}
