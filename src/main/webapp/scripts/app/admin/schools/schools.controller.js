'use strict';

angular.module('nummapApp')
	.controller('SchoolsController', function ($scope, School, $modal) {

		$scope.schools = [];

		$scope.querySchool = "";

		$scope.load = function() {
			School.query(function (result) {
				$scope.schools = result;
				console.log($scope.schools);
			});
		};

		$scope.load();

		$scope.create = function () {
			var modalSchool = $modal.open({
				templateUrl: 'scripts/app/admin/schools/school-detail.html',
				controller: 'SchoolDetailController',
				size: 'lg',
				resolve: {
					title: function () {
						return "modal.title.creation";
					}
				}
			});
		};



	});