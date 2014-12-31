package de.fkoehne.archi.archi2prolog.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.SolveInfo;

import com.archimatetool.model.IArchimatePackage;

public class TestVocabularyPredicates extends AbstractBaseTest {

    @Test
    public void withoutGoalsDoesNotFindGoals() throws IOException, MalformedGoalException, NoSolutionException,
            InvalidTheoryException {
        // Given
        exporter.export(model);
        load();

        // When
        SolveInfo result = engine.solve("goal(X).");

        // Then
        assertFalse(result.isSuccess());
    }

    @Test
    public void withGoalsShouldFindGoal() throws IOException, MalformedGoalException, NoSolutionException,
            InvalidTheoryException {
        // Given
        ModelUtil.createAndAddArchimateElement(model, IArchimatePackage.eINSTANCE.getGoal(), "G", "g");
        exporter.export(model);
        load();

        // When
        SolveInfo result = engine.solve("goal(X).");

        // Then
        checkThat(result).is("g");
    }

    @Test
    public void elementsNameCanBeFoundViaId() throws IOException, MalformedGoalException, NoSolutionException,
            InvalidTheoryException {
        // Given
        exporter.export(model);
        load();

        // When
        SolveInfo result = engine.solve("named(X, 'A').");

        // Then
        checkThat(result).is("a");
    }

    @Test
    public void elementsCanBeFoundViaName() throws IOException, MalformedGoalException, NoSolutionException,
            InvalidTheoryException {
        // Given
        exporter.export(model);
        load();

        // When
        SolveInfo result = engine.solve("named(X).");

        // Then
        checkThat(result).is("A");
    }

    @Test
    public void infrastructureElementsCanBeFound() throws IOException, MalformedGoalException, NoSolutionException,
            InvalidTheoryException {
        // Given
        ModelUtil.createAndAddArchimateElement(model, IArchimatePackage.eINSTANCE.getNode(), "N", "n");
        ModelUtil.createAndAddArchimateElement(model, IArchimatePackage.eINSTANCE.getInfrastructureInterface(), "II",
                "ii");
        ModelUtil.createAndAddArchimateElement(model, IArchimatePackage.eINSTANCE.getDevice(), "D", "d");
        ModelUtil.createAndAddArchimateElement(model, IArchimatePackage.eINSTANCE.getSystemSoftware(), "S", "s");

        ModelUtil.createAndAddArchimateElement(model, IArchimatePackage.eINSTANCE.getCommunicationPath(), "C", "c");
        ModelUtil.createAndAddArchimateElement(model, IArchimatePackage.eINSTANCE.getNetwork(), "N", "n");
        ModelUtil.createAndAddArchimateElement(model, IArchimatePackage.eINSTANCE.getArtifact(), "Ar", "ar");
        ModelUtil.createAndAddArchimateElement(model, IArchimatePackage.eINSTANCE.getInfrastructureService(), "Is",
                "is");
        ModelUtil.createAndAddArchimateElement(model, IArchimatePackage.eINSTANCE.getInfrastructureFunction(), "Is",
                "is");
        exporter.export(model);
        load();

        // When
        SolveInfo result = engine.solve("infrastructure(X).");

        // Then
        assertTrue(result.isSuccess());
        checkThat(result).is("n,ii,is,d,s,c,n,ar");
    }

}
