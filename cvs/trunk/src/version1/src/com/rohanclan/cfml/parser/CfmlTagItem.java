/*
 * Created on Mar 18, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.parser;

import com.rohanclan.cfml.parser.exception.*;

import java.util.Set;
/**
 * @author Oliver Tupman
 *
 */
public class CfmlTagItem extends TagItem {
	/**
	 * Adds a string-based name/value attribute pair to the tag
	 * 
	 * @param attrName - the name of the attribute
	 * @param attrValue - the value for the attribute
	 * @throws DuplicateAttributeException - The attribute already exists in the attr list
	 * @throws InvalidAttributeException - The attribute does not belong to this tag.
	 */
	public void addAttribute(String attrName, String attrValue) 
				throws DuplicateAttributeException, 
						InvalidAttributeException
	{
		Set attributes = syntax.getFilteredAttributes(itemName, attrName);
		if(attributes.size() == 0)
		{
			InvalidAttributeException excep = new InvalidAttributeException(attrName, attrValue, lineNumber);
			throw excep;
		}
			
		super.addAttribute(attrName, attrValue);
	}
	/**
	 * @param line
	 * @param startDocPos
	 * @param endDocPos
	 * @param name
	 */
	public CfmlTagItem(int line, int startDocPos, int endDocPos, String name) {
		super(line, startDocPos, endDocPos, name);
	}
	
	
}
