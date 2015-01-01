package de.fkoehne.archi.archi2prolog.tests;

import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Test;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.SolveInfo;

import com.archimatetool.model.IArchimatePackage;

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

    @Test
    public void shouldNotFindAppsWithInfrastructure() throws IOException, MalformedGoalException, NoSolutionException,
            InvalidTheoryException {
        // Given ...
        // the application component "a" from the test base class
        modelUtil.createElement(IArchimatePackage.eINSTANCE.getNode(), "N", "n");
        modelUtil.createRelationship(IArchimatePackage.eINSTANCE.getUsedByRelationship(), "usedBy", "n", "a");
        exporter.export(model);
        load();

        // When
        SolveInfo result = engine.solve("infrastructureless(X).");

        // Then
        assertFalse(result.isSuccess());
    }

    @Test
    public void shouldFindRedundantRelationships() throws IOException, MalformedGoalException, NoSolutionException,
            InvalidTheoryException {
        // Given ...
        // the application component "a" from the test base class
        modelUtil.createElement(IArchimatePackage.eINSTANCE.getNode(), "N", "n");
        modelUtil.createRelationship(IArchimatePackage.eINSTANCE.getUsedByRelationship(), "usedBy1", "n", "a");
        modelUtil.createRelationship(IArchimatePackage.eINSTANCE.getUsedByRelationship(), "usedBy2", "n", "a");
        exporter.export(model);
        load();

        // When
        SolveInfo result = engine.solve("redundantRelationship(X,Y)."); // Source / Target

        // Then
        checkThat(result).is("n");
    }
}
