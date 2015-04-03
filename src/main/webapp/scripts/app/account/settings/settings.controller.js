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
            });


            $scope.competenciesSelected = [];
            $scope.settingsAccount.competencies.forEach( function (element) {
                $scope.competenciesSelected.push(element);
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

            // Récupération des réseaux sociaux;
            $scope.personSocialNetworkList = [];
            $scope.companySocialNetworkList = [];
            if ($scope.settingsAccount.category === 'STUDENT' ||
                $scope.settingsAccount.category === 'PROFESSOR' ||
                $scope.settingsAccount.category === 'FREELANCE') {
                $scope.personSocialNetworkList = $scope.settingsAccount.personContactInformation.socialNetworkList;
                if ($scope.personSocialNetworkList === null) {
                    $scope.personSocialNetworkList = [];
                }
            }

            if ($scope.settingsAccount.category === 'COMPANY' ||
                $scope.settingsAccount.category === 'ASSOCIATION') {
                $scope.companySocialNetworkList = $scope.settingsAccount.companyContactInformation.socialNetworkList;
                if ($scope.companySocialNetworkList === null) {
                    $scope.companySocialNetworkList = []; 
                }
            }
        });

        $scope.personSocialNetworkList = [];
        $scope.companySocialNetworkList = [];
        
        $scope.categories = [
            {value: 'STUDENT', translationKey: 'register.form.category.student'},
            {value: 'PROFESSOR', translationKey: 'register.form.category.professor'},
            {value: 'FREELANCE', translationKey: 'register.form.category.freelance'},
            {value: 'COMPANY', translationKey: 'register.form.category.company'},
            {value: 'ASSOCIATION', translationKey: 'register.form.category.association'}
        ];

        console.log($scope.settingsAccount);

        $scope.addElement = function(list) {
            list.push({});
            console.log(list);
        };

        $scope.loadTags = function(query) {
            var res = [];
            $scope.competencies.forEach(function(element) {
                if (element.name.substr(0, query.length) === query) {
                    res.push(element);
                }
            });
            return res;
        };

        $scope.save = function () {

            // Ajout des réseaux sociaux
            if ($scope.settingsAccount.category === 'STUDENT' ||
                $scope.settingsAccount.category === 'PROFESSOR' ||
                $scope.settingsAccount.category === 'FREELANCE') {
                $scope.settingsAccount.personContactInformation.socialNetworkList = $scope.personSocialNetworkList;
            }

            if ($scope.settingsAccount.category === 'COMPANY' ||
                $scope.settingsAccount.category === 'ASSOCIATION') {
                $scope.settingsAccount.companyContactInformation.socialNetworkList = $scope.companySocialNetworkList;
            }




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
