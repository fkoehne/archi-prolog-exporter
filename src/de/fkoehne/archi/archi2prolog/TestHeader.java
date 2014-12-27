package de.fkoehne.archi.archi2prolog;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.impl.ArchimateFactory;

public class TestHeader {

    @Test
    public void shouldGenerateMeaningfulOutputForEmptyModels() {
        IArchimateModel emptyModel = ArchimateFactory.init().createArchimateModel();
        Header header = new Header(emptyModel);
        assertNotNull("Header could not be generated", header.toString());
        assertFalse("At least one field is empty and exported as 'null': " + header.toString(), header.toString()
                .contains("null"));
    }

}
