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

public class TwoLevelQuery {
	
	public static class Parent {
		
		public String name;
		
		public Child child;
		
	}
	
	public static class Child {
		
		public String name;
		
	}

	private static final String FILE_NAME = "SecondLevelQuery.db4o";
	
	private static final int COUNT = 100000;
	
	private static final int QUERIES = 1000;
	
	public static void main(String[] args) {
		store();
		queryForParents();
		queryForChildren();
	}

	private static void store() {
		File4.delete(FILE_NAME);
		ObjectContainer objectContainer = openObjectContainer();
		long start = System.currentTimeMillis();
		for (int i = 0; i < COUNT; i++) {
			Parent parent = new Parent();
			parent.child = new Child();
			parent.name = "p" + i;
			parent.child.name = "c" + i;
			objectContainer.store(parent);
		}
		objectContainer.commit();
		long stop = System.currentTimeMillis();
		long duration = stop - start;
		System.out.println("Time to store " + COUNT + " parent and child objects:\r\n" + duration + "ms");
		objectContainer.close();
	}

	private static EmbeddedObjectContainer openObjectContainer() {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().objectClass(Parent.class).objectField("name").indexed(true);
		config.common().objectClass(Parent.class).objectField("child").indexed(true);
		config.common().objectClass(Child.class).objectField("name").indexed(true);
		return Db4oEmbedded.openFile(config, FILE_NAME);
	}
	
	private static void queryForParents() {
		ObjectContainer objectContainer = openObjectContainer();
		long start = System.currentTimeMillis();
		for (int i = 0; i < QUERIES; i++) {
			Query query = objectContainer.query();
			query.constrain(Parent.class);
			query.descend("name").constrain("p" + i);
			query.execute();
		}
		long stop = System.currentTimeMillis();
		long duration = stop - start;
		System.out.println("Time to query for " + QUERIES + " parent objects:\r\n" + duration + "ms");
		objectContainer.close();
	}
	
	private static void queryForChildren() {
		ObjectContainer objectContainer = openObjectContainer();
		long start = System.currentTimeMillis();
		for (int i = 0; i < QUERIES; i++) {
			Query query = objectContainer.query();
			query.constrain(Parent.class);
			query.descend("child").descend("name").constrain("c" + i);
			query.execute();
		}
		long stop = System.currentTimeMillis();
		long duration = stop - start;
		System.out.println("Time to query for " + QUERIES + " child objects:\r\n" + duration + "ms");
		objectContainer.close();
	}



}
