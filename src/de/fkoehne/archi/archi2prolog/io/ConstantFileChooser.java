package de.fkoehne.archi.archi2prolog.io;

import java.io.File;

public class ConstantFileChooser implements FileChooser {

    public static final String EXPORT_PL = "export.pl";

    @Override
    public File choose() {
        return new File(EXPORT_PL);
    }

}
