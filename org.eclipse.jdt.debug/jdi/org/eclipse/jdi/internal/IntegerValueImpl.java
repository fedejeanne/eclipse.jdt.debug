package org.eclipse.jdi.internal;/*
 * JDI class Implementation
 *
 * (BB)
 * (C) Copyright IBM Corp. 2000
 */



import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;
import org.eclipse.jdi.internal.connect.*;
import org.eclipse.jdi.internal.request.*;
import org.eclipse.jdi.internal.event.*;
import org.eclipse.jdi.internal.jdwp.*;
import org.eclipse.jdi.internal.spy.*;
import java.util.*;
import java.io.*;

/**
 * this class implements the corresponding interfaces
 * declared by the JDI specification. See the com.sun.jdi package
 * for more information.
 *
 */
public class IntegerValueImpl extends PrimitiveValueImpl implements IntegerValue {
	/** JDWP Tag. */
	public static final byte tag = JdwpID.INT_TAG;

	/**
	 * Creates new instance.
	 */
	public IntegerValueImpl(VirtualMachineImpl vmImpl, Integer value) {
		super("IntegerValue", vmImpl, value);
	}
	
	/**
	 * @returns tag.
	 */
	public byte getTag() {
		return tag;
	}

	/**
	 * @returns type of value.
   	 */
	public Type type() {
		return new IntegerTypeImpl(virtualMachineImpl());
	}

	/**
	 * @returns Value.
	 */
	public int value() {
		return intValue();
	}
	
	/**
	 * @return Reads and returns new instance.
	 */
	public static IntegerValueImpl read(MirrorImpl target, DataInputStream in) throws IOException {
		VirtualMachineImpl vmImpl = target.virtualMachineImpl();
		int value = target.readInt("integerValue", in);
		return new IntegerValueImpl(vmImpl, new Integer(value));
	}
	
	/**
	 * Writes value without value tag.
	 */
	public void write(MirrorImpl target, DataOutputStream out) throws IOException {
		target.writeInt(((Integer)fValue).intValue(), "intValue", out);
	}
}
