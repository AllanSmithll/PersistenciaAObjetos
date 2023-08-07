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
package com.db4o.db4ounit.jre11.staging;

import com.db4o.db4ounit.jre11.events.*;
import com.db4o.events.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;

import db4ounit.*;
import db4ounit.extensions.*;

/**
 * COR-594
 */
public class CommittedCallbacksUpdateTestCase extends Db4oClientServerTestCase
		implements EventListener4 {

	private ExtObjectContainer readingClient;

	private ExtObjectContainer writingClient;
	
	private Object lock = new Object();

	public static void main(String[] args) {
		new CommittedCallbacksUpdateTestCase().runAll();
	}

	@Override
	protected void db4oSetupAfterStore() throws Exception {
		writingClient = db();
		readingClient = openNewSession();

		EventRegistryFactory.forObjectContainer(readingClient).committed()
				.addListener(this);
	}

	public void onEvent(Event4 e, EventArgs args) {
		ObjectInfoCollection updated = ((CommitEventArgs) args).updated();
		Iterator4 infos = updated.iterator();
		while (infos.moveNext()) {
			ObjectInfo info = (ObjectInfo) infos.current();
			Object obj = info.getObject();
			synchronized (lock) {
				if (readingClient.isActive(obj)) {
					System.err.println("Refreshing " + System.identityHashCode(obj));
					readingClient.refresh(obj, 1);
				} else {
					System.err.println("Not active" + System.identityHashCode(obj));
				}
				lock.notify();
			}
		}
	}

	protected void db4oTearDownBeforeClean() throws Exception {
		EventRegistryFactory.forObjectContainer(readingClient).committed()
				.removeListener(this);
		if (readingClient != null) {
			readingClient.close();
		}
	}

	public void testUpdate() {
		Item i = new Item("1", null);
		writingClient.store(i);
		writingClient.commit();
		long id = writingClient.getID(i);

		Item i2 = (Item) readingClient.getByID(id);
		readingClient.activate(i2, 1);
		Assert.areEqual(i.id, i2.id);

		i.id = "2";
		writingClient.store(i);
		writingClient.commit();
		
		synchronized(lock){
			try {
				lock.wait(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Item i3 = (Item) readingClient.getByID(id);
			readingClient.activate(i3, 1);
			System.err.println("Checking " + System.identityHashCode(i3));
			Assert.areEqual(i.id, i3.id);
		}


	}

}
