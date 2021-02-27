import Controller from '@ember/controller';
import {action} from '@ember/object';
import { tracked } from '@glimmer/tracking';
import ENV from 'employee-manager-ui/config/environment';

export default class DepartmentController extends Controller {
    
    @tracked deleteMode = false;

    @action
    async deleteDepartmentData(id){
    let response = await fetch(`${ENV.apiUrl}/department/${id}`, {
        method: 'DELETE'
        });
    console.log(response.status);
    this.deleteMode = false;
    this.transitionToRoute('/departments');
    }

    @action
    deleteDepartmentDataDialogOpen(){
        this.deleteMode = true;
    }

    @action
    deleteDepartmentDataDialogClose(){
        this.deleteMode = false;
    }
}
