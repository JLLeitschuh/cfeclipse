/*
 * Created on Apr 22, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Stephen Milligan
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
package com.rohanclan.cfml.preferences;

//import java.net.URL;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;

import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.ICFColorConstants;

/**
 * @author Stephen Milligan
 */
public class CFMLPreferenceManager implements ICFMLPreferenceConstants {

	private IPreferenceStore store;
	
	
	private static final int DEFAULT_TAB_WIDTH = 4;
	private static final int DEFAULT_INSIGHT_DELAY = 500;
	
	private static final int DEFAULT_MAX_UNDO_STEPS = 25;
	
	private static final boolean DEFAULT_INSERT_SPACES_FOR_TABS 	= false;
	private static final boolean DEFAULT_ENABLE_HS_COMPATIBILITY 	= false;
	private static final boolean DEFAULT_ENABLE_DW_COMPATIBILITY 	= false;
	private static final boolean DEFAULT_TABBED_BROWSER = false;

	// Parser prefs
	private static final boolean	DEFAULT_PARSE_DOCFSCRIPT 			= false;
	private static final boolean	DEFAULT_PARSE_DOCFML	 			= true;
	private static final boolean	DEFAULT_PARSE_REPORT_ERRORS 		= true; 
	
	// Tag indent prefs
	private static final boolean	DEFAULT_AUTOCLOSE_DOUBLEQUOTES	= true;
	private static final boolean	DEFAULT_AUTOCLOSE_SINGLEQUOTES 	= true;
	private static final boolean	DEFAULT_AUTOCLOSE_TAGS			= true;
	private static final boolean	DEFAULT_AUTOCLOSE_HASHES			= true;
	private static final boolean	DEFAULT_AUTOINSERT_TAGS			= true;
	private static final boolean	DEFAULT_AUTOINDENT_ONTAGCLOSE		= false;
	
	// Colour defaults
	//private static final RGB DEFAULT_COLOR_DOCTYPE 		= ICFColorConstants.DOCTYPE;
	private static final RGB DEFAULT_COLOR_CFCOMMENT 		= ICFColorConstants.CF_COMMENT;
	private static final RGB DEFAULT_COLOR_CFKEYWORD 		= ICFColorConstants.CFKEYWORD;
	private static final RGB DEFAULT_COLOR_CFNUMBER 		= ICFColorConstants.CFNUMBER;
	private static final RGB DEFAULT_COLOR_CFSCRIPT 		= ICFColorConstants.CFSCRIPT;
	private static final RGB DEFAULT_COLOR_CFSCRIPTFUNCTION = ICFColorConstants.CFSCRIPT_FUNCTION;
	private static final RGB DEFAULT_COLOR_CFSCRIPT_KEYWORD = ICFColorConstants.CFSCRIPT_KEYWORD;
	private static final RGB DEFAULT_COLOR_CFSCRIPT_STRING 	= ICFColorConstants.CFSCRIPT_STRING;
	private static final RGB DEFAULT_COLOR_CFSTRING 		= ICFColorConstants.CFSTRING;
	private static final RGB DEFAULT_COLOR_CFTAG 			= ICFColorConstants.CFTAG;
	private static final RGB DEFAULT_COLOR_CSS 			= ICFColorConstants.CSS;
	private static final RGB DEFAULT_COLOR_DEFAULT 		= ICFColorConstants.DEFAULT;
	private static final RGB DEFAULT_COLOR_HTMCOMMENT	 	= ICFColorConstants.HTM_COMMENT;
	private static final RGB DEFAULT_COLOR_JSCRIPT 		= ICFColorConstants.JSCRIPT;
	private static final RGB DEFAULT_COLOR_JSCRIPTFUNCTION 	= ICFColorConstants.JSCRIPT_FUNCTION;
	private static final RGB DEFAULT_COLOR_STRING 			= ICFColorConstants.STRING;
	private static final RGB DEFAULT_COLOR_TAG 			= ICFColorConstants.TAG;
	private static final RGB DEFAULT_COLOR_UNKTAG 			= ICFColorConstants.UNK_TAG;
	private static final RGB DEFAULT_COLOR_FORMTAG			= ICFColorConstants.FORM;
	private static final RGB DEFAULT_COLOR_TABLETAG		= ICFColorConstants.TABLE;
	private static final RGB DEFAULT_COLOR_BACKGROUND		= ICFColorConstants.BACKGROUND_COLOR;
	
