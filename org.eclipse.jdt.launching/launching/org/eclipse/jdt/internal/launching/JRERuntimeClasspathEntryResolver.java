package org.eclipse.jdt.internal.launching;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IRuntimeClasspathEntryResolver;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

/**
 * Resolves for JRELIB_VARIALBE and JRE_CONTAINER
 */
public class JRERuntimeClasspathEntryResolver implements IRuntimeClasspathEntryResolver {

	/**
	 * @see IRuntimeClasspathEntryResolver#resolveForClasspath(IRuntimeClasspathEntry, ILaunchConfiguration)
	 */
	public IRuntimeClasspathEntry[] resolveRuntimeClasspathEntry(IRuntimeClasspathEntry entry, ILaunchConfiguration configuration) throws CoreException {
		IRuntimeClasspathEntry[] resolved = null;
		int kind = IRuntimeClasspathEntry.STANDARD_CLASSES;
		IVMInstall configJRE = JavaRuntime.computeVMInstall(configuration);
		LibraryLocation[] libs = configJRE.getLibraryLocations();
		if (libs == null) {
			// default system libs
			libs = configJRE.getVMInstallType().getDefaultLibraryLocations(configJRE.getInstallLocation());
		} else {
			// custom system libs - place on bootpath explicitly
			kind = IRuntimeClasspathEntry.BOOTSTRAP_CLASSES;
		}
		resolved = new IRuntimeClasspathEntry[libs.length];
		for (int i = 0; i < libs.length; i++) {
			resolved[i] = JavaRuntime.newArchiveRuntimeClasspathEntry(libs[i].getSystemLibraryPath());
			resolved[i].setSourceAttachmentPath(libs[i].getSystemLibrarySourcePath());
			resolved[i].setSourceAttachmentRootPath(libs[i].getPackageRootPath());
			resolved[i].setClasspathProperty(kind);
		}
		return resolved;
	}
	
}