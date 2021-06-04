package com.k3itech.utils;

/**
 * created by dell on 2020-03-17
 */
public class CosineSimilarity {
	/**
	 * Method to calculate cosine similarity between two documents.计算两个文档之间余弦相似度的方法。
	 *
	 * @param docVector1 : document vector 1 (a)
	 * @param docVector2 : document vector 2 (b)
	 * @return
	 */
	public double cosineSimilarity(double[] docVector1, double[] docVector2) {
		double dotProduct = 0.0;
		double magnitude1 = 0.0;
		double magnitude2 = 0.0;
		double cosineSimilarity = 0.0;
//docVector1 and docVector2 must be of same length   docVector1和docVector2的长度必须相同
		for (int i = 0; i < docVector1.length; i++)
		{
			//a.b
			dotProduct += docVector1[i] * docVector2[i];
			//(a^2)
			magnitude1 += Math.pow(docVector1[i], 2);
			//(b^2)
			magnitude2 += Math.pow(docVector2[i], 2);
		}
		//sqrt(a^2)平方根
		magnitude1 = Math.sqrt(magnitude1);
		//sqrt(b^2)
		magnitude2 = Math.sqrt(magnitude2);

		if (Math.abs(magnitude1)!= 0.0 && Math.abs(magnitude2)!= 0.0) {
			cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
		} else {
			return 0.0;
		}
		return cosineSimilarity;
	}
}

