package buchlager.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Buch")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "titel")
    private String title;

    @Column(name = "bestand")
    private int amount;
    @ManyToOne
    @JoinColumn(name = "verlagId")
    private Publisher publisher;

    @JoinTable(name = "BuchAutoren", 
       joinColumns = @JoinColumn(name = "buchId", referencedColumnName = "id"), 
       inverseJoinColumns = @JoinColumn(name = "autorId", 
       referencedColumnName = "id"))
    @ManyToMany
    private Collection<Author> authors;

    public Book() {
    }

    public Book( String title, int amount) {
        this.title = title;
        this.amount = amount;
    }


    public Book(int id, String title, int amount) {
        this.id = id;
        this.title = title;
        this.amount = amount;
    }

    
    public Book(int id, String title, int amount, Publisher publisher) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.publisher = publisher;
    }

    public Book(int id, String title, int amount, Publisher publisher, Collection<Author> authors) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.publisher = publisher;
        this.authors = authors;
    }


	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + amount;
        result = prime * result + id;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (amount != other.amount)
            return false;
        if (id != other.id)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Book [amount=" + amount + ", id=" + id + ", title=" + title + "]";
    }

    
}