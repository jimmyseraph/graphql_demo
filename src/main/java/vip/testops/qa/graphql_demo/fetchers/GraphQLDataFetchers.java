package vip.testops.qa.graphql_demo.fetchers;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GraphQLDataFetchers {
    private static final List<Map<String, String>> books = new ArrayList<>();
    private static final List<Map<String, String>> authors = new ArrayList<>();

    static {
        Map<String, String> bookInfo1 = new HashMap<>();
        bookInfo1.put("id", "book-1");
        bookInfo1.put("name", "Harry Potter and the Philosopher's Stone");
        bookInfo1.put("authorId", "author-1");
        Map<String, String> bookInfo2 = new HashMap<>();
        bookInfo2.put("id", "book-2");
        bookInfo2.put("name", "Moby Dick");
        bookInfo2.put("authorId", "author-2");
        Map<String, String> bookInfo3 = new HashMap<>();
        bookInfo3.put("id", "book-3");
        bookInfo3.put("name", "Interview with the vampire");
        bookInfo3.put("authorId", "author-3");
        books.add(bookInfo1);
        books.add(bookInfo2);
        books.add(bookInfo3);

        Map<String, String> authorInfo1 = new HashMap<>();
        authorInfo1.put("id", "author-1");
        authorInfo1.put("firstName", "Joanne");
        authorInfo1.put("lastName", "Rowling");
        Map<String, String> authorInfo2 = new HashMap<>();
        authorInfo2.put("id", "author-2");
        authorInfo2.put("firstName", "Herman");
        authorInfo2.put("lastName", "Melville");
        Map<String, String> authorInfo3 = new HashMap<>();
        authorInfo3.put("id", "author-3");
        authorInfo3.put("firstName", "Anne");
        authorInfo3.put("lastName", "Rice");
        authors.add(authorInfo1);
        authors.add(authorInfo2);
        authors.add(authorInfo3);
    }

    public DataFetcher getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            return books
                    .stream()
                    .filter(book -> book.get("id").equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String,String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            return authors
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher createBookDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, Object> bookInput = dataFetchingEnvironment.getArgument("bookInfo");
            Map<String, String> book = new HashMap<>();
            book.put("id", "book-"+(books.size()+1));
            book.put("name", bookInput.get("name").toString());
            Map<String, String> authorInput = (Map<String, String>) bookInput.get("author");
            Map<String, String> author = new HashMap<>();
            author.put("id", "author-"+(authors.size()+1));
            book.put("authorId", author.get("id"));
            author.put("firstName", authorInput.get("firstName"));
            author.put("lastName", authorInput.get("lastName"));
            System.out.println("book-->"+book);
            System.out.println("author-->"+author);
            books.add(book);
            authors.add(author);
            return book;
        };
    }

//    public DataFetcher createAuthorDataFetcher() {
//        return dataFetchingEnvironment -> {
//            Map<String, String> author = dataFetchingEnvironment.getArgument("author");
//            author.put("id", "author-"+(authors.size()+1));
//            System.out.println("author-->"+author);
//            authors.add(author);
//            return author;
//        };
//    }
}
