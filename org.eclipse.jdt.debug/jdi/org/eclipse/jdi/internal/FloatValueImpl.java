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
public class FloatValueImpl extends PrimitiveValueImpl implements FloatValue {
	/** JDWP Tag. */
	public static final byte tag = JdwpID.FLOAT_TAG;

	/**
	 * Creates new instance.
	 */
	public FloatValueImpl(VirtualMachineImpl vmImpl, Float value) {
		super("FloatValue", vmImpl, value);
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
		return new FloatTypeImpl(virtualMachineImpl());
	}

	/**
	 * @returns Value.
	 */
	public float value() {
		return floatValue();
	}
	
	/**
	 * @return Reads and returns new instance.
	 */
	public static FloatValueImpl read(MirrorImpl target, DataInputStream in) throws IOException {
		VirtualMachineImpl vmImpl = target.virtualMachineImpl();
		float value = target.readFloat("floatValue", in);
		return new FloatValueImpl(vmImpl, new Float(value));
	}
	
	/**
	 * Writes value without value tag.
	 */
	public void write(MirrorImpl target, DataOutputStream out) throws IOException {
		target.writeFloat(((Float)fValue).floatValue(), "floatValue", out);
	}
}
