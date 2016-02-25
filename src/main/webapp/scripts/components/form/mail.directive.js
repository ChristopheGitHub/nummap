'use strict';

angular.module('nummapApp')
    .directive('nmMail', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/mail.partial.html',
            scope: { mailText: '=ngModel'}
        };
    });