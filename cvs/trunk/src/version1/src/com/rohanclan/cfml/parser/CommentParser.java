/*
 * Created on Jul 27, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;
import java.util.Iterator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Document;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CommentParser {

    private ArrayList CFCommentList; 
    
    private CFCommentItem comment; 
    private IResource resource = null;
    private int CFCommentDepth = 0;
    private int CFCommentStartOffset,CFCommentEndOffset = 0;
    private int CFScriptDepth = 0;
    private int lineCommentEnd = 0;
    
    private Pattern pattern = Pattern.compile("(<!---)|(<cfscript)|(--->)|(</cfscript)|(/\\*)|(//)|(\\*/)");

    private Document doc;
    
    public void ParseDocument(Document doc, IResource resource) {
        
        this.doc = doc;
        this.resource = resource; 
        CFCommentList = new ArrayList();
        try {

            Matcher matcher = pattern.matcher(doc.get());
            
            while (matcher.find()) {
                handleMatch(matcher.group().trim(),matcher.start(),matcher.end());
            }
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }

        
        
    }
    
    
    private void handleMatch(String group,int start, int end) {
       int commentLength;

       
       //System.out.println("Handler matcher called - Start: " + start + " End: " + end + "Group: " + group + "lineComment: " + lineCommentEnd + "CFSCript: " + CFScriptDepth + "CFComment: " + CFCommentDepth);
        // Checking for opening CFML comment (<!---)
        if (CFScriptDepth == 0 
                && "<!---".equalsIgnoreCase(group)) {
            // Looks like we've found the start of a CFML comment
            
            // If we're not already inside a CF comment we can set the comment start offset
            if (CFCommentDepth == 0) {
                CFCommentStartOffset = end;
                //System.out.println("Found a CFML comment at " + start);
            }
            
            CFCommentDepth++;
            
        }
        
        
        
        // Checking for closing CFML comment (--->)
        if (CFScriptDepth == 0
                && "--->".equalsIgnoreCase(group)) {
            // Looks like we've found the end of a CFML comment
            CFCommentDepth--;
            
            // Check if this closed out a comment block. If so, grab the comment.
            if (CFCommentDepth == 0) {
                CFCommentEndOffset = start;
                commentLength = CFCommentEndOffset - CFCommentStartOffset;

                try {
                    comment = new CFCommentItem(doc.getLineOfOffset(CFCommentStartOffset),CFCommentStartOffset,CFCommentEndOffset,doc.get(CFCommentStartOffset,commentLength));
                    CFCommentList.add(comment);
                    //System.out.println("CFML comment added to list " + comment.getContents());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        //Checking for opening CFSCRIPT tag (<cfscript)
        if (CFScriptDepth == 0 
                && CFCommentDepth == 0
                && "<cfscript".equalsIgnoreCase(group)) {
            // Looks like we've found the start of a CFSCRIPT block
            

            //System.out.println("Found a CFSCRIPT start tag at " + start);
            
            CFScriptDepth++;
            
        }
        
        //Checking for closing CFSCRIPT tag (<cfscript)
        if (CFScriptDepth == 1 
                && CFCommentDepth == 0
                && "</cfscript".equalsIgnoreCase(group)) {
            // Looks like we've found the end of a CFSCRIPT block
            

            //System.out.println("Found a CFSCRIPT end tag at " + start);
            
            CFScriptDepth--;
            
        }
        
        
        
        
        // Checking for opening CFSCRIPT comment (/*)
        if (CFScriptDepth == 1 
                && "/*".equalsIgnoreCase(group)
                && start > lineCommentEnd){
            // Looks like we've found the start of a CFSCRIPT comment
            if (CFCommentDepth == 0) {
                CFCommentStartOffset = end;
                //System.out.println("Found a CFSCRIPT block comment at " + start);
            }
            CFCommentDepth++;
        }
        
        
        if (CFScriptDepth == 1 
                && "*/".equalsIgnoreCase(group)
                && start > lineCommentEnd) {
            // Looks like we've found the end of a CFSCRIPT comment
            CFCommentDepth--;
            if (CFCommentDepth == 0) {
                CFCommentEndOffset = start;
                commentLength = CFCommentEndOffset - CFCommentStartOffset;

                try {
                    comment = new CFCommentItem(doc.getLineOfOffset(CFCommentStartOffset),CFCommentStartOffset,CFCommentEndOffset,doc.get(CFCommentStartOffset,commentLength));
                    CFCommentList.add(comment);
                    //System.out.println("CFSCRIPT comment added to list " + comment.getContents());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        
        if (CFScriptDepth == 1 
                && "//".equalsIgnoreCase(group)) {
            // Looks like we've found a single line script comment
            CFCommentStartOffset = end;
            //System.out.println("Found a CFSCRIPT line comment at " + start);
            try {
                IRegion region = doc.getLineInformationOfOffset(start);
                region.getLength();
                region.getOffset();
                lineCommentEnd = region.getOffset() + region.getLength(); 
                commentLength =  lineCommentEnd - CFCommentStartOffset;
                comment = new CFCommentItem(doc.getLineOfOffset(CFCommentStartOffset),CFCommentStartOffset,CFCommentEndOffset,doc.get(CFCommentStartOffset,commentLength));
                CFCommentList.add(comment);
                //System.out.println("CFSCRIPT line comment added to list " + comment.getContents());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    
    
    public void setTaskMarkers() {
        Iterator i = CFCommentList.iterator();
        
        int taskLine;
        
        while (i.hasNext()) {
            CFCommentItem comment = (CFCommentItem)i.next();
            
            Pattern markerPattern = Pattern.compile("TODO[^A-Za-z]",Pattern.CASE_INSENSITIVE);
            
            String[] lines = comment.getContents().split("[\\n]");
            
            for (int line = 0; line < lines.length; line++) {
                Matcher matcher = markerPattern.matcher(lines[line]);
                
                if (matcher.find()) {
                    try {
                        /*
	        	        IMarker[] taskMarkers =  resource.findMarkers(IMarker.TASK, true, IResource.DEPTH_ONE);
	        			
	        	        taskLine = comment.getLineNumber() + line + 1;
	        	        
	        			for (int j=0; j<taskMarkers.length;j++) {
	        			    if (!taskMarkers[j].exists()) {
	        			        taskMarkers[j].delete();
	        			        System.out.println("Invalid marker deleted");
	        			    }
	        			}
	        			*/
	                    Map attrs = new HashMap();
	                    MarkerUtilities.setLineNumber(attrs, comment.getLineNumber()+line + 1);
                    
                        String message = lines[line].substring(matcher.end(),lines[line].length()-1);
	                    MarkerUtilities.setMessage(attrs, message);
	                    MarkerUtilities.createMarker(resource,attrs,IMarker.TASK);
                    
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            
        }
    }
    
    
    
}
