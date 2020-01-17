package org.cisco.ciscoutil.cu.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.cisco.ciscoutil.cu.constants.CommonUtilConstants;

public class CommonStringUtil {
	public static int levenshteinDistance (CharSequence lhs, CharSequence rhs) {                          
	    int len0 = lhs.length() + 1;                                                     
	    int len1 = rhs.length() + 1;                                                     
	                                                                                    
	    // the array of distances                                                       
	    int[] cost = new int[len0];                                                     
	    int[] newcost = new int[len0];                                                  
	                                                                                    
	    // initial cost of skipping prefix in String s0                                 
	    for (int i = 0; i < len0; i++) cost[i] = i;                                     
	                                                                                    
	    // dynamically computing the array of distances                                  
	                                                                                    
	    // transformation cost for each letter in s1                                    
	    for (int j = 1; j < len1; j++) {                                                
	        // initial cost of skipping prefix in String s1                             
	        newcost[0] = j;                                                             
	                                                                                    
	        // transformation cost for each letter in s0                                
	        for(int i = 1; i < len0; i++) {                                             
	            // matching current letters in both strings                             
	            int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;             
	                                                                                    
	            // computing cost for each transformation                               
	            int cost_replace = cost[i - 1] + match;                                 
	            int cost_insert  = cost[i] + 1;                                         
	            int cost_delete  = newcost[i - 1] + 1;                                  
	                                                                                    
	            // keep minimum cost                                                    
	            newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
	        }                                                                           
	                                                                                    
	        // swap cost/newcost arrays                                                 
	        int[] swap = cost; cost = newcost; newcost = swap;                          
	    }                                                                               
	                                                                                    
	    // the distance is the cost for transforming all letters in both strings        
	    return cost[len0 - 1];                                                          
	}
	
	public static boolean isEqual(String token1, String token2) {
		if (token1.equals(token2)) {
			return true;
		}
		
		if (token1.replaceAll("\\s+", "").equals(token2.replaceAll("\\s+", ""))) {
			return true;
		}
		return false;
	}

	public static boolean contains(String name, String query) {
		if (name.contains(query)) {
			return true;
		} else if (name.replaceAll("\\s+", "").contains(query.replaceAll("\\s+", ""))) {
			return true;
		}
		return false;
	}
	
	public static boolean isPrefix(String haystack, String prefix) {
		String[] splitHaystackParts = haystack.split("\\s+");
		String[] splitPrefixParts = prefix.split("\\s+");
		int prefixMatchCount = 0;
		int  j = 0;
		int lastFoundPrefixIndex = 0;
		
		for (int i = 0; i < splitHaystackParts.length; ++i) {
			String splitHaystack = splitHaystackParts[i];
			while (j < splitPrefixParts.length) {
				String splitPrefix = splitPrefixParts[j];
				
				if (splitHaystack.startsWith(splitPrefix)) {
					++prefixMatchCount;
					lastFoundPrefixIndex = j;
					break;
				}
				++j;
			}
			if (j == splitPrefixParts.length) {
				if (prefixMatchCount < splitPrefixParts.length) {
					j = lastFoundPrefixIndex;
				}
			}
		}
		if (prefixMatchCount == splitPrefixParts.length) {
			return true;
		}
		return false;
	}

	public static Integer getPrefixStartIndex(String haystack, String prefix) {
		String[] splitHaystackParts = haystack.split("\\s+");
		String[] splitPrefixParts = prefix.split("\\s+");
		
		for (int i = 0; i < splitHaystackParts.length; ++i) {
			String splitHaystack = splitHaystackParts[i];
			for (int j = 0; j < splitPrefixParts.length ; ++j) {
				String prefixPart = splitPrefixParts[j];
				if (splitHaystack.startsWith(prefixPart)) {
					return i;
				}
			}
		}
		return Integer.MAX_VALUE;
	}

	public static boolean containsWordsInAnyOrder(String token1, String token2) {
		String[] token1SplitParts = token1.split("\\s+");
		token2 = " " + token2 + " ";
		
		for (String split : token1SplitParts) {
			if (token2.contains(" " + split + " ")) {
				token2 = StringUtils.replaceOnce(token2, split, "");
			} else {
				return false;
			}
		}
		return true;
	}
	
