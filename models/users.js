const mongoose = require("mongoose");

const UserSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true
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
    type: String,
    required: false,
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