package net.demo.ro

import grails.plugin.spock.IntegrationSpec

class PostIntegrationSpec extends IntegrationSpec {

    def "Adding posts to user links post to user"(){
        given: 'a new user'
        def joe = new User(loginId: 'claudiu', password: 'secret').save(failOnError: true)
        when: 'we add some posts to user'
        joe.addToPosts(new Post(content: 'some content'))
        joe.addToPosts(new Post(content: 'some content 1'))
        joe.addToPosts(new Post(content: 'some content 2'))
        then: 'user must have 3 posts'
        User.get(joe.id).posts.size() == 3
        print User.get(1).posts.last().content
    }

    def "Ensure posts linked to a user can be retrieved"() {
        given: 'a user with some posts'
        def joe = new User(loginId: 'claudiu', password: 'qwe123').save(failOnError: true)
        joe.addToPosts(new Post(content: 'content1'))
        joe.addToPosts(new Post(content: 'content2'))
        joe.addToPosts(new Post(content: 'content3'))
        when: 'we get user by id'
        def user = User.get(joe.id)
        List<String> posts = user.posts.collect{
            it.content
        }.sort()
        then:'the post appear'
        posts == ['content1','content2','content3']
    }

    def "Exercise tagging several posts with various tags"() {
        given: 'a new user'
        def joe = new User(loginId: 'claudiu3',password:  'secret').save(failOnError: true)
        def tagG = new Tag(name: 'groovy')
        def tagGr = new Tag(name: 'grails')
        joe.addToTags(tagG)
        joe.addToTags(tagGr)
        when: 'user tags some fresh posts'
        def onePost = new Post(content: 'a groovy post')
        joe.addToPosts(onePost)
        onePost.addToTags(tagG)
        def posts = new Post(content: 'some content')
        posts.addToTags(tagG)
        posts.addToTags(tagGr)
        then:
        joe.tags*.name == ['groovy', 'grails']
        joe.posts.size() == 1
        onePost.tags.size() == 1
        posts.tags.size() == 2
        joe.tags.size() == 2

    }

    def "Ensure a user can follow other users"(){

        given: 'a new user'
        def  joe = new User(loginId: 'claud', password: 'qwe123').save(failOnError: true)
        def  stan = new User(loginId: 'claudoiu', password: 'qwe123').save(failOnError: true)
        when: 'some follow'
        joe.addToFollowing(stan)
        stan.addToFollowing(joe)
        then:
        joe.following.size() == 1
        stan.following.size() == 1
    }

}