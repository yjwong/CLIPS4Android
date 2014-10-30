RDF for CLIPS parser/serializer
===============================

Completely unstandard CLIPS-suitable semantic format's parser and serializer for [Sesame's Rio](http://www.openrdf.org/doc/sesame2/2.7.0-beta1/users/ch09.html).

This format is based on NTriples.
Therefore, we used Aduna's NTriples serializer and parser as a baseline.
Most of the credit goes for them.

Regarding our format
--------------------
 
 * A RDF triple in our CLIPS format must have the following shape: *(. subject predicate object )*
  * The subject's, predicate's and object's serializations are the same as in [N-Triples](http://www.w3.org/2001/sw/RDFCore/ntriples/).
  * The URIs can be shortened using prefixes which are hidden to CLIPS in the CLP serialization.
    * This means that we must retain this correspondences to extend the URIs after CLIPS reasoning.
    * In other words, in our format we hide the namespace's values, so CLIPS doesn't know them.
 * This format is used in conjuntion with some RDFS and OWL rules also provided in this project.


Compilation
-----------

This project is [mavenized](http://maven.apache.org/) so feel free to use:

>   mvn clean
>
>   mvn build
>
>   mvn package

Or any other common [Maven goals](http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html).


Usage example
-------------

You may want to check this [Android application](http://github.com/gomezgoiri/CLIPS4Android/tree/master/examples/SemanticReasoningClipsDemo).
