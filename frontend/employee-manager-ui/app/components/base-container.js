import Component from '@glimmer/component';
import { inject as service } from '@ember/service';

export default class BaseContainer extends Component {
    @service storage;

    get getDepartmentId(){
        return this.storage.departmentData.id;
    }

    get getDepartmentName(){
        return this.storage.departmentData.name;
    }

    get getEmployeeName(){
        return this.storage.employeeName;
    }
}