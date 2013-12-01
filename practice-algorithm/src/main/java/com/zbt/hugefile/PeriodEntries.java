//package com.zbt.hugefile;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//import java.util.TimeZone;
//
//public class PeriodEntries<T extends Timestamped> implements Iterable<List<T>> {
//	  private final Iterator<List<T extends Timestamped>> entriesIt;
//	  private final long interval;
//	  private PeriodEntries(Iterable<List<T>> entriesIt, long interval) {
//	    this.entriesIt = entriesIt.iterator();
//	    this.interval = interval;
//	  }
//	 
//	  public static <T extends Timestamped> PeriodEntries<T> create(Iterable<List<T>> entriesIt, long interval) {
//	   return new PeriodEntries<T>(entriesIt, interval);
//	  }
//	 
//	  public Iterator<List<T extends Timestamped>> iterator() {
//	    return new Iterator<List<T>>() {
//	      private Queue<List<T>> queue = new LinkedList<List<T>>();
//	      private long previous;
//	      private Iterator<T> entryIt;
//	 
//	      @Override
//	      public boolean hasNext() {
//	        if (!advanceEntries()) {
//	          return false;
//	        }
//	        T entry =  entryIt.next();
//	        long time = normalizeInterval(entry);
//	        if (previous == 0) {
//	          previous = time;
//	        }
//	        if (queue.peek() == null) {
//	          List<T> group = new ArrayList<T>();
//	          queue.add(group);
//	        }
//	        while (previous == time) {
//	          queue.peek().add(entry);
//	          if (!advanceEntries()) {
//	            break;
//	          }
//	          entry = entryIt.next();
//	          time = normalizeInterval(entry);
//	        }
//	        previous = time;
//	        List<T> result = queue.peek();
//	        if (result == null || result.isEmpty()) {
//	          return false;
//	        }
//	        return true;
//	      }
//	 
//	      private boolean advanceEntries() {
//	        // if there are no rows left
//	        if (entryIt == null || !entryIt.hasNext()) {
//	          // try get more rows if possible
//	          if (entriesIt.hasNext()) {
//	            entryIt = entriesIt.next().iterator();
//	            return true;
//	          } else {
//	            // no more rows
//	            return false;
//	          }
//	        }
//	        return true;
//	      }
//	 
//	      private long normalizeInterval(Timestamped entry) {
//	        long time = entry.getTime();
//	        int utcOffset = TimeZone.getDefault().getOffset(time);
//	        long utcTime = time + utcOffset;
//	        long elapsed = utcTime % interval;
//	        return time - elapsed;
//	      }
//	      @Override
//	      public List<T> next() {
//	        return queue.poll();
//	      }
//	      @Override
//	      public void remove() {
//	        throw new UnsupportedOperationException();
//	      }
//	   };
//	  }
//	}
