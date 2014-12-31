package de.fkoehne.archi.archi2prolog.tests;

import java.io.IOException;

import org.junit.Test;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.SolveInfo;

public class TestConsistencyPredicates extends AbstractBaseTest {

    @Test
    public void shouldFindAppsWithoutInfrastructure() throws IOException, MalformedGoalException, NoSolutionException,
            InvalidTheoryException {
        // Given ...
        // the application component "a" from the test base class
        exporter.export(model);
        load();

        // When
        SolveInfo result = engine.solve("infrastructureless(X).");

        // Then
        checkThat(result).is("a");
    }

}