	// Scribble pad defaults
	private static final String DEFAULT_SCRIBBLE_PAD_FILE = "scribble.cfm";
	private static final boolean DEFAULT_SCRIBBLE_CLEAR_ON_LOAD = false;
	private static final String DEFAULT_SCRIBBLE_PROJECT_ID = "scribble";
	private static final String DEFAULT_SCRIBBLE_URL = "";
	private static final boolean DEFAULT_SCRIBBLE_LOAD_BROWSER = true;
	
	//Bracket Matching defaults
	private static final boolean DEFAULT_BRACKET_MATCHING_ENABLED = true;
	private static final RGB DEFAULT_BRACKET_MATCHING_COLOR = ICFColorConstants.BRACKET_MATCHING_COLOR;
	
	// Folding defaults
	private static final boolean DEFAULT_ENABLE_CODE_FOLDING = true;
	private static final int DEFAULT_MINIMUM_CODE_FOLDING_LINES = 3;
	
	// CFML Comments
	private static final boolean DEFAULT_FOLDING_CFMLCOMMENTS_FOLD = true;
	private static final boolean DEFAULT_FOLDING_CFMLCOMMENTS_COLLAPSE = true;
	// HTML Comments
	private static final boolean DEFAULT_FOLDING_HTMLCOMMENTS_FOLD = true;
	private static final boolean DEFAULT_FOLDING_HTMLCOMMENTS_COLLAPSE = true;
	// tag1
	private static final boolean DEFAULT_FOLDING_TAG1_FOLD = true;
	private static final boolean DEFAULT_FOLDING_TAG1_COLLAPSE = false;
	private static final String DEFAULT_FOLDING_TAG1_NAME = "cffunction";
	// tag2
	private static final boolean DEFAULT_FOLDING_TAG2_FOLD = true;
	private static final boolean DEFAULT_FOLDING_TAG2_COLLAPSE = false;
	private static final String DEFAULT_FOLDING_TAG2_NAME = "cfquery";
	// tag3
	private static final boolean DEFAULT_FOLDING_TAG3_FOLD = true;
	private static final boolean DEFAULT_FOLDING_TAG3_COLLAPSE = false;
	private static final String DEFAULT_FOLDING_TAG3_NAME = "cfscript";
	// tag4
	private static final boolean DEFAULT_FOLDING_TAG4_FOLD = true;
	private static final boolean DEFAULT_FOLDING_TAG4_COLLAPSE = false;
	private static final String DEFAULT_FOLDING_TAG4_NAME = "cfloop";
	// tag5
	private static final boolean DEFAULT_FOLDING_TAG5_FOLD = true;
	private static final boolean DEFAULT_FOLDING_TAG5_COLLAPSE = false;
	private static final String DEFAULT_FOLDING_TAG5_NAME = "cfoutput";
	// tag6
	private static final boolean DEFAULT_FOLDING_TAG6_FOLD = true;
	private static final boolean DEFAULT_FOLDING_TAG6_COLLAPSE = false;
	private static final String DEFAULT_FOLDING_TAG6_NAME = "cfif";
	// tag7
	private static final boolean DEFAULT_FOLDING_TAG7_FOLD = true;
	private static final boolean DEFAULT_FOLDING_TAG7_COLLAPSE = false;
	private static final String DEFAULT_FOLDING_TAG7_NAME = "cfswitch";
	// tag8
	private static final boolean DEFAULT_FOLDING_TAG8_FOLD = true;
	private static final boolean DEFAULT_FOLDING_TAG8_COLLAPSE = false;
	private static final String DEFAULT_FOLDING_TAG8_NAME = "cftry";
	
