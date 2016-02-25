'use strict';

angular.module('nummapApp')
    .directive('nmSchool', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/school.partial.html',
            scope: { schoolText: '=ngModel' },
            controller: function ($scope, School) {
                School.query(function(result) {
                    $scope.schools = result;
                });
            }
        };
    });