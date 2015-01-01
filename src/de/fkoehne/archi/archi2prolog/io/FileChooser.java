package de.fkoehne.archi.archi2prolog.io;

import java.io.FileNotFoundException;
import java.io.Writer;

/**
 * A strategy to determine a target file to export to.
 *
 */
public interface FileChooser {

    Writer chooseFileAndCreateWriter() throws FileNotFoundException;
}
