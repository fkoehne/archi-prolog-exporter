package de.fkoehne.archi.archi2prolog;

import java.util.Date;

import com.archimatetool.model.IArchimateModel;

/**
 * This class builds the header on top of a prolog export of an Archi model.
 *
 */
public class Header {

    private static final StringBuffer buf = new StringBuffer();

    public Header(IArchimateModel model) {
        buf.append("/*\n");
        buf.append("This a prolog representation of an Archi model (http://www.archimatetool.com).\n");
        buf.append("It was created using the prolog exporter (https://github.com/fkoehne/archi-prolog-exporter).\n");
        buf.append("Export date: ");
        buf.append(new Date());
        buf.append("\n");
        buf.append("Model: ");
        buf.append(model.getName());
        buf.append("\n");
        buf.append("*/\n");

        buf.append(":- use_module(vocabulary).\n\n");
        buf.append(":- use_module(consistency).\n\n");
    }

    @Override
    public String toString() {

        return buf.toString();
    }
}
