package com.example.minchen.intelligent_tutoring_system_min_chen;

import com.example.minchen.intelligent_tutoring_system_min_chen.sentiment_analysis.SentimentAnalysis;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void sentiment_isCorrect() throws Exception {
        assertEquals("Not equal",SentimentAnalysis.getSentimentScore("I am sad."), 1, 0.001);
        assertEquals("Not equal",SentimentAnalysis.getSentimentScore("I am happy."), 3, 0.001);
        assertEquals("Not equal",SentimentAnalysis.getSentimentScore("I am so happy!"),4, 0.001);
    }
}