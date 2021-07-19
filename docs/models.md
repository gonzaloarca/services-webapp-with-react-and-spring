# Modelos - DTOs

{{baseUrl}} = {{absolutePath}}/api/v1

Recordamos que en los PUT no son todos los datos necesarios.

Tampoco lo son ciertos query params en determinadas situaciones.

## Tabla de Contenidos

- [Modelos - DTOs](#modelos---dtos)
  - [Tabla de Contenidos](#tabla-de-contenidos)
  - [jobContract](#jobcontract)
    - [POST: {{baseUrl}}/contracts](#post-baseurlcontracts)
    - [PUT: {{baseUrl}}/contracts/40?role=professional](#put-baseurlcontracts40roleprofessional)
    - [GET: {{baseUrl}}/contracts/40](#get-baseurlcontracts40)
    - [GET image: {{baseUrl}}/contracts/18/image](#get-image-baseurlcontracts18image)
    - [PUT image: {{baseUrl}}/contracts/3/image](#put-image-baseurlcontracts3image)
    - [GET contracts by role and state:](#get-contracts-by-role-and-state)
    - [{{baseUrl}}/contracts?userId=5&role=professional&state=finalized](#baseurlcontractsuserid5roleprofessionalstatefinalized)
  - [jobPost](#jobpost)
    - [POST: {{baseUrl}}/job-posts](#post-baseurljob-posts)
    - [PUT: {{baseUrl}}/job-posts/19/](#put-baseurljob-posts19)
    - [GET all: {{baseUrl}}/job-posts](#get-all-baseurljob-posts)
    - [GET: {{baseUrl}}/job-posts/19/](#get-baseurljob-posts19)
  - [package](#package)
    - [POST: {{baseUrl}}/job-posts/1/packages](#post-baseurljob-posts1packages)
    - [PUT: {{baseUrl}}/job-posts/1/packages/21](#put-baseurljob-posts1packages21)
    - [GET all: {{baseUrl}}/job-posts/1/packages](#get-all-baseurljob-posts1packages)
    - [GET: {{baseUrl}}/job-posts/1/packages/21](#get-baseurljob-posts1packages21)
  - [jobCard](#jobcard)
    - [GET all: {{baseUrl}}/job-cards/?page=1](#get-all-baseurljob-cardspage1)
    - [GET search: {{baseUrl}}/job-cards/search?page=1&zone=1&query=plumbi](#get-search-baseurljob-cardssearchpage1zone1queryplumbi)
    - [GET by proId: {{baseUrl}}/job-cards/?page=1&userId=5](#get-by-proid-baseurljob-cardspage1userid5)
    - [GET related by proId: {{baseUrl}}/job-cards/?page=1&userId=5&type=related](#get-related-by-proid-baseurljob-cardspage1userid5typerelated)
  - [review](#review)
    - [POST: {{baseUrl}}/reviews/](#post-baseurlreviews)
    - [GET: {{baseUrl}}/reviews/1](#get-baseurlreviews1)
    - [GET all: {{baseUrl}}/reviews](#get-all-baseurlreviews)
    - [GET by postId: {{baseUrl}}/reviews?page=1&postId=5](#get-by-postid-baseurlreviewspage1postid5)
    - [GET by userId: {{baseUrl}}/reviews?page=1&userId=5&role=professional](#get-by-userid-baseurlreviewspage1userid5roleprofessional)
  - [user](#user)
    - [POST: {{baseUrl}}/users](#post-baseurlusers)
    - [PUT: {{baseUrl}}/users/5](#put-baseurlusers5)
    - [GET: {{baseUrl}}/users](#get-baseurlusers)
    - [GET by id: {{baseUrl}}/users/5](#get-by-id-baseurlusers5)
    - [GET by email: {{baseUrl}}/users/search?email=manaaasd@gmail.com](#get-by-email-baseurluserssearchemailmanaaasdgmailcom)
    - [GET rates: {{baseUrl}}/professionals/5/reviews-by-exact-rate](#get-rates-baseurlprofessionals5reviews-by-exact-rate)
    - [GET rankings: {{baseUrl}}/users/5/rankings](#get-rankings-baseurlusers5rankings)
    - [GET image: {{baseUrl}}/users/5/image](#get-image-baseurlusers5image)
    - [PUT image: {{baseUrl}}/users/5/image](#put-image-baseurlusers5image)
    - [POST verify email: {{baseUrl}}/users/5/verify](#post-verify-email-baseurlusers5verify)
    - [POST recover account: {{baseUrl}}/users/recover-account](#post-recover-account-baseurlusersrecover-account)
  - [contract (Mis servicios)](#contract-mis-servicios)
    - [POST: {{baseUrl}}/contracts](#post-baseurlcontracts-1)
    - [PUT: {{baseUrl}}/contracts/40?role=professional](#put-baseurlcontracts40roleprofessional-1)
    - [GET: {{baseUrl}}/contracts/40](#get-baseurlcontracts40-1)
    - [GET contractCards: {{baseUrl}}/contracts?userId=5&role=professional&state=finalized](#get-contractcards-baseurlcontractsuserid5roleprofessionalstatefinalized)
    - [GET image: {{baseUrl}}/contracts/3/image](#get-image-baseurlcontracts3image)
    - [PUT image: {{baseUrl}}/contracts/3/image](#put-image-baseurlcontracts3image-1)
  - [categories](#categories)
    - [GET: {{baseUrl}}/categories](#get-baseurlcategories)

## jobContract

### POST: {{baseUrl}}/contracts

```json
{
  "clientId": 1,
  "description": "Necesito que cuides a 4 nenes de 4 años",
  "jobPackageId": 13,
  "scheduledDate": "2021-06-16T14:25:28.028799"
}
```

### PUT: {{baseUrl}}/contracts/40?role=professional

```json
{
  "newScheduledDate": "2021-06-16T14:20:20.028799",
  "newState": 2
}
```

### GET: {{baseUrl}}/contracts/40

```json
{
  "clientId": 1,
  "creationDate": "2021-07-16T16:24:36.055",
  "description": "Necesito que cuides a 4 nenes de 4 años",
  "id": 40,
  "image": "http://localhost:8080/api/v1/contracts/40/image",
  "jobPackage": "http://localhost:8080/api/v1/job-posts/13/packages/13",
  "lastModifiedDate": "2021-07-16T16:35:40.292",
  "scheduledDate": "2021-06-16T14:20:20.028799",
  "state": {
    "description": "CLIENT_REJECTED",
    "id": 2
  }
}
```

### GET image: {{baseUrl}}/contracts/18/image

Retorna imagen como body

### PUT image: {{baseUrl}}/contracts/3/image

En un form con key "file" viajara la imagen

### GET contracts by role and state:

### {{baseUrl}}/contracts?userId=5&role=professional&state=finalized

```json
[
  {
    "avgRate": 3.6666666666666665,
    "client": "http://localhost:8080/api/v1/users/3",
    "contractsCompleted": 5,
    "creationDate": "2021-07-15T21:50:22.663",
    "imageUrl": "http://localhost:8080/api/v1/job-posts/8/images/12",
    "jobContractDto": "http://localhost:8080/api/v1/contracts/34",
    "jobPackageDto": "http://localhost:8080/api/v1/job-posts/8/packages/8",
    "jobPost": "http://localhost:8080/api/v1/job-posts/8",
    "jobTitle": "Niñero turno mañana",
    "jobType": {
      "description": "Babysitting",
      "id": 7
    },
    "packageTitle": "4 dias a la semana 4 horas",
    "professional": "http://localhost:8080/api/v1/users/5",
    "rateType": {
      "description": "TBD",
      "id": 2
    },
    "reviewsCount": 3,
    "scheduledDate": "2021-06-16T14:25:28.028799",
    "state": {
      "description": "CLIENT_REJECTED",
      "id": 2
    }
  }
]
```

## jobPost

### POST: {{baseUrl}}/job-posts

```json
{
  "availableHours": "Lunes a viernes entre las 8am y 2pm",
  "jobType": 7,
  "proId": 5,
  "title": "Niñero turno mañana",
  "zones": [25, 20, 9]
}
```

### PUT: {{baseUrl}}/job-posts/19/

```json
{
  "active": false,
  "availableHours": "Lunes a viernes entre las 8am y 2pm",
  "jobType": 7,
  "title": "Niñero turno mañana",
  "zones": [25, 20, 9, 10, 11, 12]
}
```

### GET all: {{baseUrl}}/job-posts

```json
[
  {
    "active": true,
    "availableHours": "Todo los dias",
    "creationDate": "2021-05-02T18:22:13.338478",
    "id": 10,
    "images": ["http://localhost:8080/api/v1/job-posts/10/images/15"],
    "jobType": {
      "description": "Electricity",
      "id": 1
    },
    "packages": "http://localhost:8080/api/v1/job-posts/packages",
    "professional": "http://localhost:8080/api/v1/users/6",
    "reviews": "http://localhost:8080/api/v1/reviews?postId=10",
    "title": "Fran",
    "zones": [
      {
        "description": "Retiro",
        "id": 28
      }
    ]
  }
]
```

### GET: {{baseUrl}}/job-posts/19/

```json
{
  "active": false,
  "availableHours": "Lunes a viernes entre las 8am y 2pm",
  "creationDate": "2021-07-16T17:01:46.003",
  "id": 19,
  "images": [],
  "jobType": {
    "description": "Babysitting",
    "id": 7
  },
  "packages": "http://localhost:8080/api/v1/job-posts/19/packages",
  "professional": "http://localhost:8080/api/v1/users/5",
  "reviews": "http://localhost:8080/api/v1/reviews?postId=19",
  "title": "Niñero turno mañana",
  "zones": [
    {
      "description": "Parque Patricios",
      "id": 25
    },
    {
      "description": "Nuñez",
      "id": 20
    },
    {
      "description": "Colegiales",
      "id": 9
    },
    {
      "description": "Constitucion",
      "id": 10
    },
    {
      "description": "Flores",
      "id": 11
    },
    {
      "description": "Floresta",
      "id": 12
    }
  ]
}
```

## package

### POST: {{baseUrl}}/job-posts/1/packages

```json
{
  "description": "Muchos trabajos",
  "price": 160.0,
  "rateType": 0,
  "title": "Paquete simple"
}
```

### PUT: {{baseUrl}}/job-posts/1/packages/21

> Aclaración: no tiene price porque el rateType es TBD

```json
{
  "isActive": true,
  "description": "Muchos trabajos",
  "price": 160.0,
  "rateType": 0,
  "title": "Paquete simple"
}
```

### GET all: {{baseUrl}}/job-posts/1/packages

```json
[
  {
    "active": true,
    "description": "Muchos trabajos",
    "id": 20,
    "jobPost": "http://localhost:8080/api/v1/job-posts/1",
    "price": 160.0,
    "rateType": {
      "description": "HOURLY",
      "id": 0
    },
    "title": "Paquete simple"
  }
]
```

### GET: {{baseUrl}}/job-posts/1/packages/21

```json
{
  "active": false,
  "description": "Muchos trabajos",
  "id": 21,
  "jobPost": "http://localhost:8080/api/v1/job-posts/1",
  "price": 160.0,
  "rateType": {
    "description": "HOURLY",
    "id": 0
  },
  "title": "Paquete simple"
}
```

## jobCard

### GET all: {{baseUrl}}/job-cards/?page=1

### GET search: {{baseUrl}}/job-cards/search?page=1&zone=1&query=plumbi

### GET by proId: {{baseUrl}}/job-cards/?page=1&userId=5

### GET related by proId: {{baseUrl}}/job-cards/?page=1&userId=5&type=related

```json
[
  {
    "avgRate": 0.0,
    "contractsCompleted": 0,
    "imageUrl": "http://localhost:8080/api/v1/job-posts/1/images/1",
    "jobPost": "http://localhost:8080/api/v1/job-posts/1",
    "jobType": {
      "description": "Plumbing",
      "id": 0
    },
    "price": 160.0,
    "rateType": {
      "description": "HOURLY",
      "id": 0
    },
    "reviewsCount": 0,
    "title": "Plomería de excelente calidad",
    "zones": [
      {
        "description": "Retiro",
        "id": 28
      }
    ]
  }
]
```

## review

### POST: {{baseUrl}}/reviews/

```json
{
  "description": "EL MEJOR NIÑERO",
  "jobContractId": 29,
  "rate": 5,
  "title": "No debes moverte de donde estas ⛹⛹⛹⛹⛹⛹"
}
```

### GET: {{baseUrl}}/reviews/1

```json
{
  "client": "http://localhost:8080/api/v1/users/3",
  "creationDate": "2021-05-02T18:22:21.684413",
  "description": "EL MEJOR NIÑERO",
  "jobContract": "http://localhost:8080/api/v1/contracts/1",
  "rate": 5,
  "title": "No debes moverte de donde estas ⛹⛹⛹⛹⛹⛹"
}
```

### GET all: {{baseUrl}}/reviews

### GET by postId: {{baseUrl}}/reviews?page=1&postId=5

### GET by userId: {{baseUrl}}/reviews?page=1&userId=5&role=professional

```json
[
  {
    "client": "http://localhost:8080/api/v1/users/3",
    "creationDate": "2021-05-02T18:22:21.684413",
    "description": "EL MEJOR NIÑERO",
    "jobContract": "http://localhost:8080/api/v1/contracts/1",
    "rate": 5,
    "title": "No debes moverte de donde estas ⛹⛹⛹⛹⛹⛹"
  }
]
```

## user

### POST: {{baseUrl}}/users

```json
{
  "phone": "03034560123",
  "username": "Manuel Rodriguezaa",
  "password": "123123123",
  "email": "masdasdasdasdasd@itba.edu.ar"
}
```

### PUT: {{baseUrl}}/users/5

```json
{
  "email": "manaaasd@gmail.com",
  "phone": "03034560123",
  "username": "Manuel Rodrigueaaaaa",
  "password": "1232312323"
}
```

### GET: {{baseUrl}}/users

```json
[
  {
    "contracts": "http://localhost:8080/api/v1/contracts?userId=13",
    "email": "amigoitba@yopmail.com",
    "id": 13,
    "phone": "1234467567",
    "roles": ["CLIENT"],
    "username": "amigo"
  }
]
```

### GET by id: {{baseUrl}}/users/5

### GET by email: {{baseUrl}}/users?email=manaaasd@gmail.com

```json
{
  "contracts": "http://localhost:8080/api/v1/contracts?userId=5",
  "email": "manaaasd@gmail.com",
  "id": 5,
  "image": "http://localhost:8080/api/v1/users/5/image",
  "phone": "03034560123",
  "roles": ["CLIENT", "PROFESSIONAL"],
  "username": "Manuel Rodrigueaaaaa"
}
```

### GET rates: {{baseUrl}}/professionals/5/reviews-by-exact-rate

```json
{
  "five": 2,
  "four": 0,
  "one": 1,
  "three": 0,
  "two": 0
}
```

### GET rankings: {{baseUrl}}/users/5/rankings

```json
[
  {
    "jobType": {
      "description": "Babysitting",
      "id": 7
    },
    "ranking": 1
  }
]
```

### GET professional-info: {{baseUrl}}/users/11/professional-info

```json
{
  "contractsCompleted": 1,
  "reviewAvg": 0.0,
  "reviewsQuantity": 0
}
```

### GET image: {{baseUrl}}/users/5/image

Retorna imagen como body

### PUT image: {{baseUrl}}/users/5/image

En un form con key "file" viajara la imagen

### POST verify email: {{baseUrl}}/users/5/verify

En un form con key "token" viajara el token

### POST recover account: {{baseUrl}}/users/recover-account

```json
{
  "email": "manaaasd@gmail.com"
}
```

### POST recover password: {{baseUrl}}/recover-account/change-password

```json
{
  "email": "manaaasd@gmail.com",
  "token": "TOKEN_VALIDO",
  "password": "12345678"
}
```

## contract (Mis servicios)

### POST: {{baseUrl}}/contracts

```json
{
  "clientId": 1,
  "description": "Necesito que cuides a 4 nenes de 4 años",
  "jobPackageId": 13,
  "scheduledDate": "2021-06-16T14:25:28.028799"
}
```

### PUT: {{baseUrl}}/contracts/40?role=professional

```json
{
  "newScheduledDate": "2021-06-16T14:20:20.028799",
  "newState": 2
}
```

### GET: {{baseUrl}}/contracts/40

```json
{
  "clientId": 1,
  "creationDate": "2021-07-16T16:24:36.055",
  "description": "Necesito que cuides a 4 nenes de 4 años",
  "id": 40,
  "image": "http://localhost:8080/api/v1/contracts/40/image",
  "jobPackage": "http://localhost:8080/api/v1/job-posts/13/packages/13",
  "lastModifiedDate": "2021-07-16T16:35:40.292",
  "scheduledDate": "2021-06-16T14:20:20.028799",
  "state": {
    "description": "CLIENT_REJECTED",
    "id": 2
  }
}
```

### GET contractCards: {{baseUrl}}/contracts?userId=5&role=professional&state=finalized

```json
[
  {
    "avgRate": 3.6666666666666665,
    "client": "http://localhost:8080/api/v1/users/3",
    "contractsCompleted": 5,
    "creationDate": "2021-07-15T21:50:22.663",
    "imageUrl": "http://localhost:8080/api/v1/job-posts/8/images/12",
    "jobContractDto": "http://localhost:8080/api/v1/contracts/34",
    "jobPackageDto": "http://localhost:8080/api/v1/job-posts/8/packages/8",
    "jobPost": "http://localhost:8080/api/v1/job-posts/8",
    "jobTitle": "Niñero turno mañana",
    "jobType": {
      "description": "Babysitting",
      "id": 7
    },
    "packageTitle": "4 dias a la semana 4 horas",
    "professional": "http://localhost:8080/api/v1/users/5",
    "rateType": {
      "description": "TBD",
      "id": 2
    },
    "reviewsCount": 3,
    "scheduledDate": "2021-06-16T14:25:28.028799",
    "state": {
      "description": "CLIENT_REJECTED",
      "id": 2
    }
  }
]
```

### GET image: {{baseUrl}}/contracts/3/image

Retorna imagen como body

### PUT image: {{baseUrl}}/contracts/3/image

En un form con key "file" viajara la imagen

## categories

### GET: {{baseUrl}}/categories

```json
[
  {
    "description": "Plumbing",
    "id": 0
  },
  {
    "description": "Electricity",
    "id": 1
  },
  {
    "description": "Carpentry",
    "id": 2
  },
  {
    "description": "Catering",
    "id": 3
  },
  {
    "description": "Painting",
    "id": 4
  },
  {
    "description": "Teaching",
    "id": 5
  },
  {
    "description": "Cleaning",
    "id": 6
  },
  {
    "description": "Babysitting",
    "id": 7
  }
]
```
