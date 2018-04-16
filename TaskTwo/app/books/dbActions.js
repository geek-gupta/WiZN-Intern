let mydb = "";
var sort = {
  price: 1
};

function init(client) {
  mydb = client.db('booksdb');
}

function fetchBooks(done) {
  let books = mydb.collection('books');
  books.find().toArray(function(err, result) {
    if (err) throw err;
    console.log(result);
    done(result);
  });
}

function fetchSortedBooks(done) {
  let books = mydb.collection('books');
  books.find().sort(sort).toArray(function(err, result) {
    if (err) throw err;
    console.log(result);
    done(result);
  })
}

function createBook(bookData, done) {
  let books = mydb.collection('books');
  console.log(bookData);
  books.insertOne(bookData, function(err, result) {
    if (err) throw err;
    console.log(result);
    done(result);
  })
}


module.exports = {
  fetchBooks,
  createBook,
  init,
  fetchSortedBooks
}
