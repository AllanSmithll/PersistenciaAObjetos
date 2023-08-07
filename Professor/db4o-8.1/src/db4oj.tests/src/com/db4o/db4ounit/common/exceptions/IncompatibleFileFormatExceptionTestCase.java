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
package com.db4o.db4ounit.common.exceptions;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.io.*;

import db4ounit.*;

public class IncompatibleFileFormatExceptionTestCase implements TestLifeCycle {

	private final static String DB_PATH = "inmemory.db4o";
	private Storage storage;
	
	public static void main(String[] args) throws Exception {
		new ConsoleTestRunner(IncompatibleFileFormatExceptionTestCase.class).run();
	}

	public void setUp() throws Exception {
		storage = new MemoryStorage();
		Bin bin = storage.open(new BinConfiguration(DB_PATH, false, 0, false));
		bin.write(0, new byte[] { 1, 2, 3 }, 3);
		bin.close();
	}

	public void tearDown() throws Exception {
	}

	public void test() {
		final EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.file().storage(storage);
		Assert.expect(IncompatibleFileFormatException.class, new CodeBlock() {
			public void run() throws Throwable {
				Db4oEmbedded.openFile(config, DB_PATH);
			}
		});
	}


}
