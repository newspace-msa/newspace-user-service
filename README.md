# newspace-user-service
<img src="https://github.com/user-attachments/assets/04d415b7-b379-4a0b-9aba-ff1d3609db85" width="300" />

<br>
newspace-user-service 입니다.
<br>
해당 레포지토리에는 유저 서비스를 제공하는 스프링 부트 프로젝트가 포함되어있습니다.
<br>

## 📍 프로젝트 설명
25.03.27 ~ 25.04.02
<br>
LG CNS AM Inspire Camp
<br>
미니프로젝트 2 - 9조
<br>
현민영(팀장) / 김지수 / 구동혁 / 박상욱 / 유영서

## 🛠️ 기술 스택

<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white"> <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white"> <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"> <img src="https://img.shields.io/badge/Spring%20Cloud-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring%20AI-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring%20WebFlux-6DB33F?style=for-the-badge&logo=SpringWebFlux&logoColor=white"> <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white"> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"> <img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white"> <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"> <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white"> <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"> <img src="https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=NGINX&logoColor=white"> 

<br/>

## 📂 폴더 구조

```
.
├── build.gradle
├── Dockerfile // 도커파일
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── lgcns
    │   │           └── newspaceuserservice
    │   │               ├── config
    │   │               │   └── PasswordConfig.java
    │   │               ├── controller
    │   │               │   └── UserController.java
    │   │               ├── dto
    │   │               │   ├── LoginRequestDto.java
    │   │               │   ├── SignupRequestDto.java
    │   │               │   ├── UserInfoRequestDto.java
    │   │               │   └── UserInfoResponseDto.java
    │   │               ├── entity
    │   │               │   ├── TimeStamp.java
    │   │               │   ├── User.java
    │   │               │   └── UserRole.java
    │   │               ├── exception
    │   │               │   ├── UserException.java
    │   │               │   └── UserResponseStatus.java
    │   │               ├── NewspaceUserServiceApplication.java
    │   │               ├── repository
    │   │               │   └── UserRepository.java
    │   │               ├── security
    │   │               │   ├── config
    │   │               │   │   └── SecurityConfig.java
    │   │               │   ├── constant
    │   │               │   │   ├── GrantType.java
    │   │               │   │   └── TokenType.java
    │   │               │   ├── dto
    │   │               │   │   └── JwtTokenInfo.java
    │   │               │   ├── filter
    │   │               │   │   ├── JwtAuthenticationFilter.java
    │   │               │   │   └── JwtAuthorizationFilter.java
    │   │               │   ├── jwt
    │   │               │   │   └── JwtTokenUtil.java
    │   │               │   ├── UserDetailsImpl.java
    │   │               │   ├── UserDetailsServiceImpl.java
    │   │               │   └── util
    │   │               │       └── FilterResponseUtil.java
    │   │               ├── service
    │   │               │   └── UserService.java
    │   │               └── util
    │   │                   └── FileUtil.java
    │   └── resources
    │       └── application.yml
    └── test
        └── java
            └── com
                └── lgcns
                    └── newspaceuserservice
                        └── NewspaceUserServiceApplicationTests.java
```
<br/>

## 🏗️ 시스템 아키텍처
![image](https://media.discordapp.net/attachments/1355032731234336798/1356602546273390592/image.webp?ex=67ed2a13&is=67ebd893&hm=8541702842be7512cbdcaf4b94296d582b256e16cb2cee1bac7f8807ba92095d&=&format=webp&width=1225&height=850)

## 📦 Github Repository
전체 : https://github.com/orgs/newspace-msa/repositories
<br>
Deploy : https://github.com/newspace-msa/newspace-deploy
<br>
Frontend : https://github.com/newspace-msa/newspace-frontend
<br>
Config : https://github.com/newspace-msa/newspace-config
<br>
Config-Server : https://github.com/newspace-msa/newspace-config-service
<br>
Gateway : https://github.com/newspace-msa/newspace-gateway
<br>
Eureka : https://github.com/newspace-msa/newspace-eureka
<br>
User-Service : https://github.com/newspace-msa/newspace-user-service
<br>
Notice-Service : https://github.com/newspace-msa/newspace-notice-service
<br>
News-Service : https://github.com/newspace-msa/newspace-news-service

## 📚 Notion
https://www.notion.so/LG-CNS-2-9-1c35254cd71680b490c6f7d3a8a0b2e6


