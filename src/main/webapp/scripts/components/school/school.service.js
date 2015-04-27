'use strict';

angular.module('nummapApp')
    .factory('School', function ($resource) {
        return $resource('api/schools/:name', {}, {
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

