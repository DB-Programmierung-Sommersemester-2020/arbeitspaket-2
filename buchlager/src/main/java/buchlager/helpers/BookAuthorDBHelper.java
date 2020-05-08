package buchlager.helpers;

import buchlager.repositories.implementations.AuthorDB;
import buchlager.repositories.implementations.BookDB;

public class BookAuthorDBHelper {
   
    public static boolean authorIdExistsInAuthors(int id) {
        return (AuthorDB.getInstance().getById(id) != null);
    }

    public static boolean bookIdExistsInBooks(int bookId){
        return (BookDB.getInstance().getById(bookId) != null);
    }

    public static boolean bookAllreadyBelongsToAuthor(int authorId, int bookId){

        return AuthorDB.getInstance().getById(authorId).
            getBooks().contains(BookDB.getInstance().getById(bookId));
    }

    public static boolean authorAllreadyBelongsToBook(int bookId, int authorId){

        return BookDB.getInstance().getById(bookId).getAuthors().contains(AuthorDB.getInstance().getById(authorId));
    }
}