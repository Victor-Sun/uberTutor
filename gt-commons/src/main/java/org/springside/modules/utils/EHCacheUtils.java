/**
 * Copyright 2010-2025 Gnomon Technology. All rights reserved
 */
package org.springside.modules.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.RegisteredEventListeners;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

/**
 * @author frank
 *
 */
public class EHCacheUtils {
	
	/**
	 * 
	 */
	private static final String GLOBAL_CACHE = "globalCache";

	private static CacheManager getManager(){
		return CacheManager.create();
	}

	private static Cache getGlobalCache(){
		
		Cache globalCache = null;
		globalCache = getManager().getCache(GLOBAL_CACHE);
		if(globalCache == null){
			String name = GLOBAL_CACHE;
			int maxElementsInMemory = 1000;
			MemoryStoreEvictionPolicy memoryStoreEvictionPolicy = MemoryStoreEvictionPolicy.FIFO;//FIFO
			boolean overflowToDisk = false;
			String diskStorePath = getManager().getDiskStorePath();
			boolean eternal = true;
			long timeToLiveSeconds = 0;
			long timeToIdleSeconds = 0;
			boolean diskPersistent = false;
			long diskExpiryThreadIntervalSeconds = 0;
			RegisteredEventListeners registeredEventListeners = null;
			globalCache = new Cache(name, maxElementsInMemory, memoryStoreEvictionPolicy, overflowToDisk, diskStorePath, eternal, timeToLiveSeconds, timeToIdleSeconds, diskPersistent, diskExpiryThreadIntervalSeconds, registeredEventListeners);
			try{
				getManager().addCache(globalCache);
			}catch(net.sf.ehcache.ObjectExistsException e){
				//ignore
			}

		}
		return globalCache;
	}
	
	public static void put(String name,Object value){
		Element element = new Element(name,value);
		getGlobalCache().put(element);
	}
	
	public static Object get(String name){
		Element element = getGlobalCache().get(name);
		if(element != null){
			return element.getObjectValue();
		}
		return null;
	}
	
	public static void remove(String name){
		getGlobalCache().remove(name);
	}
}
