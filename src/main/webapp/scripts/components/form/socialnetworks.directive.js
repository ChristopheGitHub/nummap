'use strict';

angular.module('nummapApp')
    .directive('nmSocialNetworks', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/socialnetworks.partial.html',
            scope: { socialNetworkList: '=ngModel'},
            controller: function($scope) {
                $scope.addElement = function(list) {
                    list.push({});
                    console.log(list);
                };
            }
        };
    });