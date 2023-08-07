/* This file is part of the db4o object database http://www.db4o.com

Copyright (C) 2004 - 2011  Versant Corporation http://www.versant.com

db4o is free software; you can redistribute it and/or modify it under
the terms of version 3 of the GNU General Public License as published
by the Free Software Foundation.

db4o is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License along
with this program.  If not, see http://www.gnu.org/licenses/. */
package com.db4o.db4ounit.common.assorted;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.io.*;
import com.db4o.reflect.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class KnownClassesIndexTestCase implements TestLifeCycle {

	private static final String DB_PATH = "inmem";
	private Storage _storage = new MemoryStorage();
	
	public static class WithIndex {
		public int _id;

		public WithIndex(int id) {
			_id = id;
		}
	}

	@Override
	public void setUp() throws Exception {
		EmbeddedObjectContainer db = Db4oEmbedded.openFile(config(), DB_PATH);
		db.store(new WithIndex(42));
		db.close();
	}

	private EmbeddedConfiguration config() {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().objectClass(WithIndex.class).objectField("_id").indexed(true);
		config.file().storage(_storage);
		return config;
	}

	@Override
	public void tearDown() throws Exception {
	}

    public void testIndexInfoAvailableAfterInfoGathering() {
    	EmbeddedConfiguration config = config();
    	config.common().reflectWith(new ExcludingReflector(WithIndex.class));
		EmbeddedObjectContainer db = Db4oEmbedded.openFile(config, DB_PATH);
		try {
	    	scanThroughKnownClassesInfo(db);
	    	assertHasIndexInfo(db);
		}
		finally {
			db.close();
		}
    }

    private void scanThroughKnownClassesInfo(ObjectContainer db)
    {
    	for(ReflectClass clazz : db.ext().knownClasses()) {
    		for(ReflectField field: clazz.getDeclaredFields()) {
    			field.getName();
    			field.getFieldType();
    		}
    	}
    }


    private void assertHasIndexInfo(ObjectContainer db) {
    	for(StoredClass sc : db.ext().storedClasses()) {
    		if(!sc.getName().equals(WithIndex.class.getName())) {
    			continue;
    		}
    		for(StoredField sf : sc.getStoredFields()) {
    			if(sf.hasIndex()) {
    				return;
    			}
    		}
    		Assert.fail("no index found");
    	}
    }

}
