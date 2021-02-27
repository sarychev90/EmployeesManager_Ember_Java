import Controller from '@ember/controller';
import {action} from '@ember/object';
import ENV from 'employee-manager-ui/config/environment';

export default class NewemployeeController extends Controller {

    @action
    async createEmployee(firstName, lastName, patronymicName, position,
        phoneNumber, emailAddress, age, salary, photo, departmentId){

    const preview = document.querySelector('img');

    let employee = {firstName, lastName, patronymicName, position,
        phoneNumber, emailAddress, age, salary, photo};
    
    employee.photo = preview.src;
    
    let response = await fetch(`${ENV.apiUrl}/employee/${departmentId}`, {
        method: 'POST',
        headers: {'Content-Type':'application/json;charset=utf-8'},
        body: JSON.stringify(employee)
        });
    
    console.log(response.status);
    this.transitionToRoute(`/department/${departmentId}`);
    }

    @action
    cancelCreateEmployee(departmentId){
    this.transitionToRoute(`/department/${departmentId}`);
    }

    @action
    uploadPhoto() {
        const preview = document.querySelector('img');
        const file = document.querySelector('input[type=file]').files[0];
        const reader = new FileReader();
      
        reader.addEventListener("load", () => {
          preview.src = reader.result;
        }, false);
      
        if (file) {
          reader.readAsDataURL(file);
        } 
    }
}
