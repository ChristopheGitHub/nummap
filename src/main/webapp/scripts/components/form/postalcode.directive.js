'use strict';

angular.module('nummapApp')
    .directive('nmPostalcode', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/postalcode.partial.html',
            scope: { postalcodeText: '=ngModel'}
        };
    });