	/** this is public because the browser uses it on errors */
	public static final String DEFAULT_PROJECT_URL = "http://livedocs.macromedia.com";
	
	private static final String DEFAULT_CFML_DICTIONARY = "cfml.xml";
	
	public CFMLPreferenceManager() {
		store = CFMLPlugin.getDefault().getPreferenceStore();
	}

	public boolean getBooleanPref(String prefKey) {
		return store.getBoolean(prefKey);
	}

	/**
	 * Sets up all of the colours... Note proper spelling of 'colour' :D
	 * (and initialise not initialize)
	 */
	public void initialiseDefaultColours() {
		store.setDefault(P_COLOR_CFKEYWORD, getColorString(DEFAULT_COLOR_CFKEYWORD));
		//store.setDefault(P_COLOR_, DEFAULT_COLOR_DOCTYPE.toString());
		//store.setDefault(P_COLOR_, DEFAULT_COLOR_CFCOMMENT.toString());
		
		store.setDefault(P_COLOR_CFNUMBER, getColorString(DEFAULT_COLOR_CFNUMBER));
		store.setDefault(P_COLOR_CFCOMMENT, getColorString(DEFAULT_COLOR_CFCOMMENT));
		store.setDefault(P_COLOR_CFSCRIPT_TEXT, getColorString(DEFAULT_COLOR_CFSCRIPT));
		store.setDefault(P_COLOR_CFSCRIPT_FUNCTION, getColorString(DEFAULT_COLOR_CFSCRIPTFUNCTION));
		store.setDefault(P_COLOR_CFSCRIPT_KEYWORD, getColorString(DEFAULT_COLOR_CFSCRIPT_KEYWORD));
		store.setDefault(P_COLOR_CFSCRIPT_STRING, getColorString(DEFAULT_COLOR_CFSCRIPT_STRING));
		store.setDefault(P_COLOR_CFSTRING, getColorString(DEFAULT_COLOR_CFSTRING));
		store.setDefault(P_COLOR_CFTAG, getColorString(DEFAULT_COLOR_CFTAG));
		store.setDefault(P_COLOR_CSS, getColorString(DEFAULT_COLOR_CSS));
		store.setDefault(P_COLOR_DEFAULT_TEXT, getColorString(DEFAULT_COLOR_DEFAULT));
		store.setDefault(P_COLOR_HTM_COMMENT, getColorString(DEFAULT_COLOR_HTMCOMMENT));
		store.setDefault(P_COLOR_JSCRIPT_TEXT, getColorString(DEFAULT_COLOR_JSCRIPT));
		store.setDefault(P_COLOR_JSCRIPT_FUNCTION, getColorString(DEFAULT_COLOR_JSCRIPTFUNCTION));
		store.setDefault(P_COLOR_STRING, getColorString(DEFAULT_COLOR_STRING));
		store.setDefault(P_COLOR_HTM_TAG, getColorString(DEFAULT_COLOR_TAG));
		store.setDefault(P_COLOR_BACKGROUND, getColorString(DEFAULT_COLOR_BACKGROUND));
		store.setDefault(P_COLOR_UNK_TAG, getColorString(DEFAULT_COLOR_UNKTAG));
		store.setDefault(P_COLOR_HTM_FORM_TAG, getColorString(DEFAULT_COLOR_FORMTAG));
		store.setDefault(P_COLOR_HTM_TABLE_TAG, getColorString(DEFAULT_COLOR_TABLETAG));
		store.setDefault(P_BRACKET_MATCHING_COLOR, getColorString(DEFAULT_BRACKET_MATCHING_COLOR));
	}
	
