import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';

export default class NewdepartmentRoute extends Route {
    @service storage;
    model(){
        let department = {id: this.storage.departmentData.id, 
                            name:this.storage.departmentData.name};
        return department;
    }
}
