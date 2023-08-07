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
package com.db4o.db4ounit.common.logging;

import java.io.*;
import java.util.*;

import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.logging.*;

import db4ounit.*;

public class LoggingTestCase implements TestLifeCycle {
	
	@LogInterface
	public interface TestLogger {
		void msg();
	}

	public interface InvalidTestLogger {
	}
	
	@Override
	public void setUp() throws Exception {
		Logger.purgeCache();
	}

	
	@Override
	public void tearDown() {
	}

	public void testInvalidTestLogger() {
		try {
			Logger.get(InvalidTestLogger.class);
			Assert.fail("An exception should have been generated since our "+InvalidTestLogger.class.getSimpleName() +" is not annotated with @LogInterface");
		} catch (IllegalArgumentException expected) {
		}
	}
	
	public void testWithNoRootInterceptor() {
		
		Logging<TestLogger> logger = Logger.get(TestLogger.class);
		
		logger.debug().msg();

	}
	
	
	public void testLoggingLevels() {
		
		List<Pair<Level, String>> methodsCalled = setRootInterceptor();
		
		Logging<TestLogger> logger = Logger.get(TestLogger.class);
		
		
		logger.trace().msg();
		logger.debug().msg();
		logger.info().msg();
		logger.warn().msg();
		logger.error().msg();
		logger.fatal().msg();
		
		Assert.areEqual(Pair.of(Logger.TRACE, "msg"), popFirst(methodsCalled));
		Assert.areEqual(Pair.of(Logger.DEBUG, "msg"), popFirst(methodsCalled));
		Assert.areEqual(Pair.of(Logger.INFO, "msg"), popFirst(methodsCalled));
		Assert.areEqual(Pair.of(Logger.WARN, "msg"), popFirst(methodsCalled));
		Assert.areEqual(Pair.of(Logger.ERROR, "msg"), popFirst(methodsCalled));
		Assert.areEqual(Pair.of(Logger.FATAL, "msg"), popFirst(methodsCalled));
		
	}

	private List<Pair<Level, String>> setRootInterceptor() {
		final List<Pair<Level,String>> methodsCalled = new ArrayList<Pair<Level,String>>();
		
		Logger.intercept(new LoggingInterceptor() {
		
			@Override
			public void log(Level loggingLevel, String method, Object[] args) {
				methodsCalled.add(Pair.of(loggingLevel, method));
			}
		
		});
		return methodsCalled;
	}
	
	public void testSetLoggingLevel() {
		
		List<Pair<Level, String>> methodsCalled = setRootInterceptor();
		
		Logging<TestLogger> logger = Logger.get(TestLogger.class);
		
		
		Logger.loggingLevel(Logger.DEBUG);
		
		logger.trace().msg();
		logger.debug().msg();
		logger.info().msg();
		
		Assert.areEqual(Pair.of(Logger.DEBUG, "msg"), popFirst(methodsCalled));
		Assert.areEqual(Pair.of(Logger.INFO, "msg"), popFirst(methodsCalled));
		
		
		logger.loggingLevel(Logger.INFO);
		
		logger.debug().msg();
		logger.info().msg();
		logger.error().msg();
		
		Assert.areEqual(Pair.of(Logger.INFO, "msg"), popFirst(methodsCalled));
		Assert.areEqual(Pair.of(Logger.ERROR, "msg"), popFirst(methodsCalled));
		
	}
	
	public void testPrintWriterLogger() throws SecurityException, NoSuchMethodException {
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		PrintWriterLoggerInterceptor interceptor = new PrintWriterLoggerInterceptor(new PrintWriter(bout, true));
		Logger.intercept(interceptor);
		
		Logging<TestLogger> logger = Logger.get(TestLogger.class);
		
		logger.debug().msg();
		logger.info().msg();
		
		String actual = Platform4.asUtf8(bout.toByteArray());
		
		String debugMsg = PrintWriterLoggerInterceptor.formatMessage(Logger.DEBUG, "msg", null);
		String infoMsg = PrintWriterLoggerInterceptor.formatMessage(Logger.INFO, "msg", null);

		Assert.isTrue(actual.contains(debugMsg));
		Assert.isTrue(actual.contains(infoMsg));
		
	}
	
	public void testInterceptor() {

		Logging<TestLogger> logger = Logger.get(TestLogger.class);
		
		Logger.intercept(new LoggingInterceptor() {
			
			@Override
			public void log(Level loggingLevel, String method, Object[] args) {
				Assert.fail("The root interceptor should not be called");
			}
		});

		final ByRef<Level> called = new ByRef<Level>();
		
		logger.forward(new TestLogger() {
			
			@Override
			public void msg() {
				called.value = Logger.contextLoggingLevel(); 
			}
		});
		
		logger.debug().msg();
		
		Assert.areEqual(Logger.DEBUG, called.value);
		
		called.value = null;
		
		logger = Logger.get(TestLogger.class);
		logger.debug().msg();
		
		Assert.areEqual(Logger.DEBUG, called.value);

	}
	
	public static <T> T popFirst(List<T> list) {
		T first = list.get(0);
		list.remove(0);
		return first;
	}

}
