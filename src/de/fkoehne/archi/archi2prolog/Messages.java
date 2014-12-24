/**
 * This program and the accompanying materials are made available under the terms of the License which accompanies this
 * distribution in the file LICENSE.txt
 */
package de.fkoehne.archi.archi2prolog;

import org.eclipse.osgi.util.NLS;

/**
 * This class provides natural language support and encapsulates all texts in the user interface.
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "de.fkoehne.archi.archi2prolog.messages"; //$NON-NLS-1$

    public static String Export;

    public static String FileAlreadyExists;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
