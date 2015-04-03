/**
 * Created by eisti on 3/17/15.
 */
/**
 * Created by eisti on 2/18/15.
 */
'use strict';

angular.module('nummapApp')
    .controller('UserDetailController', function ($scope, $stateParams, User, Domains, Competencies, Auth) {
        $scope.user = {};
        // $scope.user = {};
        $scope.load = function (login) {
            User.get({login: login}, function(result) {
                $scope.user = result;
                console.log($scope.user);

                Domains.query(function(result) {
	                $scope.sectors = result;
	                console.log('sectors');
	                console.log($scope.sectors);
	                
	                // Valeurs déjà choisies
	                $scope.sectors.forEach(function (element) {
	                    if ($.inArray(element.name, $scope.user.sectors) !== -1) {
	                        element.checked = true;
	                    }
	                });
	            });

	            Competencies.query(function(result) {
	                $scope.competencies = result;
	            });

	            $scope.competenciesSelected = [];
	            $scope.user.competencies.forEach( function (element) {
	                $scope.competenciesSelected.push(element);
	            });

		        $scope.fields = [
		            {name: 'Outsourcing', value: 'OUTSOURCING'},
		            {name: 'Consulting', value: 'CONSULTING'},
		            {name: 'System Integration', value: 'SYSTEM_INTEGRATION'}
		        ];

	            $scope.fields.forEach(function (element) {
	                if ($.inArray(element.value, $scope.user.fields) !== -1) {
	                    element.checked = true;
	                } else {
	                    element.checked = false;
	                }
	                console.log(element.name +' : ' + element.checked);
	            });

	            // Récupération des réseaux sociaux;
	            $scope.personSocialNetworkList = [];
	            $scope.companySocialNetworkList = [];
	            if ($scope.user.category === 'STUDENT' ||
	                $scope.user.category === 'PROFESSOR' ||
	                $scope.user.category === 'FREELANCE') {
	                $scope.personSocialNetworkList = $scope.user.personContactInformation.socialNetworkList;
	                if ($scope.personSocialNetworkList === null) {
	                    $scope.personSocialNetworkList = [];
	                }
	            }

	            if ($scope.user.category === 'COMPANY' ||
	                $scope.user.category === 'ASSOCIATION') {
	                $scope.companySocialNetworkList = $scope.user.companyContactInformation.socialNetworkList;
	                if ($scope.companySocialNetworkList === null) {
	                    $scope.companySocialNetworkList = []; 
	                }
	            }


            });
        };

        $scope.load($stateParams.login);

        $scope.personSocialNetworkList = [];
        $scope.companySocialNetworkList = [];

        $scope.addElement = function(list) {
            list.push({});
            console.log(list);
        };

        $scope.categories = [
            {value: 'STUDENT', translationKey: 'register.form.category.student'},
            {value: 'PROFESSOR', translationKey: 'register.form.category.professor'},
            {value: 'FREELANCE', translationKey: 'register.form.category.freelance'},
            {value: 'COMPANY', translationKey: 'register.form.category.company'},
            {value: 'ASSOCIATION', translationKey: 'register.form.category.association'}
        ];

        $scope.save = function () {
	        
	        // Ajout des réseaux sociaux
	        if ($scope.user.category === 'STUDENT' ||
	            $scope.user.category === 'PROFESSOR' ||
	            $scope.user.category === 'FREELANCE') {
	            $scope.user.personContactInformation.socialNetworkList = $scope.personSocialNetworkList;
	        }

	        if ($scope.user.category === 'COMPANY' ||
	            $scope.user.category === 'ASSOCIATION') {
	            $scope.user.companyContactInformation.socialNetworkList = $scope.companySocialNetworkList;
	        }

            Auth.manageAccount($scope.user).then(function() {
                $scope.error = null;
                $scope.success = 'OK';
                $scope.loadAll();
                $('#saveUserModal').modal('hide');
                $scope.clear();
            }).catch(function() {
                $scope.success = null;
                $scope.error = 'ERROR';
            });
        };


    });