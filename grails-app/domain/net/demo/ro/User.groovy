package net.demo.ro

class User {

    String loginId
    String password

    static hasOne = [profile:Profile]
    static hasMany = [posts:Post, tags:Tag, following:User]

    static constraints = {
        loginId unique: true, size: 3..10
        password size: 2..30, validator: {pass, user ->
            return pass != user.loginId         //return false if the loginId is equal with password field
        }
        profile nullable: true
    }
}
