package matcher;

public class StudentClass {

	public KMPMatcher kmpMatcher;

	public StudentClass(String text, String pattern) {
		kmpMatcher = new KMPMatcher(text, pattern);
	}

	public void buildPrefixFunction() {
		kmpMatcher.setPrefixFunction(computePrefixFunction(kmpMatcher.getPattern()));
	}
	
	public static void main(String[] args) {
		
		//Matcher.getRuntimes(10, 100, "matcherTimes.txt"); // Q2
		//Matcher.getRatios(10, 100, 50000, "matcherRatios.txt"); // Q3
		
		 Matcher.plotRuntimes(0.015060, 0.009816, "matcherTimes.txt"); // Q3	
		
		
	}
	
	public static int[] computePrefixFunction(String pattern) {
		
		int m = pattern.length(); // m <- P.length
		int k = 0; // k <- 0
		
		int[] pi = new int[m];
		
		pi[0] = 0; // p[1] <- 0
		
		for(int q = 1; q < m; q++) { // for q <- 2 to m do
			
			while((k > 0) && (pattern.charAt(k) != pattern.charAt(q))) { // while k > 0 and P[k + 1] != P[q] 
				k = pi[k - 1]; // do k <- pi[k]
			}
			if (pattern.charAt(k) == pattern.charAt(q)) { // if P[k + 1] = P[q] then  
				k++; //k <- k + 1
			}
			pi[q] = k; // pi[q] <- k
		}
		return pi; // return 
	}


	public static class KMPMatcher {

		private String text;
		private String pattern;
		private int textLen;
		private int patternLen;
		private int[] prefixFunction;
		private Queue matchIndices;

		public KMPMatcher(String text, String pattern) {
			this.text = text;
			this.pattern = pattern;
			this.textLen = text.length();
			this.patternLen = pattern.length();
			this.prefixFunction = new int[patternLen + 1];
			this.matchIndices = new Queue();
		}

		public void setPrefixFunction(int[] prefixFunction) {
			this.prefixFunction = prefixFunction;
		}

		public int[] getPrefixFunction() {
			return prefixFunction;
		}

		public String getPattern() {
			return pattern;
		}

		public Queue getMatchIndices() {
			return matchIndices;
		}
		
		// KMP Matcher
		public void search() {
			
			prefixFunction = computePrefixFunction(pattern); // pi <- Compute-Prefix-Function(P)
			int q = 0; // q <- 0
			
			if (m > n){
				matchIndices = new Queue();
			}

			for (int i = 1; i < textLen; i++) { // for i <- 1 to n do
				
				while ((q > 0) && (pattern.charAt(q) != text.charAt(i-1))) { // while q > 0 and P[q+1] != T[i]
					q = prefixFunction[q-1]; // do q <- pi[q]
				}
				
				if (pattern.charAt(q) == text.charAt(i-1)) { // if P[q + 1] = T[i] then
					q++; // q <- q + 1
				}
				
				if (q == patternLen) { // if q = m then 
					matchIndices.enqueue(i - patternLen); // enqueue(Q, i - m)
					q = prefixFunction[q-1]; // q <- pi[q]
				}
			}
		}
	}
}
