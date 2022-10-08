package com.mimiter.mgs.core.utils;

import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.ArrayList;
import java.util.List;

public class NLPUtil {

    private final String text;
    private final String[] mapKeywords = {"分布", "哪里", "哪些地方"};
    private final String[] outlookKeywords = {"长什么样", "外观", "外形"};

    public static final int OTHER_QUESTION = 0;

    public static final int MAP_QUESTION = 2;

    public static final int OUTLOOK_QUESTION = 3;

    public NLPUtil(String text) {
        this.text = text;
    }

    public int questionTypeRecognition(String question) {
        for (String keyword: mapKeywords) {
            int flag = question.indexOf(keyword);
            if (flag != -1) {
                return MAP_QUESTION;
            }
        }
        for (String keyword: outlookKeywords) {
            int flag = question.indexOf(keyword);
            if (flag != -1) {
                return OUTLOOK_QUESTION;
            }
        }

        return OTHER_QUESTION;
    }

    public List<String> getNoun() {
        ArrayList<String> res = new ArrayList<>();
        List<Term> termList = StandardTokenizer.segment(text);
        for (Term term : termList) {
            if (term.nature.startsWith("n")) {
                res.add(term.word);
            }
        }
        return res;
    }

    public void updateCustomDictionary(List<String> labels) {
        for (String label : labels) {
            CustomDictionary.add(label);
        }
    }
}
