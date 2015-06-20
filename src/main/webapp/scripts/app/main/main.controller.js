'use strict';

angular.module('nummapApp')
    .controller('MainController', function ($scope, $state, Principal, $http, $filter, leafletMarkersHelpers) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        // Icons
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

        /* Etat de la zone de recherche */
        $scope.isCollapsed = true;
        $scope.isCollapsedCat = true;
        $scope.isCollapsedField = true;
        

        /* Pour la recherche */
        $scope.categories = [
            {value: 'ALL', translationKey: 'register.form.category.all', checked: 'true'},
            {value: 'STUDENT', translationKey: 'register.form.category.student', checked: 'false', icon: local_icons.STUDENT.iconMenuUrl},
            {value: 'PROFESSOR', translationKey: 'register.form.category.professor', checked: 'false', icon: local_icons.PROFESSOR.iconMenuUrl},
            {value: 'FREELANCE', translationKey: 'register.form.category.freelance', checked: 'false', icon: local_icons.FREELANCE.iconMenuUrl},
            {value: 'COMPANY', translationKey: 'register.form.category.company', checked: 'false', icon: local_icons.COMPANY.iconMenuUrl},
            {value: 'ASSOCIATION', translationKey: 'register.form.category.association', checked: 'false', icon: local_icons.ASSOCIATION.iconMenuUrl}
        ];

        $scope.fields = [
            {name: 'All', value: 'ALL', checked: 'true'},
            {name: 'Outsourcing', value: 'OUTSOURCING', checked: 'false'},
            {name: 'Consulting', value: 'CONSULTING', checked: 'false'},
            {name: 'System Integration', value: 'SYSTEM_INTEGRATION', checked: 'false'}
        ];

        $scope.choosenCategories = ['ALL', 'STUDENT', 'PROFESSOR', 'FREELANCE', 'COMPANY', 'ASSOCIATION'];
        $scope.choosenFields = ['ALL', 'OUTSOURCING', 'CONSULTING', 'SYSTEM_INTEGRATION'];

        $scope.selectCategory = function(index) {
            if (index === 0) {
                $scope.choosenCategories = [];
                $scope.categories.forEach(function (element) {
                    element.checked = 'false';
                    // On sélectionne toutes les catégories
                    $scope.choosenCategories.push(element.value);
                });
                $scope.categories[0].checked = 'true';    
            } else {
                // On déselectionne 'ALL'
                $scope.categories[0].checked = 'false';
                if ($scope.choosenCategories.indexOf('ALL') !== -1){
                    $scope.choosenCategories = [];
                }

                // Si selectionné, on le déselectionne et on le retire des choosenCategories
                if ($scope.categories[index].checked === 'true') {
                    $scope.categories[index].checked = 'false';
                    $scope.choosenCategories.splice($scope.choosenCategories.indexOf($scope.categories[index].value),1);
                }
                // Sinon, on le selectionne et on le push dans les choosenCategories
                else {
                    console.log('ici');
                    $scope.categories[index].checked = 'true';
                    $scope.choosenCategories.push($scope.categories[index].value);
                }
            }
            console.log($scope.choosenCategories);
        };

        $scope.selectField = function(index) {
            if (index === 0) {
                $scope.choosenFields = [];
                $scope.fields.forEach(function (element) {
                    element.checked = 'false';
                    // On sélectionne toutes les catégories
                    $scope.choosenFields.push(element.value);
                });
                $scope.fields[0].checked = 'true';    
            } else {
                // On déselectionne 'ALL'
                $scope.fields[0].checked = 'false';
                if ($scope.choosenFields.indexOf('ALL') !== -1){
                    $scope.choosenFields = [];
                }

                // Si selectionné, on le déselectionne et on le retire des choosenFields
                if ($scope.fields[index].checked === 'true') {
                    $scope.fields[index].checked = 'false';
                    $scope.choosenFields.splice($scope.choosenFields.indexOf($scope.fields[index].value),1);
                }
                // Sinon, on le selectionne et on le push dans les choosenFields
                else {
                    console.log('ici');
                    $scope.fields[index].checked = 'true';
                    $scope.choosenFields.push($scope.fields[index].value);
                }
            }
            console.log($scope.choosenFields);
        };

        /* Position de la "camera" sur la carte */
        $scope.center = {
            lat: 43.3811,
            lng: -0.6345,
            zoom: 10
        };

        /* Déclaration des tableaux */
        $scope.markers = [];
        $scope.markersFiltered = [];

        /* Fonction permettant de set les icons */
        $scope.setIcons = function(){
            $scope.markers.forEach(function(element){
                element.group = 'numlab';
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

        /* Fonction permettant d'ajouter à tous les  markers la propriété detail, qui permettra d'afficher des details sur le marker */
        $scope.setBoolDetail = function(){
            $scope.markers.forEach(function(element){
                element.detail = false;
            });
        };

        $scope.$on('leafletDirectiveMarker.click', function(e, args) {
            if (typeof  $scope.markersFiltered[args.markerName] !== 'undefined') {
                $scope.markersFiltered[args.markerName].detail = true;
                document.getElementById($scope.markersFiltered[args.markerName].name).scrollIntoView( true );
            }
        });

        $scope.$on('leafletDirectiveMarker.popupclose', function(e, args) {
            if (typeof  $scope.markersFiltered[args.markerName] !== 'undefined') {
                $scope.markersFiltered[args.markerName].detail = false;
            }
        });

        /* Centrage de la carte sur le marker sur lequel on a cliqué dans le menu */
        $scope.goToMarker = function(marker){
            console.log(marker);
           $scope.center.lat = marker.lat;
           $scope.center.lng = marker.lng;
           $scope.center.zoom = 35;
          // marker.focus = true;
        };

        /* Récupération de l'ensemble des markers au chargement de la page */
        $scope.loadAll = function() {
            $http.get('api/markers', {})
                .success(function(data){
                    $scope.markers = data;
                    /* Ajout des incones */
                    $scope.setIcons();
                    /* Ajout du boolean details permettant l'affichage en détails de l'element */
                    $scope.setBoolDetail();
                    $scope.markersFiltered = $scope.markers;
                });
        };
        $scope.loadAll();

        /* Icones pour l'affichage des markers */
        

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
            tiles: tilesDict.openstreetmap,
            markers: $scope.markersFiltered,
            defaults: {
                scrollWheelZoom: true,
                zoomControlPosition: 'bottomleft'
            }
        });
        
        $scope.$watch('markerFilter', function(newText) {
            change(newText, $scope.choosenCategories, $scope.choosenFields);
        }, true);

        $scope.$watch('choosenCategories', function(newText) {
            change($scope.markerFilter, newText, $scope.choosenFields);
        }, true);

        $scope.$watch('choosenFields', function(newText) {
            change($scope.markerFilter, $scope.choosenCategories, newText);
        }, true);

        /**
         * Trigger a search from click on people's field, sector or competency
         * @param  {String} competency
         * @param  {String} sector
         * @param  {String} field
         */
        $scope.search = function(competency, sector, field) {
            if (field) {
                console.log('on y passe : ' + 'field');
                $scope.isCollapsed = false;
                // Needed for the animation
                setTimeout(function() {
                    console.log('On y est ; 2');
                    $scope.isCollapsedField = false;
                }, 100);
                $scope.choosenFields = [];
                $scope.choosenFields.push(field);
                change($scope.markerFilter, $scope.choosenCategories, field);
            }
        };

        var change = function (text, categories, fields) {
            $scope.markersFiltered1 = $filter('filter')($scope.markers, text);
            $scope.markersFiltered2 = $filter('categories')($scope.markersFiltered1, categories);
            $scope.markersFiltered = $filter('fields')($scope.markersFiltered2, fields);
            $scope.markers.forEach(function (element) {
                element.group = "totalité";
                element.detail = false;
            });
            $scope.markersFiltered.forEach(function (element) {
                element.group = "triés";
            });
        };

        $scope.button = function () {
            console.log('e');
            change($scope.markerFilter, $scope.choosenCategories, $scope.choosenFields);
        };

        $scope.$on('$destroy', function () {
            leafletMarkersHelpers.resetCurrentGroups();
        });

    });
