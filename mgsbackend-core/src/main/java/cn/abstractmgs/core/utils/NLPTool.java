package cn.abstractmgs.core.utils;

import com.hankcs.hanlp.dictionary.CustomDictionary;
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

    public void updateCustomDictionary(List<String> labels) {
        for (String label : labels) {
            CustomDictionary.add(label);
        }
    }

    public static void main(String[] args) {
        NLPTool tool = new NLPTool("金樱子原产于哪里");
        System.out.println(tool.getNoun());

    }
}
