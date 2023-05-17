package com.example.firstrestalishev.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    public static final long tenDaysInMilliseconds = 864000000;

    public Book(Integer personId, String name, String author, int year) {
        this.personId = personId;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book(Integer personId, String name, String author, int year, Date receiptDate) {
        this.personId = personId;
        this.name = name;
        this.author = author;
        this.year = year;
        this.receiptDate = receiptDate;
    }

    public Book(int id, Integer personId, String name, String author, int year) {
        this.id = id;
        this.personId = personId;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="person_id")
    private Integer personId;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "year")
    private int year;

    @Column(name = "receipt_date")
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date receiptDate;

    @Transient
    private boolean bookIsOverdue;

    public Book(int id, int personId) {
        this.id = id;
        this.personId = personId;
    }

    public Book() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public boolean isBookIsOverdue() {
        return bookIsOverdue;
    }

    public void setBookIsOverdue(boolean bookIsOverdue) {
        this.bookIsOverdue = bookIsOverdue;
    }

    /*public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMilliseconds = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
    }*/

    public boolean bookIsOverdue() {
        long diffInMilliseconds = new Date().getTime() - this.receiptDate.getTime();
        return diffInMilliseconds > tenDaysInMilliseconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (year != book.year) return false;
        if (personId != null ? !personId.equals(book.personId) : book.personId != null) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        return receiptDate != null ? receiptDate.equals(book.receiptDate) : book.receiptDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (receiptDate != null ? receiptDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("id=").append(id);
        sb.append(", personId=").append(personId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", year=").append(year);
        sb.append(", receiptDate=").append(receiptDate);
        sb.append('}');
        return sb.toString();
    }
}
