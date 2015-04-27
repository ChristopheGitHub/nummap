'use strict';

angular.module('nummapApp')
	.controller('SchoolDetailController', function ($scope, $modalInstance, title, School) {

		$scope.title = title;

		console.log($scope.title);

		$scope.save = function () {
			console.log($scope.school);
			School.save($scope.school);
		}
	})