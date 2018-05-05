Bartosz Brzozowski REST Api
============================
## Film Object
**Id**

	Long

**Tilte**

	String

**Director**

	String

**PremiereYear**

	Long

## GET
  ### */filmObjects* - get the list of films with all fields
    
  ### */films* - get the list of films only with id and title
  
  ### */film/{id}* - get film with given id
  
  ### */film* - get film with given id as a parameter
  
```
Parameter:
  
  - Name: id
  
  - Data Type: number  
```
  
## POST
  ### */addFilm* - post film to the database
  
```
Header:
  - Content-Type →application/json  
Body:
  - title  String
  - director  String
  - premiereDate  Long
  
  {
	  "title":"MyFilm",
	  "director":"Bartosz Brzozowski",
	  "premiereDate":"2017"
  }
  
  
```
