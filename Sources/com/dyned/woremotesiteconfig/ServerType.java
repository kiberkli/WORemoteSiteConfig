/**
 * 
 */
package com.dyned.woremotesiteconfig;

import java.util.HashMap;

/**
 * Describe different types of server configuration.
 * 
 * @author jacky
 * 
 */
public enum ServerType {

    // Types definition
    // ---------------------------------------------------------------------------
    DEV("Server used typically by the developers."),
    STAGE("Server used typically for testing by the users or QA."),
    PROD("Production server, use with care.");

    // Constants definition
    // ---------------------------------------------------------------------------
    private final String                             description;

    private static final HashMap<String, ServerType> descriptionIndex = new HashMap<String, ServerType>(ServerType.values().length * 2);

    // Indexes constructors
    // ---------------------------------------------------------------------------
    static {
        for (ServerType enumTemplate : ServerType.values()) {
            descriptionIndex.put(enumTemplate.description, enumTemplate);
        }
    }

    // EnumSets accessors
    // ---------------------------------------------------------------------------
    // n/a

    // Indexes accessors
    // ---------------------------------------------------------------------------
    public static ServerType serverTypeByDescription(final String description) {
        return description == null ? null : descriptionIndex.get(description);
    }

    /**
     * Private constructor
     * 
     * @param description
     * @param color
     */
    private ServerType(final String description) {
        this.description = description;
    }

    // Getters
    // ---------------------------------------------------------------------------
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

}
