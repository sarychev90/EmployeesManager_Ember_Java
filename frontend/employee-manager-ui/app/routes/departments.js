import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';
import ENV from 'employee-manager-ui/config/environment';

export default class DepartmentsRoute extends Route {
    @service storage;
    async model(){
        this.storage.setDepartmentData("", 0)
        let response = await fetch(`${ENV.apiUrl}/departments`);
        let parsed = await response.json();
        return parsed;
    }
}
