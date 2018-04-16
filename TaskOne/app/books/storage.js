const fileSystem = require('fs');
const filePath = "./data/books.json";
var books = [];

fileSystem.readFile(filePath, function(err, data) {
  if (err) throw err;
  if(data.length > 0){
    try{
      var fileData = JSON.parse(data);
      books = fileData;
    } catch (e){console.log(e);}
  }
});

function fetchFromFile(done) {
  done(books);
}


function writeToFile(book, done) {
  books.push(book);
  fileSystem.writeFile(filePath, JSON.stringify(books), function(err) {
    if (err) throw err;
    done(books);
  });
}

module.exports = {
  fetchFromFile,
  writeToFile
}
