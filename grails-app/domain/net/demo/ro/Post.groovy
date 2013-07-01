package net.demo.ro

class Post {
    String content
    Date dateCreated

    static belongsTo = [user:User]
    static hasMany = [tags:Tag]

    static constraints = {
        content maxSize: 1000

    }

    static mapping = {
        sort(dateCreated: 'desc')
    }

}
