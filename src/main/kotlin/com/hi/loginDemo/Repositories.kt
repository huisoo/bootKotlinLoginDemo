package com.hi.loginDemo

import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Long> {
    fun findByUserId(userId:String):User
    fun findByPassword(password:String):User
}