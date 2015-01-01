package de.fkoehne.archi.archi2prolog.tests;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;

import com.archimatetool.model.IArchimateElement;
import com.archimatetool.model.IArchimateFactory;
import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.IArchimatePackage;
import com.archimatetool.model.IFolder;
import com.archimatetool.model.IRelationship;

/**
 * Convenience class to make the creation of test scenarios easier.
 *
 */
public class ModelUtil {

    protected final Map<String, IArchimateElement> lookup = new HashMap<>();

    protected final IArchimateModel model;

    public ModelUtil(final IArchimateModel model) {
        this.model = model;
    }

    public IArchimateElement createElement(EClass type, String name, String id) {
        if (!IArchimatePackage.eINSTANCE.getArchimateElement().isSuperTypeOf(type)) {
            throw new IllegalArgumentException("Eclass type should be of archimate element type");
        }

        IArchimateElement element = (IArchimateElement) IArchimateFactory.eINSTANCE.create(type);
        element.setName(name);
        element.setId(id);
        IFolder folder = model.getDefaultFolderForElement(element);
        folder.getElements().add(element);

        lookup.put(id, element);

        return element;
    }

    public IRelationship createRelationship(EClass type, String name, String id, IArchimateElement source,
            IArchimateElement target) {
        if (!IArchimatePackage.eINSTANCE.getRelationship().isSuperTypeOf(type)) {
            throw new IllegalArgumentException("Eclass type should be of relationship type");
        }
        IRelationship relationship = (IRelationship) IArchimateFactory.eINSTANCE.create(type);
        relationship.setName(name);
        relationship.setId(id);
        relationship.setSource(source);
        relationship.setTarget(target);
        IFolder folder = model.getDefaultFolderForElement(relationship);
        folder.getElements().add(relationship);

        return relationship;
    }

    public IRelationship createRelationship(EClass type, String id, String source, String target) {
        IRelationship relationship = createRelationship(type, "Name_" + id, id, lookup.get(source), lookup.get(target));

        return relationship;
    }

}
