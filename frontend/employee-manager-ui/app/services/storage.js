import Service from '@ember/service';
import { tracked } from '@glimmer/tracking';
import {action} from '@ember/object';


export default class StorageService extends Service {

    @tracked department = {name: "", id: 0};
    @tracked employeeName = "";

    get departmentData(){
        return this.department;
    }

    get employeeName(){
        return this.employeeName;
    }

    @action
    setDepartmentData(name, id){
        this.department.name = name;
        this.department.id = id;
    }

    @action
    setEmployeeData(employeeName){
        this.employeeName = employeeName;
    }
}
