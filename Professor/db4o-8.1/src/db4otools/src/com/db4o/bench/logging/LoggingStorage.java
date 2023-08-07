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
package com.db4o.bench.logging;

import java.io.*;

import com.db4o.ext.*;
import com.db4o.io.*;
/**
 * 
 * @sharpen.ignore
 *
 */
public class LoggingStorage extends StorageDecorator {
	
	public final static int LOG_READ 	= 1;
	public final static int LOG_WRITE 	= 2;
	public final static int LOG_SYNC 	= 4;
	public final static int LOG_SEEK    = 8;
	
	public final static int LOG_ALL = LOG_READ + LOG_WRITE + LOG_SYNC + LOG_SEEK;
	
	private final String _fileName;
	
	private int _config;

    public LoggingStorage(Storage delegateAdapter, String fileName)  {
    	this(delegateAdapter, fileName, LOG_ALL);
    }

    public LoggingStorage(Storage storage, String fileName, int config)  {
        super(storage);
        _fileName = fileName;
        _config = config;
    }
    
	protected Bin decorate(BinConfiguration config, Bin bin) {
        try {
			PrintStream out = new PrintStream(new FileOutputStream(_fileName));
			return new LoggingBin(bin, out, _config);
		} 
        catch(FileNotFoundException e) {
			throw new Db4oIOException(e);
		}
	}
	
	private static class LoggingBin extends BinDecorator {

		private final PrintStream _out;
		private int _config;

		public LoggingBin(Bin bin, PrintStream out, int config) {
			super(bin);
			_out = out;
			_config = config;
		}
		
		public void close() throws Db4oIOException {
			_out.flush();
			_out.close();
			super.close();
		}
	
	    public int read(long pos, byte[] bytes, int length) throws Db4oIOException {
	    	if(config(LOG_READ)) {
	    		println(LogConstants.READ_ENTRY + pos + " " + length);
	    	}
	        return _bin.read(pos, bytes, length);
	    }
	
	    public void sync() throws Db4oIOException {
	    	if(config(LOG_SYNC)) {
	    		println(LogConstants.SYNC_ENTRY);
	    	}
	        _bin.sync();
	    }
	
	    public void write(long pos, byte[] buffer, int length) throws Db4oIOException {
	    	if(config(LOG_WRITE)) {
	    		println(LogConstants.WRITE_ENTRY + pos + " " + length);
	    	}
	        _bin.write(pos, buffer, length);
	    }
	    
	    private void println(String s){
	    	_out.println(s);
	    }

	    private boolean config(int mask) {
	    	return (_config & mask) != 0;
	    }
	}
    
}
