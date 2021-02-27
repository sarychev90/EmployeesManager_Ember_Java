import EmberRouter from '@ember/routing/router';
import config from 'employee-manager-ui/config/environment';

export default class Router extends EmberRouter {
  location = config.locationType;
  rootURL = config.rootURL;
}

Router.map(function () {
  this.route('not-found', {path: '/*path'});
  this.route('department', {path: '/department/:id'});
  this.route('employee', {path: '/employee/:id'});
  this.route('departments');
  this.route('newemployee', {path: '/newemployee/:departmentId'});
  this.route('departmentdata');
});
