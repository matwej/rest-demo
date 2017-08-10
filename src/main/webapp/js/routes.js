app.config(['$stateProvider','$urlRouterProvider','$locationProvider',function($stateProvider, $urlRouteProvider, $locationProvider) {
	$stateProvider.state('root', {
		url: '/',
		controller : 'HomeCtrl',
		templateUrl: 'views/home.html'
	}).state('home', {
		url: '/home',
		controller : 'HomeCtrl',
		templateUrl: 'views/home.html'
	}).state('new_todo', {
		url: '/todos/new',
		templateUrl : 'views/todo/new.html'
	}).state('edit_todo', {
		url: '/todos/edit/:id',
		templateUrl : 'views/todo/edit.html'
	}).state('show_todo', {
		url: '/todos/:id',
		controller : 'ShowTodoCtrl',
		templateUrl : 'views/todo/show.html'	
	}).state('todos', {
		url: '/todos',
		controller : 'TodosCtrl',
		templateUrl : 'views/todo/list.html'	
	});
	
// $urlRouteProvider.otherwise('home');
	
	$locationProvider.html5Mode(true);
}]);