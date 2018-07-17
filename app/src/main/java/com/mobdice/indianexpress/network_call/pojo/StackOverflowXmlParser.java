package com.mobdice.indianexpress.network_call.pojo;

import android.util.Log;
import android.util.Xml;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class StackOverflowXmlParser {
    private static final String ns = null;

    public  InputStream stringToIS(String data)
    {
        InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        return stream;
    }

    public String xmlToString(String data)
    {
        XmlToJson xmlToJson = new XmlToJson.Builder(data).build();
        JSONObject jsonObject = xmlToJson.toJson();
        String jsonString = xmlToJson.toString();
        String formatted = xmlToJson.toFormattedString();
        Log.e("PRINT",""+jsonObject.toString());
        Log.e("PRINT1",""+jsonString.toString());
        Log.e("PRINT2",""+formatted.toString());
        return jsonString;
    }

    public void parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            readChannel(parser);
        } finally {
            in.close();
        }
    }

    private void readChannel(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            Log.e("XML:DATA1",""+name);
            if (name.equals("channel")) {
                readItem(parser);
//                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
    }

    private void readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            Log.e("XML:DATA2",""+name);
            if (name.equals("item")) {
                readItemContent(parser);
//                readItem(parser);
//                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
    }


    private void readItemContent(XmlPullParser parser) throws XmlPullParserException, IOException {
        List list = new ArrayList();
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String summary = null;
        String link = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle(parser);
                Log.e("HERE",""+title);
            }/* else if (name.equals("summary")) {
                summary = readSummary(parser);
            } else if (name.equals("link")) {
                link = readLink(parser);
            }*/ else {
                skip(parser);
            }
        }
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }



    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
