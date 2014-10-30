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

import java.nio.charset.Charset;
import org.openrdf.rio.RDFFormat;

/**
 * CLP format.
 * 
 * @author Aitor Gómez Goiri
 */
public class CLPFormat extends RDFFormat {

	public static RDFFormat CLP = new CLPFormat();
	
	private CLPFormat() {
		super("CLP", "text/vnd.clp+plain", Charset.forName("US-ASCII"), "clp", false, false);
	}
}
