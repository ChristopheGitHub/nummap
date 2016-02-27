'use strict';

angular.module('nummapApp')
    .directive('nmAssociationNumber', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/association-number.partial.html',
            scope: { associationNumber: '=ngModel'}
        };
    });