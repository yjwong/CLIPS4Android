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

import java.util.HashSet;
import java.util.Set;

import org.openrdf.model.Namespace;
import org.openrdf.model.impl.NamespaceImpl;
import org.openrdf.model.vocabulary.OWL;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;

/**
 * Class to add the Namespaces used by our CLIPS rules or any other
 * prefix which can contribute to reduce the length of CLIPS asserts.
 * 
 * These namespaces must be retained to interpret the results of the reasoning.
 * 
 * @author Aitor Gómez Goiri
 */
public class NamespacesFactory {

	public static Set<Namespace> createNamespacesUsedByDefaultInOurCLIPSRules() {
		Set<Namespace> namespaces = new HashSet<Namespace>();
		namespaces.add( new NamespaceImpl("rdf", RDF.NAMESPACE) );
		namespaces.add( new NamespaceImpl("rdfs", RDFS.NAMESPACE) );
		namespaces.add( new NamespaceImpl("owl", OWL.NAMESPACE) );
		return namespaces;
	}

}