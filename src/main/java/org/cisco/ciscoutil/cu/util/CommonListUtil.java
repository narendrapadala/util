package org.cisco.ciscoutil.cu.util;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

public class CommonListUtil {
	
	public static List<String> getPermutations(List<String> s) {
		List<String> permutations = Lists.newArrayList();
	    // Find length of string and factorial of length
		if (CollectionUtils.isEmpty(s)) {
			return s;
		}
		
	    int n = s.size();
	    if (n == 1) {
	    	return Lists.newArrayList(s);
	    } else if (n == 2) {
	    	String perm1 = s.get(0) + " " + s.get(1);
	    	String perm2 = s.get(1) + " " + s.get(0);
	    	return Lists.newArrayList(new String[]{perm1, perm2});
	    }
	    
	    int fc = factorial(n);
	 
	    // Point j to the 2nd position
	    int j = 1;
	 
	    // To store position of character to be fixed next.
	    // m is used as in index in s[].
	    int m = 0;
	 
	    // Iterate while permutation count is
	    // smaller than n! which fc
	    for (int perm_c = 0; perm_c < fc; )
	    {
	        // Store perm as current permutation
	        List<String> perm = s;
	 
	        // Fix the first position and iterate (n-1)
	        // characters upto (n-1)!
	        // k is number of iterations for current first
	        // character.
	        int k = 0;
	        while (k != fc/n)
	        {
	            // Swap jth value till it reaches the end position
	            while (j != n-1)
	            {
	                // Print current permutation
//	                System.out.println(perm);
	                permutations.add(getConcantenatedString(perm));
	                // Swap perm[j] with next character
	                perm = swap(j, j+1, perm);
	 
	                // Increment count of permutations for this
	                // cycle.
	                k++;
	 
	                // Increment permutation count
	                perm_c++;
	 
	                // Increment 'j' to swap with next character
	                j++;
	            }
	 
	            // Again point j to the 2nd position
	            j = 1;
	        }
	 
	        // Move to next character to be fixed in s[]
	        m++;
	 
	        // If all characters have been placed at
	        if (m == n)
	           break;
	 
	        // Move next character to first position
	        s = swap(0, m, s);
	    }
//	    System.out.println(permutations);
	    return permutations;
	}
	
	// NOTE: if this changes, then concatenation logic for array of size 2 should also change (s.get(0) + " " + s.get(1)) 
	private static String getConcantenatedString(List<String> perm) {
		String finalStr = StringUtils.EMPTY;
		for (String str : perm) {
			finalStr += (finalStr.isEmpty() ? str : " " + str);
		}
		return finalStr;
	}

	private static List<String> swap(int index1, int index2, List<String> s) {
		List<String> newS = Lists.newArrayList(s);
		String t = new String(newS.get(index1));
		String t2 = new String(newS.get(index2));
		
		newS.set(index1, t2);
		newS.set(index2, t);
		return newS;
	}

	private static int factorial(int n) {
		int i,fact=1;  
		for(i = 1 ; i <= n ; ++i){    
			fact=fact*i;    
		}    
		return fact;
	}
	
//	public static void main(String[] args) {
//		CommonListUtil lis = new CommonListUtil();
//		System.out.println(lis.getPermutations(Lists.newArrayList(new String[]{"hot"})));
//		System.out.println(lis.getPermutations(Lists.newArrayList(new String[]{"hot", "spicy"})));
//		System.out.println(lis.getPermutations(Lists.newArrayList(new String[]{"hot", "spicy", "chicken"})));
//		System.out.println(lis.getPermutations(Lists.newArrayList(new String[]{"hot", "spicy", "chicken", "make"})));
//		System.out.println(lis.getPermutations(Lists.newArrayList(new String[]{"hot", "spicy", "chicken", "make", "puff"})));
//	}
}
