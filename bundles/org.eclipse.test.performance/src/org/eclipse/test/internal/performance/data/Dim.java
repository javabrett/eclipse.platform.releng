/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.test.internal.performance.data;

import org.eclipse.test.internal.performance.InternalDimensions;
import org.eclipse.test.internal.performance.PerformanceTestPlugin;
import org.eclipse.test.performance.Dimension;

/**
 * @since 3.1
 */
public class Dim implements Dimension {
    
    private static Dim[] fgRegisteredDimensions= new Dim[100];
    
    private final int fId;
	private final Unit fUnit;
	private final int fMultiplier;
	private final boolean fLargerIsBetter= false;	// true indicates that larger values are better
	
	public static Dim getDimension(int id) {
        InternalDimensions.COMITTED.getId();	// trigger loading class InternalDimensions
	    if (id >= 0 && id < fgRegisteredDimensions.length)
	        return fgRegisteredDimensions[id];
	    return null;
	}
	
	public Dim(int id) {
		this(id, Unit.CARDINAL, 1);
	}

	public Dim(int id, Unit unit) {
		this(id, unit, 1);
	}

	public Dim(int id, Unit unit, int multiplier) {
	    
	    if (id >= 0 && id < fgRegisteredDimensions.length) {
		    if (fgRegisteredDimensions[id] == null) {
		        fgRegisteredDimensions[id]= this;
		    } else
			    PerformanceTestPlugin.logError("dimension with id '" + id + "' already registered"); //$NON-NLS-1$ //$NON-NLS-2$
	    }
		fId= id;
		fUnit= unit;
		fMultiplier= multiplier;
	}

    public int getId() {
        return fId;
    }
	
	public Unit getUnit() {
		return fUnit;
	}
	
	public int getMultiplier() {
		return fMultiplier;
	}

	public boolean largerIsBetter() {
	    return fLargerIsBetter;
	}
	
	public String getName() {
		return DimensionMessages.getString(fId);
	}
	
	public String toString() {
		return "Dimension [name=" + getName() + ", " + fUnit + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	public String getDisplayValue(Scalar scalar) {
		return fUnit.getDisplayValue1(scalar.getMagnitude(), fMultiplier);
	}
	
	public String getDisplayValue(double scalar) {
		return fUnit.getDisplayValue1(scalar / fMultiplier);
	}
}
