package com.sun.jdi;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import com.sun.jdi.connect.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

public interface InterfaceType extends com.sun.jdi.ReferenceType {
	public java.util.List implementors();
	public java.util.List subinterfaces();
	public java.util.List superinterfaces();
}
