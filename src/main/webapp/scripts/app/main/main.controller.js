'use strict';

angular.module('nummapApp')
    .controller('MainController', function ($scope, $state, Principal, $http, $filter) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        /* Position de la "camera" sur la carte */
        $scope.center = {
            lat: 43.28520334369384,
            lng: -0.289764404296875,
            zoom: 9
        }

        /* Déclaration des tableaux */
        $scope.markers = [];
        $scope.markersFiltered = [];

        /* Fonction permettant de set les icons */
        $scope.setIcons = function(){
            $scope.markers.forEach(function(element){
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
                else if(element.category == "ASSOCIATION"){
                    element.icon = local_icons.ASSOCIATION;
                }

            });
        };

        /* Centrage de la carte sur le marker sur lequel on a cliqué dans le menu */
        $scope.goToMarker = function(marker){
           console.log(marker);
           $scope.center.lat = marker.lat;
           $scope.center.lng = marker.lng;
           $scope.center.zoom = 35
           marker.focus = true;
        };

        /* Récupération de l'ensemble des markers au chargement de la page */
        $scope.loadAll = function() {
            $http.get('api/markers', {})
                .success(function(data){
                    $scope.markers = data;
                    $scope.success = 'OK';
                    $scope.setIcons();
                    console.log("Taille markers : "+$scope.markers.length);
                    /* Tant qu'aucun filtre n'a été appliqué les markersfiltered sont égaux à l'ensenble des markers */
                    $scope.markersFiltered = $scope.markers;
                });
        };
        $scope.loadAll();



        /* Icones pour l'affichage des markers */
        var local_icons = {
            default_icon: {},
            STUDENT: {
                iconUrl: 'scripts/app/images/university.png',
                iconMenuUrl : 'scripts/app/images/universityMenu.png',
                iconAnchor:   [0, 0], // point of the icon which will correspond to marker's location
                popupAnchor:  [15, 5]
            },
            PROFESSOR: {
                iconUrl: 'scripts/app/images/highschool.png',
                iconMenuUrl : 'scripts/app/images/highschoolMenu.png',
                iconAnchor:   [0, 0], // point of the icon which will correspond to marker's location
                popupAnchor:  [15, 5]
            },
            COMPANY:{
                iconUrl: 'scripts/app/images/office-building.png',
                iconMenuUrl : 'scripts/app/images/office-buildingMenu.png',
                iconAnchor:     [0, 0],
                popupAnchor:  [15, 5]
            },
            FREELANCE:{
                iconUrl: 'scripts/app/images/workoffice.png',
                iconMenuUrl : 'scripts/app/images/workofficeMenu.png',
                iconAnchor:     [0, 0],
                popupAnchor:  [15, 5]
            },
           ASSOCIATION:{
                iconUrl: 'scripts/app/images/museum_science.png',
               iconMenuUrl : 'scripts/app/images/museum_scienceMenu.png',
                iconAnchor:     [0, 0],
               popupAnchor:  [15, 5]
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
            center: $scope.center,
           markers:  $scope.markers,
            tiles: tilesDict.openstreetmap,
            defaults: {
                scrollWheelZoom: true,
                zoomControlPosition: 'bottomleft'
            }
        });

        $scope.$watch("markerFilter", function(newText, oldText) {
            $scope.markersFiltered = $filter('filter')($scope.markers, newText);
        },true);





    });
