package cn.abstractmgs.utils;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.ArrayList;
import java.util.List;

public class NLPTool {
    private final String text;

    public NLPTool(String text) {
        this.text = text;
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
}
