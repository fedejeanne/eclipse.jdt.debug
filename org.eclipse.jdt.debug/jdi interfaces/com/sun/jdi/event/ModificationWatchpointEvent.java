package com.sun.jdi.event;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.request.*;

public interface ModificationWatchpointEvent extends com.sun.jdi.event.WatchpointEvent {
	public com.sun.jdi.Value valueToBe();
}
