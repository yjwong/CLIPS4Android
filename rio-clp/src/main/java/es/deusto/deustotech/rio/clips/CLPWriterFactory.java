/*
 * Copyright (C) 2013 onwards University of Deusto
 * 
 * All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.
 * 
 * This software consists of contributions made by many individuals, 
 * listed in the file NOTICE and below:
 *
 * Author: Aitor Gómez Goiri <aitor.gomez@deusto.es>
 */
package es.deusto.deustotech.rio.clips;

import java.io.OutputStream;
import java.io.Writer;

import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.RDFWriterFactory;

/**
 * An {@link RDFWriterFactory} for CLP writers.
 * 
 * @author Aitor Gómez Goiri
 */
public class CLPWriterFactory implements RDFWriterFactory {
	
	/*static {
		RDFWriterRegistry.getInstance().add(new CLPWriterFactory());
	}*/
	
	/**
	 * Returns {@link CLPFormat#CLP}.
	 */
	public RDFFormat getRDFFormat() {
		return CLPFormat.CLP;
	}

	/**
	 * Returns a new instance of {@link CLPWriter}.
	 */
	public RDFWriter getWriter(OutputStream out) {
		return new CLPWriter(out);
	}

	/**
	 * Returns a new instance of {@link CLPWriter}.
	 */
	public RDFWriter getWriter(Writer writer) {
		return new CLPWriter(writer);
	}
}