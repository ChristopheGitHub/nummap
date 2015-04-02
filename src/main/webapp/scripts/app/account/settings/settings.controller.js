'use strict';

angular.module('nummapApp')
    .controller('SettingsController', function ($scope, Principal, Auth, Domains, Competencies) {
        $scope.success = null;
        $scope.error = null;
        Principal.identity().then(function(account) {
            $scope.settingsAccount = account;
            console.log('settings');
            console.log($scope.settingsAccount);
            
            Domains.query(function(result) {
                $scope.sectors = result;
                console.log('sectors');
                console.log($scope.sectors);
                
                // Valeurs déjà choisies
                $scope.sectors.forEach(function (element) {
                    if ($.inArray(element.name, $scope.settingsAccount.sectors) !== -1) {
                        element.checked = true;
                    }
                });
            });
        
            Competencies.query(function(result) {
                $scope.competencies = result;
                // $scope.competencies
            });

            $scope.fields = [
                {name: 'Outsourcing', value: 'OUTSOURCING'},
                {name: 'Consulting', value: 'CONSULTING'},
                {name: 'System Integration', value: 'SYSTEM_INTEGRATION'}
            ];

            $scope.fields.forEach(function (element) {
                if ($.inArray(element.value, $scope.settingsAccount.fields) !== -1) {
                    element.checked = true;
                } else {
                    element.checked = false;
                }
                console.log(element.name +' : ' + element.checked);
            });
        });


        
        $scope.categories = [
            {value: 'STUDENT', translationKey: 'register.form.category.student'},
            {value: 'PROFESSOR', translationKey: 'register.form.category.professor'},
            {value: 'FREELANCE', translationKey: 'register.form.category.freelance'},
            {value: 'COMPANY', translationKey: 'register.form.category.company'},
            {value: 'ASSOCIATION', translationKey: 'register.form.category.association'}
        ];

        



        console.log($scope.settingsAccount);



        $scope.save = function () {
            Auth.updateAccount($scope.settingsAccount).then(function() {
                $scope.error = null;
                $scope.success = 'OK';
                Principal.identity().then(function(account) {
                    $scope.settingsAccount = account;
                });
            }).catch(function() {
                $scope.success = null;
                $scope.error = 'ERROR';
            });
        };
    });
