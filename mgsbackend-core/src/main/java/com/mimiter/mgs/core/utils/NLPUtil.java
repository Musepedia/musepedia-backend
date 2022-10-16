package com.mimiter.mgs.core.utils;

import com.mimiter.mgs.core.config.NLPConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NLPUtil {

    private final NLPConfig nlpConfig;

    private final String[] mapKeywords = {"分布", "哪里", "哪些地方"};
    private final String[] outlookKeywords = {"长什么样", "外观", "外形"};

    public static final int OTHER_QUESTION = 0;

    public static final int MAP_QUESTION = 2;

    public static final int OUTLOOK_QUESTION = 3;

    public void updateCustomDictionary(List<String> labels) {
        for (String label : labels) {
            DicLibrary.insert(nlpConfig.DICT_KEY, label);
        }
    }

    public void clearCustomDictionary() {
        DicLibrary.clear(nlpConfig.DICT_KEY);
    }

    public List<String> getTextSegmentation(String text) {
        List<String> res = new ArrayList<>();
        Result segmentResult = IndexAnalysis.parse(text, DicLibrary.get(nlpConfig.DICT_KEY));

        for (Term term : segmentResult) {
            if (term.getNatureStr().equals("userDefine") || term.getNatureStr().charAt(0) == 'n') {
                res.add(term.getName());
            }
        }

        return res;
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
}
