package edu.gatech.cs6300.testcases;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for edu.gatech.cc.cs6300.testcases");
		//$JUnit-BEGIN$
		suite.addTestSuite(SessionTest.class);
		suite.addTestSuite(GradesDBTest.class);
		suite.addTestSuite(GradesGUITest.class);
		//$JUnit-END$
		return suite;
	}

}
