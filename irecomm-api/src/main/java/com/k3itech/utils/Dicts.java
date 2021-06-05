package com.k3itech.utils;

import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.huaban.analysis.jieba.WordDictionary;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author:dell
 * @since: 2021-06-04
 */
public class Dicts {
    private static Dicts singleton;
    public final Set<String> loadedPath = new HashSet<String>();




    public static Dicts getInstance() {
        if (singleton == null) {
            synchronized (Dicts.class) {
                if (singleton == null) {
                    singleton = new Dicts();
                    return singleton;
                }
            }
        }
        return singleton;
    }

    public static void init(String userDictPath, Charset charset) {
        synchronized (Dicts.class) {
            if (singleton == null) {
                getInstance();
                singleton.loadUserDict(userDictPath, charset);
            }

        }
    }

    public void loadUserDict(String userDictPath, Charset charset) {

        WordDictionary wordDictionary = WordDictionary.getInstance();
        Path dictpath = Paths.get(userDictPath);
        wordDictionary.loadUserDict(dictpath, charset);

        try {
            InputStream is = new FileInputStream(userDictPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));

            long s = System.currentTimeMillis();
            int count = 0;
            while (br.ready()) {
                String line = br.readLine();
                String[] tokens = line.split("[\t ]+");

                if (tokens.length < 1) {
                    // Ignore empty line
                    continue;
                }

                String word = tokens[0];
                if (tokens.length < 2) {
                    CustomDictionary.add(word);
                } else {
                    String freq = tokens[1];
                    CustomDictionary.add(word, "nz " + freq);
                }

            }
            System.out.println("load userdict");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
