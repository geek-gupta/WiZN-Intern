const express = require('express');
const app = express();
const mongodb = require('mongodb');
const mongoURL = "mongodb://localhost:27017/";
const MongoClient = mongodb.MongoClient;
const bodyParser = require('body-parser');
const booksActions = require('./app/books/');
const booksStorage = require('./app/books/dbActions');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true
}));

app.get('/books', booksActions.fetchBooks);
app.get('/books/sort', booksActions.fetchSortedBooks);

app.post('/books/create', booksActions.createBook);

app.use('/', booksActions.fetchBooks);


MongoClient.connect(mongoURL, function(err, client) {
  if (err) throw err;
  booksStorage.init(client);
  app.listen(4000, function() {
    console.log("Server Started at 4000");
  })
})
