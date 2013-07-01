package net.demo.ro

class Profile {
    User user
    byte [] photo
    String fullName
    String email
    String bio
    String country
    String homepage

    static constraints = {
        fullName blank: false
        bio nullable: true, maxSize: 1000
        homepage nullable: true, url: true
        email nullable: true, email: true
        country nullable: true

    }
}
