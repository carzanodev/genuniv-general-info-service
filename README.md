# 1. Overview
The general-info service consists of data that are used as common information across all services, and should be accessed only by users with the highest rights. These data include:
1. School Year - the school year of which a data may belong to (e.g. Offering) 
2. School Period - the period in the school year of which a data may belong to (e.g. Offering)
3. Schedule - the university's allowed enrolment schedules.

This service uses [genuniv-common-service](https://github.com/carzanodev/genuniv-common-service) as its chassis.

![general-info](./.assets/genuniv-general-info-service.png)

# 2. Access Endpoints
## 2.1. School Year 
Get all school years by using a GET request on `/api/v1/school-year`. \
`curl -X GET http://localhost:18000/api/v1/school-year`

Get a specific school year by using a GET request with ID on path on `/api/v1/school-year/{id}`. \
`curl -X GET http://localhost:18000/api/v1/school-year/1`

Add a school year by using a POST request containing data on `/api/v1/school-year`. \
`curl -H 'Content-Type: application/json' -d @data.json -X POST http://localhost:18000/api/v1/school-year`
```json
{
	"name" : "sample school year 123"
}
```

Update a school year by using a POST request containing data with ID on path on `/api/v1/school-year/{id}`. \
`curl -H 'Content-Type: application/json' -d @data.json -X POST http://localhost:18000/api/v1/school-year/1`
```json
{
	"name" : "sample school year 123"
}
```

Delete a specific school year by using a GET request with ID on path on `/api/v1/school-year/{id}`. \
`curl -X DELETE http://localhost:18000/api/v1/school-year/1`

## 2.2. School Period 
Get all school periods by using a GET request on `/api/v1/school-period`. \
`curl -X GET http://localhost:18000/api/v1/school-period`

Get a specific school period by using a GET request with ID on path on `/api/v1/school-period/{id}`. \
`curl -X GET http://localhost:18000/api/v1/school-period/1`

Add a school period by using a POST request containing data on `/api/v1/school-period`. \
`curl -H 'Content-Type: application/json' -d @data.json -X POST http://localhost:18000/api/v1/school-period`
```json
{
	"name" : "sample school period 123"
}
```

Update a school period by using a POST request containing data with ID on path on `/api/v1/school-period/{id}`. \
`curl -H 'Content-Type: application/json' -d @data.json -X POST http://localhost:18000/api/v1/school-period/1`
```json
{
	"name" : "sample school period 123"
}
```

Delete a specific school period by using a GET request with ID on path on `/api/v1/school-period/{id}`. \
`curl -X DELETE http://localhost:18000/api/v1/school-period/1`

## 2.3. Schedule
Get all schedule by using a GET request on `/api/v1/schedule`. \
`curl -X GET http://localhost:18000/api/v1/schedule`

Get a specific schedule by using a GET request with ID on path on `/api/v1/schedule/{id}`. \
`curl -X GET http://localhost:18000/api/v1/schedule/1`

Add a schedule by using a POST request containing data on `/api/v1/schedule`. \
`curl -H 'Content-Type: application/json' -d @data.json -X POST http://localhost:18000/api/v1/schedule`
```json
{
	"name" : "sample schedule 123"
}
```

Update a schedule by using a POST request containing data with ID on path on `/api/v1/schedule/{id}`. \
`curl -H 'Content-Type: application/json' -d @data.json -X POST http://localhost:18000/api/v1/schedule/1`
```json
{
	"name" : "sample schedule 123"
}
```

Delete a specific schedule by using a GET request with ID on path on `/api/v1/schedule/{id}`. \
`curl -X DELETE http://localhost:18000/api/v1/schedule/1`