	private String getColorString(RGB color) {
	    return color.red + "," + color.green + "," + color.blue;
	}
	
	public void initializeDefaultValues() {
		
		//this should set the default path for snippets to the plugin directory
		//in a sub directory called snippets... I think that makes more sense :)
		String snippath = "";
		try
		{
			//TODO figure out how to get to the snippets absolute path
			/* snippath = new URL(
				CFMLPlugin.getDefault().getBundle().getEntry("/"),
				"/snippets"
			).toString();
			*/
			snippath = CFMLPlugin.getDefault().getStateLocation().toString();
		}
		catch(Exception e)
		{
			//this should never happen.
		}		
		
        store.setDefault(P_INSIGHT_DELAY, DEFAULT_INSIGHT_DELAY); 
        store.setDefault(P_TAB_WIDTH, DEFAULT_TAB_WIDTH);
        store.setDefault(P_MAX_UNDO_STEPS, DEFAULT_MAX_UNDO_STEPS);
        store.setDefault(P_INSERT_SPACES_FOR_TABS, DEFAULT_INSERT_SPACES_FOR_TABS);
        store.setDefault(P_ENABLE_HS_COMPATIBILITY, DEFAULT_ENABLE_HS_COMPATIBILITY);
        store.setDefault(P_ENABLE_DW_COMPATIBILITY, DEFAULT_ENABLE_DW_COMPATIBILITY);
        store.setDefault(P_SNIPPETS_PATH, snippath);
        store.setDefault(P_TABBED_BROWSER, DEFAULT_TABBED_BROWSER);
        //store.setDefault(P_SNIPPETS_PATH, CFMLPlugin.getDefault().getStateLocation().toString());
        
        // Parser prefs.
        store.setDefault(P_PARSE_DOCFSCRIPT, DEFAULT_PARSE_DOCFSCRIPT);
        store.setDefault(P_PARSE_DOCFML, DEFAULT_PARSE_DOCFML);
        store.setDefault(P_PARSE_REPORT_ERRORS, DEFAULT_PARSE_REPORT_ERRORS);
        
        // Scribble pad prefs
        store.setDefault(P_SCRIBBLE_PAD_FILE, DEFAULT_SCRIBBLE_PAD_FILE);
        store.setDefault(P_SCRIBBLE_CLEAR_ON_LOAD, DEFAULT_SCRIBBLE_CLEAR_ON_LOAD);
        store.setDefault(P_SCRIBBLE_PROJECT_NAME, DEFAULT_SCRIBBLE_PROJECT_ID);
        store.setDefault(P_SCRIBBLE_LOAD_BROWSER, DEFAULT_SCRIBBLE_LOAD_BROWSER);
        store.setDefault(P_SCRIBBLE_URL, DEFAULT_SCRIBBLE_URL);
        
        // Tag indent prefs
        store.setDefault(P_AUTOCLOSE_DOUBLE_QUOTES, DEFAULT_AUTOCLOSE_DOUBLEQUOTES);
        store.setDefault(P_AUTOCLOSE_SINGLE_QUOTES, DEFAULT_AUTOCLOSE_SINGLEQUOTES);
        store.setDefault(P_AUTOCLOSE_TAGS, DEFAULT_AUTOCLOSE_TAGS);
        store.setDefault(P_AUTOCLOSE_HASHES, DEFAULT_AUTOCLOSE_HASHES);
        store.setDefault(P_AUTOINSERT_CLOSE_TAGS, DEFAULT_AUTOINSERT_TAGS);
        store.setDefault(P_AUTOINDENT_ONTAGCLOSE, DEFAULT_AUTOINDENT_ONTAGCLOSE);
        store.setDefault(P_BRACKET_MATCHING_ENABLED,DEFAULT_BRACKET_MATCHING_ENABLED);
        
        
        // CFML Dictionary pref
        store.setDefault(P_CFML_DICTIONARY, DEFAULT_CFML_DICTIONARY);
        initialiseDefaultColours();
        //store.setDefault(P_CFTAG_COLOR,ICFColorConstants.CFTAG.toString());
        
        
        // Folding
        store.setDefault(P_ENABLE_CODE_FOLDING,DEFAULT_ENABLE_CODE_FOLDING);
        store.setDefault(P_MINIMUM_CODE_FOLDING_LINES,DEFAULT_MINIMUM_CODE_FOLDING_LINES);
        store.setDefault(P_FOLDING_CFMLCOMMENTS_COLLAPSE,DEFAULT_FOLDING_CFMLCOMMENTS_COLLAPSE);
        store.setDefault(P_FOLDING_CFMLCOMMENTS_FOLD,DEFAULT_FOLDING_CFMLCOMMENTS_FOLD);
        store.setDefault(P_FOLDING_HTMLCOMMENTS_COLLAPSE,DEFAULT_FOLDING_HTMLCOMMENTS_COLLAPSE);
        store.setDefault(P_FOLDING_HTMLCOMMENTS_FOLD,DEFAULT_FOLDING_HTMLCOMMENTS_FOLD);
        store.setDefault(P_FOLDING_TAG1_FOLD,DEFAULT_FOLDING_TAG1_FOLD);
        store.setDefault(P_FOLDING_TAG1_COLLAPSE,DEFAULT_FOLDING_TAG1_COLLAPSE);
        store.setDefault(P_FOLDING_TAG1_NAME,DEFAULT_FOLDING_TAG1_NAME);
        store.setDefault(P_FOLDING_TAG2_FOLD,DEFAULT_FOLDING_TAG2_FOLD);
        store.setDefault(P_FOLDING_TAG2_COLLAPSE,DEFAULT_FOLDING_TAG2_COLLAPSE);
        store.setDefault(P_FOLDING_TAG2_NAME,DEFAULT_FOLDING_TAG2_NAME);
        store.setDefault(P_FOLDING_TAG3_FOLD,DEFAULT_FOLDING_TAG3_FOLD);
        store.setDefault(P_FOLDING_TAG3_COLLAPSE,DEFAULT_FOLDING_TAG3_COLLAPSE);
        store.setDefault(P_FOLDING_TAG3_NAME,DEFAULT_FOLDING_TAG3_NAME);
        store.setDefault(P_FOLDING_TAG4_FOLD,DEFAULT_FOLDING_TAG4_FOLD);
        store.setDefault(P_FOLDING_TAG4_COLLAPSE,DEFAULT_FOLDING_TAG4_COLLAPSE);
        store.setDefault(P_FOLDING_TAG4_NAME,DEFAULT_FOLDING_TAG4_NAME);
        store.setDefault(P_FOLDING_TAG5_FOLD,DEFAULT_FOLDING_TAG5_FOLD);
        store.setDefault(P_FOLDING_TAG5_COLLAPSE,DEFAULT_FOLDING_TAG5_COLLAPSE);
        store.setDefault(P_FOLDING_TAG5_NAME,DEFAULT_FOLDING_TAG5_NAME);
        store.setDefault(P_FOLDING_TAG6_FOLD,DEFAULT_FOLDING_TAG6_FOLD);
        store.setDefault(P_FOLDING_TAG6_COLLAPSE,DEFAULT_FOLDING_TAG6_COLLAPSE);
        store.setDefault(P_FOLDING_TAG6_NAME,DEFAULT_FOLDING_TAG6_NAME);
        store.setDefault(P_FOLDING_TAG7_FOLD,DEFAULT_FOLDING_TAG7_FOLD);
        store.setDefault(P_FOLDING_TAG7_COLLAPSE,DEFAULT_FOLDING_TAG7_COLLAPSE);
        store.setDefault(P_FOLDING_TAG7_NAME,DEFAULT_FOLDING_TAG7_NAME);
        store.setDefault(P_FOLDING_TAG8_FOLD,DEFAULT_FOLDING_TAG8_FOLD);
        store.setDefault(P_FOLDING_TAG8_COLLAPSE,DEFAULT_FOLDING_TAG8_COLLAPSE);
        store.setDefault(P_FOLDING_TAG8_NAME,DEFAULT_FOLDING_TAG8_NAME);
	}
	
