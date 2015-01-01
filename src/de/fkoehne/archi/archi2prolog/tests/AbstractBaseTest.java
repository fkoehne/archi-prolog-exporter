package de.fkoehne.archi.archi2prolog.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;

import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.IArchimatePackage;
import com.archimatetool.model.impl.ArchimateFactory;

import de.fkoehne.archi.archi2prolog.PrologExporter;
import de.fkoehne.archi.archi2prolog.io.MockFileChooser;

public abstract class AbstractBaseTest {

    protected IArchimateModel model;

    protected PrologExporter exporter;

    protected Prolog engine;

    protected ModelUtil modelUtil;

    @Before
    public void setup() throws FileNotFoundException, IOException, InvalidTheoryException {
        // Create a dummy model
        model = ArchimateFactory.init().createArchimateModel();
        modelUtil = new ModelUtil();
        modelUtil.createElement(model, IArchimatePackage.eINSTANCE.getApplicationComponent(), "A", "a");

        // Set up the engine with the predicates under test
        engine = new Prolog();
        Theory vocTheory = new Theory(new FileInputStream("prolog/vocabulary.pl"));
        Theory consistencyTheory = new Theory(new FileInputStream("prolog/consistency.pl"));
        engine.addTheory(vocTheory);
        engine.addTheory(consistencyTheory);

        // We will need the exporter to create .pl-Files of the dummy model but don't do that
        // yet, in order to allow individual tests to modify the model before exporting.
        exporter = new PrologExporter(new MockFileChooser());
    }

    /**
     * Load a model into the prolog engine - here we assume that the MockFileChooser is in use.
     * 
     * @throws InvalidTheoryException
     * @throws IOException
     * @throws FileNotFoundException
     */
    protected void load() throws InvalidTheoryException, IOException, FileNotFoundException {
        engine.addTheory(new Theory(exporter.getWriter().toString()));
    }

    /**
     * Convenience method - chaining pattern to make comparisons easy to read.
     * 
     * @param result
     * @return A Checker that performs JUnit assertions.
     */
    protected Checker checkThat(SolveInfo result) {
        return new Checker(result);
    }

    class Checker {

        private SolveInfo result;

        Checker(SolveInfo result) {
            this.result = result;
        }

        /**
         * Make comparisons of solutions with an expected result easily readable. We assume that the variable to compare
         * is queried as X.
         * 
         * @param string
         *            Compare the result with the expectation using JUnit means. Comparison ignores whether atoms are
         *            returned or not.
         * @throws NoSolutionException
         * @throws NoMoreSolutionException
         */
        public boolean is(final String expected) {

            assertTrue("No solution found (or even the predicate could not be found)", result.isSuccess());
            if (!expected.contains(",") && !result.hasOpenAlternatives()) { // single result
                try {
                    assertEquals(expected, result.getVarValue("X").toString().replaceAll("'", ""));
                } catch (NoSolutionException e) {
                    org.junit.Assert.fail("There is no solution");
                }
            } else {

                Set<String> resultSet = new HashSet<String>();
                Set<String> expectedSet = new HashSet<String>();
                Set<String> missingSet = new HashSet<String>();
                Set<String> unexpectedSet = new HashSet<String>();

                // See what we wanted
                String[] expectedArray = expected.split(",");
                for (String exp : expectedArray) {
                    expectedSet.add(exp);
                }

                // See what we have
                try {
                    resultSet.add(result.getVarValue("X").toString().replaceAll("'", ""));
                    while (result.hasOpenAlternatives()) {
                        result = engine.solveNext();
                        resultSet.add(result.getVarValue("X").toString().replaceAll("'", ""));
                    }
                } catch (NoSolutionException e) {
                    System.out.println(e);
                } catch (NoMoreSolutionException e) {
                    System.out.println(e);
                }

                // Check if everything expected is present
                for (String exp : expectedSet) {
                    if (!resultSet.contains(exp)) {
                        missingSet.add(exp);
                    }
                }

                // Check if anything is present which we did not expect
                for (String res : resultSet) {
                    if (!expectedSet.contains(res)) {
                        unexpectedSet.add(res);
                    }
                }

                assertTrue("Some of the elements expected (" + expected + ") are missing: \n" + missingSet,
                        missingSet.isEmpty());
                assertTrue("Looking for " + expected + " only, but some more elements were returned unexpectedly: \n"
                        + unexpectedSet, unexpectedSet.isEmpty());
            }

            return true; // Otherwise this line would not be reached
        }
    }

}
