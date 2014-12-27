package de.fkoehne.archi.archi2prolog.io;

import java.io.File;

/**
 * A strategy to determine a target file to export to.
 *
 */
public interface FileChooser {

    File choose();
}
