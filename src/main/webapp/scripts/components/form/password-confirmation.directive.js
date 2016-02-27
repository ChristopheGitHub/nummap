'use strict';

angular.module('nummapApp')
    .directive('nmPasswordConfirmation', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/password-confirmation.partial.html',
            scope: { passwordText: '=ngModel'}
        };
    });