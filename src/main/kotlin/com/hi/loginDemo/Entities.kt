package com.hi.loginDemo

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User (
    var userId:String,
    var password:String,
    @Id @GeneratedValue var id:Long?=null)

//CREATE TABLE User(
//    userId TEXT NOT NULL,
//        password VARCHAR(20) NOT NULL,
//        id LONG NOT NULL PRIME KEY