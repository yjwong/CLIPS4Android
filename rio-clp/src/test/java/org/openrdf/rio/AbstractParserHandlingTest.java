/* 
 * Licensed to Aduna under one or more contributor license agreements.  
 * See the NOTICE.txt file distributed with this work for additional 
 * information regarding copyright ownership. 
 *
 * Aduna licenses this file to you under the terms of the Aduna BSD 
 * License (the "License"); you may not use this file except in compliance 
 * with the License. See the LICENSE.txt file distributed with this work 
 * for the full License.
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.openrdf.rio;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openrdf.model.Model;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.util.ModelUtil;
import org.openrdf.model.vocabulary.DC;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.rio.helpers.BasicParserSettings;
import org.openrdf.rio.helpers.ParseErrorCollector;
import org.openrdf.rio.helpers.StatementCollector;

/**
 * Abstract tests to confirm consistent behaviour for the datatype and language
 * handling settings.
 * 
 * @author Peter Ansell
 */
public abstract class AbstractParserHandlingTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Base URI for test parsing.
	 */
	private static final String BASE_URI = "urn:test:base:";

	/**
	 * Test value used for testing unknown datatype value handling.
	 */
	private static final String UNKNOWN_DATATYPE_VALUE = "test unknown datatype literal value";

	/**
	 * Test URI used for testing unknown datatype support.
	 */
	private static final URI UNKNOWN_DATATYPE_URI = ValueFactoryImpl.getInstance().createURI(
			"urn:test:unknowndatatype");

	/**
	 * Test value used for testing unknown datatype value handling.
	 */
	private static final String KNOWN_DATATYPE_VALUE = "31415926";

	/**
	 * Test URI used for testing known datatype support.
	 * <p>
	 * This may be anything, but it must match with the given
	 * {@link DatatypeHandler}.
	 */
	private static final URI KNOWN_DATATYPE_URI = XMLSchema.INTEGER;

	/**
	 * Test value used for testing unknown language support.
	 */
	private static final String UNKNOWN_LANGUAGE_VALUE = "xsdfsawreaewraew";

	/**
	 * Test Language tag used for testing unknown language support.
	 */
	private static final String UNKNOWN_LANGUAGE_TAG = "fakelanguage123";

	/**
	 * Test value used for testing known language value handling.
	 */
	private static final String KNOWN_LANGUAGE_VALUE = "G'day mate";

	/**
	 * Test Language tag used for testing known language support.
	 */
	private static final String KNOWN_LANGUAGE_TAG = "en-AU";

	/**
	 * Fixed new instance of {@link ValueFactoryImpl} to isolate tests.
	 */
	private final ValueFactory vf = new ValueFactoryImpl();

	private RDFParser testParser;

	private ParseErrorCollector testListener;

	private Model testStatements;

	/**
	 * Returns an {@link InputStream} containing the given RDF statements in a
	 * format that is recognised by the RDFParser returned by
	 * {@link #getParser()}.
	 * 
	 * @param unknownDatatypeStatements
	 *        A {@link Model} containing statements which all contain unknown
	 *        datatypes.
	 * @return An InputStream based on the given parameters.
	 */
	protected abstract InputStream getUnknownDatatypeStream(Model unknownDatatypeStatements)
		throws Exception;

	protected abstract InputStream getKnownDatatypeStream(Model knownDatatypeStatements)
		throws Exception;

	protected abstract InputStream getUnknownLanguageStream(Model unknownLanguageStatements)
		throws Exception;

	protected abstract InputStream getKnownLanguageStream(Model knownLanguageStatements)
		throws Exception;

	protected abstract RDFParser getParser();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp()
		throws Exception
	{
		testParser = getParser();

		testParser.setValueFactory(vf);
		testListener = new ParseErrorCollector();
		testStatements = new LinkedHashModel();

		testParser.setParseErrorListener(testListener);
		testParser.setRDFHandler(new StatementCollector(testStatements));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown()
		throws Exception
	{
		testListener.reset();
		testListener = null;
		testStatements.clear();
		testStatements = null;

		testParser = null;
	}

	/**
	 * Tests whether an unknown datatype with the default settings will both
	 * generate no message and not fail.
	 */
	@Test
	public final void testUnknownDatatypeNoMessageNoFailCase1()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_DATATYPE_VALUE, UNKNOWN_DATATYPE_URI);
		InputStream input = getUnknownDatatypeStream(expectedModel);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown datatype with the default settings (using
	 * {@link ParserConfig#useDefaults()}) will both generate no message and not
	 * fail.
	 */
	@Test
	public final void testUnknownDatatypeNoMessageNoFailCase2()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_DATATYPE_VALUE, UNKNOWN_DATATYPE_URI);
		InputStream input = getUnknownDatatypeStream(expectedModel);

		testParser.getParserConfig().useDefaults();

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown datatype with the correct settings will both
	 * generate no message and not fail.
	 */
	@Test
	public final void testUnknownDatatypeNoMessageNoFailCase3()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_DATATYPE_VALUE, UNKNOWN_DATATYPE_URI);
		InputStream input = getUnknownDatatypeStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, false);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown datatype with the correct settings will both
	 * generate no message and not fail when addNonFatalError is called with the
	 * given setting.
	 */
	@Test
	public final void testUnknownDatatypeNoMessageNoFailCase4()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_DATATYPE_VALUE, UNKNOWN_DATATYPE_URI);
		InputStream input = getUnknownDatatypeStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, false);
		testParser.getParserConfig().addNonFatalError(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown datatype with the correct settings will both
	 * generate no message and not fail when setNonFatalError is called with an
	 * empty set to reset the fatal errors
	 */
	@Test
	public final void testUnknownDatatypeNoMessageNoFailCase5()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_DATATYPE_VALUE, UNKNOWN_DATATYPE_URI);
		InputStream input = getUnknownDatatypeStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, false);
		testParser.getParserConfig().setNonFatalErrors(new HashSet<RioSetting<?>>());

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown datatype with the message no fail.
	 */
	@Test
	public final void testUnknownDatatypeWithMessageNoFailCase1()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_DATATYPE_VALUE, UNKNOWN_DATATYPE_URI);
		InputStream input = getUnknownDatatypeStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, true);
		testParser.getParserConfig().addNonFatalError(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 1, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown datatype with the message no fail.
	 */
	@Test
	public final void testUnknownDatatypeWithMessageNoFailCase2()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_DATATYPE_VALUE, UNKNOWN_DATATYPE_URI);
		InputStream input = getUnknownDatatypeStream(expectedModel);

		testParser.getParserConfig().useDefaults();
		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, true);
		testParser.getParserConfig().addNonFatalError(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 1, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown datatype with the message no fail.
	 */
	@Test
	public final void testUnknownDatatypeWithMessageNoFailCase3()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_DATATYPE_VALUE, UNKNOWN_DATATYPE_URI);
		InputStream input = getUnknownDatatypeStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, true);
		testParser.getParserConfig().setNonFatalErrors(
				Collections.<RioSetting<?>> singleton(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES));

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 1, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown datatype with the message and with a failure.
	 */
	@Test
	public final void testUnknownDatatypeWithMessageWithFailCase1()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_DATATYPE_VALUE, UNKNOWN_DATATYPE_URI);
		InputStream input = getUnknownDatatypeStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, true);

		try {
			testParser.parse(input, BASE_URI);
			fail("Did not receive expected exception");
		}
		catch (RDFParseException e) {
			// expected
		}

		assertErrorListener(0, 1, 0);
		assertModel(new LinkedHashModel());
	}

	/**
	 * Tests whether an known datatype with the default settings will both
	 * generate no message and not fail.
	 */
	@Test
	public final void testKnownDatatypeNoMessageNoFailCase1()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_DATATYPE_VALUE, KNOWN_DATATYPE_URI);
		InputStream input = getKnownDatatypeStream(expectedModel);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known datatype with the default settings (using
	 * {@link ParserConfig#useDefaults()}) will both generate no message and not
	 * fail.
	 */
	@Test
	public final void testKnownDatatypeNoMessageNoFailCase2()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_DATATYPE_VALUE, KNOWN_DATATYPE_URI);
		InputStream input = getKnownDatatypeStream(expectedModel);

		testParser.getParserConfig().useDefaults();

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known datatype with the correct settings will both
	 * generate no message and not fail.
	 */
	@Test
	public final void testKnownDatatypeNoMessageNoFailCase3()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_DATATYPE_VALUE, KNOWN_DATATYPE_URI);
		InputStream input = getKnownDatatypeStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, false);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known datatype with the correct settings will both
	 * generate no message and not fail when addNonFatalError is called with the
	 * given setting.
	 */
	@Test
	public final void testKnownDatatypeNoMessageNoFailCase4()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_DATATYPE_VALUE, KNOWN_DATATYPE_URI);
		InputStream input = getKnownDatatypeStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, false);
		testParser.getParserConfig().addNonFatalError(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known datatype with the correct settings will both
	 * generate no message and not fail when setNonFatalError is called with an
	 * empty set to reset the fatal errors
	 */
	@Test
	public final void testKnownDatatypeNoMessageNoFailCase5()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_DATATYPE_VALUE, KNOWN_DATATYPE_URI);
		InputStream input = getKnownDatatypeStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, false);
		testParser.getParserConfig().setNonFatalErrors(new HashSet<RioSetting<?>>());

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known datatype with the message no fail.
	 */
	@Test
	public final void testKnownDatatypeWithMessageNoFailCase1()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_DATATYPE_VALUE, KNOWN_DATATYPE_URI);
		InputStream input = getKnownDatatypeStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, true);
		testParser.getParserConfig().addNonFatalError(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known datatype with the message no fail.
	 */
	@Test
	public final void testKnownDatatypeWithMessageNoFailCase2()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_DATATYPE_VALUE, KNOWN_DATATYPE_URI);
		InputStream input = getKnownDatatypeStream(expectedModel);

		testParser.getParserConfig().useDefaults();
		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, true);
		testParser.getParserConfig().addNonFatalError(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known datatype with the message no fail.
	 */
	@Test
	public final void testKnownDatatypeWithMessageNoFailCase3()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_DATATYPE_VALUE, KNOWN_DATATYPE_URI);
		InputStream input = getKnownDatatypeStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, true);
		testParser.getParserConfig().setNonFatalErrors(
				Collections.<RioSetting<?>> singleton(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES));

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known datatype with the message which generates a failure
	 * if the datatype is unknown.
	 */
	@Test
	public final void testKnownDatatypeWithMessageWhereUnknownWouldFailCase1()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_DATATYPE_VALUE, KNOWN_DATATYPE_URI);
		InputStream input = getKnownDatatypeStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, true);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown language with the default settings will both
	 * generate no message and not fail.
	 */
	@Test
	public final void testUnknownLanguageNoMessageNoFailCase1()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_LANGUAGE_VALUE, UNKNOWN_LANGUAGE_TAG);
		InputStream input = getUnknownLanguageStream(expectedModel);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown language with the default settings (using
	 * {@link ParserConfig#useDefaults()}) will both generate no message and not
	 * fail.
	 */
	@Test
	public final void testUnknownLanguageNoMessageNoFailCase2()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_LANGUAGE_VALUE, UNKNOWN_LANGUAGE_TAG);
		InputStream input = getUnknownLanguageStream(expectedModel);

		testParser.getParserConfig().useDefaults();

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown language with the correct settings will both
	 * generate no message and not fail.
	 */
	@Test
	public final void testUnknownLanguageNoMessageNoFailCase3()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_LANGUAGE_VALUE, UNKNOWN_LANGUAGE_TAG);
		InputStream input = getUnknownLanguageStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, false);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown language with the correct settings will both
	 * generate no message and not fail when addNonFatalError is called with the
	 * given setting.
	 */
	@Test
	public final void testUnknownLanguageNoMessageNoFailCase4()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_LANGUAGE_VALUE, UNKNOWN_LANGUAGE_TAG);
		InputStream input = getUnknownLanguageStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, false);
		testParser.getParserConfig().addNonFatalError(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown language with the correct settings will both
	 * generate no message and not fail when setNonFatalError is called with an
	 * empty set to reset the fatal errors
	 */
	@Test
	public final void testUnknownLanguageNoMessageNoFailCase5()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_LANGUAGE_VALUE, UNKNOWN_LANGUAGE_TAG);
		InputStream input = getUnknownLanguageStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, false);
		testParser.getParserConfig().setNonFatalErrors(new HashSet<RioSetting<?>>());

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown language with the message no fail.
	 */
	@Test
	public final void testUnknownLanguageWithMessageNoFailCase1()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_LANGUAGE_VALUE, UNKNOWN_LANGUAGE_TAG);
		InputStream input = getUnknownLanguageStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, true);
		testParser.getParserConfig().addNonFatalError(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 1, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown language with the message no fail.
	 */
	@Test
	public final void testUnknownLanguageWithMessageNoFailCase2()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_LANGUAGE_VALUE, UNKNOWN_LANGUAGE_TAG);
		InputStream input = getUnknownLanguageStream(expectedModel);

		testParser.getParserConfig().useDefaults();
		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, true);
		testParser.getParserConfig().addNonFatalError(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 1, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown language with the message no fail.
	 */
	@Test
	public final void testUnknownLanguageWithMessageNoFailCase3()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_LANGUAGE_VALUE, UNKNOWN_LANGUAGE_TAG);
		InputStream input = getUnknownLanguageStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, true);
		testParser.getParserConfig().setNonFatalErrors(
				Collections.<RioSetting<?>> singleton(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES));

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 1, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an unknown language with the message and with a failure.
	 */
	@Test
	public final void testUnknownLanguageWithMessageWithFailCase1()
		throws Exception
	{
		Model expectedModel = getTestModel(UNKNOWN_LANGUAGE_VALUE, UNKNOWN_LANGUAGE_TAG);
		InputStream input = getUnknownLanguageStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, true);

		try {
			testParser.parse(input, BASE_URI);
			fail("Did not receive expected exception");
		}
		catch (RDFParseException e) {
			// expected
		}

		assertErrorListener(0, 1, 0);
		assertModel(new LinkedHashModel());
	}

	/**
	 * Tests whether an known language with the default settings will both
	 * generate no message and not fail.
	 */
	@Test
	public final void testKnownLanguageNoMessageNoFailCase1()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_LANGUAGE_VALUE, KNOWN_LANGUAGE_TAG);
		InputStream input = getKnownLanguageStream(expectedModel);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known language with the default settings (using
	 * {@link ParserConfig#useDefaults()}) will both generate no message and not
	 * fail.
	 */
	@Test
	public final void testKnownLanguageNoMessageNoFailCase2()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_LANGUAGE_VALUE, KNOWN_LANGUAGE_TAG);
		InputStream input = getKnownLanguageStream(expectedModel);

		testParser.getParserConfig().useDefaults();

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known language with the correct settings will both
	 * generate no message and not fail.
	 */
	@Test
	public final void testKnownLanguageNoMessageNoFailCase3()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_LANGUAGE_VALUE, KNOWN_LANGUAGE_TAG);
		InputStream input = getKnownLanguageStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, false);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known language with the correct settings will both
	 * generate no message and not fail when addNonFatalError is called with the
	 * given setting.
	 */
	@Test
	public final void testKnownLanguageNoMessageNoFailCase4()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_LANGUAGE_VALUE, KNOWN_LANGUAGE_TAG);
		InputStream input = getKnownLanguageStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, false);
		testParser.getParserConfig().addNonFatalError(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known language with the correct settings will both
	 * generate no message and not fail when setNonFatalError is called with an
	 * empty set to reset the fatal errors
	 */
	@Test
	public final void testKnownLanguageNoMessageNoFailCase5()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_LANGUAGE_VALUE, KNOWN_LANGUAGE_TAG);
		InputStream input = getKnownLanguageStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, false);
		testParser.getParserConfig().setNonFatalErrors(new HashSet<RioSetting<?>>());

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known language with the message no fail.
	 */
	@Test
	public final void testKnownLanguageWithMessageNoFailCase1()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_LANGUAGE_VALUE, KNOWN_LANGUAGE_TAG);
		InputStream input = getKnownLanguageStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, true);
		testParser.getParserConfig().addNonFatalError(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known language with the message no fail.
	 */
	@Test
	public final void testKnownLanguageWithMessageNoFailCase2()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_LANGUAGE_VALUE, KNOWN_LANGUAGE_TAG);
		InputStream input = getKnownLanguageStream(expectedModel);

		testParser.getParserConfig().useDefaults();
		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, true);
		testParser.getParserConfig().addNonFatalError(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known language with the message no fail.
	 */
	@Test
	public final void testKnownLanguageWithMessageNoFailCase3()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_LANGUAGE_VALUE, KNOWN_LANGUAGE_TAG);
		InputStream input = getKnownLanguageStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, true);
		testParser.getParserConfig().setNonFatalErrors(
				Collections.<RioSetting<?>> singleton(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES));

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	/**
	 * Tests whether an known language with the message which generates a failure
	 * if the language is unknown.
	 */
	@Test
	public final void testKnownLanguageWithMessageWhereUnknownWouldFailCase1()
		throws Exception
	{
		Model expectedModel = getTestModel(KNOWN_LANGUAGE_VALUE, KNOWN_LANGUAGE_TAG);
		InputStream input = getKnownLanguageStream(expectedModel);

		testParser.getParserConfig().set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, true);

		testParser.parse(input, BASE_URI);

		assertErrorListener(0, 0, 0);
		assertModel(expectedModel);
	}

	private void assertModel(Model expectedModel) {
		if(logger.isTraceEnabled()) {
			logger.trace("Expected: {}", expectedModel);
			logger.trace("Actual: {}", testStatements);
		}
		assertTrue("Did not find expected statements", ModelUtil.equals(expectedModel, testStatements));
	}

	private void assertErrorListener(int expectedWarnings, int expectedErrors, int expectedFatalErrors) {
		assertEquals("Unexpected number of warnings", expectedWarnings, testListener.getWarnings().size());
		assertEquals("Unexpected number of errors", expectedErrors, testListener.getErrors().size());
		assertEquals("Unexpected number of fatal errors", expectedFatalErrors,
				testListener.getFatalErrors().size());
	}

	private final Model getTestModel(String datatypeValue, URI datatypeURI) {
		Model result = new LinkedHashModel();
		result.add(vf.createStatement(vf.createBNode(), DC.DESCRIPTION,
				vf.createLiteral(datatypeValue, datatypeURI)));
		return result;
	}

	private final Model getTestModel(String languageValue, String languageTag) {
		Model result = new LinkedHashModel();
		result.add(vf.createStatement(vf.createBNode(), RDFS.COMMENT,
				vf.createLiteral(languageValue, languageTag)));
		return result;
	}
}
