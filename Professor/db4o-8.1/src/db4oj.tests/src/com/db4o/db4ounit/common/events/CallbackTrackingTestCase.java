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
package com.db4o.db4ounit.common.events;

import com.db4o.events.*;
import com.db4o.foundation.*;

import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;

public class CallbackTrackingTestCase extends AbstractDb4oTestCase implements OptOutAllButNetworkingCS {

	public void testStaticFields() throws Exception {
		
		assertQueryOnCallBack(ItemWithStaticField.class);
	}
	
	/** @sharpen.ignore */
	public void testEnum() throws Exception {
		
		assertQueryOnCallBack(ItemWithEnum.class);
	}

	private void assertQueryOnCallBack(final Class classConstraint) throws InterruptedException {
		final Lock4 lock = new Lock4();
		
		db().store(new Item(42));
		
		final EventRegistry eventRegistry = eventRegistry();
		eventRegistry.committed().addListener(new EventListener4<CommitEventArgs>() {
			
			@Override
			public void onEvent(Event4<CommitEventArgs> e, CommitEventArgs args) {

				try {					
					args.objectContainer().query(classConstraint);
				}
				finally {
					
					lock.run(new Closure4() {
						
						@Override
						public Object run() {
							lock.awake();
							return null;
						}
						
					});
				}
			}
		});
		
		synchronized (lock) {
			db().commit();
			lock.snooze(5000);	
		}
		
	}
		
	@SuppressWarnings("unused")
	private static class ItemWithStaticField {
		public static int i;		
	}
	
	@SuppressWarnings("unused")
	private static class Item {
		
		public int id;

		public Item(int id) {
			this.id = id;
		}		
	}
	
	/** @sharpen.ignore */
	private static class ItemWithEnum extends Item {
		public ItemEnum e;
		
		public ItemWithEnum(int id, ItemEnum e) {
			super(id);
			this.e = e;
		}
	}

	/** @sharpen.ignore */
	private static enum ItemEnum {
		one,
		two
	}
	
}
