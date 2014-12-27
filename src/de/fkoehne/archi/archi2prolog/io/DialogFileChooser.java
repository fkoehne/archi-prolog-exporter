package de.fkoehne.archi.archi2prolog.io;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import de.fkoehne.archi.archi2prolog.Messages;

public class DialogFileChooser implements FileChooser {

    String MY_EXTENSION = ".pl"; //$NON-NLS-1$

    String MY_EXTENSION_WILDCARD = "*.pl"; //$NON-NLS-1$

    /**
     * Ask user for file name to save to
     */
    @Override
    public File choose() {
        FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
        dialog.setText(Messages.Export);
        dialog.setFilterExtensions(new String[] { MY_EXTENSION_WILDCARD, "*.*" }); //$NON-NLS-1$
        String path = dialog.open();
        if (path == null) {
            return null;
        }

        // Only Windows adds the extension by default
        if (dialog.getFilterIndex() == 0 && !path.endsWith(MY_EXTENSION)) {
            path += MY_EXTENSION;
        }

        File file = new File(path);

        // Make sure the file does not already exist
        if (file.exists()) {
            boolean result = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), Messages.Export,
                    NLS.bind(Messages.FileAlreadyExists, file));
            if (!result) {
                return null;
            }
        }

        return file;
    }
}
