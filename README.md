# Spring Boot Assignment for SCB

## Bookstore	RESTful	API

Develop	a	RESTful	API	for	a	bookstore that allows	a	user	to	login,	perform	user	related	tasks,	view	a	list	of	
books	and	place	book	orders.
You	should develop	the	API using	Java	Spring Boot and	commit	your	work,	setup	instructions and	any	cURL scripts	to	a	remote	git	repository	(github,	gitlab,	bitbucket,	etc).	

We	expect	that	you	demonstrate	the	following	in	your	solution:

* applied	SOLID	principles
* error	and	exception	handling
* security
* database	design
* atomic	commits	to	repository

You	will	have	our	extra attention if	you	also	provide the	following:

* unit	tests
* performance optimization
* sequence	diagrams
* API	documentation	(Swagger/RAML)

Should	there	be	any	missing	requirements	in	this	document,	you	may	exercise	your	own	discretion.
Please	send	an email	to	michael.tan@scb.co.th with	a	link	to	the	repository	once	you	have	completed	the	
assignment.
Below	are	the	APIs	that	you	would	need	to	implement:

## Book	Store	API

#### POST:	/login
        
This	is	the	user	login	authentication API.	The	request	and	response	should	be	over	a	secured	communication.
        
_Request:_
        
    {"username":"john.doe",	"password":	"thisismysecret"}
        
_Response:_
        
    200 OK
    {"token": "7888ef93-7854-4fcd-b8ba-b30a1a2078fb", "result_code": "success" }


#### GET:	/users

(Login required)	Gets information	about the	logged	in	user.	A	successfully	authenticated	request	returns	information	related	to	the	user	and	the	books	ordered.
        
_Request:_

    HEADER: x-token: 7888ef93-7854-4fcd-b8ba-b30a1a2078fb

_Response:_

    200 OK
    {
    "name":	"john",
    "surname":	"doe",
    "date_of_birth":	"15/01/1985",
    "books":	[1,	4]
    }


#### DELETE:	/users

(Login required)	Delete	logged	in	user’s record	and	order	history.

_Request:_
    
    HEADER: x-token: 7888ef93-7854-4fcd-b8ba-b30a1a2078fb

_Response:_

    200 OK


#### POST:	/users
(Login	not required)	Create	a	user account	and	store	user’s	information in	Users table	(DB).

_Request:_

    {"username": "john.doe", "password": "thisismysecret",	"date_of_birth":	"15/01/1985"}

_Response:_

    200 OK

#### POST:	/users/orders
(Login	required) Order	books	and	store	order	information	in	Orders	table	(DB).	This	returns	the	price	for	a	    successful	order.

_Request:_

    HEADER: x-token: 7888ef93-7854-4fcd-b8ba-b30a1a2078fb
    {"orders":	[1,	4]}

_Response:_

    200 OK
    {"price":	519.00}


#### GET:	/books
(Login	not	required)	Gets	a	list	of books from	an external	book	publisher’s	web	services	and	returns	the	
list	sorted	alphabetically with the	recommended books	always	appears first.	The	should	be no	
duplicated books in	the	list.
_Request:_

_Response:_

    200	OK
    {
    "books":	[
    {"id":	5,	"name":"An	American	Princess:	The	Many	Lives	of	Allene	Tew",	"author":"Annejet	van	der	Zijl,	
    Michele	Hutchison",	"price":149.00,	"is_recommended":	true},
    {"id":	4,	"name":"The	Great	Alone:	A	Novel	Kristin	Hannah",	"author":"Kristin	Hannah",	"price":495.00,	
    "is_recommended":	true},
    {"id":	2,	"name":"When	Never	Comes",	"author":"Barbara	Davis",	"price":179.00,	"is_recommended":	
    true},
    {"id":	1,	"name":"Before	We	Were	Yours:	A	Novel",	"author":"Lisa	Wingate",	"price":340.00,	
    "is_recommended":	false},
    {"id":	3,	"name":"Giraffes	Can't	Dance",	"author":"Giles	Andreae,	Guy	Parker-Rees",	"price":200.50,	
    "is_recommended":false},
    ...
    ]}

## External	Book	Publisher	Web	Service
Here	are	the	APIs	provided	by	the	book	publisher	that	you	may	consume.	The	service	may	occasionally	be	
slow	depending	on	the	usage	traffic	it	receives:

*GET:	https://scb-test-book-publisher.herokuapp.com/books* Shows a	list	of	books	from	the	publisher	(normal	+	recommended).	This	list	is	updated	on	a	weekly	basis	
(every	Sunday	at	midnight).

*GET:	https://scb-test-book-publisher.herokuapp.com/books/recommendation* Shows a	list	of	recommended	books	from	the	publisher.

## Running 

Prerequisite for the project

* Docker
* JDK 8

#### Steps

1. Running _Mongo DB_

        docker run -d --rm -p 27017:27017 mongo

2. Running _Spring boot_

        ./mvnw spring-boot:run

3. (Optional) Running _Test_

        ./mvnw test


#### REMARK

*SWAGGER URL* : http://localhost