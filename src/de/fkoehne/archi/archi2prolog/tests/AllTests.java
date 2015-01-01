package de.fkoehne.archi.archi2prolog.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * A suite of all tests for prolog predicates.
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ TestConsistencyPredicates.class, TestVocabularyPredicates.class })
public class AllTests {

}
