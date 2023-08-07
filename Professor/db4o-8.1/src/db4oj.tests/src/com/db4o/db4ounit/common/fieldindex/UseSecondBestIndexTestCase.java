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
package com.db4o.db4ounit.common.fieldindex;

import com.db4o.config.*;
import com.db4o.diagnostic.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class UseSecondBestIndexTestCase extends AbstractDb4oTestCase {

	boolean loadedFromClassIndex;

	@Override
	protected void configure(Configuration config) throws Exception {
		config.objectClass(Parent.class).objectField("id").indexed(true);
		config.objectClass(Child.class).objectField("id").indexed(true);
		config.diagnostic().addListener(new DiagnosticListener() {
			public void onDiagnostic(Diagnostic d) {
				if (d instanceof LoadedFromClassIndex) {
					loadedFromClassIndex = true;
				}
			}
		});

	}

	public static class Parent {

		public Child child;

		public int id;

		public Parent(int id) {
			this.id = id;
		}

	}

	public static class Child {

		public int id;

		public Child(int id) {
			this.id = id;
		}

	}

	@Override
	protected void store() throws Exception {
		store(new Parent(42));
		Parent parent2 = new Parent(42);
		parent2.child = new Child(42);
		store(parent2);
	}

	public void testUsingIndex() {
		Query q = db().query();
		q.constrain(Parent.class);
		q.descend("id").constrain(42);
		q.descend("child").descend("id").constrain(42);
		q.execute();
		Assert.isFalse(loadedFromClassIndex);

	}

}