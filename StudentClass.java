package matcher;

public class StudentClass {

	public KMPMatcher kmpMatcher;

	public StudentClass(String text, String pattern) {
		kmpMatcher = new KMPMatcher(text, pattern);
	}

	public void buildPrefixFunction() {
		kmpMatcher.setPrefixFunction(computePrefixFunction(kmpMatcher.getPattern()));
	}
	
	public static int[] computePrefixFunction(String pattern) {
		
		int m = pattern.length(); 
		int k = 0;
		
		int[] pi = new int[m];
		
		pi[0] = 0;
		
		for(int q = 1; q < m; q++) {
			
			while((k > 0) && (pattern.charAt(k) != pattern.charAt(q))) {
				k = pi[k - 1];
			}
			if (pattern.charAt(k) == pattern.charAt(q)) {
				k++;
			}
			pi[q] = k;
		}
		return pi;
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

		public void search() {
			
			prefixFunction = computePrefixFunction(pattern);
			int q = 0;
			
			for (int i = 0; i < textLen; i++) {
				
				while ((q > 0) && (pattern.charAt(q) != text.charAt(i))) {
					q = prefixFunction[q];
				}
				
				if (pattern.charAt(q) == text.charAt(i)) {
					q++;
				}
				
				if (q == patternLen) {
					matchIndices.enqueue(i - patternLen);
					q = prefixFunction[q];
				}
			}
		}

	}
}