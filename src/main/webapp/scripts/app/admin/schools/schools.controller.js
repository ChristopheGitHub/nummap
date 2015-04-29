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

		$scope.open = function (goal, school) {

			var modalSchoolInstance = $modal.open({
				templateUrl: 'scripts/app/admin/schools/school-detail.html',
				controller: 'SchoolDetailController',
				size: 'lg',
				resolve: {
					goal: function () {
						return goal;
					},
					school: function () {
						return school;
					}
				}
			})
		};

		$scope.delete = function (schoolName) {
			var modalDeleteSchoolInstance = $modal.open({
				templateUrl: 'scripts/app/admin/schools/school-delete.html',
				controller: 'SchoolDeleteController',
				size: 'sm',
				resolve: {
					schoolName: function () {
						return schoolName;
					}
				}
			})

			modalDeleteSchoolInstance.result.then(function (deleteSchool) {
				if (deleteSchool) {
					School.delete({name: schoolName}, function () {
						$scope.load();
					});
				}	
			});
		};
		



	});