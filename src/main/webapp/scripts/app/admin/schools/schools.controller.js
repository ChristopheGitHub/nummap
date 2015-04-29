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

		$scope.open = function (goal, school, readonly) {
			var readonly = (readonly) ? 'true' : 'false';

            console.log('readonly is ' + readonly);


			var modalSchool = $modal.open({
				templateUrl: 'scripts/app/admin/schools/school-detail.html',
				controller: 'SchoolDetailController',
				size: 'lg',
				resolve: {
					goal: function () {
						return goal;
					},
					school: function () {
						return school;
					},
					readonly: function () {

					}
				}
			})
		};

		$scope.delete = function (schoolName) {
			School.delete({name: schoolName}, function () {
				$scope.load();
			});
		};
		



	});