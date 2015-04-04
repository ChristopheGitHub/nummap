'use strict';

angular.module('nummapApp')
    .controller('MainController', function ($scope, $state, Principal, $http) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.markers = [];

        /* Fonction permettant de set les icons */
        $scope.setIcons = function(){
            $scope.markers.forEach(function(element){
            console.log(element.message);
                if(element.category == "STUDENT"){
                  element.icon = local_icons.STUDENT;
                }
                else if(element.category == "FREELANCE"){
                  element.icon = local_icons.FREELANCE;
                }
                else if(element.category == "PROFESSOR"){
                  element.icon = local_icons.PROFESSOR;
                }
                else if(element.category == "COMPANY"){
                  element.icon = local_icons.COMPANY;
                }
                else if(element.category == " ASSOCIATION"){
                    element.icon = local_icons.COMPANY;
                }

            });
        }

        /* Récupération de l'ensemble des markers */
        $scope.loadAll = function() {
            $http.get('api/markers', {})
                .success(function(data){
                    $scope.markers = data;
                    $scope.success = 'OK';
                    $scope.setIcons();
                });
        };
        $scope.loadAll();



        /* Icones pour l'affichage des markers */
        var local_icons = {
            default_icon: {},
            STUDENT: {
                iconUrl: 'scripts/app/images/etudiant-icon.png',
                iconAnchor:   [32, 32] // point of the icon which will correspond to marker's location
            },
            PROFESSOR: {
                iconUrl: 'scripts/app/images/prof-icon.png',
                iconAnchor:   [32, 32] // point of the icon which will correspond to marker's location
            },
            COMPANY:{
                iconUrl: 'scripts/app/images/entierprise-icon.png',
                iconSize:     [32, 32]
            },
            FREELANCE:{
                iconUrl: 'scripts/app/images/freelance.png',
                iconSize:     [32, 32]
            }
        };



        var tilesDict = {
            openstreetmap: {
                url: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
                options: {
                    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                }
            }
        };


        angular.extend($scope, {
            center: {
                lat: 43.28520334369384,
                lng: -0.289764404296875,
                zoom: 9
            },
            markers:
              $scope.markers
            ,
            tiles: tilesDict.openstreetmap,
            defaults: {
                scrollWheelZoom: true,
                zoomControlPosition: 'bottomleft'
            }
        });




    });
