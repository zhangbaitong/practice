package com.zbt.hash;

public class SearchHash {

	/**
	 * 加法Hash 加法Hash就是把输入元素一个一个的加起来构成最后的结果
	 * 
	 * @param key
	 * @param prime
	 *            任意的质数
	 * @return 结果的值域为[0,prime-1]
	 */
	public static int additiveHash(String key, int prime) {
		int hash, i;
		for (hash = key.length(), i = 0; i < key.length(); i++)
			hash += key.charAt(i);
		return (hash % prime);
	}

	/**
	 * 位运算Hash 这类型Hash函数通过利用各种位运算（常见的是移位和异或）来充分的混合输入元素 标准的旋转Hash的构造如下
	 * 
	 * @param key
	 * @param prime
	 * @return
	 */
	public static int rotatingHash(String key, int prime) {
		int hash, i;
		for (hash = key.length(), i = 0; i < key.length(); ++i)
			hash = (hash << 4) ^ (hash >> 28) ^ key.charAt(i);
		return (hash % prime);
	}

	public static int rotatingHashOther(String key, int prime) {
		int hash = key.length(), i = 0;
		// 循环处理实现的其他变形
		// 1.
		hash = (hash << 5) ^ (hash >> 27) ^ key.charAt(i);
		// 2.
		hash += key.charAt(i);
		hash += (hash << 10);
		hash ^= (hash >> 6);
		// 3.
		if ((i & 1) == 0) {
			hash ^= (hash << 7) ^ key.charAt(i) ^ (hash >> 3);
		} else {
			hash ^= ~((hash << 11) ^ key.charAt(i) ^ (hash >> 5));
		}
		// 4.
		hash += (hash << 5) + key.charAt(i);
		// 5.
		hash = key.charAt(i) + (hash << 6) + (hash >> 16) - hash;
		// 6.
		hash ^= ((hash << 5) + key.charAt(i) + (hash >> 2));
		return (hash % prime);
	}

	/**
	 * 乘法Hash 这种类型的Hash函数利用了乘法的不相关性（乘法的这种性质，最有名的莫过于平方取头尾的随机数生成算法，虽然这种算法效果并不好）
	 * jdk5.0里面的String类的hashCode()方法也使用乘法Hash。不过，它使用的乘数是31。推荐的乘数还有：131, 1313,
	 * 13131, 131313等等。
	 * 
	 * @param key
	 * @return
	 */
	public static int bernstein(String key) {
		int hash = 0;
		int i;
		for (i = 0; i < key.length(); ++i)
			hash = 33 * hash + key.charAt(i);
		return hash;
	}

	// 使用这种方式的著名Hash函数还有：
	// 32位FNV算法
	int M_SHIFT = 0;
	int M_MASK = 0;// 数值待定

	public int FNVHash(byte[] data) {
		int hash = (int) 2166136261L;
		for (byte b : data)
			hash = (hash * 16777619) ^ b;
		if (M_SHIFT == 0)
			return hash;
		return (hash ^ (hash >> M_SHIFT)) & M_MASK;
	}
	
	//改进的FNV算法
	 public static int FNVHash1(String data) {
        final int p = 16777619;
        int hash = (int)2166136261L;
        for(int i=0;i<data.length();i++)
            hash = (hash ^ data.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
	}
	 
	//除了乘以一个固定的数，常见的还有乘以一个不断改变的数
	 public static int RSHash(String str){
        int b    = 378551;
        int a    = 63689;
        int hash = 0;
       for(int i = 0; i < str.length(); i++)
       {
          hash = hash * a + str.charAt(i);
          a    = a * b;
       }
       return (hash & 0x7FFFFFFF);
	}
	 
	 //数组hash
	 public static int hashcode(int[] v){
		 int s = 0;
		 int M = 0;//待定
		 for(int i=0; i<v.length; i++)
		 s=((s<<2)+(v[i]>>4))^(v[i]<<10);
		 s = s % M;
		 s = s < 0 ? s + M : s;
		 return s;
	 }

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}

}
