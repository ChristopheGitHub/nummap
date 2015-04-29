'use strict';

angular.module('nummapApp')
	.controller('SchoolDetailController', function ($scope, $modalInstance, goal, school, School) {

		$scope.goal = goal;

		switch ($scope.goal) {
			case 'create':
				$scope.title = 'modal.title.creation';
				$scope.readonly = 'false';
			break;
			
			case 'view':
				$scope.title = 'modal.title.view';
				$scope.readonly = 'true';
				$scope.school = school;
			break;
			
			case 'edit':
				$scope.title = 'modal.title.edit';
				$scope.readonly = 'false';
				$scope.school = school;
			break;
		}

		$scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        }

        $scope.ok = function () {
        	$modalInstance.dismiss('ok');
        }

		$scope.save = function () {
			console.log($scope.school);
			School.save($scope.school, function () {
				$modalInstance.close();				
			});
		};

		$scope.update = function () {
			School.save($scope.school);
		};
	})