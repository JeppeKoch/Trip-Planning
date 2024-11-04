https://github.com/JeppeKoch/Trip-Planning

### Output from endpoints

#### GET /api/trips/
```json
{
  "id": 1,
  "starttime": [
    14,
    0
  ],
  "endtime": [
    16,
    0
  ],
  "startposition": "Mountain Base",
  "name": "Mountain Exploration",
  "price": 89.99,
  "category": "BEACH"
},
{
"id": 2,
"starttime": [
9,
0
],
"endtime": [
11,
0
],
"startposition": "City Center",
"name": "Morning City Tour",
"price": 49.99,
"category": "CITY"
},
{
"id": 3,
"starttime": [
13,
0
],
"endtime": [
15,
0
],
"startposition": "Coastal Bay",
"name": "Beach Relaxation",
"price": 79.99,
"category": "BEACH"
},
{
"id": 4,
"starttime": [
10,
0
],
"endtime": [
12,
0
],
"startposition": "Historic Quarter",
"name": "Historic Sites Tour",
"price": 59.99,
"category": "LAKE"
}
```

#### GET /api/trips/1/
```json

{
  "id": 1,
  "starttime": [
    9,
    0
  ],
  "endtime": [
    11,
    0
  ],
  "startposition": "updated City Center2",
  "name": "updated Morning City Tour2",
  "price": 49.99,
  "category": "CITY",
  "guide_id": 1,
  "guide": {
    "id": 1,
    "firstname": "Alice Johnson",
    "lastname": "Alice",
    "email": "alice.johnson@example.com",
    "phone": "123456789",
    "years_of_experience": 7.0
  }
}

```


#### POST /api/trips/
```json
{
  "id": 1,
  "starttime": [
    14,
    0
  ],
  "endtime": [
    16,
    0
  ],
  "startposition": "Mountain Base",
  "name": "Mountain Exploration",
  "price": 89.99,
  "category": "BEACH",
  "guide_id": 1
}
```

#### PUT /api/trips/1/
```json
{
  "HTTP/1.1 200 OK",
  "Date: Mon, 04 Nov 2024 10:12:47 GMT",
  "Content-Type: text/plain",
  "Content-Length: 0"


}
```

#### DELETE /api/trips/1/
```json
{
  "HTTP/1.1 204 No Content",
  "Date: Mon, 04 Nov 2024 10:12:47 GMT",
  "Content-Type: text/plain",
  "Content-Length: 0"
}
```

### PUT /api/trips/7/guides/1
```json
{
  HTTP/1.1 200 OK
  Date: Mon, 04 Nov 2024 10:26:01 GMT
  Content-Type: text/plain
  Content-Length: 0
}
```

#### Why do we suggest a PUT method for adding a guide to a trip instead of a POST method? 
Både guiden og turen eksisterer allerede, så vi vil ikke persiste en ny, men bare tilknytte den til turen.

### Get /api/category/BEACH
```json
{
  "id": 3,
  "starttime": [
    13,
    0
  ],
  "endtime": [
    15,
    0
  ],
  "startposition": "Coastal Bay",
  "name": "Beach Relaxation",
  "price": 79.99,
  "category": "BEACH",
  "guide_id": 2
}
```

### GET /trips/guide/totalPrice/1
```json
{
  HTTP/1.1 200 OK
  Date: Mon, 04 Nov 2024 10:47:21 GMT
  Content-Type: application/json
  Content-Length: 6

{
  "totalPrice": 199.96
}
}
```

