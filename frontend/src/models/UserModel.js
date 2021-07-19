export default class UserModel {

  constructor(data) {
    this.id = data.id;
    this.username = data.username;
    this.email = data.email;
    this.phone = data.phone;
    this.roles = data.roles;
  }
}