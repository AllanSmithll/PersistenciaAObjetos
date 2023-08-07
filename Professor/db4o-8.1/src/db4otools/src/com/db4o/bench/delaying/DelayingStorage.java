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
package com.db4o.bench.delaying;

import com.db4o.bench.timing.*;
import com.db4o.ext.*;
import com.db4o.io.*;


public class DelayingStorage extends StorageDecorator {

	private static Delays NO_DELAYS = new Delays(0,0,0);

	private Delays _delays;
	
	public DelayingStorage(Storage delegateAdapter) {
		this(delegateAdapter, NO_DELAYS);
	}
	
	public DelayingStorage(Storage delegateAdapter, Delays delays) {
		super(delegateAdapter);
		_delays = delays;
	}
	
	protected Bin decorate(BinConfiguration config, Bin bin) {
		return new DelayingBin(bin, _delays);
	}
	
	private static class DelayingBin extends BinDecorator {
	
		private NanoTiming _timing;		
		private Delays _delays;
		
		public DelayingBin(Bin bin, Delays delays)throws Db4oIOException {
			super(bin);
			_delays = delays;
			_timing = new NanoTiming();
		}

		public int read(long pos, byte[] bytes, int length) throws Db4oIOException {
			delay(_delays.values[Delays.READ]);
			return _bin.read(pos, bytes, length);
	    }

	    public void sync() throws Db4oIOException {
			delay(_delays.values[Delays.SYNC]);
	    	_bin.sync();
	    }

	    public void write(long pos, byte[] buffer, int length) throws Db4oIOException {
			delay(_delays.values[Delays.WRITE]);
	    	_bin.write(pos, buffer, length);
	    }
		
	    private void delay(long time) {
	    	_timing.waitNano(time);
	    }
	}
}
