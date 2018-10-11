package Wolox.training.services;

import Wolox.training.DAO.BookDAO;
import org.json.*;
import org.springframework.boot.jackson.JsonObjectDeserializer;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class OpenLibraryService {

    private String titleTag = "title";
    private String subtitleTag = "subtitle";
    private String publishersTag = "publishers";
    private String publishDateTag = "publish_date";
    private String pagesTag = "number_of_pages";
    private String authorsTag = "authors";
    private String requestLink = "https://openlibrary.org/api/books?bibkeys=ISBN:<ISBN>&format=json&jscmd=data";
    private JSONObject onlineServiceBookInfo;

    private void connectToServer(String isbn) {
        try {
            URL url = new URL(requestLink.replace("<ISBN>", isbn));
            try {
                URLConnection urlConnection = url.openConnection();
                InputStream stream = url.openStream();
                int chr;
                String content = "";
                while ((chr = stream.read()) != -1) {
                    content += (char)chr;
                }
                onlineServiceBookInfo = new JSONObject(content);
            } catch (IOException exIO) {
                throw new RuntimeException("Unable to connect to server");
            } catch (JSONException exJ) {
                throw new RuntimeException("Failed to parse site");
            }
        } catch (MalformedURLException exURL) {
            throw new RuntimeException("Invalid URL");
        }
    }

    private String parseString(String tag, String isbn) {
        String returnVal;
        try {
            returnVal = onlineServiceBookInfo.getJSONObject("ISBN:" + isbn).getString(tag);
        } catch (JSONException e) {
            returnVal = "0";
        }
        return returnVal;
    }

    private String parseArray(String tag, String isbn) {
        String returnVal = "";
        try {
            returnVal = onlineServiceBookInfo.getJSONObject("ISBN:" + isbn).getJSONArray(tag).getJSONObject(0).getString("name");
        } catch (JSONException e) {
        }
        return returnVal;
    }

    public BookDAO bookInfo(String isbn) {
        connectToServer(isbn);
        if (onlineServiceBookInfo.length() == 0) {
            throw new RuntimeException("The book does not exist");
        }
        String title = parseString(titleTag, isbn), subtitle = parseString(subtitleTag, isbn),
                publishers = parseArray(publishersTag, isbn), publishDate = parseString(publishDateTag, isbn),
                authors = parseArray(authorsTag, isbn);
        int pages = Integer.parseInt(parseString(pagesTag, isbn));
        BookDAO bookDAO = new BookDAO(isbn, title, subtitle, publishers, publishDate, pages);
        //authors.forEach((author) -> bookDAO.addAuthor(author));
        bookDAO.addAuthor(authors);
        return bookDAO;
    }
}
