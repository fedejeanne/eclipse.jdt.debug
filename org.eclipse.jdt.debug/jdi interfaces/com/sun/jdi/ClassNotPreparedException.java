package com.sun.jdi;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import com.sun.jdi.connect.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

public class ClassNotPreparedException extends RuntimeException 
{
	public ClassNotPreparedException() { }
	public ClassNotPreparedException(String arg1) {
		super(arg1);
	}
}
