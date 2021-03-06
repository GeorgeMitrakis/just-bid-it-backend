# REST API 
(περιγραφή)

Συμβάσεις:

| Συμβολισμός | Σημασία |
| --- | --- |
| field: payload | το field είναι το όνομα του πεδίου του συνολικού JSon Object που επιστρέφεται με το payload ως τιμή
| ∈ { ... }  | για να δείξουμε το πεδίο τιμών μιας παραμέτρου |
| [ ... ] ή [ ... : συνθήκη ]  |  για να δηλώσουμε προαιρετικές παραμέτρους ή παραμέτρους υπό συνθήκη |
| Dates | Dates should be in RFC Format yyyy-MM-dd |

-Η έννοια του "αντικειμένου" (item) είναι ταυτόσημη με αυτήν της "δημοπρασίας".

# REST API End-Points





#### /login
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| POST   | username, password | { result:{ value: { user information fields } }, messagesReceived(int) }  |  ο χρήστης εισέρχεται στο σύστημα   |


#### /signup
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| POST   | username, password, password1, firstname, lastname,email, phone_number, country, location, tax_registration_number | { result:{id, role, access, username, firstname, lastname,email, phoneNumber, country, location, taxRegistrationNumber, sellerRating, bidderRating}, messagesReceived(int) }  |  ο χρήστης κάνει αίτηση εγγραφής στο σύστημα   |

#### Signup Custom Status Codes
| code | meaning |
| ------ | ----- |
| 461 | passwords don't match |
| 462 | username taken |
| 463 | email is already in use |
| 464 | phone number is already in use |
| 465 | tax number is already in use |

#### /admin/users
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | - | users:[ { firstname, lastname, email, phoneNumber, country, location, taxRegistrationNumber, sellerRating, bidderRating, id, username, role, access} , ...] |  ο διαχειριστής παίρνει τους χρήστες του συστήματος  |


#### /admin/users/{username} (not implemented)
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ο διαχειριστής βλέπει τις πληροφορίες του χρήστη {username}   |

#### /admin/users/{username}/accept
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| PUT    | - | user:{id, username,role,access}  |  ο διαχειριστής εγγρίνει στην αίτηση εγγραφής του χρήστη {username}   |

#### /admin/users/{username}/decline
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| PUT    | - | user:{id, username,role,access}  |  ο διαχειριστής απορρίπτει στην αίτηση εγγραφής του χρήστη {username}   |

#### /admin/download/json
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | - | items:[]  |  ο διαχειριστής παίρνει όλα τα Items σε JSON   |

#### /admin/download/xml
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | - | ???  |  ο διαχειριστής παίρνει όλα τα Items σε XML   |


#### /items
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | userId | {total, start, count, results:[{id, userId, name, running, bids:[{id, amount, bidder, bidderId, bidderRating, itemId, time }], categories:[{category: "Shoes"},{ category:...}, ...], buyPrice, firstBid, currentBid, numberOfBids, location, country, start, end, description, latitude, longitude}]}  |  ο χρήστης παίρνει τα αντικείμενά του   |
| POST   | userId, name, categories:[{category: "Shoes"},{ category:...}, ...], buy_price, first_bid, location, country, end, description, [latitude, longitude] | item:{id, selledId, name, categories:["Shoes", "Watches",...], running, buyPrice, firstBid, currentBid, numberOfBids, location:{name, latitude, longitude}, country, start, end, description}  |  ο χρήστης δημιουργεί μια νεα δημοπρασία αντικειμένου   |


#### /items/{id}
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ο χρήστης παίρνει το αντικείμενο #{id} μαζί με όλες τις προσφορές στη δημοπρασία του   |
| PUT    | userId, name, categories:[{category: "Shoes"},{ category:...}, ...], buy_price, first_bid, location, country, end, description, [latitude, longitude] | item:{id, selledId, name, categories:["Shoes", "Watches",...], running, buyPrice, firstBid, currentBid, numberOfBids, location:{name, latitude, longitude}, country, start, end, description}  |  ΠΡΕΠΕΙ ΤΟ ΑΝΤΙΚΕΙΜΕΝΟ ΝΑ ΑΝΗΚΕΙ ΣΤΟΝ ΧΡΗΣΤΗ! Ο χρήστης επεξεργάζεται τις πληροφορίες της δημοπρασίας #{id} |
| DELETE | - | item:{id, selledId, name, categories:["Shoes", "Watches",...], running, buyPrice, firstBid, currentBid, numberOfBids, location:{name, latitude, longitude}, country, start, end, description}  |  ΠΡΕΠΕΙ ΤΟ ΑΝΤΙΚΕΙΜΕΝΟ ΝΑ ΑΝΗΚΕΙ ΣΤΟΝ ΧΡΗΣΤΗ! Ο χρήστης διαγράφει τη δημοπρασία #{id}   |


#### /items/{id}/bid
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| POST   | bidder_id, amount | {item:{},bid:{}}  |  ο χρήστης υποβάλλει προσφορά στη δημοπρασία   |


#### /items/{id}/buy
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| POST   | bidder_id | {item:{},bid:{}}  |  ο χρήστης αγοράζει το αντικείμενο, κερδίζοντας τη δημοπρασία   |


#### /search
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | [term, category, location, price], page_number, page_size | start, count, total, items:[]  |  αναζήτηση ενεργών δημοπρασιών   |

#### /categories
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | category | categories:[]  |  αναζήτηση κατηγοριών με το input string ως πρόθεμα   |

#### /locations
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | location | locations:[]  |  αναζήτηση τοποθεσιών αντικειμένων με το input string ως πρόθεμα   |

#### /bids (not implemented)
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {page number, page size, +++} | {status:won/running/lost, +++ ...}  |  ο χρήστης παίρνει τα αντικείμενα στα οποία έχει υποβάλλει προσφορές   |


#### /messages (not implemented)
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ο χρήστης παίρνει όλα τα μηνύματα που έχει λάβει ή στείλει   |


#### /messages/sent
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | userId | {total, messages : [{id, sender, receiver, text}, ...]}  |  ο χρήστης παίρνει όλα τα μηνύματα που έχει στείλει   |


#### /messages/received
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | userId | {total, messages : [{id, sender, receiver, text}, ...]}    |  ο χρήστης παίρνει όλα τα μηνύματα που έχει λάβει   |


#### /messages/{id} (not implemented)
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ΤΟ ΜΗΝΥΜΑ ΠΡΕΠΕΙ ΝΑ ΑΝΗΚΕΙ ΣΤΟΝ ΧΡΗΣΤΗ! ο χρήστης παίρνει το μήνυμα {id}   |


#### /messages/{username} (not implemented)
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ο χρήστης παίρνει όλα τα μηνύματα μεταξύ αυτού και του χρήστη {username}   |

#### /messages/{username}/send
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| POST   | userId, text | message:{id, sender, receiver, text}  |  ο χρήστης στέλνει μήνυμα στον χρήστη {username}   |

#### /messages/{id}/delete
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| DELETE | - | message:{id, sender_id, receiver_id, text}  |  ο χρήστης διαγράφει το μήνυμα #{id}   |

#### /usernames
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | username | usernames:[]  |  ο χρήστης παίρνει όλα τα usernames που αρχίζουν με την παράμετρο που δίνει    |
