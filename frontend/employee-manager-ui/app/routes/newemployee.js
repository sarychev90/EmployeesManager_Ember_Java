import Route from '@ember/routing/route';

export default class NewemployeeRoute extends Route {
    model(params){
        const {departmentId} = params;
        let newEmployee = {firstName:"", lastName:"", patronymicName:"", position:"",
            phoneNumber:"", emailAddress:"", age:"", salary:0.0, photo:"", departmentId};
        return newEmployee;
    }
}
