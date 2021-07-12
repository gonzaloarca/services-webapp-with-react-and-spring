# Modelos - DTOs

## Tabla de Contenidos
- [Modelos - DTOs](#modelos---dtos)
  - [Tabla de Contenidos](#tabla-de-contenidos)
  - [jobContract](#jobcontract)
  - [jobPost](#jobpost)
  - [jobCard](#jobcard)
  - [review](#review)
  - [professional](#professional)
  - [user](#user)
  - [package](#package)
  - [contract (Mis servicios)](#contract-mis-servicios)
  - [contract (Servicios contratados)](#contract-servicios-contratados)
  - [jobContract](#jobcontract-1)
## jobContract
```json
{
 "clientId": 7,
    "creationDate": "2021-05-02T18:22:13.558460",
    "description": "Arreglar un ca ̃õ <alert>1</alert>",
    "id": 5,
    "jobPackage": {
        "id": 1,
        "uri": "http://localhost:8080/job-posts/1/packages/1"
    },
    "lastModifiedDate": "2021-06-16T14:25:28.028799",
    "scheduledDate": "2021-06-16T14:25:28.028799",
    "state": {
        "description": "COMPLETED",
        "id": 6
    }
}
```

## jobPost
```json
{
    "active": true,
    "availableHours": "Lunes a viernes entre las 8am y 2pm",
    "creationDate": "2021-05-02T18:22:13.338478",
    "id": 8,
    "jobType": {
        "description": "BABYSITTING",
        "id": 7
    },
    "packages": [
        {
            "id": 8,
            "uri": "http://localhost:8080/job-posts/8/packages/8"
        }
    ],
    "professional": {
        "id": 5,
        "uri": "http://localhost:8080/users/5"
    },
    "title": "Niñero turno mañana",
    "zones": [
        {
            "description": "RETIRO",
            "id": 28
        },
        {
            "description": "NUNIEZ",
            "id": 20
        },
        {
            "description": "COLEGIALES",
            "id": 9
        }
    ]
}
```

## jobCard
```json
{
    "avgRate": 4.666666666666667,
    "contractsCompleted": 4,
    "imageUrl": "http://localhost:8080/job-posts/1/images/1",
    "jobPost": {
        "id": 1,
        "uri": "http://localhost:8080/job-posts/1"
    },
    "jobType": {
        "description": "PLUMBING",
        "id": 0
    },
    "price": 160.0,
    "rateType": {
        "description": "HOURLY",
        "id": 0
    },
    "reviewsCount": 3,
    "title": "Plomería de excelente calidad",
    "zones": [
        {
            "description": "RETIRO",
            "id": 28
        },
        {
            "description": "PALERMO",
            "id": 21
        },
        {
            "description": "NUNIEZ",
            "id": 20
        },
        {
            "description": "COLEGIALES",
            "id": 9
        },
        {
            "description": "BELGRANO",
            "id": 4
        }
    ]
}
```

## review
```json
{
    "creationDate": "2021-05-02T18:22:21.684413",
    "description": "EL MEJOR NIÑERO",
    "jobContract": {
        "id": 1,
        "uri": "http://localhost:8080/job-posts/8/packages/8/contracts/1"
    },
    "rate": 5,
    "title": "No debes moverte de donde estas ⛹⛹⛹⛹⛹⛹"
},
```

## professional
```json
{
    "contractsCompleted": 3,
    "reviewAvg": 3.0,
    "reviewsQuantity": 2,
    "user": {
        "id": 5,
        "uri": "http://localhost:8080/users/5"
    }
}
```

## user
```json
{
    "email": "manaaasd@gmail.com",
    "id": 5,
    "phone": "03034560",
    "roles": [
        "CLIENT",
        "PROFESSIONAL"
    ],
    "username": "Manuel Rodriguez"
}
```

## package
> Aclaración: no tiene price porque el rateType es TBD
```json
{
    "active": true,
    "description": "Atencion constante y juego para el desarrollo de musculos como brazos y piernas.",
    "id": 8,
    "jobPost": {
        "id": 8,
        "uri": "http://localhost:8080/job-posts/8"
    },
    "rateType": {
        "description": "TBD",
        "id": 2
    },
    "title": "4 dias a la semana 4 horas"
}
```
## contract (Mis servicios)
```json
{
    "avgRate": 3.6666666666666665,
    "client": {
        "id": 11,
        "uri": "http://localhost:8080/users/11"
    },
    "contractsCompleted": 4,
    "creationDate": "2021-06-16T16:48:40.860",
    "imageUrl": "http://localhost:8080/job-posts/8/images/12",
    "jobContract": {
        "id": 29,
        "uri": "http://localhost:8080/job-posts/8/packages/8/contracts/29"
    },
    "jobPackage": {
        "id": 8,
        "uri": "http://localhost:8080/job-posts/8/packages/8"
    },
    "jobPost": {
        "id": 8,
        "uri": "http://localhost:8080/job-posts/8"
    },
    "jobTitle": "Niñero turno mañana",
    "jobType": {
        "description": "BABYSITTING",
        "id": 7
    },
    "packageTitle": "4 dias a la semana 4 horas",
    "rateType": {
        "description": "TBD",
        "id": 2
    },
    "reviewsCount": 3,
    "scheduledDate": "2021-06-16T16:48",
    "state": {
        "description": "PENDING_APPROVAL",
        "id": 0
    }
}
```

## contract (Servicios contratados)
```json
{
    "avgRate": 0.0,
    "contractsCompleted": 1,
    "creationDate": "2021-05-05T18:41:31.191",
    "imageUrl": "http://localhost:8080/job-posts/5/images/8",
    "jobContract": {
        "id": 12,
        "uri": "http://localhost:8080/job-posts/5/packages/5/contracts/12"
    },
    "jobPackage": {
        "id": 5,
        "uri": "http://localhost:8080/job-posts/5/packages/5"
    },
    "jobPost": {
        "id": 5,
        "uri": "http://localhost:8080/job-posts/5"
    },
    "jobTitle": "Pintor Profesional",
    "jobType": {
        "description": "PAINTING",
        "id": 4
    },
    "packageTitle": "Se pinta cualquier objeto",
    "professional": {
        "id": 3,
        "uri": "http://localhost:8080/users/3"
    },
    "rateType": {
        "description": "TBD",
        "id": 2
    },
    "reviewsCount": 0,
    "scheduledDate": "2021-06-16T14:25:28.028799",
    "state": {
        "description": "COMPLETED",
        "id": 6
    }
}
```

## jobContract
```json
{
 "clientId": 7,
    "creationDate": "2021-05-02T18:22:13.558460",
    "description": "Arreglar un ca ̃õ <alert>1</alert>",
    "id": 5,
    "jobPackage": {
        "id": 1,
        "uri": "http://localhost:8080/job-posts/1/packages/1"
    },
    "lastModifiedDate": "2021-06-16T14:25:28.028799",
    "scheduledDate": "2021-06-16T14:25:28.028799",
    "state": {
        "description": "COMPLETED",
        "id": 6
    }
}
```