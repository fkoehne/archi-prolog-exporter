package de.fkoehne.archi.archi2prolog.io;

import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.io.Writer;

public class MockFileChooser implements FileChooser {

    public static final String EXPORT_PL = "export.pl";

    @Override
    public Writer chooseFileAndCreateWriter() throws FileNotFoundException {
        return new StringWriter();
    }
}
