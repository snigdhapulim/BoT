const mongoose = require("mongoose");

const UserSchema = new mongoose.Schema({
  name: {
    type: String
  },
  email: {
    type: String,
    required: true,
  },
  calenderID: {
    type: String,
    required: true,
  },
  homeAddress: {
    type: String
  },
  friends: {
    name: {
        type: String
    },
    email: {
        type: String
    },
  }
});

const User = mongoose.model("User", UserSchema);

module.exports = User;