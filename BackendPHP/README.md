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
'Datasources' : [
        'default' : [
            'username' : 'root',
            'password' : 'root',
            'database' : 'pollsapp',
        ]
]           
```

## APIs

Use postman to test/simulate APIs.

`GET` request should have the header `Accept` with the value `application/json`

`POST` and `PUT` requests should have both `Accept` and `Content-Type` header with the value `application/json`

Each request besides register and token needs a token, you need to add the header `Authorization` with the value `Bearer {token}`

`POST` /api/register : register a new user

body request (json)
```json
{
	"first_name":"aymen",
	"last_name":"chebbi",
	"email":"aymen.chebi@gmail.com"
}
```
response (json) : Registered Successfully 
```json
{
    "code": 0,
    "data": {
        "message": "Registered Successfully",
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZjZjZjY3My01ZDkzLTQ1ZDgtYjJkYy04YmM3ZTU4NDJkN2MifQ.wQ8g92cYzUlku5hclKDT-eeMO3OpDxXZ8Dxs8n_RSTE"
    }
}
```
response (json) : This Email is registered already
```json
{
    "code": -1,
    "data": {
        "message": "This Email is registered already"
    }
}
```

response (json) : Can't register the user
```json
{
    "code": -2,
    "data": {
        "message": "Can\'t register the user"
    }
}
```

`POST` /api/token : request a token for a specific user

body request (json)

```json
{
	"email":"aymen.chebi@gmail.com",
	"password":"root"
	
}
```
response (json) : Success
```json
{
    "code": 0,
    "data": {
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZjZjZjY3My01ZDkzLTQ1ZDgtYjJkYy04YmM3ZTU4NDJkN2MifQ.wQ8g92cYzUlku5hclKDT-eeMO3OpDxXZ8Dxs8n_RSTE"
    }
}
```

response (json) : Email is not registered
```json
{
    "code": -1,
    "data": {
        "message": "Email is not registered"
    }
}
```

response (json) : Invalid Credentials
```json
{
    "code": -2,
    "data": {
        "message": "Invalid Credentials"
    }
}
```

`POST` /api/updatePassword : change current logged user password

body request (json)
```json
{
	"current_password":"root",
	"password":"rotrot"
}
```

response (json) : password changed successfully
```json
{
    "code": 0,
    "data": {
        "message": "Password updated successfully"
    }
}
```

response (json) : Password update error
```json
{
    "code": -1,
    "data": {
        "message": "Cannot update Password"
    }
}
```

response : 200 (json) : incorrect current password
```json
{
    "code": -2,
    "data": {
        "message": "Current password is incorrect"
    }
}
``` 

`GET` /api/getPollsList : returns a list of polls.

response (json)
````json
{
    "code": 0,
    "data": [
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
    "code": 0,
    "data": {
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
                "id": "38e07b06-bd38-42a1-8cc9-35d6c6c376ea",
                "user_id": "af6cf673-5d93-45d8-b2dc-8bc7e5842d7c",
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
    "code": "0",
    "data": {
        "id": "64b90327-dfbc-4c44-b3b9-5b08bac71faa",
        "question_text": "Mediatisation ? ",
        "poll_id": "30880f19-cd85-43e2-aa40-fe29a304a0cb",
        "offered_answers": [
            {
                "id": "aef75c0a-afce-4e52-a31e-d57b6a3a2b8e",
                "answer_text": "RR",
                "count": 2,
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
    "code": 0,
    "data": {
        "message": "Voted successfully"
    }
}
```
response (json) : user already voted
```json
{
    "code": -2,
    "data": {
        "message": "Did vote already"
    }
}
```
`GET` /api/didVote/{poll_id} : indicate whether the current user voted on a poll or not.
    
response (json) : Did already vote

```json
{
    "code": -1,
    "data": {
        "message": "Did vote already"
    }
}
```

response (json) : Didn't vote
```json
{
    "code": 0,
    "data": {
        "message": "Didn't vote"
    }
}
```