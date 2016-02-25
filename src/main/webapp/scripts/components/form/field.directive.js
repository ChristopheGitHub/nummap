'use strict';

angular.module('nummapApp')
    .directive('nmField', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/field.partial.html',
            scope: { fieldArray: '=ngModel' },
            controller: function ($scope) {
                $scope.fieldArray = [
                    {name: 'Outsourcing', value: 'OUTSOURCING'},
                    {name: 'Consulting', value: 'CONSULTING'},
                    {name: 'System Integration', value: 'SYSTEM_INTEGRATION'}
                ];
            }
        };
    });
