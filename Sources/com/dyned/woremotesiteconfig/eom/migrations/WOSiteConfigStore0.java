package com.dyned.woremotesiteconfig.eom.migrations;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import er.extensions.migration.ERXMigrationDatabase;
import er.extensions.migration.ERXMigrationTable;
import er.extensions.migration.ERXModelVersion;

public class WOSiteConfigStore0 extends ERXMigrationDatabase.Migration {
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
    ERXMigrationTable storedSiteTable = database.newTableNamed("StoredSite");
    storedSiteTable.newIntegerColumn("id", false);
    storedSiteTable.newStringColumn("jmHost", 50, false);
    storedSiteTable.newStringColumn("jmPassword", 50, true);
    storedSiteTable.newStringColumn("jmPort", 50, true);
    storedSiteTable.newLargeStringColumn("name", true);
    storedSiteTable.newLargeStringColumn("notes", true);
    storedSiteTable.create();
    storedSiteTable.setPrimaryKey("id");

    ERXMigrationTable storedInstanceHostTable = database.newTableNamed("StoredInstanceHost");
    storedInstanceHostTable.newStringColumn("hostName", 50, false);
    storedInstanceHostTable.newIntegerColumn("id", false);
    storedInstanceHostTable.newIntegerColumn("idStoredApp", false);
    storedInstanceHostTable.newIntegerColumn("instances", false);
    storedInstanceHostTable.create();
    storedInstanceHostTable.setPrimaryKey("id");

    ERXMigrationTable timePointApplicationTable = database.newTableNamed("TimePointApplication");
    timePointApplicationTable.newIntegerColumn("id", false);
    timePointApplicationTable.newIntegerColumn("idStoredApp", false);
    timePointApplicationTable.newIntegerColumn("idTimePoint", false);
    timePointApplicationTable.newIntegerColumn("instanceWeight", false);
    timePointApplicationTable.create();
    timePointApplicationTable.setPrimaryKey("id");

    ERXMigrationTable timePointTable = database.newTableNamed("TimePoint");
    timePointTable.newIntegerColumn("hour", false);
    timePointTable.newIntegerColumn("id", false);
    timePointTable.newIntegerColumn("idStoredSite", false);
    timePointTable.newIntegerColumn("min", false);
    timePointTable.newLargeStringColumn("notes", true);
    timePointTable.newStringColumn("title", 50, false);
    timePointTable.create();
    timePointTable.setPrimaryKey("id");

    ERXMigrationTable storedAppTable = database.newTableNamed("StoredApp");
    storedAppTable.newIntegerColumn("id", false);
    storedAppTable.newIntegerColumn("idStoredSite", false);
    storedAppTable.newIntegerColumn("instanceInterleave", true);
    storedAppTable.newLargeStringColumn("jsonString", true);
    storedAppTable.newStringColumn("name", 50, false);
    storedAppTable.create();
    storedAppTable.setPrimaryKey("id");

    storedInstanceHostTable.addForeignKey("idStoredApp", "StoredApp", "id");
    timePointApplicationTable.addForeignKey("idStoredApp", "StoredApp", "id");
    timePointApplicationTable.addForeignKey("idTimePoint", "TimePoint", "id");
    timePointTable.addForeignKey("idStoredSite", "StoredSite", "id");
    storedAppTable.addForeignKey("idStoredSite", "StoredSite", "id");
  }
  
}