	/**
	 * Gets an RGB from the preference store using key as the key. If the key
	 * does not exist, it returns 0,0,0
	 * @param key
	 * @return
	 */
	public RGB getColor(String key)
	{
		//try to get the color as a string from the store
		String rgbString = store.getString(key);
		//System.err.println(key + " :: " + rgbString);
		
		//if we didnt get anything back...
		if(rgbString.length() <= 0)
		{
			//try to get it from the default settings
			rgbString = store.getDefaultString(key);
			
			//if we still didnt get anything use black
			if(rgbString.length() <= 0)
			{
				System.err.println("Color key: " + key + " is a no show using black");
				rgbString = "0,0,0";
			}
		}
		
		//make sure we get an ok string
		rgbString = deParen(rgbString);
		
		RGB newcolor = null;
		try
		{
			newcolor = StringConverter.asRGB(deParen(rgbString));
		}
		catch(Exception e)
		{
			System.err.println("Woah... got an odd color passed: " + key);
			e.printStackTrace(System.err);
		}
		
		return newcolor;
	}
	
	/**
	 * for some reason the color can get stored as  {RGB 12, 1, 1} and the rbg maker
	 * thingy expects them in 12,1,1, format so this cleans up the string a bit
	 * @param item
	 * @return
	 */
	private String deParen(String item)
	{
		String d = item.replace('{',' ').replace('}',' '); 
		d = d.replaceAll("[RGB ]","").trim();
		return d;
	}
	
