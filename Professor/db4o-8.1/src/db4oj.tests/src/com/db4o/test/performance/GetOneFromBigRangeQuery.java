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
package com.db4o.test.performance;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.foundation.io.*;
import com.db4o.query.*;

public class GetOneFromBigRangeQuery {
	
	public static class Item {
		
		private int _id;
		
		public Item(){
			
		}
		
		public Item(int id){
			_id = id;
		}
		
	}

	private static final String FILE = "getOneFromBigRangeQuery.db4o";
		
	private static final int OBJECT_COUNT = 10000;
	
	private static final int COMMIT_INTERVAL = 1000;
	
	private static final int QUERIES = 50;
	
	public static void main(String[] args) {
		
		if(true){
			File4.delete(FILE);
			ObjectContainer objectContainer = openObjectContainer();
			long start = System.currentTimeMillis();
			for (int i = 0; i < OBJECT_COUNT; i++) {
				objectContainer.store(new Item(i));
				if(i % COMMIT_INTERVAL == 0){
					objectContainer.commit();
				}
			}
			objectContainer.commit();
			long stop = System.currentTimeMillis();
			long duration = stop - start;
			System.out.println("Time to store and commit " + OBJECT_COUNT + " objects:");
			System.out.println("" + duration + "ms");
			objectContainer.close();
			return;
		}
		
		ObjectContainer objectContainer = openObjectContainer();
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < QUERIES; i++) {
			Query query = objectContainer.query();
			query.constrain(Item.class);
			query.descend("_id").constrain(0).greater();
			query.descend("_id").constrain(OBJECT_COUNT - 1).smaller();
			ObjectSet<Object> objectSet = query.execute();
		}
		long stop = System.currentTimeMillis();
		long duration = stop - start;
		System.out.println("GetOneFromBigRangeQuery");
		System.out.println("Time to execute " + QUERIES + " queries against " + OBJECT_COUNT + " objects:");
		System.out.println("" + duration + "ms");
		objectContainer.close();
	}

	private static ObjectContainer openObjectContainer() {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().objectClass(Item.class).objectField("_id").indexed(true);
		// config.idSystem().usePointerBasedSystem();
		ObjectContainer objectContainer = Db4oEmbedded.openFile(config, FILE);
		return objectContainer;
	}

}
