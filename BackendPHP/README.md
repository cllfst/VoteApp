# Vote App CakePHP Backend

[![Build Status](https://img.shields.io/travis/cakephp/app/master.svg?style=flat-square)](https://travis-ci.org/cakephp/app)
[![License](https://img.shields.io/packagist/l/cakephp/app.svg?style=flat-square)](https://packagist.org/packages/cakephp/app)

The framework source code can be found here: [cakephp/cakephp](https://github.com/cakephp/cakephp).

## Installation

1. Download [Composer](https://getcomposer.org/doc/00-intro.md) or update `composer self-update`.
2. Change to the project directory and run `composer Install` to install 
dependencies.
3. Create a new database and import `pollsAppSameDB.sql`.

## Configuration

Read and edit `config/app.php` and setup `'username'`, `'password'` and `'database'` in `'Datasources'` according to your MySQL configuration

```php
'Datasources' => [
        'default' => [
            'username' => 'root',
            'password' => 'root',
            'database' => 'pollsapp',
        ]
]           
```

## APIs

Use postman to test/simulate APIs

`GET` /api/getPollsList : returns a list of polls.

response : 200 (json)
````json
{
    "polls": [
        {
            "id": "30880f19-cd85-43e2-aa40-fe29a304a0cb",
            "text": "Elections CLLFST",
            "start_date": "2017-10-11T22:12:00+00:00",
            "end_date": "2017-10-11T22:12:00+00:00",
            "is_open": false
        }
    ]
}
````

`GET` /api/getPollQuestions/{poll_id} : return a list question for the provided poll

response : 200 (json)
```json
{
    "poll": {
        "id": "30880f19-cd85-43e2-aa40-fe29a304a0cb",
        "text": "Elections CLLFST",
        "start_date": "2017-10-11T22:12:00+00:00",
        "end_date": "2017-10-11T22:12:00+00:00",
        "is_open": false,
        "questions": [
            {
                "id": "64b90327-dfbc-4c44-b3b9-5b08bac71faa",
                "question_text": "Mediatisation ? ",
                "poll_id": "30880f19-cd85-43e2-aa40-fe29a304a0cb"
            },
            {
                "id": "89b83179-7c85-445f-a835-3be529f114f1",
                "question_text": "SG ? ",
                "poll_id": "30880f19-cd85-43e2-aa40-fe29a304a0cb"
            }
        ],
        "answers": [
            {
                "id": "e11b5bc8-2a83-4fcb-8f40-d92435083cb3",
                "user_id": "7fc03104-3875-45c1-8aaa-c3f62ffec0e3",
                "poll_id": "30880f19-cd85-43e2-aa40-fe29a304a0cb"
            }
        ]
    }
}
```


`GET` /api/getQuestionChoices/{question_id} : return a list of offered answers for the provided question
response : 200 (json)
```json
{
    "question": {
        "id": "64b90327-dfbc-4c44-b3b9-5b08bac71faa",
        "question_text": "Mediatisation ? ",
        "poll_id": "30880f19-cd85-43e2-aa40-fe29a304a0cb",
        "offered_answers": [
            {
                "id": "aef75c0a-afce-4e52-a31e-d57b6a3a2b8e",
                "answer_text": "RR",
                "count": 0,
                "question_id": "64b90327-dfbc-4c44-b3b9-5b08bac71faa"
            },
            {
                "id": "d872ff3c-f2be-44b2-8857-05ad97c854f0",
                "answer_text": "KN",
                "count": 0,
                "question_id": "64b90327-dfbc-4c44-b3b9-5b08bac71faa"
            }
        ],
        "poll": {
            "id": "30880f19-cd85-43e2-aa40-fe29a304a0cb",
            "text": "Elections CLLFST",
            "start_date": "2017-10-11T22:12:00+00:00",
            "end_date": "2017-10-11T22:12:00+00:00",
            "is_open": false
        }
    }
}
```

`GET` /api/vote/{offered_answer_id} : increment an offered_answer vote count by one.

response : 200 (json) : successful vote

```json
{
    "vote": {
        "success": true
    }
}
```
response : 200 (json) : user already voted
```json
{
    "response": {
        "success": false,
        "error": "Did vote already"
    }
}
```
`GET` /api/didVote/{offered_answer_id} : increment an offered_answer vote count by one.

response : 200 (json) : successful vote

```json
{
    "vote": {
        "success": true
    }
}
```
`POST` /api/login : login a user

body request

```json
{
	"email":"vote@polls.com",
	"password":"polls"
}
```

response : 200 (json) : successful login

```json
{
    "response": {
        "login": "success"
    }
}
```

response : 200 (json) : unsuccessful login

```json
{
    "response": {
        "login": "failed"
    }
}
```
   
`POST` /api/login : login a user

body request

```json
{
	"email":"vote@polls.com",
	"password":"polls"
}
```

response : 200 (json) : successful login

```json
{
    "response": {
        "login": "success"
    }
}
```

response : 200 (json) : unsuccessful login

```json
{
    "response": {
        "login": "failed"
    }
}
```

`GET` /api/logout : logout a user

response : 200 (json) 

```json
{
    "response": [
        "logged out successful"
    ]
}
```
`POST` /api/updatePassword : change current logged user password

body request
```json
{
	"current_password":"root",
	"password":"rotrot"
}
```

response : 200 (json) : password changed successfully

```json
{
    "response": {
        "success": "true"
    }
}
```

response : 200 (json) : incorrect current password
```json
{
    "response": {
        "success": "false",
        "error": "current password is incorrect"
    }
}
``` 