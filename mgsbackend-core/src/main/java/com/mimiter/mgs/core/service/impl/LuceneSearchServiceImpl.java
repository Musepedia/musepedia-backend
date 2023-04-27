package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.core.service.ExhibitAliasService;
import com.mimiter.mgs.core.service.ExhibitService;
import com.mimiter.mgs.core.service.ExhibitTextService;
import com.mimiter.mgs.core.service.LuceneSearchService;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitAlias;
import com.mimiter.mgs.model.entity.ExhibitText;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@Service("luceneSearchService")
public class LuceneSearchServiceImpl implements LuceneSearchService, InitializingBean {

    private final List<String> fileDirectory = Arrays.asList("luceneForText", "luceneForLabel", "luceneForAlias");

    @Resource
    private ExhibitTextService exhibitTextService;

    @Resource
    private ExhibitAliasService exhibitAliasService;

    @Resource
    private ExhibitService exhibitService;

    @Override
    public void afterPropertiesSet() throws Exception {
        updateIndex();
    }

    /**
     * 使用query功能前需要使用updateIndex()创建索引
     */
    public void updateIndex() throws IOException {

        for (String fileD : fileDirectory) {
            Directory indexDirectory = MMapDirectory.open(new File(fileD).toPath());
            IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
            IndexWriter indexWriter = new IndexWriter(indexDirectory, config);

            //构造索引
            try {
                switch (fileD) {
                    case ("luceneForText"): {
                        List<ExhibitText> allTexts = exhibitTextService.getAllTextsForLucene();
                        for (ExhibitText text : allTexts) {
                            Document doc = new Document();
                            doc.add(new StringField("exhibit_id", String.valueOf(text.getExhibitId()), Field.Store.YES));
                            doc.add(new StringField("exhibit_text_id", String.valueOf(text.getId()), Field.Store.YES));
                            doc.add(new TextField("exhibit_text", text.getText(), Field.Store.YES));
                            indexWriter.addDocument(doc);
                        }
                        break;
                    }
                    case ("luceneForAlias"): {
                        List<ExhibitAlias> allAlias = exhibitAliasService.getAllAliases();
                        for (ExhibitAlias alias : allAlias) {
                            Document doc = new Document();
                            doc.add(new StringField("exhibit_id", String.valueOf(alias.getExhibitId()), Field.Store.YES));
                            doc.add(new TextField("exhibit_alias", alias.getAlias(), Field.Store.YES));
                            indexWriter.addDocument(doc);
                        }
                        break;
                    }
                    case ("luceneForLabel"): {
                        List<Exhibit> allLabel = exhibitService.getAllLabels();
                        for (Exhibit label : allLabel) {
                            Document doc = new Document();
                            doc.add(new StringField("exhibit_id", String.valueOf(label.getId()), Field.Store.YES));
                            doc.add(new TextField("exhibit_label", label.getLabel(), Field.Store.YES));
                            indexWriter.addDocument(doc);
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            indexWriter.close();
        }
    }

    @Override
    public List<ExhibitText> queryOnAlias(String question, Long museumId) throws IOException, ParseException {
        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(MMapDirectory.open(new File(fileDirectory.get(2)).toPath())));
        // 在Alias字段上搜索
        QueryParser parserForAlias = new QueryParser("exhibit_alias", new IKAnalyzer());

        HashSet<Long> docIds = new HashSet<>();
        List<ExhibitText> result = new ArrayList<>();

        TopDocs topDocs = indexSearcher.search(parserForAlias.parse(question), 10);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);

            Long docId = Long.valueOf(document.get("exhibit_id"));
            // 去重并判断是否在当前博物馆
            if (!docIds.contains(docId) && (museumId == exhibitService.getMuseumByExhibitId(docId))) {
                docIds.add(docId);
                result.addAll(exhibitTextService.getTextsByExhibitId(docId));
            }
        }

        indexSearcher.getIndexReader().close();


        return result;
    }

    @Override
    public List<ExhibitText> queryOnLabel(String question, Long museumId) throws IOException, ParseException {
        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(MMapDirectory.open(new File(fileDirectory.get(1)).toPath())));

        // 在Label字段上搜索
        QueryParser parserForLabel = new QueryParser("exhibit_label", new IKAnalyzer());

        HashSet<Long> docIds = new HashSet<>();
        List<ExhibitText> result = new ArrayList<>();

        TopDocs topDocs = indexSearcher.search(parserForLabel.parse(question), 10);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);

            Long docId = Long.valueOf(document.get("exhibit_id"));
            // 去重并判断是否在当前博物馆
            if (!docIds.contains(docId) && (museumId == exhibitService.getMuseumByExhibitId(docId))) {
                docIds.add(docId);
                result.addAll(exhibitTextService.getTextsByExhibitId(docId));
            }
        }

        indexSearcher.getIndexReader().close();

        return result;
    }

    @Override
    public List<ExhibitText> queryOnText(String question, Long museumId) throws IOException, ParseException {
        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(MMapDirectory.open(new File(fileDirectory.get(0)).toPath())));

        // 在Text字段上搜索
        QueryParser parserForText = new QueryParser("exhibit_text", new IKAnalyzer());
        Query query = parserForText.parse(question);

        HashSet<Long> docIds = new HashSet<>();
        List<ExhibitText> result = new ArrayList<>();

        TopDocs topDocs = indexSearcher.search(query, 30);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);

            // System.out.println(document.get("exhibit_text_id"));
            Long docId = Long.valueOf(document.get("exhibit_text_id"));
            // 去重并判断是否在当前博物馆
            if (!docIds.contains(docId) && museumId == exhibitService.getMuseumByExhibitId(Long.valueOf(document.get("exhibit_id")))) {
                docIds.add(docId);
                result.add(exhibitTextService.getTextByTextId(docId));
            }
        }
        indexSearcher.getIndexReader().close();

        return result;
    }

    @Override
    public List<ExhibitText> getTopKTexts(String question, Long museumId, int k) throws IOException, ParseException {
        List<ExhibitText> textOnText = this.queryOnText(question, museumId);
        List<ExhibitText> textOnAlias = this.queryOnAlias(question, museumId);
        List<ExhibitText> textOnLabel = this.queryOnLabel(question, museumId);

        List<ExhibitText> result = new ArrayList<>(textOnText);

        // 如果text中可以检索到相关文本，则进行Label或Alias的精确定位
        if (textOnText.size() > 0) {
            // 优先进行Label上的定位
            result.retainAll(textOnLabel);
            // 如果在Label上不存在交集，那么可能在Alias上可以定位
            if (result.size() == 0) {
                result = textOnText;
                result.retainAll(textOnAlias);
            }
        }
        // text中无法检索到，则直接通过Label或Alias进行定位
        else {
            if (textOnLabel.size() > 0) {
                result = textOnLabel;
            } else {
                result = textOnAlias;
            }
        }

        return result.subList(0, result.size() >= k ? k : result.size());
    }
}
