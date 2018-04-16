const storage = require('./dbActions');


function fetchBooks(req, res) {
  storage.fetchBooks(function(result) {
    res.send(result);
  });
}

function fetchSortedBooks(req, res) {
  storage.fetchSortedBooks(function(result) {
    res.send(result);
  })
}


function createBook(req, res) {
  storage.createBook(req.body, function(result) {
    res.send(result);
  });
}



module.exports = {
  createBook,
  fetchBooks,
  fetchSortedBooks
}
