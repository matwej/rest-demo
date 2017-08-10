app.controller("TodosCtrl", [ '$scope', 'todoservice',
		function($scope, todoservice) {
			todoservice.getTodos($scope);

			$scope.deleteTodo = function(uid) {
				todoservice.deleteTodo($scope, uid);
				$scope.todos = $scope.todos.filter(function(o) {
					return o.uid !== uid
				});
			};
		} ]);

app.controller("ManageTodoCtrl", [
		'$scope',
		'$state',
		'$stateParams',
		'todoservice',
		function($scope, $state, $stateParams, todoservice) {
			var action = $state.current.name == 'new_todo' ? 'create'
					: 'update';
			if ($state.current.name == 'edit_todo') {
				todoservice.getTodo($stateParams.id, $scope);
			}
			$scope.submitForm = function(isValid) {
				if (isValid) {
					if (action.startsWith('create')) {
						todoservice.createTodo($scope);
					} else {
						todoservice.updateTodo($scope);
					}
					$state.go('todos');
				}
			};
			$scope.has_error = function(name) {
				return $scope.todoForm[name].$invalid && $scope.todoForm[name].$dirty ? 'has-error' : 'has-success';
			}
		} ]);

app.controller("ShowTodoCtrl", [ '$scope', 'todoservice', '$stateParams',
		function($scope, todoservice, $stateParams) {
			todoservice.getTodo($stateParams.id, $scope);
			console.log($scope);
		} ]);