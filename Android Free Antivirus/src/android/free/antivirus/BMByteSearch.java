package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;


public class BMByteSearch
{

    private byte[]		P;
    
    private int			m;
    
    private int[]		charJump;
    
    private int[]		matchJump;
    
    public
    BMByteSearch(String pattern)
    {
	genPatternFromCharArray(pattern.toCharArray());
	computeJumps();
	computeMatchJumps();
    }

    public
    BMByteSearch(byte[] pattern)
    {
	genPatternFromByteArray(pattern, 0, pattern.length);
	computeJumps();
	computeMatchJumps();
    }

    public
    BMByteSearch(byte[] pattern, int offset, int length)
    {
	genPatternFromByteArray(pattern, offset, length);
	computeJumps();
	computeMatchJumps();
    }

    private static final
    int min(int i1, int i2)
    {
	return (i1 < i2) ? i1 : i2;
    }

    private static final
    int max(int i1, int i2)
    {
	return (i1 > i2) ? i1 : i2;
    }

    private final
    void genPatternFromByteArray(byte[] bytes, int off, int length)
    {
	int i,j;
	m = length;
	P = new byte[length+1];
	for (i=1, j=off; i <= length; i++, j++) P[i] = bytes[j];
    }
    
    private final
    void genPatternFromCharArray(char[] chars)
    {
	m = chars.length;
	P = new byte[m + 1];
	for (int i=1; i<= m; i++) {
	    if (chars[i-1] > 127) P[i] = (byte) ((chars[i-1] - 256) & 0xff);
	    else P[i] = (byte) (chars[i-1] & 0xff);
	}
    }
    

    private final
    void computeJumps()
    {
	charJump = new int[256];
	for (int i=0; i<255; i++) charJump[i] = m;
	for (int k=1; k<=m; k++) charJump[P[k] + 128] = m - k;
    }
    
    private
    void computeMatchJumps()
    {
	int k, q, qq, mm;
	int[] back = new int[m + 2];

	matchJump = new int[m + 2];
	mm = 2 * m;

	for (k=1; k <= m; k++) matchJump[k] = mm - k;
	k = m; q = m + 1;
	while (k > 0) {
	    back[k] = q;
	    while ((q <= m) && (P[k] != P[q])) {
		matchJump[q] = min(matchJump[q], m - k);
		q = back[q];
	    }
	    k = k - 1; q = q - 1;
	}
	for (k = 1; k <= q; k++) {
	    matchJump[k] = min(matchJump[k], m + q - k);
	}
	qq = back[q];
	while (q <= m) {
	    while (q <= qq) {
		matchJump[q] = min(matchJump[q], qq - q + m);
		q = q + 1;
	    }
	    qq = back[qq];
	}
    }
    

    public
    int getPatternLength()
    {
	return(m);
    }

    public 
    int search(byte[] byteString)
    {
	return(search(byteString, 0, byteString.length));
    }
    

    public
    int search(byte[] byteString, int offset, int length)
    {
	int j, k, len;
	j = m + offset;
	k = m;
	len = min(byteString.length, offset + length);
	while ((j <= len) && (k > 0)) {
	    if ((byteString[j-1]) == P[k]) {
		j = j - 1; k = k - 1;
	    } else {
		j = j + max(charJump[byteString[j-1] + 128], matchJump[k]);
		k = m;
	    }
	}
	if (k == 0) return(j);
	return(-1);
    }
}



