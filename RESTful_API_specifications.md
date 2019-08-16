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
| POST   | username, password | result:{ value: { user information fields } }  |  ο χρήστης εισέρχεται στο σύστημα   |


#### /signup
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| POST   | username, password, password1, first_name, last_name,email, phone_number, country, location, tax_registration_number | result:{id, role, access, username, firstName, lastName,email, phoneNumber, country, location, taxRegistrationNumber, sellerRating, bidderRating}  |  ο χρήστης κάνει αίτηση εγγραφής στο σύστημα   |


#### /admin/users
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {page number, page size, +++} | {...}  |  ο διαχειριστής παίρνει τους χρήστες του συστήματος  |


#### /admin/users/{username}
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ο διαχειριστής βλέπει τις πληροφορίες του χρήστη {username}   |
| PUT    | {...} | {...}  |  ο διαχειριστής απαντάει στην αίτηση εγγραφής του χρήστη {username}   |


#### /items
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ο χρήστης παίρνει τα αντικείμενά του   |
| POST   | {...} | {...}  |  ο χρήστης δημιουργεί μια νεα δημοπρασία αντικειμένου   |


#### /items/{id}
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ο χρήστης παίρνει το αντικείμενο #{id} μαζί με όλες τις προσφορές στη δημοπρασία του   |
| PUT    | {...} | {...}  |  ΠΡΕΠΕΙ ΤΟ ΑΝΤΙΚΕΙΜΕΝΟ ΝΑ ΑΝΗΚΕΙ ΣΤΟΝ ΧΡΗΣΤΗ! Ο χρήστης επεξεργάζεται τις πληροφορίες της δημοπρασίας #{id} |
| DELETE | {...} | {...}  |  ΠΡΕΠΕΙ ΤΟ ΑΝΤΙΚΕΙΜΕΝΟ ΝΑ ΑΝΗΚΕΙ ΣΤΟΝ ΧΡΗΣΤΗ! Ο χρήστης διαγράφει τη δημοπρασία #{id}   |


#### /items/{id}/bid
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| POST   | {...} | {...}  |  ο χρήστης υποβάλλει προσφορά στη δημοπρασία   |


#### /items/{id}/buy
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| POST   | {...} | {...}  |  ο χρήστης αγοράζει το αντικείμενο, κερδίζοντας τη δημοπρασία   |


#### /search
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {page number, page size, +++} | {...}  |  αναζήτηση ενεργών δημοπρασιών   |


#### /bids
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {page number, page size, +++} | {status:won/running/lost, +++ ...}  |  ο χρήστης παίρνει τα αντικείμενα στα οποία έχει υποβάλλει προσφορές   |


#### /messages
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ο χρήστης παίρνει όλα τα μηνύματα που έχει λάβει ή στείλει   |


#### /messages/sent
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ο χρήστης παίρνει όλα τα μηνύματα που έχει στείλει   |


#### /messages/received
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ο χρήστης παίρνει όλα τα μηνύματα που έχει λάβει   |


#### /messages/{id}
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ΤΟ ΜΗΝΥΜΑ ΠΡΕΠΕΙ ΝΑ ΑΝΗΚΕΙ ΣΤΟΝ ΧΡΗΣΤΗ! ο χρήστης παίρνει το μήνυμα {id}   |


#### /messages/{username}
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | {...} | {...}  |  ο χρήστης παίρνει όλα τα μηνύματα μεταξύ αυτού και του χρήστη {username}   |

#### /messages/{username}/send
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| POST   | {...} | {...}  |  ο χρήστης στέλνει μήνυμα στον χρήστη {username}   |
