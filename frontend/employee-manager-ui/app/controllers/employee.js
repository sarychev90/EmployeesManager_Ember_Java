import Controller from '@ember/controller';
import {action} from '@ember/object';
import { tracked } from '@glimmer/tracking';
import { inject as service } from '@ember/service';
import ENV from 'employee-manager-ui/config/environment';

export default class EmployeeController extends Controller {
  
    @tracked updateMode = false;
    @tracked deleteMode = false;
    @service storage;

    @action
    changeUpdateMode(){
        this.updateMode = !this.updateMode;
    }

    @action
    async updateEmployeeData(id, firstName, lastName, patronymicName, position,
        phoneNumber, emailAddress, age, salary, photo){
        
    const preview = document.querySelector('img');

    let employee = {id, firstName, lastName, patronymicName, position,
        phoneNumber, emailAddress, age, salary, photo};
              
    employee.photo = preview.src; 
    
    let response = await fetch(`${ENV.apiUrl}/employee`, {
        method: 'PUT',
        headers: {'Content-Type':'application/json;charset=utf-8'},
        body: JSON.stringify(employee)
        });
    
    console.log(response.status);
    this.updateMode = !this.updateMode;
    }

    @action
    deleteEmployeeDataDialogOpen(){
        this.deleteMode = true;
    }

    @action
    deleteEmployeeDataDialogClose(){
        this.deleteMode = false;
    }

    @action
    async deleteEmployeeData(id){
    let response = await fetch(`${ENV.apiUrl}/employee/${id}`, {
        method: 'DELETE'
        });
    console.log(response.status);
    this.deleteMode = false;
    this.transitionToRoute(`/department/${this.storage.departmentData.id}`);
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
