import Controller from '@ember/controller';
import {action} from '@ember/object';
import { inject as service } from '@ember/service';
import ENV from 'employee-manager-ui/config/environment';

export default class NewdepartmentController extends Controller {
    
    @service storage;
    
    @action
    async createDepartment(name){

    let department = {name};
    
    let response = await fetch(`${ENV.apiUrl}/department`, {
        method: 'POST',
        headers: {'Content-Type':'application/json;charset=utf-8'},
        body: JSON.stringify(department)
        });
    
    console.log(response.status);
    this.transitionToRoute('departments');
    }

    @action
        cancelCreate(){
        this.transitionToRoute('departments');  
    }

    @action
        cancelUpdate(departmentId){
        this.transitionToRoute(`/department/${departmentId}`);  
    }

    @action
    async updateDepartmentData(id, name){
        
    let department = {id, name};
              
    let response = await fetch(`${ENV.apiUrl}/department`, {
        method: 'PUT',
        headers: {'Content-Type':'application/json;charset=utf-8'},
        body: JSON.stringify(department)
        });

    console.log(response.status);
    this.storage.setDepartmentData(name, id);
    this.transitionToRoute(`/department/${id}`);  
    }
}
