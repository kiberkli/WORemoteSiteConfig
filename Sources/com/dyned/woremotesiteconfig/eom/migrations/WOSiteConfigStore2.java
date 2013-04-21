package com.dyned.woremotesiteconfig.eom.migrations;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import er.extensions.migration.ERXMigrationDatabase;
import er.extensions.migration.ERXMigrationTable;
import er.extensions.migration.ERXModelVersion;

public class WOSiteConfigStore2 extends ERXMigrationDatabase.Migration {
    @Override
    public NSArray<ERXModelVersion> modelDependencies() {
        return null;
    }

    @Override
    public void downgrade(EOEditingContext editingContext, ERXMigrationDatabase database) throws Throwable {
        // DO NOTHING
    }

    @Override
    public void upgrade(EOEditingContext editingContext, ERXMigrationDatabase database) throws Throwable {
        ERXMigrationTable storedSiteTable = database.existingTableNamed("StoredSite");
        storedSiteTable.newStringColumn("serverType", 50, true);
        storedSiteTable.create();

    }
}