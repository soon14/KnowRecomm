package com.k3itech.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * created by dell on 2020-03-17
 */
public class TfIdf {

    /**
     * Calculates the tf of term termToCheck
     *
     * @param totalterms  : Array of all the words under processing document
     * @param termToCheck : term of which tf is to be calculated.
     * @return tf(term frequency) of term termToCheck
     */
    public double tfCalculator(List<String> totalterms, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {//将字符串与指定的对象比较，不考虑大小写。
                count++;
            }
        }
        return count / totalterms.size();
    }

    /**
     * Calculates idf of term termToCheck
     *
     * @param allTerms    : all the terms of all the documents
     * @param termToCheck
     * @return idf(inverse document frequency) score
     */
    public double idfCalculator(List<String> allTerms, String termToCheck) {
        double count = 0;
        for (String ss : allTerms) {

            if (ss.equalsIgnoreCase(termToCheck)) {
                count++;
                break;
            }

        }
        return 1 + Math.log(allTerms.size() / count);
    }

    /**
     * Method to create termVector according to its tfidf score. 根据其tfidf得分创建termVector的方法
     */
    public List<double[]> tfIdfCalculator(List<String> sentence1, List<String> sentence2, List<String> allTerms) {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency
        List<double[]> tfidfDocsVector = new ArrayList<double[]>();
//        for (String[] docTermsArray : termsDocsArray) {
        double[] tfidfvectors = new double[allTerms.size()];
        int count = 0;
        for (String terms : allTerms) {
            tf = new TfIdf().tfCalculator(sentence1, terms);//将字符串与指定的对象比较，不考虑大小写。 这里返回相似度
            idf = new TfIdf().idfCalculator(allTerms, terms);
            tfidf = tf * idf;
            tfidfvectors[count] = tfidf;
            count++;
        }
//        }

        double[] tfidfvectors2 = new double[allTerms.size()];

        int count2 = 0;
        for (String terms : allTerms) {
            tf = new TfIdf().tfCalculator(sentence2, terms);
            idf = new TfIdf().idfCalculator(allTerms, terms);
            tfidf = tf * idf;
            tfidfvectors2[count2] = tfidf;
            count2++;
        }
        tfidfDocsVector.add(tfidfvectors);  //storing document vectors;
        tfidfDocsVector.add(tfidfvectors2);  //storing document vectors;
        return tfidfDocsVector;
    }
}

