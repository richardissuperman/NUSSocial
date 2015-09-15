# NUSSocial

NUSSocial is SNS Android application which simulates a famous Chinese In-Campus app “超级课程表”

Backend Service is here:
https://github.com/caolei-ntu/NewRestCrud


main page
![drawing](/wiki/s1.jpeg)


profile
![drawing](/wiki/s2.jpeg)



I used AWS(Amazon WebService) to deploy my RDS and Web Backend. Also, all the upload pictures will be store in AWS's' S3 service.

In server's side programming, Hibernate is used to open RDS session, Spring for Dependecy Injection(I have some Singleton Class, DataDao etc)'

