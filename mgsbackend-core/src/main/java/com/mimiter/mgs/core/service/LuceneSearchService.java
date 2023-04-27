package com.mimiter.mgs.core.service;

import com.mimiter.mgs.model.entity.ExhibitText;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *lucene search
 *倒排索引检索
 * 使用query功能前需要使用updateIndex()创建索引
 */
public interface LuceneSearchService {
    void updateIndex() throws IOException, SQLException;

    List<ExhibitText> queryOnAlias(String question, Long museumId) throws IOException, ParseException;

    List<ExhibitText> queryOnLabel(String question, Long museumId) throws IOException, ParseException;

    List<ExhibitText>queryOnText(String question, Long museumId) throws IOException, ParseException;

    List<ExhibitText>getTopKTexts(String question, Long museumId, int k) throws IOException, ParseException;
}