	public int tabWidth() {
	    //System.out.println("Tab width retrieved as: " + Integer.parseInt(store.getString(ICFMLPreferenceConstants.P_TAB_WIDTH).trim()));
		return Integer.parseInt(store.getString(ICFMLPreferenceConstants.P_TAB_WIDTH).trim());
	}
	
	public int defaultTabWidth() {
		return DEFAULT_TAB_WIDTH;
	}
	
	
	
	public boolean insertSpacesForTabs() {
		return store.getString(ICFMLPreferenceConstants.P_INSERT_SPACES_FOR_TABS).trim().equalsIgnoreCase("true");
	}
	
	public boolean defaultSpacesForTabs() {
		return DEFAULT_INSERT_SPACES_FOR_TABS;
	}
	
	
	
	public int insightDelay() {
		return Integer.parseInt(store.getString(ICFMLPreferenceConstants.P_INSIGHT_DELAY).trim());
	}
	
	public int defaultInsightDelay() {
		return DEFAULT_INSIGHT_DELAY;
	}
	
	
	
	public boolean homesiteCompatibility() {
		return store.getString(ICFMLPreferenceConstants.P_ENABLE_HS_COMPATIBILITY).trim().equalsIgnoreCase("true");
	}
	
	public boolean defaultHomesiteCompatibility() {
		return DEFAULT_ENABLE_HS_COMPATIBILITY;
	}
	
	public boolean dreamweaverCompatibility() {
		return store.getString(ICFMLPreferenceConstants.P_ENABLE_DW_COMPATIBILITY).trim().equalsIgnoreCase("true");
	}
	
	public boolean defaultDreamweaverCompatibility() {
		return DEFAULT_ENABLE_DW_COMPATIBILITY;
	}
	

	
	public String snippetsPath() {
		return store.getString(ICFMLPreferenceConstants.P_SNIPPETS_PATH).trim();
	}

	
	public int maxUndoSteps() {
		return Integer.parseInt(store.getString(ICFMLPreferenceConstants.P_MAX_UNDO_STEPS).trim());
	}
	
