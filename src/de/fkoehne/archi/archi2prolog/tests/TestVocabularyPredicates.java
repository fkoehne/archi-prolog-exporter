package de.fkoehne.archi.archi2prolog.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;

import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.IArchimatePackage;
import com.archimatetool.model.impl.ArchimateFactory;

import de.fkoehne.archi.archi2prolog.PrologExporter;
import de.fkoehne.archi.archi2prolog.io.ConstantFileChooser;

public class TestVocabularyPredicates {

    private IArchimateModel model;

    private PrologExporter exporter;

    private Prolog engine;

    @Before
    public void setup() throws FileNotFoundException, IOException, InvalidTheoryException {
        // Create a dummy model
        model = ArchimateFactory.init().createArchimateModel();
        ModelUtil.createAndAddArchimateElement(model, IArchimatePackage.eINSTANCE.getApplicationComponent(), "A", "a");

        // Set up the engine with the predicates under test
        engine = new Prolog();
        Theory theory = new Theory(new FileInputStream("prolog/vocabulary.pl"));
        engine.addTheory(theory);

        // We will need the exporter to create .pl-Files of the dummy model but don't do that
        // yet, in order to allow individual tests to modify the model before exporting.
        exporter = new PrologExporter(new ConstantFileChooser());
    }

    /**
     * Load a model file into the prolog engine
     * 
     * @throws InvalidTheoryException
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void load() throws InvalidTheoryException, IOException, FileNotFoundException {
        engine.addTheory(new Theory(new FileInputStream(ConstantFileChooser.EXPORT_PL)));
    }

    @Test
    public void withoutGoalsDoesNotFindGoals() throws IOException, MalformedGoalException, NoSolutionException,
            InvalidTheoryException {
        // Given
        exporter.export(model);
        load();

        // When
        SolveInfo info = engine.solve("goal(X).");

        // Then
        assertFalse(info.isSuccess());
    }

    @Test
    public void withGoalsShouldFindGoal() throws IOException, MalformedGoalException, NoSolutionException,
            InvalidTheoryException {
        // Given
        ModelUtil.createAndAddArchimateElement(model, IArchimatePackage.eINSTANCE.getGoal(), "G", "g");
        exporter.export(model);
        load();

        // When
        SolveInfo info = engine.solve("goal(X).");

        // Then
        assertTrue(info.isSuccess());
        assertTrue(info.toString().endsWith("g"));
    }

    @Test
    public void elementsNameCanBeFoundViaId() throws IOException, MalformedGoalException, NoSolutionException,
            InvalidTheoryException {
        // Given
        exporter.export(model);
        load();

        // When
        SolveInfo info = engine.solve("named(X, 'A').");

        // Then
        assertTrue(info.isSuccess());
        assertEquals(info.getVarValue("X").toString(), "a");
    }

    @Test
    public void elementsCanBeFoundViaName() throws IOException, MalformedGoalException, NoSolutionException,
            InvalidTheoryException {
        // Given
        exporter.export(model);
        load();

        // When
        SolveInfo info = engine.solve("named(X).");

        // Then
        assertTrue(info.isSuccess());
        assertEquals(info.getVarValue("X").toString(), "'A'");
    }

}
