/* 
 * $Id: CFEPartition.java,v 1.6 2005-01-31 08:01:13 smilligan Exp $
 * $Revision: 1.6 $
 * $Date: 2005-01-31 08:01:13 $
 * 
 * Created Jan 18, 2005 2:08:20 PM
 *
 * CFEclipse - The Open Source ColdFusion Development Environment
 * Copyright (c) 2005 Stephen Milligan and others.  All rights reserved.
 * For more information on cfeclipse, please see http://cfeclipse.tigris.og/.
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

import org.eclipse.jface.text.TypedPosition;
import org.eclipse.jface.text.Assert;
/**
 * Class description...
 * 
 * @author Stephen Milligan
 * @version $Revision: 1.6 $
 */
public final class CFEPartition extends TypedPosition {

    /** The type that the next partition should be */
    private String fNextPartitionType = null;
    /** The type of partition that this partition opens */
    private String fOpensPseudoPartition = null;
    /** The type of partition that this partition closes */
    private String fClosesPseudoPartition = null;
    
    
    private boolean fStartPartition = false;
    private boolean fMidPartition = false;
    private boolean fEndPartition = false;
    private String fTagName = null;
    private boolean fIsPseudoPartition = false;
    
    /**
     * @param offset
     * @param length
     * @param type
     */
    public CFEPartition(int offset, int length, String type) {
        super(offset, length, type);
        Assert.isNotNull(type,"Null partition type passed to CFEPartition constructor.");
        //System.out.println("Partition of type " + type + " created from " + offset + " to " + Integer.toString(offset+length));
    }

    /** 
     * Sets the type of partition that should come after this one 
     * @param type - The type of partition that should come next.
     */ 
    public final void setNextPartitionType(String type) { 
        fNextPartitionType = type;
    }
    
    /**
     * 
     * @return The type of partition that should come next 
     * or null if none specified.
     */
    public String getNextPartitionType() { 
        return fNextPartitionType;
    }
    
    /**
     * 
     * @param type - The type of partition that this partition opens.
     */
    public final void setOpensPartition(String type) {
        fOpensPseudoPartition = type;
    }
    
    public final String getOpensPartitionType() {
        return fOpensPseudoPartition;
    }
    
    /**
     * 
     * @param type - The type of partition that this partition closes.
     */
    public final void setClosesPartition(String type) {
        fClosesPseudoPartition = type;
    }
    
    /**
     * 
     * @return The type of partition that is closed by this partition or null
     */
    public final String getClosesPartitionType() {
        return fClosesPseudoPartition;
    }
    
    public final boolean isStartPartition() {
        return fStartPartition;
    }
    
    public final boolean isMidPartition() {
        return fMidPartition;
    }
    
    public final boolean isEndPartition() {
        return fEndPartition;
    }
    
    public final void setStartPartition(boolean value) {
        fStartPartition = value;
    }
    
    public final void setMidPartition(boolean value) {
        fMidPartition = value;
    }
    
    public final void setEndPartition(boolean value) {
        fEndPartition = value;
    }
    
    public final void setTagName(String name) {
        fTagName = name;
    }
    
    public String getTagName() {
        return fTagName;
    }
    
    public void delete() {
        //System.out.println("CFEPartition from " + offset + " to " + Integer.toString(offset + length));
        super.delete();
    }
    
    public void setPseudoPartition(boolean value) {
        fIsPseudoPartition = value;
    }
    
    public boolean isPseudoPartition() {
        return fIsPseudoPartition;
    }

}


/* 
 * CVS LOG
 * ====================================================================
 *
 * $Log: not supported by cvs2svn $
 * Revision 1.5  2005/01/30 18:54:57  smilligan
 * Committing a few more minor patches for the partitioner.
 *
 * Revision 1.4  2005/01/27 01:37:25  smilligan
 * put a band aid on the content assist for attributes and tags. It will need to be properly sorted out another time, but it basically works for now.
 *
 * Revision 1.3  2005/01/24 23:36:35  smilligan
 * Hopefully the last major change to the partitioner.
 *
 * Seems to be relatively robust and stable now.
 *
 * Revision 1.2  2005/01/21 08:25:15  smilligan
 * Re-implemented the partitioning in a slightly more robust way.
 *
 * Revision 1.1  2005/01/19 02:50:11  smilligan
 * Second commit of (now hopefully working) rewritten partitioner.
 *
 */