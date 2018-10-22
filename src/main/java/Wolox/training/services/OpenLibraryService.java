package Wolox.training.services;

import Wolox.training.DAO.BookDAO;
import Wolox.training.exceptions.BookDoesNotExistException;
import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class OpenLibraryService {

    private static final String TITLE_TAG = "title";
    private static final String SUBTITLE_TAG = "subtitle";
    private static final String PUBLISHERS_TAG = "publishers";
    private static final String PUBLISHDATE_TAG = "publish_date";
    private static final String PAGES_TAG = "number_of_pages";
    private static final String AUTHORS_TAG = "authors";
    private static final String COVER_TAG = "cover";
    private static final String ISBN_TAG = "ISBN: ";
    private String requestLink = "https://openlibrary.org/api/books?bibkeys=ISBN:<ISBN>&format=json&jscmd=data";
    private JSONObject onlineServiceBookInfo;

    private void connectToServer(String isbn) {
        try {
            URL url = new URL(requestLink.replace("<ISBN>", isbn));
            try {
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
            returnVal = onlineServiceBookInfo.getJSONObject(ISBN_TAG + isbn).getString(tag);
        } catch (JSONException e) {
            returnVal = "0";
        }
        return returnVal;
    }

    private String parseArray(String tag, String isbn) {
        String returnVal = "";
        try {
            returnVal = onlineServiceBookInfo.getJSONObject(ISBN_TAG + isbn).getJSONArray(tag).getJSONObject(0).getString("name");
        } catch (JSONException e) {
        }
        return returnVal;
    }

    private String parseCover(String isbn) {
        String returnVal = "";
        try {
            returnVal = onlineServiceBookInfo.getJSONObject(ISBN_TAG + isbn).getJSONArray(COVER_TAG).getJSONObject(0).getString("large");
        } catch (JSONException e) {
        }
        return returnVal;
    }

    public BookDAO bookInfo(String isbn) throws BookDoesNotExistException {
        connectToServer(isbn);
        if (onlineServiceBookInfo.length() == 0) {
            throw new BookDoesNotExistException("The book does not exist");
        }
        String title = parseString(TITLE_TAG, isbn), subtitle = parseString(SUBTITLE_TAG, isbn),
                publishers = parseArray(PUBLISHERS_TAG, isbn), publishDate = parseString(PUBLISHDATE_TAG, isbn),
                authors = parseArray(AUTHORS_TAG, isbn), cover = parseCover(isbn);
        int pages = Integer.parseInt(parseString(PAGES_TAG, isbn));
        BookDAO bookDAO = new BookDAO(isbn, title, subtitle, publishers, publishDate, pages, cover);
        bookDAO.addAuthor(authors);
        return bookDAO;
    }
}
