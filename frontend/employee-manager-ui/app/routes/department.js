import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';
import ENV from 'employee-manager-ui/config/environment';

export default class DepartmentRoute extends Route {
    @service storage;
    async model(params){
        const {id} = params;
        let response = await fetch(`${ENV.apiUrl}/department/${id}`);
        let parsed = await response.json();
        this.storage.setDepartmentData(parsed.name, parsed.id);
        return parsed;
    }
}
