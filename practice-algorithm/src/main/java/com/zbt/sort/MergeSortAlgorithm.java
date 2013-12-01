package com.zbt.sort;

/*
 * @(#)MergeSortAlgorithm.java	1.0 95/06/23 Jason Harrison
 *
 * Copyright (c) 1995 University of British Columbia
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies. Please refer to the file "copyright.html"
 * for further important copyright and licensing information.
 *
 * UBC MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. UBC SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

/**
 * A merge sort demonstration algorithm
 * SortAlgorithm.java, Thu Oct 27 10:32:35 1994
 *
 * @author Jason Harrison@cs.ubc.ca
 * @version 	1.0, 23 Jun 1995
 */
class MergeSortAlgorithm extends SortAlgorithm {
    void sort(int a[], int lo0, int hi0) throws Exception {
	int lo = lo0;
	int hi = hi0;
	pause(lo, hi);
	if (lo >= hi) {
	    return;
	}
	int mid = (lo + hi) / 2;

        sort(a, lo, mid);
        sort(a, mid + 1, hi);

	int end_lo = mid;
        int start_hi = mid + 1;
	while ((lo <= end_lo) && (start_hi <= hi)) {
	    if (stopRequested) {
                return;
            }
            if (a[lo] < a[start_hi]) {
                lo++;
            } else if (a[lo] == a[start_hi]) {
                lo++;
                start_hi++;
            } else {
		int T = a[start_hi];
                for (int k = start_hi - 1; k >= lo; k--) {
                    a[k+1] = a[k];
                    pause(lo);
                }
                a[lo] = T;
                lo++;
                end_lo++;
                start_hi++;
                pause(lo);
            }
        }
    }

    void sort(int a[])  throws Exception {
	sort(a, 0, a.length-1);
    }
}
