import Route from '@ember/routing/route';
import ENV from 'employee-manager-ui/config/environment';

export default class EmployeeRoute extends Route {
    async model(params){
        const {id} = params;
        let response = await fetch(`${ENV.apiUrl}/employee/${id}`);
        let parsed = await response.json();
        return parsed;
    }

    setupController(controller, model){
        super.setupController(controller, model);
        const updateMode = false;
        controller.set('updateMode', updateMode);
    }
}
