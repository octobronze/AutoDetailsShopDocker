export class RegisterInfo{
    username: string;
    email: string;
    status: string;
    sex: string;
    firstName: string;
    lastName: string;
    role: string;
    password: string;

    constructor(username: string, password: string, email: string, firstName: string, lastName: string, sex: string){
                    this.username = username;
                    this.firstName = firstName;
                    this.lastName = lastName;
                    this.role = 'USER';
                    this.password = password;
                    this.email = email;
                    this.sex = sex;
                    this.status = 'Suspended';
                }
}