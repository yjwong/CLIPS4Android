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

import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.RDFParserFactory;

import es.deusto.deustotech.rio.clips.CLPParser;

/**
 * An {@link RDFParserFactory} for cLP parsers.
 * 
 * @author Aitor Gómez-Goiri
 */
public class CLPParserFactory implements RDFParserFactory {

	/**
	 * Returns {@link CLPFormat#CLP}.
	 */
	public RDFFormat getRDFFormat() {
		return CLPFormat.CLP;
	}

	/**
	 * Returns a new instance of NTriplesParser.
	 */
	public RDFParser getParser() {
		return new CLPParser();
	}
}
