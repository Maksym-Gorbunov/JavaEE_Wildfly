# Schoolproject - JavaEnterprise

Small project for teaching purposes.

* Wildfly / JBoss
* JEE 8 
* Java 11
* Git
* Maven
* MySQL

## Wildfly configuration

Install any Wildfly release you want. I use 18.

Add a user, and place the school.cli script under the bin folder.<br>
Create database school. The script will need a mysql connector under `C:\Users`
to work. 

The script is predefined for `mysql.connector-java-8.0.12.jar`. Change location and version for your own liking.

Start Wildfly, and once running, open a new prompt, and go to the bin folder.<br>
Write `jboss-cli -c --file=school2.cli`

It should say outcome success. Write `jboss-cli -c --command=:reload` to restart the server.






# API Documentation
				    ---ADD---
request type: Post
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/add
body:
{
    "forename": "maks",
    "lastname": "example",
    "email": "maks.example@mail.com"
}
RESULT: 
status: 200
{
    "forename": "maks",
    "lastname": "example",
    "email": "maks.example@mail.com"
}
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/add
body:
{
    "forename": "maks",
    "lastname": "example",
    "email": "maks.example@mail.com"
}
RESULT: 
status: 417
{
    "Email already registered!"
}
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/add
body:
{
    "forename": "",
    "lastname": "example",
    "email": "maks.example@mail.com"
}
RESULT: 
status: 406
{
    "Fill in all details please"
}
______________________________________________________________________________________
Exception
RESULT:
{
    "Oops. Server side error!"		
}
______________________________________________________________________________________
		








				     ---DELETE---
request type: Delete
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/delete/maks@mail.com
RESULT: 
status: 200
{
    "id": null,
    "forename": "maks",
    "lastname": "example",
    "email": "maks.example@mail.com"
} 
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/delete/not_existed.example@mail.com
RESULT: 
status: 417
{
    "Student with current email not found!"
}
______________________________________________________________________________________
Exception
RESULT:
{
    "Oops. Server side error!"		
}
______________________________________________________________________________________
		










 				      ---FIND BY NAME---
request type: Get
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/find/name/maks
RESULT: 
status: 200
[
    {
        "id": null,
        "forename": "maks",
        "lastname": "example",
        "email": "maks.example@mail.com",
        "status": null
    }
]
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/find/name/not_existed_name
RESULT: 
status: 200
[]
______________________________________________________________________________________
Exception
RESULT:
{
    "Oops. Server side error!"		
}
______________________________________________________________________________________
		






 				---FIND BY EMAIL---
request type: Get
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/find/email/maks.example@mail.com
RESULT: 
status: 200
    {
        "id": null,
        "forename": "maks",
        "lastname": "example",
        "email": "maks.example@mail.com",
        "status": null
    }
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/find/email/not_existed_name
RESULT: 
status: 417
{
    "Student with current email not found"	
}
______________________________________________________________________________________
Exception
RESULT:
{
    "Oops. Server side error!"		
}
______________________________________________________________________________________
		









				    ---GET ALL---
request type: Get
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student	
RESULT: 
status: 200
[
    {
        "id": 1,
        "forename": "Max",
        "lastname": "Fry",
        "email": "max.fry@gmail.com"
    },
    {
        "id": 44,
        "forename": "maks",
        "lastname": "example",
        "email": "maks.example@mail.com"
    },
    ...
]






****************************
                            ---UPDATE---
request type: Put
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/update?
    forename=maks333
    &lastname=example333
    &email=maks.example@mail.com

RESULT: 
status: 200
RESULT:
{
    "id": null,
    "forename": "maks333",
    "lastname": "example333",
    "email": "maks.example@mail.com"
}
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/update?
    forename=maks333
    &lastname=example333
    &email=not_exist@mail.com

RESULT: 
status: 417
RESULT:
{
    "Student with current email not found!"
}
______________________________________________________________________________________
	
 http://localhost:8080/javaEE_lab1/student/update?
     forename=
     &lastname=example333
     &email=maks.example@mail.com
 
 RESULT: 
 status: 200
 RESULT:
 {
     "Fill in all details please"
 }
 ______________________________________________________________________________________
 	
  
  
  
  


                        ---UPDATE PARTIAL---
request type: Patch
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/update/partial

RESULT: 
status: 200
Body:
{
    "forename": "maks1111",
    "lastname": "example1111",
    "email": "maks.example@mail.com"
}
RESULT:
{
    "id": null,
    "forename": "maks1111",
    "lastname": "example1111",
    "email": "maks.example@mail.com"
}
______________________________________________________________________________________
http://localhost:8080/javaEE_lab1/student/update/partial

RESULT: 
status: 200
Body:
{
    "lastname": "example1111",
    "email": "maks.example@mail.com"
}
RESULT:
{
    "id": null,
    "forename": "maks1111",
    "lastname": "example1111",
    "email": "maks.example@mail.com"
}
______________________________________________________________________________________
	
 http://localhost:8080/javaEE_lab1/student/update/partial
 
 RESULT: 
 status: 406
 Body: {}
 RESULT:
 {
     "Fill in all details please"
 }
 ______________________________________________________________________________________
 	
  