package net.demo.ro

import grails.plugin.spock.IntegrationSpec

class UserIntegrationSpec extends IntegrationSpec {


    def 'save a user in db'(){
        given: 'a new user'
        def  joe = new User(loginId: 'claudiu', password: 'secret')
        when: 'user is save'
        joe.save()
        then: 'user must be in db and no errors'
        joe.errors.errorCount == 0
        joe.id != null
        User.get(joe.id).id == joe.id
    }

    def 'update a user'(){
        given: 'a new user'
        def  joe = new User(loginId: 'claudiu', password: 'secret').save(failOnError: true)
        when: 'we get the joe user'
        def user = User.get(joe.id)
        user.password = 'qwe123'
        user.save(failOnError: true)
        then: 'the changes are in db'
        User.get(joe.id).password == 'qwe123'

    }

    def 'deleting users'(){
        given: 'a new user'
        def joe = new User(loginId: 'claudiu', password: 'qwe123').save(failOnError: true)
        when: 'we delete the user'
        def user = User.get(joe.id)
        user.delete(flush: true)
        then: 'the user must not be in db'
        !User.exists(user.id)

    }

    def 'validation failed'(){

        given: 'a new user'
        def joe = new User(loginId: "qq", password: "qwe123")
        when: 'we try to validate user'
        joe.validate()
        then: 'we should have some errors'
        joe.hasErrors()
        !joe.errors.getFieldError('loginId')
        "size.toosmall" == joe.errors.getFieldError('loginId').code
    }

    def 'recovering from loginId error'(){
        given: 'a new user'
        def joe = new User(loginId: 'cc', password:  'secret')
        assert joe.save() == null
        assert joe.hasErrors()
        when: 'we fix'
        joe.loginId = 'claudiu'
        joe.validate()
        then: 'we can save'
        !joe.hasErrors()
        joe.save()
    }


}