	public static boolean containsWordsInAnyOrderFuzzy(String token1, String token2) {
		String[] token1SplitParts = token1.split("\\s+");
		token2 = " " + token2 + " ";
		
		for (String split : token1SplitParts) {
			if (token2.contains(" " + split + " ")) {
				token2 = StringUtils.replaceOnce(token2, split, "");
			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean isPartialFuzzy(int editDistanceScore) {
		return editDistanceScore <= CommonUtilConstants.MAX_EDIT_DISTANCE;
	}

	public static boolean isEqualFuzzy(String entityValue, String originalEntityValue) {
		int editDistanceScore = CommonStringUtil.levenshteinDistance(entityValue, originalEntityValue);
		if (editDistanceScore <= CommonUtilConstants.MAX_EDIT_DISTANCE) {
			return true;
		}
		return false;
	}
	
	public static boolean isValidFuzzyMatch(String token1, 
									  		String token2,
									  		int editDistanceScore,
									  		double editDistanceFactor, 
									  		int prefixLen,
									  		Map<String, Boolean> wordToHasCorrectSpellingMap) {
		return ((token2.replaceAll("\\s+", "").length() >= prefixLen 
					&& token1.replaceAll("\\s+", "").length() >= prefixLen 
					&& hasEachTokenPrefixMatch(token1, token2, prefixLen, wordToHasCorrectSpellingMap))
				|| token2.equalsIgnoreCase(token1)) 
				&& editDistanceScore <= Math.round(editDistanceFactor * Math.min(token1.length(), token2.length()));
	}

	private static boolean hasEachTokenPrefixMatch(String token1, 
												   String token2, 
												   int prefixLen, 
												   Map<String, Boolean> wordToHasCorrectSpellingMap) {
		String[] token1SplitParts = token1.split("\\s+");
		String[] token2SplitParts = token2.split("\\s+");
		return (token1SplitParts.length < token2SplitParts.length ? 
				hasPrefixMatch(token1SplitParts, token2SplitParts, prefixLen, wordToHasCorrectSpellingMap) : 
					hasPrefixMatch(token2SplitParts, token1SplitParts, prefixLen, wordToHasCorrectSpellingMap));  
	}

	private static boolean hasPrefixMatch(String[] token1SplitParts, 
										  String[] token2SplitParts, 
										  int prefixLen, 
										  Map<String, Boolean> wordToHasCorrectSpellingMap) {
		for (int i = 0; i < token1SplitParts.length; i++) {
			String token1Split = token1SplitParts[i];
			String token2Split = token2SplitParts[i];
			String minLenSplit = token1Split.length() < token2Split.length() ? token1Split : token2Split;
			int minLenSplitLen = minLenSplit.length();
			
			int prefixMatchLen = (MapUtils.isNotEmpty(wordToHasCorrectSpellingMap) && (wordToHasCorrectSpellingMap.containsKey(token1Split) 
								|| wordToHasCorrectSpellingMap.containsKey(token2Split))) && prefixLen < minLenSplitLen ?
										minLenSplitLen : prefixLen;
			if (token1Split.length() >= prefixMatchLen && token2Split.length() >= prefixMatchLen && token1Split.substring(0, prefixMatchLen).equalsIgnoreCase(token2Split.substring(0, prefixMatchLen))
					|| token1Split.equalsIgnoreCase(token2Split)) {
			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean isValidFuzzyMatch(String token1, 
											String token2, 
											double editDistanceFactor, 
											int prefixLen, 
											Map<String, Boolean> wordToHasCorrectSpellingMap) {
		int editDistanceScore = CommonStringUtil.levenshteinDistance(token1, token2);
		return isValidFuzzyMatch(token1, token2, editDistanceScore, editDistanceFactor, prefixLen, wordToHasCorrectSpellingMap); 
	}
	
	public static String getStringFromListParams(List<?> params) {
		return StringUtils.join(params, ",");
	}
	
	public static String removeUnwantedString(String token) {
		return transformToken(token);
	}
	
	public static void validate(String str, String name) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException(name + " can't be empty!");
		}
	}
	
	public static void validateViaCode(String str, String code) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException(code);
		}
	}
	
	public static String transformTokenWithLowerCase(String token) {
		token = transformToken(token);
		token = token.toLowerCase();
		return token;
	}
	
	public static String toLowerCaseAndTrim(String token) {
		return token.toLowerCase().trim();
	}
	
	private static String transformToken(String token) {
		if (StringUtils.isEmpty(token)) {
			throw new IllegalArgumentException("Token can't be empty!");
		}
		token = token.trim();
		token = transformNonAlphaNumericChars(token);
		return token.trim();
	}
	
	private static String transformNonAlphaNumericChars(String token) {
		token = token.replaceAll("[^A-Za-z0-9]", " ");
		return token.replaceAll("\\s+", " ");
	}
}
