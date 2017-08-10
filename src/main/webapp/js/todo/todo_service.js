app.factory('todoservice', ['$resource', function($resource){
	return new Todo($resource);
}]);

function Todo(resource) {
	this.resource = resource;
	
	this.createTodo = function(scope) {
		var Todo = resource(api_prefix + '/todos');
		Todo.save(scope.todo, function(response){
			scope.message = response.uid;
		});
	}
	
	this.getTodos = function(scope) {
		var Todos = resource(api_prefix + '/todos');
		Todos.query(function(todos){
			scope.todos = todos;
		});
	}
	
	this.deleteTodo = function(scope, uid) {
		var Todo = resource(api_prefix + '/todos/' + uid);
		Todo.delete(function(){
			scope.message = "Item deleted";
		});
	}
	
	this.updateTodo = function(scope) {
		var todo = scope.todo;
		var Todo = resource(api_prefix + '/todos/' + todo.uid);
		Todo.save(todo);
	}
	
	this.getTodo = function(uid, scope) {
		var Todo = resource(api_prefix + '/todos/' + uid);
		Todo.get(function(todo){
			scope.todo = todo;
			scope.is_done = todo.done ? 'yes' : 'no';
		});
	}
}