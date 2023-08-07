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
package com.db4o.db4ounit.common.cs;


import com.db4o.config.*;
import com.db4o.constraints.*;

import db4ounit.*;
import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;

public class UniqueConstraintOnServerTestCase extends Db4oClientServerTestCase implements CustomClientServerConfiguration{

    public static void main(String[] args) {
        new UniqueConstraintOnServerTestCase().runAll();
    }
    
    @Override
    protected void configure(Configuration config) throws Exception {
        config.objectClass(UniqueId.class).objectField("id").indexed(true);
        config.add(new UniqueFieldValueConstraint(UniqueId.class, "id"));
    }
    
	@Override
	public void configureServer(Configuration config) throws Exception {
		configure(config);
	}

	@Override
	public void configureClient(Configuration config) throws Exception {
		// do nothing
		
	}

    public void testWorksForUniqueItems() {
        store(new UniqueId(1));
        store(new UniqueId(2));
        store(new UniqueId(3));
        commit();
    }
    
    public void testNotUniqueItems() {
        store(new UniqueId(1));
        store(new UniqueId(1));
        boolean exceptionWasThrown = false;
        try {
            commit();
        } catch (UniqueFieldValueConstraintViolationException e) {
            exceptionWasThrown = true;
        }
        Assert.isTrue(exceptionWasThrown);
        db().rollback();
    }

    public static class UniqueId {
    	
        public int id;

        public UniqueId(int id) {
            this.id = id;
        }
    }

}
