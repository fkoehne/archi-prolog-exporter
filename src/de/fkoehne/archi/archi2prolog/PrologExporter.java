/**
 * This program and the accompanying materials are made available under the terms of the License which accompanies this
 * distribution in the file LICENSE.txt
 */
package de.fkoehne.archi.archi2prolog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import com.archimatetool.editor.model.IModelExporter;
import com.archimatetool.model.FolderType;
import com.archimatetool.model.IArchimateElement;
import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.IFolder;
import com.archimatetool.model.IRelationship;

/**
 * Exports archi models as prolog clauses in order to enable flexible queries and reasoning. This class is based on the
 * example provided by Phillip Beauvoir
 * 
 * @author Frank Köhne
 */
public class PrologExporter implements IModelExporter {

    String MY_EXTENSION = ".pl"; //$NON-NLS-1$

    String MY_EXTENSION_WILDCARD = "*.pl"; //$NON-NLS-1$

    private OutputStreamWriter writer;

    public PrologExporter() {

    }

    @Override
    public void export(IArchimateModel model) throws IOException {
        File file = askSaveFile();
        if (file == null) {
            return;
        }

        writer = new OutputStreamWriter(new FileOutputStream(file));
        Header header = new Header(model);
        writer.write(header.toString());

        writeFolder(model.getFolder(FolderType.BUSINESS));
        writeFolder(model.getFolder(FolderType.APPLICATION));
        writeFolder(model.getFolder(FolderType.TECHNOLOGY));
        writeFolder(model.getFolder(FolderType.CONNECTORS));
        writeFolder(model.getFolder(FolderType.MOTIVATION));
        writeFolder(model.getFolder(FolderType.RELATIONS));

        writer.close();
    }

    private void writeFolder(IFolder folder) throws IOException {
        List<EObject> list = new ArrayList<EObject>();

        getElements(folder, list);

        for (EObject eObject : list) {
            StringBuilder out = new StringBuilder();

            // Elements are represented as clauses (including their id to build on
            if (eObject instanceof IArchimateElement) {
                IArchimateElement component = (IArchimateElement) eObject;

                out.append("element('");
                out.append(normalise(component.eClass().getName().toLowerCase()));
                out.append("','");
                out.append(component.getId());
                out.append("','");
                out.append(normalise(component.getName()));

            }

            // relationships relate element clauses using their id
            if (eObject instanceof IRelationship) {
                IRelationship rel = (IRelationship) eObject;
                String relType = normalise(rel.eClass().getName().toLowerCase());

                relType = relType.substring(0, relType.length() - 11 - 1);

                out.append("relationship('");
                out.append(relType);
                out.append("','");
                out.append(rel.getId());
                out.append("','");
                out.append(rel.getSource().getId());
                out.append("','");
                out.append(rel.getTarget().getId());

            }
            out.append("').\n");
            writer.write(out.toString());
        }
    }

    private void getElements(IFolder folder, List<EObject> list) {
        for (EObject object : folder.getElements()) {
            list.add(object);
        }

        for (IFolder f : folder.getFolders()) {
            getElements(f, list);
        }
    }

    private String normalise(String s) {
        if (s == null) {
            return ""; //$NON-NLS-1$
        }

        s = s.replace("\r\n", " "); //$NON-NLS-1$ //$NON-NLS-2$

        return s;
    }

    /**
     * Ask user for file name to save to
     */
    private File askSaveFile() {
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
