'use strict';

angular.module('nummapApp')
    .factory('Competencies', function ($resource) {
        return $resource('api/competencies/:name', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });

