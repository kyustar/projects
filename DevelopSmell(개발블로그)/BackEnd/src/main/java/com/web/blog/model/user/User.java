// 하단 DB 설정 부분은 Sub PJT II에서 데이터베이스를 구성한 이후에 주석을 해제하여 사용.

package com.web.blog.model.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int userno;
   
   private String uid;
   private String password;
   private String email;
   private String introduce;
   private String salt;
   private String profileurl;
   private String profilename;
   private int isadmin;
   private String token;

   @Column(insertable = false, updatable = false)
   private LocalDateTime createDate;

}