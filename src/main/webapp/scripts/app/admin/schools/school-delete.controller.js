'use strict';

angular.module('nummapApp')
	.controller('SchoolDeleteController', function ($scope, $modalInstance, schoolName) {

		$scope.name = schoolName;

		$scope.cancel = function () {
			$modalInstance.dismiss('cancel');
		};

		$scope.validate = function () {
			$modalInstance.close(true);
		};
	})