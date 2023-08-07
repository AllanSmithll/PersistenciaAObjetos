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
package com.db4o.db4ounit.jre12.collections;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.collections.CollectionFactory;
import com.db4o.query.Query;
import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;
import db4ounit.extensions.fixtures.*;

import java.util.*;

@decaf.Remove(decaf.Platform.JDK11)
public class CollectionContainsTestCase extends AbstractDb4oTestCase implements OptOutMultiSession {

    public static void main(String[] args) {
        new CollectionContainsTestCase().runSolo();
    }
    @Override
    protected void store() throws Exception {
        super.store();
        Collection<Item> items = produceItems();
        db().store(RegularSetHolder.create(items));
        db().store(BigSetHolder.create(db(), items));
        db().commit();
    }

    public void testFindInRegularCollection(){
        final ObjectSet<RegularSetHolder> result = runQueryFor(RegularSetHolder.class);
        Assert.areEqual(1,result.size());
    }
    public void testFindInBigSet(){
        final ObjectSet<BigSetHolder> result = runQueryFor(BigSetHolder.class);

        Assert.areEqual(1,result.size());
    }

    private <T> ObjectSet<T> runQueryFor(Class<T> forType) {
        Item aItem = storedItem();
        final Query query = db().query();
        query.constrain(forType);
        query.descend("items").constrain(aItem);
        return query.execute();
    }

    private Item storedItem() {
        return db().query(Item.class).get(0);
    }

    private Collection<Item> produceItems() {
        List<Item> result = new ArrayList<Item>();
        for(int i=0;i<100;i++){
            result.add(new Item());
        }
        return result;
    }
    
    public static class RegularSetHolder {
        private Set<Item> items;

        RegularSetHolder(Set<Item> items) {
            this.items = items;
        }

        public static RegularSetHolder create(Collection<Item> items){
            return new RegularSetHolder(new HashSet<Item>(items));
        }

        public Iterable<Item> getItems() {
            return items;
        }
    }
    public static class BigSetHolder {
        private Set<Item> items;

        BigSetHolder(Set<Item> items) {
            this.items = items;
        }

        public static BigSetHolder create(ObjectContainer container,Collection<Item> items){
            Set<Item> set = CollectionFactory.forObjectContainer(container).newBigSet();
            set.addAll(items);
            return new BigSetHolder(set);
        }

        public Iterable<Item> getItems() {
            return items;
        }
    }
    public static class Item {
    }

}

