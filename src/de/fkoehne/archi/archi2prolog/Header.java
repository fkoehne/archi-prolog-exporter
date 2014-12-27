package de.fkoehne.archi.archi2prolog;

import java.util.Date;

import com.archimatetool.model.IArchimateModel;

/**
 * This class builds the header on top of a prolog export of an Archi model.
 *
 */
public class Header {

    private static final StringBuffer buf = new StringBuffer();

    /**
     * @param model
     *            The Archimate model is provided in order to access and print meta data.
     */
    public Header(final IArchimateModel model) {
        buf.append("/*\n");
        buf.append("This a prolog representation of an Archi model (http://www.archimatetool.com).\n");
        buf.append("It was created using the prolog exporter plugin (https://github.com/fkoehne/archi-prolog-exporter).\n");
        buf.append("The syntax (esp. the module syntax) is tested with SWI-Prolog, but should work with other engines as well.\n");

        buf.append("\nExport date: ");
        buf.append(new Date());
        buf.append("\n");
        buf.append("Model: ");
        buf.append(model.getName());
        buf.append("\n");
        buf.append(model.getPurpose() == null ? "[undocumented]" : model.getPurpose());
        buf.append("\n");
        buf.append("*/\n");

        buf.append(":- use_module(vocabulary).\n");
        buf.append(":- use_module(traversal).\n");
        buf.append(":- use_module(consistency).\n\n");
    }

    @Override
    public String toString() {
        return buf.toString();
    }
}
