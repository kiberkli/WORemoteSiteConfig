package com.dyned.woremotesiteconfig.eom.migrations;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import er.extensions.jdbc.ERXSQLHelper.ColumnIndex;
import er.extensions.migration.ERXMigrationDatabase;
import er.extensions.migration.ERXMigrationIndex;
import er.extensions.migration.ERXMigrationTable;
import er.extensions.migration.ERXModelVersion;

public class WOSiteConfigStore1 extends ERXMigrationDatabase.Migration {
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
		ERXMigrationTable storedAppTable = database.existingTableNamed("StoredApp");
		storedAppTable.newStringColumn("scheduleBeginHour", 50, true);
		storedAppTable.newStringColumn("scheduleWeekDay", 50, true);
		storedAppTable.newStringColumn("scheduleEndHour", 50, true);
		storedAppTable.newStringColumn("scheduleHourlyInterval", 50, true);
		storedAppTable.newStringColumn("scheduleType", 50, true);
	}
}