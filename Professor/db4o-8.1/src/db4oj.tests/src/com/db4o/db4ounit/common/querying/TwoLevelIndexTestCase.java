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
package com.db4o.db4ounit.common.querying;

import com.db4o.config.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class TwoLevelIndexTestCase extends AbstractDb4oTestCase {
	
	public static class Parent1 {
		public Child1 child;
	}
	
	public static class Parent2 extends Parent1 {
		
	}
	
	
	public static class Child1 {
		public int id;
	}
	
	public static class Child2 extends Child1 {
		
	}
	
	@Override
	protected void configure(Configuration config) throws Exception {
		config.objectClass(Parent1.class).objectField("child").indexed(true);
		config.objectClass(Child1.class).objectField("id").indexed(true);
	}
	
	@Override
	protected void store() throws Exception {
		Parent1 parent1 = new Parent1();
		parent1.child = new Child1();
		parent1.child.id = 42;
		store(parent1);
		
		Parent2 parent2 = new Parent2();
		parent2.child = new Child2();
		parent2.child.id = 42;
		store(parent2);
	}
	
	public void testTwoLevelParentIsSubclassed(){
		Query query = db().query();
		query.constrain(Parent2.class);
		query.descend("child").descend("id").constrain(42);
		Assert.areEqual(1, query.execute().size());
	}
	
	public void testChildClassConstrained(){
		Query query = db().query();
		query.constrain(Parent1.class);
		query.descend("child").descend("id").constrain(42);
		query.descend("child").constrain(Child2.class);
		Assert.areEqual(1, query.execute().size());
	}

}
