package Wolox.training.repositories;

import java.io.Serializable;
import org.springframework.data.repository.*;
import org.springframework.beans.annotation.*;

@NoRepositoryBean
public interface BookRepository <Book, ID extends Serializable> extends CrudRepository<Book, ID> {

    public Book findByAuthor(String author);

}