	public String defaultSnippetsPath() {
		return CFMLPlugin.getDefault().getStateLocation().toString();
	}
	
	public String projectURL() {
		return DEFAULT_PROJECT_URL;
	}
	
	public boolean defaultTabbedBrowser() {
	    return DEFAULT_TABBED_BROWSER;
	}
	
	public boolean tabbedBrowser() {
	    return store.getBoolean(ICFMLPreferenceConstants.P_TABBED_BROWSER);
	}

	
	public boolean enableFolding() {
		return store.getBoolean(ICFMLPreferenceConstants.P_ENABLE_CODE_FOLDING);
	}
	
	public int minimumFoldingLines() {
		return store.getInt(ICFMLPreferenceConstants.P_MINIMUM_CODE_FOLDING_LINES);
	}


	public boolean foldCFMLComments() {
		return store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_CFMLCOMMENTS_FOLD);
	}
	
	public boolean collapseCFMLComments() {
		return store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_CFMLCOMMENTS_COLLAPSE);
	}


	public boolean foldHTMLComments() {
		return store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_HTMLCOMMENTS_FOLD);
	}
	
	public boolean collapseHTMLComments() {
		return store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_HTMLCOMMENTS_COLLAPSE);
	}

	public boolean foldTag(int tagNumber) {
	    boolean val = false;
		switch (tagNumber) {
			case 1: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG1_FOLD); 
			break;
			case 2: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG2_FOLD); 
			break; 
			case 3: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG3_FOLD); 
			break;
			case 4: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG4_FOLD); 
			break;
			case 5: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG5_FOLD); 
			break;
			case 6: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG6_FOLD); 
			break;
			case 7: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG7_FOLD); 
			break;
			case 8: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG8_FOLD); 
			break;
		}
	    return val;
	}

	public boolean collapseTag(int tagNumber) {
	    boolean val = false;
		switch (tagNumber) {
			case 1: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG1_COLLAPSE);
			break;
			case 2: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG2_COLLAPSE);
			break;
			case 3: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG3_COLLAPSE);
			break;
			case 4: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG4_COLLAPSE);
			break;
			case 5: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG5_COLLAPSE);
			break;
			case 6: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG6_COLLAPSE);
			break;
			case 7: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG7_COLLAPSE);
			break;
			case 8: val = store.getBoolean(ICFMLPreferenceConstants.P_FOLDING_TAG8_COLLAPSE);
			break;
		}
	    return val;
	}

	public String foldingTagName(int tagNumber) {
	    //System.out.println("TEST : " + store.getString(ICFMLPreferenceConstants.P_FOLDING_TAG1_NAME));
	    String val = "";
		switch (tagNumber) {
			case 1: val = store.getString(ICFMLPreferenceConstants.P_FOLDING_TAG1_NAME);
			break;
			case 2: val = store.getString(ICFMLPreferenceConstants.P_FOLDING_TAG2_NAME);
			break;
			case 3: val = store.getString(ICFMLPreferenceConstants.P_FOLDING_TAG3_NAME);
			break;
			case 4: val = store.getString(ICFMLPreferenceConstants.P_FOLDING_TAG4_NAME);
			break;
			case 5: val = store.getString(ICFMLPreferenceConstants.P_FOLDING_TAG5_NAME);
			break;
			case 6: val = store.getString(ICFMLPreferenceConstants.P_FOLDING_TAG6_NAME);
			break;
			case 7: val = store.getString(ICFMLPreferenceConstants.P_FOLDING_TAG7_NAME);
			break;
			case 8: val = store.getString(ICFMLPreferenceConstants.P_FOLDING_TAG8_NAME);
			break;
		}
	    return val;
	}
	
	
	
	public boolean useFunkyContentAssist() {
		return true;
		//return true;
	}
	
}
