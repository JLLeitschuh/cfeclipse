/* 
 * $Id: TagData.java,v 1.1 2005-01-09 02:06:33 smilligan Exp $
 * $Revision: 1.1 $
 * $Date: 2005-01-09 02:06:33 $
 * 
 * Created Jan 4, 2005 10:51:03 PM
 *
 * CFEclipse - The Open Source ColdFusion Development Environment
 * Copyright (c) 2005 Stephen Milligan and others.  All rights reserved.
 * For more information on cfeclipse, please see &lt;http://cfeclipse.tigris.og/&gt;.
 *
 * ====================================================================
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the Eclipse Public License
 * as published by the Eclipse Foundation; either
 * version 1.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Eclipse 
 * Public License for more details.
 *
 * You should have received a copy of the Eclipse Public License with this 
 * software. If not, the full text of version 1.0 of the Eclipse Public License 
 * is available online at the following URL:
 * 
 * http://www.eclipse.org/legal/epl-v10.html
 * ====================================================================
 */
package com.rohanclan.cfml.editors.partitioner;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Class description...
 * 
 * @author Stephen Milligan
 * @version $Revision: 1.1 $
 */
public class TagData {

    /** The type of partition the first portion of the tag represents */
    private final String fStartPartitionType;

    /** The data for this tag from the document */
    private final String fData;

    /** Is this a closing tag */
    private boolean isCloser = false;

    /** Where does the tag name partition end */
    private int fStartPartitionEnd = -1;

    /** Where does the middle partition end */
    private int fMidPartitionEnd = -1;

    /**
     * What partition type should be assigned to the bit between the name and
     * the ">"
     */
    private final String fMidPartitionType;

    /**
     * What partition type should be assigned to the third part of the tag
     */
    private final String fEndPartitionType;

    /**
     * 
     * @param type -
     *            The type of token this data represents.
     * @param data -
     *            The text string for the tag from the document.
     */
    public TagData(String startPartitionType, String data,
            String midPartitionType, String endPartitionType) {
        fStartPartitionType = startPartitionType;
        fData = data;
        fMidPartitionType = midPartitionType;
        fEndPartitionType = endPartitionType;
        parseData();
        //describeMe();
    }

    /**
     * @return fData
     */
    public String getRawData() {
        return fData;
    }

    /**
     * 
     * @return The type of partition for the first part.
     */
    public String getStartPartitionType() {
        return fStartPartitionType;
    }

    /**
     * 
     * @return The type of partition for the middle part.
     */
    public String getMidPartitionType() {
        return fMidPartitionType;
    }

    /**
     * 
     * @return The type of partition for the end part.
     */
    public String getEndPartitionType() {
        return fEndPartitionType;
    }

    /**
     * 
     * @return The relative offset of the end of the first partition.
     */
    public int getFirstPartitionEnd() {
        return fStartPartitionEnd;
    }

    /**
     * 
     * @return The relative offset of the end of the first partition.
     */
    public int getMidPartitionEnd() {
        return fMidPartitionEnd;
    }

    /**
     * @return The partition type of the first part
     */
    public String toString() {
        return fStartPartitionType;
    }

    /**
     * 
     * @return Is this a closing tag or not.
     */
    public boolean isCloser() {
        return isCloser;
    }
    
    /**
     * Parse the raw data for the tag.
     *
     */
    private void parseData() {
        try {
            if (fData.length() < 3) {
                throw new Exception(
                        "Data length less than 3 characters for tag: " + fData);
            }
            if (fData.charAt(2) == '/') {
                isCloser = true;
                return;
            }
            // find out where the name ends
            for (int i = 0; i < fData.length(); i++) {
                if ((int) fData.charAt(i) <= 32 
                        || fData.charAt(i) == '/'
                        || fData.charAt(i) == '>') {
                    fStartPartitionEnd = i;
                    break;
                }
            }
            // find out where the mid part ends
            if (fData.charAt(fData.length() - 1) == '/') {
                fMidPartitionEnd = fData.length() - 2;
            } else {
                fMidPartitionEnd = fData.length() - 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void describeMe() {
        System.out.println("TagData class contains following data:");
        System.out.println("First Partition Type: " + fStartPartitionType); 
        System.out.println("MidPartition Type: " + fMidPartitionType); 
        System.out.println("Full Tag:" +fData); 
        System.out.println("First Partition ends at: " + fStartPartitionEnd);
        System.out.println("Mid Partition ends at: " + fMidPartitionEnd);
        System.out.println("Total length: " + fData.length());
    } 

}

/*
 * CVS LOG ====================================================================
 * 
 * $Log: not supported by cvs2svn $
 */