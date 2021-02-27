import Model, { attr } from '@ember-data/model';

export default class EmployeeModel extends Model {
    @attr ('number') id
    @attr ('string') firstName
    @attr ('string') lastName
    @attr ('string') patronymicName
    @attr ('string') position
    @attr ('string') phoneNumber
    @attr ('string') emailAddress
    @attr ('number') age
    @attr ('number') salary
    @attr ('string') photo

    get fullName() {
        return `${this.firstName} ${this.lastName} ${this.patronymicName}`;
      }
}
