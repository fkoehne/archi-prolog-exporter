package de.fkoehne.archi.archi2prolog.tests;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.impl.ArchimateFactory;

public class TestConsistencyPredicates {

    private IArchimateModel model;

    @Before
    public void setup() {
        model = ArchimateFactory.init().createArchimateModel();
    }

    @Test
    public void test() {
        fail("Not yet implemented");
    }

}
