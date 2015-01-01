/**
 * This program and the accompanying materials are made available under the terms of the License which accompanies this
 * distribution in the file LICENSE.txt
 */
package de.fkoehne.archi.archi2prolog;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.archimatetool.editor.model.IModelExporter;
import com.archimatetool.model.FolderType;
import com.archimatetool.model.IArchimateElement;
import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.IFolder;
import com.archimatetool.model.IRelationship;

import de.fkoehne.archi.archi2prolog.io.DialogFileChooser;
import de.fkoehne.archi.archi2prolog.io.FileChooser;

/**
 * Exports archi models as prolog clauses in order to enable flexible queries and reasoning. This class is based on the
 * example provided by Phillip Beauvoir
 * 
 * @author Frank Köhne
 */
public class PrologExporter implements IModelExporter {

    protected Writer writer;

    /**
     * This object is used to determine the target file for the export.
     */
    private final FileChooser fileChooser;

    public PrologExporter() {
        fileChooser = new DialogFileChooser();
    }

    public PrologExporter(final FileChooser chooser) {
        this.fileChooser = chooser;
    }

    @Override
    public void export(final IArchimateModel model) throws IOException {
        writer = fileChooser.chooseFileAndCreateWriter();
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

    private void writeFolder(final IFolder folder) throws IOException {
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

    public Writer getWriter() {
        return writer;
    }

}
