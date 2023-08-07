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
package com.db4o.db4ounit.common.io;

import com.db4o.io.*;

import db4ounit.*;

public class PagingMemoryStorageTestCase implements TestCase {
	
	private static final byte[] DATA = new byte[] {1, 2, 3, 4};

	public void test() {
		PagingMemoryStorage storage = new PagingMemoryStorage() {
			
			@Override
			protected Bin produceBin(BinConfiguration config, int pageSize) {
				Bin bin = super.produceBin(config, pageSize);
				bin.write(0, DATA , DATA.length);
				return bin;
			}
			
		};
		
		Bin testBin = storage.open(new BinConfiguration("", true, 0, false));
		Assert.areEqual(DATA.length, testBin.length());
		int actualLength = (int) testBin.length();
		
		byte[] read = new byte[actualLength];
		testBin.read(0, read, actualLength);
		ArrayAssert.areEqual(DATA, read);		
	}
}
