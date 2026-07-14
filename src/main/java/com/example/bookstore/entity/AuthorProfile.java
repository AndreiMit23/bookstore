    package com.example.bookstore.entity;

    import jakarta.persistence.*;

    import java.util.Objects;

    @Entity
    public class AuthorProfile {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long ID;
        @Column
        private String biography;
        @Column
        private String website;
        @OneToOne
        @JoinColumn(name = "author_id")

        //@JsonIgnore // se poate modifica si prin varianta cu Data Transfer Object ca sa nu trimitem AuthorRequest si AuthorProfileRequest
        // fac acest lucru pentru ca repository ul imi intoarce ba Author ba AuthorProfile si se formeaza un ciclu pt ca Author contine AuthorProfile si AuthorProfile contine Author
        //CAUZA CONCRETA: relatia bidirectionala am OneToOne si in AuthorProfile si in Author
        Author author;
        public AuthorProfile(String biography, String website){
            this.biography = biography;
            this.website = website;
        }

        public AuthorProfile() {

        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            AuthorProfile that = (AuthorProfile) o;
            return ID != null && Objects.equals(ID,that.ID);
        }

        @Override
        public int hashCode() {
            return getClass().hashCode();
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public String getBiography() {
            return biography;
        }

        public void setBiography(String biography) {
            this.biography = biography;
        }

        public Long getID() {
            return ID;
        }

        public void setID(Long ID) {
            this.ID = ID;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }
    }
