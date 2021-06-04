package com.k3itech.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by dell on 2020-03-30
 */
@Service
public class Analysis {


	public List<String> getAnalysisFromJieba(String sentence) {
		JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
		List<SegToken> tokens = jiebaSegmenter.process(sentence, JiebaSegmenter.SegMode.SEARCH);
		List<String> strings = new ArrayList<>();
		for (SegToken token : tokens) {
			strings.add(token.word);
		}
		return strings;
	}


	public double getCosineSimilarity(String sentence1, String sentence2) {
		List<String> allTerms = new ArrayList<>();
		List<String> terms1 = getAnalysisFromJieba(sentence1);
		List<String> terms2 = getAnalysisFromJieba(sentence2);

		for (String term : terms1) {
			//avoid duplicate entry 避免重复输入
			if (!allTerms.contains(term)) {
				allTerms.add(term);
			}
		}

		for (String term : terms2) {
			//avoid duplicate entry 避免重复输入
			if (!allTerms.contains(term)) {
				allTerms.add(term);
			}
		}

		TfIdf tfIdf = new TfIdf();
		//计算相似度比例
		List<double[]> tfidfvector = tfIdf.tfIdfCalculator(terms1, terms2, allTerms);
		CosineSimilarity cosineSimilarity = new CosineSimilarity();
		return cosineSimilarity.cosineSimilarity(tfidfvector.get(0), tfidfvector.get(1));

	}

	public List<String> getAnalysisFromJieba1(String sentence) {
		JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();

		if (sentence != null && sentence.length() > 0) {
			sentence = sentence.replaceAll("[、。:：……，,]", "");
		}
		List<String> strings = new ArrayList<>();
		if (sentence != null && sentence.length() > 0) {
			List<SegToken> tokens = jiebaSegmenter.process(sentence, JiebaSegmenter.SegMode.SEARCH);

			for (SegToken token : tokens) {
				if (!token.word.equalsIgnoreCase(" ") && !token.word.equalsIgnoreCase("\\n")) {
					strings.add(token.word);
				}
			}
		}
		return strings;
	}

	/**
	 * 先分词后剔除
	 *
	 * @param sentence
	 * @return
	 */
	public List<String> getAnalysisFromJieba2(String sentence) {
		JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
/*
		if (sentence != null && sentence.length() > 0) {
			sentence = sentence.replaceAll("[、。:：……，,]", "");
		}

 */
		List<String> strings = new ArrayList<>();
		if (sentence != null && sentence.length() > 0) {
			List<SegToken> tokens = jiebaSegmenter.process(sentence, JiebaSegmenter.SegMode.SEARCH);

			for (SegToken token : tokens) {
				if (!token.word.equalsIgnoreCase(" ") && !token.word.equalsIgnoreCase("、") && !token.word.equalsIgnoreCase("。") &&
						!token.word.equalsIgnoreCase(":") && !token.word.equalsIgnoreCase("：") && !token.word.equalsIgnoreCase("……")
						&& !token.word.equalsIgnoreCase("，") && !token.word.equalsIgnoreCase(",")) {
					strings.add(token.word);
				}
			}
		}
		return strings;
	}


	public double getCosineSimilaritynew(List<String> terms1, List<String> terms2) {
		List<String> allTerms = new ArrayList<>();
        /*List<String> terms1 = getAnalysisFromJieba(sentence1);
        List<String> terms2 = getAnalysisFromJieba(sentence2);*/
		if ((terms1 != null && terms1.size() != 0) && (terms2 != null && terms2.size() != 0)) {
			for (String term : terms1) {
				//avoid duplicate entry
				if (!allTerms.contains(term)) {
					allTerms.add(term);
				}
			}

			for (String term : terms2) {
				//avoid duplicate entry
				if (!allTerms.contains(term)) {
					allTerms.add(term);
				}
			}

			TfIdf tfIdf = new TfIdf();
			List<double[]> tfidfvector = tfIdf.tfIdfCalculator(terms1, terms2, allTerms);
			CosineSimilarity cosineSimilarity = new CosineSimilarity();
			return cosineSimilarity.cosineSimilarity(tfidfvector.get(0), tfidfvector.get(1));

		} else {
			return 0;
		}
	}

	public double getCosineSimilarityScore(List<String> terms1, String s2) {
		List<String> terms2 = new ArrayList<>();
		if (!StringUtils.isBlank(s2)) {
			terms2 = Arrays.asList(s2.split(","));
		}
		List<String> allTerms = new ArrayList<>();
        /*List<String> terms1 = getAnalysisFromJieba(sentence1);
        List<String> terms2 = getAnalysisFromJieba(sentence2);*/
		if ((terms1 != null && terms1.size() != 0) && (terms2 != null && terms2.size() != 0)) {
			for (String term : terms1) {
				//avoid duplicate entry
				if (!allTerms.contains(term)) {
					allTerms.add(term);
				}
			}

			for (String term : terms2) {
				//avoid duplicate entry
				if (!allTerms.contains(term)) {
					allTerms.add(term);
				}
			}

			TfIdf tfIdf = new TfIdf();
			List<double[]> tfidfvector = tfIdf.tfIdfCalculator(terms1, terms2, allTerms);
			CosineSimilarity cosineSimilarity = new CosineSimilarity();
			return cosineSimilarity.cosineSimilarity(tfidfvector.get(0), tfidfvector.get(1));

		} else {
			return 0;
		}
	}

}
