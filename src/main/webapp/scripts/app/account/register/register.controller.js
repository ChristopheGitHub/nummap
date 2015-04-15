'use strict';

angular.module('nummapApp')
    .controller('RegisterController', function ($scope, $translate, $timeout, Auth, Domains, Competencies) {
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        $timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});

        // Nécessaire pour le formulaire
        $scope.personSocialNetworkList = [];
        $scope.companySocialNetworkList = [];
        $scope.domainsSelected = [];
        $scope.competenciesSelected = [];

        // Récupère la liste de secteurs et de compétences
        Domains.query(function(result) {
            $scope.sectors = result;
            console.log($scope.sectors);
        });
        Competencies.query(function(result) {
            $scope.competencies = result;
            console.log($scope.competencies);
        });

        // Valeurs pour le questionnaire
        $scope.fields = [
            {name: 'Outsourcing', value: 'OUTSOURCING'},
            {name: 'Consulting', value: 'CONSULTING'},
            {name: 'System Integration', value: 'SYSTEM_INTEGRATION'}
        ];
        $scope.categories = [
            {value: 'STUDENT', translationKey: 'register.form.category.student'},
            {value: 'PROFESSOR', translationKey: 'register.form.category.professor'},
            {value: 'FREELANCE', translationKey: 'register.form.category.freelance'},
            {value: 'COMPANY', translationKey: 'register.form.category.company'},
            {value: 'ASSOCIATION', translationKey: 'register.form.category.association'}
        ];
        
        
        $scope.loadTags = function(query) {
            var res = [];
            $scope.competencies.forEach(function(element) {
                if (element.name.substr(0, query.length) === query) {
                    res.push(element);
                }
            });
            return res;
        };

        $scope.addElement = function(list) {
            list.push({});
            console.log(list);
        };

        $scope.register = function () {
            if ($scope.registerAccount.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.registerAccount.langKey = $translate.use();
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;

                // Ajout de la liste des secteurs
                $scope.registerAccount.sectors = [];
                $scope.sectors.forEach(function(element) {
                    if (element.checked) {
                        $scope.registerAccount.sectors.push(element.name);
                    }
                });

                // Ajout de la liste des domaines
                $scope.registerAccount.fields = [];
                $scope.fields.forEach(function(element) {
                    if (element.checked) {
                        $scope.registerAccount.fields.push(element.value);
                    }
                });

                // Ajout des compétences
                $scope.registerAccount.competencies = [];
                $scope.competenciesSelected.forEach(function (element) {
                    $scope.registerAccount.competencies.push(element.name);                 
                });

                // Ajout des réseaux sociaux
                if ($scope.registerAccount.category === 'STUDENT' ||
                    $scope.registerAccount.category === 'PROFESSOR' ||
                    $scope.registerAccount.category === 'FREELANCE') {
                    $scope.registerAccount.PersonContactInformation.socialNetworkList = $scope.personSocialNetworkList;
                }

                if ($scope.registerAccount.category === 'COMPANY' ||
                    $scope.registerAccount.category === 'ASSOCIATION') {
                    $scope.registerAccount.CompanyContactInformation.socialNetworkList = $scope.companySocialNetworkList;
                }

                // console.log('sectors : ');
                // console.log($scope.registerAccount.sectors);
                // console.log('fields : ');
                // console.log($scope.registerAccount.fields);
                // console.log('competencies : ');
                // console.log($scope.registerAccount.competencies);
                // console.log('competenciesSelected : ');
                // console.log($scope.competenciesSelected);

                Auth.createAccount($scope.registerAccount).then(function () {
                    $scope.success = 'OK';
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });
            }
        };
    });
