package com.example.minchen.intelligent_tutoring_system_min_chen.sentiment_analysis;

import android.support.annotation.NonNull;

import com.example.minchen.intelligent_tutoring_system_min_chen.helper.SentimentMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
public class SentimentAnalysis {

    public static float getSentimentScore(String text) {

        int currSentimentScore = -100;
        Map<String, Integer> sentimentMap = initializeSentimentMap();
        int sumSentimentScore = 0;

        StanfordCoreNLP pipeline = initNLPPipeline();
        Annotation annotation = pipeline.process(text);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence: sentences) {
            // current sentiment string
            String currSentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            sentimentMap.put(currSentiment, sentimentMap.get(currSentiment)+1);

            // current sentiment score
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            currSentimentScore = RNNCoreAnnotations.getPredictedClass(tree);
            sumSentimentScore += currSentimentScore;

            System.out.println(currSentiment + "\t" + sentence);
            System.out.println("Sentiment score is: " + currSentimentScore + "\n");

        }

        return sumSentimentScore/(float)sentences.size();
    }

    @NonNull
    private static StanfordCoreNLP initNLPPipeline() {
        Properties props = new Properties();
        props.put("annotators", "tokenize,ssplit,pos,lemma,parse,sentiment");
        return new StanfordCoreNLP(props);
    }

    @NonNull
    private static Map<String, Integer> initializeSentimentMap() {
        Map<String, Integer> sentimentMap = new HashMap<>();
        sentimentMap.put(SentimentMapping.VERY_POSITIVE, 0);
        sentimentMap.put(SentimentMapping.POSITIVE, 0);
        sentimentMap.put(SentimentMapping.NEUTRAL, 0);
        sentimentMap.put(SentimentMapping.NEGATIVE, 0);
        sentimentMap.put(SentimentMapping.VERY_NEGATIVE, 0);
        return sentimentMap;
    }

}
