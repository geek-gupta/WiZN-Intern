const storage = require('./storage');

function createBook(req, res) {
  storage.writeToFile(req.body, function(result) {
    res.send(result);
  });
}

function fetchBooks(req, res) {
  storage.fetchFromFile(function(result) {
    res.send(result);
  });
}

module.exports = {
  createBook,
  fetchBooks
}
