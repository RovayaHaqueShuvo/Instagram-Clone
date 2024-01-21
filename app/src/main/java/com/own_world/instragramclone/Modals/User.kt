package com.own_world.instragramclone.Modals

class User {
    var image:String? =null
    var userName:String? =null
    var email:String? =null
    var password:String? =null
    constructor()
    constructor(image: String?, userName: String?, email: String?, password: String?) {
        this.image = image
        this.userName = userName
        this.email = email
        this.password = password
    }

    constructor(userName: String?, email: String?, password: String?) {
        this.userName = userName
        this.email = email
        this.password = password
    }

    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }


}