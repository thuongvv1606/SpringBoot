package com.example.identity_service.enums;

public enum Role {
    ADMIN,
    STAFF, // updatePost, approvePost
    USER
}

// Permission (Privilege){
//    - createPost
//    - updatePost
// }

// User -> many Role
//        Role -> many Permisson
