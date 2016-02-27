'use strict';

angular.module('nummapApp')
    .directive('nmCategories', function () {
        return {
            restrict: 'E',
            require: 'ngModel',
            replace: true,
            templateUrl: 'scripts/components/form/categories.partial.html',
            scope: { categoryChoice: '=ngModel'},
            controller: function($scope) {
                $scope.categories = [
                    {value: 'STUDENT', translationKey: 'register.form.category.student'},
                    {value: 'PROFESSOR', translationKey: 'register.form.category.professor'},
                    {value: 'FREELANCE', translationKey: 'register.form.category.freelance'},
                    {value: 'COMPANY', translationKey: 'register.form.category.company'},
                    {value: 'ASSOCIATION', translationKey: 'register.form.category.association'}
                ];
            }
        };
    });