package com.own_world.instragramclone.Modals

class Post {
    var postUrl: String = ""
    var caption: String = ""

    constructor()
    constructor(postUrl: String, caption: String) {
        this.postUrl = postUrl
        this.caption = caption
    }
}