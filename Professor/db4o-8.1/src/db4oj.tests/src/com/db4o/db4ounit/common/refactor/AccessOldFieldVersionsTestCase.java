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
package com.db4o.db4ounit.common.refactor;

import com.db4o.internal.*;

import db4ounit.*;

public class AccessOldFieldVersionsTestCase extends AccessFieldTestCaseBase implements TestLifeCycle {

	private static final Class<Integer> ORIG_TYPE = Integer.TYPE;
	private static final String FIELD_NAME = "_value";
	private static final int ORIG_VALUE = 42;
	
	public void testRetypedField() {
		final Class<RetypedFieldData> targetClazz = RetypedFieldData.class;
		renameClass(OriginalData.class, ReflectPlatform.fullyQualifiedName(targetClazz));
		assertField(targetClazz, FIELD_NAME, ORIG_TYPE, ORIG_VALUE);
	}

	@Override
	protected Object newOriginalData() {
		return new OriginalData(ORIG_VALUE);
	}

	public static class OriginalData {
		public int _value;
			
		public OriginalData(int value) {
			_value = value;
		}
	}
	
	public static class RetypedFieldData {
		public String _value;
		
		public RetypedFieldData(String value) {
			_value = value;
		}
	}

}
