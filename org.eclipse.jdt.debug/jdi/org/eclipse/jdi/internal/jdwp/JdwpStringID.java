package org.eclipse.jdi.internal.jdwp;

import java.io.*;
import org.eclipse.jdi.internal.VirtualMachineImpl;

/**
 * This class implements the corresponding Java Debug Wire Protocol (JDWP) ID
 * declared by the JDWP specification.
 *
 */
public class JdwpStringID extends JdwpObjectID {
	/**
	 * Creates new JdwpID.
	 */
	public JdwpStringID(VirtualMachineImpl vmImpl) {
		super(vmImpl);
	}
}