package com.zbt.sort;

/*
 * @(#)SortItem.java	1.17f 95/04/10 James Gosling
 *
 * Copyright (c) 1994 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies. Please refer to the file "copyright.html"
 * for further important copyright and licensing information.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
/*
 * Modified by Robert I. Pitts <rip@cs.bu.edu>:
 *
 * 00/01/29
 * - Made startSort() public.
 *
 * 00/03/08
 * - Now use java.util.Random instead of java.lang.Math for random
 *   number generation.  Also use a seed so that a group of SortItem
 *   applets can all start with the same random data.
 * - Added applet parameter "seed" used to set the random seed.
 * - New public method newData() scrambles data based on a certain seed.
 * - Removed scrambling for startSort().
 */

import java.awt.*;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Random;
import java.net.*;

/**
 * A simple applet class to demonstrate a sort algorithm.
 * You can specify a sorting algorithm using the "alg"
 * attribyte. When you click on the applet, a thread is
 * forked which animates the sorting algorithm.
 *
 * @author James Gosling
 * @version 	1.17f, 10 Apr 1995
 */
public class SortItem extends java.applet.Applet implements Runnable {
    /**
     * The thread that is sorting (or null).
     */
    private Thread kicker;

    /**
     * The array that is being sorted.
     */
    int[] arr;

    /**
     * The high water mark.
     */
    int h1 = -1;

    /**
     * The low water mark.
     */
    int h2 = -1;

    /**
     * The name of the algorithm.
     */
    String algName;

    /**
     * The sorting algorithm (or null).
     */
    SortAlgorithm algorithm;

    /**
     * The random number generator.
     */
    Random randGenerator;

    /**
     * If not running the algorithm, sets the seed used for
     * random number generation, scrambles the data, and redraws.
     */
    public synchronized void newData(long seed) {
	if (kicker == null || !kicker.isAlive()) {
          randGenerator.setSeed(seed);
          scramble();
          repaint();
        }
    }

    /**
     * Fill the array with random numbers from 0..n-1.
     */
    void scramble() {
	int[] a = new int[size().height / 2];
	double f = size().width / (double) a.length;
	for (int i = a.length; --i >= 0;) {
	    a[i] = (int)(i * f);
	}
	for (int i = a.length; --i >= 0;) {
	    int j = (int)(i * randGenerator.nextDouble());
	    int t = a[i];
	    a[i] = a[j];
	    a[j] = t;
	}
	arr = a;
    }

    /**
     * Pause a while.
     * @see SortAlgorithm
     */
    void pause() {
	pause(-1, -1);
    }

    /**
     * Pause a while, and draw the high water mark.
     * @see SortAlgorithm
     */
    void pause(int H1) {
	pause(H1, -1);
    }

    /**
     * Pause a while, and draw the low&high water marks.
     * @see SortAlgorithm
     */
    void pause(int H1, int H2) {
	h1 = H1;
	h2 = H2;
	if (kicker != null) {
	    repaint();
	}
	try {Thread.sleep(20);} catch (InterruptedException e){}
    }

    /**
     * Initialize the applet.
     */
    public void init() {
	String at = getParameter("alg");
	if (at == null) {
	    at = "BubbleSort";
	}

	algName = at + "Algorithm";

        try {
	  long seed = Long.parseLong(getParameter("seed"));
          randGenerator = new Random(seed);
        }
        catch (NumberFormatException e) {
          randGenerator = new Random();
        }

	scramble();

        setBackground(Color.white);

	resize(100, 100);
    }

    /**
     * Paint the array of numbers as a list
     * of horizontal lines of varying lenghts.
     */
    public void paint(Graphics g) {
	int[] a = arr;
	int y = size().height - 1;

	// Erase old lines
	g.setColor(Color.lightGray);
	for (int i = a.length; --i >= 0; y -= 2) {
	    g.drawLine(arr[i], y, size().width, y);
	}

	// Draw new lines
	g.setColor(Color.black);
	y = size().height - 1;
	for (int i = a.length; --i >= 0; y -= 2) {
	    g.drawLine(0, y, arr[i], y);
	}

	if (h1 >= 0) {
	    g.setColor(Color.red);
	    y = h1 * 2 + 1;
	    g.drawLine(0, y, size().width, y);
	}
	if (h2 >= 0) {
	    g.setColor(Color.blue);
	    y = h2 * 2 + 1;
	    g.drawLine(0, y, size().width, y);
	}
    }

    /**
     * Update without erasing the background.
     */
    public void update(Graphics g) {
	paint(g);
    }

    /**
     * Run the sorting algorithm. This method is
     * called by class Thread once the sorting algorithm
     * is started.
     * @see java.lang.Thread#run
     * @see SortItem#mouseUp
     */
    public void run() {
	try {
	    if (algorithm == null) {
		algorithm = (SortAlgorithm)Class.forName(algName).newInstance();
		algorithm.setParent(this);
	    }
	    algorithm.init();
	    algorithm.sort(arr);
	} catch(Exception e) {
	}
    }

    /**
     * Stop the applet. Kill any sorting algorithm that
     * is still sorting.
     */
    public synchronized void stop() {
	if (kicker != null) {
            try {
		kicker.stop();
            } catch (IllegalThreadStateException e) {
                // ignore this exception
            }
	    kicker = null;
	}
	if (algorithm != null){
            try {
		algorithm.stop();
            } catch (IllegalThreadStateException e) {
                // ignore this exception
            }
	}
    }


    /**
     * For a Thread to actually do the sorting. This routine makes
     * sure we do not simultaneously start several sorts if the user
     * repeatedly clicks on the sort item.  It needs to be
     * synchronoized with the stop() method because they both
     * manipulate the common kicker variable.
     */
    public synchronized void startSort() {
	if (kicker == null || !kicker.isAlive()) {
	    kicker = new Thread(this);
	    kicker.start();
	}
    }


    /**
     * The user clicked in the applet. Start the clock!
     */
    public boolean mouseUp(java.awt.Event evt, int x, int y) {
	startSort();
	return true;
    }
}
