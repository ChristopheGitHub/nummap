'use strict';

angular.module('nummapApp')
    .directive('nmLastname', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/lastname.partial.html',
            scope: { lastnameText: '=ngModel' }
        };
    });