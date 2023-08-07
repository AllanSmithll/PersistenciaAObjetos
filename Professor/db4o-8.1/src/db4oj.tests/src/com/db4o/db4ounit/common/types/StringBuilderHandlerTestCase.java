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
package com.db4o.db4ounit.common.types;

import com.db4o.config.*;
import com.db4o.typehandlers.*;

import db4ounit.*;
import db4ounit.extensions.*;

/**
 * @sharpen.remove
 */
@decaf.Remove(unlessCompatible=decaf.Platform.JDK15)
public class StringBuilderHandlerTestCase  extends AbstractDb4oTestCase {
	
    public void testCanRead(){
        canRead();
    }
    public void testCanReadAfterDefragment() throws Exception {
        canReadAfterDefragment();
    }

    public void canRead(){
        assertCanReadData();
    }
    
    public void canReadAfterDefragment() throws Exception {
        defragment();
        assertCanReadData();

    }
    
    @Override
    protected void configure(Configuration config) throws Exception {
    	config.registerTypeHandler(new SingleClassTypeHandlerPredicate(StringBuilder.class),
                new StringBuilderHandler());
    }

    private void assertCanReadData() {
        final Holder holder = db().query(Holder.class).get(0);
        Assert.areEqual(holder.getBuilder().toString(), "TestData");
    }

    @Override
    protected void store() throws Exception {
    	db().store(new Holder());
    }

    public static class Holder{
        StringBuilder builder ;

        public Holder(String data) {
            this.builder = new StringBuilder(data);
        }

        public Holder() {
            this("TestData");
        }

        public StringBuilder getBuilder() {
            return builder;
        }

        public void setBuilder(StringBuilder builder) {
            this.builder = builder;
        }
    }

}
