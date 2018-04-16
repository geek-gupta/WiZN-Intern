'use strict'

const express = require('express');
const app = express();
const bodyparser = require('body-parser');
const booksActions = require('./app/books');

app.use(bodyparser.json());
app.use(bodyparser.urlencoded({
  extended: true
}));

app.post('/books/create', booksActions.createBook);

app.get('/books', booksActions.fetchBooks);

app.use('/', booksActions.fetchBooks);

app.listen(5000, function() {
  console.log("Server started at 5000");
});
