README for NumMap
==========================

What is it ?
------------
NumMap est un annuaire cartographié des entreprises des TICs dans les régions Béarn et Bigorre
.

Contribution
------------
* **Database :** MongoDB 2.6.9 was used for the development of the project.
Necessary settings :
```bash
# In a MongoDB shell :
use nummapdev
db.createUser({ user: "nummapdev",
  pwd: "nummapdev",
  roles: [
    { role: "readWrite", db: "nummapdev" }
  ]
})
```


* **Java ** was used for the develoment of the project.
* **Node.js, Bower, Maven** are required to contribute on the project.

To install and launch the project :
```bash
bower update
npm install
mvn spring-boot:run
```


Links
-----

* Jhipster project : [https://jhipster.github.io/](https://jhipster.github.io/)
* AngularJS documentation : [https://docs.angularjs.org/api](https://docs.angularjs.org/api)
* Leaflet : [http://leafletjs.com/](http://leafletjs.com/)
* Angular Leaflet : [https://github.com/tombatossals/angular-leaflet-directive](https://github.com/tombatossals/angular-leaflet-directive)

