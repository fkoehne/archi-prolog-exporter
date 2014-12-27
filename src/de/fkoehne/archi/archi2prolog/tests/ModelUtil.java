package de.fkoehne.archi.archi2prolog.tests;

import org.eclipse.emf.ecore.EClass;

import com.archimatetool.model.IArchimateElement;
import com.archimatetool.model.IArchimateFactory;
import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.IArchimatePackage;
import com.archimatetool.model.IFolder;

public class ModelUtil {

    public static IArchimateElement createAndAddArchimateElement(IArchimateModel model, EClass type, String name,
            String id) {
        if (!IArchimatePackage.eINSTANCE.getArchimateElement().isSuperTypeOf(type)) {
            throw new IllegalArgumentException("Eclass type should be of archimate element type");
        }

        IArchimateElement element = (IArchimateElement) IArchimateFactory.eINSTANCE.create(type);
        element.setName(name);
        element.setId(id);
        IFolder folder = model.getDefaultFolderForElement(element);
        folder.getElements().add(element);

        return element;
    }
}
