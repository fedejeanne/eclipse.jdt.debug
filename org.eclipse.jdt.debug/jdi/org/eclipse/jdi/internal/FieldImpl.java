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
public class FieldImpl extends TypeComponentImpl implements Field {
	/** ID that corresponds to this reference. */
	private JdwpFieldID fFieldID;

	/**
	 * Creates new FieldImpl.
	 */
	public FieldImpl(VirtualMachineImpl vmImpl, ReferenceTypeImpl declaringType, JdwpFieldID ID, String name, String signature, int modifierBits) {
		super("Field", vmImpl, declaringType, name, signature, modifierBits);
		fFieldID = ID;
	}
	
	/**
	 * Flushes all stored Jdwp results.
	 */
	public void flushStoredJdwpResults() {
		// Note that no results are cached.
	}
	
	/** 
	 * @return Returns fieldID of field.
	 */
	public JdwpFieldID getFieldID() {
		return fFieldID;
	}

	/**
	 * @return Returns true if two mirrors refer to the same entity in the target VM.
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		return object != null
			&& object.getClass().equals(this.getClass())
			&& fFieldID.equals(((FieldImpl)object).fFieldID)
			&& referenceTypeImpl().equals(((FieldImpl)object).referenceTypeImpl());
	}

	/**
	 * @return Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
	 */
	public int compareTo(Object object) {
		if (object == null || !object.getClass().equals(this.getClass()))
			throw new ClassCastException("Can't compare field to given object.");
		
		// See if declaring types are the same, if not return comparison between declaring types.
		Field type2 = (Field)object;
		if (!declaringType().equals(type2.declaringType()))
			return declaringType().compareTo(type2.declaringType());
		
		// Return comparison of position within declaring type.
		int index1 = declaringType().fields().indexOf(this);
		int index2 = type2.declaringType().fields().indexOf(type2);
		if (index1 < index2)
			return -1;
		else if (index1 > index2)
			return 1;
		else return 0;
	}
	
	/** 
	 * @return Returns the hash code value.
	 */
	public int hashCode() {
		return fFieldID.hashCode();
	}
	
	/**
	 * @return Returns a text representation of the declared type.
	 */
	public String typeName() {
		return TypeImpl.signatureToName(signature());
	}
	
	/** 
	 * @return Returns the type of the this Field.
	 */
	public Type type() throws ClassNotLoadedException {
		return TypeImpl.create(virtualMachineImpl(), signature(), declaringType().classLoader());
	}

	/** 
	 * @return Returns true if object is transient.
	 */
	public boolean isTransient() {
		return (modifiers() & MODIFIER_ACC_TRANSIENT) != 0;
	}
	
	/** 
	 * @return Returns true if object is volitile.
	 */
	public boolean isVolatile() {
		return (modifiers() & MODIFIER_ACC_VOLITILE) != 0;
	}
	
	/**
	 * Writes JDWP representation.
	 */
	public void write(MirrorImpl target, DataOutputStream out) throws IOException {
		fFieldID.write(out);
		if (target.fVerboseWriter != null)
			target.fVerboseWriter.println("field", fFieldID.value());
	}

	/**
	 * Writes JDWP representation, including ReferenceType.
	 */
	public void writeWithReferenceType(MirrorImpl target, DataOutputStream out) throws IOException {
		// See EventRequest case FieldOnly
		referenceTypeImpl().write(target, out);
		write(target, out);
	}

	/**
	 * @return Reads JDWP representation and returns new instance.
	 */
	public static FieldImpl readWithReferenceTypeWithTag(MirrorImpl target, DataInputStream in) throws IOException {
		VirtualMachineImpl vmImpl = target.virtualMachineImpl();
	  	// See Events FIELD_ACCESS and FIELD_MODIFICATION (refTypeTag + typeID + fieldID).
		ReferenceTypeImpl referenceType = ReferenceTypeImpl.readWithTypeTag(target, in);
		if (referenceType == null)
			return null;

		JdwpFieldID ID = new JdwpFieldID(vmImpl);
		ID.read(in);
		if (target.fVerboseWriter != null)
			target.fVerboseWriter.println("field", ID.value());

		if (ID.isNull())
			return null;
		FieldImpl field = referenceType.findField(ID);
		if (field == null)
			throw new InternalError("Got FieldID of ReferenceType that is not a member of the ReferenceType.");
		return field;
	}
	
	/**
	 * @return Reads JDWP representation and returns new instance.
	 */
	public static FieldImpl readWithNameSignatureModifiers(ReferenceTypeImpl target, ReferenceTypeImpl referenceType, DataInputStream in)  throws IOException {
		VirtualMachineImpl vmImpl = target.virtualMachineImpl();
		JdwpFieldID ID = new JdwpFieldID(vmImpl);
		ID.read(in);
		if (target.fVerboseWriter != null)
			target.fVerboseWriter.println("field", ID.value());

		if (ID.isNull())
			return null;
		String name = target.readString("name", in);
		String signature = target.readString("signature", in);
		int modifierBits = target.readInt("modifiers", AccessibleImpl.modifierVector(), in);
		
		FieldImpl mirror = new FieldImpl(vmImpl, referenceType, ID, name, signature, modifierBits);
		return mirror;
	